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
package org.tritania.pugna;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Material;
import org.bukkit.entity.CreatureType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.*;

public class MobLevel 
{
	public Pugna pg;

    public MobLevel(Pugna pg)
    {
        this.pg = pg;
    }
    
	public boolean alter(Entity mob, CreatureType type)
	{
		if (pg.config.diffmob != true)
			return true;
		if (!(mob instanceof Monster))
			return true;
		if (type == null)
			return true;
		
		Damageable mob2 = (Damageable) mob;
		
		mob2.setMaxHealth(150);
		mob2.setHealth(150);
		
		switch(type)
		{
			case CREEPER:
			{
				break;
			}
			case SKELETON:
			{
				break;
			}
			case SPIDER:
			{
				break;
			}
			case ZOMBIE:
			{
				break;
			}
			case SLIME:
			{
				break;
			}
			case ENDERMAN:
			{
				break;
			}
			case CAVE_SPIDER:
			{
				break;
			}
			case SILVERFISH:
			{
				break;
			}
			default:
			{
				
			}
		}
		
		return true;
	} 
}
