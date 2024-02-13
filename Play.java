import arc.*;
import java.awt.*;
import java.awt.image.*;

public class Play {
    //Creates variables to be accessible by all methods
    private static int intPlayerHealth = 50;
    private static int intPlayerDefence = 70;
    private static int intPlayerDamage = 60;
    private static boolean blnContinue = true;  

    //Method to play the game
    public static void Game(Console con, String strMapChoice){
        //Initializes all Array variables
        String strFileSplit[] = new String[20];
        String strMap[][] = new String[20][20];

        //Initializes all String variables
        String strFileLine;
        String strNextBlock;

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

        //Initializes all pictures required for play
        BufferedImage imgPlayer = con.loadImage("Images/Avatars/Player.png");

        //Loads the array with the chosen map
        for(intCountRows = 0; intCountRows < 20; intCountRows++){
            strFileLine = txtMap.readLine();
            strFileSplit = strFileLine.split(",");
            for(intCountColumns = 0; intCountColumns < 20; intCountColumns++){
                strMap[intCountRows][intCountColumns] = strFileSplit[intCountColumns];
            }
        }

        //Calls the method to draw the map and draws the player
        drawMap(con, intCountColumns, intCountRows, strMap);
        con.drawImage(imgPlayer, intPlayerCol, intPlayerRow);
        con.repaint();

        //Loops to get WASD Player Movement
        while(blnContinue){
            //Gets the current key pressed
            chrKeyPressed = con.getChar();

            //Tries to run this code for movement
            try{
                //Check for movement
                if((chrKeyPressed == 'w')  || (chrKeyPressed == 'a') || (chrKeyPressed == 's') || (chrKeyPressed == 'd') || chrKeyPressed == 'h'){
                    //If up, this runs
                    if(chrKeyPressed == 'w'){
                        strNextBlock = strMap[intPlayerRow - 1][intPlayerCol];

                        //Calls the action method to get the new Y position
                        intPlayerY = action(con, strNextBlock, chrKeyPressed, intPlayerY, imgPlayer);

                        //Animates Movement
                        if(intPlayerY != intPlayerRow*30){
                            intPlayerY += 30;
                            for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                intPlayerY -= 5;
                                drawMap(con, intCountColumns, intCountRows, strMap);
                                con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                con.repaint();
                                con.sleep(33);
                            }
                        }

                        intPlayerRow = intPlayerY/30;

                    //If down, this runs
                    }else if(chrKeyPressed == 's'){
                        strNextBlock = strMap[intPlayerRow + 1][intPlayerCol];

                        //Calls the action method to get the new Y position
                        intPlayerY = action(con, strNextBlock, chrKeyPressed, intPlayerY, imgPlayer);

                        //Animates Movement
                        if(intPlayerY != intPlayerRow*30){
                            intPlayerY -= 30;
                            for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                intPlayerY += 5;
                                drawMap(con, intCountColumns, intCountRows, strMap);
                                con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                con.repaint();
                                con.sleep(33);
                            }
                        }

                        intPlayerRow = intPlayerY/30;

                    //If left, this runs
                    }else if(chrKeyPressed == 'a'){
                        strNextBlock = strMap[intPlayerRow][intPlayerCol - 1];

                        //Calls the action method to get the new X position
                        intPlayerX = action(con, strNextBlock, chrKeyPressed, intPlayerX, imgPlayer);

                        //Animates Movement
                        if(intPlayerX != intPlayerCol*30){
                            intPlayerX += 30;
                            for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                intPlayerX -= 5;
                                drawMap(con, intCountColumns, intCountRows, strMap);
                                con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                con.repaint();
                                con.sleep(33);
                            }
                        }

                        intPlayerCol = intPlayerX/30;
                    
                    //If right, this runs
                    }else if(chrKeyPressed == 'd'){
                        strNextBlock = strMap[intPlayerRow][intPlayerCol + 1];
                        
                        //Calls the action method to get the new X position
                        intPlayerX = action(con, strNextBlock, chrKeyPressed, intPlayerX, imgPlayer);

                        //Animates Movement
                        if(intPlayerX != intPlayerCol*30){
                            intPlayerX -= 30;
                            for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop += 5){
                                intPlayerX += 5;
                                drawMap(con, intCountColumns, intCountRows, strMap);
                                con.drawImage(imgPlayer, intPlayerX, intPlayerY);
                                con.repaint();
                                con.sleep(33);
                            }
                        }

                        intPlayerCol = intPlayerX/30;

                    //HUD bar to display the player's stats (health, defence, and damage)
                    }else if(chrKeyPressed == 'h'){
                        blnHUDActive = HUD_Display(con, blnHUDActive, intAnimationLoop, imgPlayer, intPlayerX, intPlayerY, intCountColumns, intCountRows, strMap);
                    }
                }
                //Redraws the console
                con.repaint();
            
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
    public static void drawMap(Console con, int intCountColumns, int intCountRows, String strMap[][]){
        //String Variable Initialization
        String strBlock;
        
        //Integer Variables Initialization
        int intBlockX = 0;
        int intBlockY = 0;

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
                if(strBlock.equals("g")){
                    con.drawImage(imgGrass, intBlockX, intBlockY);
                }else if(strBlock.equals("w")){
                    con.drawImage(imgWater, intBlockX, intBlockY);
                }else if(strBlock.equals("t")){
                    con.drawImage(imgTree, intBlockX, intBlockY);
                }else if(strBlock.equals("b")){
                    con.drawImage(imgBuilding, intBlockX, intBlockY);
                }else if(strBlock.equals("e1")){
                    con.drawImage(imgEnemy1, intBlockX, intBlockY);
                }else if(strBlock.equals("e2")){
                    con.drawImage(imgEnemy2, intBlockX, intBlockY);
                }else if(strBlock.equals("e3")){
                    con.drawImage(imgEnemy3, intBlockX, intBlockY);
                }
                intBlockX += 30;
            }
            intBlockX = 0;
            intBlockY += 30;
        }
        con.repaint();
    }

    //Method for action logic
    public static int action(Console con, String strNextBlock, char chrKeyPressed, int intPlayerMovement, BufferedImage imgPlayer){
        //Loads in the Drowned Image
        BufferedImage imgDrowned = con.loadImage("Images/Other/Drowned.png");
        
        //Checks for Water on next block
        if(strNextBlock.equals("w")){
            con.drawImage(imgDrowned, 0, 0);
            blnContinue = false;

        //Checks for Building on the next block
        }else if(strNextBlock.equals("b")){
            if(intPlayerHealth < 100){
                intPlayerHealth += 10;
            }else{
                //Something here saying health maxed
            }

        //Checks for Enemies on the next block
        }else if(strNextBlock.equals("e1") || strNextBlock.equals("e2") || strNextBlock.equals("e3")){
            if(intPlayerHealth > 0){
                intPlayerHealth -= 10;
            }
        }

        //If the next block is anything but a tree, this will run
        if(!strNextBlock.equals("t") && !strNextBlock.equals("w")){
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
    public static boolean HUD_Display(Console con, boolean blnHUDActive, int intAnimationLoop, BufferedImage imgPlayer, int intPlayerX, int intPlayerY, int intCountColumns, int intCountRows, String strMap[][]){
        //Loads in the font for the display
        Font fntHUDTitle = con.loadFont("Fonts/HUD Title.ttf", 50);
        Font fntStats = con.loadFont("Fonts/Stats font.ttf",40);

        //Loads images for the HUD
        BufferedImage imgHealth = con.loadImage("Images/HUD Elements/Heart.png");
        BufferedImage imgDefence = con.loadImage("Images/HUD Elements/Shield.png");
        BufferedImage imgDamage = con.loadImage("Images/HUD Elements/Damage.png");

        //Integer variables to animate the HUD
        int intHUD_Y;
        int intBarWitdh;
        int intBarY;

        //Colors for the HUD
        Color clrBackground = new Color(114,6,6);
        Color clrHealth = new Color(2,168,41);
        Color clrDefence = new Color(0,0,204);
        Color clrDamage = new Color(255,51,51);

        //If the HUD is not currently active, this runs to pull it down
        if(!blnHUDActive){
            //Sets it to -300 initially to put it out of the screen
            intHUD_Y = -300;

            //Animates the HUD
            for(intAnimationLoop = 0; intAnimationLoop <= 300; intAnimationLoop += 20){
                //Redraws the map and player to get rid of the old HUD frame
                drawMap(con, intCountColumns, intCountRows, strMap);
                con.drawImage(imgPlayer, intPlayerX, intPlayerY);

                //Fixes lag issues in the console
                con.repaint();

                //Draws the HUD background and title
                con.setDrawColor(clrBackground);
                con.fillRect(0, intHUD_Y, 600, 300);
                con.setDrawColor(Color.white);
                con.setDrawFont(fntHUDTitle);
                con.drawString("HERO STATS", 175, intHUD_Y);

                //Draws the health bar based on the player's health stats
                con.setDrawColor(clrHealth);
                intBarWitdh = (int)(400.0*(intPlayerHealth/100.0));
                intBarY = intHUD_Y+90;
                con.fillRect(82,intBarY,intBarWitdh,31);
                con.setDrawColor(Color.black);
                drawRectangleOutline(con,79,intBarY-3,406,36,3);
                con.drawImage(imgHealth, 30, intBarY-7);
                con.setDrawColor(Color.white);
                con.setDrawFont(fntStats);
                con.drawString(Integer.toString(intPlayerHealth), 500, intBarY-20);

                //Draws the defence bar based on the player's defence stats
                con.setDrawColor(clrDefence);
                intBarWitdh = (int)(400.0*(intPlayerDefence/100.0));
                intBarY = intHUD_Y+150;
                con.fillRect(82,intBarY,intBarWitdh,31);
                con.setDrawColor(Color.black);
                drawRectangleOutline(con,79,intBarY-3,406,36,3);
                con.drawImage(imgDefence, 30, intBarY-6);
                con.setDrawColor(Color.white);
                con.setDrawFont(fntStats);
                con.drawString(Integer.toString(intPlayerDefence), 500, intBarY-20);

                //Draws the damage bar based on the player's damage stats
                con.setDrawColor(clrDamage);
                intBarWitdh = (int)(400.0*(intPlayerDamage/100.0));
                intBarY = intHUD_Y+210;
                con.fillRect(82,intBarY,intBarWitdh,31);
                con.setDrawColor(Color.black);
                drawRectangleOutline(con,79,intBarY-3,406,36,3);
                con.drawImage(imgDamage, 30, intBarY-3);
                con.setDrawColor(Color.white);
                con.setDrawFont(fntStats);
                con.drawString(Integer.toString(intPlayerDamage), 500, intBarY-20);

                //Gets ready for the next loop by adding 50 to the y and animates at 30 FPS
                intHUD_Y += 20;
                con.sleep(33);
                con.repaint();
            }

            //Returns true to set blnHUDActive to true
            return true;

        //If the HUD is currently active, this runs to pull it up
        }else{
            //Sets the HUD to Y initially
            intHUD_Y = 0;

            //Loops to animate the HUD
            for(intAnimationLoop = 0; intAnimationLoop <= 600; intAnimationLoop += 20){
                //Redraws the map and player to get rid of the old HUD frame
                drawMap(con, intCountColumns, intCountRows, strMap);
                con.drawImage(imgPlayer, intPlayerX, intPlayerY);

                //Fixes lag issues in the console
                con.repaint();

                //Draws the HUD background and title
                con.setDrawColor(clrBackground);
                con.fillRect(0, intHUD_Y, 600, 300);
                con.setDrawColor(Color.white);
                con.setDrawFont(fntHUDTitle);
                con.drawString("HERO STATS", 175, intHUD_Y);

                //Draws the health bar based on the player's health stats
                con.setDrawColor(clrHealth);
                intBarWitdh = (int)(400.0*(intPlayerHealth/100.0));
                intBarY = intHUD_Y+90;
                con.fillRect(82,intBarY,intBarWitdh,31);
                con.setDrawColor(Color.black);
                drawRectangleOutline(con,79,intBarY-3,406,36,3);
                con.drawImage(imgHealth, 30, intBarY-7);
                con.setDrawColor(Color.white);
                con.setDrawFont(fntStats);
                con.drawString(Integer.toString(intPlayerHealth), 500, intBarY-20);

                //Draws the defence bar based on the player's defence stats
                con.setDrawColor(clrDefence);
                intBarWitdh = (int)(400.0*(intPlayerDefence/100.0));
                intBarY = intHUD_Y+150;
                con.fillRect(82,intBarY,intBarWitdh,31);
                con.setDrawColor(Color.black);
                drawRectangleOutline(con,79,intBarY-3,406,36,3);
                con.drawImage(imgDefence, 30, intBarY-6);
                con.setDrawColor(Color.white);
                con.setDrawFont(fntStats);
                con.drawString(Integer.toString(intPlayerDefence), 500, intBarY-20);

                //Draws the damage bar based on the player's damage stats
                con.setDrawColor(clrDamage);
                intBarWitdh = (int)(400.0*(intPlayerDamage/100.0));
                intBarY = intHUD_Y+210;
                con.fillRect(82,intBarY,intBarWitdh,31);
                con.setDrawColor(Color.black);
                drawRectangleOutline(con,79,intBarY-3,406,36,3);
                con.drawImage(imgDamage, 30, intBarY-3);
                con.setDrawColor(Color.white);
                con.setDrawFont(fntStats);
                con.drawString(Integer.toString(intPlayerDamage), 500, intBarY-20);

                //Gets ready for the next loop by adding 50 to the y and animates at 30 FPS
                intHUD_Y -= 20;
                con.sleep(33);
                con.repaint();
            }

            //Returns false to set blnHUDActive to false
            return false;
        }
    }
}