package edu.umn.cs.csci3081w.project.model;

public class RandomBusFactory extends BusFactory {
  /**
   * Create a small bus.
   *
   * @param id    bus identifier
   * @param line  line of the bus
   * @param speed speed of the bus
   * @return the small bus
   */
  public Bus createSmallBus(int id, Line line, double speed) {
    return new SmallBus(id, line, speed);
  }

  /**
   * Create a large bus.
   *
   * @param id    bus identifier
   * @param line  line of the bus
   * @param speed speed of the bus
   * @return the large bus
   */
  public Bus createLargeBus(int id, Line line, double speed) {
    return new LargeBus(id, line, speed);
  }
}
