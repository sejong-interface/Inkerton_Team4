package com.example.aninterface;

public class Check {

  String name;
  String unit;



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Check(String name, String unit) {
    this.name = name;
    this.unit = unit;
  }
}
