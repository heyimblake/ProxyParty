package me.heyimblake.proxyparty.Commands;


/**
 * Created by heyimblake on 10/22/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public abstract class AnnotatedPartySubCommand {
    public abstract void runProxiedPlayer();
    public abstract void runConsole();

    private PartySubCommandHandler handler;

    public AnnotatedPartySubCommand(PartySubCommandHandler handler) {
        this.handler = handler;
    }

    public PartySubCommandHandler getHandler() {
        return this.handler;
    }
}
