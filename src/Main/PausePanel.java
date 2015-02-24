package Main;



import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Bryant Nielson on 12/7/2014.
 */
public class PausePanel extends JPanel implements ActionListener,ChangeListener{
    final private int HIEGHT=300, WIDTH=300;
    private int opCurr,opMax,opMin;
    private boolean quit, ispause;
    private String background;
    private JLabel Pauselable;
    private JButton Resume, Exit;
    private JSlider Opacity;
    private JRadioButton Testback,SpaceBack,CourtBack;
    private ButtonGroup backGroup;

    PausePanel(){
        opCurr =100;
        opMax=100;
        opMin=0;
        quit=false;
        ispause =false;
        background="Resources/SpaceBackground.png";
        setBounds(0,0,WIDTH,HIEGHT);

        setLayout(new FlowLayout());

        Pauselable=new JLabel("Slider for Score opacity");

        Resume=new JButton("Resume");
        Resume.setSize(100,25);
        Resume.setLocation(50,250);
        Resume.addActionListener(this);

        Exit=new JButton("Retire");
        Exit.setSize(100,25);
        Exit.setLocation(200,250);
        Exit.addActionListener(this);

        Opacity=new JSlider(JSlider.HORIZONTAL,opMin,opMax, opCurr);
        Opacity.setMajorTickSpacing(20);
        Opacity.setMinorTickSpacing(2);
        Opacity.setPaintLabels(true);
        Opacity.setPaintTicks(true);
        Opacity.addChangeListener(this);

        Testback=new JRadioButton("Test Background");
        Testback.setSize(100,25);
        Testback.addActionListener(this);

        SpaceBack=new JRadioButton("Space Background");
        SpaceBack.setSize(100, 25);
        SpaceBack.addActionListener(this);
        SpaceBack.setSelected(true);

        CourtBack=new JRadioButton("Court Background");
        CourtBack.setSize(100, 25);
        CourtBack.addActionListener(this);

        backGroup=new ButtonGroup();
        backGroup.add(Testback);
        backGroup.add(SpaceBack);
        backGroup.add(CourtBack);

        this.add(Resume);
        this.add(Exit);
        this.add(Pauselable);
        this.add(Opacity);
        this.add(Testback);
        this.add(SpaceBack);
        this.add(CourtBack);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Exit){
            quit=true;
        }
        if(e.getSource()==Resume){
            ispause =false;
        }
        if(e.getSource()==Testback){
            background="Resources/Background.png";
        }
        if(e.getSource()==SpaceBack){
            background="Resources/SpaceBackground.png";
        }
        if(e.getSource()==CourtBack){
            background="Resources/SunBall.png";
        }
    }

    @Override
    //how to use Statechanged listner from Oracle documentation, just used the first two lines
    public void stateChanged(ChangeEvent e) {
        JSlider source=(JSlider)e.getSource();
        if(!source.getValueIsAdjusting()){
            opCurr=source.getValue();
        }
    }
    //getter, and setters
    public void startPause(){
        ispause =true;
    }
    public boolean getIsPause(){
        return ispause;
    }
    public boolean getQuit(){
        return quit;
    }
    public double getOP(){
        return opCurr;
    }
    public String getBackgroundPath(){
        return background;
    }

}
