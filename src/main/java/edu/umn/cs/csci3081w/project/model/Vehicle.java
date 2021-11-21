package edu.umn.cs.csci3081w.project.model;

import edu.umn.cs.csci3081w.project.webserver.VisualTransitSimulator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle extends VehicleObserver{
  private int id;
  private int capacity;
  //the speed is in distance over a time unit
  private double speed;
  private PassengerLoader loader;
  private PassengerUnloader unloader;
  private List<Passenger> passengers;
  private String name;
  private Position position;
  private Line line;
  private double distanceRemaining;
  private Stop nextStop;
  private String vehicleType;
  private int timeToRestart;
  private String ObservedText;
  public int[] co2Array = {0, 0, 0, 0, 0};
  public boolean isLineIssued = false;

  /**
   * Constructor for a vehicle.
   *
   * @param id       vehicle identifier
   * @param capacity vehicle capacity
   * @param speed    vehicle speed
   * @param loader   passenger loader for vehicle
   * @param unloader passenger unloader for vehicle
   */
  public Vehicle(int id, int capacity, double speed, String vehicleType, Line line, PassengerLoader loader,
                 PassengerUnloader unloader) {
    this.id = id;
    this.capacity = capacity;
    this.speed = speed;
    this.vehicleType = vehicleType;
    this.loader = loader;
    this.unloader = unloader;
    this.passengers = new ArrayList<>();
    this.line = line;
    this.distanceRemaining = 0;
    this.nextStop = line.getOutboundRoute().getNextStop();
    setName(line.getOutboundRoute().getName() + id);
    setPosition(new Position(nextStop.getPosition().getLongitude(),
        nextStop.getPosition().getLatitude()));
    this.timeToRestart = 0;
  }

  public boolean isTripComplete() {
    return line.getOutboundRoute().isAtEnd() && line.getInboundRoute().isAtEnd();
  }

  public int loadPassenger(Passenger newPassenger) {
    return getPassengerLoader().loadPassenger(newPassenger, getCapacity(), getPassengers());
  }

  public double getDistanceRemaining() {
    return distanceRemaining;
  }

  /**
   * Moves the train on its route.
   */
  public void move() {
    // update passengers FIRST
    // new passengers will get "updated" when getting on the train
    for (Passenger passenger : getPassengers()) {
      passenger.pasUpdate();
    }
    //actually move
    double speed = updateDistance();
    if (!isTripComplete() && distanceRemaining <= 0) {
      //load & unload
      int passengersHandled = handleStop();
      if (passengersHandled >= 0) {
        // if we spent time unloading/loading
        // we don't get to count excess distance towards next stop
        distanceRemaining = 0;
      }
      //switch to next stop
      toNextStop();
    }

    // Get the correct route and early exit
    Route currentRoute = line.getOutboundRoute();
    if (line.getOutboundRoute().isAtEnd()) {
      if (line.getInboundRoute().isAtEnd()) {
        return;
      }
      currentRoute = line.getInboundRoute();
    }
    Stop prevStop = currentRoute.prevStop();
    Stop nextStop = currentRoute.getNextStop();
    double distanceBetween = currentRoute.getNextStopDistance();
    // the ratio shows us how far from the previous stop are we in a ratio from 0 to 1
    double ratio;
    // check if we are at the first stop
    if (distanceBetween - 0.00001 < 0) {
      ratio = 1;
    } else {
      ratio = distanceRemaining / distanceBetween;
      if (ratio < 0) {
        ratio = 0;
        distanceRemaining = 0;
      }
    }
    double newLongitude = nextStop.getPosition().getLongitude() * (1 - ratio)
        + prevStop.getPosition().getLongitude() * ratio;
    double newLatitude = nextStop.getPosition().getLatitude() * (1 - ratio)
        + prevStop.getPosition().getLatitude() * ratio;
    setPosition(new Position(newLongitude, newLatitude));
  }

  private int handleStop() {
    // This function handles arrival at a bus stop
    int passengersHandled = 0;
    // unloading passengers
    passengersHandled += unloadPassengers();
    // loading passengers
    passengersHandled += nextStop.loadPassengers(this);
    // if passengers were unloaded or loaded, it means we made
    // a stop to do the unload/load operation. In this case, the
    // distance remaining to the stop is 0 because we are at the stop.
    // If no unload/load operation was made and the distance is negative,
    // this means that we did not stop and keep going further.
    if (passengersHandled != 0) {
      distanceRemaining = 0;
    }
    return passengersHandled;
  }

  /**
   * Update the simulation state for this train.
   */
  public void update() {
    move();
  }

  /**
   * co2 consumption for a train.
   *
   * @return The current co2 consumption value
   */
  public abstract int getCurrentCO2Emission();

  public int unloadPassengers() {
    return getPassengerUnloader().unloadPassengers(getPassengers(), nextStop);
  }

  private void toNextStop() {
    //current stop
    currentRoute().nextStop();
    if (!isTripComplete()) {
      // it's important we call currentRoute() again,
      // as nextStop() may have caused it to change.
      nextStop = currentRoute().getNextStop();
      distanceRemaining +=
          currentRoute().getNextStopDistance();
      // note, if distanceRemaining was negative because we
      // had extra time left over, that extra time is
      // effectively counted towards the next stop
    } else {
      nextStop = null;
      distanceRemaining = 999;
    }
  }

  private double updateDistance() {
    // Updates the distance remaining and returns the effective speed of the train
    // Train does not move if speed is negative or train is at end of route
    if (isTripComplete()) {
      return 0;
    }
    if (getSpeed() < 0) {
      return 0;
    }
    distanceRemaining -= getSpeed();
    return getSpeed();
  }

  private Route currentRoute() {
    // Figure out if we're on the outgoing or incoming route
    if (!line.getOutboundRoute().isAtEnd()) {
      return line.getOutboundRoute();
    }
    return line.getInboundRoute();
  }

  public Stop getNextStop() {
    return nextStop;
  }

  public Line getLine() {
    return line;
  }

  public String getVehicleType() {
    return vehicleType;
  }

  public int getId() {
    return id;
  }

  public int getCapacity() {
    return capacity;
  }

  public double getSpeed() {
    return speed;
  }

  public PassengerLoader getPassengerLoader() {
    return loader;
  }

  public PassengerUnloader getPassengerUnloader() {
    return unloader;
  }

  public List<Passenger> getPassengers() {
    return passengers;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public abstract void report(PrintStream out);

  public void decreaseTimeToStart(){
    this.timeToRestart--;
  }

  public void increaseTimeToStartBy(int timeToRestart) {
    this.timeToRestart += timeToRestart;
  }

  public String getObservedText() {
    return ObservedText;
  }

  public int getTimeToRestart() {
    return timeToRestart;
  }

  public void generateObservedText(){
    String type = "";
    if(this.getVehicleType() == "BUS_VEHICLE"){
      type = "BUS";
    }
    else if(this.getVehicleType() == "TRAIN_VEHICLE"){
      type = "TRAIN";
    }
    this.ObservedText =
        "Vehicle " + this.getId() + System.lineSeparator()
            + "-----------------------------" + System.lineSeparator()
            + "*Type: " + type + System.lineSeparator()
            + "*Position: (" + this.getPosition().getLongitude() + "," + this.getPosition().getLatitude() + ")" + System.lineSeparator()
            + "*Passengers: " + this.getPassengers().size() + System.lineSeparator()
            + "*CO2: " + this.co2Array[0] + " " + this.co2Array[1] + " " + this.co2Array[2] + " " + this.co2Array[3] + " " + this.co2Array[4] + System.lineSeparator();
  }
}
