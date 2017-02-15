package me.heyimblake.proxyparty.partyutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
public class PartyManager {
    private static PartyManager instance = new PartyManager();
    private Map<ProxiedPlayer, Party> playerPartyMap;
    private Set<Party> activeParties;

    private PartyManager() {
        this.playerPartyMap = new HashMap<>();
        this.activeParties = new HashSet<>();
    }

    public static PartyManager getInstance() {
        return instance;
    }

    /**
     * Gets the instance of the party of which a player is in.
     *
     * @param player the player in the party
     * @return the party of the specified player, null if there is not a party
     */
    public Party getPartyOf(ProxiedPlayer player) {
        return this.playerPartyMap.getOrDefault(player, null);
    }

    /**
     * Gets a map of players and their party instances.
     *
     * @return map of players and party instances
     */
    public Map<ProxiedPlayer, Party> getPlayerPartyMap() {
        return this.playerPartyMap;
    }

    /**
     * See if a specified player is a participant or leader of a party.
     *
     * @param player the player to check their party status
     * @return true if player has a party, false otherwise
     */
    public boolean hasParty(ProxiedPlayer player) {
        return this.playerPartyMap.containsKey(player);
    }

    /**
     * Get a set of active party instances.
     *
     * @return set of active party instances
     */
    public Set<Party> getActiveParties() {
        return this.activeParties;
    }
}
