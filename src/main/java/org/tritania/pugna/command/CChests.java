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
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.UUID;
import org.bukkit.Bukkit;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.Message;
/*End Imports*/

public class CChests implements CommandExecutor 
{
	public Pugna pg;

    public CChests(Pugna pg)
    {
        this.pg = pg;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (pg.config.deathChest)
		{
			Player player = (Player) sender;
			if (args.length < 1) 
			{
				Message.info(sender, command.getUsage());
				return true;
			}
			else if(args[0].equals("destroy"))
			{
				if (args.length > 1 && args[1].equals("all"))
				{
					if (player.hasPermission("pugna.chestoveride"))
					{
						Message.info(sender, "Destroying all chests");
						pg.dt.destroyAll();
						return true;
					}
					else
					{
						Message.info(sender, "You don't have permisson for this");
						return true;
					}
				}
			
				else
				{
					Message.info(sender, "Destroying all of your chests");
					pg.dt.removePlayerChests(player.getUniqueId());
					return true;
				}
			}
			else if(args[0].equals("give"))
			{
				if(args.length > 1)
				{
					Player sharee = Bukkit.getPlayer(args[1]);
					pg.dt.changeOwnership(player.getUniqueId(), sharee);
				}
				else
				{
					Message.info(sender, command.getUsage());
				}
			}
			return true;
		}
		return true;
	}
}
