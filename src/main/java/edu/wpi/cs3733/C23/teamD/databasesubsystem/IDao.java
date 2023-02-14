package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import java.util.List;

public interface IDao<T> {
  T get(T t);

  void save(T t);

  void update(T t);

  List<T> getAll();

  void refresh();

  void delete(T t);

  void uploadCSV(T t);

  void downloadCSV(T t);
}
