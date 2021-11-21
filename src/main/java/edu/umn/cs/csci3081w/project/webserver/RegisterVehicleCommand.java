package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

public class RegisterVehicleCommand extends SimulatorCommand {
  private VisualTransitSimulator simulator;

  public RegisterVehicleCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Retrieves vehicles information from the simulation.
   *
   * @param session current simulation session
   * @param command the get vehicles command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    simulator.setRegisterCommand(command);
    simulator.setUpdateRegisterInfo(true);
  }
}
