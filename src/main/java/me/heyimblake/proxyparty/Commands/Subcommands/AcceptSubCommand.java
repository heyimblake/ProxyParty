package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
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
 * Created by heyimblake on 10/23/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
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
        if (target == null) {
            TextComponent msg = new TextComponent("The specified player could not be found.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        if (PartyManager.getInstance().hasParty(player)) {
            TextComponent msg = new TextComponent("You are already in a party!");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        if (PartyManager.getInstance().getPartyOf(target) == null || !PartyManager.getInstance().getPartyOf(target).getLeader().getUniqueId().equals(target.getUniqueId())) {
            TextComponent msg = new TextComponent("The specified player either is not in a party or is not the party leader.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        if (PartyManager.getInstance().getPartyOf(target).getParticipants().contains(player)) {
            TextComponent msg = new TextComponent("You've joined " + target.getName() + "'s Party!");
            msg.setColor(ChatColor.AQUA);
            Party party = PartyManager.getInstance().getPartyOf(target);
            party.addParticipant(player);
            party.getInvited().remove(player);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        TextComponent msg = new TextComponent("You have no pending invites for this party.");
        msg.setColor(ChatColor.RED);
        player.sendMessage(Constants.TAG, msg);
    }

    @Override
    public void runConsole() {

    }
}
