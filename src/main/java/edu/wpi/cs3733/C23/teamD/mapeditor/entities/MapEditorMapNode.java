package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.mapeditor.util.PopupFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import javafx.scene.paint.Color;

public class MapEditorMapNode extends MapNode {

  public MapEditorMapNode(PathNode node) {
    /* Superclass object */
    super(node);
    /* Creates popup on mouse click */
    nodeRepresentation.setOnMouseClicked(
        event -> {
          this.MakePopup();
        });
  }

  private void MakePopup() {
    if (this.popup == null) {
      /* Assuming the popup does not exist, build a new one from the factory */
      this.popup =
          PopupFactory.startBuild()
              .anchor(this.nodeRepresentation)
              .mapNode(this)
              .deletable()
              .editable()
              .closeEvent(
                  event -> {
                    this.RemovePopup();
                    this.allowTooltip = true;
                  })
              .build();
      /* Color the node on the map to represent selection */
      this.nodeRepresentation.setFill(Color.rgb(0xCC, 0x22, 0x22));
      /* Prevent this Node's tooltip from popping up */
      this.allowTooltip = false;
    }
  }

  private void RemovePopup() {
    if (popup != null) {
      /* Assuming the popup exists, hide and then remove it to save VRam space */
      popup.hide();
      popup = null;
      /* Set the color of the Node on the map to represent deselection */
      this.nodeRepresentation.setFill(Color.rgb(0x01, 0x3A, 0x75));
      /* Allow this Node's tooltip to pop up */
      this.allowTooltip = true;
    }
  }
}
