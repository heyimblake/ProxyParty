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
 * @since 10/22/2016
 */
public enum PartySetting {
    PARTY_CHAT_TOGGLE_ON("Chat", "Automatic Party Chat", new HashSet<>()),
    PARTY_INVITE_RECEIVE_TOGGLE_OFF("Invites", "Block Party Invites", new HashSet<>());

    private final Set<ProxiedPlayer> players;
    private final String argumentString;
    private final String niceName;

    PartySetting(String argumentString, String niceName, Set<ProxiedPlayer> players) {
        this.argumentString = argumentString;
        this.niceName = niceName;
        this.players = players;
    }

    /**
     * Gets a PartySetting that matches an argumentString.
     *
     * @param argumentString the argument string
     * @return the matching partysetting, or null if invalid
     */
    public static PartySetting getPartySetting(String argumentString) {
        for (PartySetting setting : PartySetting.values()) {
            if (!setting.getArgumentString().equalsIgnoreCase(argumentString)) continue;

            return setting;
        }

        return null;
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
     * Toggles a PartySetting for a specified player.
     *
     * @param player the player to toggle the partysetting for
     */
    public void toggle(ProxiedPlayer player) {
        if (this.isEnabledFor(player)) {
            this.disable(player);
        } else {
            this.enable(player);
        }
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

    /**
     * The string used in the toggle sub command.
     *
     * @return the argument in the toggle sub command corresponding to this setting
     */
    public String getArgumentString() {
        return argumentString;
    }

    /**
     * Gets the nice name of the setting.
     *
     * @return nice name
     */
    public String getNiceName() {
        return niceName;
    }
}