import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Manager {


    public static void main(String[] args) throws IOException {
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
            if (path.endsWith(".xls")) {
                isWrite = writer.setPath(path);
            }else{
                System.out.println("It's not a .xls path to file!!!");
            }
        }
        while (cycle) {
            System.out.println("Для вывода помощи введите команду help.");
            System.out.println("Выберите тип ввода(0 - из файла, 1 - с клавиатуры): ");
            switch (scanner.next()) {
                case ("0"): {
                    System.out.println("Введите полный путь к файлу: ");
                    String path = scanner.next();
                    if (path.endsWith(".csv")) {
                        isWrite = writer.setPath(path);
                    }else{
                        System.out.println("It's not a .csv path to file!!!");
                    }
                    FileReader fr = null;
                    int lines = countLines(path);
                    if (lines < 0){
                        System.out.println("Smth wrong with file");
                        break;
                    }
                    try {//C:\Users\Maxim\Downloads\1kor_regr.csv
                        fr = new FileReader(path);
                        BufferedReader reader = new BufferedReader(fr);
                        lineX = new double[lines];
                        lineY = new double[lines];
                        for (int i=0; i < lines; ++i) {
                            line = reader.readLine().split(";");
                            System.out.println(Arrays.toString(line));
                            lineX[i] = Double.parseDouble(line[0]);
                            lineY[i] = Double.parseDouble(line[1]);
                        }
                        n = lines;
                        cycle = false;
                        break;
                    }catch(Exception e){
                        e.printStackTrace();
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
                            try{
                                lineX[i] = Double.parseDouble(scanner.next());
                            }catch (Exception e){
                                --i;
                                System.out.println("Wrong! Try again!");
                            }
                        }
                        System.out.println("Введите значения Y в строку через пробел:");
                        lineY = new double[n];
                        for (int i = 0; i < lineY.length; i++) {
                            try{
                                lineY[i] = Double.parseDouble(scanner.next());
                            }catch (Exception e){
                                --i;
                                System.out.println("Wrong! Try again!");
                            }
                        }
                        cycle = false;
                        break;
                    }catch (Exception e){
                        System.out.println("You wrote smth wrong!");
                    }
                }
                case "exit": System.exit(0);
                case "help": {
                    System.out.println("Следуйте инструкциям на экране.\n" +
                            "Ввод цифры выбирает необходимое действие.\n" +
                            "Всегда доступные команды help и exit.\n" +
                            "help - выводит помощь по работе с программой.\n" +
                            "exit - выход из программы.");
                    break;
                }
                default: {
                    System.out.println("Wrong answer. Try again.");
                    break;
                }
            }

        }
        Mathematic math = new Mathematic(lineX, lineY);
        cycle = true;
        while (true){
            System.out.println("Выберите необходимое действие:\n" +
                    "0 - Вычисление линейного коэффициента корреляции и проверка его на значимость;\n" +
                    "1 - Вычисление коэффициента Спирмена;\n" +
                    "2 - Ранги\n"+
                    "3 - Рассчет уравнения регрессии, проверка его на адекватность и вывод графика уравнения.");
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
                    System.out.printf("Линейный коэффициент корреляции %.6f\n", lkk);
                    if (math.valueofLKK(lkk, Math.abs(a-0.10)<0.001?0:Math.abs(a-0.05)<0.001?1:Math.abs(a-0.01)<0.001?2:3)) {
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
                    System.out.printf("Коэффициент Спирмена равен %.6f\n", math.rangSpirman());
                    String s = "";
                    if (math.rangSpirman() > 0){
                        s = "Связь прямая";
                        System.out.println("Связь прямая");
                    }else if (math.rangSpirman() < 0){
                        s = "Связь обратная";
                        System.out.println("Связь обратная");
                    }else{
                        s = "Связи нет";
                        System.out.println("Связи нет");
                    }
                    if (isWrite>0){
                        writer.add("Коэффициент Спирмена", Double.toString(math.rangSpirman()));
                        writer.add(s, "");
                    }
                    break;
                }
                case "2":{
                    double[][] res = math.rang();
                    System.out.println("Ранги");
                    System.out.println("X");
                    for (int i=0; i< lineX.length; ++i){
                        System.out.printf("%.3f ",res[0][i]);
                    }
                    System.out.println();
                    System.out.println("Y");
                    for (int i=0; i< lineX.length; ++i){
                        System.out.printf("%.3f ",res[1][i]);
                    }
                    System.out.println();
                    if (isWrite>0){
                        writer.add("Ранги","");
                        writer.add("X","Y");
                        for (int i=0; i< lineX.length; ++i){
                            writer.add(Double.toString(res[0][i]), Double.toString(res[1][i]));
                        }
                    }
                    break;
                }
                case "3": {
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
                            if (path.endsWith(".pdf")) {
                                isWrite = writer.setPath(path);
                            }else{
                               System.out.println("It's not a .pdf path to file!!!");
                            }
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
                    break;
                }
                case "exit": {
                    writer.write();
                    System.exit(0);
                }
                case "help":
                    System.out.println("Следуйте инструкциям на экране.\n" +
                            "Ввод цифры выбирает необходимое действие.\n" +
                            "Всегда доступные команды help и exit.\n" +
                            "help - выводит помощь по работе с программой.\n" +
                            "exit - выход из программы.");
            }
        }
    }

    public static int countLines(String path) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(path))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
