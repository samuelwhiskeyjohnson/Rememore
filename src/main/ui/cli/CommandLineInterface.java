package ui.cli;

import exception.NoCardFoundException;
import model.Card;
import model.CardBox;
import model.CardBoxManager;
import ui.Persistence;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/*
CommandLineInterface class provides the console based user interface.
The class separates from code that represents the model and code that represents the user interface

 */
public class CommandLineInterface {

    //fields--------------------------------------------------------------
    private final Persistence persistenceObj;
    private final Scanner scannerObj;
    private final CardBoxManager soleCardBoxManager;



    //Constructor---------------------------------------------------------
    //REQUIRES: X
    //EFFECTS: constructs CommandLineInterface
    //and initializes soleCardBoxManager, persistence, and scannerObj
    public CommandLineInterface(Persistence inputPersistObj, CardBoxManager inputSoleCardBoxManager) {
        //wrap card box manager
        persistenceObj = inputPersistObj;
        scannerObj = new Scanner(System.in);
        soleCardBoxManager = inputSoleCardBoxManager;
    }

    //Main menu---------------------------------------------------------
 /* REQUIRES: X
    MODIFIES: X
    EFFECTS: prints main menu on console and handle user inputs of the given selection
     -1: Quit the program
     1: Box menu for first box
     2: Box menu for second box
     3: Box menu for third box
     4: Box menu for fourth box
     5: Box menu for fifth box
     6: Save all cards
     Other inputs: other inputs requires the user to input again
*/
    public void mainMenuUI() {
        persistenceObj.load();

        int chooseBoxNum = 0;
        while (true) {
            try {
                printMainMenu();

                chooseBoxNum = scannerObj.nextInt();

                if (1 <= chooseBoxNum && chooseBoxNum <= 5) {
                    boxMenuUI(chooseBoxNum);
                } else if (chooseBoxNum == 6) {
                    persistenceObj.save();
                } else if (chooseBoxNum == -1) {
                    break;
                } else {
                    System.out.print("\nYou have selected an invalid option.\n\n");
                }
//                switch (chooseBoxNum) {
//                    case 1:
//                    case 2:
//                    case 3:
//                    case 4:
//                    case 5:
//                        boxMenuUI(chooseBoxNum);
//                        break;
//                    case 6:
//                        persistenceObj.saveCards();
//                        break;
//                    default:
//                        System.out.print("\nYou have selected an invalid option.\n\n");
//                        break;
//                }

            } catch (InputMismatchException e) {
                System.err.println("Type in a number");
                scannerObj.nextLine();
            }
        }


    }


    /*
    REQUIRES: X
    MODIFIES: X
    EFFECTS: prints the options for the main menu
    -1: Quit the program
    1: Box 1 Menu
    2: Box 2 Menu
    3: Box 3 Menu
    4: Box 4 Menu
    5: Box 5 Menu
    6: Save all cards
   */
    private void printMainMenu() {
        System.out.println("\nREMEMORE: Leitner System Software");
        System.out.println("================================");
        System.out.println("Select a box (or Enter -1 to quit)");
        for (int boxNum = 1; boxNum <= 5; boxNum++) {
            System.out.println(boxNum + ") Box " + boxNum);
        }
        System.out.println("6) Save all cards");

    }



    //Box menu--------------------------------------------------------------
    /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: X
    EFFECTS:  prints box menu on console and handle user inputs of the given selection
    -1: go back to main menu
    1: Display cards in the box selected
    2: Add card in the box selected
    3: Remove card in the box selected
    4: Modify card in the box selected
    5: Move card in this box to a different box
    6: test cards in the box selected
    7: modify this box's timer
    */
    private void boxMenuUI(int boxNum) {

        printBoxMenu(boxNum);
        int choice = scannerObj.nextInt();
        boxMenuChooseLogic(choice, boxNum);

    }


    /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: X
    EFFECTS: prints the options for box menu
    -1: Go back to the main menu
    1: Display cards in the box selected
    2: Add card in the box selected
    3: Remove card in the box selected
    4: Modify card in the box selected
    5: Move card in this box to a different box
    6: test cards in the box selected
    7: modify this box's timer
    */

    private void printBoxMenu(int boxNum) {
        System.out.println("Selected Box " + boxNum);
        System.out.println("================================");
        System.out.println("Select one of the options (or Enter -1 to go back)");
        System.out.println("1) Display Cards");
        System.out.println("2) Add Card");
        System.out.println("3) Remove Card");
        System.out.println("4) Modify Card");
        System.out.println("5) Move Card To Different Box");
        System.out.println("6) Test Cards");
        System.out.println("7) Modify Box Timer");
    }


