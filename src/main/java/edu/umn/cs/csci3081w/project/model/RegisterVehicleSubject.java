package edu.umn.cs.csci3081w.project.model;

public interface RegisterVehicleSubject {
  void registerVehicle(VehicleObserver registerVehicle);

  void notifyVehicle();
}
