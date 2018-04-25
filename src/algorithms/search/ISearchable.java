package algorithms.search;

import java.util.List;

/**
 * Interface for a searchable object
 */
public interface ISearchable {
   AState getStartState();
   boolean isGoalState(AState state);
   List<AState> getAllPossibleStates(AState state);
}
