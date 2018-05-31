package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class to describe a solution
 * Data member: statePath - Path of the states which lead to the solution
 */
public class Solution implements Serializable {
    private ArrayList<AState> statesPath;

    /** Constructor
     * @param statesPath - The solution path
     */
    public Solution(Stack<AState> statesPath) {
        this.statesPath = new ArrayList<>();
        insertToList(statesPath);
    }

    /** Add to data member solution path
     * @param statesPath - solution path
     */
    private void insertToList(Stack<AState> statesPath) {
        while (!statesPath.empty())
            this.statesPath.add(statesPath.pop());
    }

    /** Get the solution path
     * @return solution path
     */
    public ArrayList<AState> getSolutionPath() {
        ArrayList<AState> copy = new ArrayList<>();
        for(int i = 0;i<statesPath.size();i++)
            copy.add(statesPath.get(i));
        return copy/*solutionPath*/;
    }
}
