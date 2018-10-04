package GeneticAlgorithm;


public class MazeMain {
    public static void main(String[] args){
     GA GAClass = new GA(50  ,0.3);
     Cell[][] result = new Cell[50][50];
     //result = GAClass.start("DFS");
     SearchAlgo searchAlgo = new SearchAlgo(50);

   result=  GAClass.start("DFS");

   //boolean x = GAClass.isSolvable(result);
    //System.out.println( "in main"+ x );
     //GAClass.printMazr(result);
     //Map<String,String> bfs = searchAlgo.bfs(result);
//        Map<String,String> dfs = searchAlgo.dfs(result);
//        Map<String,String> astar = searchAlgo.EuclidianAstar(result);
//        Map<String,String> manhattan = searchAlgo.ManhattanAstar(result);

//        System.out.println("MANHATTAN "+manhattan.get("MAXFRINGE") + " "+ manhattan.get("PATHLEN") +" " +manhattan.get("MOVES"));
       //System.out.println("BFS "+bfs.get("MAXFRINGE") + " "+ bfs.get("PATHLEN") +" " +bfs.get("MOVES"));
////        System.out.println("DFS "+dfs.get("MAXFRINGE")+ " "+ dfs.get("PATHLEN") +" " +dfs.get("MOVES"));
////        System.out.println("astar "+astar.get("MAXFRINGE")+ " "+ astar.get("PATHLEN") +" " +astar.get("MOVES"));

    }
}
