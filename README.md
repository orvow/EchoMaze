# Echo Maze: Ancient Energy Temple

A terminal based, first person dungeon exploration game developed in Java 25 for NSCC's PROG 1700 Logic & Programming course.

Explore an ancient temple, navigate mazes, collect three energy crystals, manage your energy supply, and defeat the Temple Guardian to escape.

The game will eventually feature an ASCII based 3D renderer using DDA raycasting. Initial development focuses on maze loading, movement, and core gameplay.

## Requirements

- Java JDK 25
- Linux terminal or Windows PowerShell

## Build and Run

Run the following commands from the project root directory.

**Compile:**

javac -d out src/App.java

**Run:**

java -cp out App

Those commands work on both Linux terminal and Windows PowerShell.

- `javac` compiles Java source code.
- `-d out` places compiled `.class` files in the `out/` directory.
- `-cp out` tells Java where to find compiled classes.
- `App` is the class containing the `main()` method.

## Project Structure

- `src/App.java` - Java source code and entry point.
- `res/` - Maze layouts and game resources.
- `out/` - Generated compilation output (ignored by Git).
- `README.md` - Project overview and build instructions.

All Java source code is contained in `src/App.java`.

## Development

The project is being developed incrementally, starting with top down maze navigation before introducing ASCII 3D rendering and advanced gameplay.

