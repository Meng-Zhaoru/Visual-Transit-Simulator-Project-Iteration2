package edu.umn.cs.csci3081w.project.model;

public class ElectricTrain extends Train{
  public static final String TRAIN_VEHICLE = "ELECTRIC_TRAIN_VEHICLE";
  public static final double SPEED = 1;
  public static final int CAPACITY = 120;
  /**
   * Constructor for a train.
   *
   * @param id       train identifier
   * @param line     route of in/out bound
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
