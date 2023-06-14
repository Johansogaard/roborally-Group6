
# Roborally-Group6

RoboRally is a digital implementation of the popular board game RoboRally. It brings the strategic gameplay and competitive nature of the original game to a virtual platform. The game is set in a futuristic factory where players control robots and navigate through various challenges and obstacles.



## Installation

To install and set up the RoboRally project, follow these steps:

```bash
1. Open IntelliJ.

2. If no project is currently open in IntelliJ, click on Get from VCS on the welcome screen. If a project is currently open, choose File -> New -> Project from version control.

3. In the URL field, paste the repository URL (https://github.com/Johansogaard/roborally-Group6.git).

4. Choose the directory you want to clone the repository into in the Directory field.

5. Click Clone.
```
## Saving and loading games
You can save and load games by following these steps:
```bash
Saving games:
1. Run StartRoboRally

2. Click on menu and create a new game

3. Click on the menu in the top left corner

4. Click on save game and rename game


Loading games:
1. Run StartRoboRally

2. Click on menu and click load game

3. you now have the option between loading a game from the server or locally

4. You can the again choose between playing the game online or locally



```
## Creating and editing custom game boards
To personalize your game experience you can create or edit game boards:
```bash
1. Run RoboRally

2. click on create Board under the dropdown menu

3. Choose between editing and already existing board, or creating a new blank board

4. You'll then need to input the Length and height of the map

5. Now you'll be presented with a blank board, clicking on any of tiles will give you
a dropdown meny where you can choose what board element to put there.
When placing board elements you will usually also need to choose what direction that element need to be heading

6. when you're done creating your board, you can click on the save board button at the bottom of the screen,
you should then name the board so it's easier finding i later.

Now when you start a new game the board you've just created should be one of the options to choose from.
```

## Server play
You can also play RoboRally online between multiple devices using our [Restful-API server](https://github.com/Johansogaard/restfull-service-Roborally)
```bash
1. Run the restful API server, seen above

2. one player then creates a new online game

3. The rest of the players click on the join option

4. The players will then have to input the gameID (4 digit code) they will be given by the game creator
```

### [Link to game rules](https://drive.google.com/file/d/16-TwOIYv0pkEkm7ajS1w0cRSxfQxYp7x/view?usp=sharing)

## How to Play / Gameplay Instructions

### RoboRally is played in rounds, each round consists of two phases:
- ### the programming phase
    - #### in the programming phase you draw cards from your deck and arrange them in your card register to plot your moves.
- ### the activation phase
    - #### in the activation phase your robot is activated, and it will carry out it's programming. During this phase the elements on the board will also be activated.

### Cards:
#### In Roborally there are quite a handful of cards the player is able to choose from. These cards are called programming cards and are used to move your robot during the activation phase. In addition, there are also damage cards, or spam cards, that will be added to your deck if your robot ends up taking damage.

## board elements:
#### There are multiple different board elements in Roborally. The elements only affect the player if the robot sits directly on at the end of the register.

### Antenna
#### The antenna is there to determine whose turn it is to move, it acts just like a wall.

### Lasers
#### Lasers can come from 1 to 3 lasers, when a player sits on a laser at the end of the register the player will gain 1-3 damage cards (spam cards) according to the amounts of lasers. The laser will have a wall in the back of it and the beam from the laser cannot go through walls or antenna.

### Gears
#### When a player sits on a gear the player with be rotated 90 degrees according to the gears arrows at the end of the register.

### Conveyor Belts
#### The conveyor belt can come it quite a few different forms, all of which can be read about in the games rules. The primary purpose of the conveyor belt is to move the player according to the arrows on that belt.

### Push Panels
#### The push panels work by pushing the player that is resting on it. The push panels will only push the player if the number on the push panel corresponds to the current register.

### Pits
#### Landing on a pit will reboot the player when the players land on it.

### Walls
#### Players are not able to move through walls and the walls are not able to be pushed.

#### you can read more about all the board elements in the game rules.

## how to win
#### The first player to reach every checkpoint on the game board is the winner.

## Authors

- [@VictorJustesen](https://github.com/VictorJustesen)
- [@Johansogaard](https://github.com/Johansogaard)
- [@AnnesAggressive](https://github.com/AnnesAggressive)
- [@vesthepro](https://github.com/vesthepro)
- [@mikkel780](https://github.com/mikkel780)


