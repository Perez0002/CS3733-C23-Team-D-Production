package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lombok.Setter;
import org.controlsfx.control.PopOver;

public class MoveDisplayStackController {
  @FXML private StackPane stackPane;

  @Setter private MoveDisplayController moveDisplayController;

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

    final FXMLLoader popupLoader =
        new FXMLLoader(
            App.class.getResource("/edu/wpi/cs3733/C23/teamD/views/MoveDisplayPopup.fxml"));

    Parent root = popupLoader.load();
    // stage.setScene(scene);
    // stage.show();

    popOver = new PopOver();
    popOver.setContentNode(root);
    popOver.show(App.getPrimaryStage());

    moveDisplayController = loader.getController();
    moveDisplayController.setMoveDisplayStackController(this);
    MoveDisplayPopupController moveDisplayPopupController = popupLoader.getController();
    moveDisplayPopupController.setMoveDisplayStackController(this);
  }

  public void logout() {
    App.getRootPane().setLeft(null);
    popOver.hide();
    moveDisplayController.logout();
  }

  public void login() {
    popOver.show(App.getPrimaryStage());
  }

  public void setMove(Move m) {
    moveDisplayController.setLocation(m);
  }
}
