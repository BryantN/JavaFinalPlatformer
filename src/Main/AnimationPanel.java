package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

/**
 * Created by Bryant on 11/5/2014.
 */
public class AnimationPanel extends JPanel implements Runnable,ActionListener {
    static private int HEIGHT=600,WIDTH=1000;
    static private int Fps=60;
    private int framenum=0;
    private int animSpeed=1;

    private Player Thomas =new Player();
    private char currLevel='1';
    private Level level=new Level("Resources/Levels/Level 1-1");
    private Camera camera;
    private int respawnTime=30;
    private double opacity=100;

    private boolean gamecomplete=false;

    private Thread thread;
    private Timer timer;

    //images to read

    //C:\Users\Bryant Nielson\Documents\Java\RythymGame\Resources\Background.png
    private BufferedImage background=getfromfile("Resources/SpaceBackground.png");
    private BufferedImage coin=getfromfile("Resources/Coin_sprite.png");
    private BufferedImage checkPoint =getfromfile("Resources/Goal.png");
    private BufferedImage goal=getfromfile("Resources/Finish.png");
    private BufferedImage Gcoin=getfromfile("Resources/GravityPowerup.png");
    private BufferedImage PlayerSpritesheet=getfromfile("Resources/PlayerSprite.png");
    private BufferedImage Floorimg=getfromfile("Resources/AlternatIdeatext.png");
    private BufferedImage jumppad=getfromfile("Resources/Jumppadtexture.png");
    private BufferedImage [][]PlayerSprite=new BufferedImage[4][12];



    AnimationPanel(){

        for(int i=0;i<4;++i){
            for(int j=0;j<12;++j){
                PlayerSprite[i][j]=PlayerSpritesheet.getSubimage(j*50,i*50,50,50);
            }

        }

        Thomas.setLocation(level.getPlayerSpawn());

        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setFocusable(false);

        this.setLayout(new BorderLayout());

        camera=new Camera(WIDTH/2,HEIGHT/2,WIDTH,HEIGHT);

        timer=new Timer(1000/Fps,this);
        thread =new Thread(this);
        thread.start();
        timer.start();
    }

    public void pauseGame(){
        thread=null;
        timer.stop();
    }
    public void resumeGame(){
        thread=new Thread(this);
        thread.start();
        timer.start();
    }
    public void setjump(boolean nJump){
        Thomas.setJump(nJump);
    }
    public void setDodge(boolean ndodge){Thomas.setDodge(ndodge);}
    public void setLeft(boolean left) {
        Thomas.setLeft(left);
    }
    public void setRight(boolean right) {
        Thomas.setRight(right);
    }

    @Override
    public void run() {
        Thread thisThread=Thread.currentThread();
        while(thread==thisThread) {
            repaint();
            ++framenum;
            if(framenum>=60/animSpeed)framenum=0;
            try {
                thread.sleep(1000 / Fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    protected void paintComponent(Graphics g) {

        Rectangle render=camera.getRenderArea(Thomas);
        int xoffset= (int) render.getX();
        int yoffset=(int) render.getY();

        Graphics2D g2 = (Graphics2D)g.create();
        if(level.isGameCleared()){
            gamecomplete=true;
            pauseGame();
            return;
        }
        g2.drawImage(background,null,0,0);
        //checkPoint
        g2.drawImage(goal,null,(int)(level.getGoal().getX()-xoffset),(int)(level.getGoal().getY()-yoffset));
        for(Rectangle i:level.getCheckpoints())
            g2.drawImage(checkPoint, null, (int) i.getX() - xoffset, (int) i.getY() - yoffset);
        //platforms
        for(Rectangle i:level.getPlatforms()) {
            if(i instanceof JumpPad)
                g2.drawImage(jumppad.getSubimage(0, 0, i.width, i.height), null, (int) i.getX() - xoffset, (int) i.getY() - yoffset);
             else
                 g2.drawImage(Floorimg.getSubimage(0, 0, i.width, i.height), null, (int) i.getX() - xoffset, (int) i.getY() - yoffset);
        }
        //coins
        for(Rectangle i:level.getPowerups()) {
            if(i instanceof Coin) {
                g2.drawImage(coin, null, (int) i.getX() - xoffset, (int) i.getY() - yoffset);
            }else{
                g2.drawImage(Gcoin, null, (int) i.getX() - xoffset, (int) i.getY() - yoffset);
            }
        }
        //player
        g2.drawImage(PlayerSprite[(Thomas.getGravFac()==1?Thomas.getFaceVal():3-Thomas.getFaceVal())][Thomas.getCurrActionValue()*2+framenum*2*animSpeed/(60)],null,(int)Thomas.getX()-xoffset,(int) Thomas.getY()-yoffset);
        g2.setFont(new Font("Monospaced",Font.BOLD,18));
        if(level.isCleared()){
            g2.setPaint(Color.GREEN);
            g2.drawString("Cleared!!!",WIDTH/2,HEIGHT/2);
        }

        //hud
        g2.setPaint(Color.GREEN);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) opacity/100));
        g2.drawString("Score: "+Thomas.getScore(),20,20);
        g2.drawString(level.getLevelName(),WIDTH/2,20);

    }

    public boolean isPaused(){return !timer.isRunning();}
    public boolean isGameFinished(){
        return gamecomplete;
    }

    private BufferedImage getfromfile(String filepath){
        File source=new File(filepath);
        try {
            return ImageIO.read(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Rectangle fixapperance(Rectangle toFix,int xoffset,int yoffset){
        return new Rectangle((int)(toFix.getX()-xoffset),(int)(toFix.getY()-yoffset),(int)toFix.getWidth(),(int)toFix.getHeight());
    }


    Collision collision=new Collision();
    //timer action listner
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(timer)) {
            if(level.isCleared()){
                --respawnTime;
                if(respawnTime<0){
                    respawnTime=30;
                    changelevel();
                }
            }
            if(!level.isGameCleared()) {

                Thomas.move(level.getPlatforms());
                if(Thomas.getGravFac()<0){
                    collision.FixCollisionReverseGrav(Thomas, level.getPlatforms());
                } else {
                    collision.FixCollision(Thomas, level.getPlatforms());
                }
                collision.Checkinteraction(Thomas,level.getPowerups());

                for(Rectangle i:level.getCheckpoints())
                    if (Thomas.intersects(i)) {
                        level.setCurrCheckpoint((int) i.getX(), (int) i.getY(),Thomas.getGravFac()==1);
                        level.getCheckpoints().remove(i);
                    }

                if (Thomas.intersects(level.getGoal())) {
                    level.levelcleared();
                }

                if(!Thomas.intersects(level.getInBounds())) respawn();
            }
        }
    }

    private void respawn(){
        Thomas.setLocation(level.Respawn());
        if(Thomas.getGravFac()==1^level.getGrav())
            Thomas.switchGravity();
        Thomas.setXSpeed(0);
        Thomas.setYSpeed(0);
        Thomas.clearscore();
    }


    private void changelevel(){
        currLevel= (char) ((int)currLevel+1);
        level=new Level("Resources/Levels/Level 1-"+currLevel);
        if(!level.isGameCleared()) respawn();
    }
    public void changelevel(char tostart){
        currLevel= tostart;
        level=new Level("Resources/Levels/Level 1-"+currLevel);
        gamecomplete=false;
        if(!level.isGameCleared()) respawn();
    }
    public void chageOp(double nOP){
        opacity=nOP;
    }
    public void changeBackground(String Path){
        background=getfromfile(Path);
    }

}
