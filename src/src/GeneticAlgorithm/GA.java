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
    //private Map<Integer, Integer> storeSteps = new HashMap<>();
   // private double[] storeProbability = new double[4];

    public GA(int dim, double prob) {
        this.dimensions = dim;
        this.probability = prob;
        this.mazeClass = new MazeClass(dim, prob);
    }


    private Cell[][] GenerateMazesRandomly() {

        Cell[][] mazeCreated = mazeClass.generateMaze(dimensions, probability);


        while (true) {
            if (isSolvable(mazeCreated)) {


                return mazeCreated;
            } else {
                mazeCreated = mazeClass.generateMaze(dimensions, probability);

            }

        }
    }


    public Cell[][] start(String userInput) {
        Cell[][] mazeOne = new Cell[dimensions][dimensions];
        Cell[][] mazeTwo = new Cell[dimensions][dimensions];
        Cell[][] mazeOneCopy = new Cell[dimensions][dimensions];
        Cell[][] mazeTwoCopy = new Cell[dimensions][dimensions];
        Cell[][] hardestSoFar = new Cell[dimensions][dimensions];

        double hardestSoFarVal = 0;


//      Cell[][] mazeThree  = new Cell[dimensions][dimensions];
//      Cell[][] mazeFour = new Cell[dimensions][dimensions];

        int limit = 0;
        SearchAlgo searchAlgo = new SearchAlgo(dimensions);

        boolean firstIteration = true; //Recieved two solvable mazes, going to run first iteration

        Map<String, String> parent1 = new HashMap<>();
        Map<String, String> parent2 = new HashMap<>();
        Map<String, String> child = new HashMap<>();

        while (limit < 500) {

            //System.out.println("Pass1");


            if (firstIteration) {
                mazeOne = Copying(GenerateMazesRandomly());
                mazeTwo = Copying(GenerateMazesRandomly());
                mazeOneCopy = Copying(mazeOne);
                mazeTwoCopy = Copying(mazeTwo);
                //System.out.println(isSolvable(mazeOne) + " " + isSolvable(mazeTwo));
                if (userInput.equals("DFS")) {
                    //run the maze in dfs
                    parent1 = searchAlgo.dfs(mazeOneCopy,false);
                    parent2 = searchAlgo.dfs(mazeTwoCopy,false);
                   // System.out.println(parent1.keySet());
                    //System.out.println(parent2.keySet());
                    double parent1Val = calculateFitness(parent1);

                    double parent2Val = calculateFitness(parent2);
//                parent2.put("fitnessVal", String.valueOf(parent2Val));
                   // System.out.println("Hardest3 " + isSolvable(hardestSoFar));
                    double tempVal;
                    if (parent1Val >= parent2Val) {
                        //System.out.println("READHED1");
                        hardestSoFar = Copying(mazeOne);
                        hardestSoFarVal = parent1Val;
                       // System.out.println(isSolvable(hardestSoFar) + "++++++ 1 ++++++++++++");
                    } else {
                        //System.out.println("READHED2");
                        hardestSoFar = Copying(mazeTwo);
                        hardestSoFarVal = parent2Val;
                        //System.out.println(isSolvable(hardestSoFar) + "+++++++ 2 +++++++++++");

                    }
                    //System.out.println("Hardest1 " + isSolvable(hardestSoFar));
                    Cell[][] mutated = new Cell[dimensions][dimensions];
                    Cell[][] mutatedCopy = new Cell[dimensions][dimensions];
                    //System.out.println("Hardest21 " + isSolvable(hardestSoFar));
                    mutated = CrossOver(mazeOneCopy, mazeTwoCopy);


                    //System.out.println("Hardest22 " + isSolvable(hardestSoFar));
                   // mutatedCopy = Copying(mutated);
                    if (isSolvable(mutated)) {
                        //System.out.println("mutated "+isSolvable(mutated));
                        child = searchAlgo.dfs(mutated,false);
                        double childVal = calculateFitness(child);


                        if (childVal >= hardestSoFarVal) {
                            //System.out.println("HARDEST 4" + isSolvable(hardestSoFar));
                            hardestSoFar = Copying(mutated);
                            //System.out.println("HARDEST 5" + isSolvable(hardestSoFar));
                            hardestSoFarVal = childVal;
                        }
                        firstIteration = false;
                    }else{

                    }
//                    if(!isSolvable(hardestSoFar)){
//                   // System.out.println("Hardest2 " + isSolvable(hardestSoFar));
//                    firstIteration = true;
//                    }


                } else if (userInput.equals("BFS")) {
                    //run the maze in bfs
                    parent1 = searchAlgo.bfs(mazeOneCopy,false);
                    parent2 = searchAlgo.bfs(mazeTwoCopy,false);
                    double parent1Val = calculateFitness(parent1);

                    double parent2Val = calculateFitness(parent2);
//                parent2.put("fitnessVal", String.valueOf(parent2Val));

                    double tempVal;
                    if (parent1Val >= parent2Val) {
                        hardestSoFar = Copying(mazeOne);
                        hardestSoFarVal = parent1Val;
                    } else {
                        hardestSoFar = Copying(mazeTwo);
                        hardestSoFarVal = parent2Val;
                    }
                    //System.out.println("+++++++++++++++++++++++++++++");
                    Cell[][] mutated = new Cell[dimensions][dimensions];
                   // Cell[][] mutatedCopy = new Cell[dimensions][dimensions];
                    //Cell[][] fitCopy = Copying(hardestSoFar);
                    //System.out.println("COPY1 " + isSolvable(fitCopy));
                   // System.out.println("+++++++++++++++++++++++++++++");
                   // System.out.println("1 "+isSolvable(hardestSoFar));
                    mutated = Copying(CrossOver(mazeOneCopy, mazeTwoCopy));
                    //System.out.println("2 "+isSolvable(hardestSoFar));
                    //mutated = Copying(mutation(mutatedCopy));

//                    System.out.println("3 "+isSolvable(hardestSoFar));
//                    System.out.println("+++++++++++++++++++++++++++++");
//                    System.out.println("COPY2 " + isSolvable(fitCopy));
                    if (isSolvable(mutated)) {
                        child = searchAlgo.bfs(mutated,false);
                        double childVal = calculateFitness(child);
//                    System.out.println("****CHILD*********");
//                    System.out.println(childVal);
                        if (childVal >= hardestSoFarVal) {

                            hardestSoFar = Copying(mutated);
                            hardestSoFarVal = childVal;
                            firstIteration = false;
                        }

                    }
//                System.out.println("****PARENT 1*******");
//                System.out.println(parent1Val);
//                System.out.println("****PARENT 2*******");
//                System.out.println(parent2Val);

                } else if (userInput.equals("Euclidean")) {
                    //run the maze in bfs
                    parent1 = searchAlgo.EuclidianAstar(mazeOne,false);
                    parent2 = searchAlgo.EuclidianAstar(mazeTwo,false);
                    double parent1Val = calculateFitness(parent1);

                    double parent2Val = calculateFitness(parent2);
//                parent2.put("fitnessVal", String.valueOf(parent2Val));

                    double tempVal;
                    if (parent1Val >= parent2Val) {
                        hardestSoFar = Copying(mazeOne);
                        hardestSoFarVal = parent1Val;
                    } else {
                        hardestSoFar = Copying(mazeTwo);
                        hardestSoFarVal = parent2Val;
                    }
                    Cell[][] mutated = new Cell[dimensions][dimensions];
                    mutated = CrossOver(mazeOne, mazeTwo);
                    if (isSolvable(mutated)) {
                        //firstIteration = true;
                        child = searchAlgo.EuclidianAstar(mutated,false);
                        double childVal = calculateFitness(child);
//                    System.out.println("****CHILD*********");
//                    System.out.println(childVal);
                        if (childVal >= hardestSoFarVal) {
                            hardestSoFar = Copying(mutated);
                            hardestSoFarVal = childVal;
                            firstIteration = false;
                        }

                    }

                } else if (userInput.equals("Manhattan")) {
                    //run the maze in bfs
                    parent1 = searchAlgo.ManhattanAstar(mazeOne,false);
                    parent2 = searchAlgo.ManhattanAstar(mazeTwo,false);
                    double parent1Val = calculateFitness(parent1);

                    double parent2Val = calculateFitness(parent2);
//                parent2.put("fitnessVal", String.valueOf(parent2Val));

                    double tempVal;
                    if (parent1Val >= parent2Val) {
                        hardestSoFar = Copying(mazeOne);
                        hardestSoFarVal = parent1Val;
                    } else {
                        hardestSoFar = Copying(mazeTwo);
                        hardestSoFarVal = parent2Val;
                    }
                    Cell[][] mutated = new Cell[dimensions][dimensions];
                    mutated = CrossOver(mazeOne, mazeTwo);
                    if (isSolvable(mutated)) {
                        child = searchAlgo.ManhattanAstar(mutated,false);
                        double childVal = calculateFitness(child);
//                    System.out.println("****CHILD*********");
//                    System.out.println(childVal);
                        if (childVal >= hardestSoFarVal) {
                            hardestSoFar = Copying(mutated);
                            hardestSoFarVal = childVal;
                            firstIteration = false;
                        }

                    }
                }
                //firstIteration = false;
               // System.out.println("********HARDEST SO FAR******");
                //System.out.println(hardestSoFarVal);
                //azr(hardestSoFar);

            } else {
                //System.out.println("ENTER2");
                Cell[][] maze2 = new Cell[dimensions][dimensions];
                maze2 = Copying(GenerateMazesRandomly());

                if (userInput.equals("DFS")) {
                    //run the maze in dfs
                    parent1 = searchAlgo.dfs(maze2,false);
                    double parent1Val = calculateFitness(parent1);

                    if (parent1Val >= hardestSoFarVal) {
                        hardestSoFar = Copying(maze2);
                        hardestSoFarVal = parent1Val;
                    }

                    Cell[][] mutated = new Cell[dimensions][dimensions];

                    mutated = CrossOver(hardestSoFar, maze2);
                    //mutated = mutation(mutated);
                    if (isSolvable(mutated)) {
                        child = searchAlgo.dfs(mutated,false);
                        double childVal = calculateFitness(child);

                        if (childVal >= hardestSoFarVal) {
                            hardestSoFar = Copying(mutated);
                            hardestSoFarVal = childVal;
                        }

                    }

                } else if (userInput.equals("BFS")) {
                    //run the maze in dfs
                    parent1 = searchAlgo.bfs(maze2,false);
                    double parent1Val = calculateFitness(parent1);

                    if (parent1Val >= hardestSoFarVal) {
                        hardestSoFar = Copying(maze2);
                        hardestSoFarVal = parent1Val;
                    }

                    Cell[][] mutated = new Cell[dimensions][dimensions];

                    mutated = CrossOver(hardestSoFar, maze2);
                   // mutated = mutation(mutated);
                    if (isSolvable(mutated)) {
                        child = searchAlgo.bfs(mutated,false);
                        double childVal = calculateFitness(child);

                        if (childVal >= hardestSoFarVal) {
                            hardestSoFar = Copying(mutated);
                            hardestSoFarVal = childVal;
                        }

                    }


                } else if (userInput.equals("Euclidean")) {
                    //run the maze in dfs
                    parent1 = searchAlgo.EuclidianAstar(maze2,false);
                    double parent1Val = calculateFitness(parent1);

                    if (parent1Val >= hardestSoFarVal) {
                        hardestSoFar = Copying(maze2);
                        hardestSoFarVal = parent1Val;
                    }

                    Cell[][] mutated = new Cell[dimensions][dimensions];
                    mutated = CrossOver(hardestSoFar, maze2);
                    if (isSolvable(mutated)) {
                        child = searchAlgo.EuclidianAstar(mutated,false);
                        double childVal = calculateFitness(child);

                        if (childVal >= hardestSoFarVal) {
                            hardestSoFar = Copying(mutated);
                            hardestSoFarVal = childVal;
                        }

                    }
                } else if (userInput.equals("Manhattan")) {
                    //run the maze in dfs
                    parent1 = searchAlgo.ManhattanAstar(maze2,false);
                    double parent1Val = calculateFitness(parent1);

                    if (parent1Val >= hardestSoFarVal) {
                        hardestSoFar = Copying(maze2);
                        hardestSoFarVal = parent1Val;
                    }

                    Cell[][] mutated = new Cell[dimensions][dimensions];
                    mutated = CrossOver(hardestSoFar, maze2);
                    if (isSolvable(mutated)) {
                        child = searchAlgo.ManhattanAstar(mutated,false);
                        double childVal = calculateFitness(child);

                        if (childVal >= hardestSoFarVal) {
                            hardestSoFar = Copying(mutated);
                            hardestSoFarVal = childVal;
                        }

                    }
                }

            }

            limit++;
        }

       // System.out.println("********HARDEST SO FAR******");
        //System.out.println(hardestSoFarVal);
       // printMazr(hardestSoFar);
        //System.out.println(isSolvable(hardestSoFar));
        if(isSolvable(hardestSoFar)){
       // printMazr(hardestSoFar);

            if(userInput.equals("DFS")) {
                System.out.println("Start");
                Map<String, String> x = searchAlgo.dfs(hardestSoFar, true);
                System.out.println("DFS : " + x.get("PATHLEN") + " " + x.get("MOVES") + " " + x.get("MAXFRINGE"));
                System.out.println("End");
            }
            else if(userInput.equals("BFS")){
                System.out.println("Start");
                Map<String, String> x = searchAlgo.bfs(hardestSoFar, true);
                System.out.println("BFS : " + "path length "+x.get("PATHLEN") + " Moves " + x.get("MOVES") + " Max Fringe " + x.get("MAXFRINGE"));
                System.out.println("End");

            }
            else if(userInput.equals("Euclidean")){
                System.out.println("Start");
                Map<String, String> x = searchAlgo.EuclidianAstar(hardestSoFar, true);
                System.out.println("Euclidean : " + x.get("PATHLEN") + " " + x.get("MOVES") + " " + x.get("MAXFRINGE"));
                System.out.println("End");
            }
            else {//manhattan
                System.out.println("Start");
                Map<String, String> x = searchAlgo.dfs(hardestSoFar, true);
                System.out.println("Manhattan : " + x.get("PATHLEN") + " " + x.get("MOVES") + " " + x.get("MAXFRINGE"));
                System.out.println("End");
            }
            //Map<String,String> dfs = searchAlgo.dfs(hardestSoFar);
           // Map<String,String> eu = searchAlgo.EuclidianAstar(hardestSoFar);
         //   Map<String,String> ma = searchAlgo.ManhattanAstar(hardestSoFar);


           // System.out.println("DFS : " + dfs.get("PATHLEN") + " " + dfs.get("MOVES") + " " + dfs.get("MAXFRINGE"));
           // System.out.println("EUCIDEAN : " + eu.get("PATHLEN") + " " + eu.get("MOVES") + " " + eu.get("MAXFRINGE"));
          //  System.out.println("MANHATTAN : " + ma.get("PATHLEN") + " " + ma.get("MOVES") + " " + ma.get("MAXFRINGE"));


            return hardestSoFar;
        }
        else {
            //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
            start(userInput);
        }
return null;
    }


    public boolean isSolvable(Cell[][] newMaze) {

        //Instant start = Instant.now();
        long start = System.currentTimeMillis();

        boolean[][] isVisited = new boolean[dimensions][dimensions];
        Stack<Cell> stack = new Stack<>();
        stack.add(newMaze[0][0]);
        isVisited[0][0] = true;
        int moves = 0;
        Map<Cell, Cell> path = new HashMap<>();
        Map<String, Integer> getStepg = new HashMap<>();

        if (newMaze[1][0].getOccupied() != -1) {
            isVisited[1][0] = true;
            stack.push(newMaze[1][0]);
            path.put(newMaze[1][0], newMaze[0][0]);

        }
        if (newMaze[0][1].getOccupied() != -1) {
            isVisited[0][1] = true;
            stack.push(newMaze[1][0]);
            path.put(newMaze[0][1], newMaze[0][0]);

        }
        while (!stack.isEmpty()) {

            Cell temp = stack.pop();

            int tempX = temp.getX();
            int tempY = temp.getY();
            isVisited[tempX][tempY] = true;
            moves++;

            if (tempX == dimensions - 1 && tempY == dimensions - 1) {
                getStepg.put("STEPS", moves);

                return true;
            }

            if (tempX - 1 > 0 && !isVisited[tempX - 1][tempY] && newMaze[tempX - 1][tempY].getOccupied() != -1) {
                stack.push(newMaze[tempX - 1][tempY]);
                path.put(newMaze[tempX - 1][tempY], newMaze[tempX][tempY]);
                isVisited[tempX - 1][tempY] = true;
            }
            if (tempY - 1 > 0 && newMaze[tempX][tempY - 1].getOccupied() != -1 && !isVisited[tempX][tempY - 1]) {
                stack.push(newMaze[tempX][tempY - 1]);
                path.put(newMaze[tempX][tempY - 1], newMaze[tempX][tempY]);
                isVisited[tempX][tempY - 1] = true;
            }
            if (tempX + 1 < dimensions && newMaze[tempX + 1][tempY].getOccupied() != -1 && !isVisited[tempX + 1][tempY]) {
                stack.push(newMaze[tempX + 1][tempY]);
                path.put(newMaze[tempX + 1][tempY], newMaze[tempX][tempY]);
                isVisited[tempX + 1][tempY] = true;

            }
            if (tempY + 1 < dimensions && newMaze[tempX][tempY + 1].getOccupied() != -1 && !isVisited[tempX][tempY + 1]) {
                stack.push(newMaze[tempX][tempY + 1]);
                path.put(newMaze[tempX][tempY + 1], newMaze[tempX][tempY]);
                isVisited[tempX][tempY + 1] = true;
            }


        }


        return false;

    }

    public Cell[][] Copying(Cell[][] maze) {
        Cell[][] copiedMaze = new Cell[dimensions][dimensions];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < dimensions; j++) {
                copiedMaze[i][j] = maze[i][j];
            }
        }

        return copiedMaze;
    }

    public void printMazr(Cell[][] print) {
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                System.out.print(print[i][j].getOccupied() + "   ");
            }
            System.out.println();
        }
    }

    private double calculateFitness(Map<String, String> map) {
        /**
         fitnessVal.put("PATHLEN",String.valueOf(NumberOfNodes));
         fitnessVal.put("MAXFRINGE",String.valueOf(maxFinge));
         fitnessVal.put("MOVES",String.valueOf(moves));
         * */
        if(map.isEmpty()){
            //System.out.println("NOTMAT");
            return -1;
        }
        return (0.25 * Double.parseDouble(map.get("MOVES"))) + (0.5 * Double.parseDouble(map.get("PATHLEN"))) + (0.25 * Double.parseDouble(map.get("MAXFRINGE")));
    }

    public Cell[][] CrossOver(Cell[][] mazeOne, Cell[][] mazeTwo) {
        Cell[][] afterCrossOver = new Cell[dimensions][dimensions];

        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (i + j + 2 <= dimensions) {
                    afterCrossOver[i][j] = mazeOne[i][j];
                } else {
                    afterCrossOver[i][j] = mazeTwo[i][j];
                    //afterCrossOver[i][j].setOccupied(100);
                }
            }
        }
       // System.out.println("Before mutation" + isSolvable(afterCrossOver));
        Random random = new Random();
        for (int i = 0; i <= dimensions / 2; i++) {
            int x = random.nextInt(dimensions - 1);
            int y = random.nextInt(dimensions - 1);

            if (afterCrossOver[x][y].getOccupied() == 0) {
                afterCrossOver[x][y].setOccupied(-1);
            } else {
                afterCrossOver[x][y].setOccupied(0);
            }
        }
        ///System.out.println("After mutation" + isSolvable(afterCrossOver));
        afterCrossOver[0][0].setOccupied(0);
        afterCrossOver[dimensions - 1][dimensions - 1].setOccupied(0);
        //Cell[][] copy = new Cell[dimensions][dimensions];

  //      System.out.println(isSolvable(afterCrossOver) + " crossover111 ");
       // afterCrossOver = mutation(afterCrossOver);

//        System.out.println(isSolvable(afterCrossOver) + " crossover ");


        return afterCrossOver;
    }

//    public Cell[][] mutation(Cell[][] afterCrossOver) {
//        Random random = new Random();
//        for (int i = 0; i <= dimensions / 2; i++) {
//            int x = random.nextInt(dimensions - 1);
//            int y = random.nextInt(dimensions - 1);
//
//            if (afterCrossOver[x][y].getOccupied() == 0) {
//                afterCrossOver[x][y].setOccupied(-1);
//            } else {
//                afterCrossOver[x][y].setOccupied(0);
//            }
//        }
//        afterCrossOver[0][0].setOccupied(0);
//        afterCrossOver[dimensions - 1][dimensions - 1].setOccupied(0);
//       // System.out.println("MUTATION" + isSolvable(afterCrossOver));
//        return afterCrossOver;
//    }


}
