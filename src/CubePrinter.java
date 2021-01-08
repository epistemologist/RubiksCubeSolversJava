import java.util.ArrayList;
import java.util.Arrays;

public abstract class CubePrinter {
    public static final String[] EDGES = {"UF", "UL", "UB", "UR", "FR", "FL", "BL", "BR", "DF", "DL", "DB", "DR"};
    public static final String[] CORNERS = {"URF", "ULF", "ULB", "URB", "DRF", "DLF", "DLB", "DRB"};
    public static final String[] CENTERS = {"U", "L", "F", "R", "B", "D"};
    public static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqr";
    public static final String template = "\n   U4X      \n   2m6      \n   R0O      \nV3ST1QP7YZ5W\nDnBAo89pFEqC\nhJefHcbNklLi\n   dGa      \n   IrM      \n   gKj      \n";

    private static String rotateString(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    public static String genFlatString(int co, int cp, int eo, int ep) {

        // Format corners
        ArrayList<String> corners = new ArrayList<>();
        for (byte i: Move.permutationFromInt(cp, 8)) {
            corners.add(CORNERS[i-1]);
        }

        byte[] cornerOrientations = Move.orientationFromInt(co, 3, 8);
        for (int i = 0; i < 8; i++) {
            if (cornerOrientations[i]!=0) corners.set(i, rotateString(corners.get(i), cornerOrientations[i]));
        }


        // Format edges
        ArrayList<String> edges = new ArrayList<>();
        for (byte i: Move.permutationFromInt(ep, 12)) {
            edges.add(EDGES[i-1]);
        }

        byte[] edgeOrientations = Move.orientationFromInt(eo, 2, 12);
        System.out.println(Arrays.toString(edgeOrientations));
        for (int i = 0; i < 12; i++) {
            edges.set(i, rotateString(edges.get(i), edgeOrientations[i]));
        }

        String out = "";
        for (String s: edges) out += s;
        for (String s: corners) out += s;
        for (String s: CENTERS) out += s;
        return out;

        /*
        // Format corners
        String[] corners = CORNERS.clone();
        byte[] cornerOrientations = Move.orientationFromInt(co, 3, 8);
        for (int i = 0; i < 8; i++) {
            if (cornerOrientations[i] != 0)
                corners[i] = rotateString(corners[i], cornerOrientations[i]);
        }
        ArrayList<String> cornersPermuted = new ArrayList<>();
        for (byte i : Move.permutationFromInt(cp, 8)) {
            cornersPermuted.add(corners[i - 1]);
        }
        // Format edges
        String[] edges = EDGES.clone();
        byte[] edgeOrientations = Move.orientationFromInt(eo, 2, 12);
        for (int i = 0; i < 12; i++) {
            if (edgeOrientations[i] != 0) {
                edges[i] = rotateString(edges[i], edgeOrientations[i]);
            }
        }
        ArrayList<String> edgesPermuted = new ArrayList<>();
        for (byte i : Move.permutationFromInt(ep, 12)) {
            edgesPermuted.add(edges[i - 1]);
        }
        String out = "";
        for (String s: edgesPermuted) out += s;
        for (String s: cornersPermuted) out += s;
        for (String s: CENTERS) out += s;
        return out;
        */
    }

    public static String genCubeString(String flatString) {
        System.out.println("flatString: "+flatString.length());
        String out = "";
        for (int i = 0; i < template.length(); i++) {
            char currentChar = template.charAt(i);
            if (currentChar == '\n' || currentChar == ' ') {
                out += currentChar;
            } else {
                int j;
                for (j = 0; j < 54; j++) {
                    if (alphabet.charAt(j) == currentChar) break;
                }
                out += flatString.charAt(j);
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(genFlatString(0,0,0,0));
    }
}