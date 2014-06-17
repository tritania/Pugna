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

package org.tritania.pugna.listener;

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
import org.bukkit.event.entity.ProjectileHitEvent;
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
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.event.inventory.InventoryAction;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.wrappers.*;
import org.tritania.pugna.util.Log;
import org.tritania.pugna.util.Message;


public class PlayerListener implements Listener
{
    private Pugna pg;

    public PlayerListener(Pugna pg)
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
       Player player = event.getPlayer();
       pg.bt.checkOutstanding(player);
       pg.track.startTracking(player);
       PugnaPlayer play = pg.track.getPlayerData(player);
       if (play.getTeamState()) //NPE
       {
           PugnaTeam team = pg.teams.getTeam(play.getTeam());
           team.setOnline();
       }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent event)
    {
       Player player = event.getPlayer();
       pg.storage.savePlayer(player);
       PugnaPlayer play = pg.track.getPlayerData(player);
       if (play.getTeamState())
       {
           PugnaTeam team = pg.teams.getTeam(play.getTeam());
           team.setOffline();
       }
       pg.track.stopTracking(player);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        List<ItemStack> drops = event.getDrops();
        Player player = (Player) event.getEntity();
        pg.dt.createDeathChest(player, drops);
        pg.bt.checkOutstanding(player);
        event.getDrops().clear();

        Location location = player.getLocation();
        World world = location.getWorld();
        String pName = player.getName();

        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> itemDesc = new ArrayList<String>();
        itemMeta.setDisplayName("Head of " + pName);
        itemDesc.add(event.getDeathMessage());
        itemMeta.setLore(itemDesc);
        ((SkullMeta) itemMeta).setOwner(pName);
        item.setItemMeta(itemMeta);
        world.dropItemNaturally(location, item);
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

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(!event.hasBlock())
        {

        }
        else
        {
            Block block = event.getClickedBlock();
            if(block.getType().equals(Material.CHEST))
            {
                if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
                {
                    Location location = block.getLocation();
                    if (pg.dt.checkPlayer(location, player))
                    {

                    }
                    else
                    {
                        event.setCancelled(true);
                        CommandSender pc = (CommandSender) player;
                        Message.info(pc, "Stop trying to steal stuff!");
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void projectileHit(ProjectileHitEvent event)
    {
        if (event.getEntity() instanceof Arrow)
        {
            Entity arrow = (Arrow) event.getEntity();
            //Player player = (Player) event.getEntity().getShooter();
            Block block = arrow.getLocation().getBlock();
            int flame = arrow.getFireTicks();
            if (flame > 0)
            {
                block.setType(Material.FIRE);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDmg(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            if (event.getDamager() instanceof Monster)
            {
                event.setDamage(event.getDamage() + 6);
            }
            else if (event.getDamager() instanceof Arrow)
            {

            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(PlayerChatEvent event)
    {
        Player player = event.getPlayer();
        PugnaPlayer play = pg.track.getPlayerData(player);

        if (play.getChatState() && play.getTeamState())
        {
            event.setCancelled(true);
            String teamName = play.getTeam();
            PugnaTeam team = pg.teams.getTeam(teamName); //
            team.sendMessage(player, " " + event.getMessage());
        }
        else if (player.getPlayerListName().equals(pg.emperor.getEmperorName()))
        {
            event.setFormat("[EMPEROR] %s : %s");
        }
    }
}
