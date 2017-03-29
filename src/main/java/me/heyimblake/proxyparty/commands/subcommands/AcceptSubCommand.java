package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.events.PartyAcceptInviteEvent;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.CommandConditions;
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
 * @since 10/23/2016
 */
@PartySubCommandExecutor(subCommand = "accept",
        syntax = "/party accept <Player>",
        description = "Accepts a party invitation from a player.",
        requiresArgumentCompletion = true,
        leaderExclusive = false,
        mustBeInParty = false)
public class AcceptSubCommand extends AnnotatedPartySubCommand {

    public AcceptSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        ProxiedPlayer target = ProxyParty.getInstance().getProxy().getPlayer(getHandler().getArguments()[0]);

        if (!CommandConditions.checkTargetOnline(target, player))
            return;

        if (!CommandConditions.blockIfHasParty(player))
            return;

        if (PartyManager.getInstance().getPartyOf(target) == null || !PartyManager.getInstance().getPartyOf(target).getLeader().getUniqueId().equals(target.getUniqueId())) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("The specified player either is not in a party or is not the party leader.").color(ChatColor.RED).create()[0]);
            return;
        }

        if (PartyManager.getInstance().getPartyOf(target).getInvited().contains(player)) {
            if (Constants.MAX_PARTY_SIZE <= PartyManager.getInstance().getPartyOf(target).getParticipants().size() && Constants.MAX_PARTY_SIZE != -1) {
                player.sendMessage(Constants.TAG, new ComponentBuilder("Sorry, but that party is full. Perhaps the leader could make some room for you.").color(ChatColor.RED).bold(true).create()[0]);
                return;
            }

            Party party = PartyManager.getInstance().getPartyOf(target);
            party.addParticipant(player);
            party.getInvited().remove(player);
            player.sendMessage(Constants.TAG, new ComponentBuilder(String.format("You've joined %s's Party!", target.getName())).color(ChatColor.AQUA).create()[0]);
            ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyAcceptInviteEvent(party, player));
            return;
        }
        player.sendMessage(Constants.TAG, new ComponentBuilder("You have no pending invites for this party.").color(ChatColor.RED).create()[0]);
    }

    @Override
    public void runConsole() {

    }
}
