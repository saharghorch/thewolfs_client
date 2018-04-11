package esprit.javafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class UiController implements Initializable {

    @FXML
    private AnchorPane parent;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillChart();
    }

    private void fillChart() {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Jan", 0));
        series.getData().add(new XYChart.Data("Feb", 50));
        series.getData().add(new XYChart.Data("Mar", 100));
        series.getData().add(new XYChart.Data("Apr", 200));
        series.getData().add(new XYChart.Data("Jun", 200));
        series.getData().add(new XYChart.Data("Jul", 300));
        series.getData().add(new XYChart.Data("Aug", 300));
        series.getData().add(new XYChart.Data("Sep", 400));
        series.getData().add(new XYChart.Data("Des", 500));

        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data("Jan", 30));
        series2.getData().add(new XYChart.Data("Feb", 70));
        series2.getData().add(new XYChart.Data("Mar", 130));
        series2.getData().add(new XYChart.Data("Apr", 100));
        series2.getData().add(new XYChart.Data("Jun", 250));
        series2.getData().add(new XYChart.Data("Jul", 250));
        series2.getData().add(new XYChart.Data("Aug", 220));
        series2.getData().add(new XYChart.Data("Sep", 420));
        series2.getData().add(new XYChart.Data("Des", 500));

        XYChart.Series series3 = new XYChart.Series();
        series3.getData().add(new XYChart.Data("Jan", 10));
        series3.getData().add(new XYChart.Data("Feb", 30));
        series3.getData().add(new XYChart.Data("Mar", 90));
        series3.getData().add(new XYChart.Data("Apr", 130));
        series3.getData().add(new XYChart.Data("Jun", 190));
        series3.getData().add(new XYChart.Data("Jul", 150));
        series3.getData().add(new XYChart.Data("Aug", 200));
        series3.getData().add(new XYChart.Data("Sep", 300));
        series3.getData().add(new XYChart.Data("Des", 400));

        XYChart.Series series4 = new XYChart.Series();
        series4.getData().add(new XYChart.Data("Jan", 0));
        series4.getData().add(new XYChart.Data("Feb", 50));
        series4.getData().add(new XYChart.Data("Mar", 100));
        series4.getData().add(new XYChart.Data("Apr", 140));
        series4.getData().add(new XYChart.Data("Jun", 165));
        series4.getData().add(new XYChart.Data("Jul", 210));
        series4.getData().add(new XYChart.Data("Aug", 280));
        series4.getData().add(new XYChart.Data("Sep", 350));
        series4.getData().add(new XYChart.Data("Des", 400));

        lineChart.getData().addAll(series, series2, series3, series4);
    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }
}
