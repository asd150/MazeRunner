package GeneticAlgorithm;

public class GA {
    /*
     *1. randomly generate maze. How many? (2)
     * 2. randomly select 2 maze
     * 3.crossover
     * 4.mutate
     * 5.if(fitness(prevHardermaze) < fitness(presentMaze)
     *     replace hardMazeSofar = presentMaze
     *     6. repeat  till you reach some limit
     *     7. **/

    private int dimensions;
    private double probability;
    public GA(int dim,double prob){
        this.dimensions = dim;
        this.probability  = prob;
    }


    public void GenerateMazesRandomly(){

    }
}
