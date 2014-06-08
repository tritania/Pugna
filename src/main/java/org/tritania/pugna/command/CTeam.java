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
import org.tritania.pugna.util.Message;
import org.tritania.pugna.wrappers.*;
/*End Imports*/

public class CTeam implements CommandExecutor 
{
    public Pugna pg;

    public CTeam(Pugna pg)
    {
        this.pg = pg;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player player = (Player) sender;
        PugnaPlayer trackPlayer = pg.track.getPlayerData(player);
        if (args.length < 1) 
        {
            Message.info(sender, command.getUsage());
            return true;
        }
        else if (args[0].equals("form"))
        {
            pg.teams.createTeam(player, args[1], trackPlayer);
            Message.info(sender, "You formed team: " + args[1]);
        }
        else if (trackPlayer.getTeamState())
        {
            if (args[0].equals("disband"))
            {
                String teamName = trackPlayer.getTeam();
                PugnaTeam team = pg.teams.getTeam(teamName);
                if (team.checkFounder(player))
                {
                    pg.teams.disbandTeam(teamName);
                }
                else
                {
                    Message.info(sender, "You don't have permisson to do that!");
                }
            }
            else if (args[0].equals("leave"))
            {
                String teamName = trackPlayer.getTeam();
                PugnaTeam team = pg.teams.getTeam(teamName);
                if (team.checkFounder(player))
                {
                    Message.info(sender, "Please set a new leader before leaving, /team setleader playername");
                }
            }
            else if (args[0].equals("invite"))
            {
                String teamName = trackPlayer.getTeam();
                PugnaTeam team = pg.teams.getTeam(teamName);
                if (team.checkFounder(player))
                {
                    Player invitee = Bukkit.getPlayer(args[1]);
                    PugnaPlayer invitee2 = pg.track.getPlayerData(invitee);
                    invitee2.invited(teamName);
                    Message.info(invitee, "You've been invited to join " + player.getDisplayName() + " team, /team accept or deny");
                }
            }
            else if (args[0].equals("remove"))
            {
                String teamName = trackPlayer.getTeam();
                PugnaTeam team = pg.teams.getTeam(teamName);
                if (team.checkFounder(player))
                {
                    team.removeMember(Bukkit.getPlayer(args[1]));
                }
            }
            else if (args[0].equals("setleader"))
            {
                String teamName = trackPlayer.getTeam();
                PugnaTeam team = pg.teams.getTeam(teamName);
                if (team.checkFounder(player))
                {
                    team.setNewFounder(Bukkit.getPlayer(args[1]));
                }
                else
                {
                    Message.info(sender, "You don't have permisson to do that!");
                }
            }
            else if (args[0].equals("accept"))
            {
				PugnaPlayer play = pg.track.getPlayerData(player);
				String teamName = play.getInvTeamName();
				PugnaTeam team = pg.teams.getTeam(teamName);
				team.addMember(player, play);
				play.accepted();
			}
            else if (args[0].equals("deny"))
            {
				PugnaPlayer play = pg.track.getPlayerData(player);
				play.denied();
			}
        }
        else
        {
            Message.info(sender, "You need to be in a team to use these commands!");
        }
        return true;
    }
}
