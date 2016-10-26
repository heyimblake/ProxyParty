package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyWarpEvent;
import me.heyimblake.proxyparty.partyutils.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by heyimblake on 10/26/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartyWarpListener implements Listener {
    @EventHandler
    public void onPartyWarp(PartyWarpEvent event) {
        Party party = event.getParty();
        party.sendMessage("The party leader has sent you to their server.", ChatColor.AQUA);
    }
}
