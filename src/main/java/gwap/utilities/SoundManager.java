package gwap.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * @author shruti
 * This is the central sound manager responsible for playing sounds in the game.
 */
public enum SoundManager {

	INSTANCE;
	private Clip popupSound;
	private Clip correctSound;
	private Clip incorrectSound;
	private Clip bonusSound;
	private Clip duringGameSound;
	private Clip secondCounterSound;
	private Clip backgroundSound;
	private ExecutorService engine = Executors.newSingleThreadExecutor();

	private SoundManager() {

		loadPopupSound();
		loadOnCorrectSound();
		loadOnIncorrectSound();
		loadOnBonusSound();
		loadduringGameSound();
		loadSecondCounterSound();
		loadBackgroundSound();
	}

	private void loadBackgroundSound() {
		AudioInputStream audioInputStream;
		try {
			Path path = Paths.get("src", "main", "resources", "background.wav");
			File file = path.toFile();
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			backgroundSound = AudioSystem.getClip();
			backgroundSound.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}	}

	private void loadSecondCounterSound() {
		AudioInputStream audioInputStream;
		try {
			Path path = Paths.get("src", "main", "resources", "secondticktock.wav");
			File file = path.toFile();
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			secondCounterSound = AudioSystem.getClip();
			secondCounterSound.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void loadduringGameSound() {
		AudioInputStream audioInputStream;
		try {
			Path path = Paths.get("src", "main", "resources", "ticktock.wav");
			File file = path.toFile();
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			duringGameSound = AudioSystem.getClip();
			duringGameSound.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void loadPopupSound() {
		AudioInputStream audioInputStream;
		try {
			Path path = Paths.get("src", "main", "resources", "game_ping.wav");
			File file = path.toFile();
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			popupSound = AudioSystem.getClip();
			popupSound.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	private void loadOnCorrectSound() {
		AudioInputStream audioInputStream;
		try {
			Path path = Paths.get("src", "main", "resources", "correct_answer.wav");
			File file = path.toFile();
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			correctSound = AudioSystem.getClip();
			correctSound.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void loadOnIncorrectSound() {
		AudioInputStream audioInputStream;
		try {
			Path path = Paths.get("src", "main", "resources", "wrong_answer.wav");
			File file = path.toFile();
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			incorrectSound = AudioSystem.getClip();
			incorrectSound.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void loadOnBonusSound() {
		AudioInputStream audioInputStream;
		try {
			Path path = Paths.get("src", "main", "resources", "bonus_answer.wav");
			File file = path.toFile();
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			bonusSound = AudioSystem.getClip();
			bonusSound.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void playPopupSound() {
		popupSound.setFramePosition(0);
		popupSound.start();
	}

	public void playCorrectSound() {
		engine.submit(new SoundPlayer(correctSound));

	}

	public void playBackgroundSound() {
		engine.submit(new SoundPlayer(backgroundSound));

	}
	public void playIncorrectSound() {
		incorrectSound.setFramePosition(0);
		incorrectSound.start();

	}

	public void playBonusSound() {
		bonusSound.setFramePosition(0);
		bonusSound.start();

	}

	public void playDuringGameSound() {
		engine.submit(new SoundPlayer(duringGameSound));

	}

	public void stopDuringGameSound() {
		duringGameSound.stop();
	}

	
	public void stopSecondCounterSound(){
		secondCounterSound.stop();
	}
	public void playSecondCounterSound() {
		engine.submit(new SoundPlayer(secondCounterSound));

	}

	public void load() {

	}

	public class SoundPlayer implements Callable {
		private Clip sound;

		public SoundPlayer(Clip sound) {
			this.sound = sound;
		}

		@Override
		public Object call() throws Exception {
			sound.setFramePosition(0);
			sound.start();
			return sound;
		}
	}

}
