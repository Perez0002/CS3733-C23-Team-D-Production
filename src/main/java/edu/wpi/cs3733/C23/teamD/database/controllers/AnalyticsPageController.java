package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.awt.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
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
  @FXML AreaChart chart3;
  @FXML CategoryAxis xaxis1;
  @FXML NumberAxis yaxis1;
  @FXML MFXButton backButton;
  @FXML private MFXFilterComboBox<String> timeFilter;
  @FXML private MFXFilterComboBox<String> employeeFilter;
  ArrayList<String> times = new ArrayList<>();
  ArrayList<String> employees = new ArrayList<>();

  public AnalyticsPageController() {
    times.add("Overall");
    times.add("Past Week");
    times.add("Today");
    employees.add("All");
    ArrayList<Employee> employeeList = FDdb.getInstance().getAllEmployees();
    for (Employee e : employeeList) {
      employees.add(e.getFirstName() + " " + e.getLastName());
    }
  }

  @FXML javafx.scene.control.Label completedLabel;
  @FXML javafx.scene.control.Label pendingLabel;
  @FXML javafx.scene.control.Label unassignedLabel;
  @FXML javafx.scene.control.Label urgentLabel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    timeFilter.setItems(FXCollections.observableArrayList(times));
    timeFilter.setValue("Overall");
    timeFilter.setText("Overall");
    employeeFilter.setItems(FXCollections.observableArrayList(employees));
    employeeFilter.setValue("All");
    employeeFilter.setText("All");
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_HUB));
    setLabels();
    barChartInitialization();
    pieChartInitialization();
    stackedAreaChartInitialization();
  }

  public void setLabels() {
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();
    Integer completed = 0;
    Integer processing = 0;
    Integer blank = 0;
    Integer urgency = 0;
    for (ServiceRequest s : genericServiceList) {
      if (!employeeFilter.getValue().equals("All")) {
        String name =
            s.getAssociatedStaff().getFirstName() + " " + s.getAssociatedStaff().getLastName();
        if (name.equals(employeeFilter.getValue())) {
          if (timeFilter.getValue().equals("Overall")) {
            if (s.getStat().equals(ServiceRequest.Status.DONE)) {
              completed += 1;
            }
            if (s.getStat().equals(ServiceRequest.Status.PROCESSING)) {
              processing += 1;
              if (s.getUrgency().equals("High")) {
                urgency += 1;
              }
            }
            if (s.getStat().equals(ServiceRequest.Status.BLANK)) {
              blank += 1;
              if (s.getUrgency().equals("High")) {
                urgency += 1;
              }
            }
          } else if (timeFilter.getValue().equals("Past Week")) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            if (s.getDateAndTime().after(cal.getTime())) {
              if (s.getStat().equals(ServiceRequest.Status.DONE)) {
                completed += 1;
              }
              if (s.getStat().equals(ServiceRequest.Status.PROCESSING)) {
                processing += 1;
                if (s.getUrgency().equals("High")) {
                  urgency += 1;
                }
              }
              if (s.getStat().equals(ServiceRequest.Status.BLANK)) {
                blank += 1;
                if (s.getUrgency().equals("High")) {
                  urgency += 1;
                }
              }
            }
          } else if (timeFilter.getValue().equals("Today")) {
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (dateFormat.format(s.getDateAndTime()).equals(dateFormat.format(cal.getTime()))) {
              if (s.getStat().equals(ServiceRequest.Status.DONE)) {
                completed += 1;
              }
              if (s.getStat().equals(ServiceRequest.Status.PROCESSING)) {
                processing += 1;
                if (s.getUrgency().equals("High")) {
                  urgency += 1;
                }
              }
              if (s.getStat().equals(ServiceRequest.Status.BLANK)) {
                blank += 1;
                if (s.getUrgency().equals("High")) {
                  urgency += 1;
                }
              }
            }
          }
        }
      }
      if (employeeFilter.getValue().equals("All")) {
        if (timeFilter.getValue().equals("Overall")) {
          if (s.getStat().equals(ServiceRequest.Status.DONE)) {
            completed += 1;
          }
          if (s.getStat().equals(ServiceRequest.Status.PROCESSING)) {
            processing += 1;
            if (s.getUrgency().equals("High")) {
              urgency += 1;
            }
          }
          if (s.getStat().equals(ServiceRequest.Status.BLANK)) {
            blank += 1;
            if (s.getUrgency().equals("High")) {
              urgency += 1;
            }
          }
        } else if (timeFilter.getValue().equals("Past Week")) {
          Calendar cal = Calendar.getInstance();
          cal.add(Calendar.DATE, -7);
          if (s.getDateAndTime().after(cal.getTime())) {
            if (s.getStat().equals(ServiceRequest.Status.DONE)) {
              completed += 1;
            }
            if (s.getStat().equals(ServiceRequest.Status.PROCESSING)) {
              processing += 1;
              if (s.getUrgency().equals("High")) {
                urgency += 1;
              }
            }
            if (s.getStat().equals(ServiceRequest.Status.BLANK)) {
              blank += 1;
              if (s.getUrgency().equals("High")) {
                urgency += 1;
              }
            }
          }
        } else if (timeFilter.getValue().equals("Today")) {
          Calendar cal = Calendar.getInstance();
          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          if (dateFormat.format(s.getDateAndTime()).equals(dateFormat.format(cal.getTime()))) {
            if (s.getStat().equals(ServiceRequest.Status.DONE)) {
              completed += 1;
            }
            if (s.getStat().equals(ServiceRequest.Status.PROCESSING)) {
              processing += 1;
              if (s.getUrgency().equals("High")) {
                urgency += 1;
              }
            }
            if (s.getStat().equals(ServiceRequest.Status.BLANK)) {
              blank += 1;
              if (s.getUrgency().equals("High")) {
                urgency += 1;
              }
            }
          }
        }
      }
      completedLabel.setText(completed.toString());
      pendingLabel.setText(processing.toString());
      unassignedLabel.setText(blank.toString());
      urgentLabel.setText(urgency.toString());
    }
  }

  public void barChartInitialization() {
    XYChart.Series series1 = new XYChart.Series();
    Double Sunday = 0.0;
    Double Monday = 0.0;
    Double Tuesday = 0.0;
    Double Wednesday = 0.0;
    Double Thursday = 0.0;
    Double Friday = 0.0;
    Double Saturday = 0.0;

    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();

    for (ServiceRequest s : genericServiceList) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(s.getDateAndTime());
      int day = cal.get(Calendar.DAY_OF_WEEK);
      if (day == 1) {
        Sunday += 1;
      } else if (day == 2) {
        Monday += 1;
      } else if (day == 3) {
        Tuesday += 1;
      } else if (day == 4) {
        Wednesday += 1;
      } else if (day == 5) {
        Thursday += 1;
      } else if (day == 6) {
        Friday += 1;
      } else if (day == 7) {
        Saturday += 1;
      }
    }

    double total = genericServiceList.size();

    series1.getData().add(new XYChart.Data("Sunday", Sunday / total));
    series1.getData().add(new XYChart.Data("Monday", Monday / total));
    series1.getData().add(new XYChart.Data("Tuesday", Tuesday / total));
    series1.getData().add(new XYChart.Data("Wednesday", Wednesday / total));
    series1.getData().add(new XYChart.Data("Thursday", Thursday / total));
    series1.getData().add(new XYChart.Data("Friday", Friday / total));
    series1.getData().add(new XYChart.Data("Saturday", Saturday / total));

    barChart.setLegendVisible(false);
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
      } else if (s.getServiceRequestType().equals("SanitationRequestData")) {
        sanitationRequest += 1;
      } else if (s.getServiceRequestType().equals("Security")) {
        securityRequest += 1;
      } else if (s.getServiceRequestType().equals("ComputerService")) {
        computerRequest += 1;
      } else if (s.getServiceRequestType().equals("AVRequest")) {
        avRequest += 1;
      } else if (s.getServiceRequestType().equals("PatientTransportData")) {
        patientRequest += 1;
      }
    }

    double total = genericServiceList.size();
    DecimalFormat df = new DecimalFormat("#.##");

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    if (total != 0) {
      pieChartData =
          FXCollections.observableArrayList(
              new PieChart.Data(
                  "Sanitation Request (" + df.format(sanitationRequest / total) + " %)",
                  sanitationRequest),
              new PieChart.Data(
                  "Security Request (" + df.format(securityRequest / total) + " %)",
                  securityRequest),
              new PieChart.Data(
                  "Computer Request (" + df.format(computerRequest / total) + " %)",
                  computerRequest),
              new PieChart.Data("AV Request (" + df.format(avRequest / total) + " %)", avRequest),
              new PieChart.Data(
                  "Patient Transport Request (" + df.format(patientRequest / total) + " %)",
                  patientRequest));
    }

    pieChart.setLegendVisible(false);
    pieChart.setLabelsVisible(true);
    pieChart.setClockwise(true);
    pieChart.setData(pieChartData);
  }

  public void stackedAreaChartInitialization() {
    ArrayList<LocationName> locations = FDdb.getInstance().getAllLocationNames();

    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();

    xaxis1.setCategories(
        FXCollections.<String>observableArrayList(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"));

    Double January = 0.0;
    Double February = 0.0;
    Double March = 0.0;
    Double April = 0.0;
    Double May = 0.0;
    Double June = 0.0;
    Double July = 0.0;
    Double August = 0.0;
    Double September = 0.0;
    Double October = 0.0;
    Double November = 0.0;
    Double December = 0.0;

    for (ServiceRequest s : genericServiceList) {
      LocalDate currentDate =
          s.getDateAndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      System.out.println(currentDate);
      int month = currentDate.getMonthValue();
      System.out.println(month);
      if (month == 1) {
        January += 1;
      } else if (month == 2) {
        February += 1;
      } else if (month == 3) {
        March += 1;
      } else if (month == 4) {
        April += 1;
      } else if (month == 5) {
        May += 1;
      } else if (month == 6) {
        June += 1;
      } else if (month == 7) {
        July += 1;
      } else if (month == 8) {
        August += 1;
      } else if (month == 9) {
        September += 1;
      } else if (month == 10) {
        October += 1;
      } else if (month == 11) {
        November += 1;
      } else if (month == 12) {
        December += 1;
      }
    }

    XYChart.Series series1 = new XYChart.Series();
    series1.setName("Total Requests");

    series1.getData().add(new XYChart.Data<String, Number>("January", January));
    series1.getData().add(new XYChart.Data<String, Number>("February", February));
    series1.getData().add(new XYChart.Data<String, Number>("March", March));
    series1.getData().add(new XYChart.Data<String, Number>("April", April));
    series1.getData().add(new XYChart.Data<String, Number>("May", May));
    series1.getData().add(new XYChart.Data<String, Number>("June", June));
    series1.getData().add(new XYChart.Data<String, Number>("July", July));
    series1.getData().add(new XYChart.Data<String, Number>("August", August));
    series1.getData().add(new XYChart.Data<String, Number>("September", September));
    series1.getData().add(new XYChart.Data<String, Number>("October", October));
    series1.getData().add(new XYChart.Data<String, Number>("November", November));
    series1.getData().add(new XYChart.Data<String, Number>("December", December));

    chart3.setLegendVisible(false);
    chart3.setAnimated(false);
    chart3.getData().addAll(series1);
  }
}
