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
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
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

    public Party getPartyOf(ProxiedPlayer player) {
        return this.playerPartyMap.get(player);
    }

    public Map<ProxiedPlayer, Party> getPlayerPartyMap() {
        return this.playerPartyMap;
    }

    public boolean hasParty(ProxiedPlayer player) {
        return this.playerPartyMap.containsKey(player);
    }

    public Set<Party> getActiveParties() {
        return this.activeParties;
    }
}
