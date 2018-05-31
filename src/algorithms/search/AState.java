package algorithms.search;

import java.io.Serializable;

/**
 * Abstract for state
 * Data member: cost of a move
 */
public abstract class AState implements Comparable, Serializable {

    private int cost;

    /** Constructor
     * @param cost - cost of a move
     */
    public AState(int cost){
        this.cost = cost;
    }

    /**
     * Default Constructor
     */
    public AState(){
        this.cost = 0;
    }

    /** Setter for cost
     * @param cost - cost of a move
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /** Getter for cost
     * @return cost(int)
     */
    public int getCost() {
        return cost;
    }

    /** Add a cost to the cost of current state
     * @param cost - cost to add to this.cost
     */
    public void addCost(int cost){
        this.cost += cost;
    }

    /** Compare State according to its cost
     * @param o - object to be compared to
     * @return return 0 if not comparable. return the difference between 2 costs
     */
    @Override
    public int compareTo(Object o)
    {
        if(!(o instanceof AState))
            return 0;
        AState aState = (AState)o;
        return this.cost - aState.cost;
    }
}
