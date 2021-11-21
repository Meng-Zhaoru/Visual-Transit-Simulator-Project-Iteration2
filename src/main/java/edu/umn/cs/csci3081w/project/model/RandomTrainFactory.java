package edu.umn.cs.csci3081w.project.model;

public class RandomTrainFactory extends TrainFactory {
  /**
   * Create an electric train.
   *
   * @param id       train identifier
   * @param line     line of the train
   * @param capacity capacity of the train
   * @param speed    speed of the train
   * @return the electric train
   */
  public Train createElectricTrain(int id, Line line, int capacity, double speed) {
    return new ElectricTrain(id, line, capacity, speed);
  }

  /**
   * Create a diesel train.
   *
   * @param id       train identifier
   * @param line     line of the train
   * @param capacity capacity of the train
   * @param speed    speed of the train
   * @return the diesel train
   */
  public Train createDieselTrain(int id, Line line, int capacity, double speed) {
    return new DieselTrain(id, line, capacity, speed);
  }
}