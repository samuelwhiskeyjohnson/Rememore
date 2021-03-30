package model;


import exception.NoCardFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.*;
/*
Leitner system is a studying technique that uses 1) flash cards and 2) boxes.
For more information about the Leitner system, please read README.md.
CardBox class represents a card box that users put the flash card in to determine
when to study the flash card next time.

 */

public class CardBox implements Writable {

    //Fields------------------------------------------------------------------

    private final List<Card> tableOfCards; //holds the cards in this particular box
    private final List<Card> testableTableOfCards; //holds the cards that are testable (card timer ran out)


    private final int cardBoxNum; //cardBoxNum is unique and is one of 1,2,3,4,5
    private int boxMinutesTimer; //timer associated with the box

    //Constructors------------------------------------------------------------------

/*  REQUIRES:
    inputCardBoxNum is one of 1,2,3,4,5.
    inputBoxTimer >=0 as timer can't be negative
    EFFECTS:
    Constructs a CardBox object with
    tableOfCards is set a new empty array list. tableOfCards is the cards in the CardBox object.
    cardBoxNum is set with given inputCardBoxNum.
    For relationship between cardBoxNum, timer, frequency of testing, refer to cardBoxManager constructor EFFECTS
    boxMinutesTimer is set given inputBoxTimer
    testableTableOfCards is set new empty list
    */
    public CardBox(int inputCardBoxNum, int inputBoxTimer) {
        tableOfCards = new ArrayList<>();
        cardBoxNum = inputCardBoxNum;
        boxMinutesTimer = inputBoxTimer;
        testableTableOfCards = new ArrayList<>();
    }


    //Public Methods----------------------------------------------------------------

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: constructs a new card given a card's front side information and back side information and add the card
    //to the tableOfCards and startTime is set to the current time when card is added.
    public void addCard(String inputFrontInfo, String inputBackInfo) {

        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        //make a card
        Card newCard = new Card(inputFrontInfo, inputBackInfo, currentDate);

        //add card into the table of cards
        tableOfCards.add(newCard);
    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: load card into tableOfCards given inputLoadCard from savedCards.json
    public void loadCard(Card inputLoadCard) {

        //add card into the table of cards
        tableOfCards.add(inputLoadCard);
    }

    //REQUIRES: inputID must be existing card ID in the tableOfCards.
    //MODIFIES: this
    //EFFECTS: removes the card given inputID in the tableOfCards
    public void removeCard(int inputID) {
        try {
            Card cardToRemoveFound = findCardInCardBox(inputID);

            //if card is found
            if (cardToRemoveFound != null) {
                //remove the card
                tableOfCards.remove(cardToRemoveFound);
            }
        } catch (NoCardFoundException e) {
            System.out.println("Error when removing card");
            System.out.println(e.getMessage());

        }


    }

    //REQUIRES: inputID must be existing card ID in the tableOfCards.
    //MODIFIES: this
    //EFFECTS: modify the content of the front side (question) and back side (answer) of the card found given inputID
    public void modifyCard(int inputID, String inputModifiedFrontInfo, String inputModifiedBackInfo) {
        try {
            Card cardToModifyFound = findCardInCardBox(inputID);
            //if card is found
            if (cardToModifyFound != null) {
                //change the front and back info
                cardToModifyFound.setFrontInfo(inputModifiedFrontInfo);
                cardToModifyFound.setBackInfo(inputModifiedBackInfo);
            }
        } catch (NoCardFoundException e) {
            System.out.println("Error when modifying card");
            System.out.println(e.getMessage());

        }


    }

    //REQUIRES: inputID must be existing card ID in the tableOfCards.
    //MODIFIES: this, inputToCardBox
    //EFFECTS: moves the card from this box object to inputToCardBox given inputID of a card
    public void moveCardToDifferentBox(int inputID, CardBox inputToCardBox) {
        try {
            Card cardToMoveFound = findCardInCardBox(inputID);
            //if card is found
            if (cardToMoveFound != null) {
                //move the card to another box
                inputToCardBox.getTableOfCards().add(cardToMoveFound);
                tableOfCards.remove(cardToMoveFound);
            }
        } catch (NoCardFoundException e) {
            System.out.println("Error when moving card to different box");
            System.out.println(e.getMessage());
        }


    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: shuffles testableTableOfCards to get ready to test the cards.
    public boolean readyToTestCards() {
        //find cards that have expired timer
        findCardsToTest();
        //if there are cards in the testableTableOfCards
        if (testableTableOfCards.size() != 0) {
            //shuffle the testableTableOfCards
            Collections.shuffle(testableTableOfCards);
            return true;
        }
        return false;
        //otherwise, there is no shuffle if there are no cards in the testableTableOfCards
    }

    //REQUIRES: inputBoxTimer >=0 as timer can't be negative
    //MODIFIES: this
    //EFFECTS: overwrite the current box timer to inputBoxTimer
    public void modifyBoxDueTimer(int inputBoxTimer) {
        setBoxMinutesTimer(inputBoxTimer);

    }

    //REQUIRES: inputID must be existing card ID in the tableOfCards.
    //MODIFIES: X
    //EFFECTS: returns the card given input card ID.
    public Card findCardInCardBox(int inputID) throws NoCardFoundException {
        //find in the tableOfCards
        for (Card cardInTableOfCards : tableOfCards) {
            //if inputID matches with card's ID in tableOfCard,
            if (cardInTableOfCards.getCardID() == inputID) {
                //return the found card
                return cardInTableOfCards;
            }
            //else keep looking
        }
        //if iteration is done, then it means no cards are found. Return null

        throw new NoCardFoundException("There is such card in the cardbox");

    }


    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: returns CardBox as a JSON Object
    //CITE: toJson() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cardBoxNumber", cardBoxNum);
        json.put("boxMinutesTimer", boxMinutesTimer);
        json.put("cards", tableOfCardsToJson());
        return json;
    }


    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: returns cards in this cardBox as a JSON array
    //CITE: toJson() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private JSONArray tableOfCardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card cardElement : tableOfCards) {
            jsonArray.put(cardElement.toJson());
        }

