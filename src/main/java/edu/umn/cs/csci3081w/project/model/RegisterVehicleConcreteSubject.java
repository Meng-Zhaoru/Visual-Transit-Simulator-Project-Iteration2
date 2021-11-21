package edu.umn.cs.csci3081w.project.model;

public class RegisterVehicleConcreteSubject implements RegisterVehicleSubject {
  private VehicleObserver registerVehicle;

  public void registerVehicle(VehicleObserver registerVehicle) {
    this.registerVehicle = registerVehicle;
  }

  public void notifyVehicle() {
    registerVehicle.generateObservedText();
  }

}

