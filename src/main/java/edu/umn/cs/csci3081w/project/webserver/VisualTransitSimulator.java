package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VisualTransitSimulator {

  private static boolean LOGGING = false;
  private int numTimeSteps = 0;
  private int simulationTimeElapsed = 0;
  private Counter counter;
  private List<Line> lines;
  // strategy design pattern
  private int currentTime = LocalDateTime.now().getHour();
  private BuaStrategy busStrategyDay;
  private BuaStrategy busStrategyNight;
  private TrainStrategy trainStrategyDay;
  private TrainStrategy trainStrategyNight;
  private List<Vehicle> activeVehicles;
  private List<Vehicle> completedTripVehicles;
  private List<Integer> vehicleStartTimings;
  private List<Integer> timeSinceLastVehicle;
  private StorageFacility storageFacility;
  private WebServerSession webServerSession;
  private ObserveVehicleCommand observeVehicleCommand;
  private JsonObject registerCommand;
  private boolean updateRegisterInfo = false;
  private int[] timeToRestart;
  public List<Integer> issueLineIdList = new ArrayList<>();
  public RegisterVehicleSubject registerVehicleSubject;
  public VehicleObserver registerVehicle;

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
    this.observeVehicleCommand = new ObserveVehicleCommand(this);
    this.storageFacility = configManager.getStorageFacility();
    this.registerVehicleSubject = new RegisterVehicleConcreteSubject();
    if (this.storageFacility == null) {
      this.storageFacility = new StorageFacility(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    this.timeToRestart = new int[lines.size()];
    for(int i = 0; i < lines.size(); i ++){
      timeToRestart[i] = 10;
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
      for(int j = 0; j < issueLineIdList.size(); j++){
        if(lines.get(i).getId() == issueLineIdList.get(j)){
          lines.get(i).isLineIssued = true;
        }
      }
      if (lines.get(i).isLineIssued == true && lines.get(i).getTimeToRestart() > 0) {
        lines.get(i).decreaseTimeToRestart();
      } else {
        if (timeSinceLastVehicle.get(i) <= 0) {
          Route outbound = lines.get(i).getOutboundRoute();
          Route inbound = lines.get(i).getInboundRoute();
          Line line = findLineBasedOnRoute(outbound);
          if (line.getType().equals(Line.BUS_LINE)) {
            if (currentTime >= 8 && currentTime < 16) {
              if (storageFacility.getSmallBusesNum() > 0 && busStrategyDay.getState() == 0) {
                Bus smallBus = busStrategyDay.deployBus(counter.getSmallBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
                activeVehicles.add(smallBus);
                this.storageFacility.decrementSmallBusesNum();
              } else if (storageFacility.getLargeBusesNum() > 0 && busStrategyDay.getState() > 0) {
                Bus largeBus = busStrategyDay.deployBus(counter.getLargeBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
                activeVehicles.add(largeBus);
                this.storageFacility.decrementLargeBusesNum();
              }
            } else {
              if (storageFacility.getSmallBusesNum() > 0 && busStrategyNight.getState() > 0) {
                Bus smallBus = busStrategyNight.deployBus(counter.getSmallBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
                activeVehicles.add(smallBus);
                this.storageFacility.decrementSmallBusesNum();
              } else if (storageFacility.getLargeBusesNum() > 0 && busStrategyNight.getState() == 0) {
                Bus largeBus = busStrategyNight.deployBus(counter.getLargeBusIdCounterAndIncrement(), line.shallowCopy(), Bus.SPEED);
                activeVehicles.add(largeBus);
                this.storageFacility.decrementLargeBusesNum();
              }
            }
            timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
            timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
          } else if (line.getType().equals(Line.TRAIN_LINE)) {
            if (currentTime >= 8 && currentTime < 16) {
              if (storageFacility.getElectricTrainsNum() > 0 && trainStrategyDay.getState() > 0) {
                Train electricTrain = trainStrategyDay.deployTrain(counter.getElectricTrainIdCounterAndIncrement(), line.shallowCopy(), Train.CAPACITY, Bus.SPEED);
                activeVehicles.add(electricTrain);
                this.storageFacility.decrementElectricTrainsNum();
              } else if (storageFacility.getDieselTrainsNum() > 0 && trainStrategyDay.getState() == 0) {
                Train dieselTrain = trainStrategyDay.deployTrain(counter.getDieselTrainIdCounterAndIncrement(), line.shallowCopy(), Train.CAPACITY, Bus.SPEED);
                activeVehicles.add(dieselTrain);
                this.storageFacility.decrementDieselTrainsNum();
              }
            } else {
              if (storageFacility.getElectricTrainsNum() > 0 && trainStrategyNight.getState() > 0) {
                Train electricTrain = trainStrategyNight.deployTrain(counter.getElectricTrainIdCounterAndIncrement(), line.shallowCopy(), Train.CAPACITY, Bus.SPEED);
                activeVehicles.add(electricTrain);
                this.storageFacility.decrementElectricTrainsNum();
              } else if (storageFacility.getDieselTrainsNum() > 0 && trainStrategyNight.getState() == 0) {
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
    }
    // update vehicles
    for (int i = activeVehicles.size() - 1; i >= 0; i--) {
      Vehicle currVehicle = activeVehicles.get(i);
      for(int j = 0; j < issueLineIdList.size(); j++){
        if(currVehicle.getLine().getId() == issueLineIdList.get(j)){
          currVehicle.getLine().isLineIssued = true;
        }
      }
      if(currVehicle.getLine().isLineIssued = true && currVehicle.getTimeToRestart() > 0) {
        currVehicle.decreaseTimeToStart();
      }
      else {
        if (updateRegisterInfo) {
          if (registerCommand.get("id").getAsInt() == currVehicle.getId()) {
            this.registerVehicle = currVehicle;
            registerVehicleSubject.registerVehicle(registerVehicle);
            registerVehicleSubject.notifyVehicle();
            observeVehicleCommand.execute(webServerSession, null);
          }
        }
        for (int cur = 4; cur > 0; cur--) {
          currVehicle.co2Array[cur] = currVehicle.co2Array[cur - 1];
        }
        currVehicle.co2Array[0] = currVehicle.getCurrentCO2Emission();
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
    }
    // update routes
    for (int i = 0; i < lines.size(); i++) {
      for(int j = 0; j < issueLineIdList.size(); j++){
        if(lines.get(i).getId() == issueLineIdList.get(j)){
          lines.get(i).isLineIssued = true;
        }
      }
      if(lines.get(i).isLineIssued = true && lines.get(i).getTimeToRestart() > 0){
      }
      else {
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

  public void setUpdateRegisterInfo(boolean updateRegisterInfo) {
    this.updateRegisterInfo = updateRegisterInfo;
  }

  public void setRegisterCommand(JsonObject registerCommand) {
    this.registerCommand = registerCommand;
  }

  public List<Route> getRoutes() {
    List<Route> routes = new ArrayList<>();
    for(int i = 0; i < lines.size(); i ++){
      routes.add(lines.get(i).getOutboundRoute());
      routes.add(lines.get(i).getInboundRoute());
    }
    return routes;
  }

  public List<Line> getLines() {
    return lines;
  }

  public List<Vehicle> getActiveVehicles() {
    return activeVehicles;
  }
}