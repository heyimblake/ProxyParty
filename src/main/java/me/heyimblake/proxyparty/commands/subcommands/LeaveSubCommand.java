package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.PartyAnnotationCommand;
import me.heyimblake.proxyparty.commands.PartySubCommand;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartyRole;
import me.heyimblake.proxyparty.utils.Constants;
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
 * @since 10/21/2016
 */
@PartyAnnotationCommand(name = "leave",
        syntax = "/party leave",
        description = "Leave your current party.",
        requiresArgumentCompletion = false)
public class LeaveSubCommand extends PartySubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        Party party = PartyManager.getInstance().getPartyOf(player);

        if (PartyRole.getRoleOf(player) == PartyRole.LEADER) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("You're the leader! Either promote another member to be leader and leave or disband the party.").color(ChatColor.RED).create()[0]);

            return;
        }

        party.removeParticipant(player);

        player.sendMessage(Constants.TAG, new ComponentBuilder("You left the party.").color(ChatColor.YELLOW).create()[0]);
    }
}