import java.util.*;


public class davisPutnman {
    ArrayList<ArrayList<Integer>> claus;
    HashMap<Integer,String> answer = new HashMap<>();
    ArrayList<Integer> diffCases = new ArrayList<>();
    Stack<Integer> progress = new Stack<>();

    // constructor
    davisPutnman(ArrayList<ArrayList<Integer>> claus){
        this.claus = claus;
    }

    public void implement() {
        if (this.claus.size() == 0) return;
        if (this.validCheck()) {
            boolean easyCase = false;
            for (ArrayList<Integer> atom : claus) {
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
                }
            }
            if (easyCase) {
                this.filter();
            } else {
                // no easy case
                int minRemainNum = this.minNum();
                answer.put(minRemainNum, "T");
                progress.add(Math.abs(minRemainNum));
                diffCases.add(minRemainNum);
                this.filter();
            }
        }
        else {
            int traceBack = diffCases.remove(diffCases.size()-1);
            while (progress.peek()!=traceBack){
                answer.remove(progress.pop());
            }
            answer.put(traceBack,"F");
            this.filter();
        }
        this.implement();
    }

    public void filter(){
        for (ArrayList<Integer> atom : claus){
            for (Integer num : atom){
                if (answer.containsKey(Math.abs(num))){
                    if ((num < 0 && Objects.equals(answer.get(Math.abs(num)), "F") || num >=0 && Objects.equals(answer.get(num),"T"))){
                        claus.remove(atom);
                    }
                    else {
                        atom.remove(num);
                    }
                }
            }
        }
    }

    public int minNum(){
        int min = 100;
        for (ArrayList<Integer> atom : claus){
            for (Integer num : atom){
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
        for (ArrayList<Integer> atom : claus){
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
}