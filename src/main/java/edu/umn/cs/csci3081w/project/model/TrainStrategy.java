package edu.umn.cs.csci3081w.project.model;

public interface TrainStrategy {
  public Train deployTrain(int id, Line line, int capacity, double speed);
  public int getState();
}
