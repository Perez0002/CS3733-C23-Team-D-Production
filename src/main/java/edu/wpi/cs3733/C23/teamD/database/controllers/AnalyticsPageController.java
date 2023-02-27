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
  @FXML StackedAreaChart chart3;
  @FXML CategoryAxis xaxis1;
  @FXML NumberAxis yaxis1;
  @FXML MFXButton backButton;
  @FXML private MFXFilterComboBox<String> timeFilter;
  @FXML private MFXFilterComboBox<String> employeeFilter;
  @FXML private MFXFilterComboBox<String> urgencyFilter;
  ArrayList<String> times = new ArrayList<>();
  ArrayList<String> employees = new ArrayList<>();
  ArrayList<String> urgency = new ArrayList<>();

  public AnalyticsPageController() {
    times.add("Overall");
    times.add("Past Week");
    times.add("Today");
    employees.add("All");
    ArrayList<Employee> employeeList = FDdb.getInstance().getAllEmployees();
    for (Employee e : employeeList) {
      employees.add(e.getFirstName() + " " + e.getLastName());
    }
    urgency.add("High");
    urgency.add("Medium");
    urgency.add("Low");
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
        System.out.println("yeah");
        String name =
            s.getAssociatedStaff().getFirstName() + " " + s.getAssociatedStaff().getLastName();
        System.out.println(name);
        System.out.println(employeeFilter.getText());
        if (name.equals(employeeFilter.getValue())) {
          System.out.println("babe");
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
            System.out.println("Past Week2");
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
            System.out.println("Today2");
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
          System.out.println("Overall");
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
          System.out.println("Past Week");
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
          System.out.println("today");
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

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -7);
    Date todate1 = cal.getTime();
    cal.add(Calendar.DATE, -6);
    Date todate2 = cal.getTime();
    cal.add(Calendar.DATE, -5);
    Date todate3 = cal.getTime();
    cal.add(Calendar.DATE, -4);
    Date todate4 = cal.getTime();
    cal.add(Calendar.DATE, -3);
    Date todate5 = cal.getTime();
    cal.add(Calendar.DATE, -2);
    Date todate6 = cal.getTime();
    cal.add(Calendar.DATE, -1);
    Date todate7 = cal.getTime();
    cal.add(Calendar.DATE, 0);
    Date todate8 = cal.getTime();

    String fromdate1 = dateFormat.format(todate1);
    String fromdate2 = dateFormat.format(todate2);
    String fromdate3 = dateFormat.format(todate3);
    String fromdate4 = dateFormat.format(todate4);
    String fromdate5 = dateFormat.format(todate5);
    String fromdate6 = dateFormat.format(todate6);
    String fromdate7 = dateFormat.format(todate7);

    XYChart.Series series1 = new XYChart.Series();
    series1.setName("Total Requests");
    XYChart.Series series2 = new XYChart.Series();
    series2.setName("Completed Requests");

    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();

    int completedRequests1 = 0,
        completedRequests2 = 0,
        completedRequests3 = 0,
        completedRequests4 = 0,
        completedRequests5 = 0,
        completedRequests6 = 0,
        completedRequests7 = 0;
    int totalRequests1 = 0,
        totalRequests2 = 0,
        totalRequests3 = 0,
        totalRequests4 = 0,
        totalRequests5 = 0,
        totalRequests6 = 0,
        totalRequests7 = 0;

    for (ServiceRequest s : genericServiceList) {
      if (s.getDateAndTime().before(todate2)) {
        totalRequests1 += 1;
        if (s.getStat().equals(ServiceRequest.Status.DONE)) {
          completedRequests1 += 1;
        }
      }
      if (s.getDateAndTime().before(todate3)) {
        totalRequests2 += 1;
        if (s.getStat().equals(ServiceRequest.Status.DONE)) {
          completedRequests2 += 1;
        }
      }
      if (s.getDateAndTime().before(todate4)) {
        totalRequests3 += 1;
        if (s.getStat().equals(ServiceRequest.Status.DONE)) {
          completedRequests3 += 1;
        }
      }
      if (s.getDateAndTime().before(todate5)) {
        totalRequests4 += 1;
        if (s.getStat().equals(ServiceRequest.Status.DONE)) {
          completedRequests4 += 1;
        }
      }
      if (s.getDateAndTime().before(todate6)) {
        totalRequests5 += 1;
        if (s.getStat().equals(ServiceRequest.Status.DONE)) {
          completedRequests5 += 1;
        }
      }
      if (s.getDateAndTime().before(todate7)) {
        totalRequests6 += 1;
        if (s.getStat().equals(ServiceRequest.Status.DONE)) {
          completedRequests6 += 1;
        }
      }
      if (s.getDateAndTime().before(todate8)) {
        totalRequests7 += 1;
        if (s.getStat().equals(ServiceRequest.Status.DONE)) {
          completedRequests7 += 1;
        }
      }
    }
    series1.getData().add(new XYChart.Data<String, Number>(fromdate1, totalRequests1));
    series1.getData().add(new XYChart.Data<String, Number>(fromdate2, totalRequests2));
    series1.getData().add(new XYChart.Data<String, Number>(fromdate3, totalRequests3));
    series1.getData().add(new XYChart.Data<String, Number>(fromdate4, totalRequests4));
    series1.getData().add(new XYChart.Data<String, Number>(fromdate5, totalRequests5));
    series1.getData().add(new XYChart.Data<String, Number>(fromdate6, totalRequests6));
    series1.getData().add(new XYChart.Data<String, Number>(fromdate7, totalRequests7));

    series2.getData().add(new XYChart.Data<String, Number>(fromdate1, completedRequests1));
    series2.getData().add(new XYChart.Data<String, Number>(fromdate2, completedRequests2));
    series2.getData().add(new XYChart.Data<String, Number>(fromdate3, completedRequests3));
    series2.getData().add(new XYChart.Data<String, Number>(fromdate4, completedRequests4));
    series2.getData().add(new XYChart.Data<String, Number>(fromdate5, completedRequests5));
    series2.getData().add(new XYChart.Data<String, Number>(fromdate6, completedRequests6));
    series2.getData().add(new XYChart.Data<String, Number>(fromdate7, completedRequests7));

    xaxis1.setCategories(
        FXCollections.<String>observableArrayList(
            fromdate1, fromdate2, fromdate3, fromdate4, fromdate5, fromdate6, fromdate7));
    xaxis1.setAnimated(false);
    chart3.getData().addAll(series1, series2);
  }

  //
  //  @FXML private MFXComboBox mfxComboBox;
  //  @FXML private ArrayList<String> urgency;
  //
  //  public UrgencySelectorBoxController() {
  //    urgency = new ArrayList<String>();
  //    urgency = new ArrayList<>();
  //    urgency.add("High");
  //    urgency.add("Medium");
  //    urgency.add("Low");
  //  }
  //
  //  public void initialize() {
  //    mfxComboBox.setItems(FXCollections.observableArrayList(urgency));
  //  }
  //
  //  public String getUrgency() {
  //    return mfxComboBox.getText();
  //  }
  //
  //  public void clearForm() {
  //    mfxComboBox.setValue(null);
  //  }
  //
  //  public void setText(String s) {
  //    mfxComboBox.setText(s);
  //  }
}
