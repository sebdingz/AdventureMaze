import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Point2D;


public class GameBoard extends Application {
	private boolean trapTriggered = false;
	private long originalclickTime = 0; 
	private static String highlightColor ="#FFD700";
	private String fastColor = "#0000FF"; // Blue 
	private String mediumColor = "#FF0000"; //Red
	private String slowColor = "#FFD700"; //Yellow
	private PauseTransition resetColorTransition;
	public Stage primaryStage;
	public Button btnSetting;
	public Button btnRules;
	public Button btnRestart;
    public AvatarSelection as;
    public maze mz;
    public MapSelection ms;
    public GridPane grid;
    private int posX = 0; 
    private int posY = 0; 
    private int prevX = 0;       
    private int prevY = 0;       
    private int gridWidth = 50;
    private int gridHeight = 50;
	public Image coin = new Image(getClass().getResourceAsStream("coins.jpg"));
	public Image treasure = new Image(getClass().getResourceAsStream("treasure.jpg"));
	public Image trap = new Image(getClass().getResourceAsStream("trap.jpg"));
	public Image shovel = new Image(getClass().getResourceAsStream("shovel.jpg"));
	public Image key = new Image(getClass().getResourceAsStream("key.jpg"));
	public Image gloves = new Image(getClass().getResourceAsStream("gloves.jpg"));
	public Image grass = new Image(getClass().getResourceAsStream("grass.jpg"));
	public ImageView charactermale;
	public ImageView characterfemale;
	public ImageView coinView;
	public ImageView treasureView;
	public ImageView trapView;
	public ImageView shovelView;
	public ImageView keyView;
	public ImageView glovesView;
	public ImageView grassView;
	public boolean animation = false;
	public boolean shoveldrag = false;
	public boolean keydrag = false;
	public boolean glovesdrag = false;
	public int coinX,coinY,treasureX, treasureY, trapX, trapY;
	public static int nbcoins;
	public Label lblcoins;
    
