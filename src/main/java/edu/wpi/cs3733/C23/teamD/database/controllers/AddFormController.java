package edu.wpi.cs3733.C23.teamD.database.controllers;

public interface AddFormController<Data> {

  public void setDatabaseController(DatabaseController databaseController);

  public void dataToChange(Data data);
}
