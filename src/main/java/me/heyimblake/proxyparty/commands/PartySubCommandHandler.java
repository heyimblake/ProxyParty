package me.heyimblake.proxyparty.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
 * @since 10/22/2016
 */
public class PartySubCommandHandler {
    private CommandSender commandSender;
    private String[] arguments;

    public PartySubCommandHandler(CommandSender commandSender, String[] arguments) {
        this.commandSender = commandSender;
        this.arguments = arguments;
    }

    public CommandSender getCommandSender() {
        return this.commandSender;
    }

    public String[] getArguments() {
        return this.arguments;
    }

    public boolean isSenderProxiedPlayer() {
        return getCommandSender() instanceof ProxiedPlayer;
    }
}
