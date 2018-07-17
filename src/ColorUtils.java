import java.awt.image.BufferedImage;

/**
 * Static class holding things that I need to work with colors.
 * @author jesse
 *
 */
public class ColorUtils {

	/**
	 * Computes the average RGB value of a buffered image and returns it as
	 * @param img The image you want to work with
	 * @retur and int packed with aRGB values
	 */
	public static int getAverageRGB(BufferedImage img) {
		int alpha = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		int count = 0;
		
		//Unpack aRGB values of the pixels
		for (int i=0; i<img.getWidth(); i++) {
			for (int j=0; j<img.getHeight(); j++) {
				count++;
				
				int rgb = img.getRGB(i, j);
				alpha += (rgb >> 24) & 0x000000ff; 
				red += (rgb >> 16) & 0x000000ff;
				green += (rgb >> 8) & 0x000000ff;
				blue += (rgb) & 0x000000ff;
			}
		}
		
		//Calculate averages
		alpha = 255;
		red = red / count; 
		green = green / count;
		blue = blue / count;
		
		//Repack. At this point they should all be 32 bit ints with 0's in the leading 24 bits
		int packed = 0;
		packed += (alpha << 24); 
		packed += (red << 16);
		packed += (green << 8);
		packed += (blue);
		
		return packed;
	}
	
	
	/**
	 * Computes the average RGB value of a buffered image and returns it as
	 * @param hasAlpha When true, the alpha is also averaged
	 * @param img The image you want to work with
	 * @retur and int packed with aRGB values
	 */
	public static int getAverageRGB(BufferedImage img, boolean hasAlpha) {
		
		if (hasAlpha == true) {
		int alpha = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		int count = 0;
		
		//Unpack aRGB values of the pixels
		for (int i=0; i<img.getWidth(); i++) {
			for (int j=0; j<img.getHeight(); j++) {
				count++;
				
				int rgb = img.getRGB(i, j);
				alpha += (rgb >> 24) & 0x000000ff; 
				red += (rgb >> 16) & 0x000000ff;
				green += (rgb >> 8) & 0x000000ff;
				blue += (rgb) & 0x000000ff;
			}
		}
		
		//Calculate averages
		alpha = alpha / count;
		red = red / count; 
		green = green / count;
		blue = blue / count;
		
		//Repack. At this point they should all be 32 bit ints with 0's in the leading 24 bits
		int packed = 0;
		packed += (alpha << 24); 
		packed += (red << 16);
		packed += (green << 8);
		packed += (blue);
		
		return packed;
		} else {
			return ColorUtils.getAverageRGB(img); //its opaque
		}
	}
	
	
	/**
	 * Calculates the average linear-encoded aRGB values. It does this by using the given gamma to transform the value of the RGB channels
	 * in every pixel in order to remove the gamma correction. It calculates the average of the linear range and the re-applies the gamma.
	 * 
	 * Assumes the image is opaque
	 * @param img 
	 * @param gamma This is usually 1/2.2 in most images. This is the encoding gamma (should be <1)
	 * @return the packed int which is a binary represntation of the average of the aRGB channels
	 */
	public static int getAverageRGB(BufferedImage img, double gamma) {
		int alpha = 0;
		int red = 0; 
		int green = 0; 
		int blue = 0;		
		int count = 0;
		
		for (int i=0; i<img.getWidth(); i++) {
			for (int j=0; j<img.getHeight(); j++) {
				count++;
				
				//Unpack RGB values and apply gamma correction
				int rgb = img.getRGB(i, j);	
				alpha += (rgb >> 24) & 0x000000ff; 
				red += (int)(255 * (Math.pow((double)((rgb >> 16) & 0x000000ff) / (double)255, gamma)));
				blue += (int)(255 * (Math.pow((double)((rgb >> 8) & 0x000000ff) / (double)255, gamma)));
				green += (int)(255 * (Math.pow((double)((rgb) & 0x000000ff) / (double)255, gamma)));
			}
		}	
		//Mathematical average
		alpha = 255; //opaque image
		red = red / count;
		green = green / count;
		blue = blue / count;	
		
		//Re-applying gamma
		red = (int)(255 * (Math.pow((double)red / (double)255, 1/gamma)));
		green = (int)(255 * (Math.pow((double)green / (double)255, 1/gamma)));
		blue = (int)(255 * (Math.pow((double)blue / (double)255, 1/gamma)));
		
		//Repack. At this point they should all be 32 bit ints with 0's in the leading 24 bits
		int packed = 0;
		packed += (alpha << 24); 
		packed += (red << 16);
		packed += (green << 8);
		packed += (blue);
		
		return packed;
	}
	
	
	/**
	 * Calculates the average linear-encoded aRGB values. It does this by using the given gamma to transform the value of the RGB channels
	 * in every pixel in order to remove the gamma correction. It calculates the average of the linear range and the re-applies the gamma.
	 * @param img 
	 * @param gamma This is usually 1/2.2 in most images. This is the encoding gamma (should be <1)
	 * @param hasAlpha when this is true, the function also averages the alpha value.
	 * @return the packed int which is a binary represntation of the average of the aRGB channels
	 */
	public static int getAverageRGB(BufferedImage img, double gamma, boolean hasAlpha) {
		
		if (hasAlpha == true) {
			int alpha = 0;
			int red = 0; 
			int green = 0; 
			int blue = 0;		
			int count = 0;
			
			for (int i=0; i<img.getWidth(); i++) {
				for (int j=0; j<img.getHeight(); j++) {
					count++;
					
					//Unpack RGB values and apply gamma correction
					int rgb = img.getRGB(i, j);	
					alpha += (rgb >> 24) & 0x000000ff; 
					red += (int)(255 * (Math.pow((double)((rgb >> 16) & 0x000000ff) / (double)255, gamma)));
					blue += (int)(255 * (Math.pow((double)((rgb >> 8) & 0x000000ff) / (double)255, gamma)));
					green += (int)(255 * (Math.pow((double)((rgb) & 0x000000ff) / (double)255, gamma)));
				}
			}	
			//Mathematical average
			alpha = alpha / count;
			red = red / count;
			green = green / count;
			blue = blue / count;	
			
			//Re-applying gamma
			red = (int)(255 * (Math.pow((double)red / (double)255, 1/gamma)));
			green = (int)(255 * (Math.pow((double)green / (double)255, 1/gamma)));
			blue = (int)(255 * (Math.pow((double)blue / (double)255, 1/gamma)));
			
			//Repack. At this point they should all be 32 bit ints with 0's in the leading 24 bits
			int packed = 0;
			packed += (alpha << 24); 
			packed += (red << 16);
			packed += (green << 8);
			packed += (blue);
			
			return packed;
		} else {
			return ColorUtils.getAverageRGB(img, gamma);
		}
	}
	
	
}
