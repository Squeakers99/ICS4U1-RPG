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

import java.awt.*;
import java.awt.image.*;

public class BattleAnimations{
    //Method to clear the console
    public static void clear(){
        RPG_Game.con.setDrawColor(Color.black);
        RPG_Game.con.fillRect(0, 0, 600, 600);
    }
    
    //Method to animate the enemy weapon hitting the player
    public static void enemyAnimation(int intEnemyLevel, int intFirstDown, int intRight, int intSecondDown){
        //Defines integer variables
        int intAnimationLoop = 0;
        int intHealthLost;

        //Loads images
        BufferedImage imgEnemy = RPG_Game.con.loadImage("Images/Battle Animation/Enemy " + intEnemyLevel + "/Enemy " + intEnemyLevel + ".png");
        BufferedImage imgPlayer = RPG_Game.con.loadImage("Images/Battle Animation/Player/Player.png");
        BufferedImage imgEnemy3Weaponless = RPG_Game.con.loadImage("Images/Battle Animation/Enemy " + intEnemyLevel + "/Enemy " + intEnemyLevel + " Weaponless.png");
        BufferedImage imgEnemy3Weapon = RPG_Game.con.loadImage("Images/Battle Animation/Enemy " + intEnemyLevel + "/Enemy " + intEnemyLevel + " Weapon.png");

        //Sets up the animation
        enterance(3, imgEnemy, imgPlayer, intAnimationLoop);
        drawStats(intEnemyLevel);

        //Paints the stats
        RPG_Game.con.repaint();

        //Pauses the console before the animation begins
        RPG_Game.con.sleep(500);

        //Loops that animate the weapon hitting the player
        for(intAnimationLoop = 0; intAnimationLoop <= intFirstDown; intAnimationLoop += 5) {
            clear();
            RPG_Game.con.drawImage(imgEnemy3Weaponless, 24, 0);
            RPG_Game.con.drawImage(imgPlayer, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgEnemy3Weapon, 24, intAnimationLoop);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }

        for(intAnimationLoop = 24; intAnimationLoop <= intRight; intAnimationLoop += 6){
            clear();
            RPG_Game.con.drawImage(imgEnemy3Weaponless, 24, 0);
            RPG_Game.con.drawImage(imgPlayer, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgEnemy3Weapon, intAnimationLoop, 170);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }

        for(intAnimationLoop = intFirstDown; intAnimationLoop <= intSecondDown; intAnimationLoop += 5){
            clear();
            RPG_Game.con.drawImage(imgEnemy3Weaponless, 24, 0);
            RPG_Game.con.drawImage(imgPlayer, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgEnemy3Weapon, intRight, intAnimationLoop);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }

        //Subtracts the players stats and repaints them
        intHealthLost = (int)(Play.intEnemyStats[intEnemyLevel-1][2] - ((Play.intPlayerStats[1]/100.0) * Play.intEnemyStats[intEnemyLevel-1][2]));
        Play.intPlayerStats[0] -= intHealthLost;

        if(Play.intPlayerStats[0] < 0){
            Play.intPlayerStats[0] = 0;
        }
        if(Play.intPlayerStats[1] > 0){
            Play.intPlayerStats[1] -= 5;
        }

        //Repaints the enemy stats
        RPG_Game.con.sleep(1000);
        clear();
        RPG_Game.con.drawImage(imgEnemy3Weaponless, 24, 0);
        RPG_Game.con.drawImage(imgPlayer, 455, 430);
        RPG_Game.con.drawImage(imgEnemy3Weapon, intRight, intSecondDown);
        drawStats(intEnemyLevel);
        RPG_Game.con.repaint();
        RPG_Game.con.sleep(1000);

        //Animates the weapon going back to the enemy
        for(intAnimationLoop = intSecondDown; intAnimationLoop >= intFirstDown; intAnimationLoop -= 5){
            clear();
            RPG_Game.con.drawImage(imgEnemy3Weaponless, 24, 0);
            RPG_Game.con.drawImage(imgPlayer, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgEnemy3Weapon, intRight, intAnimationLoop);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }
        
        for(intAnimationLoop = intRight; intAnimationLoop >= 24; intAnimationLoop -= 6){
            clear();
            RPG_Game.con.drawImage(imgEnemy3Weaponless, 24, 0);
            RPG_Game.con.drawImage(imgPlayer, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgEnemy3Weapon, intAnimationLoop, 170);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }

        for(intAnimationLoop = intFirstDown; intAnimationLoop >= 0; intAnimationLoop -= 5) {
            clear();
            RPG_Game.con.drawImage(imgEnemy3Weaponless, 24, 0);
            RPG_Game.con.drawImage(imgPlayer, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgEnemy3Weapon, 24, intAnimationLoop);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }
    }

