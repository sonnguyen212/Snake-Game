package snake.model;


public class Event {
	
	public enum Type {
		MOVE,
		EAT,
		LOSE,
		WIN,
		TIE,
		START,
		PAUSE,
		RESUME,
		SYNC
	}
	
	private final Type type;
	private final Player player;
	
	public Event(Type type) {
		this.type = type;
		this.player = Player.NONE;
	}
	
	public Event(Type type, Player player) {
		this.type = type;
		this.player = player;
	}
	
	public Type getType() {
		return type;
	}
	
	public Player getPlayer() {
		return player;
	}		
	
}

