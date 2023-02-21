package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class NavigationDatabases {

  public static void navigate(
      final DatabasesFXML databasesFXML,
      Pane requestFormHubPane,
      DatabasesFXML addpage,
      BorderPane container,
      DatabaseHubController hub) {
    final String filename = databasesFXML.getFilename();
    try {
      final var addresource = App.class.getResource(addpage.getFilename());
      final FXMLLoader addloader = new FXMLLoader(addresource);
      container.setCenter(addloader.load());
      AddFormController addFormController = addloader.getController();

      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      requestFormHubPane.getChildren().clear();
      System.out.println("LOAD " + filename);
      requestFormHubPane.getChildren().add(loader.load());
      hub.setCurrentController(loader.getController());

      DatabaseController databaseController = loader.getController();
      databaseController.setAddFormController(addFormController);
      addFormController.setDatabaseController(databaseController);
      hub.setAddFormController(addFormController);
      hub.setCurrentController(databaseController);
      // App.getRootPane().setCenter(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}
