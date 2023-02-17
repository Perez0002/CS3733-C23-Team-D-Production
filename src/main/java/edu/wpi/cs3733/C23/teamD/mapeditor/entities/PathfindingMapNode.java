package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.mapeditor.util.PopupFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import javafx.scene.paint.Color;

public class PathfindingMapNode extends MapNode {
  public PathfindingMapNode(PathNode node) {
    super(node);
    nodeRepresentation.setOnMouseClicked(
        event -> {
          this.MakePopup();
        });
  }

  private void MakePopup() {
    if (this.popup == null) {
      this.popup =
          PopupFactory.startBuild()
              .anchor(this.nodeRepresentation)
              .mapNode(this)
              .closeEvent(
                  event -> {
                    this.RemovePopup();
                    this.allowTooltip = true;
                  })
              .build();
      this.nodeRepresentation.setFill(Color.rgb(0xCC, 0x22, 0x22));
      this.allowTooltip = false;
    }
  }

  private void RemovePopup() {
    if (popup != null) {
      popup.hide();
      popup = null;
      this.nodeRepresentation.setFill(Color.rgb(0x01, 0x3A, 0x75));
      this.allowTooltip = true;
    }
  }
}
