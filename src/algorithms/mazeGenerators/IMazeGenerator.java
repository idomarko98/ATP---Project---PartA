package algorithms.mazeGenerators;

/**
 * Interface for maze generator
 */
public interface IMazeGenerator {

    /**
     * @param row - size of mazes row
     * @param column - size of mazes column
     * @return - return created maze
     */
    Maze generate(int row, int column);

    /**
     * @param row - size of mazes row
     * @param column - size of mazes column
     * @return - return the total time of maze creation
     */
    long measureAlgorithmTimeMillis(int row, int column);
}
