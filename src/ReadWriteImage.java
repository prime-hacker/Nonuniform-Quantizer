import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
public class ReadWriteImage 
{
	int[][] imageMAtrix = null;
	public void readImage(String filePath){
	
		File f = new File(filePath); //image file path
		
		
		try {
			BufferedImage img= ImageIO.read(f);
			int width = img.getWidth(); 
	        int height = img.getHeight(); 
	  
	        imageMAtrix=new int[height][width];
	        
	        for (int y = 0; y < height; y++) 
	        { 
	            for (int x = 0; x < width; x++) 
	            { 
	                int p = img.getRGB(x,y); 
	                int a = (p>>24)&0xff; 
	                int r = (p>>16)&0xff; 
	                int g = (p>>8)&0xff; 
	                int b = p&0xff; 
	  
	                //because in gray image r=g=b  we will select r  
	               
	                imageMAtrix[y][x]=r;
	                
	                //set new RGB value 
	                p = (a<<24) | (r<<16) | (g<<8) | b; 
	                img.setRGB(x, y, p); 
	            } 
	        } 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void writeImage(String imageoutPath){
		
		int height=imageMAtrix.length;
		int width=imageMAtrix[0].length;
		BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		
		 for (int y = 0; y < height; y++) 
	        { 
	            for (int x = 0; x < width; x++){
	            	
	            	int a=255;
	            	int pix=	imageMAtrix[y][x];            			
	            	int p=  (a<<24)	| (pix<<16) | (pix<<8) | pix;
	            	
	            	img.setRGB(x, y, p); 
	            		
	            }
	        }
	    
		 File f = new File(imageoutPath);
		
		try {
			ImageIO.write(img, "jpg", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	//Utility
	 ArrayList<Integer> to1D(int[][] imageMatrix)
	{
		ArrayList<Integer> returnArray = new ArrayList<>();
		int height=imageMatrix.length;
		int width=imageMatrix[0].length;

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++){
				returnArray.add(imageMatrix[y][x]);

			}
		}
		return returnArray;
	}
	 int[][] to2D(ArrayList<Integer> integers, int height, int width)
	{
		if (integers.size() < (height*width))
			throw new IllegalArgumentException("Invalid array length.");
		int[][] imageMatrix = new int[height][width];
		int count = -1;
		for(int i = 0; i< height; i++)
		{
			for(int k = 0; k < width; k++)
			{
				count++;
				imageMatrix[i][k] = integers.get(count);
			}
		}
		return imageMatrix;
	}


}
