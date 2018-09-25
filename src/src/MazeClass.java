import java.util.*;

public class MazeClass {
    //createMaze
    //same for all search algorithms

   public  Cell[][] map = null;
    int dimensions;

    public MazeClass(int dim,double prob){
        this.dimensions = dim;
        map = new Cell[dim][dim];
        generateMaze(dim,prob);
    }

    private Cell[][] generateMaze(int dim,double prob){
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
    public void bfs(){
     long startTime =  System.currentTimeMillis();
        Cell[][] newMaze = copyingMaze();
        boolean[][] visitedTrack = new  boolean[dimensions][dimensions];
        System.out.println(newMaze[0][0].getIsVisited() +  " ** ");
        int currentMoves =1;
        Map<String,String> path = new HashMap<>();
        Queue<Cell> queue = new LinkedList<>();
        visitedTrack[0][0] =true;
        ((LinkedList<Cell>) queue).add(newMaze[0][0]);
        int i=0;
        int j=0;
        while(!queue.isEmpty()){
            Cell temp = queue.poll();
        //System.out.println(dimensions + " dim ");
            int x = i = temp.getX();
            int y = j = temp.getY();
            currentMoves++;
        //System.out.println(y+1 + " y+1");
            //System.out.println(newMaze[x][y].getIsVisited() +  " should be false ");
           //visitedTrack[x][y] = true;

            if(x==dimensions-1 && y == dimensions-1){
                System.out.println("Path found");
               // System.out.println(newMaze[x][y].getTotalDistance());
                System.out.println(currentMoves);
                //printMaze(newMaze);
                long endTime = System.currentTimeMillis();
                System.out.println((endTime-startTime)/60 + " s");
                return;
            }
            if (currentMoves%10000 == 0){
                //System.out.println("Current Move: " + currentMoves);
            }

            if(currentMoves>150000000){
                System.out.println("Not found in time");
                //printMaze(newMaze);
                return;
            }



            if(x-1>0 && newMaze[x-1][y].getOccupied()!=-1 && !visitedTrack[x-1][y]){
                ((LinkedList<Cell>) queue).add(newMaze[x-1][y]);
                visitedTrack[x-1][y] = true;
                //newMaze[x-1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y-1>0 && newMaze[x][y-1].getOccupied()!=-1 && !visitedTrack[x][y-1]){
                ((LinkedList<Cell>) queue).add(newMaze[x][y-1]);
                visitedTrack[x][y-1] = true;
                //newMaze[x][y-1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y+1<dimensions && newMaze[x][y+1].getOccupied()!=-1 && !visitedTrack[x][y+1]){
                ((LinkedList<Cell>) queue).add(newMaze[x][y+1]);
                visitedTrack[x][y+1] = true;
                //newMaze[x][y+1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(x+1<dimensions && newMaze[x+1][y].getOccupied()!=-1 && !visitedTrack[x+1][y]){
                queue.add(newMaze[x+1][y]);
                visitedTrack[x+1][y] = true;
                //newMaze[x+1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }

        }
       // dfs();
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
                //printMaze(newMaze);
                long end = System.currentTimeMillis();
                System.out.println((end-start)/60 + " s");
                //Instant end = Instant.now();
                //System.out.println(Duration.between(start,end));
                return true;
            }

            if(tempX-1>0 && !isVisited[tempX-1][tempY] && newMaze[tempX-1][tempY].getOccupied()!=-1 ){
                stack.push(newMaze[tempX-1][tempY]);
            }
            if(tempY-1>0 && newMaze[tempX][tempY-1].getOccupied()!=-1 && !isVisited[tempX][tempY-1]){
                stack.push(newMaze[tempX][tempY-1]);
            }
            if(tempX+1<dimensions && newMaze[tempX+1][tempY].getOccupied()!=-1 && !isVisited[tempX+1][tempY] )
            {
                stack.push(newMaze[tempX+1][tempY]);

            }
            if(tempY+1<dimensions && newMaze[tempX][tempY+1].getOccupied()!=-1 && !isVisited[tempX][tempY+1]){
                stack.push(newMaze[tempX][tempY+1]);
            }


        }
       // printMaze(newMaze);
        System.out.println(moves + " not found DFS");
        return false;
    }

    public boolean EuclidianAstar(){
        long start = System.currentTimeMillis();
        Cell[][] copiedMaze = map.clone();
        boolean[][ ] visitedTrack = new boolean[dimensions][dimensions];
        Queue<Cell> queue = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                 if(o1.getHeuristic() < o2.getHeuristic()) return -1;
                if(o1.getHeuristic() > o2.getHeuristic()) return 1;
                else return 0;

            }
        });


        queue.add(copiedMaze[0][0]);
        copiedMaze[0][0].setHeuristic(euclidianHeuristic(0,0));
        int moves =0;

        while(!queue.isEmpty()){
            Cell temp = queue.poll();
            int x = temp.getX();
            int y = temp.getY();

            if(x==dimensions-1 && y == dimensions-1){
                long end = System.currentTimeMillis();

                System.out.println("A* found path");
                System.out.println(moves);
                System.out.println((end-start)/60);
                return true;
            }

            if(x-1>0 && copiedMaze[x-1][y].getOccupied()!=-1 && !visitedTrack[x-1][y]){
                copiedMaze[x-1][y].setHeuristic(euclidianHeuristic(x-1,y));
                 queue.add(copiedMaze[x-1][y]);
                visitedTrack[x-1][y] = true;
                //newMaze[x-1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y-1>0 && copiedMaze[x][y-1].getOccupied()!=-1 && !visitedTrack[x][y-1]){
                copiedMaze[x][y-1].setHeuristic(euclidianHeuristic(x,y-1));
               queue.add(copiedMaze[x][y-1]);
                visitedTrack[x][y-1] = true;
                //newMaze[x][y-1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(y+1<dimensions && copiedMaze[x][y+1].getOccupied()!=-1 && !visitedTrack[x][y+1]){
                copiedMaze[x][y+1].setHeuristic(euclidianHeuristic(x,y+1));
                 queue.add(copiedMaze[x][y+1]);
                visitedTrack[x][y+1] = true;
                //newMaze[x][y+1].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            if(x+1<dimensions && copiedMaze[x+1][y].getOccupied()!=-1 && !visitedTrack[x+1][y]){
                copiedMaze[x+1][y].setHeuristic(euclidianHeuristic(x+1,y));
                queue.add(copiedMaze[x+1][y]);
                visitedTrack[x+1][y] = true;
                //newMaze[x+1][y].setTotalDistance(newMaze[x][y].getTotalDistance());
            }
            moves++;
        }

        System.out.println("A* path not found");
        return false;
    }
    public double euclidianHeuristic(int x,int y){
        return Math.sqrt(Math.pow(dimensions-1-x , 2) + Math.pow(dimensions-1-y,2));
    }
    public Cell[][] copyingMaze(){
        Cell[][] copyMaze = new Cell[dimensions][dimensions];

        for(int i=0;i<dimensions;i++){
            for(int j=0;j<dimensions;j++){
                copyMaze[i][j] = map[i][j];
            }
        }
        return copyMaze;
    }
}
