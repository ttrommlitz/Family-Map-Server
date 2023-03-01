package services;

import request.FillRequest;
import result.FillResult;
import model.Person;

/** Service to fill database with generated data
 * for the specified username **/
public class FillService extends Service {
    /**
     * Generates and fills database with data for
     * the current User. If data already exists, it is
     * deleted. By default, it fills 4 generations
     * @param request
     * @return FillResult
     */
    public FillResult fill(FillRequest request) {
        if (request.getGenerations() == null) {
            request.setGenerations(4);
        }
        return new FillResult();
    }

    private Person generateTree(String rootGender, int generations) {
        Person mother = null;
        Person father = null;
        if (generations >= 1) {
            mother = generateTree("f", generations - 1);
            father = generateTree("m", generations - 1);

            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

        }
        Person person = new Person();
        return person;
    }
}
