import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Prompts the user to select an image file to open. Starts from the current directory.
 * @author jesse
 *
 */
public class ImageOpenListener implements ActionListener{
	
	/**
	 * Prompts the user to load an image on button click.
	 * Displays the average color of that image.
	 */
	public void actionPerformed(ActionEvent event) {
		
		File currentDirectory = new File(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
		
		//Open the file-chooser dialogue box
		final JFileChooser chooser = new JFileChooser(currentDirectory);
		chooser.setFileFilter(filter);
		chooser.addChoosableFileFilter(filter);
		int openReturnVal = chooser.showOpenDialog(null);
	
		if (openReturnVal == JFileChooser.APPROVE_OPTION)
			System.out.println("You have selected: " + chooser.getSelectedFile().getPath());
		
		//Load the image into a BufferedImage
		BufferedImage img = null;
		try {
			img = ImageIO.read(chooser.getSelectedFile());
		} catch (IOException e) {
			System.out.println("Image not found.");
		}
		
		//Output some basic stats
		int numPixels = img.getHeight() * img.getWidth();
		System.out.println("Image height is: " + img.getHeight() + " and the width is: " + img.getWidth()
							+ " and it has " + numPixels + " pixels.");	
		
	}
	

}
