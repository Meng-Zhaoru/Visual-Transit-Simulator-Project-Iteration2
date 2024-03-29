package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DieselTrainTest {
  private DieselTrain testDieselTrain;
  private Route testRouteIn;
  private Route testRouteOut;
  private Line testLine;

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

    testLine = new Line(10000, "testLine", "DIESEL_TRAIN", testRouteOut, testRouteIn);
    testDieselTrain = new DieselTrain(1, testLine, 20, 1.0);
  }

  /**
   * Test states after using constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testDieselTrain.getId());
    assertEquals(testLine, testDieselTrain.getLine());
    assertEquals(20, testDieselTrain.getCapacity());
    assertEquals(1.0, testDieselTrain.getSpeed());
  }

  /**
   * Test for getCurrentCO2Emission functionality.
   */
  @Test
  public void testGetCurrentCO2Emission() {
    Passenger newPassenger = new Passenger(1, "Ke");
    testDieselTrain.loadPassenger(newPassenger);
    assertEquals(8, testDieselTrain.getCurrentCO2Emission());
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testDieselTrain = null;
  }
}