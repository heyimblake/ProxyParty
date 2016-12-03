package me.heyimblake.proxyparty.utils;

import com.google.common.io.ByteStreams;
import me.heyimblake.proxyparty.ProxyParty;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 12/3/2016
 */
public class ConfigManager {
    private final String fileName = "config.yml";
    private Configuration configuration = null;

    public void initialize() {
        saveDefaultConfig();
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getConfigFile());
            Constants.TAG = new TextComponent(ChatColor.DARK_GRAY + "" + '\u2502' + " " + getColorizedString("prefix") + ChatColor.DARK_GRAY + '\u2502' + ChatColor.GRAY + " ");
            int max;
            try {
                max = Integer.parseInt(ProxyParty.getInstance().getConfigManager().getString("maxPartySize"));
                if (max <= 0)
                    max = -1;
            } catch (Exception ignored) {
                ProxyParty.getInstance().getLogger().log(Level.WARNING, "Error while trying to get the defined max party size. Set to UNLIMITED now.");
                max = -1;
            }
            Constants.MAX_PARTY_SIZE = max;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDefaultConfig() {
        if (!ProxyParty.getInstance().getDataFolder().exists()) {
            ProxyParty.getInstance().getDataFolder().mkdir();
        }
        File file = getConfigFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (InputStream is = ProxyParty.getInstance().getResourceAsStream(fileName);
                     OutputStream os = new FileOutputStream(file)) {
                    ByteStreams.copy(is, os);
                    os.close();
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public File getConfigFile() {
        return new File(ProxyParty.getInstance().getDataFolder().getPath(), fileName);
    }

    public String getColorizedString(String key) {
        return ChatColor.translateAlternateColorCodes('&', configuration.getString(key));
    }

    public String getString(String key) {
        return configuration.getString(key);
    }
}
