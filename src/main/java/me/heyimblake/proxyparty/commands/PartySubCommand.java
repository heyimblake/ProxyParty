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
public abstract class PartySubCommand {

    public abstract void execute(ProxiedPlayer player, String[] args);

    /**
     * Gets the Annotation of a AnnotatedPartySubCommand class.
     *
     * @return Annotation if it exists, null if invalid
     */
    public PartyAnnotationCommand getAnnotations() {
        if (this.getClass().isAnnotationPresent(PartyAnnotationCommand.class)) {
            return this.getClass().getAnnotation(PartyAnnotationCommand.class);
        }

        return null;
    }
}