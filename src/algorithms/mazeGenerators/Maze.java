package algorithms.mazeGenerators;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * A class the describes the maze
 * Data members - startPosition(the start position of the maze - position), goalPosition(the end position of the maze - position)
 *                map(the maze - int[][])
 */
public class Maze implements Serializable{
    private Position startPosition, goalPosition;
    private int[][] map;

    /** Constructor
     * @param startPosition - Start position of the maze
     * @param endPosition - End position of the maze
     * @param map - The maze int[][] representation
     */
    public Maze(Position startPosition, Position endPosition, int[][] map) {
        try{
            if(startPosition == null || endPosition == null || map == null || getAtPosition(startPosition, map) == -1 || getAtPosition(startPosition, map) == -1)
                throw new Exception("Cannot form maze");
            this.startPosition = new Position(startPosition);
            this.goalPosition = new Position(endPosition);
            this.map = copyMap(map);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

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

    /** Get size of the mazes column
     * @return size of the mazes column
     */
    public int getColumnSize(){
        return map[0].length;
    }

    /**returns value of map for a specific position
     * @param map - map to check if the position is on it
     * @param pos - position which value in map we want to get
     * @return value of map for a specific position
     */
    public int getAtPosition(Position pos, int[][] map) {
        if(map == null){
            try {
                throw new Exception("Map is null");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return -1;
            }
        }
        if(pos.getRowIndex() > map.length - 1 || pos.getColumnIndex() > map[0].length - 1 || pos.getRowIndex() < 0 || pos.getColumnIndex() < 0)
        {
            try {
                throw new Exception("Position not on map");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return -1;
            }
        }
        return map[pos.getRowIndex()][pos.getColumnIndex()];
    }

    /** returns value of map for a specific index
     * @param row - index of row
     * @param column - index of column
     * @return value of map for a specific index
     */
    public int getAtIndex(int row, int column){
        if(row > map.length - 1 || column > map[0].length - 1 || row < 0 || column < 0) { //Check if the index is in map
            try {
                throw new Exception("Position not on map");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return -1;
            }
        }
        return map[row][column];
    }

    /**
     * Print the maze
     */
    public void print(){
        for(int i = 0; i < map.length; i++){
            System.out.print("{ ");
            for(int j = 0; j < map[0].length; j++)
            {
                if(i == startPosition.getRowIndex() && j == startPosition.getColumnIndex()) // if the position is the start - mark with S
                    System.out.print("S ");
                else{
                    if(i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex()) // if the position is the start - mark with E
                        System.out.print("E ");
                    else
                        System.out.print(map[i][j] + " ");
                }
            }
            System.out.println("}");
        }
    }

    public byte[] toByteArray(){
        int dem = map.length*map[0].length;
        //int size = Integer.BYTES*6 + dem/8;
        //if(dem % 8 != 0)
        //    size++;
        byte[] bytes;
        ByteBuffer b = ByteBuffer.allocate(Integer.BYTES*(6 + dem));
        b.putInt(map.length);
        b.putInt(map[0].length);
        b.putInt(startPosition.getColumnIndex());
        b.putInt(startPosition.getRowIndex());
        b.putInt(goalPosition.getColumnIndex());
        b.putInt(goalPosition.getRowIndex());
        for(int i = 0; i < map.length; i++)
            for (int j = 0; j < map[0].length; j++)
                b.putInt(map[i][j]);
        /*
        int col = 0, row = 0, index = 0;
        byte tempByte = 0x00;
        byte[] tempBytes = {(byte)0x80,(byte)0x40, (byte)0x20, (byte)0x10, (byte)0x08, (byte)0x04, (byte)0x02, (byte)0x01};

        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++){
                if(map[row][col] == 1){
                    tempByte = (byte)(tempByte | tempBytes[index]);
                }
                if(index == 7){
                    b.put(tempByte);
                    tempByte = 0x00;
                }
                index = (index + 1) % 8;
            }
        }
        if(index != 0)
            b.put(tempByte);
        */
        bytes = b.array();
        return bytes;
    }

    public Maze(byte[] bytes){
        // move to top after javadoc
        try{
            if(bytes == null || bytes.length < 24)
                throw new Exception("Cannot form maze");
            int length = byteArrayToInt(bytes,0);
            int lengthZero = byteArrayToInt(bytes,4);
            int startCol = byteArrayToInt(bytes,8);
            int startRow = byteArrayToInt(bytes,12);
            int endCol = byteArrayToInt(bytes,16);
            int endRow = byteArrayToInt(bytes,20);

            if(length <= 0 || lengthZero <= 0 || startCol < 0 || startRow < 0 || endCol < 0 || endRow < 0)
                throw new Exception("Cannot form maze");

            this.map = new int[length][lengthZero];
            this.startPosition = new Position(startRow, startCol);
            this.goalPosition = new Position(endRow, endCol);

            /*if(bytes.length - 24 != length*lengthZero*Integer.BYTES || getAtPosition(startPosition,map) == -1 || getAtPosition(goalPosition,map) == -1) {
                throw new Exception("Cannot form maze");
            }*/
            int index = 24;
            for(int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    map[i][j] = byteArrayToInt(bytes,index);
                    index += 4;
                }
            }
            /*
            int index = 24;
            byte[] tempBytes = {(byte)0x80,(byte)0x40, (byte)0x20, (byte)0x10, (byte)0x08, (byte)0x04, (byte)0x02, (byte)0x01};
            int tempBytesIndex = 0;
            for(int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if((byte)(bytes[index] & tempBytes[tempBytesIndex]) != 0x00)
                        map[i][j] = 1;
                    index++;
                    tempBytesIndex = (tempBytesIndex + 1) % 8;
                }
            }
            */
        }
        catch (Exception e){
            System.out.println("Cannot form maze");
        }

    }

    private int byteArrayToInt(byte[] bytes, int start){
        return ((0xFF & bytes[start]) << 24) | ((0xFF & bytes[start+1]) << 16) |
                ((0xFF & bytes[start+2]) << 8) | (0xFF & bytes[start+3]);
    }
}
