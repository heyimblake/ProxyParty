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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    private final Map<String, PartySubCommand> commands = new HashMap<>();

    public PartyCommand() {
        super("party", null);
        registerSubCommand(new InviteSubCommand());
        registerSubCommand(new AcceptSubCommand());
        registerSubCommand(new ChatSubCommand());
        registerSubCommand(new DenySubCommand());
        registerSubCommand(new DisbandSubCommand());
        registerSubCommand(new FindSubCommand());
        registerSubCommand(new InvitedSubCommand());
        registerSubCommand(new KickSubCommand());
        registerSubCommand(new LeaveSubCommand());
        registerSubCommand(new ListSubCommand());
        registerSubCommand(new PromoteSubCommand());
        registerSubCommand(new RetractSubCommand());
        registerSubCommand(new ToggleSubCommand());
        registerSubCommand(new WarpSubCommand());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Constants.TAG, new TextComponent("You must be a player in order to execute party commands."));
            return;
        }
        ProxiedPlayer player = ((ProxiedPlayer) sender);
        
        if (args.length <= 0) {
            showHelpMessage(player);
            
            return;
        }

        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

        PartySubCommand subCommand = this.getCommand(args[0]);

        if (subCommand == null) {
            showHelpMessage(player);

            return;
        }

        PartyAnnotationCommand annotations = subCommand.getAnnotations();

        if (annotations == null) return;

        if (PartyRole.getRoleOf(player) == PartyRole.PARTICIPANT && annotations.leaderExclusive()) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("You must be the party leader in order to do this.").color(ChatColor.RED).create()[0]);

            return;
        }

        if (newArgs.length == 0 && annotations.requiresArgumentCompletion()) {
            player.sendMessage(Constants.TAG, new TextComponent("Usage: "),
                    new ComponentBuilder(annotations.syntax()).color(ChatColor.AQUA)
                            .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party " + annotations.name() + " "))
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.YELLOW + "Click to prepare command.")}))
                            .create()[0]);
            return;
        }

        if (PartyManager.getInstance().getPartyOf(player) == null && annotations.mustBeInParty()) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("You must be in a party to do this!").color(ChatColor.RED).create()[0]);
            return;
        }

        subCommand.execute(player, newArgs);
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

        for (PartySubCommand command : commands.values()) {
            PartyAnnotationCommand annotations = command.getAnnotations();

            if (annotations == null) continue;

            //Bullet colors tell if the player can run the command or not. Just for a quick glance.
            ChatColor bulletColor = ChatColor.DARK_GREEN;

            if (annotations.mustBeInParty()) {
                if (!PartyManager.getInstance().hasParty(player)) {
                    bulletColor = ChatColor.DARK_RED;
                } else if (annotations.leaderExclusive()) {
                    if (PartyRole.getRoleOf(player) != PartyRole.LEADER) bulletColor = ChatColor.DARK_RED;
                }
            }

            pt1.setColor(bulletColor);

            TextComponent pt2 = new TextComponent(annotations.syntax());
            pt2.setColor(ChatColor.AQUA);
            pt2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{prepareMSG}));
            pt2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party " + annotations.name() + " "));

            TextComponent pt3 = new TextComponent(" - ");
            pt3.setColor(ChatColor.DARK_GRAY);

            TextComponent pt4 = new TextComponent(annotations.description());
            pt4.setColor(ChatColor.GRAY);

            player.sendMessage(pt1, pt2, pt3, pt4);
        }

        player.sendMessage(new TextComponent(" "));
    }

    private void registerSubCommand(PartySubCommand subCommand) {
        PartyAnnotationCommand annotation = subCommand.getAnnotations();

        if (annotation == null) return;

        commands.put(annotation.name(), subCommand);
    }

    private PartySubCommand getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
}
