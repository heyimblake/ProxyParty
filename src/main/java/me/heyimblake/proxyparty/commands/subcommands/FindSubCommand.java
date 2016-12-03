package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
@PartySubCommandExecutor(subCommand = "find",
        syntax = "/party find <Player>",
        description = "Finds a party participant.",
        requiresArgumentCompletion = true,
        leaderExclusive = false,
        mustBeInParty = true)
public class FindSubCommand extends AnnotatedPartySubCommand {

    public FindSubCommand(PartySubCommandHandler handler) {
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
        if (PartyManager.getInstance().getPartyOf(target).getLeader().getUniqueId() != PartyManager.getInstance().getPartyOf(player).getLeader().getUniqueId()) {
            TextComponent msg = new TextComponent("That player isn't a member of your party!");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        ServerInfo serverInfo = target.getServer().getInfo();
        TextComponent msg = new TextComponent(target.getName() + " is playing on " + serverInfo.getName() + ".");
        msg.setColor(ChatColor.AQUA);
        player.sendMessage(Constants.TAG, msg);

        new ActionLogEntry("find", player.getUniqueId(), new String[]{target.getName()}).log();
    }

    @Override
    public void runConsole() {

    }
}
