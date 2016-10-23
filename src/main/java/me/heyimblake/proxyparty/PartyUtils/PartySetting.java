package me.heyimblake.proxyparty.PartyUtils;

import net.md_5.bungee.api.connection.ProxiedPlayer;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by heyimblake on 10/22/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public enum PartySetting {
    PARTY_CHAT_TOGGLE_ON(new HashSet<>());

    private Set<ProxiedPlayer> players;

    PartySetting(Set<ProxiedPlayer> players) {
        this.players = players;
    }

    public Set<ProxiedPlayer> getPlayers() {
        return this.players;
    }

    public void add(ProxiedPlayer player) {
        this.players.add(player);
    }

    public void remove(ProxiedPlayer player) {
        this.players.remove(player);
    }
}
