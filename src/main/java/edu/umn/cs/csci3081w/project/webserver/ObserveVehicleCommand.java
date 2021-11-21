package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

public class ObserveVehicleCommand extends SimulatorCommand {
  private VisualTransitSimulator simulator;

  public ObserveVehicleCommand(VisualTransitSimulator simulator) {
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
    JsonObject data = new JsonObject();
    data.addProperty("command", "observedVehicle");
    String text = "";
    text = simulator.registerVehicle.getObservedText();
    data.addProperty("text", text);
    session.sendJson(data);
  }
}
