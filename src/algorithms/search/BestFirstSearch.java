package algorithms.search;

import java.util.PriorityQueue;

/**
 * Best First Search algorithm
 */
public class BestFirstSearch extends BreadthFirstSearch {

    public BestFirstSearch(){
        super(); //uses the BreadthFirstSearch algorithm
        openSet = new PriorityQueue<>();
    }

    @Override
    protected void addToSet(AState state, AState fatherState) {
        try{
            if(state == null)
                throw new Exception("Cannot add null state");
            if(fatherState != null) //if the state doesn't has a father (It's the start state)
                state.setCost(1); //Add the cost as the father costs plus one
            else
                state.setCost(0); //Set the cost as 0
            openSet.add(state);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return name of algorithm - BestFirstSearch
     */
    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
