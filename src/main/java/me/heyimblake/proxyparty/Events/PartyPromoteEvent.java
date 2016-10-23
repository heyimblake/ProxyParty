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
public class PartyPromoteEvent extends Event {
    private Party party;
    private ProxiedPlayer player, promoter;

    public PartyPromoteEvent(Party party, ProxiedPlayer player, ProxiedPlayer promoter) {
        this.party = party;
        this.player = player;
        this.promoter = promoter;
    }

    public Party getParty() {
        return this.party;
    }

    public ProxiedPlayer getPromoted() {
        return this.player;
    }

    public ProxiedPlayer getPromoter() {
        return this.promoter;
    }
}
