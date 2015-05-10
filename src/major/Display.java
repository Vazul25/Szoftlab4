package major;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

public class Display extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3408032298810085229L;

	private Phoebe p=null;


	public Display(Phoebe p){
		this.p=p;		
	
		this.setSize(Phoebe.Settings.WINDOW_WIDTH,Phoebe.Settings.WINDOW_HEIGHT+Phoebe.Settings.HUD_HEIGHT);
		this.setVisible(true);	
	}
	
	public void paint(Graphics g){
		super.paint(g);
		List<iVisible> list=p.getVisibleData();
		g.drawImage(p.getBackgroundimg(), 0, 0, Phoebe.Settings.WINDOW_WIDTH,Phoebe.Settings.WINDOW_HEIGHT, null); 
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);	
		for(iVisible  i : list){
			i.paint(g2d);
			
		}
	}
}
