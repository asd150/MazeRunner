package GeneticAlgorithm;

import java.util.*;

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
    private MazeClass mazeClass = null;
    private int num = 0;
    private Map<Integer,Integer> storeSteps = new HashMap<>();
    private double[] storeProbability = new double[4];
    public GA(int dim,double prob){
        this.dimensions = dim;
        this.probability  = prob;
         this.mazeClass = new MazeClass(dim,prob);
    }


    private Cell[][] GenerateMazesRandomly() {

        Cell[][] mazeCreated = mazeClass.generateMaze(dimensions, probability);


        while(true) {
             if (isSolvable(mazeCreated).containsKey("STEPS")) {
                 num++;
                 storeSteps.put(num,isSolvable(mazeCreated).get("STEPS"));

                 return mazeCreated;
             } else {
                 mazeCreated = mazeClass.generateMaze(dimensions, probability);

             }

         }
    }


    public void start(){
        Cell[][] mazeOne = new Cell[dimensions][dimensions];
        Cell[][] mazeTwo = new Cell[dimensions][dimensions];
        Cell[][] mazeThree  = new Cell[dimensions][dimensions];
        Cell[][] mazeFour = new Cell[dimensions][dimensions];

        int limit =0;

        while(limit <1){
            System.out.println("Pass1");
            mazeOne = Copying( GenerateMazesRandomly());
            //System.out.println(mazeOne[1][1].getOccupied());
            mazeTwo = Copying(GenerateMazesRandomly());
            mazeThree = Copying(GenerateMazesRandomly());
            mazeFour = Copying(GenerateMazesRandomly());


            //get the path steps
            double sum = 0;
            for(int i=1;i<=4;i++){
                sum = sum+storeSteps.get(i);
            }
            //store the probability into array
            for(int i=1;i<=4;i++){
                int val = storeSteps.get(i);
                System.out.println(val);
                double prob = val/sum;
                storeProbability[i-1] = prob;

            }
            //generate the random number
            int it = 0;
            Cell[][] ChosenMaze1 = new Cell[dimensions][dimensions];
            Cell[][] Chosenmaze2 =  new Cell[dimensions][dimensions];
            boolean firstPass = true;



            limit++;
        }
    }


    public Map<String,Integer> isSolvable(Cell[][] newMaze){

        //Instant start = Instant.now();
        long start = System.currentTimeMillis();

        boolean[][] isVisited = new boolean[dimensions][dimensions];
        Stack<Cell> stack = new Stack<>();
        stack.add(newMaze[0][0]);
        isVisited[0][0] = true;
        int moves =0;
        Map<Cell, Cell> path = new HashMap<>();
        Map<String,Integer> getStepg = new HashMap<>();

        if(newMaze[1][0].getOccupied()!=-1){
            isVisited[1][0] = true;
            stack.push(newMaze[1][0]);
            path.put(newMaze[1][0],newMaze[0][0]);

        }
        if(newMaze[0][1].getOccupied()!=-1){
            isVisited[0][1] = true;
            stack.push(newMaze[1][0]);
            path.put(newMaze[0][1],newMaze[0][0]);

        }
        while(!stack.isEmpty()){

            Cell temp = stack.pop();

            int tempX = temp.getX();
            int tempY = temp.getY();
            isVisited[tempX][tempY] = true;
            moves++;

            if(tempX == dimensions-1 && tempY == dimensions-1){
                getStepg.put("STEPS",moves);

                return getStepg;
            }

            if(tempX-1>0 && !isVisited[tempX-1][tempY] && newMaze[tempX-1][tempY].getOccupied()!=-1 ){
                stack.push(newMaze[tempX-1][tempY]);
                path.put(newMaze[tempX-1][tempY],newMaze[tempX][tempY]);
                isVisited[tempX-1][tempY] = true;
            }
            if(tempY-1>0 && newMaze[tempX][tempY-1].getOccupied()!=-1 && !isVisited[tempX][tempY-1]){
                stack.push(newMaze[tempX][tempY-1]);
                path.put(newMaze[tempX][tempY-1],newMaze[tempX][tempY]);
                isVisited[tempX][tempY-1] = true;
            }
            if(tempX+1<dimensions && newMaze[tempX+1][tempY].getOccupied()!=-1 && !isVisited[tempX+1][tempY] )
            {
                stack.push(newMaze[tempX+1][tempY]);
                path.put(newMaze[tempX+1][tempY],newMaze[tempX][tempY]);
                isVisited[tempX+1][tempY] = true;

            }
            if(tempY+1<dimensions && newMaze[tempX][tempY+1].getOccupied()!=-1 && !isVisited[tempX][tempY+1]){
                stack.push(newMaze[tempX][tempY+1]);
                path.put(newMaze[tempX][tempY+1],newMaze[tempX][tempY]);
                isVisited[tempX][tempY+1] = true;
            }


        }


        return getStepg;

    }
    public Cell[][] Copying (Cell[][] maze){
        Cell[][] copiedMaze = new Cell[dimensions][dimensions];

        for(int i=0;i<maze.length;i++){
            for (int j=0;j<dimensions;j++){
                copiedMaze[i][j] = maze[i][j];
            }
        }

        return copiedMaze;
    }



    public void printMazr(Cell[][] print){
        for(int i=0;i<dimensions;i++){
            for(int j=0;j<dimensions;j++){
                System.out.print(print[i][j].getOccupied() + "   ");
            }
            System.out.println();
        }
    }
}
