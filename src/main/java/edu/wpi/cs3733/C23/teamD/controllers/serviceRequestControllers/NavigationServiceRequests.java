package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class NavigationServiceRequests {

  public static ServiceRequestVBoxController navigate(
      final ServiceRequests serviceRequest, Pane requestFormHubPane) {
    final String filename = serviceRequest.getFilename();
    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      requestFormHubPane.getChildren().clear();
      System.out.println("LOAD " + filename);
      requestFormHubPane.getChildren().add(loader.load());
      System.out.println("CONTROLLER" + loader.getController());
      return loader.getController();
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
    final var resource = App.class.getResource("views/VBoxInjections/hubVBox.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    return loader.getController();
  }
}
