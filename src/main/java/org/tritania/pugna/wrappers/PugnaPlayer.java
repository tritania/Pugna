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
import java.io.Serializable;

import org.tritania.pugna.wrappers.PugnaScore;

public class PugnaPlayer implements Serializable
{
    private boolean teamChat;
    private String teamName;
    private boolean invite;
    private boolean inCombat;
    private String inviteTeamName;
    private String name;
    private boolean board;
    private PugnaScore score;

    public PugnaPlayer(String name)
    {
        this.name = name;
        this.teamChat = false;
        this.inCombat = false;
        this.board = false;
        this.teamName = null;
        this.score = new PugnaScore();
    }

    public String getName()
    {
        return this.name;
    }

    public PugnaScore getScore()
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
        teamName = name;
    }

    public void removeTeam()
    {
        teamName = null;
        teamChat = false;
    }

    public boolean getTeamState()
    {
        if (this.teamName.equals(null)) {
            return false;
        } else {
            return true;
        }
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

    public void setBoard(boolean value)
    {
        board = value;
    }

    public boolean getBoard()
    {
        return board;
    }
}
