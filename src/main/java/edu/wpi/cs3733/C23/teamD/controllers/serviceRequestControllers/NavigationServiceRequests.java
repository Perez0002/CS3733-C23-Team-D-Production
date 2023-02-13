package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class NavigationServiceRequests {

  public static void navigate(final ServiceRequests serviceRequest, Pane requestFormHubPane) {
    final String filename = serviceRequest.getFilename();
    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      requestFormHubPane.getChildren().clear();
      System.out.println("LOAD " + filename);
      requestFormHubPane.getChildren().add(loader.load());
      // App.getRootPane().setCenter(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}
