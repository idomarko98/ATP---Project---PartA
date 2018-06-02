package com.company;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String file_name = "C:\\Users\\User\\Desktop\\tempForIdo\\freak1.txt";
        File f = new File(file_name);
        if(f.exists())
            System.out.println("The freak has been found!");
        else
            System.out.println("Still Searching...");
        /*
        String testFreakiStuff = "" + 123 +343 +32342 +9853;
        System.out.println(testFreakiStuff);
        byte a = (byte) 0x80;
        System.out.println(a);
        ByteBuffer b = ByteBuffer.allocate(8);
        int x = 234234;
        int y = 17;
        b.putInt(x);
        b.putInt(y);
        byte[] bbb = b.array();
        byte[] firstNum = new byte[4];
        byte[] secondNum = new byte[4];
        for (int i = 0; i < 4; i++) {
            firstNum[i] = bbb[i];
            secondNum[i] = bbb[i + 4];
        }
        System.out.println("first = " + convertByteArrayToInt(firstNum));
        System.out.println("second = " + convertByteArrayToInt(secondNum));

        String str = "10101101101010101010101";
        List<String> strs = SeperateStringToLemphel(str);
        System.out.println(strs);
        System.out.println(getFirstAddress(strs));
        System.out.println(Math.log(8) / Math.log(2));
        String index = getFirstAddress(strs);
        while (!index.equals("1111")) {
            System.out.println("curr index = " + index);
            index = getNextBinary(index);
        }
        Map<String, String> map = new HashMap<>();
        map.put("hi", "bi");
        map.put("hi", "ido is a freak");
        map.put("shone", "ve meyohad");
        for (String s :
                map.keySet()) {
            System.out.println("key = " + s + ", value = " + map.get(s));
        }
        */
        /*
        String input = "10101101101010101010101";
        List<String> sepreatedBytes = SeperateStringToLemphel(input);
        Map<String, String> lemphLocation = new HashMap<>();
        Map<String, String> lemphCode = new HashMap<>();
        String stringIndex = getFirstAddress(sepreatedBytes);
        for (String s :
                sepreatedBytes) {
            if (!lemphLocation.containsKey(s)) {
                stringIndex = getNextBinary(stringIndex);
                lemphLocation.put(s, stringIndex);
                String enter = "";
                if(enter.length() >= 2)
                    enter += lemphLocation.get(s.substring(0, s.length() - 2));
                else
                    enter += s;
                if (enter == "") {
                    enter += getFirstAddress(sepreatedBytes);
                }
                enter += s.charAt(s.length() - 1);
                lemphCode.put(s, enter);
            }
        }

        for (String s :
                lemphCode.keySet()) {
            System.out.println("key = " + s + ", value = " + lemphCode.get(s));
        }
        */
        //String input = "1010110110101010101010111000101001011101101010100101010101011110001";
        /*
        String input = "";
        for(int i = 0; i < 1000000; i++){
            if(Math.random() < 0.5)
                input += "0";
            else
                input += "1";
        }
        List<String> separatedBytes = SeperateStringToLemphel(input);
        Map<String,String> lemphLocation = new HashMap<>();
        Map<String,String> lemphCode = new HashMap<>();
        String stringIndex = getFirstAddress(separatedBytes);
        String firstIndex = getFirstAddress(separatedBytes);
        for (String s:
                separatedBytes ) {
            if(!lemphLocation.containsKey(s)) {
                stringIndex = getNextBinary(stringIndex);
                lemphLocation.put(s, stringIndex);
                String enter = "";
                if(s.length() >= 2)
                    enter += lemphLocation.get(s.substring(0,s.length()-1));
                else
                    enter += firstIndex;
                enter += s.charAt(s.length()-1);
                lemphCode.put(s, enter);
            }
        }
        */
        /*
        for (String s:
                lemphLocation.keySet()) {
            System.out.println("Key: "+ s + ", location: " + lemphLocation.get(s) + ", code: " + lemphCode.get(s));
        }
        */
        /*
        String out = "";
        for (String s: separatedBytes
             ) {
            out += lemphCode.get(s);
        }
        System.out.println("length = " + out.length());
        */
        //System.out.println("Hello World!");
        //MyMazeGenerator mmg = new MyMazeGenerator();
        //Maze m = mmg.generate(10,10);
        //SearchableMaze searchableMaze = new SearchableMaze(m);
        //m.print();
        /*
        IMazeGenerator mg1 = new MyMazeGenerator();
        IMazeGenerator mg2 = new SimpleMazeGenerator();
        Maze maze;
        SearchableMaze searchableMaze;
        System.out.println("Started checking");
        for(int i = 0; i < 1000; i++) {
            maze = mg2.generate(1000, 1000);
            searchableMaze = new SearchableMaze(maze);
            if(!compareOutputs(searchableMaze, new BreadthFirstSearch(), new BestFirstSearch())){
                System.out.println("Not Good!");
                maze.print();
                solveProblem(searchableMaze, new BreadthFirstSearch());
                solveProblem(searchableMaze, new BestFirstSearch());
                break;
            }
            if(i == 999)
                System.out.println("Awsome!");

        }
        */
        //maze.print();

        //solveProblem(searchableMaze, new BreadthFirstSearch());
        //solveProblem(searchableMaze, new DepthFirstSearch());
        //solveProblem(searchableMaze, new BestFirstSearch());
    }

    private static String getFirstAddress(List<String> sepreatedBytes) {
        int size = sepreatedBytes.size();
        String last = sepreatedBytes.remove(size - 1);
        String result = "";
        double temp = Math.log(size - 1) / Math.log(2);
        if((Math.floor(temp) == temp && sepreatedBytes.contains(last))){
            size = (int)Math.floor(temp);
        }
        else
            size = (int)Math.floor(temp) + 1;
        sepreatedBytes.add(last);
        for(int i = 0; i < size; i++)
            result += "0";
        return result;
    }

    private static String getNextBinary(String curr){
        char[] chars = curr.toCharArray();
        for(int i = chars.length - 1; i >= 0; i--){
            if(chars[i] == '0'){
                chars[i] = '1';
                break;
            }
            else
                chars[i] = '0';
        }
        return new String(chars);
    }

    private static List<String> SeperateStringToLemphel(String input) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            boolean inserted = false;
            int index = i;
            while (!inserted) {
                boolean found = false;
                if (index + 1 < input.length() && stringList.contains(input.substring(i, index + 1)))
                    found = true;
                if (!found) {
                    stringList.add(input.substring(i, index + 1));
                    inserted = true;
                }
                index++;
            }
            i = index - 1;
        }
        return stringList;
    }

    private static int convertByteArrayToInt(byte[] bytes){
        if(bytes.length == 4){
            int xt = ((0xFF & bytes[0]) << 24) | ((0xFF & bytes[1]) << 16) |
                    ((0xFF & bytes[2]) << 8) | (0xFF & bytes[3]);
            return xt;
        }
        return -1; // could'nt convert
    }

    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        //Printing Solution Path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }
    }

    private static boolean compareOutputs(ISearchable domain, ISearchingAlgorithm searcher1, ISearchingAlgorithm searcher2){
        Solution solution1 = searcher1.solve(domain);
        Solution solution2 = searcher2.solve(domain);
        ArrayList<AState> solutionPath1 = solution1.getSolutionPath();
        ArrayList<AState> solutionPath2 = solution2.getSolutionPath();
        if( solutionPath1.size() == solutionPath2.size())
            return true;
        else
        {
            System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher1.getName(), searcher1.getNumberOfNodesEvaluated()));
            //Printing Solution Path
            System.out.println("Solution path:");
            for (int i = 0; i < solutionPath1.size(); i++) {
                System.out.println(String.format("%s. %s",i,solutionPath1.get(i)));
            }

            System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher2.getName(), searcher2.getNumberOfNodesEvaluated()));
            //Printing Solution Path
            System.out.println("Solution path:");
            for (int i = 0; i < solutionPath2.size(); i++) {
                System.out.println(String.format("%s. %s",i,solutionPath2.get(i)));
            }
            return false;
        }
    }
}
