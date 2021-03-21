package amata1219.mamiyer.task;

import org.bukkit.entity.Player;

public class KickAFKerTask implements Runnable {

    private final Player player;
    private int elapsedMinutes;

    public KickAFKerTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {

    }

    /*
    	private final MamiyaAssist plugin = MamiyaAssist.plugin();

	@Override
	public void run() {
		elapsedMinutes++;

		if(elapsedMinutes >= afkedTimeRequiredForKicks()) player.kickPlayer(kickMessage());
	}

	private int afkedTimeRequiredForKicks(){
		return plugin.config().getInt("Kicking AFKer.AFKed time required for kicks");
	}

	private String kickMessage(){
		return plugin.config().getString("Kicking AFKer.Message.When the plugin kicked AFKer");
	}
     */

}
