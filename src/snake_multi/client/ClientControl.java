/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.client;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import javax.swing.JOptionPane;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import snake_multi.Request;
import snake_multi.Response;
import snake_multi.GameData;

/**
 *
 * @author TranCamTu
 */
public class ClientControl implements KeyListener {	
	private SnakeClient snakeClient;
        private CodePane codePane;
        private boolean runr, runl, runu, rund;
	Client client = new Client(8192, 8192);
	public int id = 0;
	public int score = 0;
	public int highscore = 0;
	public boolean ingame = false;
	

	public ClientControl(SnakeClient snakeClient) {
		this.snakeClient = snakeClient;
                runr = true;
                runl = true;
                runu = true;
                rund = true;
                codePane = new CodePane(this);
                              
	}
	
	public void start() {
		
		client.start();
		try {
                        //Request và response hỗ trợ bởi kryo
			Kryo kryo = client.getKryo();
			kryo.register(Request.class);
			kryo.register(Response.class);
			
			InetAddress address = client.discoverHost(54001, 5000);
			client.connect(5000, address, 54000, 54001);
			
			client.addListener(new Listener() {
				public void received(Connection connection, Object object) {
					if (object instanceof Response) {
						Response response = (Response)object;
						handleResponse(response.content, connection);
					}
				}

				@Override
				public void disconnected(Connection connection) {
                                    //Kết nối lại, đang mở rộng
				}
			});
			
			snakeClient.addKeyListener(this);
			
			request("getHighscore");
			request("getColors");
			
		} catch (Exception e) {
			if (e.toString().contains("host cannot be null.")) {
				
				int result = JOptionPane.showOptionDialog(null, "No server found! Start server first, then click 'Retry'.", "Error", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, 
						new String[]{"Retry", "Cancel"}, "Retry");
				
				if (result == 0) {
					start();
				} else {
					System.exit(1);
				}
				
			}
			
		}
		
	}
	
	public void stop() {
		client.close();
		client.stop();
	}
	
	private void handleResponse(String content, Connection connection) {
                // Set lại ID và reset điểm và Lấy highscore trên server
		if (content.startsWith("setID")) {
			
			id = Integer.valueOf(content.substring(6));
			snakeClient.board.clientID = id;
			score = 0;
			snakeClient.board.clientStatus = "Score: " + score + " | Highscore: " + highscore;
			ingame = true;
			
		} else if (content.startsWith("update")) {
			
			String raw = content.substring(7);
			String tempY[] = raw.split(";");
			int field[][] = new int[GameData.GAME_WIDTH][GameData.GAME_HEIGHT];
			
			for (int y = 0; y < GameData.GAME_HEIGHT; y++) {
				String tempX[] = tempY[y].split(":");
				for (int x = 0; x < GameData.GAME_WIDTH; x++) {
					field[x][y] = Integer.valueOf(tempX[x]);
				}
			}
			
			// 
			if (!ingame) {
				field[0][0] = -9999;
			}
			
			snakeClient.board.mainBoard = field;
			snakeClient.board.repaint();
			
		} else if (content.startsWith("score")) {
			//Update score cho client
			String temp[] = content.split(";");
			if (Integer.valueOf(temp[1]) == id) {
				score = Integer.valueOf(temp[2]);
				snakeClient.board.clientStatus = "Score: " + score + " | Highscore: " + highscore;
				snakeClient.board.repaint();
			}
			
		} else if (content.startsWith("highscore")) {
			//Update lại highcore cho client
			highscore = Integer.valueOf(content.substring(10));
			if (ingame) {
				snakeClient.board.clientStatus = "Score: " + score + " | Highscore: " + highscore;
			} else {
				snakeClient.board.clientStatus = "Press SPACE to play. | Highscore: " + highscore;
			}
			snakeClient.board.repaint();
			
		} else if (content.startsWith("dead")) {
			//Cập nhật lại gameover cho client
			String temp[] = content.split(";");
			if (Integer.valueOf(temp[1]) == id) {
				snakeClient.board.clientStatus = "GAME OVER! Press SPACE to replay. | Highscore: " + highscore;
				snakeClient.board.clientID = 9999;
				snakeClient.board.repaint();
				ingame = false;
                                runr = true;
                                runl = true;
                                runu = true;
                                rund = true;
			}
			
		} else if(content.startsWith("wrongcode")){
                    client.stop();
                }else if (content.startsWith("ban")) {
			
			snakeClient.board.clientStatus = content.substring(4);
			snakeClient.board.repaint();
			ingame = false;
			
		} else if (content.startsWith("colors")) {
                        //Get color
			for (String s : content.substring(7).split(";")) {
				String temp[] = s.split(":");
				snakeClient.board.otherClientColor.put(Integer.valueOf(temp[0]), Color.decode(temp[1]));
			}
				
		}else if(content.startsWith("gameStart")){
                    if (!ingame) {
                        request("getID;#" + Integer.toHexString(snakeClient.board.clientColor.getRGB()).substring(2));
                    }
                }
		
	}
	
	public void request(String content) {
		Request request = new Request();
		request.content = content;
		client.sendTCP(request);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		int direction = -1;
		
		if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)&&(runr == true)) {
			direction = 0;
			runr = false;
                        runl = false;
                        runu = true;
                        rund = true;
		} else if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W)&&(runu== true)) {
			direction = 1;
                        
                        runr = true;
                        runl = true;
                        runu = false;
                        rund = false;
			
		} else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)&&(runl == true)) {
			direction = 2;
                        runr = false;
                        runl = false;
                        runu = true;
                        rund = true;
			
		} else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)&&(rund == true)) {
			direction = 3;
                        runr = true;
                        runl = true;
                        runu = false;
                        rund = false;
			
		}else if (key == KeyEvent.VK_SPACE && !ingame) {
			codePane.setVisible(true);
			// wanna play
                        request("gameStart");
			
			
		}
		if (direction != -1 && ingame) {
                        //Request định hướng step tiếp theo
			request("direction;" + String.valueOf(id) + ";" + String.valueOf(direction));
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}
