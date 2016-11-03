package me.heyimblake.proxyparty.partyutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by heyimblake on 10/22/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public enum PartySetting {
    PARTY_CHAT_TOGGLE_ON(new HashSet<>());

    private Set<ProxiedPlayer> players;


    PartySetting(Set<ProxiedPlayer> players) {
        this.players = players;
    }

    /**
     * Gets a set of player for which the current PartySetting is active for.
     *
     * @return set of player with this setting currently active
     */
    public Set<ProxiedPlayer> getPlayers() {
        return this.players;
    }

    /**
     * Enables a PartySetting for a specified player.
     *
     * @param player the player to enable the partysetting for
     */
    public void enable(ProxiedPlayer player) {
        this.players.add(player);
    }

    /**
     * Disables a PartySetting for a specified player.
     *
     * @param player the player to disable the partysetting for
     */
    public void disable(ProxiedPlayer player) {
        this.players.remove(player);
    }

    /**
     * Sees if a supplied player has the current PartySetting enabled.
     *
     * @param player the player to check for
     * @return true if the player has this partysetting enabled, false otherwise
     */
    public boolean isEnabledFor(ProxiedPlayer player) {
        return getPlayers().contains(player);
    }
}
