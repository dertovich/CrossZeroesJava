import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CrossZeroes extends JComponent {

    public static final int EMPTY_CELL = 0; // Значение пустой клетки
    public static final int X_CELL = 30; // Значение крестика в клетке
    public static final int O_CELL = 200; // Значение нолика в клетке
    int[][] play_field; // Игровое поле
    int size = 3;
    boolean moveFlag;

    public CrossZeroes(){
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        play_field = new int [size][size];
        fillField();
    }

    public void fillField(){
        for (int i = 0; i < size; ++i){
            for (int j =0; j < size; ++j){
                play_field[i][j] = EMPTY_CELL;
            }
        }
        moveFlag = true;
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent){
        super.processMouseEvent(mouseEvent);
        // Проверяем нажатие ЛКМ в условии
        if (mouseEvent.getButton() == MouseEvent.BUTTON1){
            // Координаты клика мышкой
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();
            //
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
                    fillField();
                    repaint();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        drawField(graphics);
        drawCrossZeroOnField(graphics);
    }

    void drawField(Graphics graphics){
        int width = getWidth();
        int height = getHeight();
        int wCell;
        int hCell;

        wCell = width / size;
        hCell = height / size;
        graphics.setColor(Color.BLACK);
        for (int i = 1; i < size; i++){
            graphics.drawLine(0, i * hCell, width, i * hCell); // Линия на горизонтали
            graphics.drawLine(i * wCell, 0, i * wCell, height); // Линия на вертикали
        }
    }

    void drawCross(int i, int j, Graphics graphics){
        graphics.setColor(Color.BLUE);
        int x_width = getWidth() / size;
        int x_height = getHeight() / size;
        int x = i * x_width;
        int y = j * x_height;

        // Рисуем крестик полинейно
        graphics.drawLine(x, y, x + x_width, y + x_height);
        graphics.drawLine(x, y + x_height, x + x_width, y);
    }

    void drawZero(int i, int j, Graphics graphics){
        graphics.setColor(Color.RED);
        int zero_width = getWidth() / size;
        int zero_height = getHeight() / size;
        int x = i * zero_width;
        int y = j * zero_height;
        // Рисуем нолик так, чтобы он не касался линий игрового поля
        graphics.drawOval(x + 5 * zero_width / 100, y, zero_width * 9 / 10, zero_height);
    }

    void drawCrossZeroOnField(Graphics graphics){
        for (int i = 0; i < size; ++i){
            for (int j = 0; j < size; ++j)
                    if (play_field[i][j] == X_CELL)
                        drawCross(i, j, graphics);
                    else if (play_field[i][j] == O_CELL)
                        drawZero(i, j, graphics);
        }
    }

    int pickWinner(){
        int mainDiag = 0;
        int sideDiag = 0;
        int sumColumn;
        int sumRow;
        boolean isEmpty = false;

        for (int i = 0; i < 3; i++){
            mainDiag += play_field[i][i];
            sideDiag += play_field[i][2 - i];
        }

        if (mainDiag == O_CELL * 3 || mainDiag == X_CELL * 3) {
            return mainDiag;
        }
        if (sideDiag == O_CELL * 3 || sideDiag == X_CELL * 3) {
            return sideDiag;
        }

        for (int i = 0; i < 3; i++){
            sumColumn = 0;
            sumRow = 0;
            for (int j = 0; j < 3; j++){
                if (play_field[i][j] == 0){
                    isEmpty = true;
                }
                sumRow += play_field[i][j];
                sumColumn += play_field[j][i];
            }
            if (sumRow == O_CELL * 3 || sumRow == X_CELL * 3) {
                return sumRow;
            }
            if (sumColumn == O_CELL * 3 || sumColumn == X_CELL * 3) {
                return sumColumn;
            }
        }
        if (!isEmpty)
            return -1;
        return 0;
    }
}
