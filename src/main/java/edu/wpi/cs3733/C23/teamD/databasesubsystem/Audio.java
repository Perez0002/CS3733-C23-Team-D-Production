package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import javax.sound.sampled.*;
import java.io.IOException;

public class Audio implements LineListener {
    private AudioInputStream audioStream;

    @Override
    public void update(LineEvent event) {

    }
    public void createAudioStream(String audioFilePath){
        try {
            this.audioStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream(audioFilePath));
        }
        catch(IOException | UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }
    public void playtrack(){

    }

}
