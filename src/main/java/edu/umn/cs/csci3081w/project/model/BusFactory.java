package edu.umn.cs.csci3081w.project.model;

public abstract class BusFactory {
  public abstract Bus createSmallBus(int id, Line line, double speed);
  public abstract Bus createLargeBus(int id, Line line, double speed);
}
