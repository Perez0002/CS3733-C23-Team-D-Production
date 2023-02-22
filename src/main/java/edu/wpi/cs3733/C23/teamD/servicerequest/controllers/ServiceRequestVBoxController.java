package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import javafx.scene.Node;

public interface ServiceRequestVBoxController {

  public void clearTransportForms();

  public boolean submit();

  Node getVBox();

  public void setFieldsDisable(ServiceRequest serviceRequest);
}
