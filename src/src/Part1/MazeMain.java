package Part1;

import java.util.Map;

public class MazeMain {
    public static void main(String[] args){
        MazeClass mazeClass = new MazeClass(500 ,0.3);
        Cell[][] maze = mazeClass.map;
        //Cell[][] maze1 = maze;
//               if(maze==null){
//            System.out.println("mazze null;");
//        }
//       // mazeClass.printMaze(maze);
        System.out.println("+++++++++++++++BFS++++++++++++++++++++++");
        Map<Cell, Cell> x = mazeClass.bfs();
        System.out.println("+++++++++++++++ DFS ++++++++++++++++++++++");
        mazeClass.dfs();
        System.out.println("+++++++++++++++ A* Euclidean ++++++++++++++++++++++");
        mazeClass.EuclidianAstar();
        System.out.println("+++++++++++++++ A* Manhattan ++++++++++++++++++++++");
        mazeClass.ManhattanAstar();


//    maze[0][0].setOccupied(Integer.parseInt("*"));
   // System.out.println(maze[0][0].getOccupied());
    }
}
