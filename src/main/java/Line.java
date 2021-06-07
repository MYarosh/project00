public class Line {

    public static double[] solve(Data data){
        double sx=data.sumX(), sxx=data.sumXX(), sy=data.sumY(), sxy=data.sumXY();
        double a=(sxy*data.getN()-sx*sy)/(sxx*data.getN()-sx*sx), b=(sxx*sy-sx*sxy)/(sxx*data.getN()-sx*sx);
        double[] sol = new double[3];
        sol[0] = a;
        sol[1] = b;
        sol[2] = 0;
        return sol;
    }
}