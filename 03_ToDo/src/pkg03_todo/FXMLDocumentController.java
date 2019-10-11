/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg03_todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import panel.Panel;

/**
 *
 * @author t1
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ListView<String> lstFeladatok;

    @FXML
    private TextField txtUjFeladat;

    @FXML
    void elejere() {
        int index = lstFeladatok.
                getSelectionModel().getSelectedIndex();
        if (index > 0) {
            String f = lstFeladatok.getItems().get(index);
            lstFeladatok.getItems().remove(index);
            lstFeladatok.getItems().add(0, f);
            lstFeladatok.getSelectionModel().select(0);
        }
        txtUjFeladat.requestFocus();
    }

    @FXML
    void fel() {
        int index = lstFeladatok.
                getSelectionModel().getSelectedIndex();
        if (index > 0) {
            String f = lstFeladatok.getItems().get(index);
            lstFeladatok.getItems().remove(index);
            lstFeladatok.getItems().add(index - 1, f);
            lstFeladatok.getSelectionModel().select(index - 1);
        }
        txtUjFeladat.requestFocus();
    }

    @FXML
    void hozzaad() {
        String feladat = txtUjFeladat.getText();
        if (!feladat.isEmpty()) {
            lstFeladatok.getItems().add(feladat);
            int utolso = lstFeladatok.getItems().size() - 1;
            lstFeladatok.getSelectionModel().select(utolso);
        }
        txtUjFeladat.requestFocus();
        txtUjFeladat.selectAll();
    }

    @FXML
    void kilep() {
        if (ment() || Panel.igennem("Kilépés",
                "Nem tudtam menteni! Ki szeretnél lépni?")) {
            Platform.exit();
        }
    }

    private boolean ment() {
        try (PrintWriter ki
                = new PrintWriter("teendok.txt", "utf8")) {
            lstFeladatok.getItems().forEach((f) -> {
                ki.println(f);
            });
            return true;
        } catch (IOException ex) {
            Panel.hiba("Hiba a mentésnél!", ex.getMessage());
            return false;
        }
    }

    @FXML
    void le() {
        int index = lstFeladatok.
                getSelectionModel().getSelectedIndex();
        int utolso = lstFeladatok.getItems().size() - 1;
        if (index > -1 && index < utolso) {
            String f = lstFeladatok.getItems().get(index);
            lstFeladatok.getItems().remove(index);
            lstFeladatok.getItems().add(index + 1, f);
            lstFeladatok.getSelectionModel().select(index + 1);
        }
        txtUjFeladat.requestFocus();
    }

    @FXML
    void modosit() {
        int index = lstFeladatok.
                getSelectionModel().getSelectedIndex();
        if (index > -1) {
            lstFeladatok.getItems().
                    set(index, txtUjFeladat.getText());
        }
        txtUjFeladat.requestFocus();
        txtUjFeladat.selectAll();
    }

    @FXML
    void torol() {
        betolt();
        int index = lstFeladatok.
                getSelectionModel().getSelectedIndex();
        if (index > -1) {
            lstFeladatok.getItems().remove(index);
        }
        txtUjFeladat.requestFocus();
    }

    @FXML
    void vegere() {
        int index = lstFeladatok.
                getSelectionModel().getSelectedIndex();
        int utolso = lstFeladatok.getItems().size() - 1;
        if (index > -1 && index < utolso) {
            String f = lstFeladatok.getItems().get(index);
            lstFeladatok.getItems().remove(index);
            lstFeladatok.getItems().add(f);
            lstFeladatok.getSelectionModel().select(utolso);
        }
        txtUjFeladat.requestFocus();
    }

    private void betolt() {
        try (Scanner be = new Scanner(
                new File("teendok.txt", "utf8"))) {
            while (be.hasNextLine()) {
                lstFeladatok.getItems().add(be.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("input> " + ex.getMessage());
        }
    }

    @FXML
    void bill(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            torol();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstFeladatok.getSelectionModel().selectedItemProperty().
                addListener((v, regi, uj) -> {
                    if (uj != null) {
                        txtUjFeladat.setText(uj);
                    } else {
                        txtUjFeladat.setText("");
                    }
                });
    }
}
