import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class App {

  enum FacingDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST
  }

  static class Player {
    int row;
    int column;
    int energy;
    FacingDirection facingDirection = FacingDirection.EAST;
  }

  public static void main(String[] args) throws FileNotFoundException {
    Scanner keyboardInput = new Scanner(System.in);

    // Welcome the player
    System.out.println("Enter your name");
    String playerName = keyboardInput.nextLine();
    System.out.printf("Welcome %s!\n", playerName);

    // Load the mazes file and select a random maze from it
    ArrayList<char[][]> mazes = loadMazes("res/mazes.txt");
    char[][] activeMaze = selectMaze(mazes);

    // Find the player starting position of the active maze
    int[] currentPos = findStart(activeMaze);

    // Prompt the player to select their difficulty
    String selectedDifficulty = "";
    while (!selectedDifficulty.equals("1") &&
        !selectedDifficulty.equals("2") &&
        !selectedDifficulty.equals("3")) {
      System.out
          .println("Select your difficulty:\n1. Easy   (60 energy)\n2. Normal (50 energy)\n3. Hard   (40 energy)");
      selectedDifficulty = keyboardInput.nextLine().strip().toUpperCase();
    }

    // Create the player
    Player player = new Player();
    player.row = currentPos[0];
    player.column = currentPos[1];
    switch (selectedDifficulty) {
      case "1" -> player.energy = 60;
      case "2" -> player.energy = 50;
      case "3" -> player.energy = 40;
    }

    // If the second maze is selected we face south initially
    if (activeMaze == mazes.get(1)) {
      player.facingDirection = FacingDirection.SOUTH;
    }

    // Print the initial active maze once
    printFrame(activeMaze, player);

    // Clear the original player marker since position is now tracked by Player
    activeMaze[player.row][player.column] = '.';

    System.out.printf("Initial facing direction: %s\nPlayers current position: [row # %d][column # %d]\n",
        player.facingDirection,
        currentPos[0],
        currentPos[1]);

    // Enter main loop
    boolean isRunning = true;
    while (isRunning) {

      System.out.println(
          "Enter a command: Q to quit, A to turn left, D to turn right, W to move forward, S to move backward");

      String command = keyboardInput.nextLine().strip().toUpperCase();

      boolean hasMoved = false;
      if (command.equals("Q")) {
        isRunning = false;
      } else {
        hasMoved = processTurn(command, activeMaze, player);
      }
      if (hasMoved) {
        player.energy--;
      }

      printFrame(activeMaze, player);

      // Print the current facing direction
      if (isRunning) {
        System.out.printf("Facing Direction: %s\nEnergy: %d\n", player.facingDirection, player.energy);
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

  static void printFrame(char[][] maze, Player player) {
    for (int row = 0; row < maze.length; row++) {
      char[] displayRow = maze[row].clone();
      if (player.row == row) {
        displayRow[player.column] = 'P';
      }
      System.out.println(displayRow);
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

  static boolean processTurn(String command, char[][] maze, Player player) {
    boolean moved = false;
    int nextRow = player.row;
    int nextColumn = player.column;

    if (command.equals("A")) {
      System.out.println("Turning left");
      switch (player.facingDirection) {
        case NORTH -> player.facingDirection = FacingDirection.WEST;
        case EAST -> player.facingDirection = FacingDirection.NORTH;
        case SOUTH -> player.facingDirection = FacingDirection.EAST;
        case WEST -> player.facingDirection = FacingDirection.SOUTH;
      }
    } else if (command.equals("D")) {
      System.out.println("Turning right");
      switch (player.facingDirection) {
        case NORTH -> player.facingDirection = FacingDirection.EAST;
        case EAST -> player.facingDirection = FacingDirection.SOUTH;
        case SOUTH -> player.facingDirection = FacingDirection.WEST;
        case WEST -> player.facingDirection = FacingDirection.NORTH;
      }
    }
    // Check if the player has energy
    if (player.energy == 0 && (command.equals("W") || command.equals("S"))) {
      System.out.println("No energy left!");
      return false;
    }
    // Handle movement
    if (command.equals("W")) {
      switch (player.facingDirection) {
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
    } else if (command.equals("S")) {
      switch (player.facingDirection) {
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
    if (command.equals("W") || command.equals("S")) {
      if (canMove(maze, nextRow, nextColumn)) {
        player.row = nextRow;
        player.column = nextColumn;
        moved = true;
      } else {
        System.out.println("Movement blocked!");
        moved = false;
      }
    }
    return moved;
  }
}
