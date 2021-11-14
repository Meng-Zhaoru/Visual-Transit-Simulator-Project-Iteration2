package edu.umn.cs.csci3081w.project.model;

public class BusStrategyNight {
  public int state = 3;
  public BusFactory busFactory = new RandomBusFactory();

  public Bus deployBus(int id, Line line, double speed){
    if(state > 0) {
      state--;
      return busFactory.createSmallBus(id, line, speed);
    }
    else {
      state = 3;
      return busFactory.createLargeBus(id, line, speed);
    }
  }
}
