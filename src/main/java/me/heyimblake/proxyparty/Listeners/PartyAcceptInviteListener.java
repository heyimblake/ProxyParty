package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyAcceptInviteEvent;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by heyimblake on 11/3/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public class PartyAcceptInviteListener implements Listener {
    @EventHandler
    public void onPartyAcceptInvite(PartyAcceptInviteEvent event) {
        Party party = event.getParty();
        ProxiedPlayer accepter = event.getAccepter();
        TextComponent msg = new TextComponent("Player " + accepter.getName() + " has accepted your party invitation.");
        msg.setColor(ChatColor.AQUA);
        party.getLeader().sendMessage(Constants.TAG, msg);
    }
}
