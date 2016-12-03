package me.heyimblake.proxyparty.partyutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
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
