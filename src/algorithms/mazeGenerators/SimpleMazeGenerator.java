package algorithms.mazeGenerators;

import javafx.geometry.Pos;

import java.util.Random;

/**
 * Maze Generator using two permanent start and goal positions
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    /** Function to generate maze using the simple method (Two permanent start and goal positions)
     * @param row    - size of mazes row
     * @param column - size of mazes column
     * @return Maze creation
     */
    @Override
    public Maze generate(int row, int column){
        if (row < 2 || column < 2) { //Checking if input is valid
            try {
                throw new Exception("The Maze must be at least 2X2");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
        int[][] map; //Map that describes the maze
        Position startPos, endPos, tempPos;
        map = new int[row][column];
        startPos = new Position(0,0); //Permanent start position at (0,0)
        endPos = new Position(row-1,column-1); //Permanent goal position at bottom right corner
        tempPos = new Position(0 , 0); //Starting the run from the start position
        initValues(map); //Fill map with -1's

        //While tempPos hasn't reached the goal position
        while(!(tempPos.getRowIndex() == endPos.getRowIndex()) || !(tempPos.getColumnIndex() == endPos.getColumnIndex())){
            map[tempPos.getRowIndex()][tempPos.getColumnIndex()] = 0; //Mark current position as 0
            if(tempPos.getRowIndex() == endPos.getRowIndex()) //if the algorithm reached the bottom, go right until goal position reached
            {
               goRight(map, endPos.getRowIndex(), tempPos.getColumnIndex());
               break;
            }
            else if(tempPos.getColumnIndex() == endPos.getColumnIndex()) //if the algorithm reached the most right column, go down until goal position reached
            {
                goDown(map, endPos.getColumnIndex(), tempPos.getRowIndex());
                break;
            }
            Random rand = new Random();
            int randInt = rand.nextInt(3);
            switch (randInt) {
                case 0: //Move right
                    tempPos = new Position(tempPos.getRowIndex() + 1, tempPos.getColumnIndex());
                    break;
                case 1: // move down
                    tempPos = new Position(tempPos.getRowIndex(), tempPos.getColumnIndex() + 1);
                    break;
            }
        }
        map[row-1][column-1] = 0; //Mark the goal position as part of the path
        finishMap(map);
        return new Maze(startPos, endPos, map);
    }

    /** Function the goes over the map and fills "-1 cells" with 0 or 1 randomly
     * @param map - Map of the Maze
     */
    private void finishMap(int[][] map) {
        Random rand = new Random();
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map[0].length; j++)
                if(map[i][j] == -1)
                    map[i][j] = rand.nextInt(2);
    }

    /** Fill given row with 0's
     * @param map - Map of the maze
     * @param row - Row to be filled with 0's
     * @param currentColumn - Column to start filling 0's from
     */
    private void goRight(int[][] map, int row, int currentColumn) {
        for(int i = currentColumn; i < map[0].length; i++)
            map[row][i] = 0;
    }

    /**
     * @param map - Map of the maze
     * @param column - Column to be filled with 0's
     * @param currentRow - Row to start filling 0's from
     */
    private void goDown(int[][] map, int column, int currentRow) {
        for(int i = currentRow; i < map.length; i++)
            map[i][column] = 0;
    }

    /** Initialize the map with -1's
     * @param map - Map of the maze
     */
    private void initValues(int[][] map) {
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map[0].length; j++)
                map[i][j] = -1;
    }
    /*private int changeByBound(int val, int bound) {
        Random rand = new Random();
        if (val < bound - 1)
            return val + rand.nextInt(bound - val);
        return val - rand.nextInt(val);
    }

    private Position[] getStartAndEndPositions(int row, int column) {
        Random rand = new Random();
        int startX = rand.nextInt(column);
        int startY = rand.nextInt(row);
        int endX = rand.nextInt(column);
        int endY = rand.nextInt(row);
        while (startX == endX && startY == endY) {
            endX = changeByBound(endX, column);
            endY = changeByBound(endY, row);
        }
        Position[] positions = new Position[2];
        positions[0] = new Position(startY, startX);
        positions[1] = new Position(endY, endX);
        return positions;
    }

    @Override
    public Maze generate(int row, int column) {
        if (row < 2 || column < 2) {
            try {
                throw new Exception("The Maze must be at list 2X2");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
        Position[] positions = getStartAndEndPositions(row, column);
        int[][] maze = new int[row][column];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                maze[i][j] = -1;
        setRandomPath(maze, positions[0], positions[1]);
        fillEmptySpots(maze);
        return new Maze(positions[0], positions[1], maze);
    }

    private void fillEmptySpots(int[][] maze) {
        Random rand = new Random();
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[0].length; j++)
                if (maze[i][j] == -1)
                    maze[i][j] = rand.nextInt(2);
    }

    private int getNextPos(int curr, int max) {
        Random rand = new Random();
        if (curr == 0)
            return curr + 1;
        if (curr == max)
            return curr - 1;
        // not bounded
        if (rand.nextBoolean()) //go up
            return curr + 1;
        return curr - 1;
    }

    private void setRandomPath(int[][] maze, Position start, Position end) {
        Random rand = new Random();
        int currX = start.getColumnIndex();
        int currY = start.getRowIndex();
        while (currX != end.getColumnIndex() && currY != end.getRowIndex()) {
            maze[currY][currX] = 0;
            if (rand.nextBoolean()) //change x position
                currX = getNextPos(currX, maze[0].length);
            else
                currY = getNextPos(currY, maze.length);
        }
        maze[currY][currX] = 0;
    }*/
}
