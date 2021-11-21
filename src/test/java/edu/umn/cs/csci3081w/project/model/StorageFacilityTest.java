//package edu.umn.cs.csci3081w.project.model;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class StorageFacilityTest {
//  private int busesNum = 10;
//  private int trainsNum = 11;
//  private StorageFacility testStorageFacility;
//
//  @BeforeEach
//  void setUp() {
//    testStorageFacility = new StorageFacility(busesNum, trainsNum);
//  }
//
//  @Test
//  public void testConstructorNormal() {
//    StorageFacility defaultStorageFacility = new StorageFacility();
//    assertEquals(0, defaultStorageFacility.getBusesNum());
//    assertEquals(0, defaultStorageFacility.getTrainsNum());
//    assertEquals(10, testStorageFacility.getBusesNum());
//    assertEquals(11, testStorageFacility.getTrainsNum());
//  }
//}