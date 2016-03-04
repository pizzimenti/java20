import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Band.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Band newBand = new Band("Cold Play");
    Band anotherBand = new Band("Cold Play");
    assertTrue(newBand.equals(anotherBand));
  }

  @Test
  public void save_returnTrueIfNamesAreSame() {
    Band newBand = new Band("Cold Play");
    newBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(newBand.getId(), savedBand.getId());
  }

  @Test
  public void find_findsObjectInDatabaseById() {
    Band newBand = new Band("Cold Play");
    newBand.save();
    Band savedBand = Band.find(newBand.getId());
    assertTrue(newBand.equals(savedBand));
  }

  @Test
  public void addVenue_addsVenueToBand() {
    Venue myVenue = new Venue("Rose Quarter");
    myVenue.save();
    Band myBand = new Band("Clean the litter box");
    myBand.save();
    myBand.addVenue(myVenue.getId());
    Venue savedVenue = myBand.getVenues().get(0);
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void getVenues_getsVenuesFromBand() {
    Venue myVenue = new Venue("Rose Quarter");
    myVenue.save();
    Venue myVenueTwo = new Venue("Expo Center");
    myVenueTwo.save();
    Band myBand = new Band("Cold Play");
    myBand.save();
    Band myBandTwo = new Band("Nirvana");
    myBandTwo.save();
    myBand.addVenue(myVenue.getId());
    myBand.addVenue(myVenue.getId());
    List savedVenues = myBand.getVenues();
    assertEquals(savedVenues.size(), 2);
  }

  @Test
  public void deleteVenues_DeleteAllVenuesFromBand() {
    Venue myVenue = new Venue("Rose Quarter");
    myVenue.save();
    Band myBand = new Band("Clean the litter box");
    myBand.save();
    myBand.addVenue(myVenue.getId());
    myBand.deleteVenues();
    assertEquals(0, myVenue.getBands(myVenue.getId()).size());
  }
}
