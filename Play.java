/*
Soheil Rajabali
ICS4U1 RPG: Sus Sus Amogus
V7.0
*/

/*
* PERSONAL NOTES
* Health: Array Position 0
* Defence: Array Position 1
* Damage: Array Position 2
*/

import arc.*;
import java.awt.*;
import java.awt.image.*;

public class Play {
    //Creates variables to be accessible by all methods
    public static int intPlayerStats[] = new int[3];
    public static int intEnemyStats[][] = new int[3][3];

    public static String strMap[][] = new String[20][20];
    public static int intEnemyCount;
    public static boolean blnContinue = true;  

    //Method to play the game
    public static void Game(String strMapChoice){
        //Loads the Stats arrays
        intPlayerStats[0] = 50;
        intPlayerStats[1] = 50;
        intPlayerStats[2] = 50;

        intEnemyStats[0][0] = 20;
        intEnemyStats[0][1] = 20;
        intEnemyStats[0][2] = 20;

        intEnemyStats[1][0] = 30;
        intEnemyStats[1][1] = 30;
        intEnemyStats[1][2] = 30;

        intEnemyStats[2][0] = 40;
        intEnemyStats[2][1] = 40;
        intEnemyStats[2][2] = 40;

        //Initializes all Array variables
        String strFileSplit[] = new String[20];

        //Initializes all String variables
        String strFileLine;
        String strNextBlock = "";

        //Initializes all Integer variables
        int intCountRows = 0;
        int intCountColumns = 0;

        int intPlayerRow = 0;
        int intPlayerCol = 0;
        int intPlayerX = 0;
        int intPlayerY = 0;
        int intAnimationLoop = 0;
        
        //Initalizes all Boolean variables
        boolean blnHUDActive = false;

        //Initializes all character variables
        char chrKeyPressed;

        //Initializes the chosen map
        TextInputFile txtMap = new TextInputFile("Maps/" + strMapChoice + ".csv");

        //Initializes all pictures
        BufferedImage imgPlayer = RPG_Game.con.loadImage("Images/Avatars/Player.png");
        BufferedImage imgDrowned = RPG_Game.con.loadImage("Images/Other/Drowned.png");
        BufferedImage imgDeath = RPG_Game.con.loadImage("Images/Other/Death.png");
        BufferedImage imgPlayerDeath = RPG_Game.con.loadImage("Images/Other/Player (Death Animation).png");
        BufferedImage imgWin = RPG_Game.con.loadImage("Images/Other/Win Screen.png");

        //Loads the array with the chosen map
        for(intCountRows = 0; intCountRows < 20; intCountRows++){
            strFileLine = txtMap.readLine();
            strFileSplit = strFileLine.split(",");
            for(intCountColumns = 0; intCountColumns < 20; intCountColumns++){
                strMap[intCountRows][intCountColumns] = strFileSplit[intCountColumns];
            }
        }

        //Calls the method to draw the map and draws the player
        drawMap();
        RPG_Game.con.drawImage(imgPlayer, intPlayerCol, intPlayerRow);
        RPG_Game.con.repaint();

        //Tells me how many enemies there are
        System.out.println("Enemy Count: " + intEnemyCount);

        //Loops to get WASD Player Movement
        while(blnContinue){
            //Gets the current key pressed
            chrKeyPressed = RPG_Game.con.currentChar();

            //Tries to run this code for movement
            try{
                //Triggers or retracts the HUD
                if(chrKeyPressed == 'h'){
                    blnHUDActive = HUD_Display(blnHUDActive, intAnimationLoop, imgPlayer, intPlayerX, intPlayerY, intCountColumns, intCountRows);
                }

                //Only moves character if HUD is inactive
                if(!blnHUDActive){
                    //Check for movement
                    if((chrKeyPressed == 'w')  || (chrKeyPressed == 'a') || (chrKeyPressed == 's') || (chrKeyPressed == 'd')){
                        //If up, this runs
                        if(chrKeyPressed == 'w'){
                            strNextBlock = strMap[intPlayerRow - 1][intPlayerCol];

                            //Calls the action method to get the new Y position
                            intPlayerY = action(strNextBlock, chrKeyPressed, intPlayerY, imgPlayer, intAnimationLoop, intPlayerRow-1, intPlayerCol);

                            //Animates Movement
                            if(intPlayerY != intPlayerRow*30 && blnContinue){
                                intPlayerY += 30;
                                for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                    intPlayerY -= 5;
                                    drawMap();
                                    RPG_Game.con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                    RPG_Game.con.repaint();
                                    RPG_Game.con.sleep(16);
                                }
                            }

                            intPlayerRow = intPlayerY/30;

                        //If down, this runs
                        }else if(chrKeyPressed == 's'){
                            strNextBlock = strMap[intPlayerRow + 1][intPlayerCol];

                            //Calls the action method to get the new Y position
                            intPlayerY = action(strNextBlock, chrKeyPressed, intPlayerY, imgPlayer, intAnimationLoop, intPlayerRow+1, intPlayerCol);

                            //Animates Movement
                            if(intPlayerY != intPlayerRow*30 && blnContinue){                                intPlayerY -= 30;
                                for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                    intPlayerY += 5;
                                    drawMap();
                                    RPG_Game.con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                    RPG_Game.con.repaint();
                                    RPG_Game.con.sleep(16);
                                }
                            }

                            intPlayerRow = intPlayerY/30;

                        //If left, this runs
                        }else if(chrKeyPressed == 'a'){
                            strNextBlock = strMap[intPlayerRow][intPlayerCol - 1];

                            //Calls the action method to get the new X position
                            intPlayerX = action(strNextBlock, chrKeyPressed, intPlayerX, imgPlayer, intAnimationLoop, intPlayerRow, intPlayerCol-1);

                            //Animates Movement
                            if(intPlayerX != intPlayerCol*30 && blnContinue){
                                intPlayerX += 30;
                                for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                    intPlayerX -= 5;
                                    drawMap();
                                    RPG_Game.con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                    RPG_Game.con.repaint();
                                    RPG_Game.con.sleep(16);
                                }
                            }

                            intPlayerCol = intPlayerX/30;
                        
                        //If right, this runs
                        }else if(chrKeyPressed == 'd'){
                            strNextBlock = strMap[intPlayerRow][intPlayerCol + 1];
                            
                            //Calls the action method to get the new X position
                            intPlayerX = action(strNextBlock, chrKeyPressed, intPlayerX, imgPlayer, intAnimationLoop, intPlayerRow, intPlayerCol+1);

                            //Animates Movement
                            if(intPlayerX != intPlayerCol*30 && blnContinue){
                                intPlayerX -= 30;
                                for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                    intPlayerX += 5;
                                    drawMap();
                                    RPG_Game.con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                    RPG_Game.con.repaint();
                                    RPG_Game.con.sleep(16);
                                }
                            }

                            intPlayerCol = intPlayerX/30;
                        }
                    }

