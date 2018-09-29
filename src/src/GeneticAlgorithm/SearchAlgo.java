package GeneticAlgorithm;

import java.util.*;

public class SearchAlgo {
    private int dimensions;

    public SearchAlgo(int dim){
        this.dimensions = dim;
    }


    public Map<String, String> EuclidianAstar(Cell[][] copiedMaze){

        int maxFringe = 0;
        Map<String, String> fitnessVal = new HashMap<>();
        long start = System.currentTimeMillis();
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

            if(maxFringe <= queue.size()){
                maxFringe = queue.size();
            }
            if(x==dimensions-1 && y == dimensions-1){
                long end = System.currentTimeMillis();

                fitnessVal.put("PATHLEN",String.valueOf(printPath(path,copiedMaze)));
                fitnessVal.put("MAXFRINGE",String.valueOf(maxFringe));
                fitnessVal.put("MOVES",String.valueOf(path.size()));

//                System.out.println("A* found path");
//                System.out.println(moves);
//                System.out.println((end-start)/60 + " s");
              //  printPath(path);
                return fitnessVal;
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
        return fitnessVal;
    }

    public Map<String, String> ManhattanAstar(Cell[][] copiedMaze){
        HashMap<String, String> fitnessVal = new HashMap<>();
        int maxFringe = 0;
        long start = System.currentTimeMillis();
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

            if(maxFringe <= queue.size()){
                maxFringe = queue.size();
            }

            if(x==dimensions-1 && y == dimensions-1){
                long end = System.currentTimeMillis();

                int NumberOfNodes = printPath(path,copiedMaze);

//                System.out.println("A* found path");
//                System.out.println(moves);
//                System.out.println((end-start)/60 + " s");
//                //printPath(path);

                fitnessVal.put("PATHLEN",String.valueOf(NumberOfNodes));
                fitnessVal.put("MAXFRINGE",String.valueOf(maxFringe));
                fitnessVal.put("MOVES",String.valueOf(moves));
                return fitnessVal;
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

       // System.out.println("A* path not found");
        return fitnessVal;
    }

    private double euclidianHeuristic(int x,int y){
        return Math.sqrt(Math.pow(dimensions-1-x , 2) + Math.pow(dimensions-1-y,2));

    }
    private double manhattanAstar(int x, int y){
        return Math.abs((dimensions-1-x)+(dimensions-1-y));
    }

    public Map<String, String> bfs(Cell[][] newMaze){

        int maxFringe = 0;
        long startTime =  System.currentTimeMillis();

        Map<Cell, Cell> path = new HashMap<>();

        Map<String,String> fitnessVal = new HashMap<>();

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

            if(maxFringe<= queue.size()){
                maxFringe = queue.size();
            }

            if(x==dimensions-1 && y == dimensions-1){
                fitnessVal.put("PATHLEN",String.valueOf(printPath(path,newMaze)));
                fitnessVal.put("MAXFRINGE",String.valueOf(maxFringe));
                fitnessVal.put("MOVES",String.valueOf(currentMoves));
                return fitnessVal;

               /// long endTime = System.currentTimeMillis();
                //System.out.println((endTime-startTime)/60 + " s");
             //   printPath(path);
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
        return fitnessVal;
    }

    public Map<String,String> dfs( Cell[][] newMaze)
    {
        //Instant start = Instant.now();
        long start = System.currentTimeMillis();
        Map<String,String> fitnessVal = new HashMap<>();
        boolean[][] isVisited = new boolean[dimensions][dimensions];
        Stack<Cell> stack = new Stack<>();
        stack.add(newMaze[0][0]);
        isVisited[0][0] = true;
       //steps
        int moves =0;
        //maxFringe
        int maxFinge =0;
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
            if(maxFinge<= stack.size()){
                maxFinge = stack.size();
            }
            Cell temp = stack.pop();

            int tempX = temp.getX();
            int tempY = temp.getY();
            isVisited[tempX][tempY] = true;
            moves++;
            //System.out.println(tempX + " " + tempY);
            if(tempX == dimensions-1 && tempY == dimensions-1){
                //System.out.println(moves + " Path found dfs ");
                int NumberOfNodes = printPath(path,newMaze);
                long end = System.currentTimeMillis();
                fitnessVal.put("PATHLEN",String.valueOf(NumberOfNodes));
                fitnessVal.put("MAXFRINGE",String.valueOf(maxFinge));
                fitnessVal.put("MOVES",String.valueOf(moves));
                return fitnessVal;
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
        //System.out.println(moves + " not found DFS");
        return fitnessVal;
    }


    private int printPath(Map<Cell, Cell> path, Cell[][] map){
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

        return arrayList.size();
    }
}
