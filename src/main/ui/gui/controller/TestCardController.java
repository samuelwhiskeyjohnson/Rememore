package ui.gui.controller;

import model.Card;
import model.CardBox;
import model.CardBoxManager;
import ui.Persistence;
import ui.gui.view.View;
import ui.gui.view.panel.TestCardPanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;



/*
For Rememore as a GUI application, MVC pattern is used.
Controller represents the initialization and change of data by user input to be shown on GUI

Controller is broken down to 3 controllers for each 3 panels: MainMenuPanel, CardBoxPanel, TestCardPanel
MainMenuController initializes and response to user input on the MainMenuPanel GUI
CardBoxController initializes and response to user input on the CardBoxPanel GUI
TestCardController initializes and response to user input on the TestCardPanel GUI

 */

public class TestCardController {

    //connection needed for MVC model
    private Persistence persistenceModel;
    private View view;
    private int numberOfCardsCorrect;
    private int getNumberOfCardsIncorrect;
    private int totalNumberOfCardsTested;


    //REQUIRES: X
    //EFFECTS: constructs TestCardController
    public TestCardController(Persistence inputPersistenceModel, View inputView) {
        persistenceModel = inputPersistenceModel;
        view = inputView;



    }





    //Card Test panel view----------------------------------------------------------------------------------------------


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS:  if there are cards in the listOfTestableCards,
    // initializes test card screen with a question of a random card in listOfTestableCards
    // else
    // show pop up menu saying there is no card to test to return to main menu.
    public void initTestCardView() {
        TestCardPanel testCardPanel = view.getTestCardPanel();

        // init test card panel.
        CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();

        CardBox cardBox = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());
        List<Card> listOfTestableCards = cardBox.getTestableTableOfCards();
        //get the cards ready to test
//        cardBox.testCardsWithExpiredDueTimer();
        cardBox.readyToTestCards();
        totalNumberOfCardsTested = listOfTestableCards.size();

        if (listOfTestableCards.size() == 0) {
            JOptionPane.showConfirmDialog(null, "no cards to test");
            view.getFrame().setContentPane(view.getMainMenuPanel());
            view.getFrame().revalidate();
        } else {
            Card currentTestCard = listOfTestableCards.get(0);
            testCardPanel.getCardArea().setText(currentTestCard.getFrontInfo());
            testCardPanel.switchFrontPanel(); //this should be added, it wasn't added before
//            System.out.println("394line:" + listOfTestableCards.size());
        }


    }




    //Card test panel logic--------------------------------------------------------------------------------------
    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes action for buttons for show answer button, got correct button, got wrong button
    //go back to main menu button
    public void initTestCardController() {
        TestCardPanel testCardPanel = view.getTestCardPanel();

        //add action to show answer button
        addActionToShowAnswerButton(testCardPanel);

        //add action to got correct button
        addActionToGotCorrectButton(testCardPanel);

        //add action to got wrong button
        addActionToGotWrongButton(testCardPanel);

        //add action to go back menu button in the results panel
        addActionToGoBackToMainMenuButton(testCardPanel);


    }



    //REQUIRES: X
    //MODIFIES: testCardPanel, this
    //EFFECTS: sets response when show answer button is pressed, screen shows back side of card
    private void addActionToShowAnswerButton(TestCardPanel testCardPanel) {
        testCardPanel.getShowAnswerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();
                CardBox cardBox = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());
                List<Card> listOfTestableCards = cardBox.getTestableTableOfCards();

                // System.out.println(listOfTestableCards.size());
                // to check size of listOfTestableCards
                if (listOfTestableCards.size() == 0) {
                    return;
                }

                Card currentTestCard = listOfTestableCards.get(0);

                //show back panel.
                testCardPanel.getCardArea().setText(currentTestCard.getBackInfo());
                testCardPanel.getCardOrientationLabel().setText("Back");
                testCardPanel.switchBackPanel();
            }
        });
    }


    //REQUIRES: X
    //MODIFIES: testCardPanel, this
    //EFFECTS: sets response when got correct button is pressed,
    //if there are no more cards to test,
    //end the test
    //else (if there are more cards to test)
    //moves the correct card one card to next box (if the card is in the last box, it stays)
    //show the next question
    //numberOfCardsCorrect is incremented by 1
    private void addActionToGotCorrectButton(TestCardPanel testCardPanel) {
        testCardPanel.getGotCorrectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/correct.wav");

                CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();
                CardBox cardBox = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());
                List<Card> listOfTestableCards = cardBox.getTestableTableOfCards();

