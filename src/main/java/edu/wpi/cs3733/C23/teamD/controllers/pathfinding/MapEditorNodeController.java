package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.entities.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class MapEditorNodeController {

  /* Attributes */
  Node node; // contains pathfinding node information
  PopOver popover; // contains popover object
  javafx.scene.Node anchor; // anchor to anchor the popover to

  private MFXTextField xCoordTextField;
  private MFXTextField yCoordTextField;
  private MFXTextField longNameTextField;
  private MFXTextField shortNameTextField;
  private MFXTextField buildingTextField;
  private MFXTextField floorTextField;
  MFXButton closeButton;
  MFXButton submitButton;

  private EventHandler<ActionEvent> closeEvent;
  private EventHandler<ActionEvent> submitEvent;

  public MapEditorNodeController() {}

  public MapEditorNodeController(Node node, javafx.scene.Node anchor) {
    this.node = node;
    this.anchor = anchor;
    closeButton = new MFXButton();
    submitButton = new MFXButton();
    xCoordTextField = new MFXTextField();
    yCoordTextField = new MFXTextField();
    longNameTextField = new MFXTextField();
    shortNameTextField = new MFXTextField();
    buildingTextField = new MFXTextField();
    floorTextField = new MFXTextField();
    makeEditorNode(); // calls pseudo-constructor object
  }

  /**
   * Creates a popover object that contains necessary popup information (pane, text containing node
   * information). neither param nor return
   */
  private void makeEditorNode() {
    popover = new PopOver(); // creates PopOver container
    VBox vBox = new VBox(); // creates Pane object to place within PopOver

    vBox.setPrefWidth(200);
    vBox.setPrefHeight(490);
    VBox.setMargin(vBox, new Insets(5, 0, 5, 0));

    // Start format PopOver container

    popover.setCloseButtonEnabled(false);
    popover.setAutoHide(false);
    // End format PopOver container

    if (node != null) { // connection to database is intact
      popover.setTitle("Node Editor");

      Label xCoordLabel = new Label("X Coordinate");
      xCoordTextField.setPrefWidth(190);
      xCoordTextField.setText(Integer.toString(node.getXcoord()));
      VBox xCoordVBox = new VBox(xCoordLabel, xCoordTextField);
      VBox.setMargin(xCoordVBox, new Insets(5, 5, 5, 5));

      Label yCoordLabel = new Label("Y Coordinate");
      yCoordTextField.setPrefWidth(190);
      yCoordTextField.setText(Integer.toString(node.getYcoord()));
      VBox yCoordVBox = new VBox(yCoordLabel, yCoordTextField);
      VBox.setMargin(yCoordVBox, new Insets(5, 5, 5, 5));

      Label shortNameLabel = new Label("Short Name");
      shortNameTextField.setPrefWidth(190);
      shortNameTextField.setText(node.getShortName());
      VBox shortNameVBox = new VBox(shortNameLabel, shortNameTextField);
      VBox.setMargin(shortNameVBox, new Insets(5, 5, 5, 5));

      Label longNameLabel = new Label("Long Name");
      longNameTextField.setPrefWidth(190);
      longNameTextField.setText(node.getLongName());
      VBox longNameVBox = new VBox(longNameLabel, longNameTextField);
      VBox.setMargin(longNameVBox, new Insets(5, 5, 5, 5));

      Label buildingLabel = new Label("Building");
      buildingTextField.setPrefWidth(190);
      buildingTextField.setText(node.getBuilding());
      VBox buildingVBox = new VBox(buildingLabel, buildingTextField);
      VBox.setMargin(buildingVBox, new Insets(5, 5, 5, 5));

      Label floorLabel = new Label("Floor");
      floorTextField.setPrefWidth(190);
      floorTextField.setText(node.getFloor());
      VBox floorVBox = new VBox(floorLabel, floorTextField);
      VBox.setMargin(floorVBox, new Insets(5, 5, 10, 5));

      submitButton.getStyleClass().add("submitButton");
      submitButton.setText("Submit");
      closeButton.getStyleClass().add("cancelButton");
      closeButton.setText("Close");
      HBox buttonBox = new HBox(closeButton, submitButton);
      HBox.setMargin(buttonBox, new Insets(10, 5, 5, 5));
      HBox.setMargin(submitButton, new Insets(0, 0, 5, 5));
      HBox.setMargin(closeButton, new Insets(0, 5, 5, 0));
      buttonBox.setAlignment(Pos.CENTER);

      vBox.getChildren().add(xCoordVBox);
      vBox.getChildren().add(yCoordVBox);
      vBox.getChildren().add(shortNameVBox);
      vBox.getChildren().add(longNameVBox);
      vBox.getChildren().add(buildingVBox);
      vBox.getChildren().add(floorVBox);
      vBox.getChildren().add(buttonBox);

    } else { // connection to database is null (test case)
      popover.setTitle(String.format("%s Information", "nodeLongName"));
      Text nodeData =
          new Text(
              String.format(
                  "\n  xCoord: %d \n  yCoord: %d \n  floor: %d \n  shortName: %s \n  longName: %s ",
                  -10, -10, -10, "getShortName", "getLongName"));
      nodeData.setStyle("-fx-font-family: OpenSans");
      vBox.getChildren().add(nodeData); // adds text to pane
    }

    popover.setHeaderAlwaysVisible(true);

    popover.setContentNode(vBox); // sets pane as content of popover
  } // end makeEditorNode class

  void makePopupAppear() {
    popover.show(anchor); // shows Popup
  }

  void makePopupDisappear() {
    popover.hide(); // shows popup object
  }

  public void setOnClose(EventHandler<ActionEvent> event) {
    closeButton.setOnAction(event);
  }

  public void initialize() {}
}
