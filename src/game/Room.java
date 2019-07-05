package game;


import java.util.ArrayList;

import dao.Account;



public class Room {

	private Account admin;
	private int curPlayers;
	private int Rounds;
	private int roundDuration;
	private int MaxPlayer;
	private String roomId;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	
	public Room(Account admin, int Rounds, int roundDuration, int MaxPlayer) {
		this.admin  = admin;
		this.Rounds = Rounds;
		this.roundDuration = roundDuration;
		this.MaxPlayer = MaxPlayer;
		curPlayers = 1;
		players.add(new Player(admin));
	}
	
	public String toString() {
		return "Players: " + players.toString() + " Rounds: " + Rounds + " Time: " + roundDuration ; 
	}
	
	public boolean addPlayer(Player newPlayer) {
		if(curPlayers < MaxPlayer) {
			if(players.contains(newPlayer))return false;
			curPlayers++;
			newPlayer.setRoom();
			players.add(newPlayer);
			
			return true;
		}
			//dastartvas vinc elodeba imattanac gamochndes.. da soketi gvinda albat iq
		
		return false;	
	}
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	
	public Account getAdmin() {
		return this.admin;
	}
	public int getRounds() {
		return this.Rounds;
	}
	
	public int getTime() {
		return this.roundDuration;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
