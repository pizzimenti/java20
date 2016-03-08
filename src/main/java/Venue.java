
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Venue {
  private int id;
  private String name;

  public Venue(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static List<Venue> all() {
    String sql = "SELECT * FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO venues (name) VALUES (:name)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    String sql = "SELECT * FROM venues WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id",id)
        .executeAndFetchFirst(Venue.class);
    }
  }

  public void addBand(int bandId) {
    String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id);";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("band_id", bandId)
      .addParameter("venue_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Band> getBands() {
    String sql = "SELECT bands.* FROM venues " +
                "JOIN bands_venues ON (venues.id = bands_venues.venue_id) " +
                "JOIN bands ON (bands_venues.band_id = bands.id) " +
                "WHERE venues.id =:venue_id";
    try (Connection con = DB.sql2o.open()) {
    List<Band> bandList = con.createQuery(sql)
        .addParameter("venue_id", this.getId())
        .executeAndFetch(Band.class);
      return bandList;

    }
  }

  public void deleteBands() {
    String sql = "DELETE FROM bands_venues WHERE venue_id=:id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getId() == newVenue.getId();
    }
  }
}
