public class Quadra {
    public static double[] solve(Data data){
        double a11=data.getN(), a21=data.sumX(), a31=data.sumXX(), b1=data.sumY(),
                a12=data.sumX(), a22=data.sumXX(), a32=data.sumX3(), b2=data.sumXY(),
                a13=data.sumXX(), a23=data.sumX3(), a33=data.sumX4(), b3=data.sumXXY();
        double d=a11*(a22*a33-a23*a32)-a21*(a12*a33-a13*a32)+a31*(a12*a23-a13*a22);
        double a0 = (b1*(a22*a33-a23*a32)-a21*(b2*a33-b3*a32)+a31*(b2*a23-b3*a22))/d,
                a1 = (a11*(b2*a33-b3*a32)-b1*(a12*a33-a13*a32)+a31*(a12*b3-a13*b2))/d,
                a2 = (a11*(a22*b3-a23*b2)-a21*(a12*b3-a13*b2)+b1*(a12*a23-a13*a22))/d;
        double[] sol = new double[3];
        sol[0] = a0;
        sol[1] = a1;
        sol[2] = a2;
        return sol;
    }
}
