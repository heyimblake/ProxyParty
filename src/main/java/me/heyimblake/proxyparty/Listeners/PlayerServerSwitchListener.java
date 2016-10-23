package me.heyimblake.proxyparty.Listeners;

import me.heyimblake.proxyparty.PartyUtils.Party;
import me.heyimblake.proxyparty.PartyUtils.PartyManager;
import me.heyimblake.proxyparty.PartyUtils.PartyRole;
import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
public class PlayerServerSwitchListener implements Listener {
    @EventHandler
    public void onPlayerServerSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (!PartyManager.getInstance().hasParty(player))
            return;
        Party party = PartyManager.getInstance().getPartyOf(player);
        if (PartyRole.getRoleOf(player) == PartyRole.LEADER) {
            TextComponent msg = new TextComponent("Sending you to your party leader's server!");
            msg.setColor(ChatColor.AQUA);
            party.getParticipants().forEach(participant -> participant.sendMessage(Constants.TAG, msg));
            party.warpParticipants(player.getServer().getInfo());
            TextComponent msg1 = new TextComponent("Attempting to send all party members to your server.");
            msg1.setColor(ChatColor.DARK_AQUA);
            party.getLeader().sendMessage(Constants.TAG, msg1);
            return;
        }

        ServerInfo hub = ProxyParty.getInstance().getProxy().getServerInfo("HUB");
        player.connect(hub);

        TextComponent msg = new TextComponent("Only party leaders can join servers whilst in a party!");
        msg.setColor(ChatColor.RED);
        msg.setBold(true);

        player.sendMessage(Constants.TAG, msg);
    }
}
