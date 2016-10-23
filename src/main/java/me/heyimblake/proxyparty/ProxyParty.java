package me.heyimblake.proxyparty;

import me.heyimblake.proxyparty.Commands.PartyCommand;
import me.heyimblake.proxyparty.Listeners.PartySendInviteListener;
import me.heyimblake.proxyparty.Listeners.PlayerChatListener;
import me.heyimblake.proxyparty.Listeners.PlayerQuitListener;
import me.heyimblake.proxyparty.Listeners.PlayerServerSwitchListener;
import net.md_5.bungee.api.plugin.Plugin;


public final class ProxyParty extends Plugin {
    private static ProxyParty instance;

    public static ProxyParty getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getProxy().getPluginManager().registerCommand(this, new PartyCommand());
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new PlayerChatListener());
        getProxy().getPluginManager().registerListener(this, new PlayerQuitListener());
        getProxy().getPluginManager().registerListener(this, new PlayerServerSwitchListener());
        getProxy().getPluginManager().registerListener(this, new PartySendInviteListener());
    }
}
