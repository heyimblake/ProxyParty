package me.heyimblake.proxyparty.commands.subcommands;

import me.heyimblake.proxyparty.commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
@PartySubCommandExecutor(subCommand = "invited",
        syntax = "/party invited",
        description = "Shows a list of players invited to your party.",
        requiresArgumentCompletion = false,
        leaderExclusive = false,
        mustBeInParty = true)
public class InvitedSubCommand extends AnnotatedPartySubCommand {

    public InvitedSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        Party party = PartyManager.getInstance().getPartyOf(player);
        if (party.getInvited().size() != 0) {
            List<BaseComponent> names = new ArrayList<>();
            for (ProxiedPlayer invited : party.getInvited()) {
                TextComponent textComponent = new TextComponent(invited.getName());
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party retract " + invited.getName()));
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.YELLOW + "Click to retract invite of " + ChatColor.AQUA + invited.getName() + ".")}));
                textComponent.setColor(ChatColor.DARK_AQUA);
                names.add(textComponent);
            }
            TextComponent msg = new TextComponent("These players have invitations to your party:");
            msg.setColor(ChatColor.AQUA);
            TextComponent retractMSG = new TextComponent("Click on a name above to retract their invitation.");
            retractMSG.setColor(ChatColor.GRAY);
            player.sendMessage(Constants.TAG, msg);
            names.forEach(name -> player.sendMessage(Constants.TAG, name));
            player.sendMessage(Constants.TAG, retractMSG);
            return;
        }
        TextComponent msg = new TextComponent("You do not have any outgoing invitations.");
        msg.setColor(ChatColor.RED);
        player.sendMessage(Constants.TAG, msg);
    }

    @Override
    public void runConsole() {

    }
}
