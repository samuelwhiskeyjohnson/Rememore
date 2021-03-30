package model;

import exception.NoCardFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardBoxTest {


    @Test
    public void testConstructor() {
        CardBox cardBoxObj = new CardBox(3,22);

        //test cardBoxObj is initialized with given input
        assertEquals(3, cardBoxObj.getCardBoxNum());
        assertEquals(22, cardBoxObj.getBoxMinutesTimer());


    }




    @Test
    public void testAddCard() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);


        CardBox cardBoxObj = new CardBox(3,22);
        cardBoxObj.addCard("Question", "Answer");
        List<Card> tableOfCards = cardBoxObj.getTableOfCards();

        //test size is 1
        assertEquals(1, tableOfCards.size());



        //test it contains the card with the same content.
        //Card expectedCard = new Card("Question","Answer",22,0); Example
        Card actualCard = tableOfCards.get(0);
        assertEquals("Question", actualCard.getFrontInfo());
        assertEquals("Answer", actualCard.getBackInfo());

        Calendar cal = Calendar.getInstance();
        cal.set(2020,Calendar.FEBRUARY,7, 12, 0, 0);
        //month 1 is february since 0 index
        Date currentDate = cal.getTime();

        actualCard.setStartTime(currentDate);

        assertEquals(currentDate, actualCard.getStartTime());
        assertEquals(0, actualCard.getCardID());

    }


    @Test
    public void testLoadCard() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);
        Calendar cal = Calendar.getInstance();
        cal.set(2020,Calendar.FEBRUARY,7, 12, 0, 0);
        //month 1 is february since 0 index
        Date currentDate = cal.getTime();

        CardBox cardBoxObj = new CardBox(3,22);
        Card loadCard = new Card("Question","Answer", currentDate,2);
        cardBoxObj.loadCard(loadCard);
        List<Card> tableOfCards = cardBoxObj.getTableOfCards();

        //test size is 1
        assertEquals(1, tableOfCards.size());
        Card actualCard = tableOfCards.get(0);
        assertEquals("Question", actualCard.getFrontInfo());
        assertEquals("Answer", actualCard.getBackInfo());
        assertEquals(currentDate, actualCard.getStartTime());
        assertEquals(2, actualCard.getCardID());

    }


    @Test
    public void testRemoveCardValidID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxObj = new CardBox(3,22);
        List<Card> tableOfCards = cardBoxObj.getTableOfCards();

        //add three cards to the card box
        cardBoxObj.addCard("Question1", "Answer1");
        cardBoxObj.addCard("Question2", "Answer2");
        cardBoxObj.addCard("Question3", "Answer3");

        //check that table of cards is 3
        assertEquals(3, tableOfCards.size());

        //remove card with index of 0
        cardBoxObj.removeCard( 0 );

        //check that table of card is 2.
        assertEquals(2, tableOfCards.size());
        assertEquals( "Question2", tableOfCards.get(0).getFrontInfo());
        assertEquals( "Question3", tableOfCards.get(1).getFrontInfo());


    }

    @Test
    public void testRemoveCardInvalidID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxObj = new CardBox(3,22);
        List<Card> tableOfCards = cardBoxObj.getTableOfCards();

        //add three cards to the card box
        cardBoxObj.addCard("Question1", "Answer1");
        cardBoxObj.addCard("Question2", "Answer2");
        cardBoxObj.addCard("Question3", "Answer3");

        //check that table of cards is 3
        assertEquals(3, tableOfCards.size());

        //remove card with index of 500, nothing happens
        cardBoxObj.removeCard( 500 );

        //check that table of card is 2.
        assertEquals(3, tableOfCards.size());
        assertEquals( "Question1", tableOfCards.get(0).getFrontInfo());
        assertEquals( "Question2", tableOfCards.get(1).getFrontInfo());
        assertEquals( "Question3", tableOfCards.get(2).getFrontInfo());
    }


    @Test
    public void testModifyCardValidID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxObj = new CardBox(3,22);


        List< Card > tableOfCards = cardBoxObj.getTableOfCards();
        //check that the tableOfCards is empty
        assertEquals( 0, tableOfCards.size() );

        //add the card into the tableOfCards. The first card's ID is 0
        cardBoxObj.addCard( "Question", "Answer" );
        assertEquals( "Question", cardBoxObj.getTableOfCards().get(0).getFrontInfo() );
        assertEquals( "Answer", cardBoxObj.getTableOfCards().get(0).getBackInfo() );



        //check that the card has Question Modified" and "Answer Modified"
        cardBoxObj.modifyCard( 0, "Question Modified", "Answer Modified");
        assertEquals( "Question Modified", cardBoxObj.getTableOfCards().get(0).getFrontInfo());
        assertEquals( "Answer Modified", cardBoxObj.getTableOfCards().get(0).getBackInfo());



    }

    @Test
    public void testModifyCardInvalidID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxObj = new CardBox(3,22);


        List< Card > tableOfCards = cardBoxObj.getTableOfCards();
        //check that the tableOfCards is empty
        assertEquals( 0, tableOfCards.size() );

        //add the card into the tableOfCards. The first card's ID is 0
        cardBoxObj.addCard( "Question", "Answer" );
        assertEquals( "Question", cardBoxObj.getTableOfCards().get(0).getFrontInfo() );
        assertEquals( "Answer", cardBoxObj.getTableOfCards().get(0).getBackInfo() );



        //check that the card has Question Modified" and "Answer Modified", but with Invalid ID, nothing changed
        cardBoxObj.modifyCard( 500, "Question Modified", "Answer Modified");
        assertEquals( "Question", cardBoxObj.getTableOfCards().get(0).getFrontInfo());
        assertEquals( "Answer", cardBoxObj.getTableOfCards().get(0).getBackInfo());

    }


    @Test
    public void testMoveCardToDifferentBoxValidID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxOne = new CardBox( 1, 40 );
        CardBox cardBoxTwo = new CardBox( 2, 40 );

        //add card to cardBoxOne
        cardBoxOne.addCard( "front", "back" );
        cardBoxOne.addCard( "front", "back" );

        //check cardBoxOne has 2 cards and cardBoxTwo has 0 cards
        assertEquals( 2, cardBoxOne.getTableOfCards().size());
        assertEquals( 0, cardBoxTwo.getTableOfCards().size());

        //move the card from cardBoxTwo tableOfCards to cardBoxObj2 tableOfCards
        cardBoxOne.moveCardToDifferentBox( 0, cardBoxTwo );

        //check cardBoxOne has 1 card and cardBoxTwo has 1 card
        assertEquals( 1, cardBoxOne.getTableOfCards().size());
        assertEquals( 1, cardBoxTwo.getTableOfCards().size());
    }

    @Test
    public void testMoveCardToDifferentBoxInvalidID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxOne = new CardBox( 1, 40 );
        CardBox cardBoxTwo = new CardBox( 2, 40 );

        //add card to cardBoxOne
        cardBoxOne.addCard( "front", "back" );
        cardBoxOne.addCard( "front", "back" );

        //check cardBoxOne has 2 cards and cardBoxTwo has 0 cards
        assertEquals( 2, cardBoxOne.getTableOfCards().size());
        assertEquals( 0, cardBoxTwo.getTableOfCards().size());

        //move the card from cardBoxTwo tableOfCards to cardBoxObj2 tableOfCards, but with invalidID, nothing happens
        cardBoxOne.moveCardToDifferentBox( 500, cardBoxTwo );

        //check cardBoxOne has 1 card and cardBoxTwo has 1 card
        assertEquals( 2, cardBoxOne.getTableOfCards().size());
        assertEquals( 0, cardBoxTwo.getTableOfCards().size());

    }


    @Test
    public void testReadyToTestCardsTimerZero() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        //card box one has timer of 0, so its cards also have timer of 0
        CardBox cardBoxOne = new CardBox( 1, 0 );

        cardBoxOne.addCard( "What does the duck say?", "quack" );
        cardBoxOne.addCard( "What does the cow say?", "moo" );
        cardBoxOne.addCard( "What does the dog say?", "bark" );
        cardBoxOne.addCard( "What does the cat say?", "meow" );

        //check testableTableOfCards is empty and tableOfCards has 4 cards
        assertEquals(0, cardBoxOne.getTestableTableOfCards().size());
        assertEquals(4, cardBoxOne.getTableOfCards().size());

        //find cards to test
        cardBoxOne.readyToTestCards();

        //check testableTableOfCards has 4 cards and tableOfCards has 4 cards
        assertEquals( 4, cardBoxOne.getTestableTableOfCards().size());
        assertEquals( 4, cardBoxOne.getTableOfCards().size());

    }


    @Test
    public void testReadyToTestCardsTimerNonZero() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        //card box one has timer of 20, so its cards also have timer of 20
        CardBox cardBoxTwo = new CardBox( 2, 20 );

        cardBoxTwo.addCard( "What does the duck say?", "quack" );
        cardBoxTwo.addCard( "What does the cow say?", "moo" );
        cardBoxTwo.addCard( "What does the dog say?", "bark" );
        cardBoxTwo.addCard( "What does the cat say?", "meow" );

        //check testableTableOfCards is empty and tableOfCards has 4 cards
        assertEquals(0, cardBoxTwo.getTestableTableOfCards().size());
        assertEquals(4, cardBoxTwo.getTableOfCards().size());

        //find cards to test
        cardBoxTwo.readyToTestCards();

        //check testableTableOfCards has 4 cards and tableOfCards has 0 cards since card timer is not 0
        assertEquals( 0, cardBoxTwo.getTestableTableOfCards().size());
        assertEquals( 4, cardBoxTwo.getTableOfCards().size());
    }



    @Test
    public void testModifyBoxDueTimer() {
        CardBox cardBoxOne = new CardBox( 1, 20);
        //check that box timer is 20
        assertEquals(20, cardBoxOne.getBoxMinutesTimer());

        //change the box timer to 1300
        cardBoxOne.modifyBoxDueTimer( 1300 );

        //check box timer is 1300
        assertEquals(1300, cardBoxOne.getBoxMinutesTimer());
    }



    @Test
    public void testFindCardInCardBoxWithValidID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxOne = new CardBox( 1, 20);
        cardBoxOne.addCard( "What does the duck say?", "quack" );
        cardBoxOne.addCard( "What does the cow say?", "moo" );
        try {
            Card foundCardDuck = cardBoxOne.findCardInCardBox( 0 );
            assertEquals(cardBoxOne.getTableOfCards().get(0), foundCardDuck);
            assertEquals(cardBoxOne.getTableOfCards().get(0).getFrontInfo(), foundCardDuck.getFrontInfo());

            Card foundCardCow = cardBoxOne.findCardInCardBox( 1 );
            assertEquals(cardBoxOne.getTableOfCards().get(1), foundCardCow);
            assertEquals(cardBoxOne.getTableOfCards().get(1).getFrontInfo(), foundCardCow.getFrontInfo());

            //pass
        } catch(NoCardFoundException e) {
            System.out.println(e.getMessage());
            fail("No Exception should be thrown");
        }




    }

    @Test
    public void testFindCardInCardBoxWithInvalidID() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxOne = new CardBox( 1, 20);
        cardBoxOne.addCard( "What does the duck say?", "quack" );
        cardBoxOne.addCard( "What does the cow say?", "moo" );
        try {
            Card foundCardDuck = cardBoxOne.findCardInCardBox( 55 );
            assertEquals(null, foundCardDuck);
            fail("NoCardFoundException should be thrown");
        } catch (NoCardFoundException e) {
            System.out.println(e.getMessage());
            //pass
        }




    }


    @Test
    public void testToJson() {
        //card's nextCardID is a static variable that remembers accumulation from previous card instantiations.
        //Therefore reset the cardID back to 0
        Card.setNextCardID(0);

        CardBox cardBoxOne = new CardBox( 1, 20);
        cardBoxOne.addCard( "What does the duck say?", "quack" );

        JSONObject cardBoxJson = cardBoxOne.toJson();
        int cardBoxNum = cardBoxJson.getInt("cardBoxNumber");
        assertEquals(1, cardBoxNum);



        JSONArray tableOfCards = cardBoxJson.getJSONArray("cards");
        JSONObject cardJson = tableOfCards.getJSONObject(0);

        assertEquals(1, tableOfCards.length());
        assertEquals("What does the duck say?", cardJson.getString("frontInfo"));
        assertEquals("quack", cardJson.getString("backInfo"));
        assertEquals(0, cardJson.getInt("cardID"));



    }





    //Test Getters and Setters------------------------------------------------------------------------------

    @Test
    public void getCardBoxNum() {
        CardBox cardBoxOne = new CardBox( 1, 10);
        assertEquals( 1, cardBoxOne.getCardBoxNum());

        CardBox cardBoxTwo = new CardBox( 2, 30);
        assertEquals( 2, cardBoxTwo.getCardBoxNum());

        CardBox cardBoxThree = new CardBox( 3, 100);
        assertEquals( 3, cardBoxThree.getCardBoxNum());

        CardBox cardBoxFour = new CardBox( 4, 300);
        assertEquals( 4, cardBoxFour.getCardBoxNum());
    }

    @Test
    public void getBoxMinutesTimer() {
        CardBox cardBoxOne = new CardBox( 1, 10);
        assertEquals( 10, cardBoxOne.getBoxMinutesTimer());

        CardBox cardBoxTwo = new CardBox( 2, 1550);
        assertEquals( 1550, cardBoxTwo.getBoxMinutesTimer());
    }

    @Test
    public void setBoxMinutesTimer() {
        CardBox cardBoxOne = new CardBox( 1, 10);
        assertEquals( 10, cardBoxOne.getBoxMinutesTimer());


    }


    @Test
    public void getTableOfCards() {
        CardBox cardBoxOne = new CardBox( 1, 10);
        //check that it is empty
        assertEquals( 0, cardBoxOne.getTableOfCards().size());

        cardBoxOne.addCard( "What does the duck say?", "quack" );
        cardBoxOne.addCard( "What does the cow say?", "moo" );

        assertEquals( 2, cardBoxOne.getTableOfCards().size());



    }

    @Test
    public void getTestableTableOfCards() {
        //cards in the box has timer of 0 therefore there are testable cards
        CardBox cardBoxOne = new CardBox( 1, 0);
        assertEquals( 0, cardBoxOne.getTestableTableOfCards().size());
        cardBoxOne.addCard( "What does the duck say?", "quack" );
        cardBoxOne.addCard( "What does the cow say?", "moo" );
        //search for cards that has timer 0 and put the cards in the testableTableOfCards
        cardBoxOne.readyToTestCards();
        assertEquals( 2, cardBoxOne.getTestableTableOfCards().size());

        //cards in the box has timer of 10 therefore there are not testable cards
        CardBox cardBoxTwo = new CardBox( 2, 10);
        cardBoxTwo.addCard( "What does the duck say?", "quack" );
        cardBoxTwo.addCard( "What does the cow say?", "moo" );
        //search for cards that has timer 0 and put the cards in the testableTableOfCards
        cardBoxTwo.readyToTestCards();
        assertEquals( 0, cardBoxTwo.getTestableTableOfCards().size());


    }










}
