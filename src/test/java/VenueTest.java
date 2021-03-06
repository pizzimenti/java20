
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Venue.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Venue newVenue = new Venue("Rose Quarter");
    Venue anotherVenue = new Venue("Rose Quarter");
    assertTrue(newVenue.equals(anotherVenue));
  }

  @Test
  public void save_returnTrueIfNamesAreSame() {
    Venue newVenue = new Venue("Rose Quarter");
    newVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(newVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_findsObjectInDatabaseById() {
    Venue newVenue = new Venue("Rose Quarter");
    newVenue.save();
    Venue savedVenue = Venue.find(newVenue.getId());
    assertTrue(newVenue.equals(savedVenue));
  }

  @Test
  public void addVenue_addsBandToVenue() {
    Band myBand = new Band("Coldplay");
    myBand.save();
    Venue myVenue = new Venue("Rose Quarter");
    myVenue.save();
    myVenue.addBand(myBand.getId());
    Band savedBand = myVenue.getBands().get(0);
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void getBands_getsBandsFromVenue() {
    Venue myVenue = new Venue("Rose Quarter");
    myVenue.save();
    Band myBand = new Band("Coldplay");
    myBand.save();
    Band myBandTwo = new Band("Pink Martini");
    myBandTwo.save();
    myVenue.addBand(myBand.getId());
    myVenue.addBand(myBandTwo.getId());
    List savedBands = myVenue.getBands();
    assertEquals(2, savedBands.size());
  }

    @Test
    public void deleteBands_DeleteAllBandsFromVenue() {
      Venue myVenue = new Venue("Rose Quarter");
      myVenue.save();
      Band myBand = new Band("Coldplay");
      myBand.save();
      myVenue.addBand(myBand.getId());
      myVenue.deleteBands();
      assertEquals(0, myVenue.getBands().size());
    }
}
