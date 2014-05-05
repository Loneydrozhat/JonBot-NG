JonBot-NG
=========

![alt tag](http://i.imgur.com/NSAIqpq.png)


The third and final iteration of my Zezenia Automation tool.

JonBot, the first iteration was nothing more than key and mouse stroke automation. Watching the screen, and reacting to
changes.

JonBot2, took the original further, digging into the Zezenia clients running memory to read information directly
from the client instead of watching for pixels changing colors on the screen. A more advanced, but cleaner version
of the original. This version also included a functional warrior cave bot with a scripter. It would follow the
scripted path killing monsters and looting them along the way. This version suffered from my lack of deep knowledge
in how to access and understand an applications memory.

JonBot-NG seeks to rectify the inadequacies of JonBot2, while at the same time providing a more advanced, more 
complete, and cleaner tool for the end user.


==================
Required Libraries
==================
JNA - https://github.com/twall/jna

JIntelliType - http://code.google.com/p/jintellitype/

DS Explorer - https://code.google.com/p/dsexplorer/


=========================
Compilation instructions
=========================

1) - Using netbeans, create a new project.

2) - Add the above libraries to the project.

3) - Add source files found here.

4) - Compile.

5) - Enjoy.


================
Non-Cavebot Use
================

1) - Start zezenia.

2) - Log into the game.

3) - Start the bot.

4) - Set your settings.

5) - Make sure you set your backpacks. This step is crucial.

6) - Save your settings, if you haven't.

7) - Open your backpacks in the order that you specified in the bot. This step is non-negotiable. The loot backpack, containing the 24 empty loot backpacks must be fully extended. 

///////////////////////////////////////////////////////////////////////////////////////////////////

[Example Ordering] Main backpack - 1, Mana BP - 3, Health Potion BP - 2, Loot BP - 5, Food BP - 4

First open the main backpack. then open the health potion bp. Next open the mana potion backpack followed by the food backpack. Next, open the backpack containing the loot backpacks, and finally, open the first loot backpack.

///////////////////////////////////////////////////////////////////////////////////////////////////

8) - In the bot, check every feature you want to use, and press start.

9) - You have 3 seconds to tab back to the game before things start running.


=============
Cavebot Use
=============

1) - Do the above.

2) - Load, or create a script.

3) - Goto the start point, and press start in the cavebot tab.

4) - You have 3 seconds to tab back to the game before the cavebot starts.


=================
Creating Scripts
=================

1) - Withing the cavebot open, and logged into the game, walk arround (preferably in straight lines)
adding waypoints to the current script.

2) - You can add a waypoint either by pressing 'Alt+a', or the add waypoint buttons in the bot.

3) - When finished, save your script.
