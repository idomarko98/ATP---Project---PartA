package algorithms.mazeGenerators;

import algorithms.search.ICloneable;

/**
 * Abstract class for maze generator - implements IMazeGenerator
 */
public abstract class AMazeGenerator implements IMazeGenerator, ICloneable {

    /**
     * @param row -  size of mazes row
     * @param column - size of mazes column
     * @return - return time of maze creation
     */
    @Override
    public long measureAlgorithmTimeMillis(int row, int column) {
        long initTime,endTime, totalTime;
        initTime = System.currentTimeMillis(); //start time of generating
        generate(row,column);
        endTime = System.currentTimeMillis(); //end time of generating

        totalTime = endTime - initTime;
        return totalTime;
    }
}
