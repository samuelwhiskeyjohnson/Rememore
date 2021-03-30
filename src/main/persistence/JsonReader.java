package persistence;


import model.Card;
import model.CardBox;
import model.CardBoxManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;


/*
Represents a reader that reads CardBoxManager from savedCards.json
 */
public class JsonReader {

    private String source; //source is the location of savedCards.json
    private CardBoxManager soleCardBoxManager; //location of the model savedCards.json is loaded to
    private int largestCardID;

    //REQUIRES: X
    // EFFECTS: constructs reader to read from source file savedCards.json
    //CITE: JsonReader() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JsonReader(String source, CardBoxManager inputCardBoxManager) {
        this.soleCardBoxManager = inputCardBoxManager;
        this.source = source;
    }


    //REQUIRES: X
    //MODIFIES: this
    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    // throws JSONException if there is nothing to read from the file.
    // throws ParseException if the date in the json file is wrongly formatted.
    //date must be formatted as yyyy-MM-dd HH:mm:ss
    //CITE: read() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public void read() throws IOException, JSONException, ParseException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        //return loadCardBoxManager(jsonObject);
        loadCardBoxManager(jsonObject);
    }


    //REQUIRES: X
    //MODIFIES: X
    // EFFECTS: reads source file savedCards.json as string and returns it
    //CITE: readFile() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //REQUIRES: X
    //MODIFIES: this
    // EFFECTS: load cardBoxManager with CardBoxes and returns it
    //CITE: loadCardBoxManager() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadCardBoxManager(JSONObject jsonObjectCardBoxManager) throws ParseException {
        //String name = jsonObject.getString("name");
        //WorkRoom wr = new WorkRoom(name); no need to make a new one

        loadCardBoxes(jsonObjectCardBoxManager);
        //return soleCardBoxManager;
    }

    //REQUIRES: X
    // MODIFIES: this
    // EFFECTS: parses card boxes in cardBoxManager from JSON object
    //CITE: loadCardBoxes() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadCardBoxes(JSONObject jsonObjectCardBoxManager) throws ParseException {
        JSONArray jsonArray = jsonObjectCardBoxManager.getJSONArray("cardBoxes");
        for (Object cardBoxInCardBoxManager : jsonArray) {
            JSONObject jsonCardBoxInCardBoxManager = (JSONObject) cardBoxInCardBoxManager;
            parseCardBox(jsonCardBoxInCardBoxManager);
        }

    }


    //REQUIRES: X
    //MODIFIES: this
    // EFFECTS: parses a cardBox to get card box number from JSON object and returns it
    //CITE: parseCardBox() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void parseCardBox(JSONObject jsonObjectCardBox) throws ParseException {
        int cardBoxNumber = jsonObjectCardBox.getInt("cardBoxNumber");
        int boxMinutesTimer = jsonObjectCardBox.getInt("boxMinutesTimer");
        soleCardBoxManager.findCardBoxInCardBoxManager(cardBoxNumber).setBoxMinutesTimer(boxMinutesTimer);

        loadCards(cardBoxNumber,jsonObjectCardBox);
    }


    //REQUIRES: cardBoxNumber is one of 1,2,3,4,5
    // MODIFIES: this
    // EFFECTS: parses cards from cardBox JSON object and adds them to corresponding card box
    //CITE: loadCards() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadCards(int cardBoxNumber, JSONObject jsonObjectCardBox) throws ParseException {

        JSONArray jsonArray = jsonObjectCardBox.getJSONArray("cards");
        //for finding out the largest card ID to determine the cardID when new card is added after application restarts

        for (Object cardInCardBox : jsonArray) {
            JSONObject jsonCardInCardBox = (JSONObject) cardInCardBox;
            loadCard(cardBoxNumber, jsonCardInCardBox);



        }
    }


    //REQUIRES: cardBoxNumber is one of 1,2,3,4,5
    // MODIFIES: this
    // EFFECTS: parses a card from JSON object and adds it to the card box with corresponding cardBoxNumber
    //CITE: loadCard() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadCard(int cardBoxNumber, JSONObject jsonObject) throws ParseException { //jsonObject made from card
        CardBox loadCurrentCardBox = soleCardBoxManager.findCardBoxInCardBoxManager(cardBoxNumber);

        String backInfo = jsonObject.getString("backInfo");
        int cardID = jsonObject.getInt("cardID");
//        int timeUntilTestedAgain = jsonObject.getInt("timeUntilTestedAgain");
        String frontInfo = jsonObject.getString("frontInfo");
//        Category category = Category.valueOf(jsonObject.getString("category"));
//        Thingy thingy = new Thingy(name, category);
        String startTimeString = jsonObject.getString("startTime");


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date loadStartTime = dateFormat.parse(startTimeString);

        Card loadCard = new Card(frontInfo, backInfo, loadStartTime, cardID);
        loadCurrentCardBox.loadCard(loadCard);
//            e.printStackTrace();
        //System.out.println("unable to parse date strings");

        if (cardID > largestCardID) {
            largestCardID = cardID;
        }
        Card.setNextCardID(largestCardID + 1);

    }



    //getters and setters


    public CardBoxManager getSoleCardBoxManager() {
        return soleCardBoxManager;
    }

    public String getSource() {
        return source;
    }
}
