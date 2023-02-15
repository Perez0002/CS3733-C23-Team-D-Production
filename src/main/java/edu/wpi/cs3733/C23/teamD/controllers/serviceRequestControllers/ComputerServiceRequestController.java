package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.controllers.ServiceRequestController;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.ComputerServiceRequest;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class ComputerServiceRequestController extends ServiceRequestController
    implements ServiceRequestVBoxController {
  @FXML private ArrayList<String> deviceType;

  @FXML private MFXComboBox deviceTypeBox;
  @FXML private MFXComboBox urgencyBox;
  @FXML private MFXComboBox employeeBox;
  @FXML private MFXTextField descriptionBox;
  @FXML private MFXComboBox locationBox;

  public ComputerServiceRequestController() {
    deviceType = new ArrayList<String>();
    deviceType.add("Laptop Computer");
    deviceType.add("Desktop Computer");
    deviceType.add("Tablet");
    deviceType.add("Kiosk");
    deviceType.add("Other");
  }

  public void initialize() {
    deviceTypeBox.setItems(FXCollections.observableArrayList(deviceType));
  }

  public boolean submit() {

    if (descriptionBox.getText() != null
        && urgencyBox.getText() != null
        && employeeBox.getText() != null
        && descriptionBox.getText() != null
        && locationBox.getText() != null) {
      System.out.println("Submit computer request");

      ComputerServiceRequest computerServiceRequest =
          new ComputerServiceRequest(
              descriptionBox.getText(),
              employeeBox.getText(),
              urgencyBox.getText(),
              deviceTypeBox.getText(),
              locationBox.getText());
      FDdb.getInstance().saveServiceRequest(computerServiceRequest);

      return true;
    }
    return false;
  }

  @Override
  public Node getVBox() {
    return null;
  }

  // TODO: set the rest to clear
  public void clearComputerForms() {
    deviceTypeBox.clearSelection();
    urgencyBox.clearSelection();
    locationBox.clearSelection();
    descriptionBox.clear();
    employeeBox.clearSelection();
  }
}
