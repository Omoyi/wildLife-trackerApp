import org.sql2o.*;

public class DB {
//  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker", "wecode", "1234");

  static String connectionString = "jdbc:postgresql://ec2-174-129-18-42.compute-1.amazonaws.com:5432/dfhl6tfoet6kmg"; //!
  static Sql2o sql2o = new Sql2o(connectionString, "ejiocljhmacryk", "ee86a57fef54ecaa573ffebf3b2acfca04b6040d290f36ed041e971f18c73b1c");
}
