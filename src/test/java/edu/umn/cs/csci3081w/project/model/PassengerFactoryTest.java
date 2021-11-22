package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PassengerFactoryTest {

  private PassengerFactory testPassengerFactory;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;

    testPassengerFactory = new PassengerFactory();
  }

  /**
   * Test for generate functionality.
   */
  @Test
  public void testGenerate() {
    Passenger newPassenger = new Passenger(5, "Goldy");
    Passenger testPassenger = testPassengerFactory.generate(3, 1);
    assertTrue(testPassenger.equals(newPassenger));
  }

  /**
   * Test for nameGeneration functionality.
   */
  @Test
  public void testNameGeneration() {
    String name = PassengerFactory.nameGeneration();
    assertEquals("Goldy", name);
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testPassengerFactory = null;
  }
}