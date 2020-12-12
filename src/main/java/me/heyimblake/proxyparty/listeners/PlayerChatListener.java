package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartySetting;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

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
 * @since 10/21/2016
 */
public class PlayerChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        
        Party party = PartyManager.getInstance().getPartyOf(player);

        if (party == null) return;
        if (!PartySetting.PARTY_CHAT_TOGGLE_ON.isEnabledFor(player)) return;
        if (event.getMessage().substring(0, 1).equalsIgnoreCase("/")) return;

        event.setCancelled(true);

        party.sendMessage(player, event.getMessage());

        new ActionLogEntry("chat", player.getUniqueId(), new String[]{event.getMessage()}).log();
    }
}
