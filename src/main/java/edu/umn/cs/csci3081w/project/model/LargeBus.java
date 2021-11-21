package edu.umn.cs.csci3081w.project.model;

public class LargeBus extends Bus {
  public static final double SPEED = 0.5;
  public static final int CAPACITY = 80;

  /**
   * Constructor for a large bus.
   *
   * @param id    bus identifier
   * @param line  line of the bus
   * @param speed speed of bus
   */
  public LargeBus(int id, Line line, double speed) {
    super(id, line, CAPACITY, speed);
  }

  public int getCurrentCO2Emission() {
    return (1 * getPassengers().size()) + 3;
  }
}
