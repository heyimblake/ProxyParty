package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartyRole;
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
@PartySubCommandExecutor(subCommand = "leave",
        syntax = "/party leave",
        description = "Leave your current party.",
        requiresArgumentCompletion = false,
        leaderExclusive = false,
        mustBeInParty = true)
public class LeaveSubCommand extends AnnotatedPartySubCommand {

    public LeaveSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = (ProxiedPlayer) getHandler().getCommandSender();
        Party party = PartyManager.getInstance().getPartyOf(player);
        if (PartyRole.getRoleOf(player) == PartyRole.LEADER) {
            TextComponent msg = new TextComponent("You're the leader! Either promote another member to be leader and leave or disband the party.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        party.removeParticipant(player);
        TextComponent msg = new TextComponent("You left the party.");
        msg.setColor(ChatColor.YELLOW);
        player.sendMessage(Constants.TAG, msg);
    }

    @Override
    public void runConsole() {

    }
}
