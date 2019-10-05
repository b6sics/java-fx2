/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg02a_temperature;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 *
 * @author t1
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label lblFok;

    @FXML
    private Slider sldCsuszka;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sldCsuszka.valueProperty().addListener((o, regi, uj) -> {
            int c = uj.intValue();
            int f = (int) Math.round(9.0 / 5.0 * c + 32);
            lblFok.setText(c + " °C = " + f + " °F");
        });
    }

}
