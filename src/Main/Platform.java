package Main;

import java.awt.*;

/**
 * Created by Bryant Nielson on 12/1/2014.
 */
public class Platform extends Rectangle {
    protected int jumpmod=1;
    Platform(Rectangle dimension){
        width= dimension.width;
        height= dimension.height;
        x=dimension.x;
        y=dimension.y;
    }


    public int getJumpMod(){return jumpmod;}

}
