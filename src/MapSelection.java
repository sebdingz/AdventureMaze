import java.time.Duration;
import java.util.Optional;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MapSelection extends Application {
	public Stage primaryStage;
	public Image mapImage1 = new Image(getClass().getResourceAsStream("grass.jpg"));
	public Image mapImage2 = new Image(getClass().getResourceAsStream("water.gif"));
    public static Image mapselected;
	public Pane root;
	public Scene mapselectionscene;
    public maze mz ;
    public MapSelection(maze mz) {
    	this.mz=mz;
    }
    @Override
    public void start(Stage primaryStage)  {
    	this.primaryStage=primaryStage;
    	root = new Pane();
    	ImageView mapView = new ImageView(mapImage1);
    	ImageView mapView2 = new ImageView(mapImage2);

    	mapView.setFitWidth(380); 
    	mapView.setFitHeight(280);
    	mapView.setLayoutX(115); 
    	mapView.setLayoutY(50); 
    	mapView2.setFitWidth(380); 
    	mapView2.setFitHeight(280);
    	mapView2.setLayoutX(115); 
    	mapView2.setLayoutY(50); 
    	mapView2.setVisible(false);
     	Button leftButton = new Button("<");
    	leftButton.setLayoutX(0);   
    	leftButton.setLayoutY(190);  
        // Layout of mapselection menu
    	Button rightButton = new Button(">");
    	rightButton.setLayoutX(560); 
    	rightButton.setLayoutY(190); 
    	// Setting action for the '>' button
    	rightButton.setOnAction(e -> {
    	    if(mapView.isVisible()) {
    	        mapView.setVisible(false);
    	        mapView2.setVisible(true);
    	        
    	    } else {
    	        mapView.setVisible(true);
    	        mapView2.setVisible(false);
    	        
    	    }
    	});
    	// Setting action for the '<' button
    	leftButton.setOnAction(e -> {
    	    if(mapView.isVisible()) {
    	        mapView.setVisible(false);
    	        mapView2.setVisible(true);
    	    } else {
    	        mapView.setVisible(true);
    	        mapView2.setVisible(false);
    	    } 
    	});
    	
    	Button btnContinue = new Button("Continue");
    	btnContinue.setLayoutX(255); 
    	btnContinue.setLayoutY(340); 
    	Button btnBack = new Button("Back");
    	btnBack.setStyle("-fx-font-size: 15px;");
    	btnBack.setLayoutX(0); 
    	btnBack.setLayoutY(10);  
    	btnBack.setOnAction(e -> {
	        primaryStage.setScene(mz.getMenuScene()); 
	    });
    	btnContinue.setOnAction(e -> {
    		if(mapView.isVisible()) {
     	       mapselected=mapImage1;
     	    } else {
     	    	mapselected=mapImage2;
     	    } 
	        showavatarselection();
	    });
    	// calls the next page
    	root.getChildren().addAll(mapView,mapView2, leftButton, rightButton, btnContinue, btnBack);
    	mapselectionscene = new Scene(root, 600, 400);
    	mapselectionscene.getStylesheets().add("style.css");
    	primaryStage.setTitle("Map Selection");
    	primaryStage.setScene(mapselectionscene);
    	primaryStage.show();
    	    }
    public void showavatarselection() {
        AvatarSelection as = new AvatarSelection(this,mz);
        try {
     	   as.start(primaryStage);
        }catch (Exception e) {
     	   e.printStackTrace();
        }
     }
    }
