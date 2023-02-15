package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import java.io.IOException;
import javax.sound.sampled.*;

public class Audio implements LineListener {
  private AudioInputStream audioStream;

  @Override
  public void update(LineEvent event) {}

  public void createAudioStream(String audioFilePath) {
    try {
      this.audioStream =
          AudioSystem.getAudioInputStream(
              getClass().getClassLoader().getResourceAsStream(audioFilePath));
    } catch (IOException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
  }

  public void playtrack() {}
}
