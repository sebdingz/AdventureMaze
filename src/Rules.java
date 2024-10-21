import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Rules extends Application {
	public Scene scene;
	public maze mz;
	private final Scene previousScene;
    public Rules(Scene scene)
    {
         	previousScene=scene;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Rules");
        BorderPane borderPane = new BorderPane();
        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> {primaryStage.setScene(previousScene); });
        borderPane.setTop(btnBack);
        VBox vbox = new VBox(10);
        Text rulesText = new Text("  Welcome to the maze game! You need to walk from the starting point to the end of the game, which is marked by 🚩 in the bottom right corner. Use WASD to move the villain in the right direction. Along the way you will encounter traps, gold coins and treasure. Your torch glows yellow, red, and blue, and the light it emits depends on the speed of your mouse clicks. Drag and drop the shovel, glove and key under the map to collect gold coins, treasures or escape the traps. Gloves collecting gold coins will give you an additional gold coin. Keys to open treasure will generate random gold coins. If you are caught in a trap, you need to spend a gold coin to destroy the trap using the shovel.\r\n"
        		+ "Enjoy the game!");
        rulesText.getStyleClass().add("text-style");
        rulesText.setWrappingWidth(620 * 0.95);  
        
        vbox.getChildren().add(rulesText);
        VBox.setMargin(rulesText, new Insets(10,0,0,25));
        borderPane.setCenter(vbox);
        scene = new Scene(borderPane, 620, 600);
        scene.getStylesheets().add("stylerules.css");
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue() * 0.8; 
            rulesText.setWrappingWidth(width);
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
