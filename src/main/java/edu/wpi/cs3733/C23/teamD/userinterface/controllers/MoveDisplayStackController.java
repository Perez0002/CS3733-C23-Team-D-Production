package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.PopOver;

public class MoveDisplayStackController {
  @FXML private StackPane stackPane;

  @Getter @Setter private MoveDisplayController moveDisplayController;

  private BorderPane mapPane = new BorderPane();
  private PopOver popOver;

  javafx.scene.Node anchor;

  @FXML
  public void initialize() throws IOException {
    mapPane.setCenter(MapFactory.startBuild().build(1));
    stackPane.getChildren().add(mapPane);

    final FXMLLoader loader =
        new FXMLLoader(App.class.getResource("/edu/wpi/cs3733/C23/teamD/views/MoveDisplay.fxml"));
    stackPane.getChildren().add(loader.load());

    // stage.setScene(scene);
    // stage.show();

    moveDisplayController = loader.getController();
    moveDisplayController.setMoveDisplayStackController(this);
  }

  public void logout() {
    App.getRootPane().setLeft(null);
    moveDisplayController.logout();
  }

  public void setMove(Move m) {
    moveDisplayController.setLocation(m);
  }
}
