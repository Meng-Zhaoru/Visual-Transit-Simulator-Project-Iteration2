package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassengerLoaderTest {

  private Passenger testPassenger;
  private PassengerLoader testPassengerLoader = new PassengerLoader();
  private List<Passenger> testPassengers = new ArrayList<>();

  @BeforeEach
  void setUp() {
    testPassenger = new Passenger(5, "Kobe Bryant");

    Passenger testPassenger2 = new Passenger(5, "Lebron James");
    testPassengers.add(testPassenger2);
  }

  @Test
  public void testLoadPassenger() {
    int result = testPassengerLoader.loadPassenger(testPassenger, 5, testPassengers);
    assertEquals(1, result);
  }
}