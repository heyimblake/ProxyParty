package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyPromoteEvent;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by heyimblake on 10/24/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public class PartyPromoteListener implements Listener {
    @EventHandler
    public void onPartyPromote(PartyPromoteEvent event) {
        ProxiedPlayer player = event.getPromoted();
        if (event.getPromoter() != null) {
            event.getParty().sendMessage(player.getName() + " was promoted to Party Leader!", ChatColor.YELLOW);
            new ActionLogEntry("promote", event.getPromoter().getUniqueId(), new String[]{event.getPromoted().getName()}).log();
        }
    }
}
