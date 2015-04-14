package minor;

import java.awt.Point;
import java.awt.geom.Point2D;

public class VectorUtil {
	
	public static double[] getBouncedAngles(Point robot0, double alpha0, Point robot1, double alpha1){
		double[] calculated = new double[2];
		Point2D d = new Point2D.Double(robot0.getX()-robot1.getX(),robot0.getY()-robot1.getY());
		double angle = Math.atan2(d.getY(), d.getX());
		calculated[0] = 2 * angle - alpha0;
		calculated[1] = 2 * angle - alpha1;
		return calculated;
	}

}
