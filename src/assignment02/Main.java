package assignment02;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.io.File;



public class Main {

    public static void main(String[] args) throws IOException {

        // front-end

        File file_frontend = new File("src/assignment02/front-end-input.txt");
        Scanner scanner_frontend = new Scanner(file_frontend);
        String[] nodes = scanner_frontend.nextLine().split(" ");
        String[] treasures = scanner_frontend.nextLine().split(" ");
        int allow_step = Integer.parseInt(scanner_frontend.nextLine());
        ArrayList<String[]> steps = new ArrayList<>();
        while (scanner_frontend.hasNextLine()){
            steps.add(scanner_frontend.nextLine().split(" "));
        }
        frontEnd fe = new frontEnd(allow_step,treasures,nodes,steps);
        fe.implementation();

        // write output to file
        FileWriter writer = new FileWriter("output.txt");


        System.out.println("front-end output: ");

        for (int i = 0; i < fe.output.size(); i++){
            int[] atom = fe.output.get(i);
            String writable = "";
            for (int a = 0; a < atom.length; a ++){
                writable = writable + String.valueOf(atom[a]) + " ";
            }
            writable += "\n";
            System.out.println(Arrays.toString(atom));
            writer.write(writable);
        }


        System.out.println(" key for translating the numbers used for propositional atoms into correct path ");
        for ( Map.Entry<String, Integer> s: fe.At.entrySet()){
            String str = "";
            str += String.valueOf(s.getValue()) + " ";
            String [] keys = s.getKey().split(" ");
            str += "At(" + keys[0] + "," + keys[1] + ")" + "\n";
            System.out.print(str);
            writer.write(str);
        }

        for ( Map.Entry<String, Integer> s: fe.Has.entrySet()){
            String str = "";
            str += String.valueOf(s.getValue()) + " ";
            String [] keys = s.getKey().split(" ");
            str += "Has(" + keys[0] + "," + keys[1] + ")" + "\n";
            System.out.print(str);
            writer.write(str);
        }

        writer.close();




        File file = new File("output.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<ArrayList<Integer>> clause = new ArrayList<>();
        boolean nextLine = true;
        while (nextLine){
            String[] satom = scanner.nextLine().split(" ");
            ArrayList<Integer> atom = new ArrayList<Integer>();
            for (String s : satom){
                atom.add(Integer.parseInt(s));
            }

            if (atom.size()==1 && atom.get(0).equals(0)) {
                nextLine = false;
            }
            else {
                clause.add(atom);
            }
        }


        System.out.println("final output from davis-putnam algo: ");
        davisPutnman solution = new davisPutnman(clause);
        boolean answer = solution.implement();
        if (answer){
            solution.addTrueToEmptyAnswer();
            System.out.println(solution.answer);
        }
        else {
            System.out.println("NO SOLUTION");
        }
    }


}
