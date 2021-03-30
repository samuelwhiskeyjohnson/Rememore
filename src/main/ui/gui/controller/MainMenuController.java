package ui.gui.controller;

import model.CardBox;
import model.CardBoxManager;
import ui.Persistence;
import ui.gui.view.View;
import ui.gui.view.dialog.mainmenupaneldialog.ChangeBoxTimerDialog;
import ui.gui.view.panel.MainMenuPanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;



/*
For Rememore as a GUI application, MVC pattern is used.
Controller represents the initialization and change of data by user input to be shown on GUI

Controller is broken down to 3 controllers for each 3 panels: MainMenuPanel, CardBoxPanel, TestCardPanel
MainMenuController initializes and response to user input on the MainMenuPanel GUI
CardBoxController initializes and response to user input on the CardBoxPanel GUI
TestCardController initializes and response to user input on the TestCardPanel GUI

 */

public class MainMenuController {

    //connection needed for MVC model
    private Persistence persistenceModel;
    private View view;
    private CardBoxController cardBoxController;
    private TestCardController testCardsService;


    //REQUIRES: X
    //EFFECTS: constructs a MainMenuController
    public MainMenuController(Persistence inputPersistenceModel,
                              View inputView,
                              CardBoxController inputCardBoxController,
                              TestCardController inputTestCardsService) {
        persistenceModel = inputPersistenceModel;
        view = inputView;
        cardBoxController = inputCardBoxController;
        testCardsService = inputTestCardsService;

    }






    //Main menu view----------------------------------------------------------------------------------------------------
    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes main menu by setting timer on the boxes
    public void initMainMenuView() {
        MainMenuPanel mainMenuPanel = view.getMainMenuPanel();

        createBoxButtons(mainMenuPanel);

    }

    //REQUIRES: X
    //MODIFIES: mainMenuPanel, this
    //EFFECTS: initializes box timer
    public void createBoxButtons(MainMenuPanel mainMenuPanel) {
        JButton[] mainMenuButtons = mainMenuPanel.getMainMenuBoxButtons();

        for (int mainMenuButtonsIndex = 0; mainMenuButtonsIndex < mainMenuButtons.length; mainMenuButtonsIndex++) {
            //get box timer from model
            CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();

            CardBox cardBox = cardBoxManager.findCardBoxInCardBoxManager(mainMenuButtonsIndex + 1);
            String boxButtonText = mainMenuPanel.formatDate(cardBox.getBoxMinutesTimer());

            //set box timer text
            JButton boxButton = mainMenuButtons[mainMenuButtonsIndex];
            boxButton.setText(boxButtonText);
            boxButton.setVerticalTextPosition(AbstractButton.TOP);
            boxButton.setHorizontalTextPosition(AbstractButton.CENTER);
        }


    }

    //Main menu logic---------------------------------------------------------------------------------------------------
    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes action for buttons for select box buttons, view cards cards button,
    // modify cards button, test cards button, load cards button, save cards button
    public void initMainMenuController() {
        MainMenuPanel mainMenuPanel = view.getMainMenuPanel();

        //Add action to 5 buttons
        addActionToBoxButton(mainMenuPanel);


        //add action to view cards button
        addActionToViewCardsButton(mainMenuPanel);

        //add action to modify box timer button
        addActionToModifyBoxTimerButton(mainMenuPanel);

        //add action to test cards button
        addActionToTestCardsButton(mainMenuPanel);

        //add action to load cards button
        addActionToLoadCardsButton(mainMenuPanel);

        //add action to save cards button
        addActionToSaveCardsButton(mainMenuPanel);

    }

