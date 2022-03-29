package assignment02;

import java.util.*;


public class davisPutnman {
    ArrayList<ArrayList<Integer>> claus;
    ArrayList<ArrayList<Integer>> originalClause;

    HashMap<Integer,String> answer = new HashMap<>();
    Stack <Integer> diffCases = new Stack<>();

    Stack<ArrayList<ArrayList<Integer>> > historyClaus = new Stack<>();
    Stack<Integer> progress = new Stack<>();

    int maximunAtom = 0;


    // constructor
    davisPutnman(ArrayList<ArrayList<Integer>> claus){
        this.claus = claus;
        this.originalClause = claus;
    }

    public boolean implement() {
        ArrayList<ArrayList<Integer>> hclause = new ArrayList<>(); // deep clone
        for (int i = 0; i < claus.size(); i++){
            hclause.add((ArrayList<Integer>) claus.get(i).clone());
        }
        historyClaus.push(hclause);



        if (this.claus.size() == 0) return true;
        if (this.validCheck()) {
            boolean easyCase = false;
            for(int i = 0; i<claus.size(); i++){
                ArrayList<Integer> atom = claus.get(i);
                for (int j = 0; j < atom.size(); j++) {
                    if (Math.abs(atom.get(j)) > maximunAtom) maximunAtom = Math.abs(atom.get(j));
                }
                if (atom.size() == 1) {
                    easyCase = true;
                    if (atom.get(0) < 0) {
                        // assign False value
                        answer.put(Math.abs(atom.get(0)), "F");
                        progress.add(Math.abs(atom.get(0)));
                    } else {
                        answer.put(atom.get(0), "T");
                        progress.add(Math.abs(atom.get(0)));
                    }
                    break;
                }
            }
            if (easyCase) {
                this.filter();
            } else {
                // no easy case
                int minRemainNum = this.minNum();
                answer.put(minRemainNum, "T");
                progress.add(Math.abs(minRemainNum));
                diffCases.push(minRemainNum);
                this.filter();
            }

        }
        else {
            if (diffCases.size() == 0) return false; // no solution
            else {
                int traceBack = diffCases.pop();
                claus = historyClaus.pop();
                while (progress.peek() != traceBack) {
                    answer.remove(progress.pop());
                    claus = historyClaus.pop();
                }
                claus = historyClaus.peek();
                answer.put(traceBack, "F");
                this.filter();
            }
        }
        return this.implement();
    }

    public void filter(){
        int i = 0;
        while (i < claus.size()){
                ArrayList<Integer> atom = claus.get(i);
                int j = 0;
                while(j < atom.size()){
                    int num = atom.get(j);
                    if (answer.containsKey(Math.abs(num))){
                        if ((num < 0 && Objects.equals(answer.get(Math.abs(num)), "F") || num >=0 && Objects.equals(answer.get(num),"T"))){
                            claus.remove(atom);
                            i--;
                        }
                        else {
                            atom.remove(j);
                        }
                    }
                    j++;
            }
                i++;
        }

    }

    public int minNum(){
        int min = 100;
        for(int i = 0; i<claus.size(); i++){
            ArrayList<Integer> atom = claus.get(i);
            for (int j = 0; j < atom.size(); j++){
                int num = atom.get(j);
                if (Math.abs(num) < min){
                    min = Math.abs(num);
                }
            }
        }
        return min;
    }

    public boolean validCheck(){
        boolean valid = true;
        ArrayList<Integer> sizeOne = new ArrayList<>();
        for(int i = 0; i < claus.size(); i++){
            ArrayList<Integer> atom = claus.get(i);
            if (atom.size() == 1){
                int currentNum = atom.get(0);
                for (Integer integer : sizeOne){
                    if (integer + currentNum == 0) {
                        valid = false;
                        break;
                    }
                }
                if (valid) sizeOne.add(currentNum);
            }
        }
        return valid;
    }

    public void addTrueToEmptyAnswer(){
        if (answer.size() < maximunAtom){
            for (int i = 1; i <= maximunAtom; i++){
                if (!answer.containsKey(i)) answer.put(i,"T");
            }
        }
    }
}