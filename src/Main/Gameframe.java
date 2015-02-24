package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Bryant on 10/9/2014.
 */

public class Gameframe extends JFrame implements KeyListener, ActionListener{
    private static final int WIDTH=1000,HEIGHT=600;
    private JButton beginGame;
    private PausePanel pausePanel;
    private JDialog dialog;
    private BufferedImage titlepic;
    private JLabel titleCard;

    Timer timer;


    AnimationPanel animationPanel;

    Gameframe() {
        this.setTitle("SLAM JAM");
        pausePanel=new PausePanel();
        dialog=new JDialog();
        dialog.setModal(true);
        dialog.setContentPane(pausePanel);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();

        beginGame = new JButton();
        beginGame.setText("Tip off");
        beginGame.setBackground(Color.ORANGE);
        beginGame.setSize(100, 50);
        beginGame.setFocusable(false);
        beginGame.addActionListener(this);
        beginGame.setLocation(WIDTH/2-50,HEIGHT/2-25);

        String titlePath = "Resources/Title Screen.png";
        titlepic=getfromfile(titlePath);
        titleCard=new JLabel(new ImageIcon(titlepic));
        titleCard.setSize(WIDTH,HEIGHT);
        titleCard.setVisible(true);

        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);

        this.getLayeredPane().add(titleCard,3);
        this.getLayeredPane().add(beginGame, 0);
        animationPanel=new AnimationPanel();
        this.getLayeredPane().add(animationPanel, 5);
        animationPanel.setVisible(false);

        timer=new Timer(1000,this);
        timer.start();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(animationPanel==null)return;
        if (e.getKeyChar() == ' ') animationPanel.setjump(true);
        if (Character.toUpperCase(e.getKeyChar()) == 'D') animationPanel.setRight(true);
        if (Character.toUpperCase(e.getKeyChar()) == 'A') animationPanel.setLeft(true);
        if (e.getKeyCode()==KeyEvent.VK_SHIFT) animationPanel.setDodge(true);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            if (animationPanel.isPaused()) {
                animationPanel.resumeGame();
            }else{
                animationPanel.pauseGame();
                pausePanel.startPause();
                dialog.setVisible(true);
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(animationPanel==null)return;
        if(e.getKeyChar()==' ')animationPanel.setjump(false);
        if(Character.toUpperCase(e.getKeyChar())=='D') animationPanel.setRight(false);
        if(Character.toUpperCase(e.getKeyChar())=='A') animationPanel.setLeft(false);
        if(e.getKeyCode()==KeyEvent.VK_SHIFT) animationPanel.setDodge(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource().equals(beginGame)){
            animationPanel.setVisible(true);
            beginGame.setVisible(false);
            beginGame.setEnabled(false);
            this.getLayeredPane().setLayer(animationPanel, 5);
            titleCard.setVisible(false);
        }
        if(animationPanel==null)return;
        if(e.getSource().equals(timer)&&!animationPanel.isGameFinished()){
            animationPanel.chageOp(pausePanel.getOP());
            if(!pausePanel.getIsPause()){
                animationPanel.resumeGame();
                animationPanel.changeBackground(pausePanel.getBackgroundPath());
                dialog.dispose();
            }
            if(pausePanel.getQuit()){
                System.exit(0);
            }

        }
        else if(e.getSource().equals(timer)){
            //restart game
            String endPath = "Resources/End Screen.png";
            titlepic=getfromfile(endPath);
            titleCard.setIcon(new ImageIcon(titlepic));
            titleCard.setVisible(true);
            this.getLayeredPane().setLayer(titleCard, 1);
            int result=JOptionPane.showConfirmDialog(null,"Do You wish to Restart the Game, No will exit the application",null,JOptionPane.YES_NO_OPTION);
            if(result==JOptionPane.YES_OPTION){
                animationPanel.changelevel('1');
                animationPanel.resumeGame();
                animationPanel.changeBackground(pausePanel.getBackgroundPath());
                dialog.dispose();
                titleCard.setVisible(false);
            }
            else System.exit(0);
        }
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
}
