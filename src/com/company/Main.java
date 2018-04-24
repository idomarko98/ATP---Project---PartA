package com.company;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.SimpleMazeGenerator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello World!");
        SimpleMazeGenerator smg = new SimpleMazeGenerator();
        Maze m = smg.generate(4,5);
        m.print();
    }
}
