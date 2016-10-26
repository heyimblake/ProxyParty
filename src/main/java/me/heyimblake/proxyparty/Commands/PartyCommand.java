package me.heyimblake.proxyparty.Commands;

import me.heyimblake.proxyparty.Commands.Subcommands.*;
import me.heyimblake.proxyparty.PartyUtils.PartyManager;
import me.heyimblake.proxyparty.PartyUtils.PartyRole;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartyCommand extends Command {

    private static Map<String, Class<? extends AnnotatedPartySubCommand>> subCommandClasses = new HashMap<>();
    private static Set<Class<? extends AnnotatedPartySubCommand>> defaultSubCommandClasses = new HashSet<>();

    public PartyCommand() {
        super("party", null, "p");
        defaultSubCommandClasses.add(InviteSubCommand.class);
        defaultSubCommandClasses.add(AcceptSubCommand.class);
        defaultSubCommandClasses.add(DenySubCommand.class);
        defaultSubCommandClasses.add(FindSubCommand.class);
        defaultSubCommandClasses.add(ListSubCommand.class);
        defaultSubCommandClasses.add(InvitedSubCommand.class);
        defaultSubCommandClasses.add(RetractSubCommand.class);
        defaultSubCommandClasses.add(ChatSubCommand.class);
        defaultSubCommandClasses.add(LeaveSubCommand.class);
        defaultSubCommandClasses.add(PromoteSubCommand.class);
        defaultSubCommandClasses.add(DisbandSubCommand.class);
        registerDefaultSubCommands();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            PartySubCommandHandler handler = new PartySubCommandHandler(sender, Arrays.copyOfRange(args, 1, args.length));
            String subCMDInput = args[0];
            for (String key : subCommandClasses.keySet()) {
                Class<? extends AnnotatedPartySubCommand> clazz = subCommandClasses.get(key);
                if (key.equalsIgnoreCase(subCMDInput)) {
                    if (sender instanceof ProxiedPlayer && PartyRole.getRoleOf(((ProxiedPlayer) sender)) == PartyRole.PARTICIPANT && getSubCommandClassAnnotation(clazz).leaderExclusive()) {
                        TextComponent errmsg = new TextComponent("You must be the party leader in order to do this.");
                        errmsg.setColor(ChatColor.RED);
                        sender.sendMessage(Constants.TAG, errmsg);
                        return;
                    }
                    if (handler.getArguments().length == 0 && getSubCommandClassAnnotation(clazz).requiresArgumentCompletion()) {
                        TextComponent usage = new TextComponent(getSubCommandClassAnnotation(clazz).syntax());
                        usage.setColor(ChatColor.AQUA);
                        usage.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party " + getSubCommandClassAnnotation(clazz).subCommand() + " "));
                        usage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.YELLOW + "Click to prepare command.")}));
                        sender.sendMessage(Constants.TAG, new TextComponent("Usage: "), usage);
                        return;
                    }
                    if (sender instanceof ProxiedPlayer && PartyManager.getInstance().getPartyOf(((ProxiedPlayer) sender)) == null && getSubCommandClassAnnotation(clazz).mustBeInParty()) {
                        TextComponent errmsg = new TextComponent("You must be in a party in order to do this!");
                        errmsg.setColor(ChatColor.RED);
                        sender.sendMessage(Constants.TAG, errmsg);
                        return;
                    }
                    performSubCommand(clazz, handler);
                    return;
                }
            }
            showHelpMessage(sender);
            return;
        }
        showHelpMessage(sender);
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


    private void showHelpMessage(CommandSender sender) {
        TextComponent topMSG = new TextComponent("Party Commands:");
        topMSG.setColor(ChatColor.LIGHT_PURPLE);
        topMSG.setBold(true);
        sender.sendMessage(topMSG);
        TextComponent prepareMSG = new TextComponent("Click to prepare this command.");
        prepareMSG.setColor(ChatColor.YELLOW);
        prepareMSG.setItalic(true);
        for (Class<? extends AnnotatedPartySubCommand> clazz : subCommandClasses.values()) {
            TextComponent pt1 = new TextComponent("" + '\u25CF' + " ");
            pt1.setColor(ChatColor.DARK_AQUA);
            TextComponent pt2 = new TextComponent(getSubCommandClassAnnotation(clazz).syntax());
            pt2.setColor(ChatColor.AQUA);
            pt2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{prepareMSG}));
            pt2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party " + getSubCommandClassAnnotation(clazz).subCommand() + " "));
            TextComponent pt3 = new TextComponent(" - ");
            pt3.setColor(ChatColor.DARK_GRAY);
            TextComponent pt4 = new TextComponent(getSubCommandClassAnnotation(clazz).description());
            pt4.setColor(ChatColor.GRAY);
            sender.sendMessage(pt1, pt2, pt3, pt4);
        }
        sender.sendMessage(new TextComponent(" "));

    }

    private void registerDefaultSubCommands() {
        defaultSubCommandClasses.forEach(clazz -> registerSubCommand(getSubCommandClassAnnotation(clazz).subCommand(), clazz));
    }

    private void registerSubCommand(String name, Class<? extends AnnotatedPartySubCommand> annotatedPartySubCommand) {
        subCommandClasses.put(name, annotatedPartySubCommand);
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
