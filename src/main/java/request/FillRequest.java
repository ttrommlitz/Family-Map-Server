package request;

/** Contains the HTTP POST Request
 * for filling database **/
public class FillRequest {
    private String username;
    private Integer generations;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGenerations() {
        return generations;
    }

    public void setGenerations(Integer generations) {
        this.generations = generations;
    }
}
