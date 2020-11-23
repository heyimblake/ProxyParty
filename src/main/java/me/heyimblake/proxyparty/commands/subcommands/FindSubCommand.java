package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.PartyAnnotationCommand;
import me.heyimblake.proxyparty.commands.PartySubCommand;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import me.heyimblake.proxyparty.utils.CommandConditions;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
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
@PartyAnnotationCommand(name = "find",
        syntax = "/party find <Player>",
        description = "Finds a party participant.",
        requiresArgumentCompletion = true)
public class FindSubCommand extends PartySubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);

        if (!CommandConditions.checkTargetOnline(target, player)) return;

        if (PartyManager.getInstance().getPartyOf(target).getLeader().getUniqueId() != PartyManager.getInstance().getPartyOf(player).getLeader().getUniqueId()) {
            player.sendMessage(Constants.TAG, new ComponentBuilder("That player isn't a member of your party!").color(ChatColor.RED).create()[0]);

            return;
        }

        ServerInfo serverInfo = target.getServer().getInfo();

        player.sendMessage(Constants.TAG, new ComponentBuilder(String.format("%s is playing on %s.", target.getName(), serverInfo.getName())).color(ChatColor.AQUA).create()[0]);

        new ActionLogEntry("find", player.getUniqueId(), new String[]{target.getName()}).log();
    }
}