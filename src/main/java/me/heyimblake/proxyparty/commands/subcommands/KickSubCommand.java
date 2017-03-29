package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.events.PartyKickEvent;
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
 * @since 10/21/2016
 */
@PartySubCommandExecutor(subCommand = "kick",
        syntax = "/party kick <Player>",
        description = "Remove a player from the party.",
        requiresArgumentCompletion = true,
        leaderExclusive = true,
        mustBeInParty = true)
public class KickSubCommand extends AnnotatedPartySubCommand {

    public KickSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        ProxiedPlayer target = ProxyParty.getInstance().getProxy().getPlayer(getHandler().getArguments()[0]);
        Party party = PartyManager.getInstance().getPartyOf(player);
        if (!CommandConditions.checkTargetOnline(target, player))
            return;

        Party targetParty = PartyManager.getInstance().getPartyOf(target);
        if (targetParty == null || targetParty != party) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("That player isn't in your party!").color(ChatColor.RED).create()[0]);
            return;
        }
        if (party.getLeader().getUniqueId() == target.getUniqueId() || target.getUniqueId() == player.getUniqueId()) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("You can't kick yourself or the Party Leader!").color(ChatColor.RED).create()[0]);
            return;
        }
        party.removeParticipant(target);
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyKickEvent(party, target));
        player.sendMessage(Constants.TAG, new ComponentBuilder(String.format("You kicked %s out of the party!", target.getName())).color(ChatColor.YELLOW).create()[0]);
    }

    @Override
    public void runConsole() {

    }
}
