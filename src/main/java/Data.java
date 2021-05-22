public class Data {
    private int n;
    private double x[];
    private double y[];

    public void setX(double lineX[]){
        x = lineX;
    }
    public void setY(double lineY[]){
        y = lineY;
    }

    public double sumX(){
        double sum=0;
        for (int i=0;i<x.length;i++) {
            sum += x[i];
        }
        return sum;
    }
    public double sumXX(){
        double sum=0;
        for (int i=0;i<x.length;i++) {
            sum += x[i]*x[i];
        }
        return sum;
    }
    public double sumX3(){
        double sum=0;
        for (int i=0;i<x.length;i++) {
            sum += x[i]*x[i]*x[i];
        }
        return sum;
    }
    public double sumX4(){
        double sum=0;
        for (int i=0;i<x.length;i++) {
            sum += x[i]*x[i]*x[i]*x[i];
        }
        return sum;
    }
    public double sumY(){
        double sum=0;
        for (int i=0;i<y.length;i++) {
            sum += y[i];
        }
        return sum;
    }
    public double sumXY(){
        double sum=0;
        for (int i=0;i<x.length;i++) {
            sum += x[i]*y[i];
        }
        return sum;
    }
    public double sumXXY(){
        double sum=0;
        for (int i=0;i<x.length;i++) {
            sum += x[i]*x[i]*y[i];
        }
        return sum;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
