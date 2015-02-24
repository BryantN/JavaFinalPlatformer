package Main;

/**
 * Created by Bryant Nielson on 12/1/2014.
 */
public class GravSwitch extends Interactable {
    GravSwitch(int xn,int ny){
        this.setBounds(xn,ny,10,10);
    }
    @Override
    public void Interact(Player player) {
        player.switchGravity();
    }
}
