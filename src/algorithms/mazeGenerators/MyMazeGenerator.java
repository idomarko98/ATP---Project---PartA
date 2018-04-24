package algorithms.mazeGenerators;


import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int row, int column) {
        if (row < 3 || column < 3) {
            try {
                throw new Exception("The Maze must be at least 3X3");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
        int[][] map;
        Stack<Position> walls;
        //ArrayList<Position> visitedPosition = new ArrayList<>();
        boolean[][] visitedPositions = new boolean[row][column]; //update
        Position startPosition, endPosition, tempPosition;
        walls = new Stack<>();
        Random rand = new Random();
        startPosition = new Position(rand.nextInt(row - 2) + 1, 0); //start at left side
        //tempPosition = new Position(rand.nextInt(row),rand.nextInt(column));
        //visitedPosition.add(tempPosition);
        map = new int[row][column];
        fillWithWalls(map);
        map[startPosition.getRowIndex()][startPosition.getColumnIndex()] = 0;
        //visitFrame(visitedPosition, map);
        visitFrame(visitedPositions, map);
        //addWallsForPosition(startPosition, walls, map, visitedPosition);
        addWallsForPosition(startPosition, walls, map, visitedPositions);
        while (!walls.isEmpty()) {
            tempPosition = walls.pop();
            if (changeable(tempPosition, map)) {
                map[tempPosition.getRowIndex()][tempPosition.getColumnIndex()] = 0;
                //addWallsForPosition(tempPosition, walls, map, visitedPosition);
                addWallsForPosition(tempPosition, walls, map, visitedPositions);
            }
        }
        endPosition = pickEnd(map);
        return new Maze(startPosition, endPosition, map);
    }

    private Position pickEnd(int[][] map) {
        Random rand = new Random();
        int randInt = 0;
        int timeOut = 0;
        int col = map[0].length - 1;
        boolean picked = false;
        while (!picked && timeOut < map.length){
            timeOut++;
            randInt = rand.nextInt(map.length-2) + 1;
            if(map[randInt][col-1] == 0){
                map[randInt][col] = 0;
                picked = true;
            }
        }
        if(!picked)
            return pickEndIterative(map);
        return  new Position(randInt,col);

    }

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

    private boolean changeable(Position tempPosition, int[][] map) {
        int amountOfZeros = 0;
        if (map[tempPosition.getRowIndex() - 1][tempPosition.getColumnIndex()] == 0)
            amountOfZeros++;
        if (map[tempPosition.getRowIndex() + 1][tempPosition.getColumnIndex()] == 0)
            amountOfZeros++;
        if (map[tempPosition.getRowIndex()][tempPosition.getColumnIndex() - 1] == 0)
            amountOfZeros++;
        if (map[tempPosition.getRowIndex()][tempPosition.getColumnIndex() + 1] == 0)
            amountOfZeros++;
        if (amountOfZeros >= 2)
            return false;
        return true;
    }
    /*
    private void visitFrame(ArrayList<Position> visitedPosition, int[][] map) {
        for (int i = 0; i < map.length; i++) {
            visitedPosition.add(new Position(i, 0));
            visitedPosition.add(new Position(i, map[0].length - 1));
        }
        for (int i = 1; i < map[0].length - 1; i++) {
            visitedPosition.add(new Position(0, i));
            visitedPosition.add(new Position(map.length - 1, i));
        }
    }
    */
    private void visitFrame(boolean[][] visitedPosition, int[][] map) {
        for (int i = 0; i < map.length; i++) {
            visitedPosition[i][0] = true;
            visitedPosition[i][map[0].length - 1] = true;
        }
        for (int i = 1; i < map[0].length - 1; i++) {
            visitedPosition[0][i] = true;
            visitedPosition[map.length - 1][i] = true;
        }
    }

    /*
    private void addWallsForPosition(Position currPosition, Stack<Position> walls, int[][] map, ArrayList<Position> visited) {
        Position tempPos;
        Random rand = new Random();
        int randomIndex;
        ArrayList<Position> availibleWays = new ArrayList<>();
        if (currPosition.getRowIndex() < map.length - 1 && map[currPosition.getRowIndex() + 1][currPosition.getColumnIndex()] == 1) { //check down
            tempPos = new Position(currPosition.getRowIndex() + 1, currPosition.getColumnIndex());
            availibleWays.add(tempPos);
        }
        if (currPosition.getColumnIndex() < map[0].length - 1 && map[currPosition.getRowIndex()][currPosition.getColumnIndex() + 1] == 1) { //check right
            tempPos = new Position(currPosition.getRowIndex(), currPosition.getColumnIndex() + 1);
            availibleWays.add(tempPos);
        }
        if (currPosition.getRowIndex() > 0 && map[currPosition.getRowIndex() - 1][currPosition.getColumnIndex()] == 1) { //check up
            tempPos = new Position(currPosition.getRowIndex() - 1, currPosition.getColumnIndex());
            availibleWays.add(tempPos);
        }
        if (currPosition.getColumnIndex() > 0 && map[currPosition.getRowIndex()][currPosition.getColumnIndex() - 1] == 1) { //check left
            tempPos = new Position(currPosition.getRowIndex(), currPosition.getColumnIndex() - 1);
            availibleWays.add(tempPos);
        }
        while (!availibleWays.isEmpty()) {
            randomIndex = rand.nextInt(availibleWays.size());
            tempPos = availibleWays.get(randomIndex);
            availibleWays.remove(randomIndex);
            if (!visited.contains(tempPos)) {
                walls.push(tempPos);
                visited.add(tempPos);
            }
        }
    }
    */

    private void addWallsForPosition(Position currPosition, Stack<Position> walls, int[][] map, boolean[][] visited) {
        Position tempPos;
        Random rand = new Random();
        int randomIndex;
        ArrayList<Position> availibleWays = new ArrayList<>();
        if (currPosition.getRowIndex() < map.length - 1 && map[currPosition.getRowIndex() + 1][currPosition.getColumnIndex()] == 1) { //check down
            tempPos = new Position(currPosition.getRowIndex() + 1, currPosition.getColumnIndex());
            availibleWays.add(tempPos);
        }
        if (currPosition.getColumnIndex() < map[0].length - 1 && map[currPosition.getRowIndex()][currPosition.getColumnIndex() + 1] == 1) { //check right
            tempPos = new Position(currPosition.getRowIndex(), currPosition.getColumnIndex() + 1);
            availibleWays.add(tempPos);
        }
        if (currPosition.getRowIndex() > 0 && map[currPosition.getRowIndex() - 1][currPosition.getColumnIndex()] == 1) { //check up
            tempPos = new Position(currPosition.getRowIndex() - 1, currPosition.getColumnIndex());
            availibleWays.add(tempPos);
        }
        if (currPosition.getColumnIndex() > 0 && map[currPosition.getRowIndex()][currPosition.getColumnIndex() - 1] == 1) { //check left
            tempPos = new Position(currPosition.getRowIndex(), currPosition.getColumnIndex() - 1);
            availibleWays.add(tempPos);
        }
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

    private void fillWithWalls(int[][] map) {
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                map[i][j] = 1;
    }
}
