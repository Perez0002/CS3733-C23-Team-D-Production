package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import java.util.ArrayList;

public class Ddb {
  public static void connectNodestoLocations(ArrayList<Node> nodes) {
    ArrayList<Move> moves = FDdb.getInstance().getAllMoves();
    for (Node node : nodes) {
      for (Move move : moves) {
        if (node.nodeEquals(move.getNode())) {
          node.setLocation(move.getLocation());
        }
      }
    }
  }
}
