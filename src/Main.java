
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	

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


		ArrayList<Integer> tstList = c.to1D(c.imageMAtrix);

		ArrayList<Double> averages = non.constructQuantizer(tstList, 6);
        ArrayList<QuantizerElement> list = non.constructTable(averages);

        ArrayList<Integer> compressed = non.compress(tstList, list);

        System.out.println("Size of compressed image in 1D: " + compressed.size());
        c.imageMAtrix = c.to2D(compressed, c.imageMAtrix.length, c.imageMAtrix[0].length);


        c.writeImage("compressedTEst3.jpeg");










        //Test to decompress
		ReadWriteImage c1 = new ReadWriteImage();
		c1.readImage("compressedTEst3.jpeg");
		ArrayList<Integer> commpressed = c1.to1D(c1.imageMAtrix);
        System.out.println("Size of toBeDecompressed image in 1D: " + commpressed.size());
        //Error is here
        ArrayList<Integer> original = non.decompress(commpressed, list);
        System.out.println("Size of decompressed image in 1D: " + original.size());
		c1.imageMAtrix = c1.to2D(original, c1.imageMAtrix.length, c1.imageMAtrix[0].length);
		c1.writeImage("OriginalFromCompressedTEst3.jpeg");

    }

}

