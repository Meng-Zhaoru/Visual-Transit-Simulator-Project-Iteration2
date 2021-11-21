package edu.umn.cs.csci3081w.project.model;

public interface TrainStrategy {
  Train deployTrain(int id, Line line, int capacity, double speed);
  int getState();
}
