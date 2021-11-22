package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public class Passenger {

  private String name;
  private int destinationStopId;
  private int waitAtStop;
  private int timeOnVehicle;

  /**
   * Constructor for passenger.
   *
   * @param name              name of passenger
   * @param destinationStopId destination stop id
   */
  public Passenger(int destinationStopId, String name) {
    this.name = name;
    this.destinationStopId = destinationStopId;
    this.waitAtStop = 0;
    this.timeOnVehicle = 0;
  }

  /**
   * Updates time variables for passenger.
   */
  public void pasUpdate() {
    if (isOnVehicle()) {
      timeOnVehicle++;
    } else {
      waitAtStop++;
    }
  }

  public String getName() {
    return name;
  }

  public void setOnVehicle() {
    timeOnVehicle = 1;
  }

  public boolean isOnVehicle() {
    return timeOnVehicle > 0;
  }

  public int getDestination() {
    return destinationStopId;
  }

  /**
   * Report statistics for passenger.
   *
   * @param out stream for printing
   */
  public void report(PrintStream out) {
    out.println("####Passenger Info Start####");
    out.println("Name: " + name);
    out.println("Destination: " + destinationStopId);
    out.println("Wait at stop: " + waitAtStop);
    out.println("Time on vehicle: " + timeOnVehicle);
    out.println("####Passenger Info End####");
  }

  public int getWaitAtStop() {
    return waitAtStop;
  }

  public int getTimeOnVehicle() {
    return timeOnVehicle;
  }

  /**
   * Compares two Passenger objects.
   * check if all the attributes of these two objects are same
   * @param pas the passenger that is going to be compared
   * @return <code>true</code> if it's the same object, <code>false</code> otherwise
   */
  public boolean equals(Passenger pas) {
    return (pas.getDestination() == this.destinationStopId)
        && (pas.getName().equals(this.name))
        && (pas.getWaitAtStop() == this.waitAtStop)
        && (pas.getTimeOnVehicle() == this.timeOnVehicle);
  }
}
