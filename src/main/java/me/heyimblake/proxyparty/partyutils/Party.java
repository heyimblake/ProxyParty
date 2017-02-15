package me.heyimblake.proxyparty.partyutils;

import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.events.*;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (C) 2017 heyimblake
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author heyimblake
 * @since 10/21/2016
 */
public class Party {
    private ProxiedPlayer leader = null;
    private Set<ProxiedPlayer> participants, invited;

    /**
     * Creates a party with a leader and no participants.
     *
     * @param leader the party leader
     */
    protected Party(ProxiedPlayer leader) {
        this.leader = leader;
        this.participants = new HashSet<>();
        this.invited = new HashSet<>();
        PartyRole.setRoleOf(leader, PartyRole.LEADER);
        PartyManager.getInstance().getActiveParties().add(this);
        PartyManager.getInstance().getPlayerPartyMap().put(this.leader, this);
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyCreateEvent(this));
    }

    /**
     * Creates a party with a leader and a set of participants.
     *
     * @param leader       the party leader
     * @param participants the set of participants
     */
    protected Party(ProxiedPlayer leader, Set<ProxiedPlayer> participants) {
        this.leader = leader;
        this.participants = participants;
        this.invited = new HashSet<>();
        PartyRole.setRoleOf(leader, PartyRole.LEADER);
        this.participants.forEach(participant -> PartyRole.setRoleOf(participant, PartyRole.PARTICIPANT));
        this.participants.forEach(participant -> PartyManager.getInstance().getPlayerPartyMap().put(participant, this));
        PartyManager.getInstance().getPlayerPartyMap().put(this.leader, this);
        PartyManager.getInstance().getActiveParties().add(this);
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyCreateEvent(this));
    }

    /**
     * Gets the leader of the instance of the party.
     *
     * @return the party leader.
     */
    public ProxiedPlayer getLeader() {
        return this.leader;
    }

    /**
     * Replaces the party leader with another proxied player.
     *
     * @param player the new party leader
     */
    public void setLeader(ProxiedPlayer player) {
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyPromoteEvent(this, player, this.leader));
        if (this.leader != null) {
            PartyRole.setRoleOf(this.leader, PartyRole.PARTICIPANT);
            this.participants.add(this.leader);
        }
        this.leader = player;
        PartyRole.setRoleOf(player, PartyRole.LEADER);
        if (this.participants.contains(player))
            this.participants.remove(player);
        PartyManager.getInstance().getPlayerPartyMap().remove(player);
        PartyManager.getInstance().getPlayerPartyMap().put(player, this);
    }

    /**
     * Gets the set of participants.
     *
     * @return the set of participants of the party
     */
    public Set<ProxiedPlayer> getParticipants() {
        return this.participants;
    }

    /**
     * Gets the currently invited set of players.
     *
     * @return set of invited players
     */
    public Set<ProxiedPlayer> getInvited() {
        return this.invited;
    }

    /**
     * Removes a player from the current party instance.
     *
     * @param player the player to be removed
     */
    public void removeParticipant(ProxiedPlayer player) {
        this.participants.remove(player);
        PartyManager.getInstance().getPlayerPartyMap().remove(player);
        PartyRole.removeRoleFrom(player);
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyPlayerQuitEvent(this, player));
    }

    /**
     * Adds a player to the current party instance.
     *
     * @param player the player to be added
     */
    public void addParticipant(ProxiedPlayer player) {
        this.participants.add(player);
        PartyManager.getInstance().getPlayerPartyMap().put(player, this);
        PartyRole.setRoleOf(player, PartyRole.PARTICIPANT);
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyPlayerJoinEvent(this, player));
    }

    /**
     * Invites a player to the current party instance.
     *
     * @param player the player to be invited
     */
    public void invitePlayer(ProxiedPlayer player) {
        this.invited.add(player);
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartySendInviteEvent(this, player));
    }

    /**
     * Removes an invitation from a player invited to the current party instance.
     *
     * @param player the player to retract the invitation from
     */
    public void retractInvite(ProxiedPlayer player) {
        this.invited.remove(player);
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyRetractInviteEvent(this, player));
    }

    /**
     * Send all party participants to a specified server.
     *
     * @param serverInfo the server to send the participants to
     */
    public void warpParticipants(ServerInfo serverInfo) {
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyWarpEvent(this));
        this.participants.forEach(participant -> participant.connect(serverInfo));
    }

    /**
     * Sends a chat message from a player to the party chat.
     *
     * @param player the player sending the message
     * @param string the message to be sent
     */
    public void sendMessage(ProxiedPlayer player, String string) {
        TextComponent name = new TextComponent(player.getName() + ": ");
        name.setColor(ChatColor.YELLOW);
        TextComponent message = new TextComponent(string);
        getParticipants().forEach(participant -> participant.sendMessage(Constants.TAG, name, message));
        getLeader().sendMessage(Constants.TAG, name, message);
    }

    /**
     * Sends a message with a specified chat color to all participants and leader of the current party instance.
     *
     * @param string    the message to be sent
     * @param chatColor the color of the message
     */
    public void sendMessage(String string, ChatColor chatColor) {
        TextComponent message = new TextComponent(string);
        message.setColor(chatColor);
        getParticipants().forEach(participant -> participant.sendMessage(Constants.TAG, message));
        getLeader().sendMessage(Constants.TAG, message);
    }

    /**
     * Disbands the current party instance.
     */
    public void disband() {
        ProxyParty.getInstance().getProxy().getPluginManager().callEvent(new PartyDisbandEvent(this));
        this.getParticipants().forEach(participant -> PartyManager.getInstance().getPlayerPartyMap().remove(participant));
        this.getParticipants().forEach(PartyRole::removeRoleFrom);
        PartyRole.removeRoleFrom(this.leader);
        PartyManager.getInstance().getPlayerPartyMap().remove(this.getLeader());
        PartyManager.getInstance().getActiveParties().remove(this);
    }
}
