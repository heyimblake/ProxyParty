package me.heyimblake.proxyparty.partyutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by heyimblake on 10/22/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public enum PartyRole {
    LEADER,
    PARTICIPANT;

    private static Map<ProxiedPlayer, me.heyimblake.proxyparty.partyutils.PartyRole> playerPartyRoleMap = new HashMap<>();

    /**
     * Gets the partyrole of a supplied player.
     *
     * @param player the player to retrieve the role of
     * @return the role of the supplied player, null if they have no role
     */
    public static me.heyimblake.proxyparty.partyutils.PartyRole getRoleOf(ProxiedPlayer player) {
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
    public static void setRoleOf(ProxiedPlayer player, me.heyimblake.proxyparty.partyutils.PartyRole role) {
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
