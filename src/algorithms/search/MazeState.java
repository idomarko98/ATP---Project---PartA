package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState{

    private Position position;

    public MazeState(Position position) {
        this.position = new Position(position);
    }

    public Position getPositionOfMazeState() {
        return new Position(position);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MazeState){
            MazeState mazeState = (MazeState)obj;
            return position.equals(mazeState.position);
        }
        return  false;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
