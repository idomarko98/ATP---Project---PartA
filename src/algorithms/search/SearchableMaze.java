package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for maze and a search algorithm
 * Data member: maze - maze to be solved
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;

    /** Constructor
     * @param maze - maze
     */
    public SearchableMaze(Maze maze) {
        try{
            if(maze == null)
                throw new Exception("Maze is null");
            this.maze = new Maze(maze);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /** Get the start state of the maze
     * @return Start state of maze
     */
    @Override
    public AState getStartState() {
        return new MazeState(maze.getStartPosition());
    }

    /** Check if a given state if the goal state
     * @param state - state to be compared to goal state
     * @return true if the given state is the goal state
     */
    @Override
    public boolean isGoalState(AState state) {
        return state.equals(new MazeState(maze.getGoalPosition()));
    }

    /** Get all possible states we can get from given a state
     * @param state = state for which we want to get all the states of
     * @return All possible state we can to from given state
     */
    @Override
    public List<AState> getAllPossibleStates(AState state) {
        if(!(state instanceof MazeState))
            return null;
        MazeState mazeState = (MazeState)state;
        Position tempPos;
        Position position = mazeState.getPositionOfMazeState();
        List<AState> possiblePositions = new ArrayList<>();
        //Check down
        if (position.getRowIndex() < maze.getRowSize() - 1 && maze.getAtIndex(position.getRowIndex() + 1, position.getColumnIndex()) == 0) {
            tempPos = new Position(position.getRowIndex() + 1, position.getColumnIndex());
            possiblePositions.add(new MazeState(tempPos));
        }
        //Check right
        if (position.getColumnIndex() < maze.getColumnSize() - 1 && maze.getAtIndex(position.getRowIndex(),position.getColumnIndex() + 1) == 0) {
            tempPos = new Position(position.getRowIndex(), position.getColumnIndex() + 1);
            possiblePositions.add(new MazeState(tempPos));
        }
        //Check up
        if (position.getRowIndex() > 0 && maze.getAtIndex(position.getRowIndex() - 1, position.getColumnIndex()) == 0) {
            tempPos = new Position(position.getRowIndex() - 1, position.getColumnIndex());
            possiblePositions.add(new MazeState(tempPos));
        }
        //Check left
        if (position.getColumnIndex() > 0 && maze.getAtIndex(position.getRowIndex(), position.getColumnIndex() - 1) == 0) {
            tempPos = new Position(position.getRowIndex(), position.getColumnIndex() - 1);
            possiblePositions.add(new MazeState(tempPos));
        }
        return possiblePositions;
    }
}
