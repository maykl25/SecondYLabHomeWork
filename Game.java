package theGame;
import java.io.*;
import java.util.Scanner;
/* Простейшая реализация игры "Крестики-нолики"
* Согласно правилам, победивший игрок получает 3 очка, а проигравший - 0 очков.
* Если происходит ничья, каждый игрок получает по одному очку.*/
public class Game {
    static int VERTICAL = 3;
    static int HORIZONTAL = 3;
    static boolean repeat = true;
    static int firstCount = 0;
    static int secondCount = 0;
    static String firstPlayerName;
    static String secondPlayerName;

    static char [][] gameField = new char [VERTICAL][HORIZONTAL];

    private static Scanner scanner = new Scanner(System.in);

    static char firstPlayerSign = 'X';
    static char secondPlayerSign = 'O';
    static char initialSign = '.';

    // метод по записи в файл рейтинга игроков
    public static void fileWork(String name1, String name2, int rating1, int rating2) throws IOException {
        File file = new File("d:/rating.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufWriter = new BufferedWriter(fileWriter);

        String rat1 = name1 + " " + rating1;
        String rat2 = name2 + " " + rating2;

        if (rating1 > rating2) {
            bufWriter.write(rat1);
            bufWriter.newLine();
            bufWriter.write(rat2);
        } else {
            bufWriter.write(rat2);
            bufWriter.newLine();
            bufWriter.write(rat1);
        }
        bufWriter.close();
    }

    //начальная настройка игрового поля
    public static void initialPlayField() {
        for (int i = 0; i < VERTICAL; i++) {
            for (int j = 0; j < HORIZONTAL; j++) {
                gameField[i][j] = initialSign;
            }
        }
    }

    //Графическое отображение игрового поля
    public static void printField() {
        System.out.println("  1 2 3");
        int count = 1;
        for (int i = 0; i < VERTICAL; i++) {
            for (int j = 0; j < HORIZONTAL; j++) {
                if (j == 0) {
                    System.out.print(count + "|");
                    count++;
                }
                System.out.print(gameField[i][j] + "|");
            }
            System.out.println();
        }
    }

    // запись символа на игровое поле
    public static void setSym(int y, int x, char sym) {
        gameField[y][x] = sym;
    }

    public static void firstPlayerStep() {
        int x;
        int y;

        do {
            System.out.println("Введите координаты: X Y (1-3)");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y,x));
        setSym(y,x, firstPlayerSign);
    }

    public static void secondPlayerStep() {
        int x;
        int y;

        do {
            System.out.println("Введите координаты: X Y (1-3)");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y,x));
        setSym(y,x, secondPlayerSign);
    }
    
    // Проверка правильности координат
    public static boolean isCellValid( int y, int x) {
        if (x < 0 || y < 0 || x > HORIZONTAL - 1 || y > VERTICAL - 1) {
            return false;
        }
        return gameField[y][x] == initialSign;
    }

    // проверка, есть ли свободные места на игровом поле
    public static boolean isFieldFull() {
        for (int i = 0; i < VERTICAL; i++) {
            for (int j = 0; j < HORIZONTAL; j++) {
                if (gameField[i][j] == initialSign) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkWin(char sym) {
        // Проверка горизонтальных позиций на наличие трех подряд ячеек
        if (gameField[0][0] == sym && gameField[0][1] == sym && gameField[0][2] == sym) {
            return true;
        }
        if (gameField[1][0] == sym && gameField[1][1] == sym && gameField[1][2] == sym) {
            return true;
        }
        if (gameField[2][0] == sym && gameField[2][1] == sym && gameField[2][2] == sym) {
            return true;
        }


        // Проверка вертикальных позиций на наличие трех подряд ячеек

        if (gameField[0][0] == sym && gameField[1][0] == sym && gameField[2][0] == sym) {
            return true;
        }
        if (gameField[0][1] == sym && gameField[1][1] == sym && gameField [2][1] == sym) {
            return true;
        }
        if (gameField[0][2] == sym && gameField[1][2] == sym && gameField[2][2] == sym) {
            return true;
        }


        //Проверка диагоналей на наличие трех ячеек подряд

        if (gameField[0][0] == sym && gameField[1][1] == sym && gameField[2][2] == sym) {
            return true;
        }

        return false;
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Введите имя первого игрока:");
        firstPlayerName = scanner.nextLine();
        System.out.println("Введите имя второго игрока:");
        secondPlayerName = scanner.nextLine();
        while (repeat) {
            initialPlayField();
            printField();

            while (true) {
                firstPlayerStep();
                printField();
                if (checkWin(firstPlayerSign)) {
                    System.out.println(firstPlayerName + " победил(a)!");
                    firstCount +=3 ;
                    break;
                }
                if (isFieldFull()) {
                    System.out.println("Ничья!");
                    firstCount++;
                    break;
                }

                secondPlayerStep();
                printField();
                if (checkWin(secondPlayerSign)) {
                    System.out.println(secondPlayerName + " победил(a)!");
                    secondCount += 3;
                    break;
                }
                if (isFieldFull()) {
                    System.out.println("Ничья!");
                    secondCount++;
                    break;
                }
            }
            System.out.println("Хотите сыграть еще?");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String answer = reader.readLine();
            if (answer.equals("No")) {
                repeat = false;
            }
        }
        fileWork(firstPlayerName, secondPlayerName, firstCount, secondCount);
    }
}
