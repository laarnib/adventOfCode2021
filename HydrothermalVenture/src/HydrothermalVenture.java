import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HydrothermalVenture {
    public static void main(String[] args) throws Exception {
        Scanner puzzleInput = new Scanner(Paths.get("input.txt"));
        ArrayList<ArrayList<Integer>> listOfCoordinates = new ArrayList<>();


        // Store the list of coordinates in an array list
        while(puzzleInput.hasNextLine()) {
            String line = puzzleInput.nextLine().replace(" -> ", ",");
            String[] parts = line.split(",");
            ArrayList<Integer> coordinate = new ArrayList<>();
            for (int i = 0; i < parts.length; i++) {
                coordinate.add(Integer.valueOf(parts[i]));
            }
            listOfCoordinates.add(coordinate);
        }

        // Find the bottom right position
        int[] bottomRightCoordinates = findCoordinatesOfBottomRight(listOfCoordinates);
        int row = bottomRightCoordinates[1] + 1;
        int column = bottomRightCoordinates[0 ] + 1;
        int[][] diagram = new int[row][column];



        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                diagram[i][j] = 0;
            }
        }

        // Plot the line segments
        plotLineSegments(diagram, listOfCoordinates);

        // Print diagram
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(diagram[i][j]);
            }
            System.out.println("");
        }

        int numOfPointsLinesOverlap = findOverlapLines(diagram, row, column);
        System.out.println(numOfPointsLinesOverlap);
    }

    public static int[] findCoordinatesOfBottomRight(ArrayList<ArrayList<Integer>> coordinates) {
        ArrayList<Integer> xCoords = new ArrayList<>();
        ArrayList<Integer> yCoords = new ArrayList<>();
        int[] position = new int[2];
        for (ArrayList<Integer> coordinate : coordinates) {
            xCoords.add(coordinate.get(0));
            xCoords.add(coordinate.get(2));
            yCoords.add(coordinate.get(1));
            yCoords.add(coordinate.get(3));
        }

        // Sort
        Collections.sort(xCoords);
        Collections.sort(yCoords);

        System.out.println("SOrted X: " + xCoords);
        System.out.println("Sorted Y: " + yCoords);
        position[0] = xCoords.get(xCoords.size() - 1);
        position[1] = yCoords.get(yCoords.size() - 1);

        System.out.println("X: " + position[0]);
        System.out.println("Y: " + position[1]);
        return position;
    }

    public static void plotLineSegments(int[][] diagram, ArrayList<ArrayList<Integer>> coords) {
        for (ArrayList<Integer> coord : coords) {
            if (coord.get(0) == coord.get(2)) {
                int column = coord.get(0);
                int row1 = coord.get(1);
                int row2 = coord.get(3);
                int start = 0;
                int end = 0;
                if (row1 > row2 ) {
                    start = row2;
                    end = row1;
                } else {
                    start = row1;
                    end = row2;
                }
                for (int row = start; row <= end; row++) {
                    for (int j = column; j == column; j++) {
                        int value = diagram[row][column];
                        diagram[row][column] = ++value;
                    }
                }
            } else if (coord.get(1) == coord.get(3)) {
                int row = coord.get(1);
                int col1 = coord.get(0);
                int col2 = coord.get(2);
                int start = 0;
                int end = 0;

                if (col1 > col2) {
                    start = col2;
                    end = col1;
                } else {
                    start = col1;
                    end = col2;
                }

                for (int i = row; i == row; i++) {
                    for (int column = start; column <= end; column++) {
                        int value = diagram[i][column];
                        diagram[i][column] = ++value;
                    }
                }
            } else {
                continue;
            }
        }
    }

    public static int findOverlapLines(int[][] diagram, int row, int column) {
        int count = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (diagram[i][j] > 1) {
                    count++;
                }
            }
        }

        return count;
    }
}
