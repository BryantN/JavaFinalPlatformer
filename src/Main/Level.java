package Main;

import javax.xml.transform.Source;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Bryant on 11/14/2014.
 */
public class Level {

    private LinkedList<Platform> Platforms= new LinkedList<Platform>();
    private LinkedList<Interactable> PowerupLoc=new LinkedList<Interactable>();
    private LinkedList<Interactable> PowerupsLeft= new LinkedList<Interactable>();
    private LinkedList<Rectangle> Checkpoints=new LinkedList<Rectangle>();
    private String levelName;
    private Point playerSpawn;
    private Rectangle goal;
    private Rectangle inBounds;
    private boolean normgrav=true;
    private boolean cleared=false;
    private boolean gameCleared;

    Level(String source){
        //default Level
        readfromFile(source);

    }

    public void readfromFile(String source) {
        Scanner fin=null;
        int x,y,width,hieght,numPlatforms,numcoins,numjuppads,numgravswitch,numcheckpoints;

        try {
            fin=new Scanner(new File(source));
        } catch (FileNotFoundException e) {
            gameCleared=true;
            return;
        }
        gameCleared=false;
        levelName=fin.nextLine();
        Platforms.clear();
        x=fin.nextInt();
        y=fin.nextInt();
        playerSpawn=new Point(x,y);
        numPlatforms=fin.nextInt();
        numcoins=fin.nextInt();
        numjuppads=fin.nextInt();
        numgravswitch=fin.nextInt();
        numcheckpoints=fin.nextInt();
        while (numcoins>0||numPlatforms>0||numjuppads>0||numgravswitch>0||numcheckpoints>0){

            x=fin.nextInt();
            y=fin.nextInt();

            if(numPlatforms>0) {
                width=fin.nextInt();
                hieght=fin.nextInt();
                Platforms.add(new Platform(new Rectangle(x, y, width, hieght)));
                --numPlatforms;
            }
            else if(numcoins>0){
                PowerupsLeft.add(new Coin(x,y));
                PowerupLoc.add(new Coin(x,y));
                --numcoins;
            } else if(numjuppads>0) {
                width=fin.nextInt();
                hieght=fin.nextInt();
                Platforms.add(new JumpPad(new Rectangle(x, y, width, hieght)));
                --numjuppads;
            } else if(numgravswitch>0){
                PowerupsLeft.add(new GravSwitch(x,y));
                PowerupLoc.add(new GravSwitch(x,y));
                --numgravswitch;
            }else {
                Checkpoints.add(new Rectangle(x,y,25,75));
                --numcheckpoints;
            }
        }
        x=fin.nextInt();
        y=fin.nextInt();
        width=fin.nextInt();
        hieght=fin.nextInt();
        goal=new  Rectangle(x,y,width,hieght);
        x=fin.nextInt();
        y=fin.nextInt();
        width=fin.nextInt();
        hieght=fin.nextInt();
        inBounds=new  Rectangle(x,y,width,hieght);
        fin.close();
    }



    public void levelcleared(){
        cleared=true;
    }
    public Rectangle getInBounds(){
        return inBounds;
    }

    public boolean isCleared(){return cleared;}
    public LinkedList<Platform> getPlatforms() {
        return Platforms;
    }
    public synchronized LinkedList<Interactable> getPowerups() {
        return PowerupsLeft;
    }
    public LinkedList<Rectangle> getCheckpoints(){return Checkpoints;}

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public Rectangle getGoal() {
        return goal;
    }

    public boolean isGameCleared() {
        return gameCleared;
    }
    public void setCurrCheckpoint(int x,int y,boolean normGrav){
        playerSpawn=new Point(x,y);
        PowerupLoc=new LinkedList<Interactable>();
        normgrav=normGrav;
        for(Interactable i:PowerupsLeft)
            PowerupLoc.add(i);
    }
    public boolean getGrav(){return normgrav;}
    public Point Respawn(){
        PowerupsLeft=new LinkedList<Interactable>();
        for(Interactable i:PowerupLoc)
            PowerupsLeft.add(i);
        return playerSpawn;
    }

    public String getLevelName() {
        return levelName;
    }
}
