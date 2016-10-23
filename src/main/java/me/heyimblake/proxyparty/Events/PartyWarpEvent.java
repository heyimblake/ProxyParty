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
public class PartyWarpEvent extends Event {
    private Party party;

    public PartyWarpEvent(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return this.party;
    }

    public ProxiedPlayer getWarper() {
        return this.getParty().getLeader();
    }
}
