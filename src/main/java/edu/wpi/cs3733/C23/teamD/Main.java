package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.database.util.DBSingleton;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;

public class Main {

  public static void main(String[] args) {

    FDdb.getInstance().refreshAll();
    DBSingleton.refreshSession();
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
