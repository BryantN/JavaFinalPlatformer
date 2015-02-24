package Main;

import java.awt.*;
import java.awt.geom.Area;

/**
 * Created by Bryant on 11/6/2014.
 */
public class Camera {
    private Rectangle renderArea;
    private int cx,cy;
    private Rectangle freemoveArea;
    private double x;
    private double y;

    Camera(int Centerx,int Centery,int Width,int Height){
        renderArea=new Rectangle(0,0,Width,Height);
        cx=Centerx;
        cy=Centery;
        freemoveArea=new Rectangle(cx-200,cy-200,400,400);
    }

    Rectangle getRenderArea(Player player){
        //check if player is within the bounds of the render area
        boolean ct=false,cb=false,cl=false,cr=false;
        int xchange=0,ychange=0;

        if(freemoveArea.contains(player.cPoints[0].getX()+player.getX(),player.cPoints[0].getY()+player.getY())
                ||freemoveArea.contains(player.cPoints[1].getX()+player.getX(),player.cPoints[1].getY()+player.getY())
                )
            ct=true;
        if(freemoveArea.contains(player.cPoints[2].getX()+player.getX(),player.cPoints[2].getY()+player.getY())
                ||freemoveArea.contains(player.cPoints[3].getX()+player.getX(),player.cPoints[3].getY()+player.getY()))
            cl=true;
        if(freemoveArea.contains(player.cPoints[4].getX()+player.getX(),player.cPoints[4].getY()+player.getY())
                ||freemoveArea.contains(player.cPoints[5].getX()+player.getX(),player.cPoints[5].getY()+player.getY()))
            cr=true;
        if(freemoveArea.contains(player.cPoints[6].getX()+player.getX(),player.cPoints[6].getY()+player.getY())
                ||freemoveArea.contains(player.cPoints[7].getX()+player.getX(),player.cPoints[7].getY()+player.getY()))
            cb=true;

        if(!cb){
            ychange=(int)(freemoveArea.getMaxY()-player.getMaxY());
        }
        //check up collision
        else if(!ct){
            ychange=(int)(freemoveArea.getY()-player.getY());
        }
        //check left collision
        if(!cl){
            xchange=(int)(freemoveArea.getX()-player.getX());
        }
        //check right collision
        else if(!cr){
            xchange=(int)(freemoveArea.getMaxX()-player.getMaxX());
        }


        cx-=xchange;
        cy-=ychange;

        freemoveArea.setLocation(cx-200,cy-200);
        int x,y;
        x=cx-(int)renderArea.getWidth()/2;
        y=cy-(int)renderArea.getHeight()/2;
        return new Rectangle(x,y,(int)renderArea.getWidth(),(int)renderArea.getHeight());
    }


}