                    //Draws the drowned screen if they drowned after player is animated
                    if(strNextBlock.equals("w")){
                        //Sleeps the console so the player is seen in the water
                        RPG_Game.con.sleep(100);

                        //Slide Animation
                        for (intAnimationLoop = 600; intAnimationLoop >= 0; intAnimationLoop -= 15) {
                            RPG_Game.con.drawImage(imgDrowned, intAnimationLoop, 0);
                            RPG_Game.con.repaint();
                            RPG_Game.con.sleep(16);
                        }

                        //Will not loop again
                        blnContinue = false;
                    }

                    //Checks if the player's health is below or equal to 0
                    if(intPlayerStats[0] <= 0){
                        for (intAnimationLoop = -600; intAnimationLoop <= 0; intAnimationLoop += 15) {
                            RPG_Game.con.drawImage(imgDeath, intAnimationLoop, 0);
                            RPG_Game.con.repaint();
                            RPG_Game.con.sleep(16);
                        }
                        RPG_Game.con.sleep(500);
                        for (intAnimationLoop = -150; intAnimationLoop <= 600; intAnimationLoop += 50){
                            RPG_Game.con.drawImage(imgDeath, 0, 0);
                            RPG_Game.con.drawImage(imgPlayerDeath, intAnimationLoop, 175);
                            RPG_Game.con.repaint();
                            RPG_Game.con.sleep(16);
                        }
                        blnContinue = false;
                    }

