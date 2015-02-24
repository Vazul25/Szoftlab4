package major;

public abstract class Obstacle extends Unit {

	public Obstacle(int x, int y, String imagelocation) {
		super(x, y, imagelocation);
		// TODO Auto-generated constructor stub
	}
	public abstract  void effect(Robot r);
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}



}
