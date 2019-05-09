# FighterPilot-Java
Old Java Game that I wrote as a learning excercise when I was in High School and also during my freshman year at USC.

[Here is a Demo Video on Youtube of it.](https://www.youtube.com/watch?v=a17pFXZS934)

## **IMPORTANT:** 
__This Game is no longer maintained. It is an old abandoned project I wrote a few years ago.__

This is _NOT_ an accurate representation of my current abilities as a coder. This project was initially started in 2014, and stopped sometime around 2016. I may make incremental improvements and re-writes to this codebase in the future and make the software more efficient, and get everything working again.

There are also a number of half-implemented features present in the codebase, so be prepared for things to not work as expected.

## Building the game
I recommend using Eclipse or some other Java IDE to build this software. It will not currently support compiling the game into a .jar file, and having the assets be inside of that .jar file. Furthermore, Audio does not work on Linux (as far as I have tested).

## Running the Game
The __main method__ is inside the `src/fighterpilot/Main.java` file. The game should be run from there.

Note that currently the levels are randomly generated, and there is currently no loading screen, so it may look like the game has frozen, but it is probably just generating a level. This goes for both the Title and GameOver Screens.

## Controls
The controls for the game are as follows:

__WASD__ - Move the Player Up, Down, Left, and Right

__C__ - Toggle Camera lock onto player

  - If the camera is not locked on the player, it can be moved freely around the level using the arrow keys.
  
__[ARROW KEYS]__ - Moves the Camera field of view around the level, only if the camera lock is disengaged (use the __C__ key).

__SPACE__ - On the Title and GameOver screens, advance to the next screen
