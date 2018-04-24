package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {
    private int changeByBound(int val, int bound) {
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
                    //maze[i][j] = 1; // --  for testing
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
        while (currX != end.getColumnIndex() || currY != end.getRowIndex()) {
            maze[currY][currX] = 0;
            if (rand.nextBoolean()) //change x position
                currX = getNextPos(currX, maze[0].length - 1);
            else
                currY = getNextPos(currY, maze.length - 1);
        }
        maze[currY][currX] = 0;
    }
}

