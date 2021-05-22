import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Scanner;

public class Manager {


    public static void main(String[] args) {
        Reader reader0 = new Reader();
        Writer writer = new Writer();
        byte isWrite = 0;
        System.out.println("Добро пожаловать в программу 'Корреляционно-регрессионный анализ'!");
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(new Locale("Russian"));
        boolean cycle = true;
        double[] lineX = null, lineY = null;
        String[] line;
        int n;
        System.out.println("Желаете сохранять результаты в файл?(0 - нет, 1 - да)" +
                "Если файла нет, то он будет создан, иначе перезаписан." +
                "Если не получиться записать, то будет выдано соответствующее предупреждение." +
                "При вводе отличного значения от предложенных двух запись в файл производиться не будет.");
        if (scanner.next().equals("1")){
            System.out.println("Введите полный путь к файлу: ");
            String path = scanner.next();
            isWrite = writer.setPath(path);
        }
        while (cycle) {
            System.out.println("Для вывода помощи введите команду help.");
            System.out.println("Выберите тип ввода(0 - из файла, 1 - с клавиатуры): ");
            /* С файла. Первая строка - Х, разделенные запятыми, вторая строка - У, разделенные запятыми*/
            switch (scanner.next()) {
                case ("0"): {
                    System.out.println("Введите полный путь к файлу: ");
                    String path = scanner.next();
                    FileReader fr = null;
                    try {
                        fr = new FileReader(path);
                        BufferedReader reader = new BufferedReader(fr);
                        line = reader.readLine().replaceAll("[^\\d+',']","").split(",");
                        lineX = new double[line.length];
                        n = line.length;
                        for (int i=0;i<line.length;i++){
                            lineX[i]=Double.parseDouble(line[i]);
                        }
                        line = reader.readLine().replaceAll("[^\\d+',']","").split(",");
                        lineY = new double[line.length];
                        for (int i=0;i<line.length;i++){
                            lineY[i]=Double.parseDouble(line[i]);
                        }
                        cycle = false;
                        break;
                    }catch(Exception e){
                        System.out.println("Can't read this file!");
                    }
                }
                case ("1"): {
                    try {

                        System.out.println("Введите количество пар XY:");
                        n = Integer.parseInt(scanner.next());
                        System.out.println("Введите значения X в строку через пробел:");
                        lineX = new double[n];
                        for (int i = 0; i < lineX.length; i++) {
                            lineX[i] = Double.parseDouble(scanner.next());
                        }
                        System.out.println("Введите значения Y в строку через пробел:");
                        lineY = new double[n];
                        for (int i = 0; i < lineY.length; i++) {
                            lineY[i] = Double.parseDouble(scanner.next());
                        }
                        cycle = false;
                        break;
                    }catch (Exception e){
                        System.out.println("You wrote smth wrong!");
                    }
                }
                case "exit": System.exit(0);
                case "help":
                    System.out.println("Следуйте инструкциям на экране.\n" +
                            "Ввод цифры выбирает необходимое действие.\n" +
                            "Всегда доступные команды help и exit.\n" +
                            "help - выводит помощь по работе с программой.\n" +
                            "exit - выход из программы.");
                default: {
                    System.out.println("Wrong answer. Try again.");
                    break;
                }
            }

        }
        Mathematic math = new Mathematic(lineX, lineY);
        cycle = true;
        while (cycle){
            System.out.println("Выберите необходимое действие:\n" +
                    "0 - Вычисление линейного коэффициента корреляции и проверка его на значимость;\n" +
                    "1 - Вычисление коэффициента Спирмена;\n" +
                    "2 - Рассчет уравнения регрессии, проверка его на адекватность и вывод графика уравнения.");
            switch (scanner.next()) {
                case "0": {
                    double lkk, a = 0.10;
                    if (lineX.length<30) {
                        System.out.println("Введите необходимый уровень значимости(0.10, 0.05, 0.01, 0.001)");
                        a = Double.parseDouble(scanner.next());
                    }
                    lkk = math.lineKK();
                    if (isWrite>0)
                        writer.add("Линейный коэффициент корреляции", Double.toString(lkk));
                    System.out.printf("Линейный коэффициент корреляции %f.6", lkk);
                    if (math.valueofLKK(lkk, a-0.10<0.001?0:a-0.05<0.001?1:a-0.01<0.001?2:3)) {
                        System.out.println("Коэффициент значимый");
                        if (isWrite>0)
                            writer.add("Коэффициент значимый", "");
                    }
                    else{
                        System.out.println("Коэффициент незначимый");
                        if (isWrite>0)
                            writer.add("Коэффициент незначимый", "");
                    }
                    break;
                }
                case "1": {
                    System.out.printf("Коэффициент Спирмена равен %f.6", math.rangSpirman());
                    if (isWrite>0)
                        writer.add("Коэффициент Спирмена", Double.toString(math.rangSpirman()));
                }
                case "2": {
                    System.out.println("Выберите тип функции, к которой хотите приводить:\n" +
                            "0 - Линейная\n" +
                            "1 - Полиномиальная\n" +
                            "2 - Экспоненциальная\n" +
                            "3 - Логарифмическая\n" +
                            "5 - Гиперболическая");
                    int type = scanner.nextInt();
                    double a = 0.10;
                    if (lineX.length<30) {
                        System.out.println("Введите необходимый уровень значимости(0.10, 0.05, 0.01, 0.001)");
                        a = Double.parseDouble(scanner.next());
                    }
                    System.out.println("Хотите сохранить график?(0 - нет, 1 - да)");
                    byte graph = 0;
                    if (scanner.next().equals("1")){
                        graph = 1;
                        System.out.println("Если файла нет, то он будет создан, иначе перезаписан." +
                                "Если не получиться записать, то будет выдано соответствующее предупреждение." +
                                "При вводе отличного значения от предложенных двух запись в файл производиться не будет.");
                            System.out.println("Введите полный путь к файлу: ");
                            String path = scanner.next();
                            try{
                                FileWriter fr = new FileWriter(new File(path));
                                writer.setGraphPath(path);
                            }catch (Exception e){
                                System.out.println("Не получается записать... Записи графика не будет.");
                                graph = 0;
                            }
                    }
                    int res = math.regr(graph, isWrite, writer, type, a-0.10<0.001?0:a-0.05<0.001?1:a-0.01<0.001?2:3);
                    if (res == 1) {
                        System.out.println("Значимый только а0");
                        if (isWrite>0){
                            writer.add("Значимый только а0", "");
                        }
                    } else {
                        if (res == 2) {
                            System.out.println("Значимый только а1");
                            if (isWrite>0){
                                writer.add("Значимый только а1", "");
                            }
                        } else {
                            if (res == 3) {
                                System.out.println("Значимы оба");
                                if (isWrite>0){
                                    writer.add("Значимы оба", "");
                                }
                            } else {
                                System.out.println("Ни один не значим");
                                if (isWrite>0){
                                    writer.add("Ни один не значим", "");
                                }
                            }
                        }
                    }
                }
                case "exit": System.exit(0);
                case "help":
                    System.out.println("Следуйте инструкциям на экране.\n" +
                            "Ввод цифры выбирает необходимое действие.\n" +
                            "Всегда доступные команды help и exit.\n" +
                            "help - выводит помощь по работе с программой.\n" +
                            "exit - выход из программы.");
            }
        }
    }
}
