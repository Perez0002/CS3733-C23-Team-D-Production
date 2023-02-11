package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import edu.wpi.cs3733.C23.teamD.entities.Node;

import java.util.ArrayList;

public class FDdb {
    private static final FDdb instance = new FDdb();

    private final EdgeIDaoImpl edgeIDao;
    private final NodeIDaoImpl nodeIDao;
    private final LocationNameIDaoImpl locationNameIDao;
    private final MoveIDaoImpl moveIDao;

    private FDdb() {
        this.edgeIDao = new EdgeIDaoImpl();
        this.nodeIDao = new NodeIDaoImpl();
        this.locationNameIDao = new LocationNameIDaoImpl();
        this.moveIDao = new MoveIDaoImpl();
    }

    public static FDdb getInstance() {
        return instance;
    }



    // nodeDao wrapper methods
    public Node getNode(Node n) {
        return nodeIDao.get(n);
    }

    public Node getNode(String nodeID) {
        return nodeIDao.get(nodeID);
    }

    public ArrayList<Node> getAllNodes() {
        return nodeIDao.getAll();
    }

    public void saveNode(Node n) {
        nodeIDao.save(n);
    }

    public void updateNode(Node n) {
        nodeIDao.update(n);
    }

    public void deleteNode(Node n) {
        nodeIDao.delete(n);
    }

    public void nodeEdgeSwap(Node oldNode, Node newNode) {
        nodeIDao.nodeEdgeSwap(oldNode, newNode);
    }



    // edgeDao wrapper methods
    public Edge getEdge(Edge e) {
        return edgeIDao.get(e);
    }

    public ArrayList<Edge> getAllEdges() {
        return edgeIDao.getAll();
    }

    public void saveEdge(Edge e) {
        edgeIDao.save(e);
    }

    public void updateEdge(Edge e) {
        edgeIDao.update(e);
    }

    public void deleteEdge(Edge e) {
        edgeIDao.delete(e);
    }



    // locationNameDao wrapper methods
    public LocationName getNode(LocationName l) {
        return locationNameIDao.get(l);
    }

    public ArrayList<LocationName> getAllLocationNames() {
        return locationNameIDao.getAll();
    }

    public void saveLocationName(LocationName l) {
        locationNameIDao.save(l);
    }

    public void updateLocationName(LocationName l) {
        locationNameIDao.update(l);
    }

    public void deleteLocationName(LocationName l) {
        locationNameIDao.delete(l);
    }



    // moveDao wrapper methods
    public Move getMove(Move m) {
        return moveIDao.get(m);
    }

    public ArrayList<Move> getAllMoves() {
        return moveIDao.getAll();
    }

    public void saveMove(Move m) {
        moveIDao.save(m);
    }

    public void updateMove(Move m) {
        moveIDao.update(m);
    }

    public void deleteMove(Move m) {
         moveIDao.delete(m);
    }
}
