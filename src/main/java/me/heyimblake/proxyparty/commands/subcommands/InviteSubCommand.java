package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyCreator;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartySetting;
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
@PartySubCommandExecutor(subCommand = "invite",
        syntax = "/party invite <Player>",
        description = "Invites a player (and creates a party if not in one).",
        requiresArgumentCompletion = true,
        leaderExclusive = true,
        mustBeInParty = false)
public class InviteSubCommand extends AnnotatedPartySubCommand {

    public InviteSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        String targetName = getHandler().getArguments()[0];
        ProxiedPlayer target = ProxyParty.getInstance().getProxy().getPlayer(targetName);
        if (target == null || (target != null && target.getUniqueId() == player.getUniqueId())) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("Cannot find or invite that player to your party! :(").color(ChatColor.RED).create()[0]);
            return;
        }
        Party targetParty = PartyManager.getInstance().getPartyOf(target);
        if (targetParty != null) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("This player is already a member of a party. Ask them to leave their party and try this command again.").color(ChatColor.RED).create()[0]);
            return;
        }
        Party party = !PartyManager.getInstance().hasParty(player) ? new PartyCreator().setLeader(player).create() : PartyManager.getInstance().getPartyOf(player);
        if (party.getInvited().contains(target)) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("This player is already invited to your party!").color(ChatColor.RED).create()[0]);
            return;
        }
        if (PartySetting.PARTY_INVITE_RECIEVE_TOGGLE_OFF.getPlayers().contains(target)) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("This player is currently not accepting party invitations.").color(ChatColor.RED).create()[0]);
            return;
        }
        party.invitePlayer(target);
        player.sendMessage(Constants.TAG, new ComponentBuilder(String.format("Invited player %s!", targetName)).color(ChatColor.AQUA).create()[0]);
    }

    @Override
    public void runConsole() {

    }
}
