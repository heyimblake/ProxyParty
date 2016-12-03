package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.PartySetting;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 11/28/2016
 */
@PartySubCommandExecutor(subCommand = "toggle",
        syntax = "/party toggle <Setting Name>",
        description = "Toggles a given setting on or off.",
        requiresArgumentCompletion = true,
        leaderExclusive = false,
        mustBeInParty = false)
public class ToggleSubCommand extends AnnotatedPartySubCommand {

    public ToggleSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = (ProxiedPlayer) getHandler().getCommandSender();
        String settingString = getHandler().getArguments()[0];
        PartySetting partySetting = PartySetting.getPartySetting(settingString);
        if (partySetting == null) {
            TextComponent msg = new TextComponent("Sorry, that setting couldn't be found. Here's a list of valid settings: ");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            sendAllSettings(player);
            return;
        }
        partySetting.toggle(player);
        TextComponent msg = new TextComponent(partySetting.getNiceName() + " has been");
        msg.setColor(ChatColor.YELLOW);
        TextComponent msg2 = new TextComponent(partySetting.isEnabledFor(player) ? " enabled." : " disabled.");
        msg2.setColor(ChatColor.AQUA);
        player.sendMessage(Constants.TAG, msg, msg2);
        new ActionLogEntry("toggle", player.getUniqueId(), new String[]{settingString}).log();
    }

    @Override
    public void runConsole() {

    }

    private void sendAllSettings(ProxiedPlayer player) {
        String str = "";
        for (PartySetting partySetting : PartySetting.values()) {
            str += partySetting.getArgumentString() + ", ";
        }
        TextComponent msg = new TextComponent(str);
        msg.setColor(ChatColor.WHITE);
        player.sendMessage(Constants.TAG, msg);
    }
}
