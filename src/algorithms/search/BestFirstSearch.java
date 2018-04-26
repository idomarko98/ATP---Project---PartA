package algorithms.search;

import java.util.PriorityQueue;

/**
 * Best First Search algorithm
 * To set different costs, the user must set it for the state itself
 */
public class BestFirstSearch extends BreadthFirstSearch {

    public BestFirstSearch(){
        super(); //uses the BreadthFirstSearch algorithm
        openSet = new PriorityQueue<>();
    }

    /**
     * @return name of algorithm - BestFirstSearch
     */
    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