    //Method to animate the player weapon hitting the enemy
    public static void playerAnimation(int intEnemyLevel, int intLeft){
        //Defines integer variables
        int intAnimationLoop;
        int intCount;
        
        //Loads Images
        BufferedImage imgEnemy = RPG_Game.con.loadImage("Images/Battle Animation/Enemy " + intEnemyLevel + "/Enemy " + intEnemyLevel + ".png");
        BufferedImage imgPlayerBackpackless = RPG_Game.con.loadImage("Images/Battle Animation/Player/Player Backpackless.png");
        BufferedImage imgPlayerBackpack = RPG_Game.con.loadImage("Images/Battle Animation/Player/Player Backpack.png");
        BufferedImage imgBullet = RPG_Game.con.loadImage("Images/Battle Animation/Player/Bullet.png");
        
        //Animates the backback going to the enemy
        for(intAnimationLoop = 430; intAnimationLoop >= 150; intAnimationLoop -= 5){
            clear();
            RPG_Game.con.drawImage(imgEnemy, 24, 0);
            RPG_Game.con.drawImage(imgPlayerBackpackless, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgPlayerBackpack, 455, intAnimationLoop);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }
        
        for(intAnimationLoop = 455; intAnimationLoop >= intLeft; intAnimationLoop -= 6){
            clear();
            RPG_Game.con.drawImage(imgEnemy, 24, 0);
            RPG_Game.con.drawImage(imgPlayerBackpackless, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgPlayerBackpack, intAnimationLoop, 150);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }

        //Animates 3 bullets hitting the enemy
        for(intCount = 0; intCount < 3; intCount++){
            for(intAnimationLoop = 150; intAnimationLoop >= 0; intAnimationLoop -= 30){
                clear();
                RPG_Game.con.drawImage(imgBullet, intLeft, intAnimationLoop);
                RPG_Game.con.drawImage(imgEnemy, 24, 0);
                RPG_Game.con.drawImage(imgPlayerBackpackless, 455, 430);
                drawStats(intEnemyLevel);
                RPG_Game.con.drawImage(imgPlayerBackpack, intLeft, 150);
                RPG_Game.con.repaint();
                RPG_Game.con.sleep(16);
            }
        }
        
        //Subtracts the enemy's stats and repaints them
        Play.intEnemyStats[intEnemyLevel-1][0] = 0;
        
        //Animates the backback going back to the player
        for(intAnimationLoop = intLeft; intAnimationLoop <= 455; intAnimationLoop += 6){
            clear();
            RPG_Game.con.drawImage(imgEnemy, 24, 0);
            RPG_Game.con.drawImage(imgPlayerBackpackless, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgPlayerBackpack, intAnimationLoop, 150);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }
        
        for(intAnimationLoop = 150; intAnimationLoop <= 430; intAnimationLoop += 5){
            clear();
            RPG_Game.con.drawImage(imgEnemy, 24, 0);
            RPG_Game.con.drawImage(imgPlayerBackpackless, 455, 430);
            drawStats(intEnemyLevel);
            RPG_Game.con.drawImage(imgPlayerBackpack, 455, intAnimationLoop);
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }
    }

    //Method to enter the player and enemy into the map
    public static void enterance(int intEnemyLevel, BufferedImage imgEnemy, BufferedImage imgPlayer, int intAnimationLoop) {
        //Defines integer variables
        int intPlayerX = 600;
        int intEnemyX = -150;

        //Clears the console and delays it
        clear();

        //Sleeps the console for 500 milliseconds
        RPG_Game.con.sleep(500);

        //Loops to animate the enterance into the map
        for(intAnimationLoop = 0; intAnimationLoop < 30; intAnimationLoop++) {
            clear();
            RPG_Game.con.drawImage(imgPlayer, intPlayerX, 430);
            RPG_Game.con.drawImage(imgEnemy, intEnemyX, 0);
            intPlayerX -= 5;
            intEnemyX += 6;
            RPG_Game.con.repaint();
            RPG_Game.con.sleep(16);
        }
    }

