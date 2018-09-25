public class MazeMain {
    public static void main(String[] args){
        MazeClass mazeClass = new MazeClass(8000,0.3);
        Cell[][] maze = mazeClass.map;
        Cell[][] maze1 = maze;
        if(maze==null){
            System.out.println("mazze null;");
        }
       // mazeClass.printMaze();
        mazeClass.bfs();
        System.out.println("_+++++++++++++++++++");
       mazeClass.dfs();

       // mazeClass.dfs();

        System.out.println("_+++++++++++++++++++");
        mazeClass.EuclidianAstar();

    }
}
