import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class AdventOfCodeDay03 {
    public static void main(String[] args) throws Exception {

        Scanner puzzleInput = new Scanner(Paths.get("input.txt"));
        String line = "";
        ArrayList<String> binaryNumsArr = new ArrayList<>();
        ArrayList<String> oxygenGeneratorRating = new ArrayList<>();
        ArrayList<String> co2ScrubberRating = new ArrayList<>();
        int lifeSupportRating = 0;
        int powerConsumption = 0;

        int numOfBits = 0;

        while(puzzleInput.hasNextLine()) {
            binaryNumsArr.add(puzzleInput.nextLine());
        }

        // All numbers in the list have the same number of bits
        numOfBits = binaryNumsArr.get(0).length();

        // Power consumption of the submarine
        powerConsumption = getGammaRate(binaryNumsArr, numOfBits)
                * getEpsilonRate(binaryNumsArr, numOfBits);

        System.out.println("Power consumption of the submarine is " + powerConsumption);

        // Find the Oxygen Generator Rating
        oxygenGeneratorRating = getOxygenGeneratorRating(binaryNumsArr, 0, numOfBits);

        // Find the CO2 Scrubber Rating
        co2ScrubberRating = getCO2ScrubberRating(binaryNumsArr, 0, numOfBits);
        // Convert to a decimal value

        // Life support rating of the submarine
        lifeSupportRating = Integer.parseInt(oxygenGeneratorRating.get(0), 2)
                * Integer.parseInt(co2ScrubberRating.get(0), 2);
        System.out.println("Life Support Rating of the submarine is " + lifeSupportRating);
    }

    // Finds gamma rate
    public static int getGammaRate(ArrayList<String> arr, int totalBits) {
        String gammaRate = "";

        for (int i = 0; i < totalBits; i++) {
            gammaRate += getMostCommonBit(arr, i);
        }

        return Integer.parseInt(gammaRate, 2);
    }

    // Finds epsilon rate
    public static int getEpsilonRate(ArrayList<String> arr, int totalBits) {
        String epsilonRate = "";

        for (int i = 0; i < totalBits; i++) {
            epsilonRate += getLeastCommonBit(arr, i);
        }

        return Integer.parseInt(epsilonRate, 2);
    }

    // Finds the oxygen generator rating
    public static ArrayList<String> getOxygenGeneratorRating(ArrayList<String> arr, int currBit, int totalBits) {
        ArrayList<String> oxygenGeneratorRate = new ArrayList<>();
        Character mostCommonBit;

        if(arr.size() == 1) {
            return arr;
        } else {
            mostCommonBit = getMostCommonBit(arr, currBit);

            for (int i = 0; i < arr.size(); i++) {
                String value = arr.get(i);
                if (value.charAt(currBit) == mostCommonBit) {
                    oxygenGeneratorRate.add(value);
                }
            }

            return getOxygenGeneratorRating(oxygenGeneratorRate, ++currBit, totalBits);
        }
    }

    // Finds the CO2 Scrubber Rating
    public static ArrayList<String> getCO2ScrubberRating(ArrayList<String> arr, int currBit, int totalBits) {
        ArrayList<String> co2ScrubberRate = new ArrayList<>();
        Character leastCommonBit;

        if(arr.size() == 1) {
            return arr;
        } else {
            leastCommonBit = getLeastCommonBit(arr, currBit);

            for (int i = 0; i < arr.size(); i++) {
                String value = arr.get(i);
                if (value.charAt(currBit) == leastCommonBit) {
                    co2ScrubberRate.add(value);
                }
            }

            return getCO2ScrubberRating(co2ScrubberRate, ++currBit, totalBits);
        }
    }

    // Gets the most common bit in a current position of a list of binary numbers
    public static Character getMostCommonBit(ArrayList<String> arr, int currBit) {
        int countOnes = 0;
        int countZeros = 0;

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).charAt(currBit) == '1') {
                countOnes++;
            } else {
                countZeros++;
            }
        }

        return countOnes == countZeros || countOnes > countZeros ? '1' : '0';
    }

    // Gets the least common bit in a current position of a list of binary numbers
    public static Character getLeastCommonBit(ArrayList<String> arr, int currBit) {
        int countOnes = 0;
        int countZeros = 0;

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).charAt(currBit) == '1') {
                countOnes++;
            } else {
                countZeros++;
            }
        }

        return countOnes == countZeros || countOnes > countZeros ? '0' : '1';
    }
}