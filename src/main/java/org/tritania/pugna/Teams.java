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
import java.util.Map;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.entity.Player;

import org.tritania.pugna.Pugna;
import org.tritania.pugna.util.Message;
import org.tritania.pugna.wrappers.*;


public class Teams
{
    public Pugna pg;
    private HashMap<String, PugnaTeam> teamList = new HashMap<String, PugnaTeam>();

    public Teams(Pugna pg)
    {
        this.pg = pg;
    }

    public void createTeam(Player player, String teamName, PugnaPlayer tracked)
    {
        PugnaTeam team = new PugnaTeam(player, teamName, tracked);
        teamList.put(teamName, team);
        tracked.setTeam(teamName);
    }

    public void disbandTeam(String teamName)
    {
        PugnaTeam team = teamList.get(teamName);
        HashMap<UUID, PugnaPlayer> players = team.getMembers();
        for (Map.Entry<UUID, PugnaPlayer> entry : players.entrySet())
        {
            PugnaPlayer player = entry.getValue();
            player.setChat(false);
            player.removeTeam();
        }
        teamList.remove(teamName);
    }

    public PugnaTeam getTeam(String teamName)
    {
        return teamList.get(teamName);
    }

    public void loadTeams()
    {
        teamList = pg.storage.loadData("teams.data");
    }
    public void saveTeams()
    {
        pg.storage.saveData(teamList, "teams.data");
    }
}

