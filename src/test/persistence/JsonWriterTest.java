package persistence;

import model.Card;
import model.CardBoxManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest {


    @Test
    public void testConstructor() {
        //JsonReader Constructor
        JsonWriter writer = new JsonWriter("./data/persistence/testReaderBlankSavedCards.json" );
        assertEquals("./data/persistence/testReaderBlankSavedCards.json", writer.getDestination());


    }


    @Test
    public void testOpenInvalidSavedCardsFile() {
        JsonWriter writer = new JsonWriter("./data/persistence/this\0invalid:file.json");

        try {
            writer.open();
            fail("FileNotFoundException Expected");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            //pass
        }


    }

    @Test
    public void testOpenValidSavedCardsFile() {
        JsonWriter writer = new JsonWriter("./data/persistence/testWriterEmptySavedCards.json" );

        try {
            writer.open();
            PrintWriter printWriter =
                    new PrintWriter(new File("./data/persistence/testWriterEmptySavedCards.json"));
            //pass
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            fail("File not found");
        }
    }

    //testing open() valid file, write(), close()

    @Test
    public void testWriteEmptySavedCards() {
        CardBoxManager cardBoxManager = new CardBoxManager();
        JsonWriter writer = new JsonWriter("./data/persistence/testWriterEmptySavedCards.json" );

        try {
            writer.open();
            writer.write(cardBoxManager);
            writer.close();

            JsonReader reader =
                    new JsonReader("./data/persistence/testWriterEmptySavedCards.json", cardBoxManager);
            reader.read();
            //read that there are 5 card boxes
            assertEquals(5, cardBoxManager.getListOfCardBoxes().size());

            assertEquals( 1, cardBoxManager.findCardBoxInCardBoxManager(1).getCardBoxNum());
            assertEquals( 2, cardBoxManager.findCardBoxInCardBoxManager(2).getCardBoxNum());
            assertEquals( 3, cardBoxManager.findCardBoxInCardBoxManager(3).getCardBoxNum());
            assertEquals( 4, cardBoxManager.findCardBoxInCardBoxManager(4).getCardBoxNum());
            assertEquals( 5, cardBoxManager.findCardBoxInCardBoxManager(5).getCardBoxNum());

            //but there are no cards in the boxes
            assertEquals( 0,
                    cardBoxManager.findCardBoxInCardBoxManager(1).getTableOfCards().size());
            assertEquals( 0,
                    cardBoxManager.findCardBoxInCardBoxManager(2).getTableOfCards().size());
            assertEquals( 0,
                    cardBoxManager.findCardBoxInCardBoxManager(3).getTableOfCards().size());
            assertEquals( 0,
                    cardBoxManager.findCardBoxInCardBoxManager(4).getTableOfCards().size());
            assertEquals( 0,
                    cardBoxManager.findCardBoxInCardBoxManager(5).getTableOfCards().size());


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            fail("File not found");
        } catch (ParseException e) {
            System.out.println("Incorrect Date Formatting");
            fail("Incorrect Date Formatting");

        } catch (IOException e) {
            System.out.println("Unable to read from file" );
            fail("Unable to read from file");

        }


    }


    @Test
    public void testWriteNonEmptySavedCards() {
        CardBoxManager cardBoxManager = new CardBoxManager();
        JsonWriter writer = new JsonWriter("./data/persistence/testWriterNonEmptySavedCards.json" );

        try {

            //1 card in first box, 2 cards in second box, 1 card in third box, 1 card in fourth box, no card in fifth box
            cardBoxManager.findCardBoxInCardBoxManager(1).addCard("Front", "Back");
            cardBoxManager.findCardBoxInCardBoxManager(2).addCard("Question", "Ans");
            cardBoxManager.findCardBoxInCardBoxManager(2).addCard("Color of sky", "Blue");
            cardBoxManager.findCardBoxInCardBoxManager(3).addCard("1+1?", "2");
            cardBoxManager.findCardBoxInCardBoxManager(4).addCard("Hello", "World");

            writer.open();
            writer.write(cardBoxManager);
            writer.close();


            //simulating program closed and every card in the cardbox is lost
            cardBoxManager.findCardBoxInCardBoxManager(1).getTableOfCards().clear();
            cardBoxManager.findCardBoxInCardBoxManager(2).getTableOfCards().clear();
            cardBoxManager.findCardBoxInCardBoxManager(3).getTableOfCards().clear();
            cardBoxManager.findCardBoxInCardBoxManager(4).getTableOfCards().clear();
            cardBoxManager.findCardBoxInCardBoxManager(5).getTableOfCards().clear();


            JsonReader reader = new JsonReader("./data/persistence/testWriterNonEmptySavedCards.json", cardBoxManager);

            //CardBoxManager cardBoxManagerLoad = reader.read();
            reader.read();

            //1 card in first box, 2 cards in second box, 1 card in third box, 1 card in fourth box, no card in fifth box
            assertEquals( 1,
                    cardBoxManager.findCardBoxInCardBoxManager(1).getTableOfCards().size());
            assertEquals( 2,
                    cardBoxManager.findCardBoxInCardBoxManager(2).getTableOfCards().size());
            assertEquals( 1,
                    cardBoxManager.findCardBoxInCardBoxManager(3).getTableOfCards().size());
            assertEquals( 1,
                    cardBoxManager.findCardBoxInCardBoxManager(4).getTableOfCards().size());
            assertEquals( 0,
                    cardBoxManager.findCardBoxInCardBoxManager(5).getTableOfCards().size());


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            fail("File not found");
        } catch (ParseException e) {
            System.out.println("Incorrect Date Formatting");
            fail("Incorrect Date Formatting");

        } catch (IOException e) {
            System.out.println("Unable to read from file" );
            fail("Unable to read from file");

        }
    }




}
