package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.CommandConditions;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
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
 * @since 10/26/2016
 */
@PartySubCommandExecutor(subCommand = "retract",
        syntax = "/party retract <Player>",
        description = "Retracts an invite from a player.",
        requiresArgumentCompletion = true,
        leaderExclusive = true,
        mustBeInParty = true)
public class RetractSubCommand extends AnnotatedPartySubCommand {

    public RetractSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        ProxiedPlayer target = ProxyParty.getInstance().getProxy().getPlayer(getHandler().getArguments()[0]);
        if (!CommandConditions.checkTargetOnline(target, player))
            return;

        Party party = PartyManager.getInstance().getPartyOf(player);
        if (!party.getInvited().contains(target)) {
            player.sendMessage(Constants.TAG, new ComponentBuilder(String.format("%s is not invited to your party.", target.getName())).color(ChatColor.RED).create()[0]);
            return;
        }
        party.retractInvite(target);
        player.sendMessage(Constants.TAG, new ComponentBuilder(String.format("You've retracted the invite of %s.", target.getName())).color(ChatColor.AQUA).create()[0]);
    }

    @Override
    public void runConsole() {

    }
}
