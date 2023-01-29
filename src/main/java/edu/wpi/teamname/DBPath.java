package edu.wpi.teamname;

import java.sql.*;
import java.util.ArrayList;

public class DBPath {
    public static void main(String[] args) {
        Connection conn = Ddb.makeConnection();
        ArrayList<Node> nodeList = Ddb.createJavaNodes(conn);
        ArrayList<Edge> edgeList = Ddb.createJavaEdges(conn, nodeList);
        ArrayList<locationName> locList = Ddb.createJavaLocat(conn);
        for(Node node: nodeList){
            ResultSet rset =null;
            String curName = "";
            try{
                PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM Move where nodeID = ?");
                pstmnt.setString(1,node.getNodeID());
                rset = pstmnt.executeQuery();
                curName = rset.getString("longName");
                for(locationName loc: locList){
                    if(loc.getLongName().equals(curName)){
                        node.setLocation(loc);
                        break;
                    }
                }
           }
           catch (SQLException e){
               return;
           }
        }
    }
}
