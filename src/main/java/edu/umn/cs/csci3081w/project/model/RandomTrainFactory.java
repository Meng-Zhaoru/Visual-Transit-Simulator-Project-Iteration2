package edu.umn.cs.csci3081w.project.model;

public class RandomTrainFactory extends TrainFactory{

  public Train createElectricTrain(int id, Line line, int capacity, double speed) {
    return new ElectricTrain(id, line, capacity, speed);
  }

  public Train createDieselTrain(int id, Line line, int capacity, double speed) {
    return new DieselTrain(id, line, capacity, speed);
  }
}