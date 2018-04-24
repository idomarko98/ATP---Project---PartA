package com.company;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello World!");
        MyMazeGenerator mmg = new MyMazeGenerator();
        Maze m = mmg.generate(1000,1000);
        m.print();
    }
}
