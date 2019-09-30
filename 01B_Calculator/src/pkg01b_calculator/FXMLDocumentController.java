/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01b_calculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 * @author t1
 */
public class FXMLDocumentController implements Initializable {

    ScriptEngine js = new ScriptEngineManager().getEngineByExtension("js");

    @FXML
    private TextField txtEredmeny;

    @FXML
    private TextField txtKifejezes;

    @FXML
    void sugoAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SugoAblak.fxml"));
        Scene scene = new Scene(root);
        Stage sugoablak = new Stage();
        sugoablak.setTitle("Súgó");
        sugoablak.getIcons().add(new Image("calc.png"));
        sugoablak.setResizable(false);
        sugoablak.initModality(Modality.APPLICATION_MODAL);
        sugoablak.setScene(scene);
        sugoablak.showAndWait();

        txtKifejezes.requestFocus();
        txtKifejezes.end();
    }

    @FXML
    void torlesAction(ActionEvent event) {
        txtKifejezes.clear();
        txtKifejezes.requestFocus();
    }

    @FXML
    void vagolapAction(ActionEvent event) {
        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent tartalom = new ClipboardContent();
        tartalom.putString(txtEredmeny.getText());
        cb.setContent(tartalom);

        txtKifejezes.requestFocus();
        txtKifejezes.end();
    }

    @FXML
    void zarojelAction(ActionEvent event) {
        String s = txtKifejezes.getText();
        if (!s.isEmpty()) {
            txtKifejezes.setText("(" + s + ")");
        }
        txtKifejezes.requestFocus();
        txtKifejezes.end();
    }

    private String szamol(String kifejezes) {
        String eredmeny;
        kifejezes = kifejezes.replace(",", ".");
        kifejezes = kifejezes.replace("pi", "Math.PI");
        kifejezes = kifejezes.replace("sqrt", "Math.sqrt");
        try {
            eredmeny = "" + js.eval(kifejezes);
        } catch (Exception e) {
            eredmeny = "";
        }
        if (eredmeny.equals("null")) {
            eredmeny = "0";
        } else if (eredmeny.contains("fun")) {
            eredmeny = "";
        }
        return eredmeny;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtKifejezes.textProperty().
                addListener((o, regi, uj)
                        -> txtEredmeny.setText(szamol(uj)));
    }

}
