package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

public class AnalyticsPageController implements Initializable {
  @FXML BarChart barChart;
  @FXML CategoryAxis xAxis;
  @FXML NumberAxis yAxis;
  @FXML PieChart pieChart;
  @FXML LineChart lineChart;
  @FXML MFXButton backButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_HUB));
    barChartInitialization();
    pieChartInitialization();
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

  public void pieChartInitialization() {
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();
    Double patientRequest = 0.0;
    Double avRequest = 0.0;
    Double securityRequest = 0.0;
    Double sanitationRequest = 0.0;
    Double computerRequest = 0.0;

    for (ServiceRequest s : genericServiceList) {
      if (s.getServiceRequestType() == null) {
        continue;
      } else if (s.getServiceRequestType() == "SanitationRequestData") {
        sanitationRequest += 1;
      } else if (s.getServiceRequestType() == "Security") {
        securityRequest += 1;
      } else if (s.getServiceRequestType() == "ComputerService") {
        computerRequest += 1;
      } else if (s.getServiceRequestType() == "AVRequest") {
        avRequest += 1;
      } else if (s.getServiceRequestType() == "PatientTransportData") {
        patientRequest += 1;
      }
    }

    double total = genericServiceList.size();
    total = 100;
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    if (total != 0) {
      pieChartData =
          FXCollections.observableArrayList(
              new PieChart.Data("Sanitation Request (" + 5 / total + " %)", 30),
              new PieChart.Data("Security Request (" + 30 / total + " %)", 40),
              new PieChart.Data("Computer Request (" + 5 / total + " %)", 50),
              new PieChart.Data("AV Request (" + 35 / total + " %)", 30),
              new PieChart.Data("Patient Transport Request (" + 45 / total + " %)", 50));
    }

    pieChart.setLegendVisible(false);
    pieChart.setLabelsVisible(true);
    pieChart.setClockwise(true);
    pieChart.setData(pieChartData);
  }
}
