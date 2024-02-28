/*
Soheil Rajabali
ICS4U1 RPG: Sus Sus Amogus
V7.0
*/

import arc.*;
import java.awt.*;
import java.awt.image.*;

public class RPG_Game{
    public static void main(String[] args){
        //Initializes the console
        Console con = new Console("Sus Sus Amogus: The Game", 600, 600);

        //Initializes inputs for selection
        int intMouseX = 0;
        int intMouseY = 0;
        int intMouseButtonClicked = 0;

        //Initializes string variables, defaults Map to Map 1
        String strChoice;
        String strMap = "Map 1";

        boolean blnPlay = false;

        //Loops until the user decides to play
        while(!blnPlay){
            //Gets their button choice
            strChoice = GameMenu(con, intMouseX, intMouseY, intMouseButtonClicked);

            //Runs the Map Select screen
            if(strChoice.equals("Select Map")){
                con.sleep(300);
                strMap = mapSelect(con, intMouseX, intMouseY, intMouseButtonClicked);
                con.setDrawColor(Color.black);
                con.fillRect(0, 0, 600, 600);
                con.repaint();

            //Runs the help screen
            }else if(strChoice.equals("Help")){

            //Quits the console
            }else if(strChoice.equals("Quit")){
                con.closeConsole();
            
            //Plays the game
            }else{
                blnPlay = true;
            }
        }

        //Runs the game
        Play.Game(con, strMap);
    }

    //Method to display the menu
    public static String GameMenu(Console con, int intMouseX, int intMouseY, int intMouseButtonClicked){
        //Loads the menu fonts into the game
        Font fntTitle = con.loadFont("Fonts/Menu Title.ttf", 100);
        Font fntButtons = con.loadFont("Fonts/Menu Options.otf", 75);
        
        //Sets the draw color to White
        con.setDrawColor(Color.white);

        //Draws the Title for the Game
        con.setDrawFont(fntTitle);
        con.drawString("The Great RPG", 23, 30);

        //Sets the font to draw the buttons
        con.setDrawFont(fntButtons);

        //Draws the buttons for the game
        while(true){
            //Gets Mouse inputs
            intMouseX = con.currentMouseX();
            intMouseY = con.currentMouseY();
            intMouseButtonClicked = con.currentMouseButton();

            //Hover animation for Play button
            if(((intMouseX < 390) && (intMouseX > 225)) && ((intMouseY < 265) && (intMouseY > 210))){
                con.setDrawColor(Color.red);
                con.drawString("Play", 225, 190);
                con.setDrawColor(Color.white);
                con.drawString("Select Map", 100, 280);
                con.drawString("Help", 225, 370);
                con.drawString("Quit", 225, 460);

                //If Play is clicked, returns "Play"
                if(intMouseButtonClicked == 1){
                    return("Play");
                }
            
            //Hover animation for Map Select Button
            }else if(((intMouseX < 500) && (intMouseX > 100)) && ((intMouseY < 355) && (intMouseY > 300))){
                con.setDrawColor(Color.red);
                con.drawString("Select Map", 100, 280);
                con.setDrawColor(Color.white);
                con.drawString("Play", 225, 190);
                con.drawString("Help", 225, 370);
                con.drawString("Quit", 225, 460);

                //If Select Map is clicked, returns "Select Map"
                if(intMouseButtonClicked == 1){
                    return("Select Map");
                }
            
            //Hover animation for Help Button
            }else if(((intMouseX < 390) && (intMouseX > 220)) && ((intMouseY < 445) && (intMouseY > 390))){
                con.setDrawColor(Color.red);
                con.drawString("Help", 225, 370);
                con.setDrawColor(Color.white);
                con.drawString("Play", 225, 190);
                con.drawString("Select Map", 100, 280);
                con.drawString("Quit", 225, 460);

                //If Help is clicked, returns "Help"
                if(intMouseButtonClicked == 1){
                    return("Help");
                }
            
            //Hover animation for Quit Button
            }else if(((intMouseX < 380) && (intMouseX > 220)) && ((intMouseY < 540) && (intMouseY > 480))){
                con.setDrawColor(Color.red);
                con.drawString("Quit", 225, 460);
                con.setDrawColor(Color.white);
                con.drawString("Play", 225, 190);
                con.drawString("Select Map", 100, 280);
                con.drawString("Help", 225, 370);

                //If Quit is clicked, returns "Quit"
                if(intMouseButtonClicked == 1){
                    return("Quit");
                }

            //If no button is hovered, this runs
            }else{
                con.setDrawColor(Color.white);
                con.drawString("Play", 225, 190);
                con.drawString("Select Map", 100, 280);
                con.drawString("Help", 225, 370);
                con.drawString("Quit", 225, 460);
            }

            //Repaints and animates the console at 30 fps
            con.repaint();
            con.sleep(33);
        }
    }

