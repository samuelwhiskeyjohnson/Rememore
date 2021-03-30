package ui.gui.view.panel;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

/*
MainMenuPanel represents the main menu screen when the application first starts
There are 5 box buttons for the users to choose from

It presents the options of
viewing cards for selected card box,
modifying box timer for selected card box,
testing for the selected card box,
loading all cards, and
saving all cards.

 */


public class MainMenuPanel extends JPanel {

    private static final int NUMBER_OF_CARD_BOXES = 5;

    //get image for button and scale image
    private Image boxImage;
    private Image boxImagePressed;

    //buttons panels at the top of main menu screen
    private JPanel buttonsPanel;
    private JLabel selectedBoxLabel;
    private JButton viewCardsButton;
    private JButton modifyBoxTimerButton;
    private JButton testCardsButton;
    private JButton loadCardsButton;
    private JButton saveCardsButton;

    //box buttons panel in the middle of the main menu screen
    private JPanel boxPanel;
    private JButton[] mainMenuBoxButtons;

    //REQUIRES: X
    //EFFECTS: constructs structure for main menu screen
    public MainMenuPanel() {
        //Main Menu Panel
        setLayout(new BorderLayout()); //vgap doesn't do anything

        //Main menu panel has two panels: box panel and buttons panel
        initButtonsPanel();
        initBoxPanel();

    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes buttons at the top of the main menu screen
    private void initButtonsPanel() {
        //buttons panel
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        selectedBoxLabel = new JLabel("Selected box 1");

        viewCardsButton = new JButton("View cards");
        modifyBoxTimerButton = new JButton("Modify Box Timer");
        testCardsButton = new JButton("Test cards with expired timer in the selected box");
        loadCardsButton = new JButton("Load cards");
        saveCardsButton = new JButton("save cards");
        buttonsPanel.add(selectedBoxLabel);
        buttonsPanel.add(viewCardsButton);
        buttonsPanel.add(modifyBoxTimerButton);
        buttonsPanel.add(testCardsButton);
        buttonsPanel.add(loadCardsButton);
        buttonsPanel.add(saveCardsButton);

        add(buttonsPanel, BorderLayout.NORTH);

    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes select box button in the middle of the main menu screen
    private void initBoxPanel() {
        //box panel
        boxPanel = new JPanel();
        boxPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        boxImage = loadBoxImageForButtons();
        boxImagePressed = loadBoxImageForPressedButtons();
        mainMenuBoxButtons = new JButton[NUMBER_OF_CARD_BOXES];

        for (int mainMenuButtonsIndex = 0; mainMenuButtonsIndex < mainMenuBoxButtons.length; mainMenuButtonsIndex++) {
            mainMenuBoxButtons[mainMenuButtonsIndex] = new JButton(new ImageIcon(boxImage));

            //button style
            mainMenuBoxButtons[mainMenuButtonsIndex].setContentAreaFilled(false);
            mainMenuBoxButtons[mainMenuButtonsIndex].setFocusPainted(false);
            mainMenuBoxButtons[mainMenuButtonsIndex].setBorderPainted(false);

            //change image when pressed
            mainMenuBoxButtons[mainMenuButtonsIndex].setPressedIcon(new ImageIcon(boxImagePressed));

            boxPanel.add(mainMenuBoxButtons[mainMenuButtonsIndex], gbc);
        }

        add(boxPanel, BorderLayout.CENTER);
    }


    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: load box image for select box buttons
    private Image loadBoxImageForButtons() {
        BufferedImage buttonIcon;
        Image boxImage = null;
        try {
            buttonIcon = ImageIO.read(new File("./data/image/CardBox.png"));
            boxImage = buttonIcon.getScaledInstance(150, 150, Image.SCALE_SMOOTH); //unit in pixels

        } catch (IOException e) {
            System.out.println("No image found");
        }
        return boxImage;
    }


    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: load box image for pressed box buttons
    private Image loadBoxImageForPressedButtons() {
        BufferedImage buttonIcon;
        Image boxImage = null;
        try {
            buttonIcon = ImageIO.read(new File("./data/image/CardBoxPressed.png"));
            boxImage = buttonIcon.getScaledInstance(150, 150, Image.SCALE_SMOOTH); //unit in pixels

        } catch (IOException e) {
            System.out.println("No image found");
        }
        return boxImage;
    }


    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: changes the box timer, which is in minutes, to format of hour and minutes.
    //for example, 80 minute box timer is formated as 1 hr 20 min
    public String formatDate(int inputBoxTimer) {
        Duration duration = Duration.ofMinutes(inputBoxTimer);

        String formattedTime = String.format("%02d hr %02d min",
                duration.toHours(),
                duration.minusHours(duration.toHours()).toMinutes());
        return formattedTime;
    }


    //REQUIRES: X
    //MODIFIES: X
    //EFFECTS: changes the box timer, which is in minutes, to format of hour and minutes.
    //for example, 80 minute box timer is formated as 1 hr 20 min
    public String formatDate(long inputBoxTimer) {
        Duration duration = Duration.ofMinutes(inputBoxTimer);

        String formattedTime = String.format("%02d hr %02d min",
                duration.toHours(),
                duration.minusHours(duration.toHours()).toMinutes());
        return formattedTime;
    }



    //getters for initialization and button action implementation-----------------------------------------


    public JButton[] getMainMenuBoxButtons() {
        return mainMenuBoxButtons;
    }


    public JButton getViewCardsButton() {
        return viewCardsButton;
    }

    public JButton getModifyBoxTimerButton() {
        return modifyBoxTimerButton;
    }

    public JButton getTestCardsButton() {
        return testCardsButton;
    }

    public JButton getSaveCardsButton() {
        return saveCardsButton;
    }

    public JLabel getSelectedBoxLabel() {
        return selectedBoxLabel;
    }

    public JButton getLoadCardsButton() {
        return loadCardsButton;
    }



}
