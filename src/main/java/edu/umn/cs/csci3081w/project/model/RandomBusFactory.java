package edu.umn.cs.csci3081w.project.model;

public class RandomBusFactory extends BusFactory {
  public Bus createSmallBus(int id, Line line, double speed) {
    return new SmallBus(id, line, speed);
  }

  public Bus createLargeBus(int id, Line line, double speed) {
    return new LargeBus(id, line, speed);
  }
}
