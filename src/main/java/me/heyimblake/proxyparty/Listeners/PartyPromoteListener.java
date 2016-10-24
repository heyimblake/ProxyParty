package me.heyimblake.proxyparty.Listeners;

import me.heyimblake.proxyparty.Events.PartyPromoteEvent;
import me.heyimblake.proxyparty.ProxyParty;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Level;

/**
 * Created by heyimblake on 10/24/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartyPromoteListener implements Listener {
    @EventHandler
    public void onPartyPromote(PartyPromoteEvent event) {
        ProxiedPlayer player = event.getPromoted();
        if (event.getPromoter() != null) {
            event.getParty().sendMessage(player.getName() + " was promoted to Party Leader!", ChatColor.YELLOW);
            ProxyParty.getInstance().getLogger().log(Level.INFO, player.getName() + "was promoted to Party Leader by " + event.getPromoter().getName());
        }
    }
}
