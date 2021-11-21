package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class Train extends Vehicle {
  public static final String TRAIN_VEHICLE = "TRAIN_VEHICLE";
  public static final double SPEED = 1;
  public static final int CAPACITY = 120;


  /**
   * Constructor for a train.
   *
   * @param id       train identifier
   * @param line     line of the train
   * @param capacity capacity of the train
   * @param speed    speed of the train
   */
  public Train(int id, Line line, int capacity, double speed) {
    super(id, capacity, speed, TRAIN_VEHICLE, line, new PassengerLoader(), new PassengerUnloader());
  }

  /**
   * Report statistics for the train.
   *
   * @param out stream for printing
   */
  public void report(PrintStream out) {
    out.println("####Train Info Start####");
    out.println("ID: " + getId());
    out.println("Name: " + getName());
    out.println("Speed: " + getSpeed());
    out.println("Capacity: " + getCapacity());
    out.println("Position: " + (getPosition().getLatitude() + "," + getPosition().getLongitude()));
    out.println("Distance to next stop: " + getDistanceRemaining());
    out.println("****Passengers Info Start****");
    out.println("Num of passengers: " + getPassengers().size());
    for (Passenger pass : getPassengers()) {
      pass.report(out);
    }
    out.println("****Passengers Info End****");
    out.println("####Train Info End####");
  }

  public abstract int getCurrentCO2Emission();
}
