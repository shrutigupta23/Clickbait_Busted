package gwap.login.support;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gwap.exceptions.TaskInterruptedException;
import gwap.gameplay.screens.GameStartScreen;
import gwap.models.dto.PlayerDTO;
import gwap.utilities.ApplicationContext;

/**
 * @author shruti
 * Support class shared between Guest and Sign In screen
 * that starts a new game
 */
public class GameStartListener implements ActionListener {
	
	private Window window;
	private String username;
	
	public GameStartListener(Window window, String username){
		this.window = window;
		this.username = username;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PlayerDTO player = new PlayerDTO(username);
		ApplicationContext.INSTANCE.addOnlinePlayer(player);
		window.setVisible(false);
		window.dispose();
		try {
			new GameStartScreen(player).launch();
		} catch (InterruptedException e1) {
			throw new TaskInterruptedException(e1);
		}
		
	}
	
	
}
