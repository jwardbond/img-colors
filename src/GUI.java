import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame implements ActionListener{
	
	
	private JPanel gammaDecodedPanel;
	private JPanel linearPanel;
	private ImagePanel centerPanel;
	
	
	public GUI(String title) {
		super();
		
		this.gammaDecodedPanel = new JPanel();
		this.linearPanel = new JPanel();
		this.centerPanel = new ImagePanel();		
		
		//Build the GUI
		super.setTitle(title);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new GridLayout(4, 0));
		super.setMinimumSize(new Dimension(300, 300));
				
		//Create the button to load an image
		JButton open = new JButton("Open");
		open.addActionListener(this); //the GUI is listening for open being clicked
		
		//Add elements
		super.add(gammaDecodedPanel);
		super.add(centerPanel);
		super.add(linearPanel);
		super.add(open);		
	}
	
	
	public static void main (String[] args) {
		
		GUI userInterface = new GUI("Color Averager");
		userInterface.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		
		//Display an image in the center panel
		BufferedImage img = this.loadImage();
		this.centerPanel.setImg(img);
		
		//Color the other panels accordingly
		Color gammaEncodedAverageColor = new Color(ColorUtils.getAverageRGB(img), true);
		this.gammaDecodedPanel.setBackground(gammaEncodedAverageColor);
		this.gammaDecodedPanel.add(new JLabel("Simple Average"));
		
		Color linearlyEncodedAverageColor = new Color(ColorUtils.getAverageRGB(img, 2.2), true);//decodes using the gamma of 2.2
		this.linearPanel.setBackground(linearlyEncodedAverageColor);
		this.linearPanel.add(new JLabel("Average after\n gamma decoding"));
			
		repaint();
		setVisible(true); //refreshes the window I hope
		
	}	
	
	
	/**
	 * Prompts the user to load an image using a JFileChooser
	 * @return a BufferedImage containing the file the user chose.
	 */
	public BufferedImage loadImage() {
		
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
		
		this.repaint();
		
		return img;
	}
}
