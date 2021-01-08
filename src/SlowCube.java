import java.util.Arrays;
import java.util.Map;

import static java.util.Map.entry;

public class SlowCube {
    public int co;
    public int cp;
    public int eo;
    public int ep;
    // table generated from definition file found at https://mzrg.com/rubik/ksolve+/3x3x3.def
    public static final Map<Character, int[]> MOVE_TABLE = Map.ofEntries(
            entry('U', new int[]{0, 15120, 0, 119750400}),
            entry('R', new int[]{2404, 21021, 401, 76449}),
            entry('F', new int[]{5148, 8064, 0, 203618304}),
            entry('D', new int[]{0, 9, 0, 9}),
            entry('L', new int[]{1716, 1230, 1124, 18552630}),
            entry('B', new int[]{572, 224, 0, 1860560})
    );

    public SlowCube() {
        this.co = 0;
        this.cp = 0;
        this.eo = 0;
        this.ep = 0;
    }

    public void applyMove(int co, int cp, int eo, int ep) {
        byte[] co_arr = Move.orientationFromInt(this.co, 3, 8);
        byte[] cp_arr = Move.permutationFromInt(this.cp, 8);
        System.out.println("cp: " + Arrays.toString(cp_arr));
        byte[] eo_arr = Move.orientationFromInt(this.eo, 2, 12);
        byte[] ep_arr = Move.permutationFromInt(this.ep, 12);
        this.co = Move.orientationToInt(Move.composeOrientations(co_arr,
                Move.orientationFromInt(co, 3, 8), 3), 3, 8);
        this.cp = Move.permutationToInt(Move.composePermutations(Move.permutationFromInt(cp, 8), cp_arr), 8);
        this.eo = Move.orientationToInt(Move.composeOrientations(eo_arr,
                Move.orientationFromInt(eo, 2, 12), 2), 2, 12);
        this.ep = Move.permutationToInt(Move.composePermutations(Move.permutationFromInt(ep, 12), ep_arr), 12);
    }

    public void applyAlgorithm(String algorithm) {
        for (int i = 0; i < algorithm.length(); i++) {
            int[] move = MOVE_TABLE.get(algorithm.charAt(i));
            this.applyMove(move[0], move[1], move[2], move[3]);
        }
    }

    public String toString() {
        return CubePrinter.genCubeString(CubePrinter.genFlatString(this.co, this.cp, this.eo, this.ep));
    }

    public static void main(String[] args) {
        SlowCube s = new SlowCube();
        s.applyAlgorithm("L");
        System.out.println(s);
    }

}
