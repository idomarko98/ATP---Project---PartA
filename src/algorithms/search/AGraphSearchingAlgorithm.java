package algorithms.search;

import java.util.*;

/**
 * Abstract class for algorithm which uses stack or queue as part of its searching (such as BFS and DFS)
 * Data members: meta - map of moves from one state to another, closedSet - set of visited set as part of the algorithm
 */
public abstract class AGraphSearchingAlgorithm extends ASearchingAlgorithm {

    protected Map<AState, AState> meta; // State, father state
    protected Set<AState> closedSet;

    /**
     * Constructor
     */
    public AGraphSearchingAlgorithm(){
        meta = new HashMap<>();
        closedSet = new HashSet<>();
    }

    /** Construct the path for the solution
     * @param finalState - state to which we build the path solution
     * @return the solution for the searching problem
     */
    protected Solution constructPath(AState finalState){
        AState tempState = finalState;
        Stack<AState> path = new Stack<>();
        while (tempState != null){
            path.push(tempState);
            tempState = meta.get(tempState);
        }
        return new Solution(path);
    }
}
