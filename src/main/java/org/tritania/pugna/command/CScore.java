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
import org.bukkit.Bukkit;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.wrappers.*;
import org.tritania.pugna.util.Message;
/*End Imports*/

public class CScore implements CommandExecutor
{
    public Pugna pg;

    public CScore(Pugna pg)
    {
        this.pg = pg;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player player = (Player) sender;
        PugnaPlayer play = pg.track.getPlayerData(player);
        if (args.length < 1)
        {
            Message.info(sender, command.getUsage());
            return true;
        }
        if (player.hasPermission("pugna.scores"))
        {
            if (args[0].equals("add"))
            {
                PugnaPlayer playerValue = pg.track.getPlayerData(Bukkit.getPlayer(args[1]));
                pg.emperor.addPlayerScore(playerValue, Integer.parseInt(args[2]));
                Message.info(sender, "Added score to " + args[1]);
            }
            else if (args[0].equals("remove"))
            {
                PugnaPlayer playerValue = pg.track.getPlayerData(Bukkit.getPlayer(args[1]));
                pg.emperor.removePlayerScore(playerValue, Integer.parseInt(args[2]));
                Message.info(sender, "Removed score from " + args[1]);
            }
        }
        if (args[0].equals("status"))
        {
            Message.info(sender, pg.emperor.getEmperorName() + " is currently emperor");
            pg.stats.setPlayerBoard(player);
        }
        else
        {
            Message.info(sender, command.getUsage());
            return true;
        }

        return true;
    }
}
