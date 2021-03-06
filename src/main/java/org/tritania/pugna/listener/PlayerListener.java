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

import org.bukkit.event.player.PlayerEvent; //events
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.entity.CreatureType; //entity
import org.bukkit.entity.Monster;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack; //inventory
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.meta.*;
import org.bukkit.Material; //world
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender; //plugin
import org.bukkit.plugin.PluginManager;

import org.bukkit.ChatColor; //chat

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
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Location location = event.getTo();
        Chunk chunk = location.getChunk();
        PugnaPlayer play = pg.track.getPlayerData(player);
        if (play.getTeamState())
        {
           PugnaTeam team = pg.teams.getTeam(play.getTeam()); //will pass to golem class
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
       Player player = event.getPlayer();
       pg.bt.checkOutstanding(player);
       pg.track.startTracking(player);
       PugnaPlayer play = pg.track.getPlayerData(player);
       if (play.getTeamState())
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

        PugnaPlayer play = pg.track.getPlayerData(player);
        play.setCombatState(false);
        play.getScore().addDeath();

        if(play.getTeamState())
        {
            PugnaTeam team = pg.teams.getTeam(play.getTeam());
            team.getScore().addDeath();
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
        if (pg.config.flame)
        {
            if (event.getEntity() instanceof Arrow)
            {
                Entity arrow = (Arrow) event.getEntity();
                Block block = arrow.getLocation().getBlock();
                int flame = arrow.getFireTicks();
                if (flame > 0)
                {
                    block.setType(Material.FIRE);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDmg(EntityDamageByEntityEvent event) //main combat driver method
    {
        if (event.getEntity() instanceof Player)
        {
            if (event.getDamager() instanceof Monster)
            {
                event.setDamage(event.getDamage() + 6); //needs to be calculated
            }
            else if (event.getDamager() instanceof Arrow)
            {
                Arrow arrow = (Arrow) event.getEntity();
                if (arrow.getShooter() instanceof Player)
                {
                    Player playerShooter = (Player) arrow.getShooter(); //might throw NPE
                    Player playerHit     = (Player) event.getEntity();
                    PugnaPlayer playS = pg.track.getPlayerData(playerShooter);
                    PugnaPlayer playH = pg.track.getPlayerData(playerHit);
                    pg.track.startCombatTimer(playS);
                    pg.track.startCombatTimer(playH);
                }
            }
            else if (event.getDamager() instanceof Player)
            {
                Player playerShooter = (Player) event.getDamager(); //might throw NPE
                Player playerHit     = (Player) event.getEntity();
                PugnaPlayer playS = pg.track.getPlayerData(playerShooter);
                PugnaPlayer playH = pg.track.getPlayerData(playerHit);
                pg.track.startCombatTimer(playS);
                pg.track.startCombatTimer(playH);
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
            event.setFormat(ChatColor.DARK_PURPLE + pg.config.emperorTitle + " %s: %s");
        }
    }
}
