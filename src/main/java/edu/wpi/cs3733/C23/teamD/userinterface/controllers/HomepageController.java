package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.NavigationServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.detailsControllers.RequestDetailsController;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.*;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.userinterface.entities.Notif;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

public class HomepageController {

  @FXML private VBox homepageBorderPane;

  @FXML private MFXButton profileButton;
  @FXML private MFXButton helpButton;

  @FXML private Label welcomeText;

  @FXML private Label firstStatText;

  @FXML private Label secondStatText;

  @FXML private Label thirdStatText;

  @FXML private Label firstStat;

  @FXML private Label secondStat;

  @FXML private Label thirdStat;

  @FXML private Label outgoingIncoming;

  @FXML private Pane pane;

  @FXML private TableColumn<ServiceRequest, String> serviceRequests;
  @FXML private TableColumn<ServiceRequest, Date> serviceDates;
  @FXML private TableColumn<ServiceRequest, Integer> requestID;

  @FXML private TableView<ServiceRequest> serviceRequestHistory;
  @FXML private TableView<Notif> notifTable;

  @FXML private TableColumn<Notif, String> notification;

  @FXML
  public void setDetails() {
    ServiceRequest request = serviceRequestHistory.getSelectionModel().getSelectedItem();
    switchVBox(request);
  }

  private RequestDetailsController currentController;

  Pane getPane() {
    return pane;
  }

