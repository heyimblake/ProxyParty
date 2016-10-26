package me.heyimblake.proxyparty.events;

import me.heyimblake.proxyparty.partyutils.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * Created by heyimblake on 10/26/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartyDenyInviteEvent extends Event {
    private Party party;
    private ProxiedPlayer denier;

    public PartyDenyInviteEvent(Party party, ProxiedPlayer denier) {
        this.party = party;
        this.denier = denier;
    }

    public Party getParty() {
        return this.party;
    }

    public ProxiedPlayer getDenier() {
        return this.denier;
    }
}
