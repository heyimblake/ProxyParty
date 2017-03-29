package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyRetractInviteEvent;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
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
 * @since 10/26/2016
 */
public class PartyRetractInviteListener implements Listener {
    @EventHandler
    public void onPartyRetractInvite(PartyRetractInviteEvent event) {
        ProxiedPlayer retracted = event.getRetracted();
        ProxiedPlayer retractor = event.getRetractor();

        retracted.sendMessage(Constants.TAG, new ComponentBuilder(String.format("%s has retracted your invitation to their party.", retractor.getName())).color(ChatColor.AQUA).create()[0]);
        new ActionLogEntry("retract", retractor.getUniqueId(), new String[]{retracted.getName()}).log();
    }
}