  @FXML
  public void initialize() {
    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    if (currentUser.getEmployeeType().equals("ADMIN")) {
      outgoingIncoming.setText("Outgoing Service Requests");
      serviceRequestTableOutgoing();
      initializeAdminStats();
    } else if (CurrentUserEnum._CURRENTUSER.getCurrentUser().getEmployeeType().equals("STAFF")) {
      outgoingIncoming.setText("Incoming Service Requests");
      serviceRequestTableIncoming();
      initializeStaffStats();
    }

    notificationTableHandling();

    currentController =
        NavigationServiceRequests.navigateHomepage(
            ServiceRequests.TEMPLATE_REQUEST_DETAILS, getPane(), new ServiceRequest());

    welcomeText.setText("Hello, " + CurrentUserEnum._CURRENTUSER.getCurrentUser().getFirstName());
    profileButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PROFILE_PAGE));

    helpButton.setOnMouseClicked(
        event -> {
          try {
            help();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void initializeStaffStats() {
    String toDo = counterYourToDo();
    String done = counterYourDone();
    String total = Integer.toString(Integer.parseInt(toDo) + Integer.parseInt(done));
    firstStatText.setText("Total Assigned Requests");
    secondStatText.setText("To Do Requests");
    thirdStatText.setText("Completed Requests");
    firstStat.setText(total);
    secondStat.setText(toDo);
    thirdStat.setText(done);
  }

  public void notificationTableHandling() {
    ArrayList<Move> allmoves = FDdb.getInstance().getAllMoves();
    ArrayList<Notif> futureMoveList = new ArrayList<>();

    long millis = System.currentTimeMillis();
    Date todayDate = new java.sql.Date(millis);
    for (Move m : allmoves) {
      Move aNewMove = new Move();
      if (m.getMoveDate().after(todayDate)) {
        aNewMove.setMoveDate(m.getMoveDate());
        aNewMove.setLocation(m.getLocation());
        aNewMove.setNode(m.getNode());
        if (m.getMessage() == null) {
          aNewMove.setMessage("null");
        } else {
          aNewMove.setMessage(m.getMessage());
        }
        Notif notif =
            new Notif(
                aNewMove.getLocation().getShortName()
                    + " will be moving on "
                    + aNewMove.getMoveDate()
                    + ", here is the attached message: \n"
                    + aNewMove.getMessage());
        futureMoveList.add(0, notif);
      }
    }

    ObservableList<Notif> notificationList = FXCollections.observableArrayList(futureMoveList);

    notification.setCellValueFactory(new PropertyValueFactory<Notif, String>("notification"));
    notifTable.setItems(notificationList);
  }

  private void initializeAdminStats() {
    firstStat.setText(counterTotal());
    secondStat.setText(counterYourOutgoing());
    thirdStat.setText(counterCompletedMoves());
  }

  private String counterCompletedMoves() {
    ArrayList<Move> allMoves = FDdb.getInstance().getAllMoves();
    int count = 0;
    for (Move m : allMoves) {
      if (m.getMoveDate().before(new Date())) count++;
    }
    return Integer.toString(count);
  }

  private String counterTotal() {
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();

    int count = 0;
    for (ServiceRequest s : genericServiceList) {
      if (s.getStat().equals(ServiceRequest.Status.PROCESSING)) count++;
    }
    return Integer.toString(count);
  }

  private String counterYourOutgoing() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();

    int count = 0;
    for (ServiceRequest s : genericServiceList) {
      if (s.getAssociatedStaff().getEmployeeID() == currentuser.getEmployeeID()
          && s.getStat().equals(ServiceRequest.Status.PROCESSING)) count++;
    }
    return Integer.toString(count);
  }

  private String counterYourToDo() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();

    int count = 0;
    for (ServiceRequest s : genericServiceList) {
      if (s.getAssociatedStaff().getEmployeeID() == currentuser.getEmployeeID()
          && s.getStat().equals(ServiceRequest.Status.PROCESSING)) count++;
    }
    return Integer.toString(count);
  }

  private String counterYourDone() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();

    int count = 0;
    for (ServiceRequest s : genericServiceList) {
      if (s.getAssociatedStaff().getEmployeeID() == currentuser.getEmployeeID()
          && s.getStat().equals(ServiceRequest.Status.DONE)) count++;
    }
    return Integer.toString(count);
  }

  private void help() throws IOException {
    final var resource = App.class.getResource("views/VBoxInjections/HomepageHelp.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    PopOver popover = new PopOver(loader.load());
    popover.setArrowSize(0);
    popover.setTitle("Help");
    popover.show(App.getPrimaryStage());
  }

  @FXML
  /** user open menubutton, clicks Exit, and it closes the window. */
  void closeApplication(ActionEvent event) {
    homepageBorderPane.getScene().getWindow().hide();
  }

  // takes in a service request enum, sets controller to it
  void switchVBox(ServiceRequest request) {
    if (request.getClass().equals(AVRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.AV_REQUEST_DETAILS, getPane(), request);
    } else if (request.getClass().equals(ComputerServiceRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.COMPUTER_REQUEST_DETAILS, getPane(), request);
    } else if (request.getClass().equals(PatientTransportRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.PATIENT_TRANSPORT_DETAILS, getPane(), request);
    } else if (request.getClass().equals(SanitationRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.SANITATION_REQUEST_DETAILS, getPane(), request);
    } else if (request.getClass().equals(SecurityServiceRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.SECURITY_REQUEST_DETAILS, getPane(), request);
    }
  }

  public void serviceRequestTableIncoming() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();
    ArrayList<ServiceRequest> employeeServiceRequests = new ArrayList<>();

    for (ServiceRequest s : genericServiceList) {

      if (s.getAssociatedStaff() == null) {
        continue;
      } else if ((s.getAssociatedStaff().getEmployeeID() == currentuser.getEmployeeID())
          && (s.getStat().equals(ServiceRequest.Status.PROCESSING))) {
        employeeServiceRequests.add(s);
      }
    }

    ObservableList<ServiceRequest> listserviceRequests =
        FXCollections.observableArrayList(employeeServiceRequests);

    serviceRequests.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("serviceRequestType"));
    serviceDates.setCellValueFactory(new PropertyValueFactory<ServiceRequest, Date>("dateAndTime"));
    requestID.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, Integer>("serviceRequestId"));

    serviceRequestHistory.setItems(listserviceRequests);
  }

  public void serviceRequestTableOutgoing() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();
    ArrayList<ServiceRequest> employeeServiceRequests = new ArrayList<>();

    for (ServiceRequest s : genericServiceList) {

      if (s.getAssociatedStaff() == null) {
        continue;
      } else if (s.getStat().equals(ServiceRequest.Status.PROCESSING)) {
        employeeServiceRequests.add(s);
      }
    }

    ObservableList<ServiceRequest> listserviceRequests =
        FXCollections.observableArrayList(employeeServiceRequests);

    serviceRequests.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("serviceRequestType"));
    serviceDates.setCellValueFactory(new PropertyValueFactory<ServiceRequest, Date>("dateAndTime"));
    requestID.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, Integer>("serviceRequestId"));

    serviceRequestHistory.setItems(listserviceRequests);
  }
}
