package edu.umn.cs.csci3081w.project.model;

public class Counter {

  public int routeIdCounter = 10;
  public int stopIdCounter = 100;
  public int smallBusIdCounter = 1000;
  public int largeBusIdCounter = 2000;
  public int electricTrainIdCounter = 3000;
  public int dieselTrainIdCounter = 4000;
  public int lineIdCounter = 10000;

  public Counter() {

  }

  public int getRouteIdCounterAndIncrement() {
    return routeIdCounter++;
  }

  public int getStopIdCounterAndIncrement() {
    return stopIdCounter++;
  }

  public int getSmallBusIdCounterAndIncrement() {
    return smallBusIdCounter++;
  }

  public int getLargeBusIdCounterAndIncrement() {
    return largeBusIdCounter++;
  }

  public int getElectricTrainIdCounterAndIncrement() {
    return electricTrainIdCounter++;
  }

  public int getDieselTrainIdCounterAndIncrement() {
    return dieselTrainIdCounter++;
  }

  public int getLineIdCounterAndIncrement() {
    return lineIdCounter++;
  }

}
