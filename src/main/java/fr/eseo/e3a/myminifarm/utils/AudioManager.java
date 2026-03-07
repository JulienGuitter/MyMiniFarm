package fr.eseo.e3a.myminifarm.utils;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.controller.ConfigController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The AudioManager class is responsible for managing audio playback in the application.
 * It handles loading and playing music tracks, as well as adjusting the volume.
 */
public class AudioManager {
    private static AudioManager instance;
    private static final String MUSIC_PATH = "audio/musics/";

    private static MediaPlayer mediaPlayer;

    private List<String> homeTracks;
    private List<String> musicTracks;

    /**
     * Private constructor for the AudioManager singleton.
     * It initializes the list of music tracks by checking if the files exist.
     */
    private AudioManager() {
        homeTracks = checkFile(List.of(
                "Crepuscule_sur_la_Colline.mp3"
        ), MUSIC_PATH);

        musicTracks = checkFile(List.of(
                "Balade_au_Village.mp3",
                "Matin_Brumeux.mp3",
                "Pluie_Douce_sur_les_Champs.mp3",
                "Tranquille_au_Potager.mp3",
                "Tranquille_au_Potager_2.mp3"
        ), MUSIC_PATH);

        System.out.println("Home : " + homeTracks);
        System.out.println("Musics : " + musicTracks);
    }

    /**
     * Get the singleton instance of the AudioManager.
     *
     * @return the instance of AudioManager
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Check if the files exist in the specified path.
     * It prevents the application from crashing if the files are not found.
     *
     * @param fileList the list of file names to check
     * @param path     the path to check
     * @return a list of existing file names
     */
    private List<String> checkFile(List<String> fileList, String path){
        List<String> res = new ArrayList<>();

        for(String line : fileList){
            try{
                Objects.requireNonNull(MyMiniFarm.class.getResource(path + line));
                res.add(line);
            }catch(NullPointerException e){
                // Noting to do
            }
        }

        return res;
    }

    /**
     * Play a random music track from the list of music tracks.
     * If no music tracks are available, it prints a message to the console.
     */
    public void playHomeMusic() {
        if(homeTracks.size() <= 0){
            System.out.println("No music to play !");
            return;
        }

        String track = homeTracks.get((int) (Math.random() * homeTracks.size()));

        Media media = new Media(Objects.requireNonNull(MyMiniFarm.class.getResource(MUSIC_PATH + track)).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        setVolume(ConfigController.getInstance().getConfig().getVolume());
        mediaPlayer.play();
    }

    /**
     * Used to change the current volume of the MediaPlayer.
     *
     * @param volume the volume level (0-100)
     */
    public void setVolume(int volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume / 100.0);
        }
    }
}
