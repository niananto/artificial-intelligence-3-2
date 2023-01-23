package Classes;

public class Random {
    private static java.util.Random random;
    public static void setSeed(long seed) {
        random = new java.util.Random(seed);
    }

    public static double nextDouble() {
//        return random.nextDouble();
        return Math.random();
    }
}
