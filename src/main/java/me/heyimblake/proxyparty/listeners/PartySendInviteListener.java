package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartySendInviteEvent;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

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
 * @since 10/23/2016
 */
public class PartySendInviteListener implements Listener {
    @EventHandler
    public void onPartySendInvite(PartySendInviteEvent event) {
        ProxiedPlayer player = event.getInvited();
        ProxiedPlayer inviter = event.getInviter();

        new ActionLogEntry("invite", inviter.getUniqueId(), new String[]{player.getName()}).log();

        BaseComponent[] clickMessages = new ComponentBuilder("ACCEPT")
                .color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + inviter.getName()))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.GRAY + "Click to accept this invite!")}))
                .append(" or ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.GRAY)
                .append("DENY").color(ChatColor.RED).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny " + inviter.getName()))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.GRAY + "Click to deny this invite!")})).create();

        player.sendMessage(new TextComponent(" "));
        player.sendMessage(new ComponentBuilder("You received a Party Invite!").color(ChatColor.LIGHT_PURPLE).bold(true).create()[0]);
        player.sendMessage(new ComponentBuilder(String.format("%s invited you to their party! Do you want to join it?", inviter.getName())).color(ChatColor.YELLOW).create()[0]);
        player.sendMessage(new TextComponent(" "));
        player.sendMessage(clickMessages[0], clickMessages[1], clickMessages[2]);
        player.sendMessage(new TextComponent(" "));
    }
}
