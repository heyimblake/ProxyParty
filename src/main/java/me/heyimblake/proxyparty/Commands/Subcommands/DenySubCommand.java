package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.events.PartyDenyInviteEvent;
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
 *         https://heyimblake.me
 */
@PartySubCommandExecutor(subCommand = "deny",
        syntax = "/party deny <Player>",
        description = "Denies a party invitation from a player.",
        requiresArgumentCompletion = true,
        leaderExclusive = false,
        mustBeInParty = false)
public class DenySubCommand extends AnnotatedPartySubCommand {

    public DenySubCommand(PartySubCommandHandler handler) {
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
        if (PartyManager.getInstance().getPartyOf(target) == null || !PartyManager.getInstance().getPartyOf(target).getLeader().getUniqueId().equals(target.getUniqueId())) {
            TextComponent msg = new TextComponent("The specified player either is not in a party or is not the party leader.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        Party party = PartyManager.getInstance().getPartyOf(target);
        if (!party.getInvited().contains(player)) {
            TextComponent msg = new TextComponent("You are not invited to this party.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        party.getInvited().remove(player);
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyDenyInviteEvent(party, player));
        TextComponent msg = new TextComponent("You declined " + target.getName() + "'s party invitation.");
        msg.setColor(ChatColor.AQUA);
        player.sendMessage(Constants.TAG, msg);
    }

    @Override
    public void runConsole() {

    }
}
