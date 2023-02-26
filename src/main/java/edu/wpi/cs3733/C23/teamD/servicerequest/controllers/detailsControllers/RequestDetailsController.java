package edu.wpi.cs3733.C23.teamD.servicerequest.controllers.detailsControllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import javafx.scene.Node;

public interface RequestDetailsController {
  Node getVBox();

  public void setFields(ServiceRequest serviceRequest);
}
