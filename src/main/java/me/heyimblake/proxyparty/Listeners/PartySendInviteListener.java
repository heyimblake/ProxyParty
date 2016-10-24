package me.heyimblake.proxyparty.Listeners;

import me.heyimblake.proxyparty.Events.PartySendInviteEvent;
import me.heyimblake.proxyparty.ProxyParty;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Level;

/**
 * Created by heyimblake on 10/23/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartySendInviteListener implements Listener {
    @EventHandler
    public void onPartySendInvite(PartySendInviteEvent event) {
        ProxiedPlayer player = event.getInvited();
        ProxiedPlayer inviter = event.getInviter();

        ProxyParty.getInstance().getLogger().log(Level.INFO, player.getName() + " was invited to " + inviter.getName() + "'s party.");

        TextComponent pt1 = new TextComponent("You received a Party Invite!");
        pt1.setColor(ChatColor.LIGHT_PURPLE);
        pt1.setBold(true);

        TextComponent pt2 = new TextComponent(inviter.getName() + " invited you to their party! Do you want to join it?");
        pt2.setColor(ChatColor.AQUA);

        TextComponent accept = new TextComponent("ACCEPT");
        accept.setColor(ChatColor.GREEN);
        accept.setBold(true);
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + inviter.getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.GRAY + "Click to accept this invite!")}));

        TextComponent deny = new TextComponent("DECLINE");
        deny.setColor(ChatColor.RED);
        deny.setBold(true);
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny " + inviter.getName()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.GRAY + "Click to deny this invite!")}));

        TextComponent pt3 = new TextComponent(" or ");
        pt3.setColor(ChatColor.GRAY);

        player.sendMessage(new TextComponent(" "));
        player.sendMessage(pt1);
        player.sendMessage(pt2);
        player.sendMessage(new TextComponent(" "));
        player.sendMessage(accept, pt3, deny);
        player.sendMessage(new TextComponent(" "));
    }
}
