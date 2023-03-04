package services;

import dao.DataAccessException;
import dao.Database;
import result.Result;

import java.sql.Connection;

/** Base Service class **/
public abstract class Service {
    protected static Database db;
    protected static Connection conn;
    public Service() {
        try {
            if (db == null) {
                db = new Database();
            }
            conn = db.getConnection();
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
    }

    public void closeConnection(boolean commit) {
        db.closeConnection(commit);
    }

    public Connection getConnection() {
        return conn;
    }

    protected void handleException(DataAccessException e, Result result) {
        e.printStackTrace();
        result.setMessage("Error: " + e.getMessage());
        result.setSuccess(false);
        db.closeConnection(false);
    }
}
