package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Setter;

public class AddNodeController implements AddFormController<Node> {
  @Setter private DatabaseController databaseController;

  @FXML private MFXTextField buildingTextField;

  @FXML private Text errorText;

  @FXML private MFXTextField floorTextField;

  @FXML private MFXButton submitButton;

  @FXML private Label titleLabel;

  @FXML private MFXTextField xCoordTextField;

  @FXML private MFXTextField yCoordTextField;

  private Node currentNode;

  @FXML
  void clearFields() {
    yCoordTextField.clear();
    xCoordTextField.clear();
    buildingTextField.clear();
    floorTextField.clear();
  }

  @FXML
  void submit() {
    if (currentNode == null) {
      errorText.setVisible(false);
      ZoneId defaultZoneId = ZoneId.systemDefault();

      FDdb.getInstance()
          .saveNode(
              new Node(
                  Integer.parseInt(xCoordTextField.getText()),
                  Integer.parseInt(yCoordTextField.getText()),
                  floorTextField.getText(),
                  buildingTextField.getText()));
      databaseController.refresh();
    } else {
      currentNode.setBuilding(buildingTextField.getText());
      currentNode.setFloor(floorTextField.getText());
      currentNode.setYcoord(Integer.parseInt(yCoordTextField.getText()));
      currentNode.setXcoord(Integer.parseInt(xCoordTextField.getText()));

      FDdb.getInstance().updateNodePK(currentNode);
      databaseController.refresh();
    }
  }

  @Override
  public void dataToChange(Node node) {
    currentNode = node;
    if (node == null) {
      submitButton.setText("Add Node");
      titleLabel.setText("Add a Node");
      clearFields();
    } else {
      submitButton.setText("Submit Changes");
      titleLabel.setText("Change a Node");
      xCoordTextField.setText(Integer.toString(node.getXcoord()));
      yCoordTextField.setText(Integer.toString(node.getYcoord()));
      buildingTextField.setText(node.getBuilding());
      floorTextField.setText(node.getFloor());
    }
  }
}
