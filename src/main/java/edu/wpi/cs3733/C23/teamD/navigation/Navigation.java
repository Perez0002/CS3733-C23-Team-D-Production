package edu.wpi.cs3733.C23.teamD.navigation;

import edu.wpi.cs3733.C23.teamD.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Navigation {

  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();

    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      App.getRootPane().setCenter(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static void navigate(final Node scene) {
    try {
      App.getRootPane().setCenter(scene);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }
}
