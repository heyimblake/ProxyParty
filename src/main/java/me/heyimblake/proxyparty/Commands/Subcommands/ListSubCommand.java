package me.heyimblake.proxyparty.Commands.Subcommands;

import me.heyimblake.proxyparty.Commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.Commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.Commands.PartySubCommandHandler;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
@PartySubCommandExecutor(subCommand = "list",
        syntax = "/party list",
        description = "Shows a list of current party members.",
        requiresArgumentCompletion = false,
        leaderExclusive = false)
public class ListSubCommand extends AnnotatedPartySubCommand {

    public ListSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {

    }

    @Override
    public void runConsole() {

    }
}
