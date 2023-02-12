package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import java.util.ArrayList;
import javafx.scene.Node;

public class UIManager {
  private ArrayList<Node> staticComponents;
  private ArrayList<Node> variableComponents;

  /** Creates a new UIManager object */
  public UIManager() {
    this.variableComponents = new ArrayList<Node>();
    this.staticComponents = new ArrayList<Node>();
  }

  /**
   * @param staticComponents A list of nodes (node1, node2, node3) to set as static (always active)
   *     components
   */
  public void setStaticComponents(Node... staticComponents) {
    for (int i = 0; i < staticComponents.length; i++) {
      this.staticComponents.add(staticComponents[i]);
    }
  }

  /**
   * @param variableComponents A list of nodes (node1, node2, node3) to set as variable (changing)
   *     components
   */
  public void setVariableComponents(Node... variableComponents) {
    for (int i = 0; i < variableComponents.length; i++) {
      this.variableComponents.add(variableComponents[i]);
    }
  }

  /** @param enabled A list of nodes (node1, node2, node3) to set visible and managed */
  public void enable(Node... enabled) {
    for (int i = 0; i < enabled.length; i++) {
      enabled[i].setVisible(true);
      enabled[i].setManaged(true);
    }
  }

  /**
   * @param enabled A list of nodes (node1, node2, node3) to set as enabled, and disable every other
   *     variable node
   */
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

  /** @param disabled A list of nodes (node1, node2, node3) to disable */
  public void disable(Node... disabled) {
    for (int i = 0; i < disabled.length; i++) {
      disabled[i].setVisible(false);
      disabled[i].setManaged(false);
    }
  }

  /**
   * @param disabled A list of nodes (node1, node2, node3) to set as disabled, and enable every
   *     other variable Node
   */
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
