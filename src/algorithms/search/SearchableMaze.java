package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

public class SearchableMaze implements ISearchable {
    private Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = new Maze(maze);
    }

    @Override
    public AState getStartState() {
        return new MazeState(maze.getStartPosition());
    }

    @Override
    public boolean isGoalState(AState state) {
        return state.equals(new MazeState(maze.getGoalPosition()));
    }

    @Override
    public List<AState> getAllPossibleStates(AState state) {
        if(!(state instanceof MazeState))
            return null;
        MazeState mazeState = (MazeState)state;
        Position tempPos;
        Position position = mazeState.getPositionOfMazeState();
        List<AState> possiblePositions = new ArrayList<>();
        if (position.getRowIndex() < maze.getRowSize() - 1 && maze.getAtIndex(position.getRowIndex() + 1, position.getColumnIndex()) == 0) { //check down
            tempPos = new Position(position.getRowIndex() + 1, position.getColumnIndex());
            possiblePositions.add(new MazeState(tempPos));
        }
        if (position.getColumnIndex() < maze.getColumnSize() - 1 && maze.getAtIndex(position.getRowIndex(),position.getColumnIndex() + 1) == 0) { //check right
            tempPos = new Position(position.getRowIndex(), position.getColumnIndex() + 1);
            possiblePositions.add(new MazeState(tempPos));
        }
        if (position.getRowIndex() > 0 && maze.getAtIndex(position.getRowIndex() - 1, position.getColumnIndex()) == 0) { //check up
            tempPos = new Position(position.getRowIndex() - 1, position.getColumnIndex());
            possiblePositions.add(new MazeState(tempPos));
        }
        if (position.getColumnIndex() > 0 && maze.getAtIndex(position.getRowIndex(), position.getColumnIndex() - 1) == 0) { //check left
            tempPos = new Position(position.getRowIndex(), position.getColumnIndex() - 1);
            possiblePositions.add(new MazeState(tempPos));
        }
        return possiblePositions;
    }
}