    public GameBoard(AvatarSelection as,maze mz,MapSelection ms) {
    	this.as=as;
    	this.mz=mz;
    	this.ms=ms;
    }
        @Override
        public void start(Stage primaryStage) {
        	Text flagText = new Text("🚩");
        	flagText.setFont(new Font(30)); 
        	
        	// Creating ImageViews for different game elements
        	coinView = new ImageView(coin);
        	treasureView = new ImageView(treasure);
        	trapView = new ImageView(trap);
        	shovelView = new ImageView(shovel);
        	keyView = new ImageView(key);
        	glovesView = new ImageView(gloves);
        	grassView = new ImageView(grass);
        	
        	// Creating buttons for game settings and rules
        	btnSetting = new Button("Settings");
        	btnRules = new Button("rules");
        	btnRestart = new Button("Restart");
        	
        	// Setting dimensions for the images
        	coinView.setFitHeight(30);
        	coinView.setFitWidth(30);
        	treasureView.setFitHeight(30);
        	treasureView.setFitWidth(30);
        	trapView.setFitHeight(30);
        	trapView.setFitWidth(30);
        	shovelView.setFitHeight(40);
        	shovelView.setFitWidth(40);
        	keyView.setFitHeight(40);
        	keyView.setFitWidth(40);
        	glovesView.setFitHeight(40);
        	glovesView.setFitWidth(40);
        	
        	// Making the image views invisible initially
        	coinView.setVisible(false);
        	treasureView.setVisible(false);
        	trapView.setVisible(false);
        	
        	// Creating a horizontal box to place buttons
        	HBox hb = new HBox(10); 
        	hb.getChildren().addAll(btnRestart,btnSetting, btnRules);
        	
        	// Vertical box and a horizontal box for tools and coin display
        	VBox vb = new VBox(10);
        	HBox hbtools = new HBox(10);
            lblcoins = new Label("Your Coins : "+nbcoins);
        	hbtools.getChildren().addAll(shovelView,keyView,glovesView,lblcoins);
        	HBox.setMargin(lblcoins, new Insets(0,0,0,305));
        	
        	// Creating a StackPane and GridPane for game layout
        	this.primaryStage=primaryStage;
        	StackPane root = new StackPane();
            grid = new GridPane();
        	grid.setPrefSize(600, 400);
        	grid.setGridLinesVisible(true);  
        	
        	// Setting the background image for the grid
        	BackgroundImage backgroundImage = new BackgroundImage(ms.mapselected, 
                    BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);      
                grid.setBackground(new Background(backgroundImage));
                
             // Creating column and row constraints for the grid
            for (int i = 0; i < 12; i++) {
                ColumnConstraints col = new ColumnConstraints(gridWidth);
                grid.getColumnConstraints().add(col);
            }
            for (int j = 0; j < 8; j++) {
                RowConstraints row = new RowConstraints(gridHeight);
                grid.getRowConstraints().add(row);
            }
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 8; j++) {
                    Pane pane = new Pane();
                    grid.add(pane, i, j);
                }
            }
            
         // Randomly determining positions for coins, treasure, and traps
            	coinX = new Random().nextInt(12);
                coinY = new Random().nextInt(8);
                do {
                    treasureX = new Random().nextInt(12);
                    treasureY = new Random().nextInt(8);
                } while(treasureX == coinX && treasureY == coinY);
                do {
                    trapX = new Random().nextInt(12);
                    trapY = new Random().nextInt(8);
                } while(trapX == coinX && trapY == coinY || trapX == treasureX && trapY == treasureY);
            
                // Displaying the positions for the demo
                System.out.println("coinX:"+coinX+" coinY:"+coinY);
                System.out.println("treasureX"+treasureX+" treasureY:"+treasureY);
                System.out.println("trapX:"+trapX+" trapY:"+trapY);
                
                // Adding the game elements to the grid at their determined positions
                grid.add(coinView, coinX, coinY);
                grid.add(treasureView, treasureX, treasureY);
                grid.add(trapView, trapX, trapY);
                StackPane stack = new StackPane();
                stack.setAlignment(Pos.CENTER);                
                stack.getChildren().add(flagText);  
                grid.add(stack, 11, 7);
            as.role.setFitWidth(50);
            as.role.setFitHeight(50);
            grid.add(as.role, posX, posY);
            grid.setFocusTraversable(true);
            
         // Arranging the overall layout
            vb.getChildren().addAll(hb, grid,hbtools);
           root.getChildren().add(vb);
           StackPane.setAlignment(vb, Pos.CENTER);
           StackPane.setMargin(vb, new Insets(60,80,80,100));
           
        // Creating the scene, setting CSS, and displaying the primary stage
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add("style.css");
            scene.setOnKeyPressed(this::handleKey);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Game");
            primaryStage.show();
            
         // Setting action for the 'Settings' button
            btnSetting.setOnAction(e -> {
                Settings settingsApp = new Settings(primaryStage.getScene());
                try {
                    settingsApp.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
         // Setting action for the 'Rules' button
            btnRules.setOnAction(e -> {
                Rules rs = new Rules(primaryStage.getScene());
                try {
                    rs.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            // Setting action for the 'Restart' button
            btnRestart.setOnAction(e -> {
                mz = new maze();
                try {
                    mz.start(primaryStage);// Restarting the game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
         // Setting a Mouse Moved listener on the grid
            grid.setOnMouseMoved(event -> {
                int mouseX = (int) event.getX();
                int mouseY = (int) event.getY();
                
                int col = mouseX / gridWidth;
                int row = mouseY / gridHeight;
                clearHighlightedBackgrounds();
             // Highlighting adjacent cells if the current cell is adjacent to a specific cell
                if (isAdjacent(posX, posY, col, row)) {
                    highlightAdjacentBackgrounds(col, row);
                }
            });
         // Setting a drag over listener on the grid
            grid.setOnDragOver(event -> {
                if (event.getGestureSource() != grid && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });
         // Setting drag detection listeners for different game items
        	shovelView.setOnDragDetected(event -> {
        		shoveldrag = true;
                Dragboard db = shovelView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent cc = new ClipboardContent();
                cc.putString("indicator");
                db.setContent(cc);
                event.consume();
            });
        	keyView.setOnDragDetected(event -> {
        		keydrag = true;
                Dragboard db = shovelView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent cc = new ClipboardContent();
                cc.putString("indicator");
                db.setContent(cc);
                event.consume();
            });
        	glovesView.setOnDragDetected(event -> {
        		glovesdrag = true;
                Dragboard db = shovelView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent cc = new ClipboardContent();
                cc.putString("indicator");
                db.setContent(cc);
                event.consume();
            });
        	// Setting a drop listener for the grid
        	grid.setOnDragDropped(event -> {     		
        		 // Calculate where the item was dropped within the grid
        	    int dropCol = (int) event.getX() / gridWidth; 
        	    int dropRow = (int) event.getY() / gridHeight; 
        	    System.out.println(dropCol + "   " + dropRow);
        	    if (dropCol == trapX && dropRow == trapY&&shoveldrag) {
        	        grid.getChildren().remove(trapView);
        	        trapX=-1;
        	        trapY=-1;
        	        if(nbcoins>2)
        	        {
        	        	nbcoins=nbcoins-1;        	        	
        	        }
        	    }
        	    else if (dropCol == coinX && dropRow == coinY&&glovesdrag) {
        	        grid.getChildren().remove(coinView);
        	        coinX=-1;
        	        coinY=-1;
        	        nbcoins=nbcoins+1;
        	        
        	    }
        	    else if (dropCol == treasureX && dropRow == treasureY&&keydrag) {
        	        grid.getChildren().remove(treasureView);
        	        treasureX=-1;
        	        treasureY=-1;
        	        Random random = new Random();
        	        nbcoins = nbcoins+random.nextInt(6) + 5;  
        	    }
        	    lblcoins.setText("Your Coins : "+nbcoins);
        	    event.setDropCompleted(true);
        	    event.consume();
        	});
        	// Creating a pause transition that waits for a given duration
            resetColorTransition = new PauseTransition(javafx.util.Duration.millis(1500));
            resetColorTransition.setOnFinished(e -> highlightColor = slowColor);
            grid.setOnMouseClicked(this::handleClick);
        }
        private void handleKey(KeyEvent event) {
            // Check if an animation is in progress
        	if(animation) {
        		return;
        	}
        	 // Check if the player is in the trap
        	else if(posX==trapX&&posY==trapY) {
        		Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("You are in the trap! You can't move.Please use the shovel.");
                alert.showAndWait();
        		return ;
        	}
        	// Handle movement
            switch (event.getCode()) {
                case W:
                	if (posY >0) posY--;
                    break;
                case S:
                    if (posY < 7) posY++;
                    break;
                case A:
                    if (posX > 0) posX--;
                    break;
                case D:
                    if (posX < 11) posX++;
                    break;
                default:
                    return;
            }
            updatePosition();
        }
        private void updatePosition() {
        	animation = true;
            // Use a TranslateTransition to animate the movement of the role
        	 TranslateTransition tt = new TranslateTransition(javafx.util.Duration.millis(500), as.role); 
        	    tt.setFromX(prevX * gridWidth);
        	    tt.setFromY(prevY * gridHeight);
        	    tt.setToX(posX * gridWidth);
        	    tt.setToY(posY * gridHeight);
        	    tt.setOnFinished(event -> {
        	        prevX = posX;
        	        prevY = posY;
        	        animation = false;
        	    });
        	    tt.play(); 
        }
     // function for clearing any highlighted backgrounds on the grid
        private void clearHighlightedBackgrounds() {
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 8; j++) {
                    grid.getChildren().get(j * 12 + i).setStyle("-fx-background-color: transparent;");
                }
            }
        }
        private void highlightAdjacentBackgrounds(int col, int row) {
            List<Point2D> adjacentPoints = new ArrayList<>();
        	if(posX==trapX&&posY==trapY&&!trapTriggered)
        	{
        		trapTriggered=true;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("You are in the trap! Use the shovel to get out.");
                alert.showAndWait();
        	}
        	if(posX==11 && posY ==7)
        	{
        		Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Congratulations!");
                alert.setHeaderText(null);
                alert.setContentText("Now you escape the Maze!Click OK to return to main menu.");
                Optional<ButtonType> result = alert.showAndWait();
        	    if(result.isPresent() && result.get()==ButtonType.OK) {
        	     primaryStage.setScene(mz.getMenuScene());
        	    }
        	}
        	
            // Check surrounding grid cells based on player's position and highlight them
            if (col == posX - 1&&row == posY) {
                adjacentPoints.add(new Point2D(col, posY - 1));
                adjacentPoints.add(new Point2D(col, posY));
                adjacentPoints.add(new Point2D(col, posY + 1));
            }
            else if (col == posX - 1&&row == posY-1) {
                adjacentPoints.add(new Point2D(col, row));
                adjacentPoints.add(new Point2D(col, row+1));
                adjacentPoints.add(new Point2D(col+1, row));
            }
            else if (col == posX &&row == posY-1) {
                adjacentPoints.add(new Point2D(col - 1, row));
                adjacentPoints.add(new Point2D(col, row));
                adjacentPoints.add(new Point2D(col + 1, row));
            }
            else if (col == posX + 1&&row == posY) {
                adjacentPoints.add(new Point2D(col, row - 1));
                adjacentPoints.add(new Point2D(col, row));
                adjacentPoints.add(new Point2D(col, row + 1));
            }
            else if (col == posX + 1&&row == posY-1) {
                adjacentPoints.add(new Point2D(col, row));
                adjacentPoints.add(new Point2D(col-1, row));
                adjacentPoints.add(new Point2D(col, row+1));
            }
            else if (col == posX + 1&&row == posY+1) {
                adjacentPoints.add(new Point2D(col, row));
                adjacentPoints.add(new Point2D(col, row-1));
                adjacentPoints.add(new Point2D(col-1, row));
            }
            else if (col == posX&&row == posY+1) {
                adjacentPoints.add(new Point2D(col, row));
                adjacentPoints.add(new Point2D(col-1, row));
                adjacentPoints.add(new Point2D(col+1, row));
            }
            else if (col == posX-1&&row == posY+1) {
                adjacentPoints.add(new Point2D(col, row));
                adjacentPoints.add(new Point2D(col+1, row));
                adjacentPoints.add(new Point2D(col, row-1));
            }
         // Hide all the views by default
            	coinView.setVisible(false);
            	treasureView.setVisible(false);
            	trapView.setVisible(false);
            for (Point2D point : adjacentPoints) {
                if (isWithinBounds(point.getX(), point.getY())) { 
                    grid.getChildren().get((int) (point.getX()*8  + (int) point.getY())+1).setStyle("-fx-background-color: " + highlightColor + ";");
                    if (highlightColor.equals(slowColor) && point.getX() == coinX && point.getY() == coinY) { 
                    	coinView.setVisible(true);
                    } else if(highlightColor.equals(mediumColor) && point.getX() == treasureX && point.getY() == treasureY) {
                        treasureView.setVisible(true);
                    }else if(highlightColor.equals(fastColor) && point.getX() == trapX && point.getY() == trapY) {
                        trapView.setVisible(true);
                    }
                    }
                
                }
            }
        private boolean isWithinBounds(double x, double y) {
            return x >= 0 && x < 12 && y >= 0 && y < 8;
        }
        
     // Check if two given points are adjacent on the grid
        private boolean isAdjacent(double x1, double y1, double x2, double y2) {
            double dx = Math.abs(x1 - x2);
            double dy = Math.abs(y1 - y2);
            return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
        }
        
     // Handle the player's click event to set the highlight color
        private void handleClick(MouseEvent event) {
            long currentClickTime = System.currentTimeMillis();
            long clickInterval = currentClickTime - originalclickTime;
            originalclickTime = currentClickTime;
            if (clickInterval < 180) { 
                highlightColor = fastColor;
            } else if (clickInterval < 500) { 
                highlightColor = mediumColor;
            } else { 
                highlightColor = slowColor;
            }
            resetColorTransition.stop();
            resetColorTransition.play();
        }
}
