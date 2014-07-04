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

package org.tritania.pugna.command;

/*Start Imports*/
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.bukkit.Bukkit;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.Inventory;
import org.tritania.pugna.util.Message;
import org.tritania.pugna.util.Tools;
/*End Imports*/

public class CBounty implements CommandExecutor
{
    public Pugna pg;

    public CBounty(Pugna pg)
    {
        this.pg = pg;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player player = (Player) sender;
        if (args.length < 1)
        {
            Message.info(sender, command.getUsage());
            return true;
        }
        else if (args[0].equals("add") && args.length == 3)
        {
            if (Tools.isPlayer(args[1]))
            {
                Player target = Bukkit.getPlayer(args[1]);
                Material item = Material.getMaterial(args[2].toUpperCase());
                int amount = Integer.parseInt(args[3]);
                if (pg.bt.checkOutstanding(target, player))
                {
                    Message.info(sender, "You already have a bounty against them.");
                    return true;
                }
                if (Inventory.checkForItems(player, item, amount))
                {
                    ItemStack bounty = new ItemStack(item, amount);
                    pg.bt.createBounty(target, player, bounty);
                    Inventory.removeItems(player, bounty);
                    return true;
                }
                else
                {
                     Message.info(sender, "You don't have the items for that");
                     return true;
                }
            }
            else
            {
                Message.info(sender, "Not currently a player");
            }
        }
        else if (args[0].equals("remove"))
        {
            if (Tools.isPlayer(args[1]))
            {
                Player target = Bukkit.getPlayer(args[1]);
                pg.bt.removeBounty(target, player);
                return true;
            }
            else
            {
                Message.info(sender, "No bounty for this player");
            }
        }
        else
        {
            Message.info(sender, command.getUsage());
            return true;
        }
        return true;
    }
}


