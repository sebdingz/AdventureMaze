import javafx.scene.shape.Arc;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.SVGPath;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class maze extends Application {
	public maze mz;
    private Stage primaryStage;
    private Pane root;
    private GridPane gameGrid;
    private Scene menuscene;
   // private Media BGM = new Media(getClass().getResource("clairedelune.mp3").toString());
    //public MediaPlayer mp;
    private Circle eye1;
    private Circle eye2;
    private Ellipse ellipse1;
    private Ellipse ellipse2;
    private static final int CENTER_X = 585;
    private static final int CENTER_Y = 70;
    private static final int ELLIPSE_RADIUS_X = 13;
    private static final int ELLIPSE_RADIUS_Y = 13;
    private static final int CENTER_X2 = 520;
    private static final int CENTER_Y2 = 15;
    private static final int ELLIPSE_RADIUS_X2 = 13;
    private static final int ELLIPSE_RADIUS_Y2 = 13;
    public Scene getMenuScene() {
		return menuscene; 
    }
    public void maze(maze mz) {
    	this.mz=mz;
    }
    public void start(Stage stage) {
        primaryStage = stage;
        root = new Pane();
        menuscene = new Scene(root, 600, 450);
        menuscene.setOnMouseMoved(e -> updateEyePosition(e.getX(), e.getY()));
        menuscene.getStylesheets().add("style.css");
        primaryStage.setTitle("Maze");
        primaryStage.setScene(menuscene);
        primaryStage.setMinWidth(300);
        primaryStage.setMaxWidth(900);
        primaryStage.setMinHeight(300);
        primaryStage.setMaxHeight(800);
        showStartPage();
        primaryStage.show();
    }

    public void showStartPage() { 	
        Label lblmaze = new Label("ADVENTURE MAZE");
        Button btnStart = new Button("Start");
        Button btnSettings = new Button("Settings");
        Button btnRules = new Button("Rules");
        Button btnQuit = new Button("Quit");
        
        btnStart.setStyle("-fx-font-size: 20px;");
        btnSettings.setStyle("-fx-font-size: 20px;");
        btnRules.setStyle("-fx-font-size: 20px;");
        btnQuit.setStyle("-fx-font-size: 20px;");

        lblmaze.setLayoutX(195); 
        lblmaze.setLayoutY(72);  
        
        btnStart.setLayoutX(262.5); 
        btnStart.setLayoutY(150); 

        btnSettings.setLayoutX(248); 
        btnSettings.setLayoutY(225); 

        btnRules.setLayoutX(262.5);
        btnRules.setLayoutY(300); 

        btnQuit.setLayoutX(262.5);
        btnQuit.setLayoutY(375); 
        // Setting action for the 'start' button
        btnStart.setOnAction(e -> {
        	MapSelection ms = new MapSelection(this);
			try {
				ms.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        // Setting action for the 'rules' button
        btnRules.setOnAction(e -> {
        	Rules rs = new Rules(primaryStage.getScene());
			try {
				rs.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        // Setting action for the 'quit' button
        btnQuit.setOnAction(e ->{
        	try {
        		primaryStage.close();
        	}catch (Exception e1) {
				e1.printStackTrace();
			}
        });
        // Setting action for the 'settings' button
        btnSettings.setOnAction(e ->{
        	Settings settings = new Settings(primaryStage.getScene());
        	try {
        		settings.start(primaryStage);
        	}catch (Exception e1) {
				e1.printStackTrace();
			}
        });
        ///Button functionalities
        ellipse1 = new Ellipse(CENTER_X, CENTER_Y, ELLIPSE_RADIUS_X, ELLIPSE_RADIUS_Y);
        ellipse1.setStroke(Color.BLACK);
        ellipse1.setFill(Color.WHITE);
        ellipse1.setStrokeWidth(2.0);
        ellipse2 = new Ellipse(CENTER_X2, CENTER_Y2, ELLIPSE_RADIUS_X2, ELLIPSE_RADIUS_Y2);
        ellipse2.setStroke(Color.BLACK);
        ellipse2.setFill(Color.WHITE);
        ellipse2.setStrokeWidth(2.0);
        eye1 = new Circle(CENTER_X, CENTER_Y, 2, Color.BLACK);
        eye2 = new Circle(CENTER_X2, CENTER_Y2, 2, Color.BLACK);
        Arc arcLine = new Arc();
        arcLine.setCenterX(515); 
        arcLine.setCenterY(96);
        arcLine.setRadiusX(40);
        arcLine.setRadiusY(26);
        arcLine.setStartAngle(0);
        arcLine.setLength(180);
        arcLine.setRotate(-140);
        arcLine.setType(ArcType.OPEN);
        arcLine.setStroke(Color.BLACK);
        arcLine.setStrokeWidth(2);
        arcLine.setFill(Color.TRANSPARENT);
        root.getChildren().addAll(arcLine,ellipse1,eye1,ellipse2,eye2,lblmaze, btnStart, btnSettings, btnRules, btnQuit);
        ///smile face
    }
    private void updateEyePosition(double mouseX, double mouseY) {
        double localMouseX = mouseX - CENTER_X;
        double localMouseY = mouseY - CENTER_Y;
        double angle = Math.atan2(localMouseY, localMouseX);
        double eyeX = (ELLIPSE_RADIUS_X - 3.1) * Math.cos(angle);
        double eyeY = (ELLIPSE_RADIUS_Y - 3.1) * Math.sin(angle);
        if (Math.hypot(localMouseX, localMouseY) < Math.hypot(eyeX, eyeY)) {
            eyeX = localMouseX;
            eyeY = localMouseY;
        }
        eye1.setCenterX(eyeX + CENTER_X);
        eye1.setCenterY(eyeY + CENTER_Y);
        eye2.setCenterX(eyeX + CENTER_X2);
        eye2.setCenterY(eyeY + CENTER_Y2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}