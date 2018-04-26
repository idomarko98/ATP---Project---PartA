package algorithms.search;

import java.util.*;

/**
 * Depth First Search algorithm
 * Data member: stateStack stack - saves the possible moves
 */
public class DepthFirstSearch extends AGraphSearchingAlgorithm {

    private Stack<AState> statesStack;

    /**
     * Constructor
     */
    public DepthFirstSearch(){
        super();
        statesStack = new Stack<>();
    }

    /** Solve a searching problem
     * @param domain - Problem that can be searched in
     * @return Solution to the problem
     */
    @Override
    public Solution solve(ISearchable domain) {
        try {
            if(domain == null)
                throw new Exception("Cannot solve null problem");
            AState tempState;
            List<AState> surroundingStates;
            tempState = domain.getStartState(); //Start to solve from starting state
            meta.put(tempState, null); //Set the father of start state as null
            statesStack.push(tempState);
            //Start DepthFirstSearch algorithm
            while (!statesStack.isEmpty()) {
                tempState = statesStack.pop();
                numOfEvaluatedStates++; //Increase the number of modeling
                if (domain.isGoalState(tempState))
                    return constructPath(tempState); //Start building solution
                if (!closedSet.contains(tempState)) {
                    closedSet.add(tempState);
                    surroundingStates = domain.getAllPossibleStates(tempState);
                    for (AState surroundingState : surroundingStates) {
                        if (!closedSet.contains(surroundingState)) {
                            statesStack.push(surroundingState);
                            meta.put(surroundingState, tempState);
                        }
                    }
                }
            }
            couldNotFormPathError(); //Send error if could not find solution
            return null;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * @return Name of algorithm - DepthFirstSearch
     */
    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

}
