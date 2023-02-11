package edu.wpi.cs3733.C23.teamD.entities;

import java.util.ArrayList;

public class PathfinderDFS {
    private GraphMap fullMap;
    public PathfinderDFS(GraphMap fullMap) {
        this.fullMap = fullMap;
    }

    public void init(ArrayList<Node> nodeList, ArrayList<Edge> edgeList) {
        this.fullMap.init(nodeList, edgeList);
    }

    public void initFromDB() {
        this.fullMap.initFromDB();
    }

    public ArrayList<Node> depthFirstSearch(Node startNode, Node endNode) {
        ArrayList<Node> Path = new ArrayList<Node>();
        ArrayList<Node> Visited = new ArrayList<Node>();
        ArrayList<Edge> Neighbors = new ArrayList<Edge>();
        Node currentNode = startNode;
        int rollbackCount = 1;

        while (!currentNode.getNodeID().equals(endNode.getNodeID())) {
            // Neighbors.clear();

            Neighbors = (ArrayList<Edge>) currentNode.getNodeEdges().clone();

            for (int i = 0; i < Neighbors.size(); i++) {
                // if the edge leads to an unvisited node: Make it the new current node

                if (!Visited.contains(Neighbors.get(i).getToNode())) {
                    rollbackCount = 1;
                    currentNode = Neighbors.get(i).getToNode();
                    Visited.add(currentNode);
                    Path.add(currentNode);
                    break;
                }

                // If the current node has no unvisited neighbors, go back through visited until we find one
                // that does. Assign it to be the new current node
                else if (i == (Neighbors.size() - 1)) {
                    // Visited.add(currentNode);
                    Path.remove(currentNode);
                    currentNode = Visited.get((Visited.size() - rollbackCount));
                    rollbackCount++;
                }
            }
        }

        return Path;
    }
}




}
