/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Random;
import javax.swing.BorderFactory;

import javax.swing.JPanel;
import snake_multi.GameData;
/**
 *
 * @author TranCamTu
 */
public class Board extends JPanel {
    private static final long serialVersionUID = 1L;
	
        //Mảng phần tử board game
	public int[][] mainBoard;
	public int clientID;
        public Color clientColor;
	public String clientStatus;
	public HashMap<Integer, Color> otherClientColor;
	
	
	public Board() {
		//Khởi tạo class với size và background Color
		setBackground(GameData.WINDOW_COLOR);
		setPreferredSize(new Dimension(GameData.GAME_WIDTH * GameData.SNAKE_SIZE,
                                     GameData.GAME_HEIGHT*GameData.SNAKE_SIZE));
                
                setBorder(BorderFactory.createLineBorder(GameData.WALL_COLOR,GameData.WALL_WIDTH));
                
                //Khởi tạo main board mảng
                mainBoard = new int[GameData.GAME_WIDTH][GameData.GAME_HEIGHT];
                /**
                 * -1: Lề
                 *  0: Board rỗng
                 *  1: Food
                 *  clientID: Mình rắn
                 * -clientID: Đầu rắn
                 * >1: Mình rắn khác
                 * nhỏ hơn -1: Đầu rắn khác
                 */
                
                //Khởi tạo random
                Random rnd = new Random();
		
                //Tạo ID động
                clientID = rnd.nextInt(8999) + 1000;
                
                //Message client
                clientStatus = "";
                
		//Tạo màu ngẫu nhiên cho mỗi client
                switch(GameData.isColorAsPlayer){
                    case 1:
                        clientColor = GameData.SNAKE_HEAD_COLOR_P0;
                        break;
                    case 2:
                        clientColor = GameData.SNAKE_HEAD_COLOR_P1;
                        break;
                    case 3: 
                        clientColor = GameData.SNAKE_HEAD_COLOR_P2;
                        break;
                    case 4:
                        clientColor = GameData.SNAKE_HEAD_COLOR_P3;
                        break;
                }
		
		
                //Khởi tạo otherClientColor
                otherClientColor = new HashMap<>();
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Chạy toàn mảng, set màu, vẽ hình chữ nhật tại địa chỉ mảng set
		for (int x = 0; x < GameData.GAME_WIDTH; x++) {
			for (int y = 0; y < GameData.GAME_HEIGHT; y++) {
				//Tại food
				if (mainBoard[x][y] == 1) {				
					g.setColor(GameData.FOOD_COLOR);
                                //Tại ngoài lề
				} else if (mainBoard[x][y] == -1) {		
					g.setColor(GameData.WINDOW_COLOR);
				//Tại body rắn	
				} else if (mainBoard[x][y] == clientID) {		
					g.setColor(clientColor);
				//Tại đầu rắn (Màu đậm hơn body rắn)	
				} else if (mainBoard[x][y] == -clientID) {	
					g.setColor(clientColor.brighter());
                                //Tại body rắn khác, Lấy màu khác từ server trả về
				} else if (mainBoard[x][y] > 1) {		
					g.setColor(otherClientColor.get(mainBoard[x][y]));
				//Tại đầu rắn khác, Lấy màu khác từ server trả về	(Màu đậm hơn)
				} else if (mainBoard[x][y] < -1) {	
                                        g.setColor(otherClientColor.get(-mainBoard[x][y]).brighter());
				//Tại những chỗ không có giá trị	
				} else {							
					g.setColor(GameData.BOARD_COLOR);
				}
				
                                //Vẽ hình chữ nhật với màu đã chọn, size của rắn
				g.fillRect(x * GameData.SNAKE_SIZE, 
                                           y * GameData.SNAKE_SIZE, 
                                           GameData.SNAKE_SIZE, 
                                           GameData.SNAKE_SIZE);
				
			}
		}
		//Xoá màu của graphic
		g.setColor(GameData.BRUSH_DEFAUTL_COLOR);
		g.setFont(GameData.FONT_STATUS);
                //Vẽ text status lên client tại cuối màn hình
		g.drawString(clientStatus, 10, getHeight() - 10);
		
	}
}
