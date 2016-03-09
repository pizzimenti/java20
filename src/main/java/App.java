import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import org.sql2o.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      // model.put("bands", Band.all());
      model.put("venues", Venue.all());

      int selectedVenueId = Integer.parseInt(request.queryParams("venue"));
      Venue selectedVenue = Venue.find(selectedVenueId);
      List<Band> bandsByVenue = selectedVenue.getBands();
      String venueName = Venue.find(selectedVenueId).getName();

      model.put("listVenueName", venueName);
      model.put("bands", bandsByVenue);
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    get("/add-band", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/add-band.vtl");
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    post("/add-band", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String bandName = request.queryParams("band");
      int venueId = Integer.parseInt(request.queryParams("venue"));
      Band newBand = new Band(bandName);
      newBand.save();
      newBand.addVenue(venueId);

      response.redirect("/add-band");
      return null;
    });

    get("/add-venue", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/add-venue.vtl");
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    post("/add-venue", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String venueName = request.queryParams("venue");
      Venue newVenue = new Venue(venueName);
      newVenue.save();

      response.redirect("/add-venue");
      return null;
    });

    post("/add-venue-to-band", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();


      int venueId = Integer.parseInt(request.queryParams("venue-existing"));
      int bandId = Integer.parseInt(request.queryParams("band-existing"));
      addBandAndVenue(bandId, venueId);


      response.redirect("/add-venue");
      return null;
    });

    get("/band/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/band.vtl");

      Band band = Band.find(Integer.parseInt(request.params(":id")));
      List<Venue> venueList = band.getVenues();
      model.put("band", band);
      model.put("venueList", venueList);
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    post("/band/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String newBandName = request.queryParams("band-name");
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      band.setName(newBandName);
      band.updateName();

      List<Venue> venueList = band.getVenues();
      model.put("venues", Venue.all());
      model.put("bands", Band.all());
      model.put("template", "templates/band.vtl");
      model.put("band", band);
      model.put("venueList", venueList);
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    get("/delete-band/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/delete-band.vtl");

      Band band = Band.find(Integer.parseInt(request.params(":id")));
      List<Venue> venueList = band.getVenues();
      model.put("band", band);
      model.put("venueList", venueList);
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    post("/delete-band/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Band band = Band.find(Integer.parseInt(request.params(":id")));
      band.deleteBand();

      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    get("/delete-band-venues/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/delete-band-venues.vtl");

      Band band = Band.find(Integer.parseInt(request.params(":id")));
      List<Venue> venueList = band.getVenues();
      model.put("band", band);
      model.put("venueList", venueList);
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    post("/delete-band-venues/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Band band = Band.find(Integer.parseInt(request.params(":id")));
      band.deleteVenues();

      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

  } //end of main
  public static void addBandAndVenue(int bandId, int venueId) {
    String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id);";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("band_id", bandId)
      .addParameter("venue_id", venueId)
      .executeUpdate();
    }
  }
} //end of app
