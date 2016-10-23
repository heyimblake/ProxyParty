package me.heyimblake.proxyparty.PartyUtils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by heyimblake on 10/22/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public enum PartyRole {
    LEADER,
    PARTICIPANT;

    private static Map<ProxiedPlayer, me.heyimblake.proxyparty.PartyUtils.PartyRole> playerPartyRoleMap = new HashMap<>();

    public static me.heyimblake.proxyparty.PartyUtils.PartyRole getRoleOf(ProxiedPlayer player) {
        return playerPartyRoleMap.get(player);
    }

    public static void removeRoleFrom(ProxiedPlayer player) {
        playerPartyRoleMap.remove(player);
    }

    public static void setRoleOf(ProxiedPlayer player, me.heyimblake.proxyparty.PartyUtils.PartyRole role) {
        if (playerPartyRoleMap.containsKey(player))
            playerPartyRoleMap.remove(player);
        playerPartyRoleMap.put(player, role);
    }

    public static Map<ProxiedPlayer, PartyRole> getPlayerPartyRoleMap() {
        return playerPartyRoleMap;
    }
}
