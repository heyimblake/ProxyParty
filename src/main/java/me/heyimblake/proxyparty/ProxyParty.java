package me.heyimblake.proxyparty;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.heyimblake.proxyparty.commands.PartyCommand;
import me.heyimblake.proxyparty.listeners.*;
import me.heyimblake.proxyparty.utils.ActionLogEntry;
import me.heyimblake.proxyparty.utils.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public final class ProxyParty extends Plugin {
    private static ProxyParty instance;
    private boolean loggingEnabled = true;
    private String logFileName = "log.json";
    private File logFile;
    private ConfigManager configManager;

    public static ProxyParty getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getProxy().getPluginManager().registerCommand(this, new PartyCommand());
        registerListeners();
        setupLogFile();
        configManager = new ConfigManager();
        configManager.initialize();
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
        getProxy().getPluginManager().registerListener(this, new PartyCreateListener());
        getProxy().getPluginManager().registerListener(this, new PartyPlayerJoinListener());
        getProxy().getPluginManager().registerListener(this, new PartyPlayerQuitListener());
        getProxy().getPluginManager().registerListener(this, new PartyDisbandListener());
        getProxy().getPluginManager().registerListener(this, new PartyPromoteListener());
        getProxy().getPluginManager().registerListener(this, new PartyKickListener());
        getProxy().getPluginManager().registerListener(this, new PartyDenyInviteListener());
        getProxy().getPluginManager().registerListener(this, new PartyWarpListener());
        getProxy().getPluginManager().registerListener(this, new PartyRetractInviteListener());
        getProxy().getPluginManager().registerListener(this, new PartyAcceptInviteListener());
    }

    private void setupLogFile() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        logFile = new File(getDataFolder().getPath(), logFileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
                try (InputStream is = getResourceAsStream(logFileName);
                     OutputStream os = new FileOutputStream(logFile)) {
                    ByteStreams.copy(is, os);
                    os.close();
                    is.close();
                    getLogger().log(Level.INFO, logFileName + " was created with no issues!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().log(Level.WARNING, "Could not create " + logFileName + ". Disabling Logging for now!");
                loggingEnabled = false;
            }
            return;
        }
        if (!loggingEnabled)
            return;
        getLogger().log(Level.INFO, "Detected " + logFileName + ".");
        try {
            Gson gson = new Gson();
            List<ActionLogEntry> entires;
            Type type = new TypeToken<List<ActionLogEntry>>() {
            }.getType();
            entires = gson.fromJson(new FileReader(logFile), type);
            entires.forEach(actionLogEntry -> ActionLogEntry.savedEntries.add(actionLogEntry));
            getLogger().log(Level.INFO, "Imported old actions from " + logFileName + ".");
        } catch (FileNotFoundException e) {
            //Not really possible as it's created/verified above, but oh well, here's a catch block!
        }
    }

    public File getLogFile() {
        return logFile;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
