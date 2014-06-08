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
import org.bukkit.ChatColor;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.wrappers.*;
import org.tritania.pugna.util.Message;
/*End Imports*/

public class CTeamChat implements CommandExecutor 
{
	public Pugna pg;

    public CTeamChat(Pugna pg)
    {
        this.pg = pg;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = (Player) sender;
		PugnaPlayer play = pg.track.getPlayerData(player);
		if (play.getTeamState())
		{
			String teamName = play.getTeam();
            PugnaTeam team = pg.teams.getTeam(teamName);
			if (args.length == 0) 
			{
				if (play.getChatState())
				{
					Message.info(sender, "Disabling team auto chat");
					play.setChat(false);
				}
				else 
				{
					Message.info(sender, "Enabling team auto chat");
					play.setChat(true);
				}
			}
			else if (args[0].equals("true"))
			{
				Message.info(sender, "Enabling team auto chat");
				play.setChat(true);
			}
			else if (args[0].equals("false"))
			{
				Message.info(sender, "Disabling team auto chat");
				play.setChat(false);
			}
			else 
			{
				String result = "";
				for (String entry : args) 
				{
					result += " " + entry;
				}
				team.sendMessage(player, result);
			}
			return true;
		}
		else
		{
			Message.info(sender, "You don't have a team to talk too!");
			return true;
		}
	}
}
