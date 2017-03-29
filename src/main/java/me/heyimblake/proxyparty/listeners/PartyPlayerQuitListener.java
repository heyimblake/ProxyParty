package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyPlayerQuitEvent;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

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
 * @since 10/23/2016
 */
public class PartyPlayerQuitListener implements Listener {
    @EventHandler
    public void onPartyPlayerQuit(PartyPlayerQuitEvent event) {
        Party party = event.getParty();
        ProxiedPlayer quitter = event.getWhoQuit();
        BaseComponent[] messages = new ComponentBuilder(Character.toString('\u2716').concat(" ")).color(ChatColor.RED).append(quitter.getName(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GRAY).create();

        party.getParticipants().forEach(participant -> participant.sendMessage(Constants.TAG, messages[0], messages[1]));
        party.getLeader().sendMessage(Constants.TAG, messages[0], messages[1]);

        new ActionLogEntry("leave", quitter.getUniqueId()).log();
    }
}
