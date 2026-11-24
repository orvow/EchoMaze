# Development notes

## TODO
- [x] Refactor main into smaller methods (loadMazes, selectMaze, findStart, printFrame)
- [x] Implement main game loop
- [x] Implement A/D rotation with direction wraparound
- [x] Implement W/S movement based on facing direction
- [x] Implement collision detection with canMove()
- [x] Refactor facingDirection handling to use an enum instead for better readability
- [x] Implement processTurn and extract player movement, rotation, and collision handling out of main and into processTurn
- [ ] Update player position on the printed maze
- [ ] Implement difficulty selection and starting energy

## Learned
- .strip() Removes leading or trailing whitespaces from a string. 
- SomeEnum.values() returns an array of enum constants in their declared order. An index can be used to access a specific constant (e.g., `FacingDirection.values()[2]` returns `SOUTH`).
