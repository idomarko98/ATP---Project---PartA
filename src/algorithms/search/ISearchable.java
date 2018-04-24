package algorithms.search;

import java.util.List;

public interface ISearchable {
   AState getStartState();
   boolean isGoalState(AState state);
   List<AState> getAllPossibleStates(AState state);
}