        return jsonArray;
    }

    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: given a date, return a new date with given minutes added to it.
    public Date addMinutesToDate(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }


    //Private Methods-------------------------------------------------------------------------
    //REQUIRES: X
    // (It doesn't require tableOfCards to be non-empty. If tableOfCards is empty,
    // then it is simply guaranteed that there are no cards to test)
    //MODIFIES: this
    //EFFECTS: finds testable cards where current time is same as or
    // exceeds due time calculated with startTime + cardBox timer
    // in tableOfCards and put them into testableTableOfCards
    private void findCardsToTest() {
        //find card in the tableOfCards
        for (Card cardInTableOfCards : tableOfCards) {
            //if the card has boxTimer of 0
            //startTime + box timer > current time
            //current time
            Calendar cal = Calendar.getInstance();
            Date currentTime = cal.getTime();

            //start time + boxMinutesTimer
            Date startTime = cardInTableOfCards.getStartTime();
            Date timeToTest = addMinutesToDate(startTime, boxMinutesTimer);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:HH:mm:ss");
//            System.out.println(sdf.format(startTime));
//            System.out.println(sdf.format(timeToTest));
//            System.out.println(sdf.format(currentTime));

            //start time + card box timer = time you can test
            //current time has to be after or equal to time you can test
            if (currentTime.after(timeToTest) || currentTime.compareTo(timeToTest) == 0) {
                //move into testableTableOfCards
                testableTableOfCards.add(cardInTableOfCards);
            }
        }
    }



    //Getters and setters--------------------------------------------------------------------------

    public int getCardBoxNum() {
        return cardBoxNum;
    }

    public int getBoxMinutesTimer() {
        return boxMinutesTimer;
    }

    public void setBoxMinutesTimer(int inputBoxTimer) {
        boxMinutesTimer = inputBoxTimer;
    }

    public List<Card> getTableOfCards() {
        return tableOfCards;
    }

    public List<Card> getTestableTableOfCards() {
        return testableTableOfCards;
    }


}