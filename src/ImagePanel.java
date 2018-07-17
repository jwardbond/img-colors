import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Used to display a scalable image in a JPanel
 * @author jesse
 *
 */
public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	
	/**
	 * Opens a dialog box and prompts the user to select an image
	 */
	public ImagePanel() {
		super();		
	}
	
	public ImagePanel(BufferedImage img) {
		super();
		this.img = img;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0, null);
		repaint();
	}
	
	public void setImg(BufferedImage img) {
		this.img = img;
	}

	
	
}
