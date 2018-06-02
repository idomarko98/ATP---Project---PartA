package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private ASearchingAlgorithm algorithm;
    private String tempDirectoryPath;

    public ServerStrategySolveSearchProblem() {
        this.algorithm = Server.Configurations.getSearchingAlgorithm();
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
                Solution sol = checkAndSave(maze);
                toClient.writeObject(sol);
                toClient.flush();
                /*if(sol == null) {
                    ISearchable iSearchable = new SearchableMaze(maze);
                    //sol = algorithm.solve(iSearchable);
                    //saveSolution(sol, maze);
                }*/
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private Solution checkAndSave(Maze maze) {
        int colSize = maze.getColumnSize();
        int rowSize = maze.getRowSize();
        int startCol = maze.getStartPosition().getColumnIndex();
        int startRow = maze.getStartPosition().getRowIndex();
        int goalCol = maze.getGoalPosition().getColumnIndex();
        int goalRow = maze.getGoalPosition().getRowIndex();
        int index = 0;
        String path = tempDirectoryPath + "maze-" + colSize + "," + rowSize + "," + startCol + "," + startRow + "," + goalCol + "," + goalRow + "-" +index;
        File file = new File(path);
        Solution sol = null;
        while(file.exists())
        {
            try {
                MyDecompressorInputStream decompressor = new MyDecompressorInputStream(new FileInputStream(path));
                byte[] bytes = maze.toByteArray();
                byte[] decompressedBytes = new byte[bytes.length];
                decompressor.read(decompressedBytes);
                if(Arrays.equals(bytes, decompressedBytes))
                {
                    sol = readSolutionFromFile(tempDirectoryPath + "solution-" + colSize + "," + rowSize + "," + startCol + "," + startRow + "," + goalCol + "," + goalRow + "-" + index);
                    break;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            path = path.substring(0,path.length() - (index + "").length());
            index++;
            path += index;
            file = new File(path);
        }
        if(sol == null){
            ISearchable iSearchable = new SearchableMaze(maze);
            ASearchingAlgorithm usedAlgorithm = (ASearchingAlgorithm)algorithm.cloneMe();
            sol = usedAlgorithm.solve(iSearchable);
            saveSolution(sol, maze, index);
        }
        return sol;
    }

    private Solution readSolutionFromFile(String s) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(s));
            Solution sol = (Solution)input.readObject();
            return sol;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void saveSolution(Solution solution, Maze maze, int index) {
        int colSize = maze.getColumnSize();
        int rowSize = maze.getRowSize();
        int startCol = maze.getStartPosition().getColumnIndex();
        int startRow = maze.getStartPosition().getRowIndex();
        int goalCol = maze.getGoalPosition().getColumnIndex();
        int goalRow = maze.getGoalPosition().getRowIndex();
        String fileName = "" + colSize + "," + rowSize + "," + startCol + "," + startRow + "," + goalCol + "," + goalRow;
        File dir = new File(tempDirectoryPath);
        File[] directoryListing = dir.listFiles();
        //int index = 0;
        /*
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
        */
        fileName += "-" + index;
        File fileMaze = new File(tempDirectoryPath + "maze-" + fileName);
        File fileSolution = new File(tempDirectoryPath + "solution-" + fileName);

        //if(fileMaze.canWrite()) {
        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileMaze));
            out.write(maze.toByteArray());
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //}
        //if(fileSolution.canWrite()) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileSolution));
            out.writeObject(solution);
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //}
    }

}
