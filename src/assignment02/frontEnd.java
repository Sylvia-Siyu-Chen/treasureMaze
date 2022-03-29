package assignment02;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class frontEnd {
    int allowed_step;
    String[] treasures;
    String[] nodes;
    HashMap<String,Integer> At = new HashMap<>();
    HashMap<String,Integer> Has = new HashMap<>();
    ArrayList<int[]> output = new ArrayList<int[]>();
    HashMap<String, String[]> Treasures = new HashMap<>();
    int numAtoms = 1;
    HashMap<String, String> Connect = new HashMap<String, String>();

    ArrayList<String[]> steps;

    public frontEnd( int allowed_step, String[] treasures, String[] nodes, ArrayList<String[]> steps) throws FileNotFoundException {
        this.allowed_step = allowed_step;
        this.treasures = treasures;
        this.nodes = nodes;
        this.steps = steps;
    }

    public void constructAt(){
        for (int i = 0; i < allowed_step+1; i++){
            for (String node : nodes)
            {
                String at = node + " " + String.valueOf(i);
                At.put(at, numAtoms);
                numAtoms++;
            }
        }
    }

    public void constructHas(){
        for (int i = 0; i < allowed_step+1; i++){
            for (String treasure : treasures){
                String has = treasure + " " + String.valueOf(i);
                Has.put(has,numAtoms);
                numAtoms++;
            }
        }
    }

    public void setConnect(){
        for (int i = 0; i < steps.size(); i++){
            String[] currentStep = steps.get(i);
            String node = currentStep[0];
            boolean next = false;
            String connect =  "";
            for (String s : currentStep){
                if (next){
                    connect = connect + s + " ";
                }
                if (s.equals("NEXT")) next = true;
            }
            Connect.put(node, connect);
        }
    }

    public void setTreasures() {
        for (int i = 1; i < steps.size(); i++) {
            {
                String[] step = steps.get(i);
                String node = step[0];
                StringBuilder treasures = new StringBuilder();
                boolean next = true;
                boolean treasure = false;

                if (!step[0].equals("START")) {
                    for (String s : step) {
                        if (s.equals("NEXT")) next = false;
                        if (treasure && next) {
                            treasures.append(s).append(" ");
                        }
                        if (s.equals("TREASURES")) treasure = true;
                    }
                }


                String[] str_treasure = treasures.toString().split(" ");
                Treasures.put(node, str_treasure);
            }
        }
    }


    public void categoriesOne(){
        for (int k = 0; k < allowed_step+1; k++) {
            for (int i = 0; i < nodes.length; i++) {
                for (int j = i + 1; j < nodes.length; j++) {
                    String at1 = nodes[i] + " " + String.valueOf(k);
                    int num1 = At.get(at1);
                    String at2 = nodes[j] + " " + String.valueOf(k);
                    int num2 = At.get(at2);
                    output.add(new int[] {num1*(-1),num2*(-1)});
                }
            }
        }
    }

    public void categoriesTwo(){
        for (int k = 0; k < allowed_step; k++) {
            for (String node: nodes){
                String[] connectedNode = Connect.get(node).split(" ");
                int nodeAt = At.get(node + " " + String.valueOf(k));
                int[] net_nodeAt = {nodeAt * (-1)};
                int[] connected = new int[connectedNode.length];
                for (int j = 0; j < connectedNode.length; j++) {
                    connected[j] = At.get(connectedNode[j] + " " + String.valueOf(k + 1));
                }
                int[] append = Arrays.copyOf(net_nodeAt, connected.length + 1);
                System.arraycopy(connected, 0, append, 1, connected.length);
                output.add(append);
            }
        }
    }

    public void categoriesThree(){
        for (String node : nodes){
            if (!node.equals("START")){
                String[] treasures = Treasures.get(node);
                if (treasures!=null){
                    for (int i = 0; i < allowed_step+1; i ++){
                        int at = At.get(node + " " + String.valueOf(i));
                        for (String t : treasures){
                            if (Has.containsKey(t + " " + String.valueOf(i))) {
                                int has = Has.get(t + " " + String.valueOf(i));
                                int[] put = {at * (-1), has};
                                output.add(put);
                            }
                        }
                    }
                }
            }
        }
    }

    public void categoriesFour(){
        for (String t : treasures){
            for (int i = 0; i < allowed_step; i ++){
                    int num1 = Has.get(t + " " + String.valueOf(i));
                    int num2 = Has.get(t + " " + String.valueOf(i+1));
                    output.add(new int[] {(-1)*num1, num2});

            }
        }
    }

    public void categoriesFive(){
        for (int i = 1; i < allowed_step+1; i ++){
            for (String node : nodes){
                if (!node.equals("START")) {
                    int at = At.get(node + " " + String.valueOf(i));
                    String[] treasure = Treasures.get(node);
                    for (String t : treasure) {
                        if (Has.containsKey(t + " "+String.valueOf(i-1)) && Has.containsKey(t + " " + String.valueOf(i))){
                            int has1 = Has.get(t + " " + String.valueOf(i - 1));
                            int has2 = Has.get(t + " " + String.valueOf(i));
                            int[] append = {has1, (-1) * has2, at};
                            output.add(append);
                        }
                    }
                }
            }
        }
    }

    public void categoriesSix(){
        output.add(new int[]{At.get("START" + " " + "0")});
    }

    public void categoriesSeven(){
        for (String t : treasures){
            output.add(new int[]{(-1)*Has.get(t + " " + "0")});
        }
    }

    public void categoriesEight(){
        for (String t : treasures){
            output.add(new int []{ Has.get(t + " " + String.valueOf(allowed_step))});
        }
    }


    public void implementation(){
        constructAt();
        constructHas();
        setConnect();
        setTreasures();
        System.out.println(Connect.toString());

        categoriesOne();
        categoriesTwo();
        categoriesThree();
        categoriesFour();
        categoriesFive();
        categoriesSix();
        categoriesSeven();
        categoriesEight();

        output.add(new int[] {0});
    }
}