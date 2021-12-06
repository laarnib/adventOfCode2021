import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class GiantSquid {

    public static void main(String[] args) throws Exception {
        Scanner puzzleInput = new Scanner(Paths.get("input.txt"));
        ArrayList<String> drawnNumbers = new ArrayList<>();
        HashMap<Integer, ArrayList<ArrayList<String>>> bingoCards = new HashMap<>();

        // Get the list of drawn numbers
        drawnNumbers = getListOfDrawnNumbers(puzzleInput);

        // Create a hashmap of the bingo cards
        bingoCards = createHashMap(puzzleInput);

        // Get the first Winning bingo card
        //getFirstWinningBoard(bingoCards, drawnNumbers);

        // Get the last Winning bingo card
        getLastWinningBoard(bingoCards, drawnNumbers);

        puzzleInput.close();
    }




    // Get the list of drawn numbers from the input file.
    public static ArrayList<String> getListOfDrawnNumbers(Scanner puzzleInput) {
        ArrayList<String> drawnNumbers = new ArrayList<>();

        if (puzzleInput.hasNextLine()) {
            String[] list = puzzleInput.nextLine().split(",");
            int size = list.length;

            for (int i = 0; i < size; i++) {
                drawnNumbers.add(list[i]);
            }
        }

        return drawnNumbers;
    }

    // Create a HashMap of the bingo cards
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

    public static void getLastWinningBoard(HashMap<Integer, ArrayList<ArrayList<String>>> bingoCards, ArrayList<String> drawnNumbers) {
        //System.out.println(bingoCards);
        // Draw first five numbers and check if there is a winner
        String winningValue = "";
        String drawNumber = "";
        int winningCard = -1;
        HashMap<Integer, ArrayList<ArrayList<String>>> copy = bingoCards;
        Set<Integer> hashKeys = copy.keySet();

        //System.out.println("Draw first five numbers");
        for (int i = 0; i < 5; i++) {
            drawNumber = drawnNumbers.get(i);
            //System.out.println("Draw Number: " + drawNumber);
            for (Integer key : hashKeys) {
                //System.out.println("\t Bingo card #" + key + ": ");
                for (ArrayList<String> row : bingoCards.get(key)) {
                    for (String ball : row) {
                        if (ball.equals(drawNumber)) {
                            int indexBall = row.indexOf(ball);
                            row.set(indexBall, "X");
                        }
                    }
                   // System.out.println("\t\t" + row);
                }
            }
        }

        // Check for winner
        winningCard = checkWinner(copy);

        if (winningCard != -1) {
            //System.out.println("Winning card: " + winningCard);
            //System.out.println("Winning value: " + winningValue);
            //copy.remove(winningCard);
            winningCard = -1;
        } else {
            ArrayList<ArrayList<String>> lastWinningCard = new ArrayList<>();

            // Draw more numbers
            int index = 5;
            while (copy.size() > 0) {
                int keyToPass = 0;
                ArrayList<Integer> keysToRemove = new ArrayList<>();
                if (index > drawnNumbers.size()) {
                    break;
                }

                if (copy.size() == 0) {
                    break;
                }

                drawNumber = drawnNumbers.get(index);
                //System.out.println("Draw numbers index : " + index);
                //System.out.println("Draw number: " + drawNumber);

                for (Integer key : hashKeys) {
                    //System.out.println("\t Bingo card #" + key + ": ");
                    if (copy.size() == 1) {
                        keyToPass = key;
                    }
                    for (ArrayList<String> row : bingoCards.get(key)) {
                        for (String ball : row) {
                            if (ball.equals(drawNumber)) {
                                int indexBall = row.indexOf(ball);
                                row.set(indexBall, "X");
                            }
                        }
                        //System.out.println("\t\t" + row);
                    }
                }

                if (copy.size() > 1) {
                    keysToRemove = removeAllWinners(copy);
                }

                if (copy.size() == 1) {
                    boolean isWinner = isWinningCard(copy.get(keyToPass));
                    if(isWinner) {
                        winningValue = drawNumber;
                        lastWinningCard = copy.get(keyToPass);
                        int score = getScore(lastWinningCard, winningValue);
                        System.out.println(score);
                        break;
                    }
                }

                for (Integer key : keysToRemove) {
                    copy.remove(key);
                }

                index++;
            }
        }
    }

    public static ArrayList<Integer> removeAllWinners(HashMap<Integer, ArrayList<ArrayList<String >>> copyBingoCard) {
        ArrayList<Integer> keysToRemove = new ArrayList<>();

        Set<Integer> keys = copyBingoCard.keySet();
        for (Integer key : keys) {
            boolean isWinner = isWinningCard(copyBingoCard.get(key));

            if (isWinner) {
                keysToRemove.add(key);
            }
        }
        //System.out.println(keysToRemove);
        return keysToRemove;
    }


    public static boolean isWinningCard(ArrayList<ArrayList<String>> card) {
        boolean isWinner = isWinningHorizontal(card);

        if (!isWinner) {
            isWinner = isWinningVertical(card);
        }

        return isWinner;
    }

    public static boolean isWinningHorizontal(ArrayList<ArrayList<String>> card) {
        int count = 0;
        int size = card.get(0).size();
        //System.out.println(card);
        for (ArrayList<String> row : card) {
            for (String ball : row) {
                if (ball.equals("X")) {
                    count++;
                }

                if (count == size) {
                    return true;

                }
            }
            count = 0;
        }

        return false;
    }

    public static boolean isWinningVertical(ArrayList<ArrayList<String>> card) {
        int count = 0;
        int size = card.get(0).size();
        int foundAtColumn = -1;
        int column = 0;
        while(column < size ) {
            for (int j = 0; j < size; j++) {
                if(card.get(j).get(column).equals("X")) {
                    count++;
                }
            }

            if (count == size) {
                return true;
            }

            count = 0;
            column++;
        }
        return false;
    }
    public static void getFirstWinningBoard(HashMap<Integer, ArrayList<ArrayList<String>>> bingoCards, ArrayList<String> drawnNumbers) {
        HashMap<Integer, ArrayList<ArrayList<String>>> copy = bingoCards;
        int hasWinner = -1;
        String value = "";
        int score = 0;
        int index = 0;
        while (hasWinner == -1) {
            System.out.println("Inside while: " + value);
            if (index < drawnNumbers.size()) {
                value = drawnNumbers.get(index);
                System.out.println("Draw Number: " + value);
            }
            Set<Integer> setOfKeySet = copy.keySet();
            for(Integer key : setOfKeySet) {
                System.out.println("\t Bingo card #" + key + ": ");
                for (ArrayList<String> nums : copy.get(key)) {
                    for (String ball : nums) {
                        if (ball.equals(value)) {
                            int indexBall = nums.indexOf(ball);
                            nums.set(indexBall, "X");
                        }
                    }
                    System.out.println("\t\t" + nums);
                }

            }

            hasWinner = checkWinner(copy);

            if (hasWinner > -1) {
                System.out.println("Inside hasWinner");
                System.out.println("Winning key: " + hasWinner);
                break;
            }

            index++;
        }
        System.out.println(value);
        score = getScore(copy.get(hasWinner), value);
        System.out.println(score);
    }
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




        for(Integer key : setOfKeySet) {
            int rowSize = bingoCard.get(key).size();
            int columnSize = bingoCard.get(key).get(0).size();
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

    public static int getScore(ArrayList<ArrayList<String>> boards, String value) {
        int sum = 0;

        for (ArrayList<String> board : boards) {
            for (String num : board) {
                if (num != "X") {
                    sum += Integer.valueOf(num);
                }
            }
        }
        return sum * Integer.valueOf(value);
    }
}