//                // to check size of listOfTestableCards
//                if (listOfTestableCards.size() == 0) {
//                    return;
//                }

                Card currentTestCard = listOfTestableCards.get(0);

                persistenceModel.getSoleCardBoxManager().gotAnswerCorrectly(currentTestCard, cardBox);

                //add to number of cards correct
                numberOfCardsCorrect += 1;

                //remove the card already tested
                listOfTestableCards.remove(0);

                //check test more or not.
                decideTestMoreOrEndTesting(listOfTestableCards, testCardPanel);



            }
        });
    }



    //REQUIRES: X
    //MODIFIES: testCardPanel, this
    //EFFECTS: sets response when got correct button is pressed,
    //if there are no more cards to test,
    //end the test
    //else (if there are more cards to test)
    //moves the wrong card one card to previous box (if the card is in the first box, it stays)
    //show the next question
    //getNumberOfCardsIncorrect is incremented by 1
    private void addActionToGotWrongButton(TestCardPanel testCardPanel) {
        testCardPanel.getGotWrongButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/wrong.wav");

                CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();
                CardBox cardBox = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());
                List<Card> listOfTestableCards = cardBox.getTestableTableOfCards();

//                // to check size of listOfTestableCards
//                if (listOfTestableCards.size() == 0) {
//                    return;
//                }

                Card currentTestCard = listOfTestableCards.get(0);

                persistenceModel.getSoleCardBoxManager().gotAnswerIncorrectly(currentTestCard, cardBox);
                getNumberOfCardsIncorrect += 1;

                //remove the card already tested
                listOfTestableCards.remove(0);

                //check test more or not.
                decideTestMoreOrEndTesting(listOfTestableCards, testCardPanel);



            }
        });
    }

    //REQUIRES: X
    //MODIFIES: testCardPanel
    //EFFECTS: if there is no more cards to test,
    //end the test
    //else (if there are more cards to test)
    //show the next question
    private void decideTestMoreOrEndTesting(List<Card> listOfTestableCards, TestCardPanel testCardPanel) {
        if (listOfTestableCards.size() == 0) {
            endTesting(testCardPanel);

        } else {
            // show next question
            Card nextTestCard = listOfTestableCards.get(0);
            testCardPanel.getCardArea().setText(nextTestCard.getFrontInfo());
            testCardPanel.getCardOrientationLabel().setText("Front");
            testCardPanel.switchFrontPanel();
        }
    }



    //REQUIRES: X
    //MODIFIES: testCardPanel, this
    //EFFECTS: end testing by showing the results of how many cards the user got correct and incorrect
    //if the user scores >=90,
    //show awesome job
    //else if the user scores 90> and >=70
    //show good job
    //else if the user scores 70> and >=50
    //show You can do better
    //else (if the user scores 50>)
    //show you need to study more
    private void endTesting(TestCardPanel testCardPanel) {
//            System.out.println("Go to main menu panel");
//            view.getFrame().setContentPane(view.getMainMenuPanel());
//            view.getFrame().revalidate();

        System.out.println("Results");
        testCardPanel.getCardOrientationLabel().setText("Results");

        testCardPanel.getCardArea().setText("Your score");
        testCardPanel.getCardArea().append("\n # of correct cards: " + numberOfCardsCorrect);
        testCardPanel.getCardArea().append("\n # of incorrect cards: " + getNumberOfCardsIncorrect);

        double percentageCorrectCardsScore =  ((double) numberOfCardsCorrect / totalNumberOfCardsTested) * 100;

        testCardPanel.getCardArea().append("\n Percentage score: " + percentageCorrectCardsScore + "%");

        //if percentageCorrectCardsScore >= 95% output "AWESOME JOB"
        //else if percentageCorrectCardsScore >= 70% output "Good job"
        //else if percentageCorrectCardsScore >= 50% output "You can do better"
        //else if percentageCorrectCardsScore < 50% output "Need to study more"
        if (percentageCorrectCardsScore >= 90) {
            testCardPanel.getCardArea().append("\n AWESOME JOB");
            playSound("./data/sound/success.wav");

        } else if (percentageCorrectCardsScore >= 70) {
            testCardPanel.getCardArea().append("\n Good job");
            playSound("./data/sound/success.wav");

        } else if (percentageCorrectCardsScore >= 50) {
            testCardPanel.getCardArea().append("\n You can do better");
            playSound("./data/sound/fail.wav");

        } else {
            testCardPanel.getCardArea().append("\n Need to study more");
            playSound("./data/sound/fail.wav");

        }


        //reset number of cards correct and incorrect variables
        numberOfCardsCorrect = 0;
        getNumberOfCardsIncorrect = 0;



        testCardPanel.switchResultPanel();


    }






    //REQUIRES: X
    //MODIFIES: testCardPanel, this
    //EFFECTS: when result screen is shown after testing,
    //sets response when go back to main menu button is pressed, screen goes back to main menu
    private void addActionToGoBackToMainMenuButton(TestCardPanel testCardPanel) {
        testCardPanel.getGoBackToMainMenuButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("Go to main menu panel");
                view.getFrame().setContentPane(view.getMainMenuPanel());
                view.getFrame().revalidate();
            }
        });
    }








    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: plays sound given a path to sound file
    private void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }














}
