package me.heyimblake.proxyparty.Commands;

import me.heyimblake.proxyparty.Commands.Subcommands.ListSubCommand;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.lang.annotation.Annotation;
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
        super("party");
        defaultSubCommandClasses.add(ListSubCommand.class);
        registerDefaultSubCommands();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            PartySubCommandHandler handler = new PartySubCommandHandler(sender, Arrays.copyOfRange(args, 1, args.length));
            String subCMDInput = args[0];
            for (String key : subCommandClasses.keySet()) {
                if (key.equalsIgnoreCase(subCMDInput)) {
                    performSubCommand(subCommandClasses.get(key), handler);
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
        for (Class<? extends AnnotatedPartySubCommand> clazz : subCommandClasses.values()) {
            TextComponent prepareMSG = new TextComponent("Click to prepare to run this command.");
            prepareMSG.setColor(ChatColor.YELLOW);
            prepareMSG.setItalic(true);
            TextComponent pt1 = new TextComponent("" + '\u25CF' + " ");
            pt1.setColor(ChatColor.DARK_AQUA);
            TextComponent pt2 = new TextComponent(getSubCommandClassAnnotation(clazz).syntax());
            pt2.setColor(ChatColor.AQUA);
            pt2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,  new BaseComponent[]{prepareMSG}));
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
