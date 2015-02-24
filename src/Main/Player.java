package Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 * Created by Bryant on 11/5/2014.
 */
public class Player extends Rectangle {
    private int WIDTH=50,HIEGHT=50;
    private double xspeed,yspeed;
    private double maxYspeed =15,maxXspeed=500;
    public int jumpmod=1;
    private double numJumps=2;
    private int lag=0;
    private int dodgeDir=0;
    private boolean invicible=false;
    private boolean faceRight=true;
    private Action currAction=Action.Walk;
    private int health=50;
    private int score=0;
    private boolean left=false,right=false,dodge=false,jump=false;

    public Point[] cPoints={
            new Point(WIDTH/4,0),new Point(3*WIDTH/4,0),//Top
            new Point(0,HIEGHT/4),new Point(0,3*HIEGHT/4),//Left
            new Point(WIDTH,HIEGHT/4),new Point(WIDTH,3*HIEGHT/4),//Right
            new Point(WIDTH/4,HIEGHT),new Point(3*WIDTH/4,HIEGHT),//Bottom
    };

    private double gravity;
    private int gravFac;

    public Player(){
        this.setRect(200,200,WIDTH,HIEGHT);
        yspeed=0;
        xspeed=0;
        gravity =0.5;
        gravFac=1;
    }

    public void move(LinkedList<Platform> Platforms){
        double xpos= this.getX(),ypos=this.getY();

        if (!(left ^ right)) {
            this.setXSpeed(0);
            currAction = Action.Stand;
        }
        else if (right) {
            this.setXSpeed(5);
            faceRight = true;
            currAction = Action.Walk;
        }else{
            this.setXSpeed(-5);
            faceRight = false;
            currAction = Action.Walk;
        }
        //jump and fall animations take precedence on walk/stand animations
        if(jump&&numJumps>0) {
            this.addSpeed(0, -5*jumpmod*gravFac);
            --numJumps;
            currAction=Action.Jump;
        }
        if(yspeed>3) currAction=Action.Fall;
        else if(yspeed<-2)currAction=Action.Jump;
        if(dodge&&lag==0){
            if(faceRight)dodgeDir=1;
            else dodgeDir=-1;
            lag=25;
            invicible=true;
        }else if(lag>10){
            --lag;
            setXSpeed(10 * dodgeDir);
            currAction=Action.Dodge;
        }else if(lag>0){
            --lag;
            invicible=false;
            setXSpeed(0);
            dodgeDir=0;
        }

        xpos+=xspeed;
        ypos+=yspeed;
        yspeed += gravity;

        this.setLocation((int) xpos,(int) ypos);

      //  collision.FixCollision(this,Platforms);
        checkMax();
    }
    private void checkMax(){
        if(yspeed>maxYspeed)  yspeed=maxYspeed;
        else if(yspeed<maxYspeed*-1) yspeed=maxYspeed*-1;
        if(xspeed>maxXspeed)  xspeed=maxXspeed;
        else if(xspeed<maxXspeed*-1) xspeed=maxXspeed*-1;
    }

    //Public Mutators

    public void addSpeed(double nXs,double nYs){
        xspeed+=nXs;
        yspeed+=nYs;
        checkMax();
    }
    public void setXSpeed(double nXs){
        xspeed=nXs;
        checkMax();
    }

    public void setYSpeed(double nYs){
        yspeed=nYs;
        checkMax();
    }

    public void modjump(int tomod){
        jumpmod=tomod;
    }

    public void resetJump(){
        numJumps=2;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDodge(boolean dodge){
        this.dodge= dodge;
    }

    public void setJump(boolean jump){
        this.jump= jump;
    }
    public void switchGravity(){
        gravity*=-1;
        gravFac*=-1;
    }
    public void clearscore() {
        score=0;
    }

    public void addPoints(int numpoints){
        if(numpoints<0) return;
        score+=numpoints;
    }

    //public accessors
    public Hitbox attack(){
        Hitbox hitbox=new Hitbox(10);
        //if(faceRight)
        //hitbox.setBounds(this.getX(),this.getY(),this.getHeight(),this.getWidth());
        return hitbox;
    }

    public double getXSpeed(){
        return xspeed;
    }

    public double getYSpeed(){
        return yspeed;
    }
    public int getScore(){ return score; }
    public int getCurrActionValue(){
        return currAction.getValue();
    }
    public int getFaceVal(){
        if(faceRight)return 0;
        return 1;
    }

    public int getGravFac(){
        return gravFac;
    }




}
