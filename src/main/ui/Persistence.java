package ui;

import model.CardBox;
import model.CardBoxManager;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
/*
Persistence class represents saving and loading functionality.
When saving, the data from soleCardBoxManager (Model) is saved to savedCards.json
When loading, the data from savedCards.json is loaded to soleCardBoxManager.

This class is simply abstraction of of JsonReader and JsonWriter.
JsonReader and JsonWriter already tests all branches in Persistence class.
Hence it is redundant to have a seperate test class for persistence

JSONSerializationDemo has persistence method in the WorkRoomApp, which is equivalent to CommandLineInterface class here
However, persistence method needed to be reused for the GUI, thus persistence method has its own class to be reused
for gui. You can choose to run the app using GUI or CLI.
 */


public class Persistence {

    private CardBoxManager soleCardBoxManager; //model
    private JsonReader jsonReaderObj;
    private JsonWriter jsonWriterObj;
    private static final String JSON_STORE = "./data/persistence/savedCards.json";


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: Constructs Persistence object to initialize JsonWriter and JsonReader.
    public Persistence(CardBoxManager inputSoleCardBoxManager) {
        soleCardBoxManager = inputSoleCardBoxManager;
        jsonWriterObj = new JsonWriter(JSON_STORE);
        jsonReaderObj = new JsonReader(JSON_STORE,soleCardBoxManager);

    }



    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: saves cards and cardbox's timer from soleCardBoxManager to savedCards.json
    public void save() {
        try {
            jsonWriterObj.open();
            jsonWriterObj.write(soleCardBoxManager);
            jsonWriterObj.close();
            System.out.println("All Cards saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }


    //REQUIRES: X
    // MODIFIES: this
    // EFFECTS: saves cards from savedCards.json to soleCardBoxManager
    public void load() {
        try {
            //clear everything
            for (int cardBoxNum = 1; cardBoxNum <= soleCardBoxManager.getListOfCardBoxes().size(); cardBoxNum++) {
                CardBox cardBoxToClear = soleCardBoxManager.findCardBoxInCardBoxManager(cardBoxNum);
                cardBoxToClear.getTableOfCards().clear();
            }


            //soleCardBoxManager = jsonReaderObj.read();
            jsonReaderObj.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("There is nothing to read" + JSON_STORE);
        } catch (ParseException e) {
            System.out.println("Wrong date format at" + JSON_STORE);

        }


    }


    //getters and setters
    public CardBoxManager getSoleCardBoxManager() {
        return soleCardBoxManager;
    }
}
