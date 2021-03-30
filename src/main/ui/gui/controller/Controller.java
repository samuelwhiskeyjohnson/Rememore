package ui.gui.controller;

import ui.Persistence;
import ui.gui.view.View;


/*
For Rememore as a GUI application, MVC pattern is used.
Controller represents the initialization and change of data by user input to be shown on GUI

Controller is broken down to 3 controllers for each 3 panels: MainMenuPanel, CardBoxPanel, TestCardPanel
MainMenuController initializes and response to user input on the MainMenuPanel GUI
CardBoxController initializes and response to user input on the CardBoxPanel GUI
TestCardController initializes and response to user input on the TestCardPanel GUI

 */
public class Controller {

    //select box is a variable accessed in all 3 sub controllers via getters and setters
    //chose to be static for this variable to be global within controll package as this variable is used very frequently
    static int selectedBox; //package private

    //connection needed for MVC model
    private Persistence persistenceModel;
    private View view;


    //sub controllers of Controller
    private MainMenuController mainMenuController;
    private CardBoxController cardBoxController;
    private TestCardController testCardsService;

    //REQUIRES: X
    //EFFECTS: constructs a controller used to create MVC pattern for GUI application of rememore
    public Controller(Persistence inputPersistenceModel, View inputView) {

        selectedBox = 1; //no box is selected

        persistenceModel = inputPersistenceModel;

        //for automatic loading
//        persistenceModel.loadCards();

        view = inputView;

        initCardBoxController();
        initTestCardController();
        initMainMenuController();

    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes one of the three sub controllers: CardBoxController
    private void initCardBoxController() {
        cardBoxController = new CardBoxController(persistenceModel, view);
        cardBoxController.initCardBoxController();

    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes one of the three sub controllers: TestCardController
    private void initTestCardController() {
        testCardsService = new TestCardController(persistenceModel, view);
        testCardsService.initTestCardController();
    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes one of the three sub controllers: MainMenuController
    private void initMainMenuController() {
        mainMenuController = new MainMenuController(persistenceModel, view, cardBoxController, testCardsService);
        mainMenuController.initMainMenuView();
        mainMenuController.initMainMenuController();
    }








    //getters--------------------------------------------------------

    public static int getSelectedBox() {
        return selectedBox;
    }


    public static void setSelectedBox(int selectedBox) {
        Controller.selectedBox = selectedBox;
    }
}
