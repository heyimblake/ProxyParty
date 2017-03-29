package me.heyimblake.proxyparty.utils;

import me.heyimblake.proxyparty.partyutils.PartyManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
 * @since 3/29/2017
 */
public class CommandConditions {

    private static PartyManager partyManager = PartyManager.getInstance();

    public static boolean checkTargetOnline(ProxiedPlayer proxiedPlayer, ProxiedPlayer sender) {
        if (proxiedPlayer == null) {
            sender.sendMessage(Constants.TAG, new ComponentBuilder("The specified player could not be found.").color(ChatColor.RED).create()[0]);
            return false;
        }
        return true;
    }

    public static boolean blockIfHasParty(ProxiedPlayer sender) {
        if (partyManager.hasParty(sender)) {
            sender.sendMessage(Constants.TAG, new ComponentBuilder("You are already in a party!").color(ChatColor.RED).create()[0]);
            return false;
        }
        return true;
    }
}
