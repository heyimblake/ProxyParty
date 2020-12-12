package me.heyimblake.proxyparty.commands;

import java.lang.annotation.*;

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
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PartyAnnotationCommand {
    /**
     * The name of the subcommand.
     *
     * @return subcommand name
     */
    String name();

    /**
     * The usage of the subcommand.
     *
     * @return subcommand syntax
     */
    String syntax();

    /**
     * Information about the subcommand.
     *
     * @return subcommand description
     */
    String description();

    /**
     * Subcommand requires that the user complete arguments.
     *
     * @return true if requires argument completion
     */
    boolean requiresArgumentCompletion();

    /**
     * Does the subcommand require the command sender to be the party leader
     *
     * @return true if command sender must be the party leader
     */
    boolean leaderExclusive() default false;

    /**
     * Does the subcommand require the command sender to be in a party
     *
     * @return true if sender must be in a party, false otherwise
     */
    boolean mustBeInParty() default true;
}