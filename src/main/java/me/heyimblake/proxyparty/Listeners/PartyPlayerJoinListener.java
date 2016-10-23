package me.heyimblake.proxyparty.Listeners;

import me.heyimblake.proxyparty.Events.PartyPlayerJoinEvent;
import me.heyimblake.proxyparty.PartyUtils.Party;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by heyimblake on 10/23/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PartyPlayerJoinListener implements Listener {
    @EventHandler
    public void onPartyPlayerJoin(PartyPlayerJoinEvent event) {
        Party party = event.getParty();
        ProxiedPlayer joined = event.getWhoJoined();
        TextComponent plus = new TextComponent("" + '\u271A' + " ");
        plus.setColor(ChatColor.GREEN);
        party.getParticipants().forEach(participant -> participant.sendMessage(Constants.TAG, plus, new TextComponent(joined.getName())));
        party.getLeader().sendMessage(Constants.TAG, plus, new TextComponent(joined.getName()));
    }
}
