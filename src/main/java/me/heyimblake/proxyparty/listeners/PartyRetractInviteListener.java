package me.heyimblake.proxyparty.listeners;

import me.heyimblake.proxyparty.events.PartyRetractInviteEvent;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
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
 *         https://heyimblake.me
 */
public class PartyRetractInviteListener implements Listener {
    @EventHandler
    public void onPartyRetractInvite(PartyRetractInviteEvent event) {
        ProxiedPlayer retracted = event.getRetracted();
        ProxiedPlayer retractor = event.getRetractor();
        TextComponent msg = new TextComponent(retractor.getName() + " has retracted your invitation to their party.");
        msg.setColor(ChatColor.AQUA);
        retracted.sendMessage(Constants.TAG, msg);
        new ActionLogEntry("retract", retractor.getUniqueId(), new String[]{retracted.getName()}).log();
    }
}
