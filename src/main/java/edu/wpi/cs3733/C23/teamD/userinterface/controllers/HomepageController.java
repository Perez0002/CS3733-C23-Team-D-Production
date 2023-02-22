package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.NavigationServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequestVBoxController;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.*;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class HomepageController {

  @FXML private VBox homepageBorderPane;

  @FXML private MFXButton profileButton;
  @FXML private MFXButton helpButton;

  @FXML private Label welcomeText;

  @FXML private Label allPendingRequests;

  @FXML private Label pendingRequests;

  @FXML private Label movesTomorrow;

  @FXML private Label outgoingIncoming;

  @FXML private ScrollPane scrollPane;

  @FXML private Pane pane;

  @FXML private TableColumn<ServiceRequest, String> serviceRequests;
  @FXML private TableColumn<ServiceRequest, Date> serviceDates;
  @FXML private TableColumn<ServiceRequest, Integer> requestID;

  @FXML private TableView<ServiceRequest> serviceRequestHistory;

  @FXML
  public void setDetails() {
    ServiceRequest request = serviceRequestHistory.getSelectionModel().getSelectedItem();
    switchVBox(request);
  }

  private ServiceRequestVBoxController currentController;

  Pane getPane() {
    return pane;
  }

  @FXML
  public void initialize() {
    if (CurrentUserEnum._CURRENTUSER.getCurrentUser().getEmployeeType().equals("ADMIN")) {
      outgoingIncoming.setText("Outgoing Service Requests");
      serviceRequestTableOutgoing();
    } else if (CurrentUserEnum._CURRENTUSER.getCurrentUser().getEmployeeType().equals("STAFF")) {
      outgoingIncoming.setText("Incoming Service Requests");
      serviceRequestTableIncoming();
    }
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

    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();

    //    if (currentUser.getAccessLevel() == 0) {
    //      currentUserText.setText("please log in");
    //    } else {
    //      currentUserText.setText("You are logged in as: \n" + currentUser.getUsername());
    //    }

  }

  private void help() throws IOException {
    final var resource = App.class.getResource("views/VBoxInjections/ServiceRequestHubHelp.fxml");
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

  @FXML
  /**
   * checks label text. Sets label text to help text when button clicked, and sets label text to
   * empty when button clicked again
   */
  void toggleHelpText(ActionEvent event) {
    //    if (serviceRequestHelpText.getText().equals("") || bottomHelpText.getText().equals("")) {
    //      serviceRequestHelpText.setText("Click the buttons below to fill out service request
    // forms!");
    //      bottomHelpText.setText(
    //          "<-Use the leftmost button to exit the program  \n  Click the rightmost button to
    // remove the help text->");
    //    } else {
    //      serviceRequestHelpText.setText("");
    //      bottomHelpText.setText("");
    //    }
  }

  // takes in a service request enum, sets controller to it
  void switchVBox(ServiceRequest request) {
    if (request.getClass().equals(AVRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.AV_REQUEST, getPane(), request);
    } else if (request.getClass().equals(ComputerServiceRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.COMPUTER_REQUEST, getPane(), request);
    } else if (request.getClass().equals(PatientTransportRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.PATIENT_TRANSPORT, getPane(), request);
    } else if (request.getClass().equals(SanitationRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.SANITATION_REQUEST, getPane(), request);
    } else if (request.getClass().equals(SecurityServiceRequest.class)) {
      currentController =
          NavigationServiceRequests.navigateHomepage(
              ServiceRequests.SECURITY_REQUEST, getPane(), request);
    }
  }

  public void serviceRequestTableIncoming() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();
    ArrayList<ServiceRequest> employeeServiceRequests = new ArrayList<>();

    for (ServiceRequest s : genericServiceList) {
      System.out.println(s.getAssociatedStaff().getEmployeeID());
      System.out.println(currentuser.getEmployeeID());

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
    serviceRequestHistory.getColumns().stream()
        .forEach(
            (column) -> {
              double size = serviceRequestHistory.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              for (int i = 0; i < serviceRequestHistory.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                }
              }
            });
  }

  public void serviceRequestTableOutgoing() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();
    ArrayList<ServiceRequest> employeeServiceRequests = new ArrayList<>();

    for (ServiceRequest s : genericServiceList) {
      System.out.println(s.getAssociatedStaff().getEmployeeID());
      System.out.println(currentuser.getEmployeeID());

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
    serviceRequestHistory.getColumns().stream()
        .forEach(
            (column) -> {
              double size = serviceRequestHistory.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              for (int i = 0; i < serviceRequestHistory.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                }
              }
            });
  }
}
