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

    public static me.heyimblake.proxyparty.partyutils.PartyRole getRoleOf(ProxiedPlayer player) {
        return playerPartyRoleMap.get(player);
    }

    public static void removeRoleFrom(ProxiedPlayer player) {
        playerPartyRoleMap.remove(player);
    }

    public static void setRoleOf(ProxiedPlayer player, me.heyimblake.proxyparty.partyutils.PartyRole role) {
        if (playerPartyRoleMap.containsKey(player))
            playerPartyRoleMap.remove(player);
        playerPartyRoleMap.put(player, role);
    }

    public static Map<ProxiedPlayer, PartyRole> getPlayerPartyRoleMap() {
        return playerPartyRoleMap;
    }
}
