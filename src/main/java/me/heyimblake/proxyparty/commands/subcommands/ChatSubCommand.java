package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartySetting;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;

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
@PartySubCommandExecutor(subCommand = "chat",
        syntax = "/party chat (Message)",
        description = "Sends a message to all party members, or toggles automatic party chat.",
        requiresArgumentCompletion = false,
        leaderExclusive = false,
        mustBeInParty = true)
public class ChatSubCommand extends AnnotatedPartySubCommand {

    public ChatSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        if (getHandler().getArguments().length == 0) {
            String msg = !PartySetting.PARTY_CHAT_TOGGLE_ON.isEnabledFor(player) ? "All messages will now be sent to party chat." : "All messages will no longer be sent to party chat.";
            ChatColor color = !PartySetting.PARTY_CHAT_TOGGLE_ON.isEnabledFor(player) ? ChatColor.GREEN : ChatColor.RED;
            if (PartySetting.PARTY_CHAT_TOGGLE_ON.isEnabledFor(player))
                PartySetting.PARTY_CHAT_TOGGLE_ON.disable(player);
            else
                PartySetting.PARTY_CHAT_TOGGLE_ON.enable(player);
            TextComponent message = new TextComponent(msg);
            message.setColor(color);
            player.sendMessage(Constants.TAG, message);
            return;
        }
        final String[] message = {""};
        Arrays.stream(getHandler().getArguments()).forEach(string -> message[0] += string + " ");
        PartyManager.getInstance().getPartyOf(player).sendMessage(player, message[0]);
        new ActionLogEntry("chat", player.getUniqueId(), new String[]{message[0]}).log();
    }

    @Override
    public void runConsole() {

    }
}
