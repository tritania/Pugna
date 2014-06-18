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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.meta.*;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.Log;
import org.tritania.pugna.util.Message;


public class EntityListener implements Listener
{
    private Pugna pg;

    public EntityListener(Pugna pg)
    {
        this.pg = pg;
    }

    public void register()
    {
        PluginManager manager;

        manager = pg.getServer().getPluginManager();
        manager.registerEvents(this, pg);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onCreatureSpawnEvent(CreatureSpawnEvent event)
    {
        Entity mob = event.getEntity();
        if (mob instanceof Monster)
        {
            CreatureType type = event.getCreatureType();
            pg.mb.alter(mob, type);
        }
        if (mob.getType().equals(EntityType.IRON_GOLEM))
        {

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void CreeperExplode(EntityExplodeEvent event)
    {
        if(event.getEntity() != null)
        {
            EntityType e = event.getEntity().getType();
            Location loc = event.getEntity().getLocation();
            World w = loc.getWorld();
            if(e.equals(EntityType.CREEPER))
            {
                event.setCancelled(true);
                event.getEntity().remove();
                w.createExplosion(loc, 25, true);
            }
            else
            {
                Iterator<Block> iter = event.blockList().iterator();
                while (iter.hasNext())
                {
                    Block b = iter.next();
                    if (b.getType().equals(Material.CHEST))
                    {
                        if (pg.dt.checkBlock(b))
                        {
                            iter.remove();
                        }
                    }
                }
            }
        }
        else
        {
            Iterator<Block> iter = event.blockList().iterator();
            while (iter.hasNext())
            {
                Block b = iter.next();
                if (b.getType().equals(Material.CHEST))
                {
                    if (pg.dt.checkBlock(b))
                    {
                        iter.remove();
                    }
                }
            }
        }
    }
}
