package GeneticAlgorithm;

import java.util.*;

public class MazeClass{
    //createMaze
    //same for all search algorithms

   public  Cell[][] map = null;
    int dimensions;
    double fitness =0;
    public MazeClass(int dim, double prob){
        this.dimensions = dim;
        map = new Cell[dim][dim];
        generateMaze(dim,prob);
    }

    public Cell[][] generateMaze(int dim, double prob){
        Cell[][] makeMaze = new Cell[dim][dim];
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                double randomVal = Math.random();
                makeMaze[i][j] = new Cell(i,j);
                if(randomVal<=prob){
                    makeMaze[i][j].setOccupied(-1);
                }
                else {
                    makeMaze[i][j].setOccupied(0);
                }
            }

        }
        makeMaze[0][0].setOccupied(0);
        makeMaze[dim-1][dim-1].setOccupied(0);
        //printMaze();
        map = makeMaze;
        return makeMaze;
    }

    public void printMaze(Cell[][] maze){
        //System.out.println(map[1][0].getOccupied());
        for(int i=0;i<maze.length;i++){
            for(int j=0;j<maze[0].length;j++){
                System.out.print(maze[i][j].getOccupied() + " ");
            }
            System.out.println();
        }
    }

    //bfs
    public Map<Cell, Cell> bfs(){

     long startTime =  System.currentTimeMillis();

        Map<Cell, Cell> path = new HashMap<>();

        Cell[][] newMaze = copyingMaze();
        boolean[][] visitedTrack = new  boolean[dimensions][dimensions];
        //System.out.println(newMaze[0][0].getIsVisited() +  " ** ");
        int currentMoves =1;

        Queue<Cell> queue = new LinkedList<>();
        visitedTrack[0][0] =true;
        ((LinkedList<Cell>) queue).add(newMaze[0][0]);
        int i=0;
        int j=0;
        if(newMaze[1][0].getOccupied()!=-1){
            visitedTrack[1][0] = true;
            ((LinkedList<Cell>) queue).add(newMaze[1][0]);
            path.put(newMaze[1][0],newMaze[i][j]);

        }
        if(newMaze[0][1].getOccupied()!=-1){
            visitedTrack[0][1] = true;
            ((LinkedList<Cell>) queue).add(newMaze[1][0]);
            path.put(newMaze[0][1],newMaze[i][j]);

        }
        while(!queue.isEmpty()){
            Cell temp = queue.poll();
            int x = i = temp.getX();
            int y = j = temp.getY();
            currentMoves++;

            if(x==dimensions-1 && y == dimensions-1){
                System.out.println("Path found BFS");
                System.out.println(currentMoves);

                long endTime = System.currentTimeMillis();
                System.out.println((endTime-startTime)/60 + " s");
                printPath(path);
                return path;
            }



            if(x-1>=0 && newMaze[x-1][y].getOccupied()!=-1 && !visitedTrack[x-1][y]){
                ((LinkedList<Cell>) queue).add(newMaze[x-1][y]);
                visitedTrack[x-1][y] = true;
                path.put(newMaze[x-1][y],newMaze[i][j]);
                //newMaze[x-1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y-1>=0 && newMaze[x][y-1].getOccupied()!=-1 && !visitedTrack[x][y-1]){
                ((LinkedList<Cell>) queue).add(newMaze[x][y-1]);
                visitedTrack[x][y-1] = true;
                path.put(newMaze[x][y-1],newMaze[i][j]);
                //newMaze[x][y-1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y+1<dimensions && newMaze[x][y+1].getOccupied()!=-1 && !visitedTrack[x][y+1]){
                ((LinkedList<Cell>) queue).add(newMaze[x][y+1]);
                visitedTrack[x][y+1] = true;
                path.put(newMaze[x][y+1],newMaze[i][j]);
                //newMaze[x][y+1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(x+1<dimensions && newMaze[x+1][y].getOccupied()!=-1 && !visitedTrack[x+1][y]){
                queue.add(newMaze[x+1][y]);
                visitedTrack[x+1][y] = true;
                path.put(newMaze[x+1][y],newMaze[i][j]);
                //newMaze[x+1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }

        }
       // dfs();
       return path;
    }

    public boolean dfs()
    {
        //Instant start = Instant.now();
        long start = System.currentTimeMillis();
        Cell[][] newMaze = copyingMaze();
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
            //System.out.println(stack.size());
            Cell temp = stack.pop();

            int tempX = temp.getX();
            int tempY = temp.getY();
            isVisited[tempX][tempY] = true;
            moves++;
            //System.out.println(tempX + " " + tempY);
            if(tempX == dimensions-1 && tempY == dimensions-1){
                System.out.println(moves + " Path found dfs ");

                long end = System.currentTimeMillis();
                System.out.println((end-start)/60 + " s");

                //Instant end = Instant.now();
                //System.out.println(Duration.between(start,end));
                printPath(path);
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
       // printMaze(newMaze);
        System.out.println(moves + " not found DFS");
        return false;
    }

    public boolean EuclidianAstar(){
        long start = System.currentTimeMillis();
        Cell[][] copiedMaze = copyingMaze();
        boolean[][ ] visitedTrack = new boolean[dimensions][dimensions];
        Queue<Cell> queue = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                 if(o1.getHeuristic() < o2.getHeuristic()) return -1;
                if(o1.getHeuristic() > o2.getHeuristic()) return 1;
                else return 0;

            }
        });

        Map<Cell, Cell> path = new HashMap<>();
        copiedMaze[0][0].setTotalDistance(0);



        queue.add(copiedMaze[0][0]);
        copiedMaze[0][0].setHeuristic(euclidianHeuristic(0,0));
        int moves =0;

        while(!queue.isEmpty()){
            Cell temp = queue.poll();
            int x = temp.getX();
            int y = temp.getY();
            long cost  = copiedMaze[x][y].getTotalDistance();
            if(x==dimensions-1 && y == dimensions-1){
                long end = System.currentTimeMillis();

                System.out.println("A* found path");
                System.out.println(moves);
                System.out.println((end-start)/60 + " s");
                printPath(path);
                return true;
            }

            if(x-1>0 && copiedMaze[x-1][y].getOccupied()!=-1 && !visitedTrack[x-1][y]){
                copiedMaze[x-1][y].setTotalDistance(cost+1);
                copiedMaze[x-1][y].setHeuristic(euclidianHeuristic(x-1,y) + cost);
                path.put(copiedMaze[x-1][y],copiedMaze[x][y]);
                 queue.add(copiedMaze[x-1][y]);
                visitedTrack[x-1][y] = true;
                //newMaze[x-1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y-1>0 && copiedMaze[x][y-1].getOccupied()!=-1 && !visitedTrack[x][y-1]){
                copiedMaze[x][y-1].setTotalDistance(cost+1);
                copiedMaze[x][y-1].setHeuristic(euclidianHeuristic(x,y-1) + cost);
                path.put(copiedMaze[x][y-1],copiedMaze[x][y]);

               queue.add(copiedMaze[x][y-1]);
                visitedTrack[x][y-1] = true;
                //newMaze[x][y-1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y+1<dimensions && copiedMaze[x][y+1].getOccupied()!=-1 && !visitedTrack[x][y+1]){
                copiedMaze[x][y+1].setTotalDistance(cost+1);
                copiedMaze[x][y+1].setHeuristic(euclidianHeuristic(x,y+1) + cost);
                path.put(copiedMaze[x][y+1],copiedMaze[x][y]);

                 queue.add(copiedMaze[x][y+1]);
                visitedTrack[x][y+1] = true;
                //newMaze[x][y+1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(x+1<dimensions && copiedMaze[x+1][y].getOccupied()!=-1 && !visitedTrack[x+1][y]){
                copiedMaze[x+1][y].setTotalDistance(cost+1);
                copiedMaze[x+1][y].setHeuristic(euclidianHeuristic(x+1,y) + cost);
                path.put(copiedMaze[x+1][y],copiedMaze[x][y]);
                queue.add(copiedMaze[x+1][y]);
                visitedTrack[x+1][y] = true;
                //newMaze[x+1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            moves++;
        }

        System.out.println("A* path not found");
        return false;
    }

    public boolean ManhattanAstar(){
        long start = System.currentTimeMillis();
        Cell[][] copiedMaze = copyingMaze();
        boolean[][ ] visitedTrack = new boolean[dimensions][dimensions];
        Queue<Cell> queue = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                if(o1.getHeuristic() < o2.getHeuristic()) return -1;
                if(o1.getHeuristic() > o2.getHeuristic()) return 1;
                else return 0;

            }
        });

        Map<Cell, Cell> path = new HashMap<>();
        copiedMaze[0][0].setTotalDistance(0);



        queue.add(copiedMaze[0][0]);
        copiedMaze[0][0].setHeuristic(manhattanAstar(0,0));
        int moves =0;

        while(!queue.isEmpty()){
            Cell temp = queue.poll();
            int x = temp.getX();
            int y = temp.getY();
            long cost  = copiedMaze[x][y].getTotalDistance();
            if(x==dimensions-1 && y == dimensions-1){
                long end = System.currentTimeMillis();

                System.out.println("A* found path");
                System.out.println(moves);
                System.out.println((end-start)/60 + " s");
                printPath(path);
                return true;
            }

            if(x-1>0 && copiedMaze[x-1][y].getOccupied()!=-1 && !visitedTrack[x-1][y]){
                copiedMaze[x-1][y].setTotalDistance(cost+1);
                copiedMaze[x-1][y].setHeuristic(manhattanAstar(x-1,y) + cost);
                path.put(copiedMaze[x-1][y],copiedMaze[x][y]);
                queue.add(copiedMaze[x-1][y]);
                visitedTrack[x-1][y] = true;
                //newMaze[x-1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y-1>0 && copiedMaze[x][y-1].getOccupied()!=-1 && !visitedTrack[x][y-1]){
                copiedMaze[x][y-1].setTotalDistance(cost+1);
                copiedMaze[x][y-1].setHeuristic(manhattanAstar(x,y-1) + cost);
                path.put(copiedMaze[x][y-1],copiedMaze[x][y]);

                queue.add(copiedMaze[x][y-1]);
                visitedTrack[x][y-1] = true;
                //newMaze[x][y-1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y+1<dimensions && copiedMaze[x][y+1].getOccupied()!=-1 && !visitedTrack[x][y+1]){
                copiedMaze[x][y+1].setTotalDistance(cost+1);
                copiedMaze[x][y+1].setHeuristic(manhattanAstar(x,y+1) + cost);
                path.put(copiedMaze[x][y+1],copiedMaze[x][y]);

                queue.add(copiedMaze[x][y+1]);
                visitedTrack[x][y+1] = true;
                //newMaze[x][y+1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(x+1<dimensions && copiedMaze[x+1][y].getOccupied()!=-1 && !visitedTrack[x+1][y]){
                copiedMaze[x+1][y].setTotalDistance(cost+1);
                copiedMaze[x+1][y].setHeuristic(manhattanAstar(x+1,y) + cost);
                path.put(copiedMaze[x+1][y],copiedMaze[x][y]);
                queue.add(copiedMaze[x+1][y]);
                visitedTrack[x+1][y] = true;
                //newMaze[x+1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            moves++;
        }

        System.out.println("A* path not found");
        return false;
    }

    private double euclidianHeuristic(int x,int y){
        return Math.sqrt(Math.pow(dimensions-1-x , 2) + Math.pow(dimensions-1-y,2));

    }
    private double manhattanAstar(int x, int y){
        return Math.abs((dimensions-1-x)+(dimensions-1-y));
    }
    private Cell[][] copyingMaze(){
        Cell[][] copyMaze = new Cell[dimensions][dimensions];

        for(int i=0;i<dimensions;i++){
            for(int j=0;j<dimensions;j++){
                copyMaze[i][j] = map[i][j];
            }
        }
        return copyMaze;
    }


    private void printPath(Map<Cell, Cell> path){
            Cell key = map[dimensions-1][dimensions-1];
           // System.out.println("key" + key.getX() + " " + key.getY());

            ArrayList<Cell> arrayList = new ArrayList<>();
            arrayList.add(key);
            while(!path.isEmpty()){
                if(path.containsKey(key)){
                    Cell temp = path.get(key);
                    key = temp;
                    arrayList.add(key);

                }
                else{
                    path.clear();
                }
            }
            //System.out.println(arrayList.size());
            for(int i=arrayList.size()-1;i>0;i--){
                System.out.print("("+ arrayList.get(i).getX() + " " + arrayList.get(i).getY() + ") ");

            }
    }

}
