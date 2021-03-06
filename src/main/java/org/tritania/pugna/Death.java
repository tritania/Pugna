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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.List;

import org.bukkit.permissions.PermissibleBase;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.block.BlockFace;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.*;
import org.tritania.pugna.wrappers.*;

public class Death
{
    private HashMap<String, DeathChest> deathlocations = new HashMap<String, DeathChest>();
    private HashMap<String, DeathChest> deathlocationspost = new HashMap<String, DeathChest>();

    public Pugna pg;

    public Death(Pugna pg)
    {
        this.pg = pg;
        if (pg.config.deathChest)
        {
            pg.getServer().getScheduler().runTaskLater(pg, new Runnable()
            {
                public void run()
                {
                    destroyAll();
                }
            }, pg.config.deathChestTime);
        }
    }

    public void destroyAll()
    {
        Iterator<Map.Entry<String, DeathChest>> iterator = deathlocationspost.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, DeathChest> entry = iterator.next();
            String[] ld = entry.getKey().split(",");
            Location location = new Location(Bukkit.getWorld(ld[0]),Double.parseDouble(ld[1]),Double.parseDouble(ld[2]),Double.parseDouble(ld[3]));
            Block toDestroy = location.getBlock();
            if(toDestroy.getType().equals(Material.CHEST))
            {
                Chest drops = (Chest) toDestroy.getState();
                drops.getInventory().clear();
                toDestroy.setType(Material.AIR);
                toDestroy.setType(Material.AIR);
            }
            iterator.remove();
        }
    }

    public void removeChest(UUID playerId, Location local)
    {
        String location = local.getWorld().getName() + "," + String.valueOf( local.getBlockX()) + "," + String.valueOf( local.getBlockY()) + "," + String.valueOf(local.getBlockZ());
        Iterator<Map.Entry<String, DeathChest>> iterator = deathlocations.entrySet().iterator();

        while(iterator.hasNext())
        {
            Map.Entry<String, DeathChest> entry = iterator.next();
            DeathChest chest = entry.getValue();
            String loc = entry.getKey();

            if (chest.checkOwner(playerId) && loc.equals(location))
            {
                Block toDestroy = local.getBlock();
                if(toDestroy.getType().equals(Material.CHEST))
                {
                    Chest drops = (Chest) toDestroy.getState();
                    drops.getInventory().clear();
                    toDestroy.setType(Material.AIR);
                }
                iterator.remove();
            }
        }
    }

    public void removePlayerChests(UUID playerID)
    {
        Iterator<Map.Entry<String, DeathChest>> iterator = deathlocations.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, DeathChest> entry = iterator.next();
            DeathChest chest = entry.getValue();
            if (chest.checkOwner(playerID))
            {
                String[] ld = entry.getKey().split(",");
                Location location = new Location(Bukkit.getWorld(ld[0]),Double.parseDouble(ld[1]),Double.parseDouble(ld[2]),Double.parseDouble(ld[3]));
                removeChest(playerID, location);
            }
        }
    }

    public void changeOwnership(UUID current, Player postplayer)
    {
        UUID post = postplayer.getUniqueId();
        Iterator<Map.Entry<String, DeathChest>> iterator = deathlocations.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, DeathChest> entry = iterator.next();
            DeathChest chest = entry.getValue();
            if (chest.checkOwner(current))
            {
                chest.addOther(post);
                deathlocations.put(entry.getKey(), chest);
            }
        }
    }

    public void createDeathChest(Player player, List<ItemStack> drops)
    {
        if (pg.config.deathChest)
        {
            if(pg.config.global || player.getLocation().getWorld().toString().equals(pg.config.mapName))
            {
                Location death = checkLocation(player.getLocation());
                Block chest = death.getBlock();
                chest.setType(Material.CHEST);
                Chest chestp = (Chest) death.getBlock().getState();
                for (ItemStack tmp : drops)
                {
                    chestp.getInventory().addItem(tmp);
                }
                UUID playerId = player.getUniqueId();
                String local = death.getWorld().getName() + "," + String.valueOf( death.getBlockX()) + "," + String.valueOf( death.getBlockY()) + "," + String.valueOf(death.getBlockZ());
                DeathChest box = new DeathChest(playerId);
                deathlocations.put(local, box);

                Message.info(player, "You have " + String.valueOf(pg.config.deathChestTime/20) + " seconds to retrive your items, good luck!"); 

                deathChestTimer(player, death);
            }
        }
    }

    public void deathChestTimer(final Player player, final Location location)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                UUID playID = player.getUniqueId();
                removeChest(playID, location);
            }
        }.runTaskLater(pg, pg.config.deathChestTime);
    }



    public boolean checkPlayer(Location location, Player player)
    {
        String local = location.getWorld().getName() + "," + String.valueOf( location.getBlockX()) + "," + String.valueOf( location.getBlockY()) + "," + String.valueOf(location.getBlockZ());
        String match = null;
        UUID playerId = player.getUniqueId();
        if(deathlocations.containsKey(local))
        {
            DeathChest chest = deathlocations.get(local);
            if (chest.checkOwner(playerId) || chest.checkForAcess(playerId))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (player.hasPermission("pugna.chestoveride"))
        {
            return true;
        }
        return true;
    }

    public boolean checkBlock(Block block)
    {
        Location location = block.getLocation();
        String local = location.getWorld().getName() + "," + String.valueOf( location.getBlockX()) + "," + String.valueOf( location.getBlockY()) + "," + String.valueOf(location.getBlockZ());
        if(deathlocations.containsKey(local))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Location checkLocation(Location location)
    {
        boolean safe = true;
        Block chest = location.getBlock();
        while (safe)
        {
            Block north = chest.getRelative(BlockFace.NORTH, 1);
            Block south = chest.getRelative(BlockFace.SOUTH, 1);
            Block west  = chest.getRelative(BlockFace.WEST, 1);
            Block east  = chest.getRelative(BlockFace.EAST, 1);
            if (chest.getType().equals(Material.CHEST) || north.getType().equals(Material.CHEST) || south.getType().equals(Material.CHEST) || west.getType().equals(Material.CHEST) || east.getType().equals(Material.CHEST) )
            {
                chest = chest.getRelative(BlockFace.UP, 1);
            }
            else
            {
                safe = false;
            }
        }
        return chest.getLocation();
    }

    public void loadDeathChests()
    {
        deathlocationspost = pg.storage.loadData("deathChests.data");
    }

    public void offloadDeathChests()
    {
        pg.storage.saveData(deathlocations, "deathChests.data");
    }

}
