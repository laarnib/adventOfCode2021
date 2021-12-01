import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class AdventOfCodeDay01 {

    public static void main(String[] args) throws Exception {

        Scanner puzzleInput = new Scanner(Paths.get("../input.txt"));
        ArrayList<Integer> inputList = new ArrayList<>();


        // Store the measurements in the file in an array
        while (puzzleInput.hasNextLine()) {
            inputList.add(Integer.valueOf(puzzleInput.nextLine()));
        }

        System.out.println(getNumOfIncreasedDepth(inputList));
        System.out.println(getNumOfIncreasedSum(inputList));
    }

    /*
     Gets the number of times the depth measurement is larger than the
     previous one.
     */
    private static int getNumOfIncreasedDepth(ArrayList<Integer> inputList) {
        int increaseCount = 0;
        int seaFloorDepthPrevious = 0;
        int seaFloorDepthCurrent = 0;

        // Store the first measurement
        seaFloorDepthPrevious = inputList.get(0);

        for (int i = 1; i < inputList.size(); i++) {
            seaFloorDepthCurrent = inputList.get(i);
            if (seaFloorDepthCurrent > seaFloorDepthPrevious) {
                increaseCount++;
            }

            seaFloorDepthPrevious = seaFloorDepthCurrent;
        }

        return increaseCount;
    }

    /*
     Gets the number of times the sum of a three-measurement sliding
     window is larger than the previous sum.
     */
    private static int getNumOfIncreasedSum(ArrayList<Integer> inputList) {
        int sumOfPrevious = 0;
        int increaseCount = 0;

        // Store the sum of the first three measurements
        sumOfPrevious = inputList.get(0) + inputList.get(1) + inputList.get(2);

        /*
         Loop through the measurements starting at the index 1 and ending
         when there aren't enough to make a three-measurement sliding window.
         Compare the sum of the current measurements to the sum of the previous
         measurements.
         */
        for (int i = 1; i < inputList.size() - 2; i++) {
            int sumOfCurrent = 0;

            for (int j = i; j < i + 3; j++) {
                sumOfCurrent += inputList.get(j);
            }

            if (sumOfCurrent > sumOfPrevious) {
                increaseCount++;
            }

            sumOfPrevious = sumOfCurrent;
        }
        
        return increaseCount;
    }
}
