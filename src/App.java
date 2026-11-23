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

    // Find the player starting position of the active maze
    int[] currentPos = findStart(activeMaze);

    // Initialize facing direction
    enum FacingDirection {
      NORTH,
      EAST,
      SOUTH,
      WEST
    }
    FacingDirection facingDirection = FacingDirection.NORTH;

    // If the second maze is selected we face south initially
    if (activeMaze == mazes.get(1)) {
      facingDirection = FacingDirection.SOUTH;
    }

    System.out.printf("Initial facing direction: %s%nPlayers current position: [row # %d][column # %d]%n",
        facingDirection,
        currentPos[0],
        currentPos[1]);

    boolean isRunning = true;
    while (isRunning) {
      System.out.println(
          "Enter a command: Q to quit, A to turn left, D to turn right, W to move forward, S to move backward");

      String keyPress = keyboardInput.nextLine().strip().toUpperCase();

      int nextRow = currentPos[0];
      int nextColumn = currentPos[1];

      if (keyPress.equals("Q")) {
        isRunning = false;
      }
      // Handle rotation

      if (keyPress.equals("A")) {
        System.out.println("Turning left");
        switch (facingDirection) {
          case NORTH -> facingDirection = FacingDirection.WEST;
          case EAST -> facingDirection = FacingDirection.NORTH;
          case SOUTH -> facingDirection = FacingDirection.EAST;
          case WEST -> facingDirection = FacingDirection.SOUTH;
        }
      } else if (keyPress.equals("D")) {
        System.out.println("Turning right");
        switch (facingDirection) {
          case NORTH -> facingDirection = FacingDirection.EAST;
          case EAST -> facingDirection = FacingDirection.SOUTH;
          case SOUTH -> facingDirection = FacingDirection.WEST;
          case WEST -> facingDirection = FacingDirection.NORTH;
        }
      }
      // Handle movement
      if (keyPress.equals("W")) {
        switch (facingDirection) {
          case NORTH -> {
            nextRow--;
            System.out.println("Moving north");
          }
          case EAST -> {
            nextColumn++;
            System.out.println("Moving east");
          }
          case SOUTH -> {
            nextRow++;
            System.out.println("Moving south");
          }
          case WEST -> {
            nextColumn--;
            System.out.println("Moving west");
          }
        }
      } else if (keyPress.equals("S")) {
        switch (facingDirection) {
          case SOUTH -> {
            nextRow--;
            System.out.println("Moving north");
          }
          case WEST -> {
            nextColumn++;
            System.out.println("Moving east");
          }
          case NORTH -> {
            nextRow++;
            System.out.println("Moving south");
          }
          case EAST -> {
            nextColumn--;
            System.out.println("Moving west");
          }
        }

      }
      // Handle collision
      if (keyPress.equals("W") || keyPress.equals("S")) {
        if (canMove(activeMaze, nextRow, nextColumn)) {
          currentPos[0] = nextRow;
          currentPos[1] = nextColumn;
        } else {
          System.out.println("Movement blocked!");
        }
      }

      // Print the current facing direction
      if (isRunning) {
        System.out.printf("Facing Direction: %s%n", facingDirection);
      }
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

  static boolean canMove(char[][] maze, int row, int col) {
    if (row < 0 || row >= maze.length) {
      return false;
    } else if (col < 0 || col >= maze[row].length) {
      return false;
    }
    char character = maze[row][col];
    if (character == '#') {
      return false;
    }
    return true;
  }
}
