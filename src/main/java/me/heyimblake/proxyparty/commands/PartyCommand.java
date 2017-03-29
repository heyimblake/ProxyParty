package me.heyimblake.proxyparty.commands;

import me.heyimblake.proxyparty.commands.subcommands.*;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartyRole;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Copyright (C) 2017 heyimblake
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author heyimblake
 * @since 10/21/2016
 */
public class PartyCommand extends Command {

    private static Map<String, Class<? extends AnnotatedPartySubCommand>> subCommandClasses = new HashMap<>();

    public PartyCommand() {
        super("party", null);
        registerSubCommand(InviteSubCommand.class);
        registerSubCommand(AcceptSubCommand.class);
        registerSubCommand(DenySubCommand.class);
        registerSubCommand(FindSubCommand.class);
        registerSubCommand(ListSubCommand.class);
        registerSubCommand(InvitedSubCommand.class);
        registerSubCommand(RetractSubCommand.class);
        registerSubCommand(KickSubCommand.class);
        registerSubCommand(ChatSubCommand.class);
        registerSubCommand(WarpSubCommand.class);
        registerSubCommand(LeaveSubCommand.class);
        registerSubCommand(PromoteSubCommand.class);
        registerSubCommand(DisbandSubCommand.class);
        registerSubCommand(ToggleSubCommand.class);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Constants.TAG, new TextComponent("You must be a player in order to execute party commands."));
            return;
        }
        ProxiedPlayer player = ((ProxiedPlayer) sender);
        if (args.length > 0) {
            PartySubCommandHandler handler = new PartySubCommandHandler(sender, Arrays.copyOfRange(args, 1, args.length));
            String subCMDInput = args[0];
            for (String key : subCommandClasses.keySet()) {
                Class<? extends AnnotatedPartySubCommand> clazz = subCommandClasses.get(key);
                if (key.equalsIgnoreCase(subCMDInput)) {
                    if (PartyRole.getRoleOf(player) == PartyRole.PARTICIPANT && getSubCommandClassAnnotation(clazz).leaderExclusive()) {
                        player.sendMessage(Constants.TAG, new ComponentBuilder("You must be the party leader in order to do this.").color(ChatColor.RED).create()[0]);
                        return;
                    }
                    PartySubCommandExecutor partySubCommandExecutor = getSubCommandClassAnnotation(clazz);
                    if (handler.getArguments().length == 0 && getSubCommandClassAnnotation(clazz).requiresArgumentCompletion()) {
                        player.sendMessage(Constants.TAG, new TextComponent("Usage: "),
                                new ComponentBuilder(partySubCommandExecutor.syntax()).color(ChatColor.AQUA)
                                        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party " + partySubCommandExecutor.subCommand() + " "))
                                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.YELLOW + "Click to prepare command.")}))
                                        .create()[0]);
                        return;
                    }
                    if (PartyManager.getInstance().getPartyOf(player) == null && partySubCommandExecutor.mustBeInParty()) {
                        player.sendMessage(Constants.TAG, new ComponentBuilder("You must be in a party to do this!").color(ChatColor.RED).create()[0]);
                        return;
                    }
                    performSubCommand(clazz, handler);
                    return;
                }
            }
            showHelpMessage(player);
            return;
        }
        showHelpMessage(player);
    }

    private void performSubCommand(Class<? extends AnnotatedPartySubCommand> clazz, PartySubCommandHandler handler) {
        try {
            Constructor constructor = clazz.getConstructor(handler.getClass());
            AnnotatedPartySubCommand subCommandInstance = (AnnotatedPartySubCommand) constructor.newInstance(handler);
            if (handler.isSenderProxiedPlayer()) {
                subCommandInstance.runProxiedPlayer();
            } else {
                subCommandInstance.runConsole();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private void showHelpMessage(ProxiedPlayer player) {
        TextComponent topMSG = new TextComponent("Party Commands:");
        topMSG.setColor(ChatColor.LIGHT_PURPLE);
        topMSG.setBold(true);
        player.sendMessage(topMSG);
        TextComponent prepareMSG = new TextComponent("Click to prepare this command.");
        prepareMSG.setColor(ChatColor.YELLOW);
        prepareMSG.setItalic(true);
        TextComponent pt1 = new TextComponent("" + '\u25CF' + " ");
        for (Class<? extends AnnotatedPartySubCommand> clazz : subCommandClasses.values()) {
            PartySubCommandExecutor annotatedPartySubCommand = getSubCommandClassAnnotation(clazz);
            //Bullet colors tell if the player can run the command or not. Just for a quick glance.
            ChatColor bulletColor = ChatColor.DARK_GREEN;
            if (annotatedPartySubCommand.mustBeInParty()) {
                if (!PartyManager.getInstance().hasParty(player)) {
                    bulletColor = ChatColor.DARK_RED;
                } else {
                    if (annotatedPartySubCommand.leaderExclusive()) {
                        if (PartyRole.getRoleOf(player) != PartyRole.LEADER)
                            bulletColor = ChatColor.DARK_RED;
                    }
                }
            }
            pt1.setColor(bulletColor);

            TextComponent pt2 = new TextComponent(annotatedPartySubCommand.syntax());
            pt2.setColor(ChatColor.AQUA);
            pt2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{prepareMSG}));
            pt2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party " + annotatedPartySubCommand.subCommand() + " "));
            TextComponent pt3 = new TextComponent(" - ");
            pt3.setColor(ChatColor.DARK_GRAY);
            TextComponent pt4 = new TextComponent(annotatedPartySubCommand.description());
            pt4.setColor(ChatColor.GRAY);
            player.sendMessage(pt1, pt2, pt3, pt4);
        }
        player.sendMessage(new TextComponent(" "));
    }

    private void registerSubCommand(Class<? extends AnnotatedPartySubCommand> clazz) {
        subCommandClasses.put(getSubCommandClassAnnotation(clazz).subCommand(), clazz);
    }

    private Map<String, Class<? extends AnnotatedPartySubCommand>> getSubCommandClasses() {
        return subCommandClasses;
    }

    /**
     * Gets the Annotation of a AnnotatedPartySubCommand class.
     *
     * @param clazz the annotatedpartysubcommand class
     * @return Annotation if it exists, null if invalid
     */
    private PartySubCommandExecutor getSubCommandClassAnnotation(Class<? extends AnnotatedPartySubCommand> clazz) {
        if (clazz.isAnnotationPresent(PartySubCommandExecutor.class)) {
            return clazz.getAnnotation(PartySubCommandExecutor.class);
        }
        return null;
    }
}
