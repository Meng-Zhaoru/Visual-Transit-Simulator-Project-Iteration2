package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

public class LineIssueCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public LineIssueCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Initializes the lines information for the simulation.
   *
   * @param session current simulation session
   * @param command the initialize routes command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    int issueLineId = command.get("id").getAsInt();
    simulator.issueLineIdList.add(issueLineId);
    for(int i = 0; i < simulator.getLines().size(); i ++) {
      if(simulator.getLines().get(i).getId() == issueLineId) {
        simulator.getLines().get(i).increaseTimeToStartBy(10);
      }
    }
    for(int i = 0; i < simulator.getActiveVehicles().size(); i++){
      if(simulator.getActiveVehicles().get(i).getLine().getId() == issueLineId) {
        simulator.getActiveVehicles().get(i).increaseTimeToStartBy(10);
      }
    }
  }
}
