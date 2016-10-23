package me.heyimblake.proxyparty.Commands;

import me.heyimblake.proxyparty.Commands.Subcommands.ListSubCommand;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
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
        super("party");
        defaultSubCommandClasses.add(ListSubCommand.class);
        registerDefaultSubCommands();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            PartySubCommandHandler handler = new PartySubCommandHandler(sender, Arrays.copyOfRange(args, 1, args.length));
            String subCMDInput = args[0];
            subCommandClasses.keySet().stream().filter(subcmdstr -> subcmdstr.equalsIgnoreCase(subCMDInput)).forEach(subcmdstr -> {
                performSubCommand(subCommandClasses.get(subcmdstr), handler);
            });
            return;
        }
        TextComponent msg = new TextComponent("Parties are currently disabled.");
        msg.setColor(ChatColor.RED);
        msg.setBold(true);
        sender.sendMessage(Constants.TAG, msg);
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
        //todo
    }

    private void registerDefaultSubCommands() {
        defaultSubCommandClasses.stream().filter(clazz -> clazz.isAnnotationPresent(PartySubCommandExecutor.class)).forEach(clazz -> {
            registerSubCommand(clazz.getAnnotation(PartySubCommandExecutor.class).subCommand(), clazz);
        });
    }

    private void registerSubCommand(String name, Class<? extends AnnotatedPartySubCommand> annotatedPartySubCommand) {
        subCommandClasses.put(name, annotatedPartySubCommand);
    }

    private Map<String, Class<? extends AnnotatedPartySubCommand>> getSubCommandClasses() {
        return subCommandClasses;
    }
}
