import arc.*;
import java.awt.*;
import java.awt.image.*;

public class Play {
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

        //Initializes all character variables
        char chrKeyPressed;

        //Initializes the chosen map
        TextInputFile txtMap = new TextInputFile("Maps/" + strMapChoice + ".csv");

        //Initializes all pictures required for play
        BufferedImage imgPlayer = con.loadImage("Images/Avatars/Player.png");
        //BufferedImage imgDrowned = con.loadImage("Images/Other/Drowned.png");

        //Loads the array with the chosen map
        for(intCountRows = 0; intCountRows < 20; intCountRows++){
            strFileLine = txtMap.readLine();
            strFileSplit = strFileLine.split(",");
            for(intCountColumns = 0; intCountColumns < 20; intCountColumns++){
                strMap[intCountRows][intCountColumns] = strFileSplit[intCountColumns];
            }
        }

        //Calls the method to draw the map
        drawMap(con, intCountColumns, intCountRows, strMap);

        //Loops to get WASD Player Movement
        while(true){
            //Gets the current key pressed
            chrKeyPressed = con.currentChar();

            //Check for movement
            if((chrKeyPressed == 'w')  || (chrKeyPressed == 'a') || (chrKeyPressed == 's') || (chrKeyPressed == 'd')){
                //If up, this runs
                if(chrKeyPressed == 'w'){
                    //Gets the next block the user is trying to move to
                    strNextBlock = strMap[intPlayerRow - 1][intPlayerCol];

                    //Calls the action method to get the new row position
                    intPlayerRow = action(con, strNextBlock, chrKeyPressed, intPlayerRow);

                //If down, this runs
                }else if(chrKeyPressed == 's'){
                    //Gets the next block the user is trying to move to
                    strNextBlock = strMap[intPlayerRow + 1][intPlayerCol];

                    //Calls the action method to get the new row position
                    intPlayerRow = action(con, strNextBlock, chrKeyPressed, intPlayerRow);

                //If left, this runs
                }else if(chrKeyPressed == 'a'){
                    //Gets the next block the user is trying to move to
                    strNextBlock = strMap[intPlayerRow][intPlayerCol - 1];

                    //Calls the action method to get the new column position
                    intPlayerCol = action(con, strNextBlock, chrKeyPressed, intPlayerCol);
                
                //If right, this runs
                }else if(chrKeyPressed == 'd'){
                    //Gets the next block the user is trying to move to
                    strNextBlock = strMap[intPlayerRow][intPlayerCol + 1];
                    
                    //Calls the action method to get the new column position
                    intPlayerCol = action(con, strNextBlock, chrKeyPressed, intPlayerCol);
                }
                con.sleep(200);

                //Redraws the map
                drawMap(con, intCountColumns, intCountRows, strMap);
            }

            //Draws the player
            con.drawImage(imgPlayer, intPlayerCol*30, intPlayerRow*30);
            con.repaint();
            con.sleep(33);
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
        //BufferedImage imgEnemy2 = con.loadImage("Images/Avatars/Enemy 2.png");
        //BufferedImage imgEnemy3 = con.loadImage("Images/Avatars/Enemy 3.png");

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
                    //con.drawImage(imgEnemy2, intBlockX, intBlockY);
                }else if(strBlock.equals("e3")){
                    //con.drawImage(imgEnemy3, intBlockX, intBlockY);
                }
                intBlockX += 30;
            }
            intBlockX = 0;
            intBlockY += 30;
        }
        con.repaint();
    }

    //Method for action logic
    public static int action(Console con, String strNextBlock, char chrKeyPressed, int intPlayerMovement){
        //Checks for Water on next block
        if(strNextBlock.equals("w")){
            //con.drawImage(imgDrowned, 0, 0);
        
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
        if(!strNextBlock.equals("t")){
            //Moves row/column minus one if its 'w' or 'a'
            if(chrKeyPressed == 'w' || chrKeyPressed == 'a'){
                intPlayerMovement -= 1;
        
            //Moves row/column plus one if its 's' or 'd'
            }else if(chrKeyPressed == 's' || chrKeyPressed == 'd'){
                intPlayerMovement += 1;
            }
        }

        //Returns the movement, whether it be row or column
        return intPlayerMovement;
    }
}
