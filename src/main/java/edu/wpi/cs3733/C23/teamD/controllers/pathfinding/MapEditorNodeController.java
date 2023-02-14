package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.entities.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class MapEditorNodeController {

  /* Attributes */
  Node node; // contains pathfinding node information
  PopOver popover; // contains popover object
  javafx.scene.Node anchor;
  MFXButton closeButton;
  MFXButton submitButton;

  MapEditorNodeController(Node node, javafx.scene.Node anchor) {
    this.node = node;
    this.anchor = anchor;
    makeEditorNode(); // calls pseudo-constructor object
  }

  /**
   * Creates a popover object that contains necessary popup information (pane, text containing node
   * information). neither param nor return
   */
  private void makeEditorNode() {
    popover = new PopOver(); // creates PopOver container
    VBox pane = new VBox(); // creates Pane object to place within PopOver
    pane.setAlignment(Pos.CENTER);
    pane.setFillWidth(true);

    closeButton = new MFXButton();
    closeButton.setText("Close");

    submitButton = new MFXButton();
    submitButton.setText("Submit");

    // Start format PopOver container
    popover.setCloseButtonEnabled(false);
    popover.setAutoHide(false);
    // End format PopOver container

    if (node != null) { // connection to database is intact
      popover.setTitle("Node Editor");

      HBox buttonBox = new HBox(closeButton, submitButton);

      Label xCoordLabel = new Label("XCoordinate");
      MFXTextField xCoordTextField = new MFXTextField("" + node.getXcoord());
      VBox XCoordBox = new VBox(xCoordLabel, xCoordTextField);
      XCoordBox.setFillWidth(true);

      VBox YCoordBox = new VBox(new Label("YCoordinate"), new MFXTextField("" + node.getYcoord()));
      VBox shortNameBox =
          new VBox(new Label("Short Name"), new MFXTextField("" + node.getShortName()));
      VBox longNameBox =
          new VBox(new Label("Long Name"), new MFXTextField("" + node.getLongName()));
      VBox buildingBox = new VBox(new Label("Building"), new MFXTextField("" + node.getBuilding()));
      VBox floorBox = new VBox(new Label("Floor"), new MFXTextField("" + node.getFloor()));

      pane.getChildren().add(XCoordBox);
      pane.getChildren().add(YCoordBox);
      pane.getChildren().add(shortNameBox);
      pane.getChildren().add(longNameBox);
      pane.getChildren().add(buildingBox);
      pane.getChildren().add(floorBox);
      pane.getChildren().add(buttonBox);

    } else { // connection to database is null (test case)
      popover.setTitle(String.format("%s Information", "nodeLongName"));
      Text nodeData =
          new Text(
              String.format(
                  "\n  xCoord: %d \n  yCoord: %d \n  floor: %d \n  shortName: %s \n  longName: %s ",
                  -10, -10, -10, "getShortName", "getLongName"));
      nodeData.setStyle("-fx-font-family: OpenSans");
      pane.getChildren().add(nodeData); // adds text to pane
    }

    popover.setHeaderAlwaysVisible(true);

    popover.setContentNode(pane); // sets pane as content of popover
  } // end makeEditorNode class

  void makePopupAppear() {
    popover.show(anchor); // shows Popup
  }

  void makePopupDisappear() {
    popover.hide(); // shows popup object
  }

  void setOnClose(EventHandler<ActionEvent> event) {
    closeButton.setOnAction(event);
    ((Circle) anchor).setFill(Color.rgb(1, 58, 117));
  }

  public void initialize() {}
} // end class
