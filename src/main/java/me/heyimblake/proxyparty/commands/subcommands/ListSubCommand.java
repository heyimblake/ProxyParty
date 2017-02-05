package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
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
        line1.setBold(true);
        line1.addExtra(new TextComponent(party.getLeader().getName()));

        if (party.getParticipants().size() != 0) {
            TextComponent line2 = new TextComponent("Participants: ");
            line2.setColor(ChatColor.AQUA);
            if (Constants.MAX_PARTY_SIZE != -1) {
                TextComponent count = new TextComponent(" (" + party.getParticipants().size() + "/" + Constants.MAX_PARTY_SIZE + ")");
                count.setColor(ChatColor.DARK_AQUA);
                line2.addExtra(count);
            }
            String allParticipants = "";
            for (ProxiedPlayer participant : party.getParticipants()) {
                allParticipants = allParticipants + participant.getName() + ", ";
            }
            player.sendMessage(Constants.TAG, line1);
            player.sendMessage(Constants.TAG, line2);
            player.sendMessage(Constants.TAG, new TextComponent(allParticipants));
        } else {
            TextComponent line2 = new TextComponent("There are no participants in this party.");
            line2.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, line1);
            player.sendMessage(Constants.TAG, line2);
        }

        new ActionLogEntry("list", player.getUniqueId()).log();
    }

    @Override
    public void runConsole() {

    }
}
