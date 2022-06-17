package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    public  Thread thread;
    Cursor c;

    public void initialize() {
        Image i=new Image(System.getProperty("user.dir")+"\\src\\res\\icons8-undo-60.png");
        Image i0=new Image(System.getProperty("user.dir")+"\\src\\res\\icons8-redo-60.png");
        //undo.setImage(i);
        //redo.setImage(i0);
        GraphicsContext g = canvas.getGraphicsContext2D();

        ObservableList<String> options2 = FXCollections.observableArrayList("Line", "Circle", "Elipse", "square");
        comboBox2.setItems(options2);
        ObservableList<String> options3 = FXCollections.observableArrayList("Square pen", "Circle pen");
        comboBox3.setItems(options3);
        colorPicker.setValue(Color.BLACK);
        //drawShapes(g);



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

    /*private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
    }*/


    public void pen() {
        if (comboBox3.getValue().equals("Circle pen")) {
            pen = false;
            //canvas.getScene().setCursor(Cursor.CROSSHAIR);
            initialize();
        }
        if (comboBox3.getValue().equals("Square pen")) {
           // canvas.getScene().setCursor(Cursor.CROSSHAIR);
            pen = true;
            initialize();
        }
    }

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
    public void saveAs(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
                );
        fileChooser.setInitialFileName("Untitled");
        Window stage = new Stage();


        try {
            WritableImage snapshot =canvas.snapshot(null,null);
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            System.out.println(fileChooser.getSelectedExtensionFilter().getDescription());
            System.out.println(file.getAbsolutePath());
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null), fileChooser.getSelectedExtensionFilter().getDescription(),new File(file.getAbsolutePath()));
        }
        catch (Exception e){System.out.println(e);}
    }
    public void load(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("SVG", "*.svg"));
        Window stage = new Stage();
        // WritableImage snapshot =canvas.snapshot(null,null);
        try {
            File file = fileChooser.showOpenDialog(stage);
            GraphicsContext g = canvas.getGraphicsContext2D();
            Image image= new Image(file.getPath());

            if (image.getWidth()>canvas.getWidth() || image.getHeight()>canvas.getHeight()){
                canvasW.setText(String.valueOf(image.getWidth()));
                canvasH.setText(String.valueOf(image.getHeight()));
                scrollPane.setMaxWidth(image.getWidth());
                scrollPane.setMaxHeight(image.getHeight());
                canvas.setHeight(image.getHeight());
                canvas.setWidth(image.getWidth());
                g.drawImage(image,0,0,image.getWidth(), image.getHeight());
            }

        }
        catch (Exception e){System.out.println(e);}
    }

    public void history(){
                history++;
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

    public void exit(){
        clearH();
        Platform.exit();
    }

    public static void clearH(){
        if (history<=20 && history!=0){
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
        else {
            u++;
            clearWorkspace();
        }
        //System.out.println("undo "+u+"u ,"+r+"r ,"+"an: "+((history-u)+r)+"|h: "+history);
    }
    public void redo(){

        GraphicsContext g = canvas.getGraphicsContext2D();
        if ((history-u+r+1)<=history&&u!=0){
            r++;
            clearWorkspace();
            Image image= new Image(System.getProperty("user.dir")+"\\src\\sample\\saved\\h\\Paint"+((history-u)+r)+".png");
            g.drawImage(image,0,0,image.getHeight(),image.getWidth());
        }
        //System.out.println("redo "+u+"u ,"+r+"r ,"+"an: "+((history-u)+r)+"|h: "+history);*/
    }
    public void clearWorkspace(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
       // clearH();
    }

    public void mouseMoved(javafx.scene.input.MouseEvent mouseEvent ) {
            String []x=String.valueOf(mouseEvent.getX()).split("\\.");
            String []y=String.valueOf(mouseEvent.getY()).split("\\.");
            lx.setText("X: "+x[0]);
            ly.setText("Y: "+y[0]);
    }
}