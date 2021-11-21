package edu.umn.cs.csci3081w.project.model;

public class LargeBus extends Bus{
  public static final double SPEED = 0.5;
  public static final int CAPACITY = 80;

  /**
   * Constructor for a bus.
   *
   * @param id       bus identifier
   * @param line     route of in/out bound
   * @param speed    speed of bus
   */
  public LargeBus(int id, Line line, double speed) {
    super(id, line, CAPACITY, speed);
  }

  public int getCurrentCO2Emission() {
    return (1 * getPassengers().size()) + 3;
  }
}
