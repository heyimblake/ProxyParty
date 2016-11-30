package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyPlayerJoinEvent;
import me.heyimblake.proxyparty.partyutils.Party;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by heyimblake on 10/23/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
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
        new ActionLogEntry("accept", joined.getUniqueId(), new String[]{party.getLeader().getName()}).log();
    }
}
