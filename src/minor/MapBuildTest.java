package minor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class MapBuildTest extends JFrame {
	
	MapBuilder map;

	public static void main(String[] args) {
		 MapBuildTest d = new MapBuildTest();
		 
	}
	
	public MapBuildTest()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new MapComponent());
        setSize(1000,700);
        setVisible(true);
        map = new MapBuilder();
		//map.building(getWidth()-25, getHeight()-50);
        
    }

	public class MapComponent extends JComponent{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8018028273360171818L;

		public void paint(Graphics g){
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(Color.black);
			for(Rectangle tmp : map.paintableCheckpoints){
				g2.drawRect(tmp.x, tmp.y, tmp.width, tmp.height);
			}
			g2.setPaint(Color.red);
			g2.drawRect(map.paintableInnerMap.x, map.paintableInnerMap.y, map.paintableInnerMap.width, map.paintableInnerMap.height);
			g2.setPaint(Color.green);
			g2.drawRect(map.paintableOuterMap.x, map.paintableOuterMap.y, map.paintableOuterMap.width, map.paintableOuterMap.height);
		
		}
	}
}
