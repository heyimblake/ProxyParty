package me.heyimblake.proxyparty.partyutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (C) 2017 heyimblake
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author heyimblake
 * @since 10/21/2016
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
