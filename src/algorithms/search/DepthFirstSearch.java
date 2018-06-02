package algorithms.search;

import java.util.*;

/**
 * Depth First Search algorithm
 */
public class DepthFirstSearch extends AGraphSearchingAlgorithm {

    /**
     * Constructor
     */
    public DepthFirstSearch(){
        super();
        openSet = new Stack<>();
    }

    /** Remove item from openSet Stack
     * @return state
     */
    @Override
    protected AState removeFromHead() {
        return ((Stack<AState>)openSet).pop();
    }

    /** Add state to the stack
     * @param state
     */
    @Override
    protected void addToSet(AState state) {
        try{
            if(state == null)
                throw new Exception("Cannot add null state");
            ((Stack<AState>)openSet).push(state);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return Name of algorithm - DepthFirstSearch
     */
    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

    /** Clones the DepthFirstSearch instance
     * @return new DepthFirstSearch
     */
    @Override
    public Object cloneMe() {
        return new DepthFirstSearch();
    }
}
