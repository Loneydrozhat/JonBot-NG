JonBot-NG for 64 bit pc's.
==========================

![alt tag](http://i.imgur.com/NSAIqpq.png)


The third and final iteration of my Zezenia Automation tool.

JonBot, the first iteration, watched the pixel colors on the screen, and sent key strokes to the active game window accordingly. Primitive, but functional.

JonBot 2, the sequel to the first JonBot took the original further with an attempt to add a cavebot to the application. While the endeavor was successful and the bot was functional, the initial setup upon use each time was not user friendly in the least. To this end, development on the second version was halted while I advanced my knowledge.

JonBot-NG seeks to rectify the inadequacies of JonBot2, while at the same time providing a more advanced, more 
complete, and cleaner tool for the end user. No unwieldly setup. No obscure knowledge required. Start the game. Start the bot. Enjoy. Nice, clean, simplistic, and most of all, functional. There are still areas that can be improved, but this software is designed in such a way as to facilitate ease of future development.


==================
Required Libraries
==================
JNA - https://github.com/twall/jna

JIntelliType - http://code.google.com/p/jintellitype/

DS Explorer - https://code.google.com/p/dsexplorer/


====================
System Requirements
====================
64 bit pc.

Dual core cpu. (can't be old an slow is all).

Java 8



=========================
Compilation instructions
=========================

1) - Using netbeans, create a new project.

2) - Add the above libraries to the project.

3) - Add source files found here.

4) - Compile.

5) - Enjoy.


======================
Initial Zezenia Setup
======================
Start Zezenia.

Enable dual interface.

Enable Force new windows to the right.

Enable Classic right click.

Move all non bp windows to the left hand side of the screen.


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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

[Example Ordering] Main backpack - 1, Mana BP - 3, Health Potion BP - 2, Loot BP - 5, Food BP - 4

First open the main backpack, then open the health potion bp. Next open the mana potion backpack followed by the food backpack. Next, open the backpack containing the loot backpacks and fully extend it, and finally, open the first loot backpack.

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

2a) - Waypoints are best when you can draw a straight line between them, and there is nothing in the way. The bot can
maneuver a little, but it is more optimal to not make it do so.

3) - When finished, save your script.
