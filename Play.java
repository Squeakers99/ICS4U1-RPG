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
    private static int intPlayerStats[] = new int[3];
    private static int intEnemy1Stats[] = new int[3];
    private static int intEnemy2Stats[] = new int[3];
    private static int intEnemy3Stats[] = new int[3];

    private static String strMap[][] = new String[20][20];
    private static int intEnemyCount;
    private static int intEnemiesDefeated = 0;
    private static boolean blnContinue = true;  

    //Method to play the game
    public static void Game(Console con, String strMapChoice){
        //Loads the Stats arrays
        intPlayerStats[0] = 50;
        intPlayerStats[1] = 50;
        intPlayerStats[2] = 50;

        intEnemy1Stats[0] = 20;
        intEnemy1Stats[1] = 20;
        intEnemy1Stats[2] = 20;

        intEnemy2Stats[0] = 30;
        intEnemy2Stats[1] = 30;
        intEnemy2Stats[2] = 30;

        intEnemy3Stats[0] = 40;
        intEnemy3Stats[1] = 40;
        intEnemy3Stats[2] = 40;

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
        BufferedImage imgPlayer = con.loadImage("Images/Avatars/Player.png");
        BufferedImage imgDrowned = con.loadImage("Images/Other/Drowned.png");
        BufferedImage imgDeath = con.loadImage("Images/Other/Death.png");
        BufferedImage imgPlayerDeath = con.loadImage("Images/Other/Player (Death Animation).png");

        //Loads the array with the chosen map
        for(intCountRows = 0; intCountRows < 20; intCountRows++){
            strFileLine = txtMap.readLine();
            strFileSplit = strFileLine.split(",");
            for(intCountColumns = 0; intCountColumns < 20; intCountColumns++){
                strMap[intCountRows][intCountColumns] = strFileSplit[intCountColumns];
            }
        }

        //Calls the method to draw the map and draws the player
        drawMap(con, intCountColumns, intCountRows);
        con.drawImage(imgPlayer, intPlayerCol, intPlayerRow);
        con.repaint();

        //Tells me how many enemies there are
        System.out.println("Enemy Count: " + intEnemyCount);

        //Loops to get WASD Player Movement
        while(blnContinue){
            //Gets the current key pressed
            chrKeyPressed = con.currentChar();

            //Tries to run this code for movement
            try{
                //Triggers or retracts the HUD
                if(chrKeyPressed == 'h'){
                    blnHUDActive = HUD_Display(con, blnHUDActive, intAnimationLoop, imgPlayer, intPlayerX, intPlayerY, intCountColumns, intCountRows);
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
                            if(intPlayerY != intPlayerRow*30){
                                intPlayerY += 30;
                                for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                    intPlayerY -= 5;
                                    drawMap(con, intCountColumns, intCountRows);
                                    con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                    con.repaint();
                                    con.sleep(16);
                                }
                            }

                            intPlayerRow = intPlayerY/30;

                        //If down, this runs
                        }else if(chrKeyPressed == 's'){
                            strNextBlock = strMap[intPlayerRow + 1][intPlayerCol];

                            //Calls the action method to get the new Y position
                            intPlayerY = action(strNextBlock, chrKeyPressed, intPlayerY, imgPlayer, intAnimationLoop, intPlayerRow+1, intPlayerCol);

                            //Animates Movement
                            if(intPlayerY != intPlayerRow*30){
                                intPlayerY -= 30;
                                for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                    intPlayerY += 5;
                                    drawMap(con, intCountColumns, intCountRows);
                                    con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                    con.repaint();
                                    con.sleep(16);
                                }
                            }

                            intPlayerRow = intPlayerY/30;

                        //If left, this runs
                        }else if(chrKeyPressed == 'a'){
                            strNextBlock = strMap[intPlayerRow][intPlayerCol - 1];

                            //Calls the action method to get the new X position
                            intPlayerX = action(strNextBlock, chrKeyPressed, intPlayerX, imgPlayer, intAnimationLoop, intPlayerRow, intPlayerCol-1);

                            //Animates Movement
                            if(intPlayerX != intPlayerCol*30){
                                intPlayerX += 30;
                                for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                    intPlayerX -= 5;
                                    drawMap(con, intCountColumns, intCountRows);
                                    con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                    con.repaint();
                                    con.sleep(16);
                                }
                            }

                            intPlayerCol = intPlayerX/30;
                        
                        //If right, this runs
                        }else if(chrKeyPressed == 'd'){
                            strNextBlock = strMap[intPlayerRow][intPlayerCol + 1];
                            
                            //Calls the action method to get the new X position
                            intPlayerX = action(strNextBlock, chrKeyPressed, intPlayerX, imgPlayer, intAnimationLoop, intPlayerRow, intPlayerCol+1);

                            //Animates Movement
                            if(intPlayerX != intPlayerCol*30){
                                intPlayerX -= 30;
                                for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                    intPlayerX += 5;
                                    drawMap(con, intCountColumns, intCountRows);
                                    con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                    con.repaint();
                                    con.sleep(16);
                                }
                            }

                            intPlayerCol = intPlayerX/30;
                        }
                    }

                    //Draws the drowned screen if they drowned after player is animated
                    if(strNextBlock.equals("w")){
                        //Sleeps the console so the player is seen in the water
                        con.sleep(100);

                        //Slide Animation
                        for (intAnimationLoop = 600; intAnimationLoop >= 0; intAnimationLoop -= 15) {
                            con.drawImage(imgDrowned, intAnimationLoop, 0);
                            con.repaint();
                            con.sleep(16);
                        }

                        //Will not loop again
                        blnContinue = false;
                    }

                    //Checks if the player's health is below or equal to 0
                    if(intPlayerStats[0] <= 0){
                        for (intAnimationLoop = -600; intAnimationLoop <= 0; intAnimationLoop += 15) {
                            con.drawImage(imgDeath, intAnimationLoop, 0);
                            con.repaint();
                            con.sleep(16);
                        }
                        con.sleep(500);
                        for (intAnimationLoop = -150; intAnimationLoop <= 600; intAnimationLoop += 50){
                            con.drawImage(imgDeath, 0, 0);
                            con.drawImage(imgPlayerDeath, intAnimationLoop, 175);
                            con.repaint();
                            con.sleep(16);
                        }
                        blnContinue = false;
                    }

                    //Checks if the player beat all the enemies
                    if(intEnemiesDefeated == intEnemyCount){
                        //Win Code here
                    }

                    //Redraws the console and delays to prevent multiple inputs
                    con.sleep(100);
                    con.repaint();
                }
            
            //If an array error is caught, the player cannot go there because it is outside the map. It does nothing
            }catch(Exception ArrayIndexOutOfBounds){}
        }
    }
    
    //Custom method to draw a rectangle with a given thickness
    public static void drawRectangleOutline(Console con, int intX, int intY, int intWidth, int intHeight, int intThickness){
        //Defines a variable for the loop
        int intCount;

        //loops to create a rectangle with a given thickness
        for(intCount = intThickness;intCount > 0;intCount--){
            con.drawRect(intX,intY,intWidth,intHeight);
            intX += 1;
            intY += 1;
            intWidth -= 2;
            intHeight -= 2;
        }
    }

    //Method to draw the map
    public static void drawMap(Console con, int intCountColumns, int intCountRows){
        
        //String Variable Initialization
        String strBlock;
        
        //Integer Variables Initialization
        int intBlockX = 0;
        int intBlockY = 0;
        intEnemyCount = 0;

        //Blocks and Enemies Image Loading
        BufferedImage imgWater = con.loadImage("Images/Blocks/Water.png");
        BufferedImage imgGrass = con.loadImage("Images/Blocks/Grass.png");
        BufferedImage imgTree = con.loadImage("Images/Blocks/Tree.png");
        BufferedImage imgBuilding = con.loadImage("Images/Blocks/Building.png");
        BufferedImage imgEnemy1 = con.loadImage("Images/Avatars/Enemy 1.png");
        BufferedImage imgEnemy2 = con.loadImage("Images/Avatars/Enemy 2.png");
        BufferedImage imgEnemy3 = con.loadImage("Images/Avatars/Enemy 3.png");

        //Loops to draw the board
        for(intCountRows = 0; intCountRows < 20; intCountRows++){
            for(intCountColumns = 0; intCountColumns < 20; intCountColumns++){
                strBlock = strMap[intCountRows][intCountColumns];
                if(strBlock.equals("g") || (strBlock.equals("d") || strBlock.equals("s"))){
                    con.drawImage(imgGrass, intBlockX, intBlockY);
                }else if(strBlock.equals("w")){
                    con.drawImage(imgWater, intBlockX, intBlockY);
                }else if(strBlock.equals("t")){
                    con.drawImage(imgTree, intBlockX, intBlockY);
                }else if(strBlock.equals("b")){
                    con.drawImage(imgBuilding, intBlockX, intBlockY);
                }else if(strBlock.equals("e1")){
                    con.drawImage(imgEnemy1, intBlockX, intBlockY);
                    intEnemyCount++;
                }else if(strBlock.equals("e2")){
                    con.drawImage(imgEnemy2, intBlockX, intBlockY);
                    intEnemyCount++;
                }else if(strBlock.equals("e3")){
                    con.drawImage(imgEnemy3, intBlockX, intBlockY);
                    intEnemyCount++;
                }
                intBlockX += 30;
            }
            intBlockX = 0;
            intBlockY += 30;
        }
        con.repaint();
    }

    //Method for action logic
    public static int action(String strNextBlock, char chrKeyPressed, int intPlayerMovement, BufferedImage imgPlayer, int intAnimationLoop, int intPlayerRequestedRow, int intPlayerRequestedCol){
        //Creates a variable to see how much health lost (if any)
        int intHealthLost = 0;
        
        //Checks for Building on the next block
        if(strNextBlock.equals("b") && intPlayerStats[0] < 100){
            intPlayerStats[0] += 10;
            if(intPlayerStats[0] > 100){
                intPlayerStats[0] = 100;
            }
            strMap[intPlayerRequestedRow][intPlayerRequestedCol] = "g";
        
        //Checks if the next block is a defence boost
        }else if(strNextBlock.equals("s") && intPlayerStats[1] < 100 && intPlayerStats[1] > 0){
            //Adds 10 to the player's defence and replaces the block with grass
            intPlayerStats[1] += 10;
            strMap[intPlayerRequestedRow][intPlayerRequestedCol] = "g";

        //Checks if the next block is a damage boost
        }else if(strNextBlock.equals("d") && intPlayerStats[2] < 100 && intPlayerStats[2] > 0){
            //Adds 10 to the player's damage and replaces the block with grass
            intPlayerStats[2] += 10;
            strMap[intPlayerRequestedRow][intPlayerRequestedCol] = "g";
        
        //Checks if enemy 1 is on the next block
        }else if(strNextBlock.equals("e1") && intPlayerStats[0] > 0){
            //Enemy 1 Battle logic
            intHealthLost = (int)(intEnemy1Stats[2] - ((intPlayerStats[1]/100.0) * intEnemy1Stats[2]));

        //Checks if enemy 2 is on the next block
        }else if(strNextBlock.equals("e2") && intPlayerStats[0] > 0){
            //Enemy 2 Battle logic
            intHealthLost = (int)(intEnemy2Stats[2] - ((intPlayerStats[1]/100.0) * intEnemy2Stats[2]));

        //Checks if enemy 3 is on the next block
        }else if(strNextBlock.equals("e3") && intPlayerStats[0] > 0){
            //Enemy 3 Battle logic
            intHealthLost = (int)(intEnemy3Stats[2] - ((intPlayerStats[1]/100.0) * intEnemy3Stats[2]));
        }

        //If it is any of the 3 enemy blocks, this code runs
        if((strNextBlock.equals("e1") || strNextBlock.equals("e2") || strNextBlock.equals("e3")) && intPlayerStats[0] > 0){
            //General code for all the enemy blocks
            intPlayerStats[0] -= intHealthLost;
            if(intPlayerStats[1] > 0){
                intPlayerStats[1] -= 5;
            }
            strMap[intPlayerRequestedRow][intPlayerRequestedCol] = "g";
            intEnemiesDefeated += 1;
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
    public static boolean HUD_Display(Console con, boolean blnHUDActive, int intAnimationLoop, BufferedImage imgPlayer, int intPlayerX, int intPlayerY, int intCountColumns, int intCountRows){
        //Loads in the font for the display
        Font fntHUDTitle = con.loadFont("Fonts/HUD Title.ttf", 50);
        Font fntPlayerStats = con.loadFont("Fonts/Stats font.ttf",40);
        Font fntEnemyStats = con.loadFont("Fonts/Stats font.ttf", 20);

        //Loads images for the HUD
        BufferedImage imgHealth = con.loadImage("Images/HUD Elements/Heart.png");
        BufferedImage imgDefence = con.loadImage("Images/HUD Elements/Shield.png");
        BufferedImage imgDamage = con.loadImage("Images/HUD Elements/Damage.png");

        //Integer variables to animate the HUD
        int intHUD_Y;
        int intBarWitdh;
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
            drawMap(con, intCountColumns, intCountRows);
            con.drawImage(imgPlayer, intPlayerX, intPlayerY);

            //Fixes lag issues in the console
            con.repaint();

            //Draws the HUD background and title
            con.setDrawColor(clrBackground);
            con.fillRect(0, intHUD_Y, 600, 375);
            con.setDrawColor(Color.white);
            con.setDrawFont(fntHUDTitle);
            con.drawString("HERO STATS", 175, intHUD_Y);

            //Draws the health bar based on the player's health stats
            con.setDrawColor(clrHealth);
            intBarWitdh = (int)(400.0*(intPlayerStats[0]/100.0));
            intBarY = intHUD_Y+90;
            con.fillRect(82,intBarY,intBarWitdh,31);
            con.setDrawColor(Color.black);
            drawRectangleOutline(con,79,intBarY-3,406,36,3);
            con.drawImage(imgHealth, 30, intBarY-7);
            con.setDrawColor(Color.white);
            con.setDrawFont(fntPlayerStats);
            con.drawString(Integer.toString(intPlayerStats[0]), 500, intBarY-20);

            //Draws the defence bar based on the player's defence stats
            con.setDrawColor(clrDefence);
            intBarWitdh = (int)(400.0*(intPlayerStats[1]/100.0));
            intBarY = intHUD_Y+150;
            con.fillRect(82,intBarY,intBarWitdh,31);
            con.setDrawColor(Color.black);
            drawRectangleOutline(con,79,intBarY-3,406,36,3);
            con.drawImage(imgDefence, 30, intBarY-6);
            con.setDrawColor(Color.white);
            con.setDrawFont(fntPlayerStats);
            con.drawString(Integer.toString(intPlayerStats[1]), 500, intBarY-20);

            //Draws the damage bar based on the player's damage stats
            con.setDrawColor(clrDamage);
            intBarWitdh = (int)(400.0*(intPlayerStats[2]/100.0));
            intBarY = intHUD_Y+210;
            con.fillRect(82,intBarY,intBarWitdh,31);
            con.setDrawColor(Color.black);
            drawRectangleOutline(con,79,intBarY-3,406,36,3);
            con.drawImage(imgDamage, 30, intBarY-3);
            con.setDrawColor(Color.white);
            con.setDrawFont(fntPlayerStats);
            con.drawString(Integer.toString(intPlayerStats[2]), 500, intBarY-20);

            //Draws out the enemy stats
            con.setDrawColor(Color.white);
            con.setDrawFont(fntEnemyStats);
            con.drawString("E1 Health: " + intEnemy1Stats[0], 10, intHUD_Y+260);
            con.drawString("E1 Defence: " + intEnemy1Stats[1], 10, intHUD_Y+290);
            con.drawString("E1 Damage: " + intEnemy1Stats[2], 10, intHUD_Y+320);
            con.drawString("E2 Health: " + intEnemy2Stats[0], 220, intHUD_Y+260);
            con.drawString("E2 Defence: " + intEnemy2Stats[1], 220, intHUD_Y+290);
            con.drawString("E2 Damage: " + intEnemy2Stats[2], 220, intHUD_Y+320);
            con.drawString("E3 Health: " + intEnemy3Stats[0], 425, intHUD_Y+260);
            con.drawString("E3 Defence: " + intEnemy3Stats[1], 425, intHUD_Y+290);
            con.drawString("E3 Damage: " + intEnemy3Stats[2], 425, intHUD_Y+320);

            //Gets ready for the next loop by adding the interval
            intHUD_Y += intInterval;
            con.sleep(16);
            con.repaint();
        }

        if(!blnHUDActive){
            return true;   
        }else{ 
            return false;
        }
    }
}