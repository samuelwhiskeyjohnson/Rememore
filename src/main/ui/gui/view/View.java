package ui.gui.view;


import ui.gui.view.dialog.cardboxpaneldialog.AddCardDialog;
import ui.gui.view.dialog.cardboxpaneldialog.ModifyCardDialog;
import ui.gui.view.dialog.cardboxpaneldialog.MoveCardToDifferentBoxDialog;
import ui.gui.view.panel.CardBoxPanel;
import ui.gui.view.panel.MainMenuPanel;
import ui.gui.view.dialog.mainmenupaneldialog.ChangeBoxTimerDialog;
import ui.gui.view.panel.TestCardPanel;

import javax.swing.*;
import java.awt.*;



/*
For Rememore as a GUI application, MVC pattern is used.
View represents the visual structure of the GUI
For example, in the CardBoxPanel the buttons for adding card, removing card, modifying card, moving card to another box
is ordered horizontally on the top of the application. Below that there is the table for cards.



 */


public class View {

    //Width and Height constant, unit is in pixels
    public static final int WINDOW_WIDTH_IN_PIXELS = 1000;
    public static final int WINDOW_HEIGHT_IN_PIXELS = 700;

    //frame
    private JFrame frame;

    //panels
    private MainMenuPanel mainMenuPanel;
    private CardBoxPanel cardBoxPanel;
    private TestCardPanel testCardPanel;

//    //dialogs
//    private AddCardDialog addCardDialog;
//    private ChangeBoxTimerDialog changeBoxTimerDialog;
//    private ModifyCardDialog modifyCardDialog;
//    private MoveCardToDifferentBoxDialog moveCardToDifferentBoxDialog;

    //REQUIRES: X
    //EFFECTS: constructs a view object used to create MVC pattern for GUI application of rememore
    public View(String frameName) {
        frame = new JFrame(frameName);

        //panel initalize
        mainMenuPanel = new MainMenuPanel();
        cardBoxPanel = new CardBoxPanel();
        testCardPanel = new TestCardPanel();

//        dialog initialize
//        addCardDialog = new AddCardDialog(frame);
//        changeBoxTimerDialog = new ChangeBoxTimerDialog(frame);
//        modifyCardDialog = new ModifyCardDialog(frame);
//        moveCardToDifferentBoxDialog = new MoveCardToDifferentBoxDialog(frame);


        //Rememore starts with main menu panel
        frame.add(mainMenuPanel, BorderLayout.CENTER);

//        for testing purpose
//        frame.add( cardBoxPanel, BorderLayout.CENTER );
//        frame.add( testCardPanel, BorderLayout.CENTER );


        //initialize graphics e.g.) set window visible
        setFrameSettings();

    }


    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: sets window setting for GUI application of Rememore. For example, the minimum size of the window of
    //the application is set to 1000 by 700
    private void setFrameSettings() {

        //set the size of window when first opens
        frame.setSize(WINDOW_WIDTH_IN_PIXELS, WINDOW_HEIGHT_IN_PIXELS);

        //set the minimum size of window
        frame.setMinimumSize(new Dimension(WINDOW_WIDTH_IN_PIXELS, WINDOW_HEIGHT_IN_PIXELS));

        //Swing application stop when user exits the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Open the window in the middle of the computer screen
        frame.setLocationRelativeTo(null);

        //window is visible
        frame.setVisible(true);

        frame.setLayout(new BorderLayout());

    }


    //getters---------------------------------------------------------------------------------------------------
    public JFrame getFrame() {
        return frame;
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public CardBoxPanel getCardBoxPanel() {
        return cardBoxPanel;
    }

    public TestCardPanel getTestCardPanel() {
        return testCardPanel;
    }





}
