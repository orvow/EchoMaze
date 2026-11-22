import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class App {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner input = new Scanner(new File("res/maze.txt"));
    ArrayList<String> mazeRows = new ArrayList<>();
    ArrayList<ArrayList<String>> mazes = new ArrayList<ArrayList<String>>();
    Random random = new Random();

    // Load mazes seperated by blank lines
    while (input.hasNextLine()) {
      String line = input.nextLine();
      if (line.isEmpty()) {
        mazes.add(mazeRows);
        mazeRows = new ArrayList<>();
      } else {
        mazeRows.add(line);
      }
    }
    mazes.add(mazeRows);

    // Randomly select one of the mazes
    int randomMap = random.nextInt(mazes.size());
    System.out.printf("Randomly selected map is: map# %d%n", randomMap);
    ArrayList<String> activeMaze = mazes.get(randomMap);

    // Convert the selected maze into a 2D character array
    char[][] charGrid = new char[activeMaze.size()][activeMaze.get(0).length()];

    for (int i = 0; i < activeMaze.size(); i++) {
      String row = activeMaze.get(i);
      charGrid[i] = row.toCharArray();
    }

    for (String row : activeMaze) {
      System.out.println(row);
    }

    int playerRow = 0;
    int playerColumn = 0;
    int playerFacingDirection = 0; // 0 = North, 1 = East, 2 = South, 3 = West

    // Find the players starting coordinates
    for (int row = 0; row < charGrid.length; row++) {
      for (int column = 0; column < charGrid[row].length; column++) {
        char character = charGrid[row][column];
        if (character == 'P') {
          playerRow = row;
          playerColumn = column;
        }
      }
    }
    System.out.printf("Player is at: Row: %d%nColumn: %d%n", playerRow, playerColumn);
    input.close();
  }

}
