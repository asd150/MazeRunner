package GeneticAlgorithm;

import java.util.Map;

public class MazeMain {
    public static void main(String[] args){
     GA GAClass = new GA(10,0.2);
     GAClass.start("Manhattan");

    }
}
