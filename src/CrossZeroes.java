import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CrossZeroes extends JComponent {
    // Присваеваем "уникальные" значения для пустой клетки и клеток с ноликом или крестиком
    // Чтобы при дальнейшем заполнении найти победителя

    public static final int EMPTY_CELL = 0; // Значение пустой клетки
    public static final int X_CELL = 30; // Значение крестика в клетке
    public static final int O_CELL = 200; // Значение нолика в клетке
    int[][] play_field; // Игровое поле
    int size = 3; // размер поля
    boolean moveFlag; // Для определения очередности хода

    public CrossZeroes(){
        enableEvents(AWTEvent.MOUSE_EVENT_MASK); // Создаем маску для отслеживаения действий мыши
        play_field = new int [size][size]; // Выделяем память под игровое поле
        fillField(); // Заполняем поле значениями пустой клетки
    }

    public void fillField(){
        for (int i = 0; i < size; ++i){
            for (int j =0; j < size; ++j){
                play_field[i][j] = EMPTY_CELL;
            }
        }
        moveFlag = true; // Ставим флаг в позицию true для хода крестиками
    }

    // Перегрузка функции
    @Override
    protected void processMouseEvent(MouseEvent mouseEvent){
        super.processMouseEvent(mouseEvent);
        // Проверяем нажатие ЛКМ в условии
        if (mouseEvent.getButton() == MouseEvent.BUTTON1){
            // Координаты клика мышкой
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();
            // Определяем ячейку, на которую кликнули ЛКМ
            int i = (int) ((float) x / getWidth() * size);
            int j = (int) ((float) y / getHeight() * size);

            if (play_field[i][j] == EMPTY_CELL){
                play_field[i][j] = moveFlag ? X_CELL : O_CELL;
                moveFlag = !moveFlag; // Меняем флаг на противоположный
                repaint(); // Перерисовываем поле
                // Определим победителя
                int winner = pickWinner();
                if (winner != 0){
                    if (winner == O_CELL * 3) {
                        JOptionPane.showMessageDialog(this, "Нолики выиграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                    } else if (winner == X_CELL * 3) {
                        JOptionPane.showMessageDialog(this, "Крестики выиграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Никто не выиграл!", "Ничья!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    fillField(); // Заполняем поле значениями пустой клетки
                    repaint(); // Перерисовываем поле
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        drawField(graphics); // Рисуем разлинованное поле
        drawCrossZeroOnField(graphics); // Функция для отрисовк и крестиков/ноликов
    }

    void drawField(Graphics graphics){
        int width = getWidth(); // Ширина всего поля
        int height = getHeight(); // Высота всего поля
        int wCell; // Ширина отдельной клетки
        int hCell; // Высота отдельной клетки

        wCell = width / size;
        hCell = height / size;
        graphics.setColor(Color.BLACK); // Устанавливаем цвет линий
        for (int i = 1; i < size; i++){
            graphics.drawLine(0, i * hCell, width, i * hCell); // Линия на горизонтали
            graphics.drawLine(i * wCell, 0, i * wCell, height); // Линия на вертикали
        }
    }

    void drawCross(int i, int j, Graphics graphics){
        graphics.setColor(Color.BLUE); // Устанавливаем цвет линий
        int x_width = getWidth() / size;
        int x_height = getHeight() / size;
        int x = i * x_width;
        int y = j * x_height;

        // Рисуем крестик полинейно
        graphics.drawLine(x, y, x + x_width, y + x_height); // Отрисовка линии от верхнего левого угла в правый нижний
        graphics.drawLine(x, y + x_height, x + x_width, y); // Отрисовка от левого нижнего угла в правый верхний
    }

    void drawZero(int i, int j, Graphics graphics){
        graphics.setColor(Color.RED); // Устанавливаем цвет нолика
        int zero_width = getWidth() / size;
        int zero_height = getHeight() / size;
        int x = i * zero_width;
        int y = j * zero_height;
        // Рисуем нолик так, чтобы он не касался линий игрового поля
        graphics.drawOval(x + 5 * zero_width / 100, y, zero_width * 9 / 10, zero_height);
    }

    void drawCrossZeroOnField(Graphics graphics){
        // Ходим по клеткам и проверяем их значения
        for (int i = 0; i < size; ++i){
            for (int j = 0; j < size; ++j)
                    if (play_field[i][j] == X_CELL) // Если значение крестика, то рисуем крестик
                        drawCross(i, j, graphics);
                    else if (play_field[i][j] == O_CELL) // Если значение нолика, то кушаем бутербродик
                        drawZero(i, j, graphics);
        }
    }

    int pickWinner(){
        // Тут находим победителя
        int mainDiag = 0; // Для суммы на главной диагонали
        int sideDiag = 0; // Для суммы на побочной диагонали
        int sumColumn; // Для суммы в колонке
        int sumRow; // Для суммы в строчке
        boolean isEmpty = false; // Проверка пустой клеточки

        // Проходимся по диагоналям и складываем значения клеток на них
        for (int i = 0; i < 3; i++){
            mainDiag += play_field[i][i];
            sideDiag += play_field[i][2 - i];
        }

        // Проверяем соответствует ли сумма 3 ноликам или 3 крестикам на главной диагонали
        if (mainDiag == O_CELL * 3 || mainDiag == X_CELL * 3) {
            return mainDiag;
        }
        // Проверяем соответствует ли сумма 3 ноликам или 3 крестикам на побочной диагонали
        if (sideDiag == O_CELL * 3 || sideDiag == X_CELL * 3) {
            return sideDiag;
        }

        // Затаим дыхание!
        // Тут ходим и складываем колонки и строчки
        for (int i = 0; i < 3; i++){
            sumColumn = 0;
            sumRow = 0;
            for (int j = 0; j < 3; j++){
                if (play_field[i][j] == 0){
                    isEmpty = true; // Если поле пустое, то переключаем флаг
                }
                sumRow += play_field[i][j];
                sumColumn += play_field[j][i];
            }

            // Далее проверяем есть ли выиграшная сумма в строке или столбце
            if (sumRow == O_CELL * 3 || sumRow == X_CELL * 3) {
                return sumRow;
            }
            if (sumColumn == O_CELL * 3 || sumColumn == X_CELL * 3) {
                return sumColumn;
            }
        }
        // Если поле не пусто, но победителя на данный момент не обнаружили, то возвращается -1
        if (!isEmpty)
            return -1;
        return 0;
    }
}
