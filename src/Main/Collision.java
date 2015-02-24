package Main;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Bryant on 11/6/2014.
 */


public class Collision {

    Collision(){

    }

    //credit to http://katyscode.wordpress.com/2013/01/18/2d-platform-games-collision-detection-for-dummies/
    //for helping solve this part of the code,
    //I changed my method from the four corner detection, to the 8 point method and it works perfectly
    //problems with this, possible to clip through objects on corners and center of player, good level design will offset this flaw
    public void FixCollision(Player player,LinkedList<Platform> hitboxes){

        double xpos= player.getX(),ypos=player.getY();
        for(Platform i:hitboxes) {
            boolean ct=false,cb=false,cl=false,cr=false;


            if(i.contains(player.cPoints[0].getX()+player.getX(),player.cPoints[0].getY()+player.getY())
                    ||i.contains(player.cPoints[1].getX()+player.getX(),player.cPoints[1].getY()+player.getY())
                    )
                ct=true;
            if(i.contains(player.cPoints[2].getX()+player.getX(),player.cPoints[2].getY()+player.getY())
                    ||i.contains(player.cPoints[3].getX()+player.getX(),player.cPoints[3].getY()+player.getY()))
                cl=true;
            if(i.contains(player.cPoints[4].getX()+player.getX(),player.cPoints[4].getY()+player.getY())
                    ||i.contains(player.cPoints[5].getX()+player.getX(),player.cPoints[5].getY()+player.getY()))
                cr=true;
            if(i.contains(player.cPoints[6].getX()+player.getX(),player.cPoints[6].getY()+player.getY())
                    ||i.contains(player.cPoints[7].getX()+player.getX(),player.cPoints[7].getY()+player.getY()))
                cb=true;
            //check down collision
            if(cb){
                ypos=i.getY()-player.getHeight();
                player.setYSpeed(0);
                player.resetJump();
                player.modjump(i.getJumpMod());
            }
            //check up collision
            else if(ct){
                ypos=i.getMaxY();
                player.setYSpeed(1);
            }
            //check left collision
            if(cl&&!cr){
                xpos=i.getMaxX();
            }
            //check right collision
            else if(cr&&!cl){
                xpos=i.getX()-player.getWidth();
            }
            player.setLocation((int) xpos, (int) ypos);
        }
    }

    public void FixCollisionReverseGrav(Player player,LinkedList<Platform> hitboxes){

        double xpos= player.getX(),ypos=player.getY();
        for(Platform i:hitboxes) {
            boolean ct=false,cb=false,cl=false,cr=false;


            if(i.contains(player.cPoints[0].getX()+player.getX(),player.cPoints[0].getY()+player.getY())
                    ||i.contains(player.cPoints[1].getX()+player.getX(),player.cPoints[1].getY()+player.getY())
                    )
                ct=true;
            if(i.contains(player.cPoints[2].getX()+player.getX(),player.cPoints[2].getY()+player.getY())
                    ||i.contains(player.cPoints[3].getX()+player.getX(),player.cPoints[3].getY()+player.getY()))
                cl=true;
            if(i.contains(player.cPoints[4].getX()+player.getX(),player.cPoints[4].getY()+player.getY())
                    ||i.contains(player.cPoints[5].getX()+player.getX(),player.cPoints[5].getY()+player.getY()))
                cr=true;
            if(i.contains(player.cPoints[6].getX()+player.getX(),player.cPoints[6].getY()+player.getY())
                    ||i.contains(player.cPoints[7].getX()+player.getX(),player.cPoints[7].getY()+player.getY()))
                cb=true;
            //check down collision
            if(cb){
                ypos=i.getY()-player.getHeight();
                player.setYSpeed(-1);
            }
            //check up collision
            else if(ct&&!(cr^cl)){
                ypos=i.getMaxY();
                player.setYSpeed(0);
                player.resetJump();
                player.modjump(i.getJumpMod());
            }
            //check left collision
            if(cl&&!cr){
                xpos=i.getMaxX();
            }
            //check right collision
            else if(cr&&!cl){
                xpos=i.getX()-player.getWidth();
            }
            player.setLocation((int) xpos, (int) ypos);
        }
    }


    public void Checkinteraction(Player player,LinkedList<Interactable> powerups){
        int toRemove=-1;
        for(Interactable i:powerups){
            if(i.intersects(player)) {
                i.Interact(player);
                toRemove=powerups.indexOf(i);
            }
        }
        if(toRemove!=-1) {
            powerups.remove(toRemove);
        }
    }


}
