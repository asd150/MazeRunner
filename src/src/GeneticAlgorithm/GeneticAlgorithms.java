package GeneticAlgorithm;

import java.security.cert.TrustAnchor;
import java.util.*;

public class GeneticAlgorithms {
    //create maze
    //find the probabity
    //randomly select the mazes
    //crossover
    //mutate
    //find number of steps
    //
    private int dimensions = 0;
    private double prob = 0;
  public void Start(String algo){

      int dim = dimensions = 500;
      double prob = this.prob =  0.3;
      MazeClass mazeClass = new MazeClass(5,0.3);



      boolean solvable1 = false;
      boolean solvable2 = false;

      //check if two maze are solvable

    Map<String,String> info1 = new HashMap<>();
      Map<String,String> info2 = new HashMap<>();

    Cell[][] hardMaze = new Cell[dimensions][dimensions];
   int limit =0;
      boolean initialMutation = true;
      double maxFitness =0;


      while(limit <5000){

          Cell[][] mazeOne = mazeClass.generateMaze(dim,prob);
          Cell[][] mazeTwo = mazeClass.generateMaze(dim,prob);
           // System.out.println(isSolvable(mazeOne) + " "+ isSolvable(mazeTwo));
          Cell[][] CopiedMaze1 = Copying(mazeOne);
          Cell[][] CopeidMaze2 = Copying(mazeTwo);
          //System.out.println("Pass *");
          if(initialMutation){

              switch (algo) {
                  case "DFS":


                              Cell[][] mutated = new Cell[dimensions][dimensions];
                              mutated = Copying(CrossOver(mazeOne,mazeTwo));

                             // printMazr(mutated);
                              if(isSolvable(mutated)){
                                  Map<String,String> map = DFSHard(mutated);
                                  info1 = DFSHard(mazeOne);
                                  if(calculate(map) > calculate(info1)) {

                                      //printMazr(mazeOne);
                                     // System.out.println("\n");
                                      //printMazr(mutated);
                                    //  System.out.println("*****");

                                      hardMaze = Copying(mutated);
                                      mazeOne = Copying(mutated);


                                  }
                              }
                      initialMutation = false;


                      break;
                  case "BFS":
                      Cell[][] mutated1 = new Cell[dimensions][dimensions];
                      mutated1 = Copying(CrossOver(mazeOne,mazeTwo));

                      // printMazr(mutated);
                      if(isSolvable(mutated1)){
                          Map<String,String> map = DFSHard(mutated1);
                          info1 = DFSHard(mazeOne);
                          if(calculate(map) > calculate(info1)) {

                              //printMazr(mazeOne);
                              // System.out.println("\n");
                              //printMazr(mutated);
                              //  System.out.println("*****");

                              hardMaze = Copying(mutated1);
                              mazeOne = Copying(mutated1);


                          }
                      }
                      initialMutation = false;


                      break;
                  case "Astar":

                      break;
                  default:
                      //manhattanAstar
                      break;
              }
          }
          else {
              Cell[][] nextMaze = mazeClass.generateMaze(dimensions,prob);

              switch (algo) {
                  case "DFS":
                             Cell[][] mutated = new Cell[dimensions][dimensions];
                      mutated = Copying(CrossOver(mazeOne,nextMaze));
                      if(isSolvable(mutated)){
                          Map<String,String> map =DFSHard(nextMaze);
                          Map<String,String> map1 = DFSHard(mutated);

                          if(calculate(map) > calculate(map1)){

                              //System.out.println("*****");
                              if(calculate(map) > maxFitness) {
                                  maxFitness = calculate(map);
                                  hardMaze = Copying(mutated);
                                  System.out.println(limit);
                              }
                              mazeOne = Copying(mutated);


                          }
                          else{
                              mazeOne = Copying(CopiedMaze1);
                          }

                      }else{
                          mazeOne = Copying(CopiedMaze1);
                      }


                      break;
                  case "BFS":
                      Cell[][] mutated1 = new Cell[dimensions][dimensions];
                      mutated = Copying(CrossOver(mazeOne,nextMaze));
                      if(isSolvable(mutated1)){
                          Map<String,String> map =DFSHard(nextMaze);
                          Map<String,String> map1 = DFSHard(mutated1);

                          if(calculate(map) > calculate(map1)){

                              //System.out.println("*****");
                              if(calculate(map) > maxFitness) {
                                  maxFitness = calculate(map);
                                  hardMaze = Copying(mutated1);
                                  System.out.println(limit);
                              }
                              mazeOne = Copying(mutated1);


                          }
                          else{
                              mazeOne = Copying(CopiedMaze1);
                          }

                      }else{
                          mazeOne = Copying(CopiedMaze1);
                      }
                      break;
                  case "Astar":

                      break;
                  default:
                      //manhattanAstar
                      break;
              }
          }
           // System.out.println(limit%10);
          limit++;
      }

      System.out.println(limit);
      System.out.println(maxFitness);
      printMazr(hardMaze);
      Map<String,String> result = DFSHard(hardMaze);
      System.out.println(result.get("PATHLEN"));
      System.out.println(result.get("MAXFRINGE"));
      System.out.println(result.get("NODESEXP"));
//      if(maxFitness!=0) {
//          for (int i = 0; i < dimensions; i++) {
//              for (int j = 0; j < dimensions; j++) {
//                  System.out.print(hardMaze[i][j].getOccupied() + " ");
//              }
//              System.out.println();
//          }
//
//      }

  }

