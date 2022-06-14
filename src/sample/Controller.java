package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;


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
    @FXML
    private ImageView redo;
    @FXML
    private ImageView undo;

    public boolean pen = true;
    public static int history=0;
    public int u=0;
    public int r=0;

    public void initialize() {
        Image i=new Image(System.getProperty("user.dir")+"\\src\\res\\icons8-undo-60.png");
        Image i0=new Image(System.getProperty("user.dir")+"\\src\\res\\icons8-redo-60.png");
        //undo.setImage(i);
        //redo.setImage(i0);
        GraphicsContext g = canvas.getGraphicsContext2D();
        ObservableList<String> options = FXCollections.observableArrayList("Pen", "Highlighter", "spray", "crayon", "Text Writer");
        comboBox.setItems(options);
        ObservableList<String> options2 = FXCollections.observableArrayList("Line", "Circle", "Elipse", "square");
        comboBox2.setItems(options2);
        ObservableList<String> options3 = FXCollections.observableArrayList("Square pen", "Circle pen");
        comboBox3.setItems(options3);

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
                    history();
                } else {
                    g.setFill(colorPicker.getValue());
                    g.fillRect(x, y, size, size);
                    history();
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
                    history();
                } else {
                    g.setFill(colorPicker.getValue());
                    g.fillOval(x, y, size, size);
                    history();
                }
            });
        }
    }

    public void pen() {
        if (comboBox3.getValue().equals("Circle pen")) {
            pen = false;
            initialize();
        }
        if (comboBox3.getValue().equals("Square pen")) {
            pen = true;
            initialize();
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
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null),"png",new File(System.getProperty("user.dir")+"\\src\\sample\\saved\\Paint.png"));

        }catch (Exception e){
            System.out.println("Failed to save image"+e);
        }
    }
    public void history(){

        //System.out.println(u-r+"=="+0);
            if (u-r==0){
                history++;

                    System.out.println(history+"hgyfcvgtfcvg");
                    try {
                        if (history<=20){
                            WritableImage snapshot =canvas.snapshot(null,null);
                            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null),"png",new File(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+history+".png"));
                        }
                        else {
                            try {
                                Files.deleteIfExists(
                                        Paths.get(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+(history-20)+".png"));
                            }
                            catch (NoSuchFileException e) {
                                System.out.println("No such file/directory exists");
                            }
                            WritableImage snapshot =canvas.snapshot(null,null);
                            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null),"png",new File(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+history+".png"));
                        }
                    }catch (Exception e){
                        System.out.println("Failed to save image"+e);
                    }
                }


            else {
                System.out.println("cscscscscscscscscscscscscs: ");
            for (int i = (history-u+r); i <= history; i++) {
                try {
                    Files.deleteIfExists(
                            Paths.get(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+(i)+".png"));

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            history=history-u+r-1;
            u=0;
            r=0;
                System.out.println(history);
           history();
            }
        }


    public void load(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        Image image= new Image("C:\\Users\\knutd\\IdeaProjects\\sceneB\\src\\Paint.jpg");
        g.drawImage(image,0,0,600,600);
    }

    public void exit(){
        clearH();
        Platform.exit();
    }

    public static void clearH(){
        if (history<=20){
            for (int i = 0; i <history ; i++) {
                try {
                    Files.deleteIfExists(
                            Paths.get(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+(history-i)+".png"));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            for (int i = 0; i <20 ; i++) {
                try {
                    Files.deleteIfExists(
                            Paths.get(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+(history-i)+".png"));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void undo(){

        GraphicsContext g = canvas.getGraphicsContext2D();
       // System.out.println(r);
        if (history-u+r>1){
            u++;
            clearWorkspace();
            Image image= new Image(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+((history-u)+r)+".png");
            g.drawImage(image,0,0,image.getHeight(),image.getWidth());
        }
        //System.out.println("undo "+u+"u ,"+r+"r ,"+"an: "+((history-u)+r)+"|h: "+history);
    }
    public void redo(){

        GraphicsContext g = canvas.getGraphicsContext2D();
        //System.out.println((history-u)+r);
        if ((history-u+r+1)<=history&&u!=0){
            r++;
            clearWorkspace();
            Image image= new Image(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+((history-u)+r)+".png");
            g.drawImage(image,0,0,image.getHeight(),image.getWidth());
        }
        //System.out.println("redo "+u+"u ,"+r+"r ,"+"an: "+((history-u)+r)+"|h: "+history);
    }
    public void clearWorkspace(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void mouseMoved(javafx.scene.input.MouseEvent mouseEvent ) {
            String []x=String.valueOf(mouseEvent.getX()).split("\\.");
            String []y=String.valueOf(mouseEvent.getY()).split("\\.");
            lx.setText("X: "+x[0]);
            ly.setText("Y: "+y[0]);
    }
}