public class Mathematic {
    private double[] x, y, curX, curY;
    private double[][] studTable = new double[3][50];

    public Mathematic(double[] x, double[] y){
        this.x = x;
        this.y = y;
        curX = new double[x.length]; curY = new double[y.length];
        Student.make(studTable);
    }

    public double sum(double[] n){
        double sum=0;
        for (double ni: n
             ) { sum += ni;
        }
        return sum;
    }

    public double avg(double[] n){
        return sum(n)/n.length;
    }

    public double lineKK(){
        double sum1 = 0, sum0 = 0;
        double sum2 = 0;
        double xAvg = avg(x);
        double yAvg = avg(y);
        for (int i=0; i< x.length; ++i){
            sum1 += (x[i]-xAvg)*(y[i]-yAvg);
            sum0 += Math.pow((x[i]-xAvg), 2);
            sum2 += Math.pow((y[i]-yAvg), 2);
        }
        return sum1/(Math.sqrt(sum0*sum2));
    }

    /*public int checkLKK(double lkk){
        if (lkk > 0){
            if (lkk < 0.3){
                return 1;
            }else if (lkk <= 0.7){
                return 2;
            }else if (lkk < 1){
                return 3;
            }else return 10;
        }else if (lkk < 0){
            if (lkk > -0.3){
                return -1;
            }else if (lkk >= -0.7){
                return -2;
            }else if (lkk > -1){
                return -3;
            }else return 10;
        }else return 0;
    }*/

    public boolean valueofLKK(double lkk, int a){
        if (!(x.length<30)){
            return Math.abs(lkk)/((1-Math.pow(lkk, 2))/Math.sqrt(x.length)) - 3 <0.0001;
        }else{
            return Math.abs(lkk)/(Math.sqrt(1-Math.pow(lkk, 2))/Math.sqrt(x.length-2)) - studTable[a][x.length-2] <0.0001;
        }
    }

    public static void sort(double[] arr){
        for(int i = arr.length-1 ; i > 0 ; --i){
            for(int j = 0 ; j < i ; j++){
            if( arr[j] > arr[j+1] ){
                double tmp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = tmp;
            }
        }
    }
}

    public double rangSpirman(){
        double[] rangX = new double[x.length], rangY = new double[y.length];
        for (int i=0; i<x.length; ++i){
            curX[i] = x[i]; curY[i] = y[i];
        }
        sort(curX); sort(curY);
        for (int i=0; i<x.length; ++i){
            for (int j=0; j<x.length; ++j){
                if (curX[i] == x[j]){
                    rangX[j] = i;
                    break;
                }
                if (curY[i] == y[j]){
                    rangY[j] = i;
                    break;
                }
            }
        }
        int sum = 0;
        for (int i=0; i<x.length; ++i){
            sum += Math.pow(rangX[i] - rangY[i], 2);
        }
        return 1- (6*sum)/(x.length*(Math.pow(x.length, 2) - 1));
    }

    public int regr(byte graph, byte isWrite, Writer writer, int type, int a){
        Function function = new Function();
        Data data = new Data();
        data.setX(x); data.setY(y); data.setN(x.length);
        double[] sol = new double[3];
        switch (type){
            case 0: {
                sol = Line.solve(data);
                break;
            }
            case 1: {
                sol = Quadra.solve(data);
                break;
            }
            case 2: {// экспоненциальная
                for (int i=0; i<x.length; ++i){
                    curY[i] = Math.log(y[i]);
                }
                data.setY(curY); data.setX(x);
                sol = Quadra.solve(data);
                break;
            }
            case 3: {// логарифмическая
                for (int i=0; i<x.length; ++i){
                    curY[i] = Math.log(y[i]);
                    curX[i] = Math.log(x[i]);
                }
                data.setY(curY); data.setX(curX);
                Quadra.solve(data);
                break;
            }
            case 5: {// гиперболическая
                for (int i=0; i<x.length; ++i){
                    curX[i] = 1/x[i];
                }
                data.setY(y); data.setX(curX);
                Quadra.solve(data);
            }
        }
        if (isWrite>0){
            writer.add(Double.toString(sol[0]), Double.toString(sol[1]), Double.toString(sol[2]));
        }
        Graphic graphic = new Graphic(x,y);
        graphic.draw(sol[0],sol[1],sol[2],type,String.format("%.4fx+%.4f",sol[0],sol[1]),x,function);
        if (graph>0){
            graphic.writeChartToPDF(writer.getGraphPath());
        }
        /* **** Проверка на адекватность модели **** */
        double ost = 0.0, dx = 0.0;
        double xAvg = avg(x), yAvg = avg(y);
        for (int i=0; i<x.length; ++i){
            ost += Math.pow((y[i]-yAvg), 2);
            dx += Math.pow((x[i]-xAvg), 2);
        }
        ost = Math.sqrt(ost/x.length);
        dx = Math.sqrt(dx/x.length);
        int result = 0;
        double t0 = sol[0]/(ost/Math.sqrt(x.length-2)),
                t1 = sol[0]/(ost/(dx*Math.sqrt(x.length-2)));
        if (x.length > 30){
            if (t0-3<0.0001) result = 1;
            if (t1-3<0.0001) result = 2;
            if (t1-3<0.0001 && t0-3<0.0001) result = 2;
        }else {
            if (t0 - studTable[a][x.length-2] <0.0001) result = 1;
            if (t1 - studTable[a][x.length-2] <0.0001) result = 2;
            if (t1 - studTable[a][x.length-2] <0.0001 &&t0 - studTable[a][x.length-2] <0.0001) result = 3;
        }
        return result;
    }


}
