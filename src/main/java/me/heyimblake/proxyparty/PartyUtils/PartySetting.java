package me.heyimblake.proxyparty.partyutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by heyimblake on 10/22/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public enum PartySetting {
    PARTY_CHAT_TOGGLE_ON("Chat", "Automatic Party Chat", new HashSet<>()),
    PARTY_INVITE_RECIEVE_TOGGLE_OFF("Invites", "Block Party Invites", new HashSet<>());

    private Set<ProxiedPlayer> players;
    private String argumentString, niceName;

    PartySetting(String argumentString, String niceName, Set<ProxiedPlayer> players) {
        this.argumentString = argumentString;
        this.niceName = niceName;
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
     * Toggles a PartySetting for a specified player.
     *
     * @param player the player to toggle the partysetting for
     */
    public void toggle(ProxiedPlayer player) {
        if (this.isEnabledFor(player))
            this.disable(player);
        else
            this.enable(player);
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

    /**
     * Gets a PartySetting that matches an argumentString.
     *
     * @param argumentString the argument string
     * @return the matching partysetting, or null if invalid
     */
    public static PartySetting getPartySetting(String argumentString) {
        for (PartySetting setting : PartySetting.values()) {
            if (setting.getArgumentString().equalsIgnoreCase(argumentString))
                return setting;
        }
        return null;
    }
}
