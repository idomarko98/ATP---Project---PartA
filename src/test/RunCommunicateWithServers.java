package test;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategySolveSearchProblem;
import Server.ServerStrategyGenerateMaze;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RunCommunicateWithServers {
    public static void main(String[] args) {
        //Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new
                ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new
                ServerStrategySolveSearchProblem());
        //Server stringReverserServer = new Server(5402, 1000, new ServerStrategyStringReverser());
        //Starting servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        //stringReverserServer.start();
        //Communicating with servers
        CommunicateWithServer_MazeGenerating();
        Thread [] t = new Thread[10];
        for (int i = 0; i < 10; i++){
            t[i] = new Thread(()->CommunicateWithServer_SolveSearchProblem());
            t[i].start();
        }
        for (int i = 0; i<10; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        CommunicateWithServer_SolveSearchProblem();
        //CommunicateWithServer_StringReverser();
        //Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
        //stringReverserServer.stop();
    }
    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                /*ObjectInputStream fromServer=null;
                                if(inFromServer.available() != 0)
                                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);*/
                                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                toServer.flush();
                                int[] mazeDimensions = new int[]{50, 50};
                                toServer.writeObject(mazeDimensions); //send maze dimensions to server
                                toServer.flush();
                                byte[] compressedMaze = (byte[])fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                                InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                                byte[] decompressedMaze = new byte[100000/*(int)Math.ceil(mazeDimensions[0]*mazeDimensions[1] / 8) + 24*/ /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                                is.read(decompressedMaze); //Fill decompressedMaze with bytes
                                Maze maze = new Maze(decompressedMaze);
                                maze.print();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
        }
    }
    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new
                                        ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new
                                        ObjectInputStream(inFromServer);
                                toServer.flush();
                                MyMazeGenerator mg = new MyMazeGenerator();
                                Maze maze = mg.generate(50, 50);
                                /*int[][] hi = {{0,0,1,1,1},
                                              {0,0,0,1,1},
                                              {1,1,0,0,0}};
                                Maze maze = new Maze(new Position(0,0), new Position(2,4),hi);*/
                                maze.print();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                                //Print Maze Solution retrieved from the server
                                System.out.println(String.format("Solution steps: %s", mazeSolution));
                                ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                                for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                                    System.out.println(String.format("%s. %s", i,
                                            mazeSolutionSteps.get(i).toString()));
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void CommunicateWithServer_StringReverser() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                BufferedReader fromServer = new BufferedReader(new
                                        InputStreamReader(inFromServer));
                                PrintWriter toServer = new PrintWriter(outToServer);
                                String message = "Client Message";
                                String serverResponse;
                                toServer.write(message + "\n");
                                toServer.flush();
                                serverResponse = fromServer.readLine();
                                System.out.println(String.format("Server response: %s", serverResponse));
                                toServer.flush();
                                fromServer.close();
                                toServer.close();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
    }
}
