package algorithms.mazeGenerators;

/**
 * A class the describes the maze
 * Data members - startPosition(the start position of the maze - position), goalPosition(the end position of the maze - position)
 *                map(the maze - int[][])
 */
public class Maze {
    private Position startPosition, goalPosition;
    private int[][] map;

    /** Constructor
     * @param startPosition - Start position of the maze
     * @param endPosition - End position of the maze
     * @param map - The maze int[][] representation
     */
    public Maze(Position startPosition, Position endPosition, int[][] map) {
        this.startPosition = new Position(startPosition);
        this.goalPosition = new Position(endPosition);
        this.map = copyMap(map);
    }

    /** Copy constructor
     * @param maze - maze to be copied
     */
    public Maze(Maze maze){
        this.startPosition = maze.getStartPosition();
        this.goalPosition = maze.getGoalPosition();
        this.map = copyMap(maze.map);
    }

    /** copy int[][] type
     * @param map - int[][] object to be copied
     * @return copied map
     */
    private int[][] copyMap(int[][] map) {
        int[][] newMap = new int[map.length][map[0].length];
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map[0].length; j++)
                newMap[i][j] = map[i][j];
        return newMap;
    }

    /**Get the start position of maze
     * @return start position of maze
     */
    public Position getStartPosition() {
        return new Position(startPosition);
    }

    /**Get the end position of maze
     * @return end position of maze
     */
    public Position getGoalPosition() {
        return new Position(goalPosition);
    }

    /** Get size of the mazes row
     * @return size of the mazes row
     */
    public int getRowSize(){
        return map.length;
    }

    public int getColumnSize(){
        return map[0].length;
    }

    public int getAtPosition(Position pos) throws Exception {
        if(pos.getRowIndex() > map.length - 1 || pos.getColumnIndex() > map[0].length - 1 || pos.getRowIndex() < 0 || pos.getColumnIndex() < 0)
            throw new Exception("Position not on map");
        return map[pos.getRowIndex()][pos.getColumnIndex()];
    }

    public int getAtIndex(int row, int column){
        if(row > map.length - 1 || column > map[0].length - 1 || row < 0 || column < 0) {
            try {
                throw new Exception("Position not on map");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return -1;
            }
        }
        return map[row][column];
    }

    public void print(){
        for(int i = 0; i < map.length; i++){
            System.out.print("{ ");
            for(int j = 0; j < map[0].length; j++)
            {
                if(i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print("S ");
                else{
                    if(i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex())
                        System.out.print("E ");
                    else
                        System.out.print(map[i][j] + " ");
                }/*
                if(i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print("S ");
                else{
                    if(i == endPosition.getRowIndex() && j == endPosition.getColumnIndex())
                        System.out.print("E ");
                    else
                        System.out.print(map[i][j] + " ");
                }*/
            }
            System.out.println("}");
        }
    }

}
