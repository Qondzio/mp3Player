import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
 
public class Main extends Application {
    public void start(Stage stage) throws Exception
    {
        Parent mainScene=FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Scene scene=new Scene(mainScene);
        stage.setScene(scene);
        stage.setTitle("MP3 Player");
        stage.getIcons().add(new Image("icon.png"));
        stage.setResizable(false);
        stage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}