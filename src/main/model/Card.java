package model;

/*
Leitner system is a studying technique that uses 1) flash cards and 2) boxes.
For more information about the Leitner system, please read README.md.
Card class represents a flash card that users use to study and test themselves.
Flash card has front side and back side.
Front side is where users type in their question.
Back side is where users type in their answer.

 */

import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Card implements Writable {


    //Fields------------------------------------------------------------------------------------------
    private String frontInfo; //front side of the card where user types in a question
    private String backInfo; //back side of the card where user types in the answer
    /*
    startTime is the time when box timer starts such as Mon Mar 01 15:07:40 PST 2021
    startTime is used to figure out the dueTime when card can be tested by adding startTime by cardBox's timer

    startTime is set to the current time when
    -new card is added
    -moved to another box

    startTime is set to whatever time in savedCards.json when loaded

    the due time is compared with the current time
    if the current time is after the due time, card can be tested
    else (the current time ie before the due time), card can't be tested yet.
     */
    private Date startTime;
    private final int cardID; //card ID is unique
    private static int nextCardID; //tracks ID of next card created


    //Constructors------------------------------------------------------------------------------------

/*  REQUIRES: inputStartDate is current time because this constructor is used for creating a new card
    EFFECTS:
    constructs card object given inputFrontInfo (question) and inputBackInfo (answer) of the card.
    sets the card's startTime given inputStartDate
    Card ID is incremented for every new card
    First card ID is 0, second card is 1, third card is 2.
    cardID is unique identifier using 0 and positive integer
    */
    public Card(String inputFrontInfo, String inputBackInfo, Date inputStartDate) {
        frontInfo = inputFrontInfo;
        backInfo = inputBackInfo;
        startTime = inputStartDate;
        cardID = nextCardID;
        nextCardID++;
    }

    /*  REQUIRES: X
        EFFECTS:
        constructs card object given inputFrontInfo (question) and inputBackInfo (answer) of the card.
        sets the card's startTime given inputStartTime
        Card ID is set with given inputID
        cardID is unique identifier using 0 and positive integer
        */
    public Card(String inputFrontInfo, String inputBackInfo, Date inputStartTime, int inputID) {
        frontInfo = inputFrontInfo;
        backInfo = inputBackInfo;
        startTime = inputStartTime;
        cardID = inputID;

    }



    //Public methods------------------------------------------------------------------------------------

    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: returns Card object as string with attributes of frontInfo, backInfo, startTime, cardID
    @Override
    public String toString() {
        return "Card{"
                + "frontInfo='" + frontInfo
                + '\''
                + ", backInfo='" + backInfo
                + '\''
                + ", startTime=" + startTime
                + ", ID=" + cardID
                + '}';
    }


    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: returns Card as a JSON Object
    //CITE: toJson() is inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("frontInfo", frontInfo);
        json.put("backInfo", backInfo);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(startTime);

        json.put("startTime", formattedDate);
        json.put("cardID", cardID);
        return json;
    }

    //Private methods----------------------------------------------------------------------------------


    //Getters and Setters------------------------------------------------------------------------
    public int getCardID() {
        return cardID;
    }



    public String getFrontInfo() {
        return frontInfo;
    }

    public void setFrontInfo(String frontInfo) {
        this.frontInfo = frontInfo;
    }



    public String getBackInfo() {
        return backInfo;
    }

    public void setBackInfo(String backInfo) {
        this.backInfo = backInfo;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public static int getNextCardID() {
        return nextCardID;
    }

    public static void setNextCardID(int inputIDCounter) {
        nextCardID = inputIDCounter;
    }

}