    //Method to display the map selection
    public static String mapSelect(Console con, int intMouseX, int intMouseY, int intMouseButtonClicked){
        //Initializes integer variable to count cycles
        int intCycleCounter = 0;

        //Initializes images and fonts
        BufferedImage imgMapSelectBackground = con.loadImage("Images/Map Select/Map Select Screen.png");
        BufferedImage imgMap1 = con.loadImage("Images/Map Select/Map 1.png");

        Font fntMapText = con.loadFont("Fonts/Map Select Font.otf", 50);

        //Sets the draw font to the map text font
        con.setDrawFont(fntMapText);
        
        //Loops until user selects a map
        while(true){
            //Gets Mouse inputs
            intMouseX = con.currentMouseX();
            intMouseY = con.currentMouseY();
            intMouseButtonClicked = con.currentMouseButton();

            //Draws the background
            con.drawImage(imgMapSelectBackground, 0, 0);

            //If statement for the left cycle button
            if(((intMouseX < 55) && (intMouseX > 15)) && ((intMouseY < 280) && (intMouseY > 240)) && intMouseButtonClicked == 1){
                intCycleCounter--;
            
            //If statement for the right cycle button
            }else if(((intMouseX < 585) && (intMouseX > 550)) && ((intMouseY < 280) && (intMouseY > 240)) && intMouseButtonClicked == 1){
                intCycleCounter++;
            }

            //If statement for what maps to display
            if(intCycleCounter%4 == 0){
                con.drawImage(imgMap1, 70, 160);
                con.drawString("Map 1", 130, 350);
                con.drawImage(imgMap1, 330, 160);
                con.drawString("Map 2", 385, 350);
            }else if((intCycleCounter%4 == 1) || (intCycleCounter%4 == -3)){
                con.drawImage(imgMap1, 70, 160);
                con.drawString("Map 2", 130, 350);
                con.drawImage(imgMap1, 330, 160);
                con.drawString("Map 3", 385, 350);
            }else if((intCycleCounter%4 == 2) || (intCycleCounter%4 == -2)){
                con.drawImage(imgMap1, 70, 160);
                con.drawString("Map 3", 130, 350);
                con.drawImage(imgMap1, 330, 160);
                con.drawString("Map 4", 385, 350);
            }else if((intCycleCounter%4 == 3) || (intCycleCounter%4 == -1)){
                con.drawImage(imgMap1, 70, 160);
                con.drawString("Map 4", 130, 350);
                con.drawImage(imgMap1, 330, 160);
                con.drawString("Map 1", 385, 350);
            }

             //Decides what map to return
             if(intCycleCounter%4 == 0){
                if(((intMouseX < 270) && (intMouseX > 70)) && ((intMouseY < 360) && (intMouseY > 160)) && intMouseButtonClicked == 1){
                    return "Map 1";
                }else if(((intMouseX < 530) && (intMouseX > 330)) && ((intMouseY < 360) && (intMouseY > 160)) && intMouseButtonClicked == 1){
                    return "Map 2";
                }
            }else if((intCycleCounter%4 == 1) || (intCycleCounter%4 == -3)){
                if(((intMouseX < 270) && (intMouseX > 70)) && ((intMouseY < 360) && (intMouseY > 160)) && intMouseButtonClicked == 1){
                    return "Map 2";
                }else if(((intMouseX < 530) && (intMouseX > 330)) && ((intMouseY < 360) && (intMouseY > 160)) && intMouseButtonClicked == 1){
                    return "Map 3";
                }
            }else if((intCycleCounter%4 == 2) || (intCycleCounter%4 == -2)){
                if(((intMouseX < 270) && (intMouseX > 70)) && ((intMouseY < 360) && (intMouseY > 160)) && intMouseButtonClicked == 1){
                    return "Map 3";
                }else if(((intMouseX < 530) && (intMouseX > 330)) && ((intMouseY < 360) && (intMouseY > 160)) && intMouseButtonClicked == 1){
                    return "Map 4";
                }
            }else if((intCycleCounter%4 == 3) || (intCycleCounter%4 == -1)){
                if(((intMouseX < 270) && (intMouseX > 70)) && ((intMouseY < 360) && (intMouseY > 160)) && intMouseButtonClicked == 1){
                    return "Map 4";
                }else if(((intMouseX < 530) && (intMouseX > 330)) && ((intMouseY < 360) && (intMouseY > 160)) && intMouseButtonClicked == 1){
                    return "Map 1";
                }
            }

            //Paints all maps and delays so multiple clicks dont register
            con.repaint();
            con.sleep(100);
        }
    }
}