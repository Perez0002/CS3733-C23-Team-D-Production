package edu.wpi.cs3733.C23.teamD.mapeditor.util;

import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEdge;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.controlsfx.control.PopOver;

public class PopupFactory {
  private boolean editable;
  private boolean deletable;
  private boolean drawArrows;

  private Node anchor;
  private MapNode mapNode;
  private MapEdge mapEdge;
  private EventHandler<ActionEvent> submitEvent;
  private EventHandler<ActionEvent> nextEvent;
  private EventHandler<ActionEvent> prevEvent;

  private EventHandler<ActionEvent> deleteEvent;
  private EventHandler<ActionEvent> closeEvent;
  private String directions;

  private PopupFactory() {
    this.editable = false;
    this.deletable = false;
    this.anchor = null;
    this.mapNode = null;
    this.mapEdge = null;
    this.submitEvent = event -> {};
    this.deleteEvent = event -> {};
    this.closeEvent = event -> {};
    this.drawArrows = false;
  }

  /** @return new PopupFactory to chain off of */
  public static PopupFactory startBuild() {
    PopupFactory returnable = new PopupFactory();
    return returnable;
  }

  /**
   * Makes the fields in the popup editable and allows submission
   *
   * @return the PopupFactory with these changes
   */
  public PopupFactory editable() {
    this.editable = true;
    return this;
  }
  /**
   * Allow for the drawing of next/prev arrows on the popup
   *
   * @return the PopupFactory with these changes
   */
  public PopupFactory withArrows() {
    this.drawArrows = true;
    return this;
  }

  /**
   * Makes the popup able to delete selected nodes
   *
   * @return the PopupFactory with these changes
   */
  public PopupFactory deletable() {
    this.deletable = true;
    return this;
  }

  /**
   * @param anchor the javafx Node to anchor this popup to
   * @return the PopupFactory with these changes
   */
  public PopupFactory anchor(Node anchor) {
    this.anchor = anchor;
    return this;
  }

  /**
   * @param mapNode the MapNode to get information from and potentially mutate
   * @return the PopupFactory with these changes
   */
  public PopupFactory mapNode(MapNode mapNode) {
    this.mapNode = mapNode;
    return this;
  }

  /**
   * @param mapEdge the MapEdge to get information from and potentially mutate
   * @return the PopupFactory with these changes
   */
  public PopupFactory mapEdge(MapEdge mapEdge) {
    this.mapEdge = mapEdge;
    return this;
  }

  /**
   * @param submitEvent event to occur when submission button is pressed
   * @return the PopupFactory with these changes
   */
  public PopupFactory submitEvent(EventHandler submitEvent) {
    this.submitEvent = submitEvent;
    return this;
  }

  /**
   * @param deleteEvent event to occur when the delete button is pressed
   * @return the PopupFactory with these changes
   */
  public PopupFactory deleteEvent(EventHandler deleteEvent) {
    this.deleteEvent = deleteEvent;
    return this;
  }

  /**
   * @param closeEvent the event to occur when the close button is pressed
   * @return the PopupFactory with these changes
   */
  public PopupFactory closeEvent(EventHandler closeEvent) {
    this.closeEvent = closeEvent;
    return this;
  }

  public PopupFactory nextEvent(EventHandler nextEvent) {
    this.nextEvent = nextEvent;
    return this;
  }

  public PopupFactory prevEvent(EventHandler prevEvent) {
    this.prevEvent = prevEvent;
    return this;
  }

  public PopupFactory assignDirections(String directions) {
    this.directions = directions;
    return this;
  }

