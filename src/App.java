import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class App {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner keyboardInput = new Scanner(System.in);

    // Welcome the player
    System.out.println("Enter your name");
    String playerName = keyboardInput.nextLine();
    System.out.printf("Welcome %s!%n", playerName);

    // Load the mazes file and select a random maze from it
    ArrayList<char[][]> mazes = loadMazes("res/mazes.txt");
    char[][] activeMaze = selectMaze(mazes);

    // Print the initial active maze once
    printFrame(activeMaze);

    int[] startPos = findStart(activeMaze);
    int facingDirection = 1;

    System.out.printf("Initial facing direction: %d%nPlayer starting position: [row # %d][column # %d]%n",
        facingDirection,
        startPos[0],
        startPos[1]);

    boolean isRunning = true;
    while (isRunning) {
      System.out.println("Enter a command: Q to quit, A to turn left, D to turn right");
      String keyPress = keyboardInput.nextLine().strip().toUpperCase();
      if (keyPress.equals("Q")) {
        isRunning = false;
      } else if (keyPress.equals("A")) {
        if (facingDirection == 0) {
          facingDirection = 3;
        } else {
          facingDirection--;
        }
      } else if (keyPress.equals("D")) {
        if (facingDirection == 3) {
          facingDirection = 0;
        } else {
          facingDirection++;
        }
      }
      System.out.println(facingDirection);
    }
    keyboardInput.close();
  }

  static int[] findStart(char[][] map) {
    // Find the players starting coordinates
    int[] startPos = new int[2];
    for (int row = 0; row < map.length; row++) {
      for (int column = 0; column < map[row].length; column++) {
        char character = map[row][column];
        if (character == 'P') {
          startPos[0] = row;
          startPos[1] = column;
        }
      }
    }
    return startPos;
  }

  static ArrayList<char[][]> loadMazes(String filePath) throws FileNotFoundException {
    Scanner fileInput = new Scanner(new File(filePath));
    ArrayList<String> mazeRows = new ArrayList<>();
    ArrayList<ArrayList<String>> mazes = new ArrayList<ArrayList<String>>();

    // Load mazes separated by blank lines
    while (fileInput.hasNextLine()) {
      String line = fileInput.nextLine();
      if (line.isEmpty()) {
        mazes.add(mazeRows);
        mazeRows = new ArrayList<>();
      } else {
        mazeRows.add(line);
      }
    }
    mazes.add(mazeRows);

    // Convert them to a char[][] and return them
    ArrayList<char[][]> allMazes = new ArrayList<>();

    for (int m = 0; m < mazes.size(); m++) {
      ArrayList<String> currentMaze = mazes.get(m);
      char[][] charGrid = new char[currentMaze.size()][];

      for (int i = 0; i < currentMaze.size(); i++) {
        String row = currentMaze.get(i);
        charGrid[i] = row.toCharArray();
      }
      allMazes.add(charGrid);
    }
    fileInput.close();

    return allMazes;
  }

  static char[][] selectMaze(ArrayList<char[][]> mazes) {
    Random random = new Random();
    int randomMaze = random.nextInt(mazes.size());
    System.out.printf("Selected maze #%d%n", randomMaze);
    char[][] maze = mazes.get(randomMaze);
    return maze;
  }

  static void printFrame(char[][] maze) {
    for (int row = 0; row < maze.length; row++) {
      System.out.println(maze[row]);
    }
  }
}
