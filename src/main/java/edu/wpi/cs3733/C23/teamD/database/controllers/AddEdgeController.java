package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.NodeComboBoxController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import lombok.Setter;

public class AddEdgeController implements AddFormController<Edge> {

  @Setter DatabaseController databaseController;
  @FXML private Parent fromNodeBox;
  @FXML private NodeComboBoxController fromNodeBoxController;
  @FXML private Parent toNodeBox;
  @FXML private NodeComboBoxController toNodeBoxController;
  @FXML private MFXButton submitButton;
  @FXML private Label titleLabel;

  private Edge currentEdge;

  @FXML
  public void submit() {
    if (currentEdge == null) {
      FDdb.getInstance()
          .saveEdge(new Edge(fromNodeBoxController.getNode(), toNodeBoxController.getNode()));
      databaseController.refresh();
    } else {
      currentEdge.setFromNode(fromNodeBoxController.getNode());
      currentEdge.setToNode(toNodeBoxController.getNode());
      FDdb.getInstance().updateEdge(currentEdge);
      databaseController.refresh();
    }
  }

  @FXML
  public void clearFields() {
    toNodeBoxController.clearForm();
    fromNodeBoxController.clearForm();
  }

  @Override
  public void dataToChange(Edge edge) {
    currentEdge = edge;
    if (edge == null) {
      submitButton.setText("Add Edge");
      titleLabel.setText("Add an Edge");
      clearFields();
    } else {
      submitButton.setText("Submit Changes");
      titleLabel.setText("Change an Edge");
      toNodeBoxController.setNodeID(edge.getToNodeID());
      fromNodeBoxController.setNodeID(edge.getFromNodeID());
    }
  }
}
