package me.heyimblake.proxyparty.events;

import me.heyimblake.proxyparty.partyutils.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/**
 * Created by heyimblake on 11/3/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public class PartyAcceptInviteEvent extends Event {
    private Party party;
    private ProxiedPlayer accepter;

    public PartyAcceptInviteEvent(Party party, ProxiedPlayer accepter) {
        this.party = party;
        this.accepter = accepter;
    }

    public Party getParty() {
        return this.party;
    }

    public ProxiedPlayer getAccepter() {
        return this.accepter;
    }
}
