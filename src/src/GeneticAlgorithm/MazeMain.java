package GeneticAlgorithm;

import java.util.Map;

public class MazeMain {
    public static void main(String[] args){
     GA GAClass = new GA(100,0.2);
     Cell[][] result = new Cell[100][100];
     result = GAClass.start("Euclidean");
     SearchAlgo searchAlgo = new SearchAlgo(100);
     Map<String,String> bfs = searchAlgo.bfs(result);
        Map<String,String> dfs = searchAlgo.dfs(result);
        Map<String,String> astar = searchAlgo.EuclidianAstar(result);
        Map<String,String> manhattan = searchAlgo.ManhattanAstar(result);

        System.out.println("MANHATTAN "+manhattan.get("MAXFRINGE") + " "+ manhattan.get("PATHLEN") +" " +manhattan.get("MOVES"));
        System.out.println("BFS "+bfs.get("MAXFRINGE") + " "+ bfs.get("PATHLEN") +" " +bfs.get("MOVES"));
        System.out.println("DFS "+dfs.get("MAXFRINGE")+ " "+ dfs.get("PATHLEN") +" " +dfs.get("MOVES"));
        System.out.println("astar "+astar.get("MAXFRINGE")+ " "+ astar.get("PATHLEN") +" " +astar.get("MOVES"));

    }
}
