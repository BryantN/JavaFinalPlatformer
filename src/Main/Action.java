package Main;

/**
 * Created by Bryant Nielson on 11/29/2014.
 */
public enum Action {
    Stand(0),
    Walk(1),
    Jump(2),
    Fall(3),
    Dodge(4),
    Attack(5);
    private int value;
    Action(int i) {
        value=i;
    }
    public int getValue(){return value;}
}
