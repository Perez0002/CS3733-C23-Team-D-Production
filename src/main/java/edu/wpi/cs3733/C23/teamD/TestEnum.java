package edu.wpi.cs3733.C23.teamD;

public class TestEnum {
  public static void main(String[] args) {
    Ddb.csv2DBInsertNodes("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Node1.csv");
    Ddb.csv2DBInsertEdges("src/main/resources/edu/wpi/cs3733/C23/teamD/data/edges2.csv");
    // Ddb.oldCSVtoNewCSV();
  }
}
