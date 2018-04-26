package algorithms.search;

/**
 * Interface for object that represents a searching algorithm
 */
public interface ISearchingAlgorithm {
    Solution solve(ISearchable domain); //Solve a problem that can be searched in

    String getName(); //Get the name of the algorithm

    int getNumberOfNodesEvaluated(); //Get the number of nodes that the algorithm evaluated as possible paths
}
