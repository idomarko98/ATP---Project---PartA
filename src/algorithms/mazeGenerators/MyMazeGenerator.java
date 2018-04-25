package algorithms.mazeGenerators;


import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Maze Generator using Prims algorithm
 */
public class MyMazeGenerator extends AMazeGenerator {
    /** Generate maze using Prims algorithm
     * @param row    - size of mazes row
     * @param column - size of mazes column
     * @return Maze
     */
    @Override
    public Maze generate(int row, int column) {
        if (row < 3 || column < 3) { //input validation
            try {
                throw new Exception("The Maze must be at least 3X3");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
        int[][] map; // Map of the maze
        Stack<Position> walls; // Stack of walls
        boolean[][] visitedPositions = new boolean[row][column]; //boolean[][] the indicates rather a position has been visited or not
        Position startPosition, endPosition, tempPosition;
        walls = new Stack<>();
        Random rand = new Random();
        startPosition = new Position(rand.nextInt(row - 2) + 1, 0); //Start at left side
        map = new int[row][column];
        fillWithWalls(map); //Fill the map with 1's (walls)
        map[startPosition.getRowIndex()][startPosition.getColumnIndex()] = 0; //Make start position to zero
        visitFrame(visitedPositions, map); //Mark all the frame with 1's (walls)
        addWallsForPosition(startPosition, walls, map, visitedPositions); //Add walls
        //While the walls stack is not empty, remove one from the stack and check if it's changeable,
        //                          if so make it a path and insert it surrounding walls into walls stack
        while (!walls.isEmpty()) {
            tempPosition = walls.pop();
            if (changeable(tempPosition, map)) {
                map[tempPosition.getRowIndex()][tempPosition.getColumnIndex()] = 0;
                addWallsForPosition(tempPosition, walls, map, visitedPositions);
            }
        }
        endPosition = pickEnd(map);
        return new Maze(startPosition, endPosition, map);
    }

    /** Function that picks random goal position
     * @param map  - map of the maze
     * @return - Random goal position
     */
    private Position pickEnd(int[][] map) {
        Random rand = new Random();
        int randInt = 0;
        int timeOut = 0; //time limit
        int col = map[0].length - 1;
        boolean picked = false;
        while (!picked && timeOut < map.length){ //Random pick until the pick is fit or time is out
            timeOut++;
            randInt = rand.nextInt(map.length-2) + 1;
            if(map[randInt][col-1] == 0){
                map[randInt][col] = 0;
                picked = true;
            }
        }
        if(!picked) //Reached to time limit
            return pickEndIterative(map);
        return  new Position(randInt,col);

    }

    /** Function that returns Goal position by checking iteratively
     * @param map map of the maze
     * @return Iterative picked Goal Position
     */
    private Position pickEndIterative(int[][] map) {
        int col = map[0].length - 1;
        for (int i = 0; i < map.length; i++) {
            if (map[i][col - 1] == 0) {
                map[i][col] = 0;
                return new Position(i, col);
            }
        }
        return null;
    }

    /**
     * Function that returns rather a path is changeable or not. A position is changeable if it has less than 2 surrounding paths
     * @param tempPosition - Position to check if changeable
     * @param map - Map of the maze
     * @return true if changeable and false otherwise
     */
    private boolean changeable(Position tempPosition, int[][] map) {
        int amountOfZeros = 0;
        //Checking up
        if (map[tempPosition.getRowIndex() - 1][tempPosition.getColumnIndex()] == 0)
            amountOfZeros++;
        //Checking down
        if (map[tempPosition.getRowIndex() + 1][tempPosition.getColumnIndex()] == 0)
            amountOfZeros++;
        //Checking left
        if (map[tempPosition.getRowIndex()][tempPosition.getColumnIndex() - 1] == 0)
            amountOfZeros++;
        //Checking right
        if (map[tempPosition.getRowIndex()][tempPosition.getColumnIndex() + 1] == 0)
            amountOfZeros++;
        return amountOfZeros < 2;
    }

    /** Function that marks all the frame as visited
     * @param visitedPosition - Metrix of visited positions
     * @param map - Map of the maze
     */
    private void visitFrame(boolean[][] visitedPosition, int[][] map) {
        for (int i = 0; i < map.length; i++) { //Go over left and right columns
            visitedPosition[i][0] = true;
            visitedPosition[i][map[0].length - 1] = true;
        }
        for (int i = 1; i < map[0].length - 1; i++) { //Go over top and bottom rows
            visitedPosition[0][i] = true;
            visitedPosition[map.length - 1][i] = true;
        }
    }

    /** Function that adds walls that hasn't been visited and can be added to wall list, for a specific position
     * @param currPosition - The current position for which we are adding the walls
     * @param walls - The walls Stack
     * @param map - Map of the maze
     * @param visited - Visited position metrix
     */
    private void addWallsForPosition(Position currPosition, Stack<Position> walls, int[][] map, boolean[][] visited) {
        Position tempPos;
        Random rand = new Random();
        int randomIndex;
        ArrayList<Position> availibleWays = new ArrayList<>(); //List that will save the entire walls that will be added to the walls list
        //"Check" = if the position is in the map and if it's a wall
        //Check down
        if (currPosition.getRowIndex() < map.length - 1 && map[currPosition.getRowIndex() + 1][currPosition.getColumnIndex()] == 1) {
            tempPos = new Position(currPosition.getRowIndex() + 1, currPosition.getColumnIndex());
            availibleWays.add(tempPos);
        }
        //Check right
        if (currPosition.getColumnIndex() < map[0].length - 1 && map[currPosition.getRowIndex()][currPosition.getColumnIndex() + 1] == 1) {
            tempPos = new Position(currPosition.getRowIndex(), currPosition.getColumnIndex() + 1);
            availibleWays.add(tempPos);
        }
        //check up
        if (currPosition.getRowIndex() > 0 && map[currPosition.getRowIndex() - 1][currPosition.getColumnIndex()] == 1) {
            tempPos = new Position(currPosition.getRowIndex() - 1, currPosition.getColumnIndex());
            availibleWays.add(tempPos);
        }
        //check left
        if (currPosition.getColumnIndex() > 0 && map[currPosition.getRowIndex()][currPosition.getColumnIndex() - 1] == 1) {
            tempPos = new Position(currPosition.getRowIndex(), currPosition.getColumnIndex() - 1);
            availibleWays.add(tempPos);
        }

        //Add all the available ways and mark them as visited. Pushing the walls randomly so it will make a more random maze
        while (!availibleWays.isEmpty()) {
            randomIndex = rand.nextInt(availibleWays.size());
            tempPos = availibleWays.get(randomIndex);
            availibleWays.remove(randomIndex);
            if (!visited[tempPos.getRowIndex()][tempPos.getColumnIndex()]) {
                walls.push(tempPos);
                visited[tempPos.getRowIndex()][tempPos.getColumnIndex()] = true;
            }
        }
    }

    /** Fills the entire map with 1's (walls). Used for initializing for Prims Algorithm
     * @param map - Map of the maze
     */
    private void fillWithWalls(int[][] map) {
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                map[i][j] = 1;
    }
}
