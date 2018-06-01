/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.server;
import java.util.ArrayList;
import java.util.Random;
import snake_multi.GameData;
/**
 *
 * @author TranCamTu
 */
public class Player {
	public int playerID;
	public int direction = 0;
	public int score = 0;
	public boolean updateScore = false;
	public boolean freeze = false;
	public int kamikaze = -1;
	public int steps = 1;
	public int step = 0;
	public String color;
	public ArrayList<Integer> bodysX = new ArrayList<>();
	public ArrayList<Integer> bodysY = new ArrayList<>();
	
	
	public Player(int id, String color) {
		//Khởi tạo với ID và Color
		this.playerID = id;
		this.color = color;
		
                //Tạo vị trí ngẫu nhiên của player
                Random rd = new Random();
		bodysX.add(0);
		bodysY.add(0);
		
	}
	
	public void move() {
		
		// Nếu server dừng
		if (freeze) return;
		
		step++;
		if (step >= steps) {
			step = 0;
		} else {
			return;
		}
		
		if (kamikaze > -1) direction = kamikaze;
		
		
		// Lấy vị trí của body cuối, khi nhận thức ăn sẽ thêm vào đây
		int lastX = bodysX.get(bodysX.size()-1);
		int lastY = bodysY.get(bodysY.size()-1);
		
		// Di chuyển phần thân lên phía sau
		for (int i = bodysX.size() - 1; i > 0; i--) {
			
			bodysX.set(i, bodysX.get(i-1));
			bodysY.set(i, bodysY.get(i-1));
		}
		// Thêm phần thân nếu điểm số lớn hơn kích thước của body
		if (score >= bodysX.size()) {
			bodysX.add(lastX);
			bodysY.add(lastY);
		}	
                // Di chuyển phần đầu, theo định hướng trước
		switch (direction) {
		
		// Sang phải
		case 0:
			bodysX.set(0, bodysX.get(0) + 1);
			break;
		// Sang trái
		case 2:
			bodysX.set(0, bodysX.get(0) - 1);
			break;
		// Đi lên
		case 1:
			bodysY.set(0, bodysY.get(0) - 1);
			break;

		// Đi xuống
		case 3:
			bodysY.set(0, bodysY.get(0) + 1);
			break;
		
		}
	}
	
}
