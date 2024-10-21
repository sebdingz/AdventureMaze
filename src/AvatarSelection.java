import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AvatarSelection extends Application {
	public Stage primaryStage;
	public Image maleimage = new Image(getClass().getResourceAsStream("male.jpg"));
	public Image femaleimage = new Image(getClass().getResourceAsStream("female.jpg"));
	public ImageView role;
	public MapSelection ms;
	public maze mz;
    public AvatarSelection(MapSelection ms,maze mz) {
    	this.mz=mz;
    	this.ms=ms;
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        ImageView maleImageView = new ImageView(maleimage);
        maleImageView.setFitWidth(80);
        maleImageView.setFitHeight(80);
        ToggleButton role1 = new ToggleButton();
        role1.setGraphic(maleImageView);

        ImageView femaleImageView = new ImageView(femaleimage);
        femaleImageView.setFitWidth(80);
        femaleImageView.setFitHeight(80);
        ToggleButton role2 = new ToggleButton();
        role2.setGraphic(femaleImageView);
       
        ToggleGroup group = new ToggleGroup();
        role1.setToggleGroup(group);
        role2.setToggleGroup(group);
        role1.setOnAction(e -> {
            role = maleImageView;
        });
        role2.setOnAction(e -> {
            role = femaleImageView;
        });
        // Get the role parameter to make it available to the next page
        HBox roleBox = new HBox(100);  // spacing between buttons
        roleBox.setAlignment(Pos.CENTER);
        roleBox.getChildren().addAll(role1, role2);

        Label title = new Label("Choose your role");
        title.setFont(new Font(24));

        Button btnBegin = new Button("Let's begin!!");
        btnBegin.setPrefSize(150, 40);
        btnBegin.setOnAction(e -> {
            showgameboard();
        });

        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-font-size: 15px;");
    	btnBack.setOnAction(e -> {
	        primaryStage.setScene(ms.mapselectionscene); 
	    });
        VBox mainBox = new VBox(20);  // spacing between elements
        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(btnBack, title, roleBox, btnBegin);
        mainBox.setPadding(new Insets(20, 20, 20, 20));  
        VBox.setMargin(btnBack, new Insets(-35,515,20,-30));
        VBox.setMargin(btnBegin, new Insets(40,0,0,0));
        VBox.setMargin(roleBox, new Insets(20,0,0,0));
      // Layout of the avatarselection page
        Scene scene = new Scene(mainBox, 600, 400);
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("Role Selection");
        primaryStage.setScene(scene);
        primaryStage.show();
    } 
    public void showgameboard() {
        GameBoard gb = new GameBoard(this,mz,ms);
        try {
     	   gb.start(primaryStage);
        }catch (Exception e) {
     	   e.printStackTrace();
        }
     }
}
