/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.Timer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import snake_multi.GameData;

import snake_multi.Request;
import snake_multi.Response;
/**
 *
 * @author TranCamTu
 */
public class ServerControl implements ActionListener {
	
	private SnakeServer snakeServer;
	private Server server;
	private Game game;
	private ArrayList<Integer> deadIds = new ArrayList<>();
	private HashMap<Integer, Integer> clients = new HashMap<>();
	private ArrayList<String> banList = new ArrayList<>();
	public Timer timer;
	

	public ServerControl(SnakeServer snakeServer) {
		this.snakeServer = snakeServer;
	}
	
	public void start() {
		//Start at port 8192
		server = new Server(8192, 8192);
		server.start();
		try {
			
			server.bind(54000, 54001);
			snakeServer.buttonStart.setEnabled(false);
			snakeServer.buttonStop.setEnabled(true);
			serverLog("Server started");
			
			Kryo kryo = server.getKryo();
                        //Đăng ký request và response
			kryo.register(Request.class);
			kryo.register(Response.class);
			//Lắng nghe client
			server.addListener(new Listener() {
				public void received(Connection connection, Object object) {
					if (object instanceof Request) {
						Request request = (Request)object;
						handleRequest(request.content, connection);
					}
				}
				@Override
				public void connected(Connection connection) {
					if (banList.contains(connection.getRemoteAddressTCP().getAddress().toString())) {
						serverLog("Banned IP " + connection.getRemoteAddressTCP().getAddress() + " tried to connect");
						respond("ban;You were banned!", connection);
						connection.close();
					} else {
						serverLog("Client " + connection.getID() + " (" + connection.getRemoteAddressTCP().getAddress() + ") connected");
					}
				}
				@Override
				public void disconnected(Connection connection) {
					serverLog("Client " + connection.getID() + " disconnected");
					
					// remove active players
					if (clients.containsKey(connection.getID())) {
						int playerID = clients.get(connection.getID());
						game.players.set(playerID - 2, null);
						serverLog("Player " + playerID + " removed");
						deadIds.add(playerID);
						clients.remove(connection.getID());
                                                GameData.isColorAsPlayer -= 1;
					}
					
				}
			});
			
			game = new Game();
			timer = new Timer(GameData.GAME_SPEED, this);
			timer.start();
			serverLog("Game created");
			
		} catch (Exception e) {
			serverLog(e.toString());
		}
		
	}
	
	public void stop() {
		
		server.stop();
		timer.stop();
		snakeServer.buttonStart.setEnabled(true);
		snakeServer.buttonStop.setEnabled(false);
		serverLog("Server stopped");

	}
	
	private void serverLog(String message) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        snakeServer.textLog.setText(snakeServer.textLog.getText() + sdf.format(new Date()) + " " + message + "\n");
        
	}
	
	private void handleRequest(String content, Connection connection) {
		/** --- COMMANDS ---
		 * 
		 * getID;[color]
		 * setID;[id]
		 * 
		 * direction;[id];[direction]
		 * 
		 * update;[field]
		 * score;[id];[score]
		 * dead;[id]
		 * highscore;[highscore]
		 * colors;[id]:[color];[id2]...
		 * 
		 * getHighscore
		 * getColors
		 * 
		 * dead;[id]
		 * 
		 */
		
		if (content.startsWith("getID")) {
			
			// begin with 2 ( -> 0 nothing 1 fruit -1 border)
			
			Player newPlayer = new Player(game.players.size() + 2, content.substring(6));
                        
                        if (game.players.size() == 4) {
                            serverLog("Another client connects. Room full!");
                        }
                        // Thêm player mới
                        game.players.add(newPlayer);
                        GameData.isColorAsPlayer += 1;
                        respond("setID;" + newPlayer.playerID, connection);
                        
			clients.put(connection.getID(), newPlayer.playerID);
			
			// Thêm màu từ client
			Response response = new Response();
			response.content = "colors;" + newPlayer.playerID + ":" + newPlayer.color;
			server.sendToAllTCP(response);
			
			serverLog("Player " + newPlayer.playerID + " joined the game (Client " + connection.getID() + ")");
			
		} else if(content.startsWith("join")){
                    String code = content.substring(5);
                    if (snakeServer.Code == null ? code == null : snakeServer.Code.equals(code)){
                        respond("wrongcode",connection);
                    }
                }else if (content.startsWith("direction")) {
			
			String temp[] = content.split(";");
			Player player = game.players.get(Integer.valueOf(temp[1]) - 2);
			if (player == null) return;
			player.direction = Integer.valueOf(temp[2]);
			
		} else if (content.startsWith("getHighscore")) {
			
			respond("highscore;" + game.highscore, connection);
			
		} else if (content.startsWith("getColors")) {
			
			String colors = "";
			for (Player p : game.players) {
				if (p == null) continue;
				colors += p.playerID + ":" + p.color + ";";
			}
			if (colors.length() == 0) return;
			colors = colors.substring(0, colors.length() - 1);
			respond("colors;" + colors, connection);
			
		} else if (content.startsWith("gameStart")){
                        respond("gameStart",connection);
                }
		
	}
	
	private void respond(String content, Connection connection) {
		Response response = new Response();
		response.content = content;
                if (response.content.contains("gameStart")) {
                    server.sendToAllTCP(response);
                }else{
                    connection.sendTCP(response);
                }
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// update
		game.update();
		
		// send to clients
		String raw = "";
		for (int y = 0; y < GameData.GAME_HEIGHT; y++) {
			for (int x = 0; x < GameData.GAME_WIDTH; x++) {
				raw += String.valueOf(game.serverBoard[x][y]) + ":";
			}
			raw = raw.substring(0, raw.length() - 1);
			raw += ";";
		}
		raw = raw.substring(0, raw.length() - 1);
		
		
		Response response = new Response();
		response.content = "update;" + raw;
		server.sendToAllTCP(response);
		
		
		// scores and dead updates
		for (int i = 0; i < game.players.size(); i++) {
			Player p = game.players.get(i);
			
			if (p != null && p.updateScore) {
				response = new Response();
				response.content = "score;" + p.playerID + ";" + p.score;
				server.sendToAllTCP(response);
				p.updateScore = false;
				
				if (p.score > game.highscore) {
					game.highscore = p.score;
					response = new Response();
					response.content = "highscore;" + game.highscore;
					server.sendToAllTCP(response);
					serverLog("New highscore by player " + p.playerID + ": " + game.highscore);
				}
				
			} else if (p == null && !deadIds.contains(i + 2)) {
				deadIds.add(i + 2);
				response = new Response();
				response.content = "dead;" + (i + 2);
				server.sendToAllTCP(response);
				serverLog("Player " + (i + 2) + " died");
			}
			
		}
		
	}

}