    /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: X
    EFFECTS: handle the user inputs of the given selection
    -1: Go back to the main menu
    1: Display cards in the box selected
    2: Add card in the box selected
    3: Remove card in the box selected
    4: Modify card in the box selected
    5: Move card in this box to a different box
    6: test cards in the box selected
    7: modify this box's timer
    Other inputs: other inputs requires the user to input again
    */
    private void boxMenuChooseLogic(int choice, int boxNum) {
        while (true) {
            if (choice == -1) {
                return;
            } else if (choice == 1) {
                printDisplayCards(boxNum);
            } else if (choice == 2) {
                addCardUI(boxNum);
            } else if (choice == 3) {
                removeCardUI(boxNum);
            } else if (choice == 4) {
                modifyCardUI(boxNum);
            } else if (choice == 5) {
                moveCardUI(boxNum);
            } else if (choice == 6) {
                testCardsUI(boxNum);
            } else if (choice == 7) {
                modifyBoxTimerUI(boxNum);
            } else {
                System.out.print("\nYou have selected an invalid option.\n\n");
            }

            printBoxMenu(boxNum);
            choice = scannerObj.nextInt();
        }
    }


    //Option 1 display cards------------------------------------------------------

    /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: X
    EFFECTS: given selected box with inputBoxNum, prints out all the cards in the box
    */
    private void printDisplayCards(int inputBoxNum) {

        CardBox cardBoxForDisplayingCards = soleCardBoxManager.findCardBoxInCardBoxManager(inputBoxNum);

        List<Card> tableOfCards = cardBoxForDisplayingCards.getTableOfCards();

        if (tableOfCards.size() != 0) {
            //print all the cards in the tableOfCards
            for (Card cardInTableOfCards : tableOfCards) {
                System.out.println(cardInTableOfCards.toString());
            }
        } else {
            System.out.println("No cards to display");
        }


    }


    //Option 2 add cards-----------------------------------------
    /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: this
    EFFECTS: given selected box with inputBoxNum,
    add a card in the box by using user's input of front and back card info
    */
    private void addCardUI(int inputBoxNum) {

        CardBox cardBoxForAddingCads = soleCardBoxManager.findCardBoxInCardBoxManager(inputBoxNum);

        scannerObj.nextLine();
        System.out.println("Please enter front side of the card");
        String front = scannerObj.nextLine();
        System.out.println("Please enter back side of the card");
        String back = scannerObj.nextLine();

        cardBoxForAddingCads.addCard(front, back);
    }

    //Option 3 remove cards-----------------------------------------
    /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: this
    EFFECTS: given selected box with inputBoxNum, remove a card by using user's input of cardID
    */
    private void removeCardUI(int inputBoxNum) {

        CardBox cardBoxForRemovingCards = soleCardBoxManager.findCardBoxInCardBoxManager(inputBoxNum);

        scannerObj.nextLine();
        System.out.println("Enter card ID to remove");
        int cardIDForRemoving = scannerObj.nextInt();

        cardBoxForRemovingCards.removeCard(cardIDForRemoving);

    }

    //Option 4 remove cards-----------------------------------------
    /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: this
    EFFECTS: given selected box with inputBoxNum, modify a card in the box by
    first finding the card the user wants with the cardID and
    overwrite previous front and back card info with the user's input of front and back card info
    */
    private void modifyCardUI(int inputBoxNum) {

        CardBox cardBoxForModifyingCards = soleCardBoxManager.findCardBoxInCardBoxManager(inputBoxNum);


        System.out.println("Enter card ID to modify");
        int cardIDForModifying = scannerObj.nextInt();

        try {
            Card card = cardBoxForModifyingCards.findCardInCardBox(cardIDForModifying); //card is null if not found
            if (card != null) {
                scannerObj.nextLine();
                System.out.println("Overwrite front side of the card");
                String front = scannerObj.nextLine();

                System.out.println("Overwrite back side of the card");
                String back = scannerObj.nextLine();

                cardBoxForModifyingCards.modifyCard(cardIDForModifying, front, back);
            }
        } catch (NoCardFoundException e) {
            System.out.println(e.getMessage());
        }






    }

    //Option 5 move card-----------------------------------------
    /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: this
    EFFECTS: given selected box with inputBoxNum, move a card in the box by
    first finding the card the user wants with the cardID and
    moving the card to a different box the user wants to with the box number.
    */
    private void moveCardUI(int inputBoxNum) {
        CardBox cardBoxForMovingCards = soleCardBoxManager.findCardBoxInCardBoxManager(inputBoxNum);

        System.out.println("Enter card ID to move");
        int cardIDForMovingToAnotherBox = scannerObj.nextInt();
        try {
            Card card = cardBoxForMovingCards.findCardInCardBox(cardIDForMovingToAnotherBox);
            //card is null if not found

            if (card != null) {
                System.out.println("Which box to move this card to?");
                for (int boxNum = 1; boxNum <= 5; boxNum++) {
                    System.out.println(boxNum + ") Box " + boxNum);
                }

                int differentBoxNumber = scannerObj.nextInt();

                CardBox differentCardBox = soleCardBoxManager.findCardBoxInCardBoxManager(differentBoxNumber);
                cardBoxForMovingCards.moveCardToDifferentBox(cardIDForMovingToAnotherBox, differentCardBox);

            } else {
                System.out.println("Card not found");
            }

        } catch (NoCardFoundException e) {
            System.out.println(e.getMessage());
        }




    }

