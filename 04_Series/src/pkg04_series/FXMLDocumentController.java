/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg04_series;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author t1
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField textCim;

    @FXML
    private Spinner<Integer> spEvad;

    @FXML
    private Spinner<Integer> spResz;

    @FXML
    private TableColumn<Series, String> oCim;

    @FXML
    private TableView<Series> tblSorozatok;

    @FXML
    private TableColumn<Series, String> oEvad;

    @FXML
    private TableColumn<Series, String> oResz;

    @FXML
    void hozzaad() {
        if (!textCim.getText().isEmpty()) {
            Series s = new Series();
            s.setCim(textCim.getText());
            s.setEvad(spEvad.getValue());
            s.setResz(spResz.getValue());
            tblSorozatok.getItems().add(s);
            int utolso = tblSorozatok.getItems().size() - 1;
            tblSorozatok.getSelectionModel().select(utolso);
        }
        ment();
        textCim.requestFocus();
    }

    private void beallit(Series s) {
        textCim.setText(s.getCim());
        spEvad.getValueFactory().setValue(s.getEvad());
        spResz.getValueFactory().setValue(s.getResz());
    }

    @FXML
    void leptet() {
        int i = tblSorozatok.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            Series s = tblSorozatok.getItems().get(i);
            s.novel();
            tblSorozatok.getItems().set(i, s);
        }
        ment();
    }

    @FXML
    void modosit() {
        int i = tblSorozatok.getSelectionModel().getSelectedIndex();
        if (!textCim.getText().isEmpty() && i > -1) {
            Series s = new Series();
            s.setCim(textCim.getText());
            s.setEvad(spEvad.getValue());
            s.setResz(spResz.getValue());
            tblSorozatok.getItems().set(i, s);
            tblSorozatok.getSelectionModel().select(i);
        }
        ment();
        textCim.requestFocus();
    }

    @FXML
    void bill(KeyEvent event) {
        if (event.getCode() == KeyCode.INSERT) {
            leptet();
        }
    }

    @FXML
    void torol() {
        int i = tblSorozatok.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            tblSorozatok.getItems().remove(i);
        }
        ment();
        tblSorozatok.requestFocus();
    }

    @FXML
    void ujsorozat() {
        beallit(new Series());
        tblSorozatok.getSelectionModel().select(null);
        textCim.requestFocus();
    }

    @FXML
    void web() throws Exception {
        Desktop.getDesktop().browse(new URI("http://www.sorozatbarat.online"));
    }

    private void betolt(ObservableList<Series> lista) {
        try (Scanner be = new Scanner(new File("sorozatok.txt"), "utf8")) {
            while (be.hasNext()) {
                lista.add(new Series(be.nextLine()));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("> " + ex.getMessage());
        }
    }

    private void ment() {
        try (PrintWriter ki = new PrintWriter("Sorozatok", "utf8")) {
            tblSorozatok.getItems().forEach((f) -> {
                ki.println(f);
            });
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Nem tudtam menteni!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spEvad.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1));
        spResz.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1));
        oCim.setCellValueFactory(new PropertyValueFactory<>("cim"));
        oEvad.setCellValueFactory(new PropertyValueFactory<>("evad"));
        oResz.setCellValueFactory(new PropertyValueFactory<>("resz"));
        betolt(tblSorozatok.getItems());
        tblSorozatok.getSelectionModel().selectedItemProperty().addListener((o, regi, uj) -> {
            if (uj != null) {
                beallit(uj);
            }
        });
    }

}
