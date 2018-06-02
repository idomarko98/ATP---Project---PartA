package Server;

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
        Configurations.setProp(4);
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
        static Properties prop = new Properties();
        static InputStream input = null;
        static Map<String, ASearchingAlgorithm> algorithmMap;

        public static void loadFile() {
            try {
                input = new FileInputStream("src/config.properties");
                // load a properties file
                prop.load(input);
                algorithmMap = new HashMap<>();
                algorithmMap.put("BreadthFirstSearch", new BreadthFirstSearch());
                algorithmMap.put("BestFirstSearch", new BestFirstSearch());
                algorithmMap.put("DepthFirstSearch", new DepthFirstSearch());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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

        public static ASearchingAlgorithm getSearchingAlgorithm(){
            try {
                String searchingAlgorithmName = prop.getProperty("searchingAlgorithm");
                if(algorithmMap.containsKey(searchingAlgorithmName))
                    return (ASearchingAlgorithm)algorithmMap.get(searchingAlgorithmName).cloneMe();
                return new BreadthFirstSearch(); // default...
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return new BreadthFirstSearch(); // default...
        }

        public static void setProp(int size) {
            OutputStream output = null;
            try {
                output = new FileOutputStream("src/config.properties");
                Properties prop = new Properties();
                prop.setProperty("threadPoolSize", Integer.toString(size));
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
