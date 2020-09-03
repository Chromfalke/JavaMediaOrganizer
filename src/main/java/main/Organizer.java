package main;

import java.io.File;
import java.net.URISyntaxException;

import database.DBMaster;

public class Organizer
{
    private static String directory;
    private static IDMaster idbase;
    private static DBMaster database;

    public static void main(String[] args) {
        int abort = setup();
        if (abort==1) {
            return;
        }
        shutdown();
    }
    
    // Check the database, start the gui
    public static int setup() {
        try {
            directory = new File(Organizer.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();
        }
        catch (URISyntaxException e) {
            System.out.println("Could not locate directory, abborting session");
            return 1;
        }
        idbase = new IDMaster(directory);
        database = new DBMaster();
        return 0;
    }
    
    // Save current state and shutdown properly
    public static void shutdown() {
        idbase.save(directory);
        database.shutdown();
    }
}
