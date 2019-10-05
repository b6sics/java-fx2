/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caesarcryptogram;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import panel.Panel;

/**
 *
 * @author t1
 */
public class FXMLDocumentController implements Initializable {

    private boolean mentve = true;

    public boolean isMentve() {
        return mentve;
    }

    @FXML
    private TextArea txaNyilt;

    @FXML
    private Slider sldEltolas;

    @FXML
    private TextArea TxaTitkos;

    @FXML
    void mentes(ActionEvent event) {
        FileChooser fajlvalaszto = new FileChooser();

        fajlvalaszto.setTitle("Mentés másként...");

        ExtensionFilter szuro = new ExtensionFilter("Szöveges fájl", "*.txt");
        fajlvalaszto.getExtensionFilters().add(szuro);

        fajlvalaszto.setInitialDirectory(new File("."));

        Window ablak = txaNyilt.getScene().getWindow();

        File f = fajlvalaszto.showSaveDialog(ablak);

        if (f != null) {
            try (PrintWriter ki = new PrintWriter(f.getAbsoluteFile(), "utf8")) {
                ki.print(txaNyilt.getText());
                mentve = true;
            } catch (IOException ex) {
                System.err.println("Hiba> " + ex.getMessage());
                Panel.hiba("Hiba", ex.getMessage());
            };
        }
    }

    @FXML
    void megnyitas(ActionEvent event) {
        if (!mentve && !Panel.igennem("A módosítások elvesznek!", "Folytatod?")) {
            return;
        }
        FileChooser fajlvalaszto = new FileChooser();

        fajlvalaszto.setTitle("Megnyitás");

        ExtensionFilter szuro = new ExtensionFilter("Szöveges fájl", "*.txt");
        fajlvalaszto.getExtensionFilters().add(szuro);

        fajlvalaszto.setInitialDirectory(new File("."));

        Window ablak = txaNyilt.getScene().getWindow();

        File f = fajlvalaszto.showOpenDialog(ablak);

        if (f != null) {
            try (Scanner be = new Scanner(f.getAbsoluteFile(), "utf8")) {
                txaNyilt.clear();
                while (be.hasNextLine()) {
                    txaNyilt.appendText(be.nextLine() + "\n");
                }
                mentve = true;
            } catch (IOException ex) {
                System.err.println("Hiba> " + ex.getMessage());
                Panel.hiba("Hiba", ex.getMessage());
            };
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txaNyilt.textProperty().addListener((o, regi, uj) -> {
            TxaTitkos.setText(Caesar.kodol(uj, (int) sldEltolas.getValue()));
            mentve = false;
        });

        sldEltolas.valueProperty().addListener((o, regi, uj) -> {
            TxaTitkos.setText(Caesar.kodol(txaNyilt.getText(), uj.intValue()));
        });
    }
}
