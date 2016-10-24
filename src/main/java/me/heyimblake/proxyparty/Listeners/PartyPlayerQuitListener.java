package me.heyimblake.proxyparty.Listeners;

import me.heyimblake.proxyparty.Events.PartyPlayerQuitEvent;
import me.heyimblake.proxyparty.PartyUtils.Party;
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
public class PartyPlayerQuitListener implements Listener {
    @EventHandler
    public void onPartyPlayerQuit(PartyPlayerQuitEvent event) {
        Party party = event.getParty();
        ProxiedPlayer quitter = event.getWhoQuit();
        TextComponent ex = new TextComponent("" + '\u2716' + " ");
        ex.setColor(ChatColor.RED);
        party.getParticipants().forEach(participant -> participant.sendMessage(Constants.TAG, ex, new TextComponent(quitter.getName())));
        party.getLeader().sendMessage(Constants.TAG, ex, new TextComponent(quitter.getName()));
        ProxyParty.getInstance().getLogger().log(Level.INFO, quitter.getName() + " left " + party.getLeader() + "'s party.");
    }
}
