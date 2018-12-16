import java.util.ArrayList;
import java.util.Comparator;

public class NonUnifromQuant {

    private double avg(ArrayList<Integer> integers) throws ArithmeticException
    {
        double sum=0;
        double size = integers.size();
        for(int i= 0; i<size; i++)
            sum+=integers.get(i);
        return sum/size;
    }
    private double avg(Double d1, Double d2)
    {
        return (d1+d2)/2;
    }
    
    
    
    private ArrayList<Integer> split(double avg)
    {
        ArrayList<Integer> splitted = new ArrayList<>(2);
        if(avg == Math.floor(avg)) {
            splitted.add((int) (avg - 1));
            splitted.add((int) (avg + 1));
        }
        else{
            splitted.add((int)Math.floor(avg));
            splitted.add((int) Math.ceil(avg));
        }
        return splitted;
    }

    private ArrayList<ArrayList<Integer>> associate(ArrayList<Integer> originalIntegers, ArrayList<Integer> splitAround)
    {
        ArrayList<Integer> associatedSet1 = new ArrayList<>();
        ArrayList<Integer> associatedSet2 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> sets = new ArrayList<>();

        for(int i=0; i<originalIntegers.size(); i++)
        {
            int element = originalIntegers.get(i);
            double diff1 = Math.abs(element - splitAround.get(0));
            double diff2 = Math.abs(element - splitAround.get(1));
            if(diff1 < diff2) {
                associatedSet1.add(element);
            }
            else {
                associatedSet2.add(element);
            }
        }
        if(associatedSet1.size() > 0)
        	sets.add(associatedSet1);
        if(associatedSet2.size() > 0)
        	sets.add(associatedSet2);

        return sets;
    }


    public ArrayList<Double> constructQuantizer(ArrayList<Integer> integers, int bitsNum) throws ArrayIndexOutOfBoundsException
    {
        ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<>();
        listOfLists.add(integers);

        ArrayList<Double> averages = new ArrayList<>();
        double level = Math.pow((double)2, (double)bitsNum);
        int counter =0;
        while (counter < bitsNum )
        {
            int tempSize = listOfLists.size();
            for(int i =0; i< tempSize ; i++) {
                Double avg = avg(listOfLists.get(i));
                ArrayList<Integer> toSplitAround = split(avg);
                ArrayList<ArrayList<Integer>> associateResult = associate(listOfLists.get(i), toSplitAround);
                listOfLists.remove(i);
                for(int k =0; k < associateResult.size(); k++) {
                	listOfLists.add(associateResult.get(k));
                }

            }
            counter++;
        }
        for(int i = 0; i<listOfLists.size(); i++)
        {
            Double avg = avg(listOfLists.get(i));
            averages.add(avg);
        }

        //To test

        averages.sort(new Comparator<>() {
            @Override
            public int compare(Double doub, Double t1) {
                return doub.compareTo(t1);
            }
        });

        for (Double i :
                averages     ) {
            System.out.println(i + "   ");
        }

        return averages;

    }


    public ArrayList<QuantizerElement> constructTable(ArrayList<Double> averages)
    {
        ArrayList<QuantizerElement> table = new ArrayList<>();
        for(int i =0; i<averages.size(); i++)
        {
            QuantizerElement temp = new QuantizerElement();
            if( i == averages.size()-1)
                temp.range = 255;
            else
                temp.range = avg(averages.get(i), averages.get(i+1));
            temp.Q = i;
            temp.QInverse = averages.get(i).intValue();
            table.add(temp);
        }
        for (QuantizerElement q:
             table) {
            q.print();
        }
        return table;
    }



    //Compression and decompression
    public ArrayList<Integer> compress(ArrayList<Integer> image, ArrayList<QuantizerElement> table)
    {
        ArrayList<Integer> compressedImage = new ArrayList<>();
        for(Integer pixel:
        image)
        {
            for(QuantizerElement qe:
            table)
            {
                if(pixel < qe.range) {
                    compressedImage.add(qe.Q);
                    break;
                }

            }
        }
        return compressedImage;
    }

    public ArrayList<Integer> decompress(ArrayList<Integer> imgToDecompress, ArrayList<QuantizerElement> table)
    {
        ArrayList<Integer> originalImg = new ArrayList<>();
        for(Integer pixel:
        imgToDecompress)
        {
            for (QuantizerElement qe:
                 table) {
                if(pixel == qe.Q) {
                    originalImg.add(qe.QInverse);
                    break;
                }
            }
            if(pixel > table.get(table.size()-1).Q)
                originalImg.add(table.get(table.size()-1).QInverse);
        }
        return originalImg;
    }
}
