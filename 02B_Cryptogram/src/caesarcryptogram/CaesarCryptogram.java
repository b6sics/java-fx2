/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caesarcryptogram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import panel.Panel;

/**
 *
 * @author t1
 */
public class CaesarCryptogram extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        FXMLDocumentController dc = loader.getController();

        stage.setOnCloseRequest((e) -> {
            if (!dc.isMentve()
                    && !Panel.igennem("A módosítások elvesznek!", "Folytatod?")) {
                e.consume();
            }
        });

        Scene scene = new Scene(root);
        stage.setTitle("Caesar kódolás");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
