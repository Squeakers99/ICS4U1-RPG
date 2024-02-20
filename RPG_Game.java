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

        //Initializes string variables
        String strChoice;

        boolean boolPlay = false;

        while(!boolPlay){
            strChoice = GameMenu(con);
            
            if(strChoice.equals("Select Map")){
                
            }else if(strChoice.equals("Help")){

            }else if(strChoice.equals("Quit")){
                con.closeConsole();
            }else{
                boolPlay = true;
            }
        }

        Play.Game(con, "Map 1");
    }

    //Method to display the menu
    public static String GameMenu(Console con){
        //Loads the menu fonts into the game
        Font fntTitle = con.loadFont("Fonts/Menu Title.ttf", 100);
        Font fntButtons = con.loadFont("Fonts/Menu Options.otf", 75);

        //Initializes inputs for selection
        int intMouseX;
        int intMouseY;
        int intMouseButtonClicked;
        
        //Draws the Title for the Game
        con.setDrawFont(fntTitle);
        con.drawString("The Great RPG", 23, 30);

        con.repaint();

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
}