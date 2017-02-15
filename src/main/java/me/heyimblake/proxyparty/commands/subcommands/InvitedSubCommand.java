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
 * Copyright (C) 2017 heyimblake
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author heyimblake
 * @since 10/21/2016
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
