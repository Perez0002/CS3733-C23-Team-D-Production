package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers.ServiceRequestVBoxController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class ServiceRequestTableController implements ServiceRequestVBoxController {

  @FXML private BorderPane ServiceRequestTableBorderPane;

  public ServiceRequestTableController() {}

  public void initialize() {};

  // returns the table
  public Node getVBox() {
    return ServiceRequestTableBorderPane;
  }

  public void setVisible() {
    if (ServiceRequestTableBorderPane.isVisible()) {
      ServiceRequestTableBorderPane.setVisible(false);
    } else {
      ServiceRequestTableBorderPane.setVisible(true);
    }
  }
}
