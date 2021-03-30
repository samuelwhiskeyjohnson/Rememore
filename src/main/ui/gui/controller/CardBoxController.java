package ui.gui.controller;

import model.Card;
import model.CardBox;
import model.CardBoxManager;
import ui.Persistence;
import ui.gui.view.View;
import ui.gui.view.dialog.cardboxpaneldialog.AddCardDialog;
import ui.gui.view.dialog.cardboxpaneldialog.ModifyCardDialog;
import ui.gui.view.dialog.cardboxpaneldialog.MoveCardToDifferentBoxDialog;
import ui.gui.view.dialog.cardboxpaneldialog.RemoveCardDialog;
import ui.gui.view.panel.CardBoxPanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/*
For Rememore as a GUI application, MVC pattern is used.
Controller represents the initialization and change of data by user input to be shown on GUI

Controller is broken down to 3 controllers for each 3 panels: MainMenuPanel, CardBoxPanel, TestCardPanel
MainMenuController initializes and response to user input on the MainMenuPanel GUI
CardBoxController initializes and response to user input on the CardBoxPanel GUI
TestCardController initializes and response to user input on the TestCardPanel GUI

 */

public class CardBoxController {

    //connection needed for MVC model
    private Persistence persistenceModel;
    private View view;


    //REQUIRES: X
    //EFFECTS: constructs CardBoxController
    public CardBoxController(Persistence inputPersistenceModel, View inputView) {
        persistenceModel = inputPersistenceModel;
        view = inputView;
    }



    //CardBox panel view--------------------------------------------------------------------------
    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes card box screen by putting cards into the table
    public void initCardBoxView() {

        CardBoxPanel cardBoxPanel = view.getCardBoxPanel();
        cardBoxPanel.getBoxIndicator().setText("Box: " + Controller.getSelectedBox());

        // cardBoxNum this is 0 there is no card to load
        CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();
        CardBox loadCardBox = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());
        List<Card> listOfCardsLoadedToCardTable;

        listOfCardsLoadedToCardTable = loadCardBox.getTableOfCards();
        System.out.println("Number of cards in this table: " + listOfCardsLoadedToCardTable.size());

