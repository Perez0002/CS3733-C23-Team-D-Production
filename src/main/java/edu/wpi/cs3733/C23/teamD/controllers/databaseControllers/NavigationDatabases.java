package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import edu.wpi.cs3733.C23.teamD.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class NavigationDatabases {

  public static void navigate(final DatabasesFXML databasesFXML, Pane requestFormHubPane) {
    final String filename = databasesFXML.getFilename();
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
