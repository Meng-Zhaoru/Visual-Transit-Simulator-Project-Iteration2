package edu.umn.cs.csci3081w.project.webserver;

import edu.umn.cs.csci3081w.project.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VisualTransitSimulator {

  private static boolean LOGGING = false;
  private int numTimeSteps = 0;
  private int simulationTimeElapsed = 0;
  private int currentTime = LocalDateTime.now().getHour();
  private Counter counter;
  private List<Line> lines;
  private BusStrategyDay busStrategyDay;
  private BusStrategyNight busStrategyNight;
  private TrainStrategyDay trainStrategyDay;
  private TrainStrategyNight trainStrategyNight;
  private List<Vehicle> activeVehicles;
  private List<Vehicle> completedTripVehicles;
  private List<Integer> vehicleStartTimings;
  private List<Integer> timeSinceLastVehicle;
  private StorageFacility storageFacility;
  private WebServerSession webServerSession;

  /**
   * Constructor for Simulation.
   *
   * @param configFile file containing the simulation configuration
   * @param webServerSession session associated with the simulation
   */
  public VisualTransitSimulator(String configFile, WebServerSession webServerSession) {
    this.webServerSession = webServerSession;
    this.counter = new Counter();
    ConfigManager configManager = new ConfigManager();
    configManager.readConfig(counter, configFile);
    this.lines = configManager.getLines();
    this.busStrategyDay = new BusStrategyDay();
    this.busStrategyNight = new BusStrategyNight();
    this.trainStrategyDay = new TrainStrategyDay();
    this.trainStrategyNight = new TrainStrategyNight();
    this.activeVehicles = new ArrayList<Vehicle>();
    this.completedTripVehicles = new ArrayList<Vehicle>();
    this.vehicleStartTimings = new ArrayList<Integer>();
    this.timeSinceLastVehicle = new ArrayList<Integer>();
    this.storageFacility = configManager.getStorageFacility();
    if (this.storageFacility == null) {
      this.storageFacility = new StorageFacility(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    if (VisualTransitSimulator.LOGGING) {
      System.out.println("////Simulation Routes////");
      for (int i = 0; i < lines.size(); i++) {
        lines.get(i).getOutboundRoute().report(System.out);
        lines.get(i).getInboundRoute().report(System.out);
      }
    }
  }

  /**
   * Starts the simulation.
   *
   * @param vehicleStartTimings start timings of bus
   * @param numTimeSteps        number of time steps
   */
  public void start(List<Integer> vehicleStartTimings, int numTimeSteps) {
    this.vehicleStartTimings = vehicleStartTimings;
    this.numTimeSteps = numTimeSteps;
    for (int i = 0; i < vehicleStartTimings.size(); i++) {
      this.timeSinceLastVehicle.add(i, 0);
    }
    simulationTimeElapsed = 0;
  }

  /**
   * Updates the simulation at each step.
   */
  public void update() {
    simulationTimeElapsed++;
    if (simulationTimeElapsed > numTimeSteps) {
      return;
    }
    System.out.println("~~~~The simulation time is now at time step "
        + simulationTimeElapsed + "~~~~");
    // generate vehicles
    for (int i = 0; i < timeSinceLastVehicle.size(); i++) {
      if (timeSinceLastVehicle.get(i) <= 0) {
        Route outbound = lines.get(i).getOutboundRoute();
        Route inbound = lines.get(i).getInboundRoute();
        Line line = findLineBasedOnRoute(outbound);
        if (line.getType().equals(Line.BUS_LINE)) {
          if(currentTime >= 8 && currentTime < 16){
            if (storageFacility.getSmallBusesNum() > 0 && busStrategyDay.state == 0) {
              Bus smallBus = busStrategyDay.deployBus(counter.getSmallBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
              activeVehicles.add(smallBus);
              this.storageFacility.decrementSmallBusesNum();
            } else if (storageFacility.getLargeBusesNum() > 0 && busStrategyDay.state > 0) {
              Bus largeBus = busStrategyDay.deployBus(counter.getLargeBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
              activeVehicles.add(largeBus);
              this.storageFacility.decrementLargeBusesNum();
            }
          }
          else{
            if (storageFacility.getSmallBusesNum() > 0 && busStrategyNight.state > 0) {
              Bus smallBus = busStrategyNight.deployBus(counter.getSmallBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
              activeVehicles.add(smallBus);
              this.storageFacility.decrementSmallBusesNum();
            } else if (storageFacility.getLargeBusesNum() > 0 && busStrategyNight.state == 0) {
              Bus largeBus = busStrategyNight.deployBus(counter.getLargeBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
              activeVehicles.add(largeBus);
              this.storageFacility.decrementLargeBusesNum();
            }
          }
          timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
          timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
        } else if (line.getType().equals(Line.TRAIN_LINE)) {
          if(currentTime >= 8 && currentTime < 16){
            if (storageFacility.getElectricTrainsNum() > 0 && trainStrategyDay.state > 0) {
              Train electricTrain = trainStrategyDay.deployTrain(counter.getElectricTrainIdCounterAndIncrement(), line.shallowCopy(), Train.CAPACITY, Bus.SPEED);
              activeVehicles.add(electricTrain);
              this.storageFacility.decrementElectricTrainsNum();
            } else if (storageFacility.getDieselTrainsNum() > 0 && trainStrategyDay.state == 0) {
              Train dieselTrain = trainStrategyDay.deployTrain(counter.getDieselTrainIdCounterAndIncrement(), line.shallowCopy(), Train.CAPACITY, Bus.SPEED);
              activeVehicles.add(dieselTrain);
              this.storageFacility.decrementDieselTrainsNum();
            }
          }
          else{
            if (storageFacility.getElectricTrainsNum() > 0 && trainStrategyNight.state > 0) {
              Train electricTrain = trainStrategyNight.deployTrain(counter.getElectricTrainIdCounterAndIncrement(), line.shallowCopy(), Train.CAPACITY, Bus.SPEED);
              activeVehicles.add(electricTrain);
              this.storageFacility.decrementElectricTrainsNum();
            } else if (storageFacility.getDieselTrainsNum() > 0 && trainStrategyNight.state == 0) {
              Train dieselTrain = trainStrategyNight.deployTrain(counter.getDieselTrainIdCounterAndIncrement(), line.shallowCopy(), Train.CAPACITY, Bus.SPEED);
              activeVehicles.add(dieselTrain);
              this.storageFacility.decrementDieselTrainsNum();
            }
          }
          timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
          timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
        }
      } else {
        timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
      }
    }
    // update vehicles
    for (int i = activeVehicles.size() - 1; i >= 0; i--) {
      Vehicle currVehicle = activeVehicles.get(i);
      currVehicle.update();
      if (currVehicle.isTripComplete()) {
        Vehicle completedTripVehicle = activeVehicles.remove(i);
        completedTripVehicles.add(completedTripVehicle);
        if (completedTripVehicle instanceof SmallBus) {
          this.storageFacility.incrementSmallBusesNum();
        } else if (completedTripVehicle instanceof LargeBus) {
          this.storageFacility.incrementLargeBusesNum();
        } else if (completedTripVehicle instanceof ElectricTrain) {
          this.storageFacility.incrementElectricTrainsNum();
        } else if (completedTripVehicle instanceof DieselTrain) {
          this.storageFacility.incrementDieselTrainsNum();
        }
      } else {
        if (VisualTransitSimulator.LOGGING) {
          currVehicle.report(System.out);
        }
      }
    }
    // update routes
    for (int i = 0; i < lines.size(); i++) {
      Route currOutboundRoute = lines.get(i).getOutboundRoute();
      Route currInboundRoute = lines.get(i).getInboundRoute();
      currOutboundRoute.update();
      currInboundRoute.update();
      if (VisualTransitSimulator.LOGGING) {
        currOutboundRoute.report(System.out);
        currInboundRoute.report(System.out);
      }
    }
  }

  /**
   * Method to find the line of a route.
   *
   * @param route route to search
   * @return Line containing route
   */
  public Line findLineBasedOnRoute(Route route) {
    for (Line line : lines) {
      if (line.getOutboundRoute().getId() == route.getId()
          || line.getInboundRoute().getId() == route.getId()) {
        return line;
      }
    }
    throw new RuntimeException("Could not find the line of the route");
  }

  public List<Route> getRoutes() {
    List<Route> routes = new ArrayList<>();
    for(int i = 0; i < lines.size(); i ++){
      routes.add(lines.get(i).getOutboundRoute());
      routes.add(lines.get(i).getInboundRoute());
    }
    return routes;
  }

  public List<Vehicle> getActiveVehicles() {
    return activeVehicles;
  }
}