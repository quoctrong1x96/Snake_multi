/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import snake_multi.GameData;
/**
 *
 * @author TranCamTu
 */
public class Game {
	public int[][] serverBoard = new int[GameData.GAME_WIDTH][GameData.GAME_HEIGHT];
	public ArrayList<Player> players = new ArrayList<>();
	private Random rnd = new Random();
	public ArrayList<Integer> foodsX = new ArrayList<>();
	public ArrayList<Integer> foodsY = new ArrayList<>();
	public ArrayList<Integer> solidsX = new ArrayList<>();
	public ArrayList<Integer> solidsY = new ArrayList<>();
	public int highscore = 0;
	
	
	public Game() {
            // Ngẫu nhiên thức ăn
            for (int i = 0; i < GameData.GAME_MAX_FOOD; i++) {
		foodsX.add(rnd.nextInt(GameData.GAME_WIDTH));
		foodsY.add(rnd.nextInt(GameData.GAME_HEIGHT));
            }
	}
        
                public void update() {
            // Update Move
            move();
            
            checkCollisions();
            checkWalls();
            checkFoods();
            updateField();
	}
                
                
	private void move() {
		for (Player p : players) {
			if (p == null) continue;
			p.move();
		}
	}
	
	private void checkCollisions() {
		
		outerLoop:
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (p == null) continue;
			
			// Đâm đầu vào tường
			if (	Collections.max(p.bodysX) >= GameData.GAME_WIDTH ||
					Collections.max(p.bodysY) >= GameData.GAME_HEIGHT ||
					Collections.min(p.bodysX) < 0 ||
					Collections.min(p.bodysY) < 0) {
                                // Bỏ qua player
				players.set(i, null);
				continue;
				
			}
			
			// Đâm đầu vào player khác
			int pX = p.bodysX.get(0);
			int pY = p.bodysY.get(0);
			for (Player q : players) {
				if (q == null) continue;
				
				if (p == q) {
					//Tự đâm vào bản thân
					for (int d = 1; d < q.bodysX.size(); d++) {
						if (q.bodysX.get(d) == pX && q.bodysY.get(d) == pY) {
							//Loại bỏ
							players.set(i, null);
							continue outerLoop;
							
						}
						
					}
				// Đâm vào người khác
				} else if (p != q && q.bodysX.contains(pX) && q.bodysY.contains(pY)) {
                                        //Loại bỏ
					players.set(i, null);
					continue outerLoop;	
				}
			}
		}
	}
	
	private void checkWalls() {
		
		outerLoop:
                //Với mỗi player
		for (int i = 0; i < players.size(); i++) {
                        //Lấy mẫu
			Player p = players.get(i);
			if (p == null) continue;
			
			for (int j = 0; j < solidsX.size(); j++) {
                                // Đầu rắn chạm vào tường, hoặc body rắn
				if (p.bodysX.get(0) == solidsX.get(j) && p.bodysY.get(0) == solidsY.get(j)) {
                                        
					int result = foodsX.indexOf(solidsX.get(j));
					if (result > -1 && foodsY.get(result) == solidsY.get(j)) {
						// Không chết vì đây là food
					} else {

						// Bỏ qua player
						players.set(i, null);
						continue outerLoop;
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private void checkFoods() {
		//Mỗi player
		for (Player p : players) {
			if (p == null) continue;
			//Kiểm tra player có nuốt food không
			for (int i = 0; i < GameData.GAME_MAX_FOOD; i++) {
                            // Nếu phần đầu của rắn trùng với thức ăn
                            if (p.bodysX.get(0) == foodsX.get(i) && p.bodysY.get(0) == foodsY.get(i)) {
                                // Tạo food mới
                                foodsX.set(i, rnd.nextInt(GameData.GAME_WIDTH));
				foodsY.set(i, rnd.nextInt(GameData.GAME_HEIGHT));
                                // Tăng điểm cho player, để player kiểm tra và thêm vào thân
                                p.score++;
                                // Check chế độ đã thay đổi score
				p.updateScore = true;
                            }
                        }
                }
	}
	
	private void updateField() {
		
		serverBoard = new int[GameData.GAME_WIDTH][GameData.GAME_HEIGHT];

		// solids
		for (int i = 0; i < solidsX.size(); i++) {
			
			// safezone
			if (solidsX.get(i) < 3 && solidsY.get(i) < 3) {
				solidsX.remove(i);
				solidsY.remove(i);
				continue;
			}
			
			serverBoard[solidsX.get(i)][solidsY.get(i)] = -1;
		}
		
		// fruits
		for (int i = 0; i < GameData.GAME_MAX_FOOD; i++) {
			serverBoard[foodsX.get(i)][foodsY.get(i)] = 1;
		}
		
		// players
		for (Player p : players) {
			if (p == null) continue;
			
			// segments
			for (int i = 0; i < p.bodysX.size(); i++) {
				serverBoard[p.bodysX.get(i)][p.bodysY.get(i)] = p.playerID;
			}
			
			// head
			serverBoard[p.bodysX.get(0)][p.bodysY.get(0)] = -p.playerID;
		}
	}
}
