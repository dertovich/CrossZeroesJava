import javax.swing.*;
import java.awt.*;

public class Main {
    public static void InitializingWindow(){
        JFrame window = new JFrame("Крестики & Нолики"); // Инициализация окна с игрой
        window.setDefaultCloseOperation((WindowConstants.EXIT_ON_CLOSE)); // Устанавливает кнопку закрытия, чтобы после нажатия наша программа завершалась с закрытием окна
        window.setSize(555, 555); // Устанавливаем размер окна
        window.setLayout(new BorderLayout()); // Менеджер слоев. Он поможет нам добавлять компоненты(например крестики)
        window.setLocationRelativeTo(null); // Центрируем окно на экране
        window.setVisible(true); // Делаем окно видимым
        CrossZeroes Nol = new CrossZeroes();
        window.add(Nol);
    }
    public static void RulesMessage(){
        // Сообщения с выводом приветствия и правил
        JOptionPane.showMessageDialog(null, """
                Игроки по очереди ставят крестик/нолик на свободные клетки поля N×N.
                                Один игрок может ставить только крестики, другой только нолики.
                                Первый, выстроивший в ряд N своих фигуры по вертикали, горизонтали или диагонали, выигрывает.
                                Первый ход делает игрок, ставящий крестики.""", "Правила", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        RulesMessage();
        InitializingWindow();
    }
}