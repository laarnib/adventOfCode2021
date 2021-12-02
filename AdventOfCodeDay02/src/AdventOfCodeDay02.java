import java.nio.file.Paths;
import java.util.Scanner;

public class AdventOfCodeDay02 {

    public static void main(String[] args) throws Exception {
        Scanner puzzleInput = new Scanner(Paths.get("input.txt"));
        int horizontalPos = 0;
        int depth = 0;
        int aim = 0;

        while (puzzleInput.hasNextLine()) {
            String row = puzzleInput.nextLine();
            String[] parts = row.split(" ");

            String position = parts[0];
            int units = Integer.valueOf(parts[1]);

            //Comment out lines that calculates aim to get answer of part 1
            switch(position.toLowerCase()) {
                case "forward" :
                    horizontalPos += units;
                    depth += aim * units; 
                    break;
                case "down" :
                    // depth += units;  // Uncomment to get answer of part 1
                    aim += units;
                    break;
                case "up" :
                   // depth -= units; // Uncomment to get answer of part 1
                    aim -= units;
                    break;
                default :
                    System.out.println("Unknown");
                    break;
            }
        }
        System.out.println(horizontalPos * depth);
    }
}