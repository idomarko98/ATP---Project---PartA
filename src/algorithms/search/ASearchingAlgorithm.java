package algorithms.search;

/**
 * Abstract class for searching algorithms
 * Data member: numOfEvaluatedStates - number of states which were evaluated as potential path
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm, ICloneable{
    protected int numOfEvaluatedStates;

    /**
     * Constructor
     */
    public ASearchingAlgorithm() {
        this.numOfEvaluatedStates = 0;
    }

    /** Get the number of evaluated states as path
     * @return The number of evaluated states as path
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return numOfEvaluatedStates;
    }

    /**
     * Error for when a solution couldn't be found
     */
    protected void couldNotFormPathError(){
        try {
            throw new Exception("Couldn't find a path to the Goal State");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
