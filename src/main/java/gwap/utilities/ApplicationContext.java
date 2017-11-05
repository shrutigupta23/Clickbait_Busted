package gwap.utilities;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import gwap.database.PlayerDataService;
import gwap.gameplay.screens.PlayerScreen;
import gwap.models.dto.PlayerDTO;

/**
 * @author shruti
 * Context class to store application relation global resources
 *
 */
public enum ApplicationContext {

	INSTANCE;

	private HashMap<Object, Object> context = new HashMap<Object, Object>();

	
	
		private ApplicationContext() {
		ArrayList<PlayerDTO> onlinePlayers = new ArrayList<PlayerDTO>();
		context.put("online_players", onlinePlayers);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame remoteFrame = new JFrame();
		remoteFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		int width = ((screenSize.width / 2 ) - 100);
		int height = screenSize.height;
		remoteFrame.setSize(width, height);
		setRemoteFrame(remoteFrame);

		List<String> players = PlayerDataService.getPlayers();
		for (String playerName : players) {
			PlayerDTO player = new PlayerDTO(playerName);
			addOnlinePlayer(player);
		}
		
		ImageIcon personImgIcon = new ImageIcon(Paths.get("src", "main", "resources", "personicon.png").toString());
		context.put("person_icon", personImgIcon);
		
		ImageIcon newsImgIcon = new ImageIcon(Paths.get("src", "main", "resources", "newspaper.png").toString());
		context.put("news_icon", newsImgIcon);
		


	}

	public void putCurrentPlayer(PlayerScreen currentPlayer) {
		context.put("current_player", currentPlayer);
	}

	public PlayerScreen getCurrentPlayer() {
		return (PlayerScreen) context.get("current_player");
	}

	public void setMainFrame(JFrame frame) {
		context.put("main_frame", frame);
	}

	public JFrame getMainFrame() {
		return (JFrame) context.get("main_frame");
	}

	public void addOnlinePlayer(PlayerDTO player) {
		ArrayList<PlayerDTO> players = (ArrayList<PlayerDTO>) context.get("online_players");
		players.add(player);
	}

	public PlayerDTO getOnlinePlayer(String currentPlayer) {
		ArrayList<PlayerDTO> players = (ArrayList<PlayerDTO>) context.get("online_players");
		PlayerDTO onlinePlayer = null;
		for (PlayerDTO player : players) {
			if (!player.isPlaying() && player.getUsername() != currentPlayer) {
				onlinePlayer = player;
				break;
			}
		}

		return onlinePlayer;

	}

	public void setRemoteFrame(JFrame remoteFrame) {
		context.put("remote_frame", remoteFrame);

	}

	public JFrame getRemoteFrame() {
		JFrame frame = (JFrame) context.get("remote_frame");
		return frame;
	}
	
	public ImageIcon getPersonIcon(){
		return (ImageIcon)context.get("person_icon");
	}
	
	public ImageIcon getNewsIcon(){
		return (ImageIcon)context.get("news_icon");
	}
	
	public ImageIcon getNewsIcon(int width , int height){
		ImageIcon newsIcon = (ImageIcon)context.get("news_icon");	
		Image newImage = newsIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_FAST);
		ImageIcon newIcon = new ImageIcon(newImage);
		return newIcon;
	}
	
	
	public void load() {

	}

}