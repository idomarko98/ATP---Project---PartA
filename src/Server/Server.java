package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    public static ExecutorService threadPool;
    //static Properties prop = new Properties();
    //InputStream input = null;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        /*try {
            input = new FileInputStream("src/config.properties");
            // load a properties file
            prop.load(input);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }*/
        Configurations.loadFile();
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        this.threadPool = Executors.newFixedThreadPool(Configurations.getThreadPoolSize());
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    threadPool.execute(() -> handleClient(clientSocket));
                } catch (SocketTimeoutException e) {
                }
            }
            server.close();
            threadPool.shutdown();
        } catch (IOException e) {
        }
        try {
            server.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleClient(Socket clientSocket) {
        try {
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException e) {
        }
        try {
            clientSocket.getInputStream().close();
        } catch (IOException e) {
        }
        try {
            clientSocket.getOutputStream().close();
        } catch (IOException e) {
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    public void stop() {
        stop = true;
    }


    public static class Configurations {
        static private Properties prop = new Properties();
        static private InputStream input = null;
        static private Map<String, ASearchingAlgorithm> mazeSearchingAlgorithmMap;
        static private Map<String, AMazeGenerator> mazeGeneratingAlgorithmMap;

        public static void loadFile() {
            try {
                input = new FileInputStream("src/config.properties");
                // load a properties file
                prop.load(input);
                initializeSearchingAlgorithmMap();
                initializeGeneratingAlgorithmMap();


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private static void initializeSearchingAlgorithmMap(){
            mazeSearchingAlgorithmMap = new HashMap<>();
            mazeSearchingAlgorithmMap.put("BreadthFirstSearch", new BreadthFirstSearch());
            mazeSearchingAlgorithmMap.put("BestFirstSearch", new BestFirstSearch());
            mazeSearchingAlgorithmMap.put("DepthFirstSearch", new DepthFirstSearch());
        }

        private static void initializeGeneratingAlgorithmMap(){
            mazeGeneratingAlgorithmMap = new HashMap<>();
            mazeGeneratingAlgorithmMap.put("MyMazeGenerator", new MyMazeGenerator());
            mazeGeneratingAlgorithmMap.put("SimpleMazeGenerator", new SimpleMazeGenerator());
        }

        public static int getThreadPoolSize() {
            try {
                if (Integer.valueOf(prop.getProperty("threadPoolSize")) > 0)
                    return Integer.valueOf(prop.getProperty("threadPoolSize"));
                return 1;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return 1;
        }

        public static ASearchingAlgorithm getMazeSearchingAlgorithm(){
            try {
                String mazeSearchingAlgorithmName = prop.getProperty("mazeSearchingAlgorithm");
                if(mazeSearchingAlgorithmMap.containsKey(mazeSearchingAlgorithmName))
                    return (ASearchingAlgorithm)mazeSearchingAlgorithmMap.get(mazeSearchingAlgorithmName).cloneMe();
                return new BreadthFirstSearch(); // default...
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return new BreadthFirstSearch(); // default...
        }

        public static AMazeGenerator getMazeGeneratingAlgorithm(){
            try {
                String mazeGeneratingAlgorithmName = prop.getProperty("mazeGeneratingAlgorithm");
                if(mazeGeneratingAlgorithmMap.containsKey(mazeGeneratingAlgorithmName))
                    return (AMazeGenerator) mazeGeneratingAlgorithmMap.get(mazeGeneratingAlgorithmName).cloneMe();
                return new MyMazeGenerator(); // default...
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return new MyMazeGenerator(); // default...
        }

        public static void setProp(int size, String mazeGeneratingAlgorithm, String mazeSearchingAlgorithm) {
            OutputStream output = null;
            try {
                output = new FileOutputStream("src/config.properties");
                Properties prop = new Properties();
                prop.setProperty("threadPoolSize", Integer.toString(size));
                prop.setProperty("mazeGeneratingAlgorithm", mazeGeneratingAlgorithm);
                prop.setProperty("mazeSearchingAlgorithm", mazeSearchingAlgorithm);
                prop.store(output, null);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
