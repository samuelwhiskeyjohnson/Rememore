package ui;

import model.CardBoxManager;
import ui.cli.CommandLineInterface;
import ui.gui.controller.Controller;
import ui.gui.view.View;

/*
Main class serves the purpose of creating CommandLineInterface type object to start the applicaton.
Hence, there are no more than few lines of code.

 */
public class Main {
    public static void main(String[] args) {



        //CLI MVC
        // uncomment the next 4 lines for command line interface version of Rememore
//        CardBoxManager model = new CardBoxManager();
//        Persistence persistModel = new Persistence(model);
//        CommandLineInterface cli = new CommandLineInterface(persistModel,model);
//        cli.mainMenuUI();


        //GUI MVC
        //uncomment the next 4 lines for graphical user interface version of Rememore
        CardBoxManager model = new CardBoxManager();
        Persistence persistModel = new Persistence(model);
        View v = new View("Rememore");
        Controller c = new Controller(persistModel, v);




    }
}
