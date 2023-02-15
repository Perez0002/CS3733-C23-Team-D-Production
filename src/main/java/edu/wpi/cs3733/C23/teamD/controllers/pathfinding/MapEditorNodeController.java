package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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
  javafx.scene.Node anchor; // anchor to anchor the popover to

  private MFXTextField xCoordTextField;
  private MFXTextField yCoordTextField;
  private MFXTextField longNameTextField;
  private MFXTextField shortNameTextField;
  private MFXTextField buildingTextField;
  private MFXTextField floorTextField;
  private MFXTextField typeTextField;
  MFXButton closeButton;
  MFXButton submitButton;
  MFXButton deleteButton;

  private EventHandler<ActionEvent> closeEvent;
  private EventHandler<ActionEvent> submitEvent;

  public MapEditorNodeController() {}

  public MapEditorNodeController(Node node, javafx.scene.Node anchor, double xPos, double yPos) {
    this.node = node;
    this.anchor = anchor;
    closeButton = new MFXButton();
    submitButton = new MFXButton();
    deleteButton = new MFXButton();
    xCoordTextField = new MFXTextField();
    yCoordTextField = new MFXTextField();
    longNameTextField = new MFXTextField();
    shortNameTextField = new MFXTextField();
    buildingTextField = new MFXTextField();
    floorTextField = new MFXTextField();
    typeTextField = new MFXTextField();
    makeEditorNode(xPos, yPos); // calls pseudo-constructor object
  }

  /**
   * Creates a popover object that contains necessary popup information (pane, text containing node
   * information). neither param nor return
   */
  private void makeEditorNode(double xPos, double yPos) {
    popover = new PopOver(); // creates PopOver container
    VBox fullContainer = new VBox();
    VBox vBox = new VBox(); // creates Pane object to place within PopOver
    MFXScrollPane scrollPane = new MFXScrollPane(vBox);
    scrollPane.setFitToWidth(true);
    scrollPane.getStyleClass().add("mfx-text-field");

    Point2D point = anchor.getParent().localToScene(node.getXcoord(), node.getYcoord());

    if (xPos > App.getPrimaryStage().getScene().getWidth() / 2) {
      if (yPos > App.getPrimaryStage().getScene().getHeight() / 2) {
        popover.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP); // Why stupid??
      } else {
        popover.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
      }
    } else {
      if (yPos > App.getPrimaryStage().getScene().getHeight() / 2) {
        popover.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP); // Why stupid??

      } else {
        popover.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
      }
    }

    vBox.setPrefWidth(200);
    vBox.setPrefHeight(200);
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
      VBox.setMargin(floorVBox, new Insets(5, 5, 5, 5));

      Label typeLabel = new Label("Type");
      typeTextField.setPrefWidth(190);
      typeTextField.setText(node.getLocationType());
      VBox typeVBox = new VBox(typeLabel, typeTextField);
      VBox.setMargin(typeVBox, new Insets(5, 5, 10, 5));

      submitButton.getStyleClass().add("submitButton");
      submitButton.setText("Submit");
      closeButton.getStyleClass().add("cancelButton");
      closeButton.setText("Close");
      deleteButton.getStyleClass().add("deleteButton");
      deleteButton.setText("Delete");
      HBox buttonBox = new HBox(closeButton, deleteButton);
      HBox.setMargin(buttonBox, new Insets(10, 5, 5, 5));
      HBox.setMargin(deleteButton, new Insets(0, 0, 5, 5));
      HBox.setMargin(closeButton, new Insets(0, 5, 5, 0));
      buttonBox.setAlignment(Pos.CENTER);

      VBox submitButtonHolder = new VBox(submitButton);
      VBox.setMargin(submitButtonHolder, new Insets(5, 5, 5, 5));
      submitButtonHolder.setAlignment(Pos.CENTER);

      vBox.getChildren().add(xCoordVBox);
      vBox.getChildren().add(yCoordVBox);
      vBox.getChildren().add(shortNameVBox);
      vBox.getChildren().add(longNameVBox);
      vBox.getChildren().add(buildingVBox);
      vBox.getChildren().add(floorVBox);
      vBox.getChildren().add(typeVBox);

      fullContainer.getChildren().add(scrollPane);
      fullContainer.getChildren().add(buttonBox);
      fullContainer.getChildren().add(submitButtonHolder);
      VBox.setMargin(scrollPane, new Insets(5, 0, 5, 0));
      VBox.setMargin(buttonBox, new Insets(5, 0, 5, 0));

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

    popover.setContentNode(fullContainer); // sets pane as content of popover
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

  public void setOnDelete(HashMap<Node, MapEditorNodeController> list) {
    deleteButton.setOnAction(
        event -> {
          FDdb database = FDdb.getInstance();

          database.deleteNode(node);

          anchor.setVisible(false);
          anchor.setManaged(false);

          list.get(node).makePopupDisappear();
          list.remove(node, anchor);

          node = null;
        });
  }

  public void setOnSubmit(HashMap<Node, MapEditorNodeController> list) {
    submitButton.setOnAction(
        event -> {
          FDdb database = FDdb.getInstance();

          if (xCoordTextField.getText().isEmpty()) {
            return;
          }
          if (yCoordTextField.getText().isEmpty()) {
            return;
          }
          if (longNameTextField.getText().isEmpty()) {
            return;
          }
          if (shortNameTextField.getText().isEmpty()) {
            return;
          }
          if (buildingTextField.getText().isEmpty()) {
            return;
          }
          if (floorTextField.getText().isEmpty()) {
            return;
          }
          if (typeTextField.getText().isEmpty()) {
            return;
          }

          node.setXcoord(Integer.parseInt(xCoordTextField.getText()));
          node.setYcoord(Integer.parseInt(yCoordTextField.getText()));
          node.setFloor(floorTextField.getText());
          node.setBuilding(buildingTextField.getText());

          node.getLocation().setLongName(longNameTextField.getText());
          node.getLocation().setShortName(shortNameTextField.getText());
          node.getLocation().setLocationType(typeTextField.getText());

          try {
            System.out.println("UPDATED");
            database.updateNodePK(node);
          } catch (Exception e) {
            e.printStackTrace();
            database.saveNode(node);
          }

          try {
            System.out.println("UPDATED LOCATION");
            database.updateLocationName(node.getLocation());
          } catch (Exception e) {
            e.printStackTrace();
            database.saveLocationName(node.getLocation());
          }

          ((Circle) anchor).setFill(Color.rgb(1, 58, 117));
          ((Circle) anchor).setCenterX(node.getXcoord());
          ((Circle) anchor).setCenterY(node.getYcoord());
          if (list.get(node) != null) {
            list.get(node).makePopupDisappear();
            list.remove(node, anchor);
          }
        });
  }

  public void initialize() {}
}
