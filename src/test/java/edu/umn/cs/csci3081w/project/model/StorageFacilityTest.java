package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageFacilityTest {

  private StorageFacility initializeStorageFacility;
  private StorageFacility testStorageFacility;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    initializeStorageFacility = new StorageFacility();
    testStorageFacility = new StorageFacility(3, 2, 1, 5);
  }

  /**
   * Test state after using constructor.
   */
  @Test
  public void testConstructorNormal() {
    assertEquals(0, initializeStorageFacility.getSmallBusesNum());
    assertEquals(0, initializeStorageFacility.getLargeBusesNum());
    assertEquals(0, initializeStorageFacility.getElectricTrainsNum());
    assertEquals(0, initializeStorageFacility.getDieselTrainsNum());
  }

  /**
   * Test state after using constructor.
   */
  @Test
  public void testConstructorWithParameter() {
    assertEquals(3, testStorageFacility.getSmallBusesNum());
    assertEquals(2, testStorageFacility.getLargeBusesNum());
    assertEquals(1, testStorageFacility.getElectricTrainsNum());
    assertEquals(5, testStorageFacility.getDieselTrainsNum());
  }

  /**
   * Test for incrementSmallBusesNum functionality.
   */
  @Test
  public void incrementSmallBusesNum() {
    testStorageFacility.incrementSmallBusesNum();
    assertEquals(4, testStorageFacility.getSmallBusesNum());
  }

  /**
   * Test for incrementLargeBusesNum functionality.
   */
  @Test
  public void testIncrementLargeBusesNum() {
    testStorageFacility.incrementLargeBusesNum();
    assertEquals(3, testStorageFacility.getLargeBusesNum());
  }

  /**
   * Test for incrementElectricTrainsNum functionality.
   */
  @Test
  public void testIncrementElectricTrainsNum() {
    testStorageFacility.incrementElectricTrainsNum();
    assertEquals(2, testStorageFacility.getElectricTrainsNum());
  }

  /**
   * Test for incrementDieselTrainsNum functionality.
   */
  @Test
  public void testIncrementBusesNum() {
    testStorageFacility.incrementDieselTrainsNum();
    assertEquals(6, testStorageFacility.getDieselTrainsNum());
  }


  /**
   * Test for decrementSmallBusesNum functionality.
   */
  @Test
  public void testDecrementSmallBusesNum() {
    testStorageFacility.decrementSmallBusesNum();
    assertEquals(2, testStorageFacility.getSmallBusesNum());
  }

  /**
   * Test for decrementLargeBusesNum functionality.
   */
  @Test
  public void testDecrementLargeBusesNum() {
    testStorageFacility.decrementLargeBusesNum();
    assertEquals(1, testStorageFacility.getLargeBusesNum());
  }

  /**
   * Test for decrementElectricTrainsNum functionality.
   */
  @Test
  public void testDecrementElectricTrainsNum() {
    testStorageFacility.decrementElectricTrainsNum();
    assertEquals(0, testStorageFacility.getElectricTrainsNum());
  }

  /**
   * Test for decrementDieselTrainsNum functionality.
   */
  @Test
  public void testDecrementDieselTrainsNum() {
    testStorageFacility.decrementDieselTrainsNum();
    assertEquals(4, testStorageFacility.getDieselTrainsNum());
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    initializeStorageFacility = null;
    testStorageFacility = null;
  }

}