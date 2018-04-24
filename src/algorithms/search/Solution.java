package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class Solution {
    private ArrayList<AState> statesPath;

    public Solution(Stack<AState> statesPath) {
        this.statesPath = new ArrayList<>();
        insertToList(statesPath);
    }

    private void insertToList(Stack<AState> statesPath) {
        while (!statesPath.empty())
            this.statesPath.add(statesPath.pop());
    }

    public ArrayList<AState> getSolutionPath() {
        ArrayList<AState> copy = new ArrayList<>();
        for(int i = 0;i<statesPath.size();i++)
            copy.add(statesPath.get(i));
        return copy/*solutionPath*/;
    }
}
