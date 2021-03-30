package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardBoxManagerTest {

    @Test
    public void testConstructor() {
        CardBoxManager cardBoxManagerObj = new CardBoxManager();

        CardBox cardBoxOne = cardBoxManagerObj.getListOfCardBoxes().get(0);
        CardBox cardBoxOneExpected = new CardBox(1, 0);
        assertEquals( cardBoxOneExpected.getCardBoxNum(), cardBoxOne.getCardBoxNum()  );

        CardBox cardBoxTwo = cardBoxManagerObj.getListOfCardBoxes().get(1);
        CardBox cardBoxTwoExpected = new CardBox(2, 20);
        assertEquals( cardBoxTwoExpected.getCardBoxNum(), cardBoxTwo.getCardBoxNum()  );

        CardBox cardBoxThree = cardBoxManagerObj.getListOfCardBoxes().get(2);
        CardBox cardBoxThreeExpected = new CardBox(3, 40);
        assertEquals( cardBoxThreeExpected.getCardBoxNum(), cardBoxThree.getCardBoxNum()  );

        CardBox cardBoxFour = cardBoxManagerObj.getListOfCardBoxes().get(3);
        CardBox cardBoxFourExpected = new CardBox(4, 80);
        assertEquals( cardBoxFourExpected.getCardBoxNum(), cardBoxFour.getCardBoxNum()  );

        CardBox cardBoxFive = cardBoxManagerObj.getListOfCardBoxes().get(4);
        CardBox cardBoxFiveExpected= new CardBox(5, 160);
        assertEquals( cardBoxFiveExpected.getCardBoxNum(), cardBoxFive.getCardBoxNum()  );



    }


    @Test
    public void testFindCardBoxInCardBoxManagerValidCardBoxNumber() {
        CardBoxManager cardBoxManagerObj = new CardBoxManager();
        CardBox expectedCardBoxOneFound = cardBoxManagerObj.getListOfCardBoxes().get(0);
        CardBox actualCardBoxOneFound = cardBoxManagerObj.findCardBoxInCardBoxManager(1);
        assertEquals( expectedCardBoxOneFound, actualCardBoxOneFound);

        CardBox expectedCardBoxTwoFound = cardBoxManagerObj.getListOfCardBoxes().get(1);
        CardBox actualCardBoxTwoFound = cardBoxManagerObj.findCardBoxInCardBoxManager(2);
        assertEquals( expectedCardBoxTwoFound, actualCardBoxTwoFound);

        CardBox expectedCardBoxThreeFound = cardBoxManagerObj.getListOfCardBoxes().get(2);
        CardBox actualCardBoxThreeFound = cardBoxManagerObj.findCardBoxInCardBoxManager(3);
        assertEquals( expectedCardBoxThreeFound, actualCardBoxThreeFound);

        CardBox expectedCardBoxFourFound = cardBoxManagerObj.getListOfCardBoxes().get(3);
        CardBox actualCardBoxFourFound = cardBoxManagerObj.findCardBoxInCardBoxManager(4);
        assertEquals( expectedCardBoxFourFound, actualCardBoxFourFound);

        CardBox expectedCardBoxFiveFound = cardBoxManagerObj.getListOfCardBoxes().get(4);
        CardBox actualCardBoxFiveFound = cardBoxManagerObj.findCardBoxInCardBoxManager(5);
        assertEquals( expectedCardBoxFiveFound, actualCardBoxFiveFound);
    }

    @Test
    public void testFindCardBoxInCardBoxManagerInvalidCardBoxNumber() {
        CardBoxManager cardBoxManagerObj = new CardBoxManager();
        CardBox actualCardBoxOneFound = cardBoxManagerObj.findCardBoxInCardBoxManager(60);
        assertEquals( null, actualCardBoxOneFound);


    }



    @Test
    public void testGotAnswerCorrectlyNotLastBox() {
        CardBoxManager cardBoxManagerObj = new CardBoxManager();
        //When user get a testing card correctly,
        // the card moves to the next box and timer is set to the next box's timer
        //if the card is at the last box, the card stays and timer resets the the last box's

        //add a card to cardBoxOne
        CardBox cardBoxOne = cardBoxManagerObj.findCardBoxInCardBoxManager(1);
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();

        Card cardWithExpiredTimer = new Card("Question", "Answer",currentDate,0);
        cardBoxOne.getTableOfCards().add( cardWithExpiredTimer );
        //ready to test cards by searching for cards with expired timer and putting them into testableTableOfCards
        cardBoxOne.readyToTestCards();
        List<Card> testableTableOfCards = cardBoxOne.getTestableTableOfCards();
        Card testableCard = testableTableOfCards.get(0);

        //test the card and user got it correctly.
        cardBoxManagerObj.gotAnswerCorrectly(testableCard,cardBoxOne);
        CardBox cardBoxTwo = cardBoxManagerObj.findCardBoxInCardBoxManager( 2 );
        //The card should be in the second card box and not in the first card box
        assertFalse(cardBoxOne.getTableOfCards().contains(cardWithExpiredTimer));
        assertTrue(cardBoxTwo.getTableOfCards().contains(cardWithExpiredTimer));

    }


    @Test
    public void testGotAnswerCorrectlyLastBox() {
        CardBoxManager cardBoxManagerObj = new CardBoxManager();
        //When user get a testing card correctly,
        // the card moves to the next box and timer is set to the next box's timer
        //if the card is at the last box, the card stays and timer resets the the last box's

        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();


        //add a card to cardBoxOne
        CardBox cardBoxFive = cardBoxManagerObj.findCardBoxInCardBoxManager(5);
        cardBoxFive.setBoxMinutesTimer(0);
        Card cardWithExpiredTimer = new Card("Question", "Answer",currentDate,0);
        cardBoxFive.getTableOfCards().add( cardWithExpiredTimer );
        //ready to test cards by searching for cards with expired timer and putting them into testableTableOfCards
        cardBoxFive.readyToTestCards();
        List<Card> testableTableOfCards = cardBoxFive.getTestableTableOfCards();
        Card testableCard = testableTableOfCards.get(0);

        //test the card and user got it correctly.
        cardBoxManagerObj.gotAnswerCorrectly(testableCard,cardBoxFive);
        //The card should still be in the last 5th box
        assertTrue(cardBoxFive.getTableOfCards().contains(cardWithExpiredTimer));


    }



    @Test
    public void testGotAnswerIncorrectlyNotFirstBox() {
        CardBoxManager cardBoxManagerObj = new CardBoxManager();
        //When user get a testing card incorrect,
        // the card moves to the previous box and timer is set to the previous box's timer
        //if the card is at the first box, the card stays and timer resets the the first box's
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        //add a card to cardBoxThree
        CardBox cardBoxThree = cardBoxManagerObj.findCardBoxInCardBoxManager(3);
        cardBoxThree.setBoxMinutesTimer(0);
        Card cardWithExpiredTimer = new Card("Question", "Answer",currentDate,0);
        cardBoxThree.getTableOfCards().add( cardWithExpiredTimer );
        //ready to test cards by searching for cards with expired timer and putting them into testableTableOfCards
        cardBoxThree.readyToTestCards();
        List<Card> testableTableOfCards = cardBoxThree.getTestableTableOfCards();
        Card testableCard = testableTableOfCards.get(0);

        //test the card and user got it incorrectly.
        cardBoxManagerObj.gotAnswerIncorrectly(testableCard,cardBoxThree);
        CardBox cardBoxTwo = cardBoxManagerObj.findCardBoxInCardBoxManager( 2 );
        //The card should be in the second card box and not in the third card box
        assertFalse(cardBoxThree.getTableOfCards().contains(cardWithExpiredTimer));
        assertTrue(cardBoxTwo.getTableOfCards().contains(cardWithExpiredTimer));
    }


    @Test
    public void testGotAnswerIncorrectlyFirstBox() {
        CardBoxManager cardBoxManagerObj = new CardBoxManager();
        //When user get a testing card incorrect,
        // the card moves to the previous box and timer is set to the previous box's timer
        //if the card is at the first box, the card stays and timer resets the the first box's
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        //add a card to cardBoxThree
        CardBox cardBoxOne = cardBoxManagerObj.findCardBoxInCardBoxManager(1);
        Card cardWithExpiredTimer = new Card("Question", "Answer",currentDate,0);
        cardBoxOne.getTableOfCards().add( cardWithExpiredTimer );
        //ready to test cards by searching for cards with expired timer and putting them into testableTableOfCards
        cardBoxOne.readyToTestCards();
        List<Card> testableTableOfCards = cardBoxOne.getTestableTableOfCards();
        Card testableCard = testableTableOfCards.get(0);

        //test the card and user got it incorrectly.
        cardBoxManagerObj.gotAnswerIncorrectly(testableCard,cardBoxOne);
        //The card should be in the first box
        assertTrue(cardBoxOne.getTableOfCards().contains(cardWithExpiredTimer));
    }


    @Test
    public void testToJson() {
        CardBoxManager cardBoxManagerObj = new CardBoxManager();
        JSONObject cardBoxManagerJson = cardBoxManagerObj.toJson();
        JSONArray listOfCardBoxes = cardBoxManagerJson.getJSONArray("cardBoxes");

        //first box
        JSONObject firstBoxJson = listOfCardBoxes.getJSONObject(0);
        int firstBoxNumber = firstBoxJson.getInt("cardBoxNumber");
        assertEquals(1, firstBoxNumber);

        //second box
        JSONObject secondBoxJson = listOfCardBoxes.getJSONObject(1);
        int secondBoxJsonNumber = secondBoxJson.getInt("cardBoxNumber");
        assertEquals(2, secondBoxJsonNumber);

        //third box
        JSONObject thirdBoxJson = listOfCardBoxes.getJSONObject(2);
        int thirdBoxJsonNumber = thirdBoxJson.getInt("cardBoxNumber");
        assertEquals(3, thirdBoxJsonNumber);


        //fourth box
        JSONObject fourthBoxJson = listOfCardBoxes.getJSONObject(3);
        int fourthBoxJsonNumber = fourthBoxJson.getInt("cardBoxNumber");
        assertEquals(4, fourthBoxJsonNumber);

        //fifth box
        JSONObject fifthBoxJson = listOfCardBoxes.getJSONObject(4);
        int fifthBoxJsonNumber = fifthBoxJson.getInt("cardBoxNumber");
        assertEquals(5, fifthBoxJsonNumber);


    }











}
