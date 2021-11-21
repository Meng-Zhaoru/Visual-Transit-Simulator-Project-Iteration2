package edu.umn.cs.csci3081w.project.model;

public class TrainStrategyDay implements TrainStrategy {
  private int state = 3;
  public TrainFactory trainFactory = new RandomTrainFactory();

  /**
   * Deploy a train using day strategy.
   *
   * @param id    train identifier
   * @param line  line of the train
   * @param speed speed of the train
   * @return the deployed train
   */
  public Train deployTrain(int id, Line line, int capacity, double speed) {
    if (state > 0) {
      state--;
      return trainFactory.createElectricTrain(id, line, capacity, speed);
    } else {
      state = 3;
      return trainFactory.createDieselTrain(id, line, capacity, speed);
    }
  }

  @Override
  public int getState() {
    return state;
  }
}