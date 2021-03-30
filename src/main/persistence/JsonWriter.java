package persistence;

import model.CardBoxManager;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


/*
Represents a writer that overwrites JSON representation of CardBoxManager to savedCards.json
 */
public class JsonWriter {
    private static final int TAB = 4; //indent in Json file
    private PrintWriter writer;
    private String destination; //savedCards.json file location

    //REQUIRES: X
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //REQUIRES: X
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination)); //you can choose between overwrite or append
    }


    //REQUIRES: X
    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(CardBoxManager inputCardBoxManager) {
        JSONObject json = inputCardBoxManager.toJson();
        saveToFile(json.toString(TAB));
    }


    //REQUIRES:X
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }


    //REQUIRES: X
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }



    //getters and setters

    public String getDestination() {
        return destination;
    }


}
