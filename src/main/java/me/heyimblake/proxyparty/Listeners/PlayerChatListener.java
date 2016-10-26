package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartySetting;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.logging.Level;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PlayerChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        Party party = PartyManager.getInstance().getPartyOf(player);
        if (party == null) {
            return;
        }
        if (!PartySetting.PARTY_CHAT_TOGGLE_ON.isEnabledFor(player)) {
            return;
        }
        if (event.getMessage().substring(0, 1).equalsIgnoreCase("/"))
            return;
        event.setCancelled(true);
        party.sendMessage(player, event.getMessage());
        ProxyParty.getInstance().getLogger().log(Level.INFO, party.getLeader() + "'s PARTY CHAT: " + player.getName() + ": " + event.getMessage());
    }
}
