package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassengerUnloaderTest {

  private Passenger testPassenger;
  private Passenger testPassenger2;
  private PassengerUnloader testPassengerUnloader = new PassengerUnloader();
  private List<Passenger> testPassengers = new ArrayList<>();
  private Stop testStop;

  @BeforeEach
  void setUp() {
    testPassenger = new Passenger(5, "Kobe Bryant");
    testPassenger2 = new Passenger(5, "Lebron James");
    testPassengers.add(testPassenger);
    testPassengers.add(testPassenger2);
    testStop = new Stop(5, "test stop 1", new Position(-93.243774, 44.972392));
  }

  @Test
  public void testLoadPassenger() {
    int result = testPassengerUnloader.unloadPassengers(testPassengers, testStop);
    assertEquals(2, result);
  }
}