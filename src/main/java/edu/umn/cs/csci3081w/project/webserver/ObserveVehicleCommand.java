package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.RegisterVehicleConcreteSubject;
import edu.umn.cs.csci3081w.project.model.RegisterVehicleSubject;
import edu.umn.cs.csci3081w.project.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

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
