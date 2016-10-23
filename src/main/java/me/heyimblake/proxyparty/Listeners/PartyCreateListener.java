package me.heyimblake.proxyparty.Listeners;

import me.heyimblake.proxyparty.Events.PartyCreateEvent;
import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
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
public class PartyCreateListener implements Listener {
    @EventHandler
    public void onPartyCreate(PartyCreateEvent event) {
        ProxiedPlayer player = event.getCreator();
        TextComponent msg = new TextComponent("You just created a party!");
        msg.setColor(ChatColor.YELLOW);
        player.sendMessage(Constants.TAG, msg);
        ProxyParty.getInstance().getLogger().log(Level.INFO, player.getName() + " just created a new party.");
    }
}
