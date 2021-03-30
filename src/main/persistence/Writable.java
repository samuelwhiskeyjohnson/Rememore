package persistence;

import org.json.JSONObject;

/*
    Represents the necessary condition for CardBoxManager, CardBox, and Card to be writable to savedCards.json

 */
public interface Writable {


    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
