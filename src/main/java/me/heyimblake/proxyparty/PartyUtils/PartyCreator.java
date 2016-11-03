package me.heyimblake.proxyparty.partyutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public class PartyCreator {
    private ProxiedPlayer leader;
    private Set<ProxiedPlayer> participants;

    public PartyCreator() {
        this.leader = null;
        this.participants = new HashSet<>();
    }

    public PartyCreator setLeader(ProxiedPlayer leader) {
        this.leader = leader;
        return this;
    }

    public PartyCreator addParticipant(ProxiedPlayer participant) {
        this.participants.add(participant);
        return this;
    }

    public PartyCreator setParticipants(Set<ProxiedPlayer> participants) {
        this.participants = participants;
        return this;
    }

    public Party create() {
        return new Party(this.leader, this.participants);
    }

}
