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

package org.tritania.pugna.wrappers;

import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import java.io.Serializable;

public class PugnaPlayer implements Serializable
{
    private int score;
    private int kills;
    private int deaths;
    private int blocks;
    private boolean teamChat;
    private boolean inTeam;
    private String teamName;
    private boolean invite;
    private boolean inCombat;
    private String inviteTeamName;
    private String name;
    private boolean board;

    public PugnaPlayer(String name)
    {
        this.name = name;
        score = 0;
        teamChat = false;
        inTeam = false;
        inCombat = false;
        board = false;
    }

    public String getName()
    {
        return this.name;
    }

    public void addScore(int value)
    {
        score = score + value;
    }

    public void removeScore(int value)
    {
        score = score - value;
    }

    public int getScore()
    {
        return score;
    }

    public void setChat(boolean value)
    {
        teamChat = value;
    }

    public boolean getChatState()
    {
        return teamChat;
    }

    public void setTeam(String name)
    {
        inTeam = true;
        teamName = name;
    }

    public void setTeamState(boolean value)
    {
        inTeam = value;
    }

    public void removeTeam()
    {
        inTeam = false;
        teamName = null;
        teamChat = false;
    }

    public boolean getTeamState()
    {
        return inTeam;
    }

    public String getTeam()
    {
        return teamName;
    }

    public void invited(String teamName)
    {
        invite = true;
        inviteTeamName = teamName;
    }

    public String getInvTeamName()
    {
        return inviteTeamName;
    }

    public void accepted()
    {
        invite = false;
    }

    public void denied()
    {
        invite = false;
    }

    public boolean getInviteState()
    {
        return invite;
    }

    public boolean getCombatState()
    {
        return inCombat;
    }

    public void setCombatState(boolean value)
    {
        inCombat = value;
    }

    public void addKill()
    {
        kills++;
    }

    public void addBlocks()
    {
        blocks++;
    }

    public void addDeath()
    {
        deaths++;
    }

    public void setBoard(boolean value)
    {
        board = value;
    }

    public boolean getBoard()
    {
        return board;
    }
}
