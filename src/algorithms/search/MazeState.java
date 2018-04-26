package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * Class to represent a maze state - Position in maze
 * Data member: Position that represent the state
 */
public class MazeState extends AState{

    private Position position;

    /** Constructor
     * @param position - Position of state
     */
    public MazeState(Position position) {
        this.position = new Position(position);
    }

    /** Get position of state
     * @return Position of state
     */
    public Position getPositionOfMazeState() {
        return new Position(position);
    }

    /** Check if two states are equal (if the positions are equal then states are equal)
     * @param obj - Object to be compared to
     * @return true if equals, else false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MazeState){
            MazeState mazeState = (MazeState)obj;
            return position.equals(mazeState.position);
        }
        return  false;
    }

    /** Hash code for state
     * @return int hash code
     */
    @Override
    public int hashCode() {
        return position.hashCode();
    }

    /** Get string of state
     * @return string of state
     */
    @Override
    public String toString() {
        return position.toString();
    }
}
