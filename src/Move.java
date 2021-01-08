import java.util.ArrayList;
import java.util.Arrays;
public abstract class Move {
    // Utility class to help generate move tables
    // Many of these methods come from https://www.jaapsch.net/puzzles/compindx.htm
    public static byte[] orientationFromInt(int N, int states, int length) {
        byte[] out = new byte[length];
        for (int i = length-1; i >= 0; i--) {
            out[i] = (byte) (N%states);
            N /= states;
        }
        return out;
    }

    public static int orientationToInt(byte[] arr, int states, int length) {
        int out = 0;
        for (int i = 0; i < length; i++) {
            out *= states;
            out += arr[i];
        }
        return out;
    }

    public static byte[] composeOrientations(byte[] orient1, byte[] orient2, int states) throws IllegalArgumentException {
        if (orient1.length != orient2.length) throw new IllegalArgumentException("orientations must be equal lengths!");
        int N = orient1.length;
        byte[] out = new byte[N];
        for (int i = 0; i < N; i++) {
            out[i] = (byte) ((orient1[i] + orient2[i]) % states);
        }
        return out;

    }
    public static byte[] composePermutations(byte[] perm1, byte[] perm2) throws IllegalArgumentException {
        if (perm1.length != perm2.length) throw new IllegalArgumentException("permutations must be equal lengths!");
        int N = perm1.length;
        byte[] out = new byte[N];
        for (int i = 0; i < N; i++) {
            out[i] = perm1[perm2[i]-1];
        }
        return out;
    }

    public static int permutationToInt(byte[] perm, int length) {
        int out = 0;
        for (int i = 1; i <= length-1; i++) {
            out *= (length-i+1);
            for (int j = i+1; j <= length; j++) {
                if (perm[i-1] > perm[j-1]) out++;
            }
        }
        return out;
    }

    public static byte[] permutationFromInt(int N, int length) {
        byte[] perm = new byte[length];
        perm[length-1] = 1;
        for (int i = length-1; i >= 1; i--) {
            perm[i-1] = (byte) (1 + (N % (length - i + 1)));
            N /= (length-i+1);
            for (int j = i; j < length; j++) {
                if (perm[j] >= perm[i-1]) perm[j]++;
            }
        }
        return perm;
    }

    /*
    Now, onto how to generate the actual move tables
    Each move will have a corresponding lookup table: an ArrayList of 4 int arrays:

    {
        corner_orientation = new int[6561], (3^8)
        corner_permutation = new int[40320], (8!)
        edge_orientation = new int[4096], (2^12)
        edge_permutation = new int[479001600], (12!)
    }
     */
    public static ArrayList<int[]> genMoveTable(int co, int cp, int eo, int ep) {
        ArrayList<int[]> out = new ArrayList<>();
        byte[] co_arr = orientationFromInt(co, 3, 8);
        byte[] cp_arr = permutationFromInt(cp, 8);
        byte[] eo_arr = orientationFromInt(eo, 2, 12);
        byte[] ep_arr = permutationFromInt(ep, 12);
        System.out.println("Generating move lookup table...");
        int[] corner_orientation = new int[6561];
        for (int i = 0; i < 6561; i++) {
            corner_orientation[i] = orientationToInt(composeOrientations(co_arr, orientationFromInt(i, 3, 8),3),
                    3, 8);
        }
        out.add(corner_orientation);
        System.out.println("Corner orientation table done...");
        int[] corner_permutation = new int[40320];
        for (int i = 0; i < 40320; i++) {
            corner_permutation[i] = permutationToInt(composePermutations(cp_arr, permutationFromInt(i, 8)),8);
        }
        out.add(corner_permutation);
        System.out.println("Corner permutation table done...");
        int[] edge_orientation = new int[4096];
        for (int i = 0; i < 4096; i++) {
            edge_orientation[i] = orientationToInt(composeOrientations(eo_arr, orientationFromInt(i, 2,12), 2), 2,12);
        }
        out.add(edge_orientation);
        System.out.println("Edge orientation table done...");
        int[] edge_permutation = new int[479001600];
        for (int i = 0; i < 479001600; i++) {
            edge_permutation[i] = permutationToInt(composePermutations(ep_arr, permutationFromInt(i, 12)),12);
            if (i % 500000 == 0) {
                System.out.println(i);
            }
        }
        out.add(edge_permutation);
        System.out.println("Done with table generation!");
        return out;
    }

    public static void main(String[] args) {
        /*
        System.out.println(orientationToInt(new byte[]{0,0,2,1,0,0,1,2},3,8)); // R co coord
        System.out.println(permutationToInt(new byte[]{1,2,4,8,5,6,3,7},8)); // R cp coord
        System.out.println(orientationToInt(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0},2,12)); // R eo cord
        System.out.println(permutationToInt(new byte[]{1,2,8,4,5,6,3,11,9,10,7,12},12)); // R ep coord
        // ArrayList<int[]> temp = genMoveTable(2404, 21021, 401, 76449);
         */
        System.out.println(Arrays.toString(composePermutations(
                new byte[]{1,2,3,4,5,6,7,8},
                new byte[]{1,2,3,4,6,5,8,7}
        )));
    }


}
