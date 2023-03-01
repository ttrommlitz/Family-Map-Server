package dao;

/** Exception thrown when any issue occurs
 * when interacting with database **/
public class DataAccessException extends Exception {
    /**
     * Constructor that contains the
     * specified message
     * @param message
     */
    public DataAccessException(String message) {
        super(message);
    }
}
