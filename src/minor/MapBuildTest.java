package minor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import major.iVisible;

public class MapBuildTest extends JPanel implements iVisible {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7773459518521157701L;
	
	
	MapBuilder map;

	public static void main(String[] args) {
		 MapBuildTest d = new MapBuildTest();
		 
	}
	
	public MapBuildTest()
    {
		JFrame frame = new JFrame("Phoebe");
        map = new MapBuilder();
		//map.building(getWidth()-25, getHeight()-50);
        
        frame.add(this,BorderLayout.CENTER);	
	
		frame.setSize(1000,700);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
        
    }
	
	public void paint(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.black);
		for(Rectangle tmp : map.checkpoints){
			g2.drawRect(tmp.x, tmp.y, tmp.width, tmp.height);
		}
		g2.setPaint(Color.red);
		g2.drawRect(map.paintableInnerMap.x, map.paintableInnerMap.y, map.paintableInnerMap.width, map.paintableInnerMap.height);
		g2.setPaint(Color.green);
		g2.drawRect(map.paintableOuterMap.x, map.paintableOuterMap.y, map.paintableOuterMap.width, map.paintableOuterMap.height);
	
	}
}
