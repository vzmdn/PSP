public class ejemploArgs {
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("argumentos:");
            for (String string : args) {
                System.out.println(string);
            }
        }
        else System.out.println("no hay argumentos");

    }
}