package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyCreator;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
@PartySubCommandExecutor(subCommand = "invite",
        syntax = "/party invite <Player>",
        description = "Invites a player (and creates a party if not in one).",
        requiresArgumentCompletion = true,
        leaderExclusive = true,
        mustBeInParty = false)
public class InviteSubCommand extends AnnotatedPartySubCommand {

    public InviteSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        String targetName = getHandler().getArguments()[0];
        ProxiedPlayer target = ProxyParty.getInstance().getProxy().getPlayer(targetName);
        if (target == null || (target != null && target.getUniqueId() == player.getUniqueId())) {
            TextComponent msg = new TextComponent("Cannot find or invite that player to your party! :(");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        Party targetParty = PartyManager.getInstance().getPartyOf(target);
        if (targetParty != null) {
            TextComponent msg = new TextComponent("This player is already a member of a party. Ask them to leave their party and try this command again.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        Party party = !PartyManager.getInstance().hasParty(player) ? new PartyCreator().setLeader(player).create() : PartyManager.getInstance().getPartyOf(player);
        if (party.getParticipants().contains(target)) {
            TextComponent msg = new TextComponent("This player is already invited to your party!");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        party.invitePlayer(target);
        TextComponent msg = new TextComponent("Invited player " + targetName + "!");
        msg.setColor(ChatColor.AQUA);
        player.sendMessage(Constants.TAG, msg);
    }

    @Override
    public void runConsole() {

    }
}
