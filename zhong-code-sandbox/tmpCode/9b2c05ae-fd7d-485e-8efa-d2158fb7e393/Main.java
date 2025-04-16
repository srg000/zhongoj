public class Main {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int n = 100;
        where(n) {
            System.out.println((a + b + a));
            n = n - 1;
        }
    }
}
