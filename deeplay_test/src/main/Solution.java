package main;

import java.io.*;
import java.util.*;

public class Solution {
    private static final int size = 4;
    private static final int cellCount = 4;
    private static final String costFileName = "Cost.txt";
    private static final Map<String, Integer> cost = getCost();

    /*
    I use format
    Race [<Cell Type> <Distance(Int)>]
     */
    private static Map<String, Integer> getCost() {
        Map<String, Integer> res = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(costFileName))) {
            String line = br.readLine();
            while(line != null) {
                int left = line.indexOf(' ');
                //Check format of file
                String race = line.substring(0, left);
                for(int i = 0; i < cellCount; i++) {
                    final int ind = line.indexOf(' ', left + 1);
                    int right = line.indexOf(' ', ind + 1);
                    if(right == -1) {
                        right = line.length();
                    }
                    res.put(race + '_' + line.substring(left + 1, ind),
                            Integer.parseInt(line.substring(ind + 1, right)));
                    left = right;
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Distance file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static int getDistance(String race, char c) {
        return cost.getOrDefault(race + '_' + c, 0);
    }

    public static int getResult(String w, String race) {
        if (w.length() != size * size) {
            return Integer.MAX_VALUE;
        }
        List<List<Integer>> world = new ArrayList<>(size);
        List<List<Integer>> minC = new ArrayList<>(size + 1);
        minC.add(new ArrayList<>(Collections.nCopies(size + 1, Integer.MAX_VALUE)));
        for(int i = 0; i < size; i++) {
            minC.add(new ArrayList<>(Collections.nCopies(size + 1, Integer.MAX_VALUE)));
            world.add(new ArrayList<>());
            for(int j = 0; j < size; j++) {
                world.get(i).add(getDistance(race, w.charAt(i * size + j)));
            }
        }
        minC.get(1).set(1, 0);
        /*
            I use false layers to make it easier.
            For a 4x4 and smaller field, the minimum path for cell [i][j] can be found by taking min([i-1][j], [i][j - 1])
            This may not work for larger field, it depends on cost
         */
        for(int i = 1; i < size + 1; i++)  {
            for(int j = 1; j < size + 1; j++) {
                if(i * j != 1) {
                    minC.get(i).set(j, world.get(i - 1).get(j - 1) + Math.min(minC.get(i - 1).get(j), minC.get(i).get(j - 1)));
                }
            }
        }
        return minC.get(size).get(size);
    }
}
