package me.heyimblake.proxyparty.Commands.Subcommands;

import me.heyimblake.proxyparty.Commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.Commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.Commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.PartyUtils.Party;
import me.heyimblake.proxyparty.PartyUtils.PartyManager;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
@PartySubCommandExecutor(subCommand = "disband",
        syntax = "/party disband",
        description = "Disbands your party.",
        requiresArgumentCompletion = false,
        leaderExclusive = true,
        mustBeInParty = true)
public class DisbandSubCommand extends AnnotatedPartySubCommand {

    public DisbandSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        if (!PartyManager.getInstance().hasParty(player)) {
            TextComponent msg = new TextComponent("You aren't in a party!");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        Party party = PartyManager.getInstance().getPartyOf(player);
        party.disband();
        TextComponent msg = new TextComponent("You disbanded the party.");
        msg.setColor(ChatColor.YELLOW);
        player.sendMessage(Constants.TAG, msg);
    }

    @Override
    public void runConsole() {

    }
}
