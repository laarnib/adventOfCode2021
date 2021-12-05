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
        Scanner puzzleInput = new Scanner(Paths.get("sample2.txt"));
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

        // Create bingo card
        bingoCard = createHashMap(puzzleInput);
        int index = 0;

        // Play bingo
        while (hasWinner == -1) {

            if (index < drawnNumbers.size()) {
               value = drawnNumbers.get(index);
            }
            Set<Integer> setOfKeySet = bingoCard.keySet();
            for(Integer key : setOfKeySet) {
                for (ArrayList<String> nums : bingoCard.get(key)) {
                    for (String ball : nums) {
                        if (ball.equals(value)) {
                            int indexBall = nums.indexOf(ball);
                            nums.set(indexBall, "X");
                        }
                    }
                }
            }

            hasWinner = checkVerticalWinner(bingoCard);

            if (hasWinner > -1) {
                break;
            } else {
                hasWinner = checkWinnerHorizontal(bingoCard);

                if (hasWinner > -1) {
                    break;
                }
            }

            index++;
        }

        System.out.println(value);
        System.out.println(hasWinner);
        System.out.println(bingoCard);

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

    public static int checkVerticalWinner(HashMap<Integer, ArrayList<ArrayList<String>>> bingoCard) {
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

    public static int checkWinnerHorizontal(HashMap<Integer, ArrayList<ArrayList<String>>> bingoCard) {
        Set<Integer> setOfKeySet = bingoCard.keySet();

        int count = 0;
        int winner = -1;



        for(Integer key : setOfKeySet) {
            int row = 0;
            int column = 0;
            int rowSize = bingoCard.get(key).get(row).size();
            for (String num : bingoCard.get(key).get(row)) {
                if (num.equals("X")) {
                        break;
                }
                column++;
            }
            row++;
            count++;
            column--;
            // check the rest of the column
            int totalRows = bingoCard.get(key).size();
            while (row < totalRows){
                System.out.println("row :" + row);
                if (bingoCard.get(key).get(row).get(column).equals("X")) {
                    count++;
                    row++;
                } else {
                    break;
                }
            }

            if (count == totalRows) {
                winner = key;
                return winner;
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
