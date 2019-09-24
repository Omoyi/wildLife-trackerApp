import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.security.InvalidParameterException;

public class Sighting {
  private int id;
  private Timestamp dateTime;
  private String location;
  private String rangerName;
  private int animalId;

  public Sighting(String location, String rangerName, int animalId) {
    this.location = location;
    this.rangerName = rangerName;
    this.animalId = animalId;
  }

  public int getId() {
    return id;
  }

  public String getLocation() {
    return location;
  }

  public String getRangerName() {
    return rangerName;
  }

  public int getAnimalId() {
    return animalId;
  }

  public Timestamp getDateTime() {
    return dateTime;
  }

  public void checkFields() {
    if(rangerName.equals("") || location.equals("")) {
      throw new InvalidParameterException("Please fill in all fields before submitting.");
    }
  }

  private String getFormattedDate(){
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
    return formatter.format(dateTime);
  }

  public static List <Sighting> all() {
    String sql = "SELECT * FROM sightings";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Sighting.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (dateTime, location, rangerName, animalId) VALUES (now(), :location, :rangerName, :animalId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("location", this.location)
        .addParameter("rangerName", this.rangerName)
        .addParameter("animalId", this.animalId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
    }
  }

  public void update(String location, String rangerName, int animalId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE sightings SET location = :location, rangerName = :rangerName, animalId = :animalId WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("location", location)
        .addParameter("rangerName", rangerName)
        .addParameter("animalId", animalId)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM sightings WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
    public boolean equals(Object otherSighting) {
      if (!(otherSighting instanceof Sighting)) {
        return false;
      } else {
        Sighting newSighting = (Sighting) otherSighting;
        return this.getLocation().equals(newSighting.getLocation()) &&
          this.getRangerName().equals(newSighting.getRangerName()) &&
          this.getAnimalId() == newSighting.getAnimalId();
    }
  }
}