  /**
   * Builds the PopOver from the factory
   *
   * @return A PopOver with the settings provided to the factory
   */
  public PopOver build() {
    PopOver popover = new PopOver();

    popover.setCloseButtonEnabled(false);
    popover.setAutoHide(false);
    popover.setArrowSize(0);

    MFXButton closeButton = null;
    MFXButton deleteButton = null;
    MFXButton submitButton = null;
    MFXButton nextButton = null;
    MFXButton prevButton = null;

    if (this.mapNode != null) {
      MFXTextField xCoordTextField = new MFXTextField();
      MFXTextField yCoordTextField = new MFXTextField();
      MFXTextField longNameTextField = new MFXTextField();
      MFXTextField shortNameTextField = new MFXTextField();
      MFXTextField buildingTextField = new MFXTextField();
      MFXTextField floorTextField = new MFXTextField();
      MFXTextField typeTextField = new MFXTextField();

      VBox fullContainer = new VBox();
      VBox vBox = new VBox(); // creates Pane object to place within PopOver
      MFXScrollPane scrollPane = new MFXScrollPane(vBox);
      scrollPane.setFitToWidth(true);
      scrollPane.getStyleClass().add("mfx-text-field");

      vBox.setPrefWidth(200);
      vBox.setPrefHeight(200);
      VBox.setMargin(vBox, new Insets(5, 0, 5, 0));

      StringConverter<Number> converter = new NumberStringConverter();
      if (this.editable) {
        popover.setTitle("Node Editor");
        Label xCoordLabel = new Label("X Coordinate");
        xCoordTextField.setPrefWidth(190);
        Bindings.bindBidirectional(xCoordTextField.textProperty(), mapNode.getNodeX(), converter);
        VBox xCoordVBox = new VBox(xCoordLabel, xCoordTextField);
        VBox.setMargin(xCoordVBox, new Insets(5, 5, 5, 5));

        Label yCoordLabel = new Label("Y Coordinate");
        yCoordTextField.setPrefWidth(190);
        Bindings.bindBidirectional(yCoordTextField.textProperty(), mapNode.getNodeY(), converter);
        VBox yCoordVBox = new VBox(yCoordLabel, yCoordTextField);
        VBox.setMargin(yCoordVBox, new Insets(5, 5, 5, 5));

        Label shortNameLabel = new Label("Short Name");
        shortNameTextField.setPrefWidth(190);
        shortNameTextField.textProperty().bindBidirectional(mapNode.getNodeShortName());
        VBox shortNameVBox = new VBox(shortNameLabel, shortNameTextField);
        VBox.setMargin(shortNameVBox, new Insets(5, 5, 5, 5));

        Label longNameLabel = new Label("Long Name");
        longNameTextField.setPrefWidth(190);
        longNameTextField.textProperty().bindBidirectional(mapNode.getNodeLongName());
        VBox longNameVBox = new VBox(longNameLabel, longNameTextField);
        VBox.setMargin(longNameVBox, new Insets(5, 5, 5, 5));

        Label buildingLabel = new Label("Building");
        buildingTextField.setPrefWidth(190);
        buildingTextField.textProperty().bindBidirectional(mapNode.getNodeBuilding());
        VBox buildingVBox = new VBox(buildingLabel, buildingTextField);
        VBox.setMargin(buildingVBox, new Insets(5, 5, 5, 5));

        Label floorLabel = new Label("Floor");
        floorTextField.setPrefWidth(190);
        floorTextField.textProperty().bindBidirectional(mapNode.getNodeFloor());
        VBox floorVBox = new VBox(floorLabel, floorTextField);
        VBox.setMargin(floorVBox, new Insets(5, 5, 5, 5));

        Label typeLabel = new Label("Type");
        typeTextField.setPrefWidth(190);
        typeTextField.textProperty().bindBidirectional(mapNode.getNodeType());
        VBox typeVBox = new VBox(typeLabel, typeTextField);
        VBox.setMargin(typeVBox, new Insets(5, 5, 10, 5));
        submitButton = new MFXButton();
        submitButton.getStyleClass().add("submitButton");
        submitButton.setText("Submit");
        submitButton.setOnAction(submitEvent);

        vBox.getChildren().add(xCoordVBox);
        vBox.getChildren().add(yCoordVBox);
        vBox.getChildren().add(shortNameVBox);
        vBox.getChildren().add(longNameVBox);
        vBox.getChildren().add(buildingVBox);
        vBox.getChildren().add(floorVBox);
        vBox.getChildren().add(typeVBox);

      } else {
        popover.setTitle(mapNode.getNodeLongName().getValue());
        xCoordTextField.setEditable(false);
        yCoordTextField.setEditable(false);
        buildingTextField.setEditable(false);
        floorTextField.setEditable(false);
        longNameTextField.setEditable(false);
        shortNameTextField.setEditable(false);
        typeTextField.setEditable(false);
      }

      if (this.deletable) {
        deleteButton = new MFXButton();
        deleteButton.getStyleClass().add("deleteButton");
        deleteButton.setText("Delete");
        deleteButton.setOnAction(deleteEvent);
      }
      if (this.deletable) {
        deleteButton = new MFXButton();
        deleteButton.getStyleClass().add("deleteButton");
        deleteButton.setText("Delete");
        deleteButton.setOnAction(deleteEvent);
      }
      if (this.drawArrows) {
        nextButton = new MFXButton();
        prevButton = new MFXButton();

        nextButton.setText("Next");
        prevButton.setText("Prev");

        nextButton.getStyleClass().add("cancelButton");
        prevButton.getStyleClass().add("cancelButton");

        nextButton.setOnAction(nextEvent);
        prevButton.setOnAction(prevEvent);

        Label directions = new Label(this.directions);
        directions.setPrefWidth(190);
        directions.setWrapText(true);
        VBox directionsVbox = new VBox(directions);
        VBox.setMargin(directionsVbox, new Insets(5, 5, 5, 5));
        vBox.getChildren().add(directionsVbox);
      }

      closeButton = new MFXButton();
      closeButton.getStyleClass().add("cancelButton");
      closeButton.setText("Cancel");
      closeButton.setOnAction(closeEvent);

      HBox buttonBox;

      if (deleteButton != null) {
        buttonBox = new HBox(closeButton, deleteButton);
      } else if (nextButton != null && prevButton != null) {
        buttonBox = new HBox(prevButton, closeButton, nextButton);
      } else {
        buttonBox = new HBox(closeButton);
      }

      HBox.setMargin(buttonBox, new Insets(10, 5, 5, 5));
      if (deleteButton != null) {
        HBox.setMargin(deleteButton, new Insets(0, 0, 5, 5));
      }
      if (nextButton != null) {
        HBox.setMargin(prevButton, new Insets(0, 5, 5, 0));
        HBox.setMargin(nextButton, new Insets(0, 5, 5, 0));
      }
      HBox.setMargin(closeButton, new Insets(0, 5, 5, 0));
      buttonBox.setAlignment(Pos.CENTER);

      VBox submitButtonHolder;

      if (this.editable) {
        submitButtonHolder = new VBox(submitButton);
      } else {
        submitButtonHolder = new VBox();
      }

      VBox.setMargin(submitButtonHolder, new Insets(5, 5, 5, 5));
      submitButtonHolder.setAlignment(Pos.CENTER);

      fullContainer.getChildren().add(scrollPane);
      fullContainer.getChildren().add(buttonBox);
      fullContainer.getChildren().add(submitButtonHolder);
      VBox.setMargin(scrollPane, new Insets(5, 0, 5, 0));
      VBox.setMargin(buttonBox, new Insets(5, 0, 5, 0));

      popover.setHeaderAlwaysVisible(true);
      popover.setContentNode(fullContainer);
    }

    if (this.mapEdge != null) {
      VBox fullContainer = new VBox();

      if (this.deletable) {
        deleteButton = new MFXButton();
        deleteButton.getStyleClass().add("deleteButton");
        deleteButton.setText("Delete");
        deleteButton.setOnAction(deleteEvent);
        fullContainer.getChildren().add(deleteButton);
      }

      closeButton = new MFXButton();
      closeButton.getStyleClass().add("cancelButton");
      closeButton.setText("Cancel");
      fullContainer.getChildren().add(closeButton);

      closeButton.setOnAction(closeEvent);
      deleteButton.setOnAction(deleteEvent);

      VBox.setMargin(closeButton, new Insets(5, 5, 5, 5));
      VBox.setMargin(deleteButton, new Insets(5, 5, 5, 5));
      VBox.setMargin(fullContainer, new Insets(5, 5, 5, 5));
      popover.setHeaderAlwaysVisible(false);
      popover.setContentNode(fullContainer);
    }

    if (this.anchor != null) {

      try {
        popover.show(anchor);
      } catch (Exception e) {
        System.out.println(anchor.getBoundsInLocal());
        System.out.println(anchor.getLayoutBounds());
        System.out.println(anchor.getBoundsInParent());
      }
    }

    return popover;
  }
}
