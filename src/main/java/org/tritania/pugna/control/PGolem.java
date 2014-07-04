/*
 * Copyright 2014 Erik Wilson <erikwilson@magnorum.com>
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
 */
package org.tritania.pugna.control;

import org.bukkit.entity.Entity;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.*;
import org.tritania.pugna.wrappers.*;

public class PGolem
{
    public Pugna pg;

    private String teamName;
    private String playerName;

    public PGolem(Pugna pg)
    {
        this.pg = pg;
    }

    public void setTeam(String teamName)
    {
        this.teamName = teamName;
    }

    public void setPlayer(String playerName)
    {
        this.playerName = playerName;
    }

    public void getPlayer(PugnaPlayer player, PugnaTeam team)
    {

    }

    public void getPlayer(PugnaPlayer player)
    {

    }
}

