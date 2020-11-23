package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.PartyAnnotationCommand;
import me.heyimblake.proxyparty.commands.PartySubCommand;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartySetting;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
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
@PartyAnnotationCommand(name = "invite",
        syntax = "/party invite <Player>",
        description = "Invites a player (and creates a party if not in one).",
        requiresArgumentCompletion = true,
        leaderExclusive = true,
        mustBeInParty = false)
public class InviteSubCommand extends PartySubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);

        if (target == null || target.getUniqueId() == player.getUniqueId()) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("Cannot find or invite that player to your party! :(").color(ChatColor.RED).create()[0]);

            return;
        }

        Party targetParty = PartyManager.getInstance().getPartyOf(target);

        if (targetParty != null) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("This player is already a member of a party. Ask them to leave their party and try this command again.").color(ChatColor.RED).create()[0]);

            return;
        }

        Party party = PartyManager.getInstance().getPartyOf(player);

        if (party == null) {
            party = new Party(player);
        }

        if (party.getInvited().contains(target)) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("This player is already invited to your party!").color(ChatColor.RED).create()[0]);

            return;
        }

        if (PartySetting.PARTY_INVITE_RECEIVE_TOGGLE_OFF.getPlayers().contains(target)) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("This player is currently not accepting party invitations.").color(ChatColor.RED).create()[0]);

            return;
        }

        party.invitePlayer(target);

        player.sendMessage(Constants.TAG, new ComponentBuilder(String.format("Invited player %s!", target.getName())).color(ChatColor.AQUA).create()[0]);
    }
}