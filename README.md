Pugna
=============

Pugna is latin for conflict, which describes this plugin perfectly. Pugna is a fully featured Minecraft plugin for Bukkit servers, that inhances player versus player and player versus enviroment combat. Pugna is also very
customizable allowing the Minecraft server administrator freedom in how he/she wants to deploy the plugin.

## Features ##
Features marked with a (*) have not been implemented yet but are planned
Features marked with a (~) have been added but have not been fully finished
* bounty system (~)
* death chests
* either global or per map functionality (*)
* player heads drop on death (~)
* increased monster difficulty (~)
* teams (~)
* team chat (~)
* random dungeons with more difficult monsters (*)
* non vanilla Minecraft item drops (*)
* award a title to the player with the highest score

## Commands ##
* /pg        Gives access to all administrative commands
  - reload
* /bounty    Allows players to place bounties on other players
  - add
  - remove
  - modify
* /board     Gives access to all scoreboard commands
  - remove
* /dchest    Gives access to all death chest commands
  - destroy
  - destroy all
  - share
* /t         Team chat
* /team      Gives access to team commands
* /emperor   Gives access to score/emperor status


## Permissons ##
* pugna.chestoveride allows users with this permisson to overide deathchest locks
* pugna.reload       allows users with this permisson to reload the plugins config live
* pugna.scores       allows users with this permisson to set scores/emperor

## Installation ##
To build and install this plugin simply install apache maven and run:
```mvn clean package install```
Then move the jar file inside of the target directory to your server's plugin directory.

