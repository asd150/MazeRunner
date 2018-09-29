package GeneticAlgorithm;

public class Cell {

    private int x;
    private int y;
    private int occupied;
    private double heuristic;
    private long totalDistance;
    private boolean isVisited;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.occupied =0;
        this.heuristic =0;
        this.totalDistance = 0;
        this.isVisited = false;
    }

    public boolean getIsVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOccupied() {
        return occupied;
    }
    public void setOccupied(int val){
        this.occupied = val;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    public double getHeuristic() {
        return heuristic;
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(long totalDistance) {
        this.totalDistance = this.totalDistance + totalDistance;
    }
}
