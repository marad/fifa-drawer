package com.prondzyn.fifadrawer.entities;

import java.io.Serializable;

public abstract class Team implements Serializable {

  protected String name;
  protected Double rank;

  public String getName() {
    return name;
  }

  public Double getRank() {
    return rank;
  }

  public boolean asPowerfulAs(Team team) {
    return rank.equals(team.rank);
  }
  
  public boolean notAsPowerfulAs(Team team) {
    return !asPowerfulAs(team);
  }
  
  @Override
  public String toString() {
    return name;
  }

}