package me.heyimblake.proxyparty.partyutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

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
 * @since 10/22/2016
 */
public enum PartyRole {
    LEADER,
    PARTICIPANT;

    private static Map<ProxiedPlayer, PartyRole> playerPartyRoleMap = new HashMap<>();

    /**
     * Gets the partyrole of a supplied player.
     *
     * @param player the player to retrieve the role of
     * @return the role of the supplied player, null if they have no role
     */
    public static PartyRole getRoleOf(ProxiedPlayer player) {
        return playerPartyRoleMap.getOrDefault(player, null);
    }

    /**
     * Removes the partyrole from a player.
     *
     * @param player the player to remove their partyrole from
     */
    public static void removeRoleFrom(ProxiedPlayer player) {
        playerPartyRoleMap.remove(player);
    }

    /**
     * Sets the role of a supplied player to a supplied partyrole.
     *
     * @param player the player to be set
     * @param role   the partyrole to set the player to
     */
    public static void setRoleOf(ProxiedPlayer player, PartyRole role) {
        if (playerPartyRoleMap.containsKey(player))
            playerPartyRoleMap.remove(player);
        playerPartyRoleMap.put(player, role);
    }

    /**
     * Gets a map of players with their roles.
     *
     * @return map of players and roles
     */
    public static Map<ProxiedPlayer, PartyRole> getPlayerPartyRoleMap() {
        return playerPartyRoleMap;
    }

    /**
     * Adds a player to the current partyrole.
     *
     * @param player the player to be added
     */
    public void addPlayer(ProxiedPlayer player) {
        removeRoleFrom(player);
        setRoleOf(player, this);
    }

    /**
     * Removes a player from the current partyrole.
     *
     * @param player the player to be removed
     */
    public void removePlayer(ProxiedPlayer player) {
        removeRoleFrom(player);
    }

    /**
     * Checks if a supplied player is has the current partyrole.
     *
     * @param player the player to check
     * @return true if their partyrole matches, false otherwise
     */
    public boolean check(ProxiedPlayer player) {
        return getRoleOf(player) == this;
    }
}
