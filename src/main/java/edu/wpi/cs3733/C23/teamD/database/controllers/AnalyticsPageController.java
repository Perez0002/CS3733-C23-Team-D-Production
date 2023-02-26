package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

public class AnalyticsPageController implements Initializable {
  @FXML BarChart barChart;
  @FXML CategoryAxis xAxis;
  @FXML NumberAxis yAxis;

  @FXML MFXButton backButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_HUB));
  }

  public void barChartInitialization() {
    XYChart.Series series1 = new XYChart.Series();
    series1.getData().add(new XYChart.Data("Monday", 0.2));
    series1.getData().add(new XYChart.Data("Tuesday", 0.1));
    series1.getData().add(new XYChart.Data("Wednesday", 0.1));
    series1.getData().add(new XYChart.Data("Thursday", 0.1));
    series1.getData().add(new XYChart.Data("Friday", 0.1));
    series1.getData().add(new XYChart.Data("Saturday", 0.3));
    series1.getData().add(new XYChart.Data("Sunday", 0.1));

    barChart.getData().addAll(series1);
  }
}
