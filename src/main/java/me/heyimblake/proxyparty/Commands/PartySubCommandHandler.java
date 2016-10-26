package me.heyimblake.proxyparty.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by heyimblake on 10/22/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartySubCommandHandler {
    private CommandSender commandSender;
    private String[] arguments;

    public PartySubCommandHandler(CommandSender commandSender, String[] arguments) {
        this.commandSender = commandSender;
        this.arguments = arguments;
    }

    public CommandSender getCommandSender() {
        return this.commandSender;
    }

    public String[] getArguments() {
        return this.arguments;
    }

    public boolean isSenderProxiedPlayer() {
        return getCommandSender() instanceof ProxiedPlayer;
    }
}
