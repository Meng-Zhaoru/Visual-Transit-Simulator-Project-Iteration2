package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionTest {
  private double latitude = 1.0;
  private double longitude = 2.0;
  private Position testPosition;

  @BeforeEach
  void setUp() {
    testPosition = new Position(longitude, latitude);
  }

  @Test
  public void testConstructorNormal() {
    assertEquals(latitude, testPosition.getLatitude());
    assertEquals(longitude, testPosition.getLongitude());
  }
}