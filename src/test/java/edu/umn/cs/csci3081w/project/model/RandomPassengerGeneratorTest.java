package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomPassengerGeneratorTest {

  private RandomPassengerGenerator testRandomPassengerGenerator;
  private List<Stop> stops = new ArrayList<Stop>();
  private List<Double> probabilities = new ArrayList<Double>();

  @BeforeEach
  void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;

    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stops.add(stop1);
    stops.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    probabilities.add(0.25);
    probabilities.add(0.3);
    testRandomPassengerGenerator = new RandomPassengerGenerator(stops, probabilities);
  }

  @Test
  public void testConstructorNormal() {
    assertEquals(stops, testRandomPassengerGenerator.getStops());
    assertEquals(probabilities, testRandomPassengerGenerator.getProbabilities());
  }

  @Test
  void generatePassengers() {
    int result = testRandomPassengerGenerator.generatePassengers();
    assertEquals(1, result);
  }
}