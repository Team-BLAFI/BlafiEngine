package entity.enemy;

public interface State {
    public void enterState();
    public void stateUpdate(double dt);

}
