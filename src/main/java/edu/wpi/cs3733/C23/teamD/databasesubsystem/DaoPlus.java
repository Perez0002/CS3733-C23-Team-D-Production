package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import java.util.List;

public interface DaoPlus<T> extends Dao<T> {
  List<T> getAll();

  void delete(T t);
}
