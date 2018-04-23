package algorithms.mazeGenerators;

public class Maze {
    private Position startPosition, endPosition;
    private int[][] map;

    public Maze(Position startPosition, Position endPosition, int[][] map) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.map = copyMap(map);
    }

    private int[][] copyMap(int[][] map) {
        int[][] newMap = new int[map.length][map[0].length];
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map[0].length; j++)
                newMap[i][j] = map[i][j];
        return newMap;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void print(){
        for(int i = 0; i < map.length; i++){
            System.out.print("{ ");
            for(int j = 0; j < map[0].length; j++)
            {
                if(i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print("S ");
                else{
                    if(i == endPosition.getRowIndex() && j == endPosition.getColumnIndex())
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
