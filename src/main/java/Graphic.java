import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;

import static java.lang.Math.abs;

public class Graphic {
    JFrame frame;
    JFreeChart chart;
    XYSeriesCollection xyDataset;

    public Graphic(double[] lineX, double[] lineY){
        frame = new JFrame("Graphic");
        xyDataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("");
        for (int i=0;i<lineX.length;i++){
            series.add(lineX[i],lineY[i]);
        }xyDataset.addSeries(series);
    }
    public void draw(double a, double b, double c, int func, String s, double[] lineX, Function function){
        XYSeries series = new XYSeries(s);
        double d = abs(lineX[0]-lineX[lineX.length-1])/1000;
        String title = "";
        for(double x = lineX[0]-3; x < lineX[lineX.length-1]+3; x+=d){
            switch (func){
                case 0:{
                    series.add(x,function.line(a,b,x));
                    title = "y = "+a+"*x+("+b+")";
                    break;
                }
                case 1:{
                    series.add(x,function.quadra(a,b,c,x));
                    title = "y = "+a+"*x^2+("+b+")+("+c+")";
                    break;
                }
                case 2:{
                    series.add(x,Math.pow(Math.E,function.line(a,b,x)));
                    title = "y = "+a+"*e^("+b+"*x)";
                    break;
                }
                case 3:{
                    series.add(x,function.log(a,b,x));
                    title = "y = "+a+"*ln(x)+("+b+")";
                    break;
                }
                case 4:{
                    series.add(x,Math.pow(Math.E,function.line(a,b,Math.log(x))));
                    title = "y = "+a+"*x^("+b+")";
                    break;
                }
                case 5:{
                    series.add(x,function.line(a,b,1/x));
                    title = "y = "+a+"/x+("+b+")";
                    break;
                }
            }
        }

        xyDataset.addSeries(series);
        chart = ChartFactory
                .createScatterPlot(title, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        // Помещаем график на фрейм
        {
            frame.getContentPane()
                    .add(new ChartPanel(chart));
            frame.setSize(400, 300);
            frame.show();
        }
    }

    public void writeChartToPDF(String fileName) {

        PdfWriter writer = null;

        Document document = new Document();

        try {
            float width = PageSize.A4.getWidth();
            float height = PageSize.A4.getHeight() / 2;
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    fileName));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height,
                    new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                    height);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}
