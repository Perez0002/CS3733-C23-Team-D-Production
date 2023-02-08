package edu.wpi.cs3733.C23.teamD.databasesubsystem;

public interface Dao<T> {
  T get(T t);

  void save(T t);

  void update(T t);
}