    //???
    //REQUIRES: X
    //MODIFIES: mainMenuPanel
    //EFFECTS: sets response when add card button is pressed, sets box selected to whichever box is clicked
    //if user selected box 1, box selected is set to 1, for example.
    private void addActionToBoxButton(MainMenuPanel mainMenuPanel) {
        JButton[] mainMenuButtons = mainMenuPanel.getMainMenuBoxButtons();

        for (int mainMenuButtonsIndex = 0; mainMenuButtonsIndex < mainMenuButtons.length; mainMenuButtonsIndex++) {


            final int finalMainMenuButtonsIndex = mainMenuButtonsIndex;
            mainMenuButtons[finalMainMenuButtonsIndex].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

//                    selectedBox = finalMainMenuButtonsIndex + 1;
                    Controller.setSelectedBox(finalMainMenuButtonsIndex + 1);
                    playSound("./data/sound/selectBox.wav");

                    mainMenuPanel.getSelectedBoxLabel().setText("Selected box " + Controller.getSelectedBox());

                }
            });


        }

    }



    //REQUIRES: X
    //MODIFIES: mainMenuPanel, this
    //EFFECTS: sets response when view cards button is pressed, sets to the screen to CardBoxPanel
    private void addActionToViewCardsButton(MainMenuPanel mainMenuPanel) {
        mainMenuPanel.getViewCardsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                view.getFrame().setContentPane(view.getCardBoxPanel());
                view.getFrame().revalidate();
                System.out.println("Viewing cards");

                cardBoxController.initCardBoxView();


            }
        });
    }

    //REQUIRES: X
    //MODIFIES: mainMenuPanel
    //EFFECTS: sets response when modify box timer button is pressed, opens a ChangeBoxTimerDialog
    private void addActionToModifyBoxTimerButton(MainMenuPanel mainMenuPanel) {
        mainMenuPanel.getModifyBoxTimerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

//                ModifyBoxTimerDialog setBoxTimerDialog = null;
//                String changedTime = null;
//                    JOptionPane.showConfirmDialog(null, "Select a card box!");

                ChangeBoxTimerDialog changeBoxTimerCardDialog = new ChangeBoxTimerDialog(view.getFrame());
//                ChangeBoxTimerDialog changeBoxTimerCardDialog = view.getChangeBoxTimerDialog();

                //add action to dialog buttons must be before dialog being visible or else it won't work
                addActionToDialogChangeBoxTimerButton(changeBoxTimerCardDialog);
                addActionToDialogCancelChangeBoxTimerButton(changeBoxTimerCardDialog);
                changeBoxTimerCardDialog.setVisible(true);


//                ModifyBoxTimerDialog setBoxTimerDialog = new ModifyBoxTimerDialog(view.getFrame());
//                setBoxTimerDialog.setVisible(true);
//
//                String changedTime = setBoxTimerDialog.getMinutes();
//                if (changedTime != null) {
//                    //update time in card box timer
//                    int convertedTime = Integer.parseInt(changedTime);
//
//                    CardBox selectedCardBox = soleCardBoxManager.findCardBoxInCardBoxManager(selectedBox);
//                    selectedCardBox.setBoxMinutesTimer(convertedTime);
//
//                    initMainMenuView();
////                    //update time on GUI
////                    String formattedBoxMinuteTimer = view.getMainMenuPanel().formatDate(convertedTime);
////                    view.getMainMenuPanel().getBoxTwoButton().setText(formattedBoxMinuteTimer);
//
//                }



            }
        });
    }


    //REQUIRES: X
    //MODIFIES: changeBoxTimerCardDialog, this
    //EFFECTS: when ChangeBoxTimerDialog is open, sets response when change box timer button is pressed
    //box timer changes in the model, dialog closes, and GUI is refreshed
    private void addActionToDialogChangeBoxTimerButton(ChangeBoxTimerDialog changeBoxTimerCardDialog) {
        changeBoxTimerCardDialog.getChangeTimerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                //get changed time from GUI
                String changedTime = changeBoxTimerCardDialog.getChangeBoxTimerField().getText();

                //Convert time to formatted time
                int convertedTime = Integer.parseInt(changedTime);
                CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();
                CardBox selectedCardBox = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());

                //show formatted time on GUI
                selectedCardBox.setBoxMinutesTimer(convertedTime);

                //refresh GUI
                initMainMenuView();

                changeBoxTimerCardDialog.setVisible(false);

            }
        });

    }


    //REQUIRES: X
    //MODIFIES: modifyCardDialog
    //EFFECTS: when ChangeBoxTimerDialog is open, sets response when cancel change box timer button is pressed
    //dialog closes
    private void addActionToDialogCancelChangeBoxTimerButton(ChangeBoxTimerDialog modifyCardDialog) {
        modifyCardDialog.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                modifyCardDialog.setVisible(false);

            }
        });

    }



    //REQUIRES: X
    //MODIFIES: mainMenuPanel, this
    //EFFECTS:
    private void addActionToTestCardsButton(MainMenuPanel mainMenuPanel) {
        mainMenuPanel.getTestCardsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                if (Controller.getSelectedBox() != -1) {
                    view.getFrame().setContentPane(view.getTestCardPanel());
                    view.getFrame().revalidate();

                    System.out.println("start to test cards");
                    testCardsService.initTestCardView();

                }
            }
        });
    }

    //REQUIRES: X
    //MODIFIES: mainMenuPanel, this
    //EFFECTS: sets response when load cards button is pressed, loads all cards into model from JSON
    // and GUI is refreshed
    private void addActionToLoadCardsButton(MainMenuPanel mainMenuPanel) {
        mainMenuPanel.getLoadCardsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                persistenceModel.load();
                initMainMenuView();

            }
        });
    }

    //REQUIRES: X
    //MODIFIES: mainMenuPanel, this
    //EFFECTS: sets response when save cards button is pressed, saves all cards from the model to JSON
    //and GUI is refreshed
    private void addActionToSaveCardsButton(MainMenuPanel mainMenuPanel) {
        mainMenuPanel.getSaveCardsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");


                //get all card box with each cards involved in..
                persistenceModel.save();
//                soleCardBoxManager.saveAllCards();


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
