import arc.*;
import java.awt.*;
import java.awt.image.*;

public class Play {
    //Creates variables to be accessible by all methods
    private static int intHealth = 50;
    private static boolean boolContinue = true;

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
        int intAnimationLoop;

        int intPlayerDefence = 20;
        int intPlayerDamage = 10;

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
        while(boolContinue){
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

                    }
                }
                //Redraws the console
                con.repaint();
            
            //If an array error is caught, the player cannot go there because it is outside the map. It does nothing
            }catch(Exception ArrayIndexOutOfBounds){}
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
            boolContinue = false;

        //Checks for Building on the next block
        }else if(strNextBlock.equals("b")){
            /*
            if(intHealth < 100){
                intHealth += 10;
            }else{
                Something here saying health maxed
            }
            */

        //Checks for Enemies on the next block
        }else if(strNextBlock.equals("e1") || strNextBlock.equals("e2") || strNextBlock.equals("e3")){
            /*
            if(intHealth > 0){
                intHealth -= 10;
            }
            */
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
}