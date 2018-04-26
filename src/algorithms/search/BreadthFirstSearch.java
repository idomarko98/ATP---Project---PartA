package algorithms.search;

import java.util.*;

/**
 * Breadth First Search algorithm
 * Data member: openSet queue - saves the possible moves
 */
public class BreadthFirstSearch extends AGraphSearchingAlgorithm {

    protected Queue<AState> openSet;

    /**
     * Constructor
     */
    public BreadthFirstSearch(){
        super();
        openSet = new LinkedList<>();
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
            addToSet(root, null);
            AState temp;
            //Start BreathFirstSearch algorithm
            while (!openSet.isEmpty()) {
                temp = openSet.poll();
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
                        //openSet.add(child);
                        addToSet(child, temp);
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

    /** Add to set
     * @param state - Current state
     * @param fatherState - State which lead to current state
     */
    protected void addToSet(AState state, AState fatherState){
        try{
            if(state == null)
                throw new Exception("Cannot add null state");
            openSet.add(state);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return Name of algorithm - BreadthFirstSearch
     */
    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

}
