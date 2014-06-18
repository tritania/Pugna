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

package org.tritania.pugna.wrappers;

import java.util.UUID;
import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class PugnaScore implements Serializable
{
    private int kills;
    private int deaths;
    private int blocks;
    private int score;
    private String lastKill;
    private String lastDeath;

    public PugnaScore()
    {
        this.kills = 0;
        this.deaths = 0;
        this.blocks = 0;
        this.score = 0;
    }

   public int getKills()
   {
       return kills;
   }

   public int getDeaths()
   {
       return deaths;
   }

   public int getTotalScore()
   {
       return score;
   }

   public void addKill()
   {
       kills++;
   }

   public void addDeath()
   {
       deaths++;
   }

   public void addBlocks()
   {
       blocks++;
   }

   public void changeScore(int value)
   {
       score = score + value;
   }

   public String getLastKill()
   {
       return lastKill;
   }

   public String getLastDeath()
   {
       return lastDeath;
   }

   public void setLastKill(String name)
   {
       lastKill = name;
   }

   public void setLastDeath(String name)
   {
       lastDeath = name; //might build out in the future to allow more reasoning
   }
}

