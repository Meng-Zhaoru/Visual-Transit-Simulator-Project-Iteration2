package edu.umn.cs.csci3081w.project.model;

public class BusStrategyNight implements BuaStrategy {
  private int state = 3;
  public BusFactory busFactory = new RandomBusFactory();

  /**
   * Deploy a bus using night strategy.
   *
   * @param id    bus identifier
   * @param line  line of the bus
   * @param speed speed of the bus
   * @return the deployed bus
   */
  public Bus deployBus(int id, Line line, double speed) {
    if (state > 0) {
      state--;
      return busFactory.createSmallBus(id, line, speed);
    } else {
      state = 3;
      return busFactory.createLargeBus(id, line, speed);
    }
  }

  @Override
  public int getState() {
    return state;
  }
}
