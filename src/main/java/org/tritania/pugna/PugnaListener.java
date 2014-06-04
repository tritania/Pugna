/*
 * Copyright 2012-2014 Erik Wilson <erikwilson@magnorum.com>
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

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Material;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.Log;
import org.tritania.pugna.util.Message;

public class PugnaListener implements Listener
{
	private Pugna pg;

	public PugnaListener(Pugna pg)
	{
		this.pg = pg;
	}
	
	public void register()
	{
		PluginManager manager;
		
		manager = pg.getServer().getPluginManager();
		manager.registerEvents(this, pg);
	} 
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
       
	}
    
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent event)
    {
       
    }
    
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event)
    {
		List<ItemStack> drops = event.getDrops();
		Player player = (Player) event.getEntity();
		pg.dt.createDeathChest(player, drops);
		event.getDrops().clear();
    }
    
	@EventHandler(priority = EventPriority.NORMAL) //going to need other listeners to detect the other player
	public void dmg(EntityDamageEvent event) 
	{
		Entity e = event.getEntity();
		if (e instanceof Player) 
		{
			Player player = (Player) e;
			pg.com.combatStart(player);
		}
	}
}
