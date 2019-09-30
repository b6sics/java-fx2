/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01a_average;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author t1
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txtJegyek;

    @FXML
    private Label lblDarab;

    @FXML
    private Label lblAtlag;

    @FXML
    private Button btnUj;

    @FXML
    private Button btnKilep;

    @FXML
    void kilep(ActionEvent event) {
        Platform.exit();
    }

    private void szamol(String s) {
        int db = 0;
        int osszeg = 0;
        char c;
        for (int ii = 0; ii < s.length(); ii++) {
            c = s.charAt(ii);
            if (c > '0' && c < '6') {
                db++;
                osszeg += (int) c - (int) '0';
            }
        }
        lblDarab.setText("" + db);
        if (db > 0) {
            lblAtlag.setText(String.format(
                    "%.2f", (double) osszeg / db));
        } else {
            lblAtlag.setText("0.00");
        }
    }

    @FXML
    void uj(ActionEvent event) {
        txtJegyek.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtJegyek.textProperty()
                .addListener((o, regi, uj) -> szamol(uj));
    }

}
