package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisterVehicleConcreteSubjectTest {
  private Vehicle testObserver;
  private Route testRouteIn;
  private Route testRouteOut;
  private Line testLine;
  private RegisterVehicleSubject testRegisterVehicleSubject;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    testLine = new Line(10000, "testLine", "BUS_LINE", testRouteOut, testRouteIn);
    testObserver = new SmallBus(1, testLine, 1);
    testRegisterVehicleSubject = new RegisterVehicleConcreteSubject();
  }

  /**
   * Test if testRegisterVehicle works properly.
   */
  @Test
  void testRegisterVehicle() {
    testRegisterVehicleSubject.registerVehicle(testObserver);
    assertEquals(testObserver, testRegisterVehicleSubject.getRegisterVehicle());
  }

  @Test
  void testNotifyVehicle() {
    testRegisterVehicleSubject.registerVehicle(testObserver);
    testRegisterVehicleSubject.notifyVehicle();
    String result = testObserver.getObservedText();
    String expected = "Vehicle 1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "*Type: BUS" + System.lineSeparator()
        + "*Position: (-93.235071,44.97358)" + System.lineSeparator()
        + "*Passengers: 0" + System.lineSeparator()
        + "*CO2: 0, 0, 0, 0, 0" + System.lineSeparator();
    assertEquals(expected, result);
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testObserver = null;
    testRouteIn = null;
    testRouteOut = null;
    testLine = null;
    testRegisterVehicleSubject = null;
  }
}