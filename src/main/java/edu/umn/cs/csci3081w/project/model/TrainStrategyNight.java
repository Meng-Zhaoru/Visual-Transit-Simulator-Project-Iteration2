package edu.umn.cs.csci3081w.project.model;

public class TrainStrategyNight {
  public int state = 1;
  public TrainFactory trainFactory  = new RandomTrainFactory();

  public Train deployTrain(int id, Line line, int capacity, double speed){
    if(state > 0) {
      state--;
      return trainFactory.createElectricTrain(id, line, capacity, speed);
    }
    else {
      state = 1;
      return trainFactory.createDieselTrain(id, line, capacity, speed);
    }
  }
}