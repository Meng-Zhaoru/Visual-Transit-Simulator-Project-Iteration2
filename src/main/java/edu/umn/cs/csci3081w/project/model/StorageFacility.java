package edu.umn.cs.csci3081w.project.model;

public class StorageFacility {
  private int smallBusesNum;
  private int largeBusesNum;
  private int electricTrainsNum;
  private int dieselTrainsNum;

  /**
   * Constructor for a storage facility.
   */
  public StorageFacility() {
    smallBusesNum = 0;
    largeBusesNum = 0;
    electricTrainsNum = 0;
    dieselTrainsNum = 0;
  }

  /**
   * Constructor for a storage facility.
   */
  public StorageFacility(int smallBusesNum, int largeBusesNum,
                         int electricTrainsNum, int dieselTrainsNum) {
    this.smallBusesNum = smallBusesNum;
    this.largeBusesNum = largeBusesNum;
    this.electricTrainsNum = electricTrainsNum;
    this.dieselTrainsNum = dieselTrainsNum;
  }

  public int getSmallBusesNum() {
    return smallBusesNum;
  }

  public int getLargeBusesNum() {
    return largeBusesNum;
  }

  public int getElectricTrainsNum() {
    return electricTrainsNum;
  }

  public int getDieselTrainsNum() {
    return dieselTrainsNum;
  }

  public void setSmallBusesNum(int smallBusesNum) {
    this.smallBusesNum = smallBusesNum;
  }

  public void setLargeBusesNum(int largeBusesNum) {
    this.largeBusesNum = largeBusesNum;
  }

  public void setElectricTrainsNum(int electricTrainsNum) {
    this.electricTrainsNum = electricTrainsNum;
  }

  public void setDieselTrainsNum(int dieselTrainsNum) {
    this.dieselTrainsNum = dieselTrainsNum;
  }

  public void decrementSmallBusesNum() {
    smallBusesNum--;
  }

  public void decrementLargeBusesNum() {
    largeBusesNum--;
  }

  public void decrementElectricTrainsNum() {
    electricTrainsNum--;
  }

  public void decrementDieselTrainsNum() {
    dieselTrainsNum--;
  }

  public void incrementSmallBusesNum() {
    smallBusesNum++;
  }

  public void incrementLargeBusesNum() {
    largeBusesNum++;
  }

  public void incrementElectricTrainsNum() {
    electricTrainsNum++;
  }

  public void incrementDieselTrainsNum() {
    dieselTrainsNum++;
  }
}
