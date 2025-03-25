package database;

public abstract class Database {

    public boolean isAvailable() {
        // currently not implemented, as this is just demo used in a software test
        return false;
    }
    public abstract int getNumberofUsers();
}