package view;

import java.nio.file.Paths;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public abstract class Music 
{
	public static void playMusic(String path)
	{
		Media media = new Media(Paths.get(path).toUri().toString());
		AudioClip clip = new AudioClip(media.getSource());
		clip.setCycleCount(MediaPlayer.INDEFINITE);
		clip.play();
	}
	
	public static void stopMusic(String path)
	{
		Media media = new Media(Paths.get(path).toUri().toString());
		AudioClip clip = new AudioClip(media.getSource());
		clip.stop();
	}
}
