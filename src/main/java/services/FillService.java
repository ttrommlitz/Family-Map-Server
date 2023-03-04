package services;

import com.google.gson.Gson;
import dao.DataAccessException;
import dao.EventDao;
import dao.PersonDao;
import request.FillRequest;
import result.FillResult;
import model.*;
import dao.UserDao;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.UUID;

/** Service to fill database with generated data
 * for the specified username **/
public class FillService extends Service {
    private static FNameData femaleNames;
    private static MNameData maleNames;
    private static SNameData surnames;
    private static LocationData locations;
    private PersonDao personDao;
    private EventDao eventDao;
    private FillResult result;
    private boolean beingCalledByOtherService;
    private Integer personCount;
    private Integer eventCount;

    private Integer numGenerations;
    private static final Integer CURRENT_YEAR = 2023;
    private static final Integer GENERATIONAL_DIFFERENCE = 30;
    public FillService(boolean beingCalledByOtherService) {
        try {
            maleNames = (maleNames == null) ? (MNameData)gsonMethod("json/mnames.json", MNameData.class) : maleNames;
            femaleNames = (femaleNames == null) ? (FNameData)gsonMethod("json/fnames.json", FNameData.class) : femaleNames;
            surnames = (surnames == null) ? (SNameData)gsonMethod("json/snames.json", SNameData.class) : surnames;
            locations = (locations == null) ? (LocationData)gsonMethod("json/locations.json", LocationData.class) : locations;
            personDao = new PersonDao(conn);
            eventDao = new EventDao(conn);
            result = new FillResult();
            this.beingCalledByOtherService = beingCalledByOtherService;
            personCount = 0;
            eventCount = 0;
        } catch (IOException e) {
            result.setMessage("Error: " + e.getMessage());
            result.setSuccess(false);
            if (!beingCalledByOtherService) {
                db.closeConnection(false);
            }
        }
    }
    /**
     * Generates and fills database with data for
     * the current User. If data already exists, it is
     * deleted. By default, it fills 4 generations
     * @param request
     * @return FillResult
     */
    public FillResult fill(FillRequest request) {
        if (result.getMessage() != null) {
            return result;
        }
        try {
            ClearService clearService = new ClearService();
            clearService.clearByUsername(request.getUsername());
            UserDao userDao = new UserDao(conn);
            User user = userDao.find(request.getUsername());
            if (user == null) {
                throw new DataAccessException("Given username does not exist");
            }
            numGenerations = request.getGenerations();
            Person rootPerson = generateTree(user.getGender(), numGenerations, user, CURRENT_YEAR);
            personDao.insert(rootPerson);
            personCount++;
            result.setMessage("Successfully added " + personCount + " persons and " + eventCount + " events to the database.");
            result.setSuccess(true);
            if (!beingCalledByOtherService) {
                db.closeConnection(true);
            }
        } catch (DataAccessException e) {
            result.setMessage("Error: " + e.getMessage());
            result.setSuccess(false);
            if (!beingCalledByOtherService) {
                db.closeConnection(false);
            }
        }
        return result;
    }

    private Person generateTree(String rootGender, int generations, User rootUser, Integer year) throws DataAccessException {
        Person mother = null;
        Person father = null;
        if (generations >= 1) {
            mother = generateTree("f", generations - 1, rootUser, year - GENERATIONAL_DIFFERENCE);
            father = generateTree("m", generations - 1, rootUser, year - GENERATIONAL_DIFFERENCE);

            addMarriageEvent(mother, father, year);
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            personDao.insert(mother);
            personDao.insert(father);
            personCount = personCount + 2;
        }

        Person person;
        String motherID = (mother != null) ? mother.getPersonID() : null;
        String fatherID = (father != null) ? father.getPersonID() : null;
        // This will only be true if the current person is the root User
        if (generations == numGenerations) {
            person = new Person(rootUser.getPersonID(), rootUser.getUsername(), rootUser.getFirstName(),
                    rootUser.getLastName(), rootGender, motherID, fatherID, null);
        } else {
            String personID = UUID.randomUUID().toString();
            String[] firstNames = rootGender.equals("m") ? maleNames.getData() : femaleNames.getData();
            String firstName = firstNames[(int) (firstNames.length * Math.random())];
            String lastName = surnames.getData()[(int) (surnames.getData().length * Math.random())];
            person = new Person(personID, rootUser.getUsername(), firstName, lastName, rootGender,
                    fatherID, motherID, null);
        }
        addBirthAndDeath(person, year);
        return person;
    }

    private Object gsonMethod(String filename, Type type) throws FileNotFoundException {
        Reader reader = new FileReader(filename);
        Gson gson = new Gson();
        return gson.fromJson(reader, type);
    }

    private void addMarriageEvent(Person mother, Person father, Integer currentYear) throws DataAccessException {
        //
        Integer motherBirthYear = currentYear - 20;
        Integer fatherBirthYear = currentYear - 20;
        Integer marriageYear = Math.min(motherBirthYear, fatherBirthYear) + 20 + (int)(10 * Math.random());
        Location marriageLocation = locations.getData()[(int)(locations.getData().length * Math.random())];
        String motherMarriageID = UUID.randomUUID().toString();
        String fatherMarriageID = UUID.randomUUID().toString();
        Event motherMarriage = new Event(motherMarriageID, mother.getAssociatedUsername(), mother.getPersonID(),
                marriageLocation.getLatitude(), marriageLocation.getLongitude(), marriageLocation.getCountry(),
                marriageLocation.getCity(), "Marriage", marriageYear);
        Event fatherMarriage = new Event(fatherMarriageID, father.getAssociatedUsername(), father.getPersonID(),
                marriageLocation.getLatitude(), marriageLocation.getLongitude(), marriageLocation.getCountry(),
                marriageLocation.getCity(), "Marriage", marriageYear);

        eventDao.insert(motherMarriage);
        eventDao.insert(fatherMarriage);
        eventCount = eventCount + 2;
    }

    private void addBirthAndDeath(Person person, Integer currentYear) throws DataAccessException {
        Integer birthYear = currentYear - 5 - (int)(15 * Math.random());
        Integer deathYear = birthYear + 60 + (int)(20 * Math.random());

        Location birthLocation = locations.getData()[(int)(locations.getData().length * Math.random())];
        String birthID = UUID.randomUUID().toString();
        Event personBirth = new Event(birthID, person.getAssociatedUsername(), person.getPersonID(), birthLocation.getLatitude(),
                birthLocation.getLongitude(), birthLocation.getCountry(), birthLocation.getCity(), "Birth", birthYear);

        eventDao.insert(personBirth);
        eventCount++;

        Location deathLocation = locations.getData()[(int) (locations.getData().length * Math.random())];
        String deathID = UUID.randomUUID().toString();
        Event childDeath = new Event(deathID, person.getAssociatedUsername(), person.getPersonID(), deathLocation.getLatitude(),
                deathLocation.getLongitude(), deathLocation.getCountry(), deathLocation.getCity(), "Death", deathYear);

        eventDao.insert(childDeath);
        eventCount++;
    }
}
