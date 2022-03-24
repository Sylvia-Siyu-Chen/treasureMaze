import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<ArrayList<Integer>> clause = new ArrayList<>();
        while (scanner.hasNextLine()){
            String[] satom = scanner.nextLine().split(" ");
            ArrayList<Integer> atom = new ArrayList<Integer>();
            for (String s : satom){
                atom.add(Integer.parseInt(s));
            }
            clause.add(atom);
        }
        clause.remove(clause.size()-1);
        davisPutnman solution = new davisPutnman(clause);
        solution.implement();
        System.out.println(solution.answer);
    }


}
