import java.util.ArrayList;

public class Cube {
    public byte[] edgeOrientation;
    public byte[] edgePermutation;
    public byte[] cornerOrientation;
    public byte[] cornerPermutation;
    public Cube() {
        this.cornerOrientation = new byte[8];
        this.cornerPermutation = new byte[8];
        for (int i = 1; i <= 8; i++) {
            this.cornerPermutation[i] = (byte) i;
        }
        this.edgeOrientation = new byte[12];
        this.edgePermutation = new byte[12];
        for (int i = 1; i <= 12; i++) {
            this.edgePermutation[i] = (byte) i;
        }
    }

}