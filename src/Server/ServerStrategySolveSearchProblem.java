package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    ISearchingAlgorithm algorithm;
    String tempDirectoryPath;

    public ServerStrategySolveSearchProblem() {
        this.algorithm = new BreadthFirstSearch();
        this.tempDirectoryPath = System.getProperty("java.io.tmpdir");
    }

    public ServerStrategySolveSearchProblem(ISearchingAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.tempDirectoryPath = System.getProperty("java.io.tmpdir");
    }

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            if(inFromClient != null && outToClient != null){
                ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
                ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
                toClient.flush();

                Maze maze = (Maze)fromClient.readObject();
                if(!fileExists(maze)) {
                    ISearchable iSearchable = new SearchableMaze(maze);
                    Solution solution = algorithm.solve(iSearchable);
                    saveSolution(solution, maze);
                }
                else{
                    // Do stuff
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private boolean fileExists(Maze maze) {
    }

    private void saveSolution(Solution solution, Maze maze) {
        int colSize = maze.getColumnSize();
        int rowSize = maze.getRowSize();
        int startCol = maze.getStartPosition().getColumnIndex();
        int startRow = maze.getStartPosition().getRowIndex();
        int goalCol = maze.getGoalPosition().getColumnIndex();
        int goalRow = maze.getGoalPosition().getRowIndex();
        String fileName = "" + colSize + "," + rowSize + "," + startCol + "," + startRow + "," + goalCol + "," + goalRow;
        File dir = new File(tempDirectoryPath);
        File[] directoryListing = dir.listFiles();
        int index = 0;
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String[] parts = child.getName().split("-");
                String part0 = parts[0]; // type of file
                String part1 = parts[1]; // the properties
                String part2 = parts[2]; // the index
                if (part1.equals(fileName)) {
                    int fileIndex = Integer.parseInt(part2);
                    if (index <= fileIndex)
                        index = fileIndex + 1;
                }
            }
        }
        fileName += "-" + index;
        File fileMaze = new File(tempDirectoryPath + "/maze-" + fileName);
        File fileSolution = new File(tempDirectoryPath + "/solution-" + fileName);

        if(fileMaze.canWrite()) {
            try {
                OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileMaze));
                out.write(maze.toByteArray());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if(fileSolution.canWrite()) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileSolution));
                out.writeObject(solution);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
