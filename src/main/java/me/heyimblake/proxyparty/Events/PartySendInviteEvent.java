package me.heyimblake.proxyparty.Events;

import me.heyimblake.proxyparty.PartyUtils.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartySendInviteEvent extends Event {
    private Party party;
    private ProxiedPlayer player;

    public PartySendInviteEvent(Party party, ProxiedPlayer player) {
        this.party = party;
        this.player = player;
    }

    public Party getParty() {
        return this.party;
    }

    public ProxiedPlayer getInvited() {
        return this.player;
    }

    public ProxiedPlayer getInviter() {
        return this.getParty().getLeader();
    }
}
