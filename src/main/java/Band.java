import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Band {
  private int id;
  private String name;

  public Band(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String newName) {
    name = newName;
  }

  public void updateName() {
    String sql = "UPDATE bands SET name=:newName WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("newName", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO bands (name) VALUES (:name)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Band find(int id) {
    String sql = "SELECT * FROM bands WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id",id)
        .executeAndFetchFirst(Band.class);
    }
  }


  public void addVenue(int venueId) {
    String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id);";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("band_id", this.getId())
      .addParameter("venue_id", venueId)
      .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    String sql = "SELECT venues.* FROM bands " +
                "JOIN bands_venues ON (bands.id = bands_venues.band_id) " +
                "JOIN venues ON (bands_venues.venue_id = venues.id) " +
                "WHERE bands.id =:band_id";
    try (Connection con = DB.sql2o.open()) {
    List<Venue> venueList = con.createQuery(sql)
        .addParameter("band_id", this.getId())
        .executeAndFetch(Venue.class);
      return venueList;

    }
  }

  public void deleteVenues() {
    String sql = "DELETE FROM bands_venues WHERE band_id=:id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void deleteBand() {
    String sql = "DELETE FROM bands WHERE id=:id;" +
                 "DELETE FROM bands_venues WHERE band_id=:id;";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getId() == newBand.getId();
    }
  }
}