    //Draws Enemy and Player Stats
    public static void drawStats(int intEnemyLevel){
        //Defines the font variable
        Font fntStats = RPG_Game.con.loadFont("Fonts/Stats font.ttf", 40);

        //Defines variables to draw enemy and player stats
        int intBarWidth;

        //Loads images to draw enemy and player stats
        BufferedImage imgShield = RPG_Game.con.loadImage("Images/HUD Elements/Shield.png");
        BufferedImage imgDamage = RPG_Game.con.loadImage("Images/HUD Elements/Damage.png");
        BufferedImage imgHealth = RPG_Game.con.loadImage("Images/HUD Elements/Heart.png");

        //Colors for the bars
        Color clrHealth = new Color(2,168,41);
        Color clrDefence = new Color(0,0,204);
        Color clrDamage = new Color(255,51,51);

        //Draws the player's health bar based on the player's health stats
        RPG_Game.con.setDrawColor(clrHealth);
        intBarWidth = (int)(300.0*(Play.intPlayerStats[0]/100.0));
        RPG_Game.con.fillRect(85,453,intBarWidth,31);
        RPG_Game.con.setDrawColor(Color.white);
        Play.drawRectangleOutline(82,450,306,36,3);
        RPG_Game.con.drawImage(imgHealth, 30, 450);
        RPG_Game.con.setDrawColor(Color.white);
        RPG_Game.con.setDrawFont(fntStats);
        RPG_Game.con.drawString(Integer.toString(Play.intPlayerStats[0]), 400, 435);

        //Draws the player's defence bar based on the player's defence stats
        RPG_Game.con.setDrawColor(clrDefence);
        intBarWidth = (int)(300.0*(Play.intPlayerStats[1]/100.0));
        RPG_Game.con.fillRect(85,503,intBarWidth,31);
        RPG_Game.con.setDrawColor(Color.white);
        Play.drawRectangleOutline(82,500,306,36,3);
        RPG_Game.con.drawImage(imgShield, 30, 500);
        RPG_Game.con.setDrawColor(Color.white);
        RPG_Game.con.setDrawFont(fntStats);
        RPG_Game.con.drawString(Integer.toString(Play.intPlayerStats[1]), 400, 485);

        //Draws the player's damage bar based on the player's damage stats
        RPG_Game.con.setDrawColor(clrDamage);
        intBarWidth = (int)(300.0*(Play.intPlayerStats[2]/100.0));
        RPG_Game.con.fillRect(85,553,intBarWidth,31);
        RPG_Game.con.setDrawColor(Color.white);
        Play.drawRectangleOutline(82,550,306,36,3);
        RPG_Game.con.drawImage(imgDamage, 30, 550);
        RPG_Game.con.setDrawColor(Color.white);
        RPG_Game.con.setDrawFont(fntStats);
        RPG_Game.con.drawString(Integer.toString(Play.intPlayerStats[2]), 400, 535);

        //Draws the enemy's health bar based on the enemy's health stats
        RPG_Game.con.setDrawColor(clrHealth);
        intBarWidth = (int)(300.0*(Play.intEnemyStats[intEnemyLevel-1][0]/100.0));
        RPG_Game.con.fillRect(225,13,intBarWidth,31);
        RPG_Game.con.setDrawColor(Color.white);
        Play.drawRectangleOutline(222,10,306,36,3);
        RPG_Game.con.drawImage(imgHealth, 170, 10);
        RPG_Game.con.setDrawColor(Color.white);
        RPG_Game.con.setDrawFont(fntStats);
        RPG_Game.con.drawString(Integer.toString(Play.intEnemyStats[intEnemyLevel-1][0]), 540, -5);

        //Draws the enemy's defence bar based on the enemy's defence stats
        RPG_Game.con.setDrawColor(clrDefence);
        intBarWidth = (int)(300.0*(Play.intEnemyStats[intEnemyLevel-1][1]/100.0));
        RPG_Game.con.fillRect(225,63,intBarWidth,31);
        RPG_Game.con.setDrawColor(Color.white);
        Play.drawRectangleOutline(222,60,306,36,3);
        RPG_Game.con.drawImage(imgShield, 170, 60);
        RPG_Game.con.setDrawColor(Color.white);
        RPG_Game.con.setDrawFont(fntStats);
        RPG_Game.con.drawString(Integer.toString(Play.intEnemyStats[intEnemyLevel-1][1]), 540, 45);

        //Draws the enemy's damage bar based on the enemy's damage stats
        RPG_Game.con.setDrawColor(clrDamage);
        intBarWidth = (int)(300.0*(Play.intEnemyStats[intEnemyLevel-1][2]/100.0));
        RPG_Game.con.fillRect(225,113,intBarWidth,31);
        RPG_Game.con.setDrawColor(Color.white);
        Play.drawRectangleOutline(222,110,306,36,3);
        RPG_Game.con.drawImage(imgDamage, 170, 110);
        RPG_Game.con.setDrawColor(Color.white);
        RPG_Game.con.setDrawFont(fntStats);
        RPG_Game.con.drawString(Integer.toString(Play.intEnemyStats[intEnemyLevel-1][2]), 540, 95);
    }

    //Method to animate the full enemy 1 animation
    public static void enemy1Animation(){
        //Animates the enemy and player if player health is above 0
        enemyAnimation(1, 170, 360, 385);
        if(Play.intPlayerStats[0] > 0){
            playerAnimation(1, -24);
        }else{
            Play.blnContinue = false;
        }

        //Resets the enemy health
        Play.intEnemyStats[0][0] = 20;
        RPG_Game.con.sleep(500);
    }

    //Method to animate the full enemy 2 animation
    public static void enemy2Animation(){
        //Animates the enemy and player if player health is above 0
        enemyAnimation(2, 170, 410, 385);
        if(Play.intPlayerStats[0] > 0){
            playerAnimation(2, -24);
        }else{
            Play.blnContinue = false;
        }

        //Resets the enemy health
        Play.intEnemyStats[1][0] = 30;
        RPG_Game.con.sleep(500);
    }

    //Method to animate the full enemy 3 animation
    public static void enemy3Animation(){
        //Animates the enemy and player if player health is above 0
        enemyAnimation(3, 170, 390, 385);
        if(Play.intPlayerStats[0] > 0){
            playerAnimation(3, -24);
        }else{
            Play.blnContinue = false;
        }

        //Resets the enemy health
        Play.intEnemyStats[2][0] = 40;
        RPG_Game.con.sleep(500);
    }
}