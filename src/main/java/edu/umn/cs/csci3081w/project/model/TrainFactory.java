package edu.umn.cs.csci3081w.project.model;

public abstract class TrainFactory {
  public abstract Train createElectricTrain(int id, Line line, int capacity, double speed);
  public abstract Train createDieselTrain(int id, Line line, int capacity, double speed);
}