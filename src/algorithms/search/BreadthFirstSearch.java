package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected Queue<AState> openSet;
    protected Set<AState> closedSet;
    protected Map<AState,AState> meta; //Probaly will be changed - state, father of state
    private AState root;

    public BreadthFirstSearch(){
        openSet = new LinkedList<>();
        closedSet = new LinkedHashSet<>();
        meta = new HashMap<>();
    }

    @Override
    public Solution solve(ISearchable domain) {
        root = domain.getStartState();
        meta.put(root, null);
        //openSet.add(root);
        addToSet(root);
        AState temp;
        while(!openSet.isEmpty()){
            temp = openSet.poll();

            if(domain.isGoalState(temp))
                return constructPath(meta, temp);

            List<AState> possibleStates = domain.getAllPossibleStates(temp);
            for(int i = 0; i < possibleStates.size(); i++)
            {
                AState child = possibleStates.get(i);
                if(closedSet.contains(child))
                    continue;
                if(!openSet.contains(child))
                {
                    meta.put(child, temp);
                    //openSet.add(child);
                    addToSet(child);
                }
            }

            closedSet.add(temp);
        }
        return null;
    }

    protected void addToSet(AState state){
        openSet.add(state);
    }

    private Solution constructPath(Map<AState, AState> meta, AState temp) {
        Stack<AState> statesPath = new Stack<>();
        statesPath.push(temp);
        while(meta.get(temp) != null){
            temp = meta.get(temp);
            statesPath.push(temp);
        }
        Solution solution = new Solution(statesPath);
        return solution;
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return 0;
    }
}
