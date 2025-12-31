package com.example.javafx.Controller;

import com.example.javafx.Files.FileOpener;
import com.example.javafx.JSON.JSONParser;
import com.example.javafx.XML.XMLFormatter;
import com.example.javafx.XML.XMLParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainController {
    @FXML
    private TextArea jsonText;

    @FXML
    private TextArea xmlText;

    @FXML
    public void openJSONFile(ActionEvent event) throws IOException {
        xmlText.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JSON File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );
        File file = fileChooser.showOpenDialog(getStage(event));
        if (file != null) {
            this.jsonText.setText(FileOpener.readJsonFile(new FileReader(file.getAbsoluteFile())).toString(3));
        }
    }

    @FXML
    public void convertToJSON(ActionEvent event){
        String xmlStr;
        xmlStr=xmlText.getText();
        JSONObject jsonObject=JSONParser.toJSONv2(xmlStr);
        jsonText.setText(jsonObject.toString(10));
    }

    @FXML
    public void openXMLFile(ActionEvent event) throws Exception {
        jsonText.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JSON File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.xml")
        );
        File file = fileChooser.showOpenDialog(getStage(event));
        if (file != null)
            this.xmlText.setText(XMLFormatter.formatXML(FileOpener.readXMLFile(new FileReader(file))));
    }

    @FXML
    public void convertToXML(ActionEvent event) throws Exception {
        String jsonStr,xmlStr;
        jsonStr=jsonText.getText();
        xmlStr =XMLParser.toXMLv2(new JSONObject(jsonStr));
        xmlText.setText(XMLFormatter.formatXML(xmlStr));
    }

    @FXML
    public void closeAll(ActionEvent event){
        jsonText.setText("");
        xmlText.setText("");
    }
    @FXML
    public void clearXML(ActionEvent event){
        xmlText.setText("");
    }

    @FXML
    public void clearJSON(ActionEvent event){
        jsonText.setText("");
    }
    @FXML
    private void exitApplication() {
        Platform.exit();
    }

    private Stage getStage(ActionEvent event) {
        if (event.getSource() instanceof MenuItem menuItem) {
            return (Stage) menuItem.getParentPopup().getOwnerWindow();
        }
        return null;
    }

}
