package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    //test the first constructor with parameter
    // String inputFrontInfo, String inputBackInfo, int inputTimeUntilTestedAgain
    @Test
    public void testConstructor1() {
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.FEBRUARY,7, 12, 0, 0);
        //month 1 is february since 0 index
        Date currentDate = cal.getTime();

        Card cardObj = new Card("What is the color of the sky?", "blue", currentDate);
        String frontInfo = cardObj.getFrontInfo();
        assertEquals("What is the color of the sky?", frontInfo);

        String backInfo = cardObj.getBackInfo();
        assertEquals("blue", backInfo);

        Date startTime = cardObj.getStartTime();

        assertEquals(currentDate, startTime);

        int firstCardID = cardObj.getCardID();
        assertEquals(0, firstCardID);

    }

    //test second constructor with parameter
    // String inputFrontInfo, String inputBackInfo, int inputTimeUntilTestedAgain, int inputID
    @Test
    public void testConstructor2() {
        Calendar cal = Calendar.getInstance();
        cal.set(2020,Calendar.FEBRUARY,7, 12, 0, 0);
        //month 1 is february since 0 index
        Date currentDate = cal.getTime();

        Card cardObj = new Card("My favorite color?", "red", currentDate, 5);
        String frontInfo = cardObj.getFrontInfo();
        assertEquals("My favorite color?", frontInfo);

        String backInfo = cardObj.getBackInfo();
        assertEquals("red", backInfo);

        Date startTime = cardObj.getStartTime();
        assertEquals(currentDate, startTime);

        int firstCardID = cardObj.getCardID();
        assertEquals(5, firstCardID);

    }

    @Test
    public void testToString() {
        Calendar cal = Calendar.getInstance();
        cal.set(2020,Calendar.FEBRUARY,7, 12, 0, 0);
        //month 1 is february since 0 index
        Date currentDate = cal.getTime();

        Card cardObj = new Card("What is 1+1?", "2", currentDate, 3);
        String cardAsString = cardObj.toString();
        String expectedString =
                "Card{frontInfo='What is 1+1?', backInfo='2', startTime=Fri Feb 07 12:00:00 KST 2020, ID=3}";
        assertEquals(expectedString, cardAsString);


    }

    @Test
    public void testToJson() {
        Calendar cal = Calendar.getInstance();
        cal.set(2020,Calendar.FEBRUARY,7, 12, 0, 0);
        //month 1 is february since 0 index
        Date currentDate = cal.getTime();
        Card cardObj = new Card("What is 1+1?", "2", currentDate, 3);

        JSONObject cardJsonObj = cardObj.toJson();
        String frontInfo = cardJsonObj.getString("frontInfo");
        assertEquals("What is 1+1?", frontInfo);

        String backInfo = cardJsonObj.getString("backInfo");
        assertEquals("2", backInfo);

        String startTime = cardJsonObj.getString("startTime");
        assertEquals("2020-02-07 12:00:00", startTime);

        int cardID = cardJsonObj.getInt("cardID");
        assertEquals(3, cardID);




    }


    //Test Getters and Setters------------------------------------------------------------------------------
    @Test
    public void testGetCardID() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        Card cardObj = new Card("What is 1+1?", "2", currentDate, 3);
        assertEquals(3, cardObj.getCardID());
    }


    @Test
    public void testGetFrontInfo() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        Card cardObj = new Card("What is 1+1?", "2", currentDate, 3);

        assertEquals("What is 1+1?", cardObj.getFrontInfo());

    }

    @Test
    public void testSetFrontInfo() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        Card cardObj = new Card("What is 1+1?", "2", currentDate, 3);
        cardObj.setFrontInfo("What is life?");
        assertEquals("What is life?", cardObj.getFrontInfo());

    }


    @Test
    public void testGetBackInfo() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        Card cardObj = new Card("What is 1+1?", "Two", currentDate, 3);
        assertEquals("Two", cardObj.getBackInfo());

    }

    @Test
    public void testSetBackInfo() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        Card cardObj = new Card("What is 1+1?", "2", currentDate, 3);
        cardObj.setBackInfo("Number 2");
        assertEquals("Number 2", cardObj.getBackInfo());

    }


    @Test
    public void testGetTimerUntilTestedAgain() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        Card cardObj = new Card("What is 1+1?", "2", currentDate, 3);
        assertEquals(currentDate,cardObj.getStartTime());
    }

    @Test
    public void testSetTimerUntilTestedAgain() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        Card cardObj = new Card("What is 1+1?", "2", null, 3);



        cardObj.setStartTime(currentDate);
        assertEquals(currentDate,cardObj.getStartTime());
    }



    @Test
    public void testGetNextCardID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        assertEquals(0, Card.getNextCardID());


    }

    @Test
    public void testSetNextCardID() {
        Card.setNextCardID( 55 );
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        Card cardObj = new Card("What is 1+1?", "2", currentDate);

        assertEquals( 55, cardObj.getCardID() );
    }




}