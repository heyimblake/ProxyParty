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

    /**
     * Creates a new instance of a PartyCreator
     */
    public PartyCreator() {
        this.leader = null;
        this.participants = new HashSet<>();
    }

    /**
     * Set the leader.
     *
     * @param leader the player that will be leader
     * @return the current partycreator instance
     */
    public PartyCreator setLeader(ProxiedPlayer leader) {
        this.leader = leader;
        return this;
    }

    /**
     * Adds a participant
     *
     * @param participant the player to be added as a participant
     * @return the current partycreator instance
     */
    public PartyCreator addParticipant(ProxiedPlayer participant) {
        this.participants.add(participant);
        return this;
    }

    /**
     * Sets participants to a set of proxied players.
     *
     * @param participants set of proxied players to be added as participants
     * @return the current partycreator instance
     */
    public PartyCreator setParticipants(Set<ProxiedPlayer> participants) {
        this.participants = participants;
        return this;
    }

    /**
     * Creates a party with the previously supplied leader and/or participants.
     *
     * @return the created party instance
     */
    public Party create() {
        return new Party(this.leader, this.participants);
    }

}
