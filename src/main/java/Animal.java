import org.sql2o.*;

import java.security.InvalidParameterException;
import java.util.List;


public class Animal {
  public int id;
  public String name;
  public boolean endangered;

  public Animal(String name) {
    this.name = name;
    this.endangered = false;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isEndangered() {
    return endangered;
  }

  public void checkFields() {
    if(name.equals("")) {
      throw new InvalidParameterException("Please fill in all fields before submitting.");
    }
  }

  public static List<Animal> all() {
    String sql = "SELECT * FROM animals";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(Animal.class);
    }
  }

  public static List<Animal> allNormal() {
    String sql = "SELECT * FROM animals WHERE endangered = false";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(Animal.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, endangered) VALUES (:name, :endangered)";
      this.id = (int) con.createQuery(sql, true)
        .throwOnMappingFailure(false)
        .addParameter("name", this.name)
        .addParameter("endangered", this.endangered)
        .executeUpdate()
        .getKey();
    }
  }

  public static Animal find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id = :id";
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("id", id)
        .executeAndFetchFirst(Animal.class);
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE animalId = :animalId";
      return con.createQuery(sql)
        .addParameter("animalId", this.getId())
        .executeAndFetch(Sighting.class);
    }
  }

  public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("name", name)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if (!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName()) &&
        this.isEndangered() == newAnimal.isEndangered();
    }
  }
}
