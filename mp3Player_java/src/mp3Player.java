import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class mp3Player implements Initializable{


    @FXML
    private Button nextButton;

    @FXML
    private Button muteButton;

    @FXML
    private Button playButton;

    @FXML
    private Button previousButton;

    @FXML
    private Slider songProgress;

    @FXML
    private Button stopButton;
    
    @FXML
    private Label songName;

    @FXML
    private Slider volumeBar;

    @FXML
    private Button uploadMusic;

    private File directory;
    private ArrayList<File> playlist;
    private File[] files;
    private Media media;
    private MediaPlayer mediaPlayer;
    private int songNumber=1;

    //listener for songProgress
    private void ListenerSongMusic()
    {
        media=new Media(playlist.get(songNumber).toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaPlayer.currentTimeProperty().addListener((ObservableValue,oldValue,newValue)->
         {
            songProgress.setMax(mediaPlayer.getTotalDuration().toSeconds()); 
            songProgress.setValue(newValue.toSeconds());
         });
    }

    //listener for Volume
    private void ListenerVolume()
    {
        volumeBar.valueProperty().addListener((ObservableValue,oldValue,newValue)->
        {
            mediaPlayer.setVolume((double) (newValue));
        }); 
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        playlist = new ArrayList<File>();
		
		directory = new File("music");
		
		files = directory.listFiles();
		
		if(files != null) 
        {
			for(File file : files) 
            {
				playlist.add(file);
			}
        }

        ListenerSongMusic();
        ListenerVolume();

        songProgress.setOnMousePressed(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent arg0) 
            {
                mediaPlayer.seek(Duration.seconds(songProgress.getValue())); 
            }
        });

        
        songProgress.setOnMouseDragged(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent arg0) 
            {
                mediaPlayer.seek(Duration.seconds(songProgress.getValue())); 
            }
        });
    }
    
    @FXML
    void playMusic(ActionEvent event) 
    {
        
        songName.setText(playlist.get(songNumber).getName());
        mediaPlayer.play();
    }
    
    @FXML
    void stopMusic(ActionEvent event)
    {
        mediaPlayer.pause();
    }
    
    @FXML
    void nextMusic(ActionEvent event) 
    {

        mediaPlayer.stop();
        songNumber++;
        if(songNumber==playlist.size())
        {
            songNumber=0;
        }    
        ListenerSongMusic();
        songName.setText(playlist.get(songNumber).getName());
        mediaPlayer.play();
    }

    @FXML
    void previousMusic(ActionEvent event) 
    {
        mediaPlayer.stop();
        songNumber--;
        if(songNumber==-1)
        {
            songNumber=playlist.size()-1;
        }
        ListenerSongMusic();
        songName.setText(playlist.get(songNumber).getName());
        mediaPlayer.play();
    }

    @FXML
    void uploadMusic (ActionEvent event)
    {
        mediaPlayer.pause();
        FileChooser fileChooser= new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("mp3 Files", "*.mp3"));
        File file=fileChooser.showOpenDialog(null);
        if(file!=null)
        {
            playlist.add(file);
            mediaPlayer.stop();
            songNumber=playlist.size()-1;
            ListenerSongMusic();
            songName.setText(playlist.get(songNumber).getName());
            mediaPlayer.play();
        }
        else
        {
            mediaPlayer.play();
        }
    }
}