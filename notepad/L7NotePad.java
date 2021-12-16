/** ********** notepad ********** */
package notepad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author ayman elgammal
 */
public class L7NotePad extends Application {

    MenuBar menuBar;
    Menu File;
    Menu Edit;
    Menu Help;
    TextArea txt;

    BufferedWriter fout;
    BufferedReader fin;

    FileChooser openFile = new FileChooser();

    FileChooser saveFile = new FileChooser();

    Dialog<String> aboutDialog = new Dialog<String>();
    ButtonType button;

    Dialog<String> saveDialog = new Dialog<String>();
    ButtonType yesButton, noButton, cancelButton;

    MenuItem newItem;
    MenuItem openItem;
    MenuItem saveItem;
    MenuItem exitItem;

    MenuItem undoItem;
    MenuItem cutItem;
    MenuItem copyItem;
    MenuItem pasteItem;
    MenuItem deleteItem;
    MenuItem selectAllItem;

    MenuItem aboutItem;
    MenuItem javaCompilerItem;

    BorderPane pane;

    @Override
    public void init() {

        txt = new TextArea();

        openFile.setTitle("Open File");
        openFile.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));

        saveFile.setTitle("save File");
        saveFile.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        saveFile.getExtensionFilters().addAll(new ExtensionFilter("Text Files", ".txt"));

        aboutDialog.setTitle("About");
        aboutDialog.setContentText("Made by: Ayman Elgammal\n" + "aymanyou15@gmail.com\n" + "Dec 14, 2021\n" + "V1.2");
        button = new ButtonType("OK", ButtonData.OK_DONE);
        aboutDialog.getDialogPane().getButtonTypes().add(button);

        saveDialog.setTitle("Save");
        saveDialog.setContentText("Do you want to save this file?");
        noButton = new ButtonType("no", ButtonData.NO);
        yesButton = new ButtonType("yes", ButtonData.YES);
        cancelButton = new ButtonType("cancel", ButtonData.CANCEL_CLOSE);
        saveDialog.getDialogPane().getButtonTypes().addAll(noButton, yesButton, cancelButton);

        menuBar = new MenuBar();

        File = new Menu("File");
        Edit = new Menu("Edit");
        Help = new Menu("Help");

        newItem = new MenuItem("New");
        openItem = new MenuItem("Open...");
        saveItem = new MenuItem("Save");
        exitItem = new MenuItem("Exit");

        newItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {// not finished yet
                saveDialog.show();
                txt.clear();
            }

        });

        undoItem = new MenuItem("Undo");
        cutItem = new MenuItem("Cut");
        copyItem = new MenuItem("Copy");
        pasteItem = new MenuItem("Paste");
        deleteItem = new MenuItem("Delete");
        selectAllItem = new MenuItem("Select All");

        undoItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.undo();
            }

        });

        cutItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.cut();
            }

        });

        copyItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.copy();
            }

        });

        pasteItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.paste();
            }

        });

        deleteItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.deleteNextChar();
            }

        });

        selectAllItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt.selectAll();
            }

        });

        aboutItem = new MenuItem("About");
        javaCompilerItem = new MenuItem("Java Compiler");

        aboutItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                aboutDialog.show();
            }

        });

        javaCompilerItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {// not finished yet

            }

        });

        /**
         * *********************************************************************
         */
        File.getItems().addAll(newItem, openItem, saveItem, exitItem);
        Edit.getItems().addAll(undoItem, cutItem, copyItem, pasteItem, deleteItem, selectAllItem);
        Help.getItems().addAll(aboutItem, javaCompilerItem);

        menuBar.getMenus().addAll(File, Edit, Help);

        pane = new BorderPane();
        pane.setTop(menuBar);
        pane.setCenter(txt);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(pane, 700, 500);

        primaryStage.setTitle("NotePad");
        primaryStage.setScene(scene);
        primaryStage.show();

        openItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File filein = openFile.showOpenDialog(primaryStage);

                Platform.runLater(() -> {
                    try {
                        FileReader reader = new FileReader(filein);
                        fin = new BufferedReader(reader);
                        String line;
                        do {
                            txt.appendText(fin.readLine() + "\n");

                        } while ((line = fin.readLine()) != null);

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(L7NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(L7NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

            }

        });

        saveItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File fileout = saveFile.showSaveDialog(primaryStage);

                Platform.runLater(() -> {
                    try {
                        FileWriter writer = new FileWriter(fileout.getPath());
                        fout = new BufferedWriter(writer);
                        fout.write(txt.getText());
                        fout.close();

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(L7NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(L7NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }

        });

        exitItem.setOnAction((ActionEvent event) -> {
            primaryStage.close();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
