import java.util.Scanner;

public class my_utils {
    public static int size = getSizeField();

    public static int getSizeField() {
        int size;
        Scanner input = new Scanner(System.in);

        System.out.print("Введите размер поля: ");
        size = input.nextInt();
        input.close();

        return size;
    }

}
