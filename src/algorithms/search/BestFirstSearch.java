package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {

    public BestFirstSearch(){
        super();
        openSet = new PriorityQueue<>();
    }

    @Override
    protected void addToSet(AState state, AState fatherState) {
        if(fatherState != null)
            state.setCost(fatherState.getCost()+1);
        else
            state.setCost(0);
        openSet.add(state);
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
