package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javax.imageio.ImageIO;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField brushSize;
    @FXML
    private CheckBox eraser;
    @FXML
    private CheckBox circle;
    @FXML
    private CheckBox square;
    @FXML
    private ComboBox comboBox;
    @FXML
    private ComboBox comboBox2;
    @FXML
    private ComboBox comboBox3;
    @FXML
    private TextField canvasH;
    @FXML
    private TextField canvasW;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label lx;
    @FXML
    private Label ly;
    @FXML
    private Button zo;
    @FXML
    private Button zi;


    public boolean pen = true;


    public void initialize() {

        ObservableList<String> options = FXCollections.observableArrayList("Pen", "Highlighter", "spray", "crayon", "Text Writer");
        comboBox.setItems(options);
        ObservableList<String> options2 = FXCollections.observableArrayList("Line", "Circle", "Elipse", "square");
        comboBox2.setItems(options2);
        ObservableList<String> options3 = FXCollections.observableArrayList("Square pen", "Circle pen");
        comboBox3.setItems(options3);


        GraphicsContext g = canvas.getGraphicsContext2D();
        // g.setFill(Color.BLACK);
        //g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (pen == true) {
            canvas.setOnMouseDragged(e -> {
                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;

                if (eraser.isSelected()) {
                    g.clearRect(x, y, size, size);
                } else {
                    g.setFill(colorPicker.getValue());
                    g.fillRect(x, y, size, size);
                    String []ldx=String.valueOf(e.getX()).split("\\.");
                    String []ldy=String.valueOf(e.getY()).split("\\.");
                    lx.setText("X: "+ldx[0]);
                    ly.setText("Y: "+ldy[0]);
                }
            });
            canvas.setOnMouseClicked(e -> {
                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;


                if (eraser.isSelected()) {
                    g.clearRect(x, y, size, size);
                } else {
                    g.setFill(colorPicker.getValue());
                    g.fillRect(x, y, size, size);
                }

            });
        } else {
            canvas.setOnMouseDragged(e -> {
                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;

                if (eraser.isSelected()) {
                    g.clearRect(x, y, size, size);

                } else {
                    g.setFill(colorPicker.getValue());
                    g.fillOval(x, y, size, size);
                    String []ldx=String.valueOf(e.getX()).split("\\.");
                    String []ldy=String.valueOf(e.getY()).split("\\.");
                    lx.setText("X: "+ldx[0]);
                    ly.setText("Y: "+ldy[0]);
                }
            });
            canvas.setOnMouseClicked(e -> {
                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;


                if (eraser.isSelected()) {
                    g.clearRect(x, y, size, size);
                } else {
                    g.setFill(colorPicker.getValue());
                    g.fillOval(x, y, size, size);
                }
            });
        }

    }

    public void pen() {
        if (comboBox3.getValue().equals("Circle pen")) {
            pen = false;
            initialize();
            //System.out.println(comboBox3.getValue());
        }
        if (comboBox3.getValue().equals("Square pen")) {
            pen = true;
            initialize();
            //System.out.println(comboBox3.getValue());
        }


    }
   /** public void zoomIn(){
        canvas.setScaleX(2);
        canvas.setScaleY(2);
    }
    public void zoomOut(){
        canvas.setScaleX(1);
        canvas.setScaleY(1);
    }**/

    public void setCanv() {
        if (!(Double.parseDouble(canvasW.getText())>4000||Double.parseDouble(canvasH.getText())>4000)){
            scrollPane.setMaxWidth(Double.parseDouble(canvasW.getText()));
            scrollPane.setMaxHeight(Double.parseDouble(canvasH.getText()));
            canvas.setWidth(Double.parseDouble(canvasW.getText()));
            canvas.setHeight(Double.parseDouble(canvasH.getText()));
            initialize();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Canvas size error");
            alert.setContentText("Looks like you've exceeded the max width or height of sheet!");

            alert.showAndWait();
        }
    }


    public void save(){
        try {
            WritableImage snapshot =canvas.snapshot(null,null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null),"png",new File("Paint.png"));

        }catch (Exception e){
            System.out.println("Failed to save image"+e);
        }
    }

    public void exit(){
        Platform.exit();
    }

    public void mouseMoved(javafx.scene.input.MouseEvent mouseEvent ) {

            String []x=String.valueOf(mouseEvent.getX()).split("\\.");
            String []y=String.valueOf(mouseEvent.getY()).split("\\.");
            lx.setText("X: "+x[0]);
            ly.setText("Y: "+y[0]);
    }
}