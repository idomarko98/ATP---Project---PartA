package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            if(inFromClient != null && outToClient != null) {
                ObjectOutputStream oos = new ObjectOutputStream(outToClient);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteArrayOutputStream/*oos*/);
                ObjectInputStream input = new ObjectInputStream(inFromClient);
                int[] array = (int[])input.readObject();
                int rows = array[0];
                int columns = array[1];
                AMazeGenerator generator = Server.Configurations.getMazeGeneratingAlgorithm();
                Maze maze = generator.generate(rows, columns);
                byte[] mazeBytes = maze.toByteArray();
                compressor.write(mazeBytes);
                //compressor.flush();
                oos.writeObject(byteArrayOutputStream.toByteArray());
                oos.flush();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
/*
    private int byteArrayToInt(byte[] bytes, int start){
        return ((0xFF & bytes[start]) << 24) | ((0xFF & bytes[start+1]) << 16) |
                ((0xFF & bytes[start+2]) << 8) | (0xFF & bytes[start+3]);
    }
    */
}
