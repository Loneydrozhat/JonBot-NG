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

Using netbeans, create a new project.

Add the above libraries to the project.

Add source files found here.

Compile.

Enjoy.


================
Non-Cavebot Use
================

Start zezenia.

Log into the game.

Start the bot.

Set your settings.

Make sure you set your backpacks. This step is crucial.

Save your settings, if you haven't.

Open your backpacks in the order that you specified in the bot. This step is non-negotiable.

In the bot, check every feature you want to use, and press start.

You have 3 seconds to tab back to the game before things start running.


=============
Cavebot Use
=============

Do the above.

Load, or create a script.

Goto the start point, and press start in the cavebot tab.

You have 3 seconds to tab back to the game before the cavebot starts.


=================
Creating Scripts
=================

Withing the cavebot open, and logged into the game, walk arround (preferably in straight lines)
adding waypoints to the current script.

You can add a waypoint either by presseing 'Alt+a', or the add waypoint buttons in the bot.

When finished, save your script.
