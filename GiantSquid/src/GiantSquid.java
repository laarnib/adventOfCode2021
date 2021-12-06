import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

//Set<Integer> setOfKeySet = bingoCard.keySet();

        /* Looping through hashmap
        for(Integer key : setOfKeySet) {
            System.out.println("\n Key #" + key);

            for (ArrayList<String> nums : bingoCard.get(key)) {
                System.out.println("\t\t" + nums);
            }
        } */

public class GiantSquid {

    public static void main(String[] args) throws Exception {
        Scanner puzzleInput = new Scanner(Paths.get("input.txt"));
        ArrayList<String> drawnNumbers = new ArrayList<>();
        HashMap<Integer, ArrayList<ArrayList<String>>> bingoCard = new HashMap<>();

        int hasWinner = -1;
       // boolean hasWinner = false;
        String value = "";
        int score = 0;


        // Get the list of drawn numbers
        if (puzzleInput.hasNextLine()) {
            String[] list = puzzleInput.nextLine().split(",");
            int size = list.length;

            for (int i = 0; i < size; i++) {
                drawnNumbers.add(list[i]);
            }
        }

        System.out.println("Drawn Numbers: " + drawnNumbers);
        System.out.println("");

        // Create bingo card
        bingoCard = createHashMap(puzzleInput);
        int index = 0;

        System.out.println("Bing cards:");
        // Play bingo
        while (hasWinner == -1) {
            System.out.println("Inside while: " + value);
            if (index < drawnNumbers.size()) {
               value = drawnNumbers.get(index);
                System.out.println("Draw Number: " + value);
            }
            Set<Integer> setOfKeySet = bingoCard.keySet();
            for(Integer key : setOfKeySet) {
                System.out.println("\t Bingo card #" + key + ": ");
                for (ArrayList<String> nums : bingoCard.get(key)) {
                    for (String ball : nums) {
                        if (ball.equals(value)) {
                            int indexBall = nums.indexOf(ball);
                            nums.set(indexBall, "X");
                        }
                    }
                    System.out.println("\t\t" + nums);
                }

            }

            hasWinner = checkWinner(bingoCard);

            if (hasWinner > -1) {
                System.out.println("Inside hasWinner");
                System.out.println("Winning key: " + hasWinner);
                break;
            }

            index++;
        }
        System.out.println(value);
        score = getSum(bingoCard.get(hasWinner)) * Integer.valueOf(value);
        System.out.println(score);
        puzzleInput.close();
    }

    public static HashMap<Integer, ArrayList<ArrayList<String>>> createHashMap(Scanner puzzleInput) {
        HashMap<Integer, ArrayList<ArrayList<String>>> bingoCard = new HashMap<>();
        ArrayList<ArrayList<String>> outerArrayList = new ArrayList<>();
        int key = 0;

        while(puzzleInput.hasNextLine()) {
            String currentLine = puzzleInput.nextLine();
            if (currentLine.isEmpty() == false) {
                String numbers = currentLine.replaceAll("\\s+",",");
                String[] numbersArr = numbers.split(",");
                ArrayList<String> innerArrayList = new ArrayList<>();
                for (int i = 0; i < numbersArr.length; i++) {
                    if (numbersArr[i].equals("") == false) {
                        innerArrayList.add(numbersArr[i]);
                    }
                }

                outerArrayList.add(innerArrayList);
            } else {
                outerArrayList = new ArrayList<>();
                key++;
                continue;
            }

            bingoCard.put(key, outerArrayList);
        }

        return bingoCard;
    }

    /*public static int playBingo(HashMap<Integer, ArrayList<ArrayList<String>>> bingoCard, ArrayList<String> drawnNumbers) {
        int score = 0;
        int drawnNumbersCount = drawnNumbers.size();

        for (int i = 0; i < drawnNumbersCount; i++) {
            for (H=)
        }

        return score;
    }*/

    public static int checkWinner(HashMap<Integer, ArrayList<ArrayList<String>>> bingoCard) {
        int winner = checkWinnerHorizontal(bingoCard);

       if (winner == -1) {
            winner = checkWinnerVertical(bingoCard);
        } else {
            return winner;
        }

        return winner;
    }

    public static int checkWinnerHorizontal(HashMap<Integer, ArrayList<ArrayList<String>>> bingoCard) {
        Set<Integer> setOfKeySet = bingoCard.keySet();

        int count = 0;
        int winner = -1;

        for(Integer key : setOfKeySet) {

            for (ArrayList<String> nums : bingoCard.get(key)) {
                int size = nums.size();
                for (String ball : nums) {
                    if (ball.equals("X")) {
                        count++;
                    }
                }

                if (count == size) {
                    winner = key;

                } else {
                    count = 0;
                }
            }
        }

        return winner;
    }

    public static int checkWinnerVertical(HashMap<Integer, ArrayList<ArrayList<String>>> bingoCard) {
        Set<Integer> setOfKeySet = bingoCard.keySet();
        int winner = -1;
        int count = 0;

        int rowSize = bingoCard.get(1).size();
        int columnSize = bingoCard.get(1).get(0).size();


        for(Integer key : setOfKeySet) {
            int foundXAtColumn = -1;
            // Find X at first row of bingo card, if X is found
            // store the index of the column.  If no X is found
            // return winner = -1;
            for (int i = 0; i < columnSize; i++ ) {
                if (bingoCard.get(key).get(0).get(i).equals("X")) {
                    foundXAtColumn = i;
                    count++;
                    break;
                }
            }
            //System.out.println("Checking Vertical: ");
            //System.out.println("\t\t" + count);
            if (foundXAtColumn > -1) {
                // Check remaining rows and if the column where X is found
                // matches the first row, increment count.  When count is
                // equal to five, winner = key of hashmap where the winner
                // is found
                for (int row = 1; row < rowSize; row++) {
                    for (int col = 0; col < columnSize; col++) {
                        if (bingoCard.get(key).get(row).get(col).equals("X") && col == foundXAtColumn) {
                            count++;
                            //System.out.println("\t\t" + count);
                        }
                    }
                }
            } else {
                continue;
            }

            //System.out.println("Total count: " + count);
            if (count == rowSize) {
                winner = key;
            } else {
                count = 0;
            }
        }

        return winner;
    }

    public static int getSum(ArrayList<ArrayList<String>> boards) {
        int sum = 0;

        for (ArrayList<String> board : boards) {
            for (String num : board) {
                if (num != "X") {
                    sum += Integer.valueOf(num);
                }
            }
        }
        return sum;
    }
}
