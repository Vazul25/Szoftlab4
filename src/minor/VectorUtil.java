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
	
	public static double getVectorsSummRobot0(Point robot0, Point dir0, Point robot1, Point dir1){
		Point2D distance = new Point2D.Double(robot0.getX()-robot1.getY(), robot0.getY()-robot1.getY()); //0-ás robottól az 1-es robotra mutató vektor, melynek kezdő pontja a (0,0) és végpontja ez a distance pont.
		Point2D original_dir = new Point2D.Double(dir0.getX()-robot0.getX(), dir1.getY()-robot0.getY()); //0-ás robot eredeti irány levetítve az origóba
		
		double original_dir_length = Math.hypot(original_dir.getX(), original_dir.getY());
		double distance_length = Math.hypot(distance.getX(), distance.getY());
		double normaliser = original_dir_length/distance_length;
		
		distance.setLocation(distance.getX()*normaliser, distance.getY()*normaliser);
		
		Point2D new_dir = new Point2D.Double(distance.getX()+original_dir.getX(), distance.getY()+original_dir.getY());
		
		return Math.atan2(new_dir.getY(), new_dir.getX());
	}

}