        //load the cards into the GUI table
        cardBoxPanel.getCardTableModel().setRowCount(0);
        for (Card card : listOfCardsLoadedToCardTable) {
            String loadFrontInfo = card.getFrontInfo();
            String loadBackInfo = card.getBackInfo();
//                long loadTimer = card.getTimerUntilTestedAgain();
//                //calc remaining time to test;
//                long currentTime = System.currentTimeMillis();
//                long remainingTime = currentTime - loadTimer;
//
//                //modify string format
//                String remainingTimeString = view.getMainMenuPanel().formatDate( remainingTime/(60*1000) );

            //time to test
            Date startTime = card.getStartTime();
            Date timeToTest = loadCardBox.addMinutesToDate(startTime, loadCardBox.getBoxMinutesTimer());

            //current time
            Calendar cal = Calendar.getInstance();
            Date currentTime = cal.getTime();

            //time to test - current time = how much time left


            boolean isCardTestable = currentTime.after(timeToTest) || currentTime.compareTo(timeToTest) == 0;

            int loadID = card.getCardID();

            Object[] cardInTable = new Object[]{loadID, loadFrontInfo, loadBackInfo, isCardTestable};
            cardBoxPanel.getCardTableModel().addRow(cardInTable);
        }



    }



    //CardBox Panel logic------------------------------------------------------------------------------
    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes action for buttons for going back to main menu, add card, remove card, modify card,
    //and move card
    public void initCardBoxController() {
        playSound("./data/sound/welcome.wav");

        CardBoxPanel cardBoxPanel = view.getCardBoxPanel();

        //add action to back to main menu button
        addActionToBackToMainMenuButton(cardBoxPanel);

        //add action to add card button
        addActionToAddCardButton(cardBoxPanel);

        //add action to remove card button
        addActionToRemoveCardButton(cardBoxPanel);

        //add action to modify card button
        addActionToModifyCardButton(cardBoxPanel);

        //add action to move card to another box button
        addActionToMoveCardToAnotherBoxButton(cardBoxPanel);

    }

    //REQUIRES: X
    //MODIFIES: cardBoxPanel, this
    //EFFECTS: sets response when go back to main menu button is pressed, screen goes back to main menu
    private void addActionToBackToMainMenuButton(CardBoxPanel cardBoxPanel) {
        cardBoxPanel.getBackToMainMenuButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("Back");
                view.getFrame().setContentPane(view.getMainMenuPanel());
                view.getFrame().revalidate();

            }
        });
    }



    //REQUIRES: X
    //MODIFIES: cardBoxPanel, this
    //EFFECTS: sets response when add button is pressed, AddCardDialog is shown
    private void addActionToAddCardButton(CardBoxPanel cardBoxPanel) {
        cardBoxPanel.getAddCardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("add");

//                //set editable mode
//                view.getCardBoxPanel().setModifiedMode();
//
//                //add row with default value
//                view.getCardBoxPanel().getCardTableModel().addRow(
//                        new Object[]{
//                                0000,
//                                "",
//                                "",
//                                ""});

                AddCardDialog addCardDialog = new AddCardDialog(view.getFrame());
                //don't do this it loops. 1st card added is 1 time, 2nd card added 2 times, 3rd card added added 3 times
//                AddCardDialog addCardDialog = view.getAddCardDialog();

                //add action to dialog buttons must be before dialog being visible or else it won't work
                addActionToDialogAddCardButton(addCardDialog);
                addActionToDialogCancelAddCardButton(addCardDialog);
                addCardDialog.setVisible(true);


            }
        });
    }

    //REQUIRES: X
    //MODIFIES: addCardDialog, this
    //EFFECTS: when AddCardDialog is opened,
    // sets response when add button is pressed, card is added to the model, dialog closes, and refresh GUI
    private void addActionToDialogAddCardButton(AddCardDialog addCardDialog) {

        addCardDialog.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("Add confirmed");
                addCardDialog.setVisible(false);
                //card is added
                String frontInfo = addCardDialog.getFrontInfoTextField().getText();
                String backInfo = addCardDialog.getBackInfoTextField().getText();

                CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();

                cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox()).addCard(frontInfo, backInfo);
                initCardBoxView();

            }
        });

    }

    //REQUIRES: X
    //MODIFIES: addCardDialog
    //EFFECTS: when AddCardDialog is opened,
    // sets response when cancel button is pressed, dialog closes
    private void addActionToDialogCancelAddCardButton(AddCardDialog addCardDialog) {
        addCardDialog.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                addCardDialog.setVisible(false);

            }
        });


    }




    //REQUIRES: X
    //MODIFIES: cardBoxPanel
    //EFFECTS: sets response when remove button is pressed, RemoveCardDialog is shown
    private void addActionToRemoveCardButton(CardBoxPanel cardBoxPanel) {
        cardBoxPanel.getRemoveCardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("remove");
                RemoveCardDialog removeCardDialog = new RemoveCardDialog(view.getFrame());
                addActionToDialogRemoveCardButton(removeCardDialog);
                addActionToDialogCancelRemoveCardButton(removeCardDialog);

                removeCardDialog.setVisible(true);
//                int selectedRow = view.getCardBoxPanel().getCardTable().getSelectedRow();
//                // check for selected row first
//                if (selectedRow != -1) {
//                    // remove selected row from the model
//                    view.getCardBoxPanel().getCardTableModel().removeRow(selectedRow);
//                    JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
//                }

            }
        });
    }


    //REQUIRES: X
    //MODIFIES: removeCardDialog, this
    //EFFECTS: when RemoveCardDialog is opened,
    // sets response when remove button is pressed, card is removed from the model, dialog closes and GUI is refreshed
    private void addActionToDialogRemoveCardButton(RemoveCardDialog removeCardDialog) {
        removeCardDialog.getRemoveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("Remove confirmed");
                removeCardDialog.setVisible(false);
                //card is removed
                int cardIDToRemove = Integer.parseInt(removeCardDialog.getIdentifyCardToRemoveTextField().getText());

                CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();

                CardBox cardBoxToRemovingCard = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());
                cardBoxToRemovingCard.removeCard(cardIDToRemove);


                initCardBoxView();
            }
        });

    }

    //REQUIRES: X
    //MODIFIES: removeCardDialog
    //EFFECTS: when RemoveCardDialog is opened,
    // sets response when cancel button is pressed, dialog closes
    private void addActionToDialogCancelRemoveCardButton(RemoveCardDialog removeCardDialog) {
        removeCardDialog.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                removeCardDialog.setVisible(false);

            }
        });

    }





    //REQUIRES: X
    //MODIFIES: cardBoxPanel
    //EFFECTS: sets response when modify button is pressed, ModifyCardDialog is shown
    private void addActionToModifyCardButton(CardBoxPanel cardBoxPanel) {
        cardBoxPanel.getModifyCardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("modify");

                ModifyCardDialog modifyCardDialog = new ModifyCardDialog(view.getFrame());
                addActionToDialogModifyCardButton(modifyCardDialog);
                addActionToDialogCancelModifyCardButton(modifyCardDialog);
                modifyCardDialog.setVisible(true);


//                //set editable mode
//                view.getCardBoxPanel().setModifiedMode();

            }
        });
    }


    //REQUIRES: X
    //MODIFIES: modifyCardDialog, this
    //EFFECTS: when ModifyCardDialog is opened,
    //sets response when modify button is pressed, card is modified from the model, dialog closes and GUI is refreshed
    private void addActionToDialogModifyCardButton(ModifyCardDialog modifyCardDialog) {
        modifyCardDialog.getModifyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("Modify confirmed");
                modifyCardDialog.setVisible(false);
                //card is modified
                int cardIDToModify = Integer.parseInt(modifyCardDialog.getIdentifyCardToModifyTextField().getText());
                String modifiedFrontInfo = modifyCardDialog.getOverWriteFrontInfoTextField().getText();
                String modifiedBackInfo = modifyCardDialog.getOverwriteBackInfoTextField().getText();



                CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();

                CardBox cardBoxToModifyCard = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());
                cardBoxToModifyCard.modifyCard(cardIDToModify, modifiedFrontInfo, modifiedBackInfo);


                initCardBoxView();
            }
        });


    }

    //REQUIRES: X
    //MODIFIES: modifyCardDialog
    //EFFECTS: when ModifyCardDialog is opened,
    //sets response when cancel button is pressed, dialog closes
    private void addActionToDialogCancelModifyCardButton(ModifyCardDialog modifyCardDialog) {
        modifyCardDialog.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                modifyCardDialog.setVisible(false);

            }
        });

    }






    //REQUIRES: X
    //MODIFIES: cardBoxPanel
    //EFFECTS: sets response when move button is pressed, MoveCardToDifferentBoxDialog is shown
    private void addActionToMoveCardToAnotherBoxButton(CardBoxPanel cardBoxPanel) {
        cardBoxPanel.getMoveCardToAnotherBoxButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("moved");

                MoveCardToDifferentBoxDialog modifyCardDialog = new MoveCardToDifferentBoxDialog(view.getFrame());
                addActionToDialogMoveCardButton(modifyCardDialog);
                addActionToDialogCancelMoveCardButton(modifyCardDialog);
                modifyCardDialog.setVisible(true);

            }
        });
    }


    //REQUIRES: X
    //MODIFIES: modifyCardDialog, this
    //EFFECTS: when MoveCardToDifferentBoxDialog is opened,
    //sets response when move button is pressed, card is moved in the model, dialog closes and GUI is refreshed
    private void addActionToDialogMoveCardButton(MoveCardToDifferentBoxDialog modifyCardDialog) {
        modifyCardDialog.getMoveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                System.out.println("Move confirmed");
                modifyCardDialog.setVisible(false);


                //card is moved
                int cardIDToMove = Integer.parseInt(modifyCardDialog.getIdentifyCardToMoveTextField().getText());

                String destinationCardBoxNumberString = modifyCardDialog.getMoveToDifferentBoxTextField().getText();
                int destinationCardBoxNum = Integer.parseInt(destinationCardBoxNumberString);



                CardBoxManager cardBoxManager = persistenceModel.getSoleCardBoxManager();

                CardBox originCardBox = cardBoxManager.findCardBoxInCardBoxManager(Controller.getSelectedBox());
                CardBox destinationCardBox = cardBoxManager.findCardBoxInCardBoxManager(destinationCardBoxNum);


                originCardBox.moveCardToDifferentBox(cardIDToMove, destinationCardBox);


                initCardBoxView();
            }
        });


    }


    //REQUIRES: X
    //MODIFIES: modifyCardDialog
    //EFFECTS:  when MoveCardToDifferentBoxDialog is opened,
    //sets response when cancel  button is pressed dialog closes
    private void addActionToDialogCancelMoveCardButton(MoveCardToDifferentBoxDialog modifyCardDialog) {
        modifyCardDialog.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./data/sound/click.wav");

                modifyCardDialog.setVisible(false);
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
