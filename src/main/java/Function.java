public class Function {
    public double line(double a, double b, double x){
        return a*x+b;
    }
    public double quadra(double a, double b, double c, double x){
        return a*x*x+b*x+c;
    }
    public double expon(double a, double b, double x){
        return a*Math.pow(Math.E,b*x);
    }
    public double log(double a, double b, double x){
        return a*Math.log(x)+b;
    }
}
