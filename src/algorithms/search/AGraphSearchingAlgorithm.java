package algorithms.search;

import java.util.*;

/**
 * Abstract class for algorithm which uses stack or queue as part of its searching (such as BFS and DFS)
 * Data members: meta - map of moves from one state to another, closedSet - set of visited set as part of the algorithm
 *               openSet - saves the possible moves
 */
public abstract class AGraphSearchingAlgorithm extends ASearchingAlgorithm {

    protected Collection<AState> openSet;
    protected Map<AState, AState> meta; // State, father state
    protected Set<AState> closedSet;

    /**
     * Constructor
     */
    public AGraphSearchingAlgorithm(){
        meta = new HashMap<>();
        closedSet = new HashSet<>();
    }

    /** Solve a searching problem
     * @param domain - Problem that can be searched in
     * @return Solution to the problem
     */
    @Override
    public Solution solve(ISearchable domain) {
        try {
            if (domain == null)
                throw new Exception("Cannot solve null problem");
            AState root = domain.getStartState(); //Start to solve from starting state
            meta.put(root, null); //Set the father of start state as null
            addToSet(root);
            AState temp;
            //Start searching(DFS or BFS or any type that works with queue or stack) algorithm
            while (!openSet.isEmpty()) {
                temp = removeFromHead();
                numOfEvaluatedStates++; //Increase the number of modeling

                if (domain.isGoalState(temp))
                    return constructPath(temp); //Start building solution

                List<AState> possibleStates = domain.getAllPossibleStates(temp);
                for (int i = 0; i < possibleStates.size(); i++) {
                    AState child = possibleStates.get(i);
                    if (closedSet.contains(child))
                        continue;
                    if (!openSet.contains(child)) {
                        meta.put(child, temp);
                        addToSet(child);
                    }
                }
                closedSet.add(temp);
            }

            couldNotFormPathError(); //Send error if could not find solution
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected abstract AState removeFromHead();
    protected abstract void addToSet(AState state);

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
