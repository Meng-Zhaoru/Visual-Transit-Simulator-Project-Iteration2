package edu.umn.cs.csci3081w.project.model;

public interface BuaStrategy {
  Bus deployBus(int id, Line line, double speed);
  int getState();
}
