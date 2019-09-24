import org.sql2o.*;
import java.util.List;
import java.security.InvalidParameterException;

public class EndangeredAnimal extends Animal {
  public String health;
  public String age;
  public boolean endangered;
  public String name;
  public int id;



  public static final String HEALTHY = "Healthy";
  public static final String ILL = "Ill";
  public static final String OKAY = "Okay";

  public static final String NEWBORN = "Newborn";
  public static final String YOUNG = "Young";
  public static final String ADULT = "Adult";

  public EndangeredAnimal(String name, String health, String age) {
    super(name);
    this.name = name;
    this.health = health;
    this.age = age;
    this.endangered = true;
  }

  public EndangeredAnimal(String name) {
    super(name);
    this.name = name;
    this.health = health;
    this.age = age;
    this.endangered = true;
  }

  public String getHealth() {
    return health;
  }

  public String getAge() {
    return age;
  }

  public String getName() { return name; }

  public int getId() { return id; }

  @Override
  public void checkFields() {
    if(name.equals("") ||
      ((!health.equals(EndangeredAnimal.HEALTHY)) &&
      (!health.equals(EndangeredAnimal.ILL)) &&
      (!health.equals(EndangeredAnimal.OKAY))) ||
      ((!age.equals(EndangeredAnimal.NEWBORN)) &&
      (!age.equals(EndangeredAnimal.YOUNG)) &&
      (!age.equals(EndangeredAnimal.ADULT)))
      ) {
      throw new InvalidParameterException("Please fill in all fields before submitting.");
    }
  }

  public static List<EndangeredAnimal> allEndangeredAnimals() {
    String sql = "SELECT * FROM animals WHERE endangered = true";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(EndangeredAnimal.class);
    }
  }

  public static EndangeredAnimal findEndangeredAnimal(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id = :id";
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("id", id)
        .executeAndFetchFirst(EndangeredAnimal.class);
    }
  }

  @Override
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, health, age, endangered) VALUES (:name, :health, :age, :endangered)";
      this.id = (int) con.createQuery(sql, true)
        .throwOnMappingFailure(false)
        .addParameter("name", this.name)
        .addParameter("health", this.health)
        .addParameter("age", this.age)
        .addParameter("endangered", this.endangered)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherEndangeredAnimal) {
    if (!(otherEndangeredAnimal instanceof EndangeredAnimal)) {
      return false;
    } else {
      EndangeredAnimal newEndangeredAnimal = (EndangeredAnimal) otherEndangeredAnimal;
      return this.getName().equals(newEndangeredAnimal.getName()) &&
        this.getHealth().equals(newEndangeredAnimal.getHealth()) &&
        this.getAge().equals(newEndangeredAnimal.getAge()) &&
        this.isEndangered() == newEndangeredAnimal.isEndangered();
    }
  }
}
