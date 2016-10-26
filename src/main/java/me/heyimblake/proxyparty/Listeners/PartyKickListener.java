package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyKickEvent;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by heyimblake on 10/26/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartyKickListener implements Listener {
    @EventHandler
    public void onPartyKick(PartyKickEvent event) {
        ProxiedPlayer player = event.getKickedPlayer();
        TextComponent msg = new TextComponent("You were removed from the party.");
        msg.setBold(true);
        msg.setColor(ChatColor.RED);
        player.sendMessage(Constants.TAG, msg);
    }
}
