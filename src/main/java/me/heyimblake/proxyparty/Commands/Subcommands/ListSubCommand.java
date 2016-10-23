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
