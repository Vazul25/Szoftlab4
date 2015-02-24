package major;

public class Glue extends Obstacle {

	public Glue(int x, int y, String imagelocation) {
		super(x, y, imagelocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect(Robot r) {
		// TODO Auto-generated method stub
		r.setGlue();
	}



}
