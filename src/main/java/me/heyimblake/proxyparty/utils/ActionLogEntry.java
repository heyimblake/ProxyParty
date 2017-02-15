package me.heyimblake.proxyparty.utils;

import com.google.gson.GsonBuilder;
import me.heyimblake.proxyparty.ProxyParty;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
 * @since 11/29/2016
 */
public class ActionLogEntry {

    public static List<ActionLogEntry> savedEntries = new ArrayList<>();

    private String action;
    private String[] arguments;
    private UUID senderUUID;
    private long timeMillis;

    public ActionLogEntry(String action, UUID senderUUID, String[] arguments) {
        this.action = action;
        this.arguments = arguments;
        this.senderUUID = senderUUID;
        this.timeMillis = System.currentTimeMillis();
    }

    public ActionLogEntry(String action, UUID senderUUID) {
        this.action = action;
        this.senderUUID = senderUUID;
        this.arguments = new String[]{};
        this.timeMillis = System.currentTimeMillis();
    }

    public String getAction() {
        return action;
    }

    public String[] getArguments() {
        return arguments;
    }

    public UUID getSenderUUID() {
        return senderUUID;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    private String toJson() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public void log() {
        if (!ProxyParty.getInstance().isLoggingEnabled())
            return;
        savedEntries.add(this);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(ProxyParty.getInstance().getLogFile());
            fileOutputStream.write(new GsonBuilder().setPrettyPrinting().create().toJson(savedEntries).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
