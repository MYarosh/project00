public class Mathematic {
    private double[] x, y, curX, curY;
    private double[][] studTable = new double[4][50];

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

    public int findNum(Double el, int a, int num){
        int i=0;
        if (a==0){
            for (i=0;i<x.length;++i){
                if (Math.abs(x[i]-el)<=0.0001){
                    if(num==0){
                        break;
                    }else{
                        --num;
                    }
                }
            }
        }else{
            for (i=0;i<x.length;++i){
                if (Math.abs(y[i]-el)<=0.0001){
                    if(num==0){
                        break;
                    }else{
                        --num;
                    }
                }
            }
        }
        return i;
    }

    public double[][] rang(){
        double[] rangX = new double[x.length], rangY = new double[y.length];
        for (int i=0; i<x.length; ++i){
            rangX[i] = x[i]; rangY[i] = y[i];
        }
        sort(rangX); sort(rangY);
        double[][] res = new double[2][x.length];
        int count =0, n=0;

        for (int i=0; i<x.length; ++i){
            if ((i<x.length-1)&&(Math.abs(rangX[i]-rangX[i+1]) <=0.0001)){
                ++n;
            }else{
                if (n>0){
                    double rang = (count+count+n)/2.0;
                    int h = count+n;
                    for (int c = count; c < h+1; ++c){
                        res[0][findNum(rangX[c],0,h-c)] = rang;
                    }
                    count = ++h;
                    n=0;
                }else {
                    res[0][findNum(rangX[i],0,0)] = count;
                    ++count;
                }
            }
        }
        count =0; n=0;
        for (int i=0; i<x.length; ++i){
            if ((i<x.length-1)&&(Math.abs(rangY[i]-rangY[i+1]) <=0.0001)){
                ++n;
            }else{
                if (n>0){
                    double rang = (count+count+n)/2.0;
                    int h = count+n;
                    for (int c = count; c < h+1; ++c){
                        res[1][findNum(rangY[c],1,h-c)] = rang;
                    }
                    count = ++h;
                    n=0;
                }else {
                    res[1][findNum(rangY[i],1,0)] = count;
                    ++count;
                }
            }
        }
        for (int i=0; i<x.length; ++i){
            curX[i] = rangX[i]; curY[i] = rangY[i];
        }
        return res;
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
        rang();
        graphic.draw(sol[0],sol[1],sol[2],type,"Graph",curX,function);
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
