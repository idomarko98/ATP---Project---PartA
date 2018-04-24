package algorithms.search;

public abstract class AState implements Comparable {

    private int cost;

    public AState(int cost){
        this.cost = cost;
    }

    public AState(){
        this.cost = 0;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public int compareTo(Object o)
    {
        if(!(o instanceof AState))
            return 0;
        AState aState = (AState)o;
        return this.cost - aState.cost;
    }
}
