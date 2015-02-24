package Main;


/**
 * Created by Bryant Nielson on 12/1/2014.
 */
public class Coin extends Interactable{




    Coin(int nx,int ny){
        this.setBounds(nx,ny,10,10);
    }

    @Override
    public void Interact(Player player) {
        player.addPoints(1);
    }
}