  public Cell[][] CrossOver(Cell[][] mazeOne, Cell[][] mazeTwo){
      Cell[][] afterCrossOver = new Cell[dimensions][dimensions];

      for (int i=0; i<dimensions; i++){
          for (int j=0; j<dimensions; j++){
              if (i+j+2<=dimensions){
                  afterCrossOver[i][j] = mazeOne[i][j];
              } else {
                  afterCrossOver[i][j] = mazeTwo[i][j];
                  //afterCrossOver[i][j].setOccupied(100);
              }
          }
      }

      afterCrossOver = mutation(afterCrossOver);

        //System.out.println(isSolvable(afterCrossOver) + " crossover ");



      return afterCrossOver;
  }
  public Cell[][] mutation(Cell[][] afterCrossOver){
      Random random = new Random();
      for(int i=0;i<=dimensions/2;i++){
         int x = random.nextInt(dimensions-1);
         int y = random.nextInt(dimensions-1);

         if(afterCrossOver[x][y].getOccupied() == 0){
             afterCrossOver[x][y].setOccupied(-1);
         }
         else if(afterCrossOver[x][y].getOccupied() == -1){
             afterCrossOver[x][y].setOccupied(0);
         }
      }
        afterCrossOver[0][0].setOccupied(0);
      afterCrossOver[dimensions-1][dimensions-1].setOccupied(0);

      return afterCrossOver;
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

  public boolean isSolvable(Cell[][] newMaze){

          //Instant start = Instant.now();
          long start = System.currentTimeMillis();

          boolean[][] isVisited = new boolean[dimensions][dimensions];
          Stack<Cell> stack = new Stack<>();
          stack.add(newMaze[0][0]);
          isVisited[0][0] = true;
          int moves =0;
          Map<Cell, Cell> path = new HashMap<>();

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


              if(tempX == dimensions-1 && tempY == dimensions-1){
                  return true;
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


          return false;

  }

      public int pathSize(Map<Cell,Cell> path,Cell[][]maze){
        int len = 0;
          Cell key = maze[dimensions-1][dimensions-1];
         // System.out.println(" PATHSIZE "+ path.size());
//          ArrayList<Cell> arrayList = new ArrayList<>();
//          arrayList.add(key);
//          while(!path.isEmpty()){
//              if(path.containsKey(key)){
//                  Cell temp = path.get(key);
//                  key = temp;
//                  arrayList.add(key);
//
//              }
//              else{
//                  path.clear();
//              }
//          }
            len = path.size();




        return len;
      }



      public double calculate(Map<String,String> info){
      if(info.isEmpty()){return 0;}
      String pathLen = info.get("PATHLEN");
      String fringe = info.get("MAXFRINGE");
      String NodesExp = info.get("NODESEXP");


      return (0.5*Double.parseDouble(fringe)) + (0.25*Double.parseDouble(NodesExp) + (0.25*Double.parseDouble(pathLen)));
      }

    public Map<String,String> DFSHard(Cell[][] maze){



        long start = System.currentTimeMillis();
        Map<String,String> info = new HashMap<>();
        Cell[][] newMaze = maze;
        boolean[][] isVisited = new boolean[dimensions][dimensions];
        Stack<Cell> stack = new Stack<>();
        stack.add(newMaze[0][0]);
        isVisited[0][0] = true;
        int moves =0;
        int fringeSize =0;
        int nodesExpanded = 0;
        Map<Cell, Cell> path = new HashMap<>();

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
            //System.out.println(stack.size());
            Cell temp = stack.pop();
            nodesExpanded++;
            int tempX = temp.getX();
            int tempY = temp.getY();
            isVisited[tempX][tempY] = true;
            moves++;
            //System.out.println(tempX + " " + tempY);
            if(tempX == dimensions-1 && tempY == dimensions-1){
                //System.out.println(moves + " Path found dfs ");

                long end = System.currentTimeMillis();
                //System.out.println((end-start)/60 + " s");

                //Instant end = Instant.now();
                //System.out.println(Duration.between(start,end));
                info.put("MAXFRINGE",String.valueOf(fringeSize));
                info.put("NODESEXP",String.valueOf(nodesExpanded));
                int x = pathSize(path,maze);
                info.put("PATHLEN",String.valueOf(x));

                return info;
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

            if(fringeSize<stack.size()){
                fringeSize = stack.size();
            }

        }

        return info;
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

