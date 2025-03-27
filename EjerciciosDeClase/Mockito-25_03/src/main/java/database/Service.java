package database;

public class Service {

    private Database database;

    public Service(Database database) {
        this.database = database;
    }

    public boolean query(String query) {
        return database.isAvailable();
    }

    @Override
    public String toString() {
        return "Users in the database : " + String.valueOf(database.getNumberofUsers());
    }
}