package me.heyimblake.proxyparty.Commands.Subcommands;

import me.heyimblake.proxyparty.Commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.Commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.Commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.PartyUtils.PartyManager;
import me.heyimblake.proxyparty.PartyUtils.PartySetting;
import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
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
                PartySetting.PARTY_CHAT_TOGGLE_ON.remove(player);
            else
                PartySetting.PARTY_CHAT_TOGGLE_ON.add(player);
            TextComponent message = new TextComponent(msg);
            message.setColor(color);
            player.sendMessage(Constants.TAG, message);
            return;
        }
        final String[] message = {""};
        Arrays.stream(getHandler().getArguments()).forEach(string -> message[0] += string + " ");
        PartyManager.getInstance().getPartyOf(player).sendMessage(player, message[0]);
        ProxyParty.getInstance().getLogger().log(Level.INFO, PartyManager.getInstance().getPartyOf(player).getLeader() + "'s PARTY CHAT: " + player.getName() + ": " + message[0]);
    }

    @Override
    public void runConsole() {

    }
}
