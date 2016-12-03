package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
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
        party.warpParticipants(player.getServer().getInfo());
        new ActionLogEntry("warp", player.getUniqueId()).log();
    }

    @Override
    public void runConsole() {

    }
}
