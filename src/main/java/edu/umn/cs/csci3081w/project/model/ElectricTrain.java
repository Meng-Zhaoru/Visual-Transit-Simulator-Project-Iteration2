package edu.umn.cs.csci3081w.project.model;

public class ElectricTrain extends Train {
  public static final double SPEED = 1;
  public static final int CAPACITY = 120;

  /**
   * Constructor for a electric train.
   *
   * @param id       train identifier
   * @param line     line of the train
   * @param capacity capacity of the train
   * @param speed    speed of the train
   */
  public ElectricTrain(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
  }

  public int getCurrentCO2Emission() {
    return 0;
  }
}
