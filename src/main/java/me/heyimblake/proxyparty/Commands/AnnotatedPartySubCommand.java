package me.heyimblake.proxyparty.commands;


/**
 * Created by heyimblake on 10/22/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public abstract class AnnotatedPartySubCommand {
    private PartySubCommandHandler handler;

    public AnnotatedPartySubCommand(PartySubCommandHandler handler) {
        this.handler = handler;
    }

    public abstract void runProxiedPlayer();

    public abstract void runConsole();

    public PartySubCommandHandler getHandler() {
        return this.handler;
    }
}
