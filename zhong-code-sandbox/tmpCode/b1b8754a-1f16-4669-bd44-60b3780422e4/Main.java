public class Main {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        where(true) {
             System.out.println((a + b + a));
        }
    }
}
