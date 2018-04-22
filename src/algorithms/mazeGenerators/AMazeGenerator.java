package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int row, int column) {
        long initTime,endTime, totalTime;
        initTime = System.currentTimeMillis();
        generate(row,column);
        endTime = System.currentTimeMillis();

        totalTime = endTime - initTime;
        return totalTime;
    }
}
