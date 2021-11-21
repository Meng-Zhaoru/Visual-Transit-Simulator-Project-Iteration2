package edu.umn.cs.csci3081w.project.model;

public class DieselTrain extends Train {
  /**
   * Constructor for a diesel train.
   *
   * @param id       train identifier
   * @param line     line of the train
   * @param capacity capacity of the train
   * @param speed    speed of the train
   */
  public DieselTrain(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
  }

  public int getCurrentCO2Emission() {
    return (2 * getPassengers().size()) + 6;
  }
}
