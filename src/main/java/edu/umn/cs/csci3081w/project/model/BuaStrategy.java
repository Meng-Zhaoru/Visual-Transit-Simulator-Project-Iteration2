package edu.umn.cs.csci3081w.project.model;

public interface BuaStrategy {
  public Bus deployBus(int id, Line line, double speed);
  public int getState();
}
