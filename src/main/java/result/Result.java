package result;

/** Base Result class. Contains a message
 * string and a success boolean for each
 * function call **/
public abstract class Result {
    /** Message containing result of
     * specified operation **/
    protected String message;

    /** Boolean representing whether
     * operation was successful or not **/
    protected boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
