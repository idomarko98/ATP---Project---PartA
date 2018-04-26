package algorithms.search;

import java.util.*;

/**
 * Breadth First Search algorithm
 */
public class BreadthFirstSearch extends AGraphSearchingAlgorithm {

    /**
     * Constructor
     */
    public BreadthFirstSearch(){
        super();
        openSet = new LinkedList<>();
    }

    /** Remove state from the queue
     * @return state
     */
    @Override
    protected AState removeFromHead() {
        return ((Queue<AState>)openSet).poll();
    }

    /** Add to set
     * @param state - Current state
     */
    protected void addToSet(AState state){
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
