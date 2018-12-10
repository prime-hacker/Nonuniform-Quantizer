
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	static ArrayList<Integer> to1D(int[][] imageMatrix)
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
	static int[][] to2D(ArrayList<Integer> integers, int height, int width)
	{
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
	
    public static void main(String[] args) throws IOException
    {
 
        String imgPath = "input.jpeg";//Example
		ReadWriteImage c = new ReadWriteImage();
		c.readImage(imgPath);
        NonUnifromQuant non = new NonUnifromQuant();

		/*
        //Test Quantizer
        ArrayList<Integer> tstList = new ArrayList<>();
        tstList.add(6);
        tstList.add(15);
        tstList.add(17);
        tstList.add(60);
        tstList.add(100);
        tstList.add(90);
        tstList.add(66);
        tstList.add(59);
        tstList.add(18);
        tstList.add(3);
        tstList.add(5);
        tstList.add(16);
        tstList.add(14);
        tstList.add(67);
        tstList.add(63);
        tstList.add(2);
        tstList.add(98);
        tstList.add(92);
        ArrayList<Double> test = non.constructQuantizer(tstList, 2);
        ArrayList<QuantizerElement> list = non.constructTable(test);
        //End test quantizer
        */


		ArrayList<Integer> tstList = to1D(c.imageMAtrix);
		
		ArrayList<Double> averages = non.constructQuantizer(tstList, 6);
        ArrayList<QuantizerElement> list = non.constructTable(averages);

        ArrayList<Integer> compressed = non.compress(tstList, list);
        c.imageMAtrix = to2D(compressed, c.imageMAtrix.length, c.imageMAtrix[0].length);

        c.writeImage("compressedTEst6.jpeg");
    }

}

