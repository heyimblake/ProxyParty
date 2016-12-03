package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyDisbandEvent;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by heyimblake on 10/24/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public class PartyDisbandListener implements Listener {
    @EventHandler
    public void onPartyDisband(PartyDisbandEvent event) {
        Party party = event.getParty();
        party.sendMessage(party.getLeader() + " has disbanded the party!", ChatColor.YELLOW);
        new ActionLogEntry("disband", event.getParty().getLeader().getUniqueId()).log();
    }
}
