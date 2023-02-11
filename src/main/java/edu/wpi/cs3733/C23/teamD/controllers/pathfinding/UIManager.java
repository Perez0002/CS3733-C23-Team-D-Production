package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import java.util.ArrayList;
import javafx.scene.Node;

public class UIManager {
  private ArrayList<Node> staticComponents;
  private ArrayList<Node> variableComponents;

  public UIManager() {
    this.variableComponents = new ArrayList<Node>();
    this.staticComponents = new ArrayList<Node>();
  }

  public void setStaticComponents(Node... staticComponents) {
    for (int i = 0; i < staticComponents.length; i++) {
      this.staticComponents.add(staticComponents[i]);
    }
  }

  public void setVariableComponents(Node... variableComponents) {
    for (int i = 0; i < variableComponents.length; i++) {
      this.variableComponents.add(variableComponents[i]);
    }
  }

  public void enable(Node... enabled) {
    for (int i = 0; i < enabled.length; i++) {
      enabled[i].setVisible(true);
      enabled[i].setManaged(true);
    }
  }

  public void enableOnly(Node... enabled) {
    for (Node node : this.staticComponents) {
      node.setVisible(true);
      node.setManaged(true);
    }

    for (Node node : this.variableComponents) {
      node.setVisible(false);
      node.setManaged(false);
    }

    this.enable(enabled);
  }

  public void disable(Node... disabled) {
    for (int i = 0; i < disabled.length; i++) {
      disabled[i].setVisible(false);
      disabled[i].setManaged(false);
    }
  }

  public void disableOnly(Node... disabled) {
    for (Node node : this.staticComponents) {
      node.setVisible(true);
      node.setManaged(true);
    }

    for (Node node : this.variableComponents) {
      node.setVisible(true);
      node.setManaged(true);
    }

    this.disable(disabled);
  }
}
