package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.Constants;
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
@PartySubCommandExecutor(subCommand = "list",
        syntax = "/party list",
        description = "Shows a list of current party members.",
        requiresArgumentCompletion = false,
        leaderExclusive = false,
        mustBeInParty = true)
public class ListSubCommand extends AnnotatedPartySubCommand {

    public ListSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        Party party = PartyManager.getInstance().getPartyOf(player);
        TextComponent line1 = new TextComponent("Party Leader: ");
        line1.setColor(ChatColor.YELLOW);
        line1.addExtra(new TextComponent(party.getLeader().getName()));

        TextComponent line2 = new TextComponent("Participants: ");
        line2.setColor(ChatColor.AQUA);

        String allParticipants = "";
        party.getParticipants().forEach(participants -> allParticipants.concat(participants.getName() + ", "));
        TextComponent line3 = new TextComponent(allParticipants);

        player.sendMessage(Constants.TAG, line1);
        player.sendMessage(Constants.TAG, line2);
        player.sendMessage(Constants.TAG, line3);
    }

    @Override
    public void runConsole() {

    }
}
