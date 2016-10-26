package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.Constants;
import me.heyimblake.proxyparty.utils.MiscMemory;
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
@PartySubCommandExecutor(subCommand = "warp",
        syntax = "/party warp",
        description = "Warp all players to your server.",
        requiresArgumentCompletion = false,
        leaderExclusive = true,
        mustBeInParty = true)
public class WarpSubCommand extends AnnotatedPartySubCommand {

    public WarpSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        Party party = PartyManager.getInstance().getPartyOf(player);
        TextComponent msg = new TextComponent("You've been warped to your party leader's server!");
        msg.setColor(ChatColor.AQUA);
        for (ProxiedPlayer participants : party.getParticipants()) {
            MiscMemory.SERVER_JOIN_BYPASS.add(participants);
            participants.connect(player.getServer().getInfo());
            participants.sendMessage(Constants.TAG, msg);
            MiscMemory.SERVER_JOIN_BYPASS.remove(participants);
        }
        TextComponent senderMSG = new TextComponent("Warped all party participants to your server.");
        senderMSG.setColor(ChatColor.AQUA);
        player.sendMessage(Constants.TAG, senderMSG);
    }

    @Override
    public void runConsole() {

    }
}
