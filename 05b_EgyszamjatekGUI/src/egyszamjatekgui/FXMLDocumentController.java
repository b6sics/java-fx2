/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egyszamjatekgui;

import static com.sun.javafx.util.Utils.split;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author b6dmin
 */
public class FXMLDocumentController implements Initializable {

    private final ObservableList<String> gamers
            = FXCollections.observableArrayList();

    private void load() {
        try (Scanner be = new Scanner(new File("egyszamjatek2.txt"), "utf8")) {
            String nextLine;
            while (be.hasNextLine()) {
                if (!(nextLine = be.nextLine()).isEmpty()) {
                    gamers.add(nextLine);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("> " + ex.getMessage());
        }
    }

    private boolean save() {
        try (PrintWriter ki = new PrintWriter("egyszamjatek2.txt", "utf8")) {
            gamers.forEach((gamer) -> {
                ki.println(gamer);
            });
            ki.println();
            return true;
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Nem tudtam menteni!");
            alert.showAndWait();
            return false;
        }
    }

    private boolean isValidName(String name) {
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Add meg a játékos nevét!");
            alert.showAndWait();
            return false;
        }
        for (String gamer : gamers) {
            if (split(gamer, " ")[0].equals(name)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hiba");
                alert.setHeaderText(null);
                alert.setContentText("Van már ilyen nevű játékos!");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    private boolean isValidTipNumber(String games) {
        if (split(games.trim(), " ").length == 4) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("A tippek száma nem megfelelő!");
            alert.showAndWait();
            return false;
        }
    }

    private void addNewGamer(String gamer) {
        gamers.add(gamer);
    }

    private void setLabel(String newValue) {
        int number = split(newValue.trim(), " ").length;
        label.setText(number + " db");
    }

    @FXML
    private Label label;

    @FXML
    private TextField txtFieldTips;

    @FXML
    private TextField txtFieldName;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String name = txtFieldName.getText().trim();
        String tips = txtFieldTips.getText().trim();
        if (isValidName(name) && isValidTipNumber(tips)) {
            addNewGamer(name + " " + tips);
            if (save()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Üzenet");
                alert.setHeaderText(null);
                alert.setContentText("Az állomány bővítése sikeres volt!");
                alert.showAndWait();
                txtFieldTips.setText("");
                txtFieldName.setText("");
                txtFieldName.requestFocus();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();
        txtFieldTips.textProperty().addListener((o, oldValue, newValue) -> {
            if (newValue != null) {
                setLabel(newValue);
            }
        });
    }

}
