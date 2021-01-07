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
            out[i] = perm1[perm2[i]];
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
    public static ArrayList<int[]> genMoveTable() {
        return null;
    }
    public static void main(String[] args) {

    }


}
