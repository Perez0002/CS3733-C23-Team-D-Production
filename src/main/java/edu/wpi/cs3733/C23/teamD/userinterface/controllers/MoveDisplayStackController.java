package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.Pathfinder;
import java.io.IOException;
import java.util.ArrayList;
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
  private Pathfinder pathfinder;

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
    ArrayList<MapNode> mapNodes = new ArrayList<MapNode>();
    PathNode temp = new PathNode(m.getNode(), m.getLocation());
    mapNodes.add(new MapNode(temp));

    mapPane.setCenter(
        MapFactory.startBuild().withNodes(mapNodes).build(getFloor(m.getNode().getFloor())));
  }

  private int getFloor(String floor) {
    if (floor.equals("L1")) {
      return 1;
    } else if (floor.equals("L2")) {
      return 2;
    } else if (floor.equals("1")) {
      return 2;
    } else if (floor.equals("2")) {
      return 4;
    } else {
      return 5;
    }
  }
}