    //Option 6 test cards-----------------------------------------
   /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: this
    EFFECTS: given selected box with inputBoxNum,
    test the cards by first getting ready to test the cards
    print the front info
    press any key to see the back info of the card
    print back info of the card
    print whether the user got the answer correct
    handle user inputs of the given selection
    1: got the answer correct
    2: got the answer incorrect
    */
    private void testCardsUI(int inputBoxNum) {

        CardBox cardBoxForTestingCards = soleCardBoxManager.findCardBoxInCardBoxManager(inputBoxNum);

        //ready the test by identifying cards with expired timer (timer == 0) and shuffling the order.
        readyToTestCards(cardBoxForTestingCards);

        List<Card> testableTableOfCards = cardBoxForTestingCards.getTestableTableOfCards();

        for (Card currentCardToTest : testableTableOfCards) {

            printTestCards(currentCardToTest);
            printAnswerCorrectlyOrIncorrectly();

            int userResponse = scannerObj.nextInt();

            while (true) {
                if (userResponse == 1) {
                    //if answered correctly
                    soleCardBoxManager.gotAnswerCorrectly(currentCardToTest, cardBoxForTestingCards);
                    break;
                } else if (userResponse == 2) {
                    //else if answered incorrectly
                    soleCardBoxManager.gotAnswerIncorrectly(currentCardToTest,cardBoxForTestingCards);
                    break;
                } else {
                    System.out.print("\nYou have selected an invalid option.\n\n");
                }
                printAnswerCorrectlyOrIncorrectly();
                userResponse = scannerObj.nextInt();
            }
        }
        //after all cards are tested remove all cards from testableTableOfCards
        testableTableOfCards.clear();
    }

    /*
     REQUIRES: cardBoxForTestingCards is one of the cardbox 1,2,3,4,5
     MODIFIES: cardBoxForTestingCards
     EFFECTS: given cardBoxForTestingCards, ready to test the card
     if there are no cards to test, print that there are no cards to test.
     */
    private void readyToTestCards(CardBox cardBoxForTestingCards) {
        boolean isTestingReady = cardBoxForTestingCards.readyToTestCards();
        if (isTestingReady == false) {
            System.out.println("there are no cards to test");
        }
    }


    /*
     REQUIRES: currentCardToTest is a Card in testableTableOfCards
     MODIFIES: X
     EFFECTS: given currentCardToTest, print the front side.
     User can press any key to show the back side to see the answer
     */
    private void printTestCards(Card currentCardToTest) {
        //show front side of the card
        System.out.println("Front side: " + currentCardToTest.getFrontInfo());

        //show the backside of the card after pressing any key
        System.out.println("Press any key to see the back side");
        scannerObj.nextLine();
        scannerObj.nextLine();

        //show back side of the card
        System.out.println("Back side: " + currentCardToTest.getBackInfo());
    }


    /*
    REQUIRES: X
    MODIFIES: X
    EFFECTS: after the user seeing the answer on the back of the card,
    print the options whether the user got the answer correct or incorrect.
    1: got the answer correct
    2: got the answer incorrect
    */
    private void printAnswerCorrectlyOrIncorrectly() {
        System.out.println("Did you get it correct?");
        System.out.println("1) Yes");
        System.out.println("2) No");
    }





    // Option 7 modify box timer-----------------------------------------
   /*
    REQUIRES: inputBoxNum is one of 1,2,3,4,5.
    MODIFIES: this
    EFFECTS: print the prompt to user to enter new box timer and current timer
    change the timer to the user's newly entered timer.

    */
    private void modifyBoxTimerUI(int inputBoxNum) {
        //int index = inputBoxNum - 1;
        CardBox cardBoxForModifyingItsTimer = soleCardBoxManager.findCardBoxInCardBoxManager(inputBoxNum);

        int currentBoxMinutesTimer = cardBoxForModifyingItsTimer.getBoxMinutesTimer();
        System.out.println("Enter new box timer" + " Current Timer: " + currentBoxMinutesTimer);
        int newBoxTimer = scannerObj.nextInt();
        cardBoxForModifyingItsTimer.modifyBoxDueTimer(newBoxTimer);

    }



}
