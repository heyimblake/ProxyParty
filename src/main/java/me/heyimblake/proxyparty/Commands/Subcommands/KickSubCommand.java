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
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
@PartySubCommandExecutor(subCommand = "kick",
        syntax = "/party kick <Player>",
        description = "Remove a player from the party.",
        requiresArgumentCompletion = true,
        leaderExclusive = true,
        mustBeInParty = true)
public class KickSubCommand extends AnnotatedPartySubCommand {

    public KickSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        ProxiedPlayer target = ProxyParty.getInstance().getProxy().getPlayer(getHandler().getArguments()[0]);
        Party party = PartyManager.getInstance().getPartyOf(player);
        if (target == null) {
            TextComponent msg = new TextComponent("The specified player could not be found.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        Party targetParty = PartyManager.getInstance().getPartyOf(target);
        if (targetParty == null || targetParty != party) {
            TextComponent msg = new TextComponent("That player isn't in your party!");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        if (party.getLeader().getUniqueId() == target.getUniqueId() || target.getUniqueId() == player.getUniqueId()) {
            TextComponent msg = new TextComponent("You can't kick yourself or the Party Leader!");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        party.removeParticipant(target);
        TextComponent msg = new TextComponent("You kicked " + target.getName() + " out of the party!");
        msg.setColor(ChatColor.YELLOW);
        player.sendMessage(Constants.TAG, msg);
    }

    @Override
    public void runConsole() {

    }
}
