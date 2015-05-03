package major;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
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
	
	/**
     * 
     * 
     * @param img 
     * @param angle 
     * @return 
     */
    public static Image rotate(Image img, double angle){
        double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = img.getWidth(null), h = img.getHeight(null);
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h
                * cos + w * sin);
       // BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
        BufferedImage bimg = toBufferedImage(new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB));
        Graphics2D g = bimg.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage(toBufferedImage(img), null);
        g.dispose();
        return (Image)bimg;
    }

    private static BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }
}