                    //Checks if the player beat all the enemies
                    if(intEnemyCount == 0){
                        for(intAnimationLoop = 600; intAnimationLoop >= 0; intAnimationLoop -= 15) {
                            RPG_Game.con.drawImage(imgWin, intAnimationLoop, 0);
                            RPG_Game.con.repaint();
                            RPG_Game.con.sleep(16);
                        }
                        blnContinue = false;
                    }

                    //Redraws the console and delays to prevent multiple inputs
                    RPG_Game.con.sleep(100);
                    RPG_Game.con.repaint();
                }

            
            //If an array error is caught, the player cannot go there because it is outside the map. It does nothing
            }catch(Exception ArrayIndexOutOfBounds){}
        }
    }
    
    //Custom method to draw a rectangle with a given thickness
    public static void drawRectangleOutline(int intX, int intY, int intWidth, int intHeight, int intThickness){
        //Defines a variable for the loop
        int intCount;

        //loops to create a rectangle with a given thickness
        for(intCount = intThickness;intCount > 0;intCount--){
            RPG_Game.con.drawRect(intX,intY,intWidth,intHeight);
            intX += 1;
            intY += 1;
            intWidth -= 2;
            intHeight -= 2;
        }
    }

    //Method to draw the map
    public static void drawMap(){
        int intCountColumns;
        int intCountRows;
        
        //String Variable Initialization
        String strBlock;
        
        //Integer Variables Initialization
        int intBlockX = 0;
        int intBlockY = 0;
        intEnemyCount = 0;

        //Blocks and Enemies Image Loading
        BufferedImage imgWater = RPG_Game.con.loadImage("Images/Blocks/Water.png");
        BufferedImage imgGrass = RPG_Game.con.loadImage("Images/Blocks/Grass.png");
        BufferedImage imgTree = RPG_Game.con.loadImage("Images/Blocks/Tree.png");
        BufferedImage imgBuilding = RPG_Game.con.loadImage("Images/Blocks/Building.png");
        BufferedImage imgEnemy1 = RPG_Game.con.loadImage("Images/Avatars/Enemy 1.png");
        BufferedImage imgEnemy2 = RPG_Game.con.loadImage("Images/Avatars/Enemy 2.png");
        BufferedImage imgEnemy3 = RPG_Game.con.loadImage("Images/Avatars/Enemy 3.png");

        //Loops to draw the board
        for(intCountRows = 0; intCountRows < 20; intCountRows++){
            for(intCountColumns = 0; intCountColumns < 20; intCountColumns++){
                strBlock = strMap[intCountRows][intCountColumns];
                if(strBlock.equals("g") || (strBlock.equals("d") || strBlock.equals("s"))){
                    RPG_Game.con.drawImage(imgGrass, intBlockX, intBlockY);
                }else if(strBlock.equals("w")){
                    RPG_Game.con.drawImage(imgWater, intBlockX, intBlockY);
                }else if(strBlock.equals("t")){
                    RPG_Game.con.drawImage(imgTree, intBlockX, intBlockY);
                }else if(strBlock.equals("b")){
                    RPG_Game.con.drawImage(imgBuilding, intBlockX, intBlockY);
                }else if(strBlock.equals("e1")){
                    RPG_Game.con.drawImage(imgEnemy1, intBlockX, intBlockY);
                    intEnemyCount++;
                }else if(strBlock.equals("e2")){
                    RPG_Game.con.drawImage(imgEnemy2, intBlockX, intBlockY);
                    intEnemyCount++;
                }else if(strBlock.equals("e3")){
                    RPG_Game.con.drawImage(imgEnemy3, intBlockX, intBlockY);
                    intEnemyCount++;
                }
                intBlockX += 30;
            }
            intBlockX = 0;
            intBlockY += 30;
        }
        RPG_Game.con.repaint();
    }

    //Method for action logic
    public static int action(String strNextBlock, char chrKeyPressed, int intPlayerMovement, BufferedImage imgPlayer, int intAnimationLoop, int intPlayerRequestedRow, int intPlayerRequestedCol){
        //Checks for Building on the next block
        if(strNextBlock.equals("b") && intPlayerStats[0] < 100){
            intPlayerStats[0] += 10;
            if(intPlayerStats[0] > 100){
                intPlayerStats[0] = 100;
            }
            strMap[intPlayerRequestedRow][intPlayerRequestedCol] = "g";
        
        //Checks if the next block is a defence boost
        }else if(strNextBlock.equals("s") && intPlayerStats[1] < 100 && intPlayerStats[1] > 0 && intPlayerStats[0] < 100){
            //Adds 10 to the player's defence and replaces the block with grass
            intPlayerStats[1] += 10;
            if(intPlayerStats[1] > 100){
                intPlayerStats[1] = 100;
            }
            strMap[intPlayerRequestedRow][intPlayerRequestedCol] = "g";

        //Checks if the next block is a damage boost
        }else if(strNextBlock.equals("d") && intPlayerStats[2] < 100 && intPlayerStats[2] > 0){
            //Adds 10 to the player's damage and replaces the block with grass
            intPlayerStats[2] += 10;
            strMap[intPlayerRequestedRow][intPlayerRequestedCol] = "g";
        }
        
        //If it is any of the 3 enemy blocks, this code runs
        if((strNextBlock.equals("e1") || strNextBlock.equals("e2") || strNextBlock.equals("e3")) && intPlayerStats[0] > 0){
            if(strNextBlock.equals("e1")){
                BattleAnimations.enemy1Animation();
            }else if(strNextBlock.equals("e2")){
                BattleAnimations.enemy2Animation();
            }else if(strNextBlock.equals("e3")){
                BattleAnimations.enemy3Animation();
            }
            strMap[intPlayerRequestedRow][intPlayerRequestedCol] = "g";
        }

        //If the next block is anything but a tree, this will run
        if(!strNextBlock.equals("t")){
            //Moves row/column minus one if its 'w' or 'a'
            if(chrKeyPressed == 'w' || chrKeyPressed == 'a'){
                intPlayerMovement -= 30;
        
            //Moves row/column plus one if its 's' or 'd'
            }else if(chrKeyPressed == 's' || chrKeyPressed == 'd'){
                intPlayerMovement += 30;
            }
        }

        //Returns the movement, whether it be row or column
        return intPlayerMovement;
    }

    //Method to either display or hide the HUD bar with an animation
    public static boolean HUD_Display(boolean blnHUDActive, int intAnimationLoop, BufferedImage imgPlayer, int intPlayerX, int intPlayerY, int intCountColumns, int intCountRows){
        //Loads in the font for the display
        Font fntHUDTitle = RPG_Game.con.loadFont("Fonts/HUD Title.ttf", 50);
        Font fntPlayerStats = RPG_Game.con.loadFont("Fonts/Stats font.ttf",40);
        Font fntEnemyStats = RPG_Game.con.loadFont("Fonts/Stats font.ttf", 20);

        //Loads images for the HUD
        BufferedImage imgHealth = RPG_Game.con.loadImage("Images/HUD Elements/Heart.png");
        BufferedImage imgDefence = RPG_Game.con.loadImage("Images/HUD Elements/Shield.png");
        BufferedImage imgDamage = RPG_Game.con.loadImage("Images/HUD Elements/Damage.png");

        //Integer variables to animate the HUD
        int intHUD_Y;
        int intBarWidth;
        int intBarY;
        int intInterval;

        //Colors for the HUD
        Color clrBackground = new Color(114,6,6);
        Color clrHealth = new Color(2,168,41);
        Color clrDefence = new Color(0,0,204);
        Color clrDamage = new Color(255,51,51);

        //Sets up variables depending on if HUD is opening or closing
        if(!blnHUDActive){
            intHUD_Y = -375;
            intInterval = 25;
        }else{
            intHUD_Y = 0;
            intInterval = -25;
        }

        //Animates the HUD
        for(intAnimationLoop = 0; intAnimationLoop <= 375; intAnimationLoop += 25){
            //Redraws the map and player to get rid of the old HUD frame
            drawMap();
            RPG_Game.con.drawImage(imgPlayer, intPlayerX, intPlayerY);

            //Fixes lag issues in the console
            RPG_Game.con.repaint();

            //Draws the HUD background and title
            RPG_Game.con.setDrawColor(clrBackground);
            RPG_Game.con.fillRect(0, intHUD_Y, 600, 375);
            RPG_Game.con.setDrawColor(Color.white);
            RPG_Game.con.setDrawFont(fntHUDTitle);
            RPG_Game.con.drawString("HERO STATS", 175, intHUD_Y);

            //Draws the health bar based on the player's health stats
            RPG_Game.con.setDrawColor(clrHealth);
            intBarWidth = (int)(400.0*(intPlayerStats[0]/100.0));
            intBarY = intHUD_Y+90;
            RPG_Game.con.fillRect(82,intBarY,intBarWidth,31);
            RPG_Game.con.setDrawColor(Color.black);
            drawRectangleOutline(79,intBarY-3,406,36,3);
            RPG_Game.con.drawImage(imgHealth, 30, intBarY-7);
            RPG_Game.con.setDrawColor(Color.white);
            RPG_Game.con.setDrawFont(fntPlayerStats);
            RPG_Game.con.drawString(Integer.toString(intPlayerStats[0]), 500, intBarY-20);

            //Draws the defence bar based on the player's defence stats
            RPG_Game.con.setDrawColor(clrDefence);
            intBarWidth = (int)(400.0*(intPlayerStats[1]/100.0));
            intBarY = intHUD_Y+150;
            RPG_Game.con.fillRect(82,intBarY,intBarWidth,31);
            RPG_Game.con.setDrawColor(Color.black);
            drawRectangleOutline(79,intBarY-3,406,36,3);
            RPG_Game.con.drawImage(imgDefence, 30, intBarY-6);
            RPG_Game.con.setDrawColor(Color.white);
            RPG_Game.con.setDrawFont(fntPlayerStats);
            RPG_Game.con.drawString(Integer.toString(intPlayerStats[1]), 500, intBarY-20);

            //Draws the damage bar based on the player's damage stats
            RPG_Game.con.setDrawColor(clrDamage);
            intBarWidth = (int)(400.0*(intPlayerStats[2]/100.0));
            intBarY = intHUD_Y+210;
            RPG_Game.con.fillRect(82,intBarY,intBarWidth,31);
            RPG_Game.con.setDrawColor(Color.black);
            drawRectangleOutline(79,intBarY-3,406,36,3);
            RPG_Game.con.drawImage(imgDamage, 30, intBarY-3);
            RPG_Game.con.setDrawColor(Color.white);
            RPG_Game.con.setDrawFont(fntPlayerStats);
            RPG_Game.con.drawString(Integer.toString(intPlayerStats[2]), 500, intBarY-20);

            //Draws out the enemy stats
            RPG_Game.con.setDrawColor(Color.white);
            RPG_Game.con.setDrawFont(fntEnemyStats);
            RPG_Game.con.drawString("E1 Health: " + intEnemyStats[0][0], 10, intHUD_Y+260);
            RPG_Game.con.drawString("E1 Defence: " + intEnemyStats[0][1], 10, intHUD_Y+290);
            RPG_Game.con.drawString("E1 Damage: " + intEnemyStats[0][2], 10, intHUD_Y+320);
            RPG_Game.con.drawString("E2 Health: " + intEnemyStats[1][0], 220, intHUD_Y+260);
            RPG_Game.con.drawString("E2 Defence: " + intEnemyStats[1][1], 220, intHUD_Y+290);
            RPG_Game.con.drawString("E2 Damage: " + intEnemyStats[1][2], 220, intHUD_Y+320);
            RPG_Game.con.drawString("E3 Health: " + intEnemyStats[2][0], 425, intHUD_Y+260);
            RPG_Game.con.drawString("E3 Defence: " + intEnemyStats[2][1], 425, intHUD_Y+290);
            RPG_Game.con.drawString("E3 Damage: " + intEnemyStats[2][2], 425, intHUD_Y+320);

            //Gets ready for the next loop by adding the interval
            intHUD_Y += intInterval;
            RPG_Game.con.sleep(16);
            RPG_Game.con.repaint();
        }

        //Returns true or false depending on if the HUD is active
        if(!blnHUDActive){
            return true;   
        }else{ 
            return false;
        }
    }
}