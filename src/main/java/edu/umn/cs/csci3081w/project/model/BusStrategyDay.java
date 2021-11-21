package edu.umn.cs.csci3081w.project.model;

public class BusStrategyDay implements BuaStrategy {
  private int state = 2;
  public BusFactory busFactory = new RandomBusFactory();

  /**
   * Deploy a bus using day strategy.
   *
   * @param id    bus identifier
   * @param line  line of the train
   * @param speed speed of the bus
   * @return the deployed bus
   */
  public Bus deployBus(int id, Line line, double speed) {
    if (state > 0) {
      state--;
      return busFactory.createLargeBus(id, line, speed);
    } else {
      state = 2;
      return busFactory.createSmallBus(id, line, speed);
    }
  }

  @Override
  public int getState() {
    return state;
  }
}
