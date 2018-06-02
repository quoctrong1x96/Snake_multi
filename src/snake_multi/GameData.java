/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author TranCamTu
 */
public class GameData {
/**
 * Đặt tên để tiện quy đổi dê dàng thao tác mà chỉ cần nhớ tên
 */
    
    // Hằng Snake
    public final static int SNAKE_MAXLENGTH_SNAK = 200;
    public final static int SNAKE_INITIALLENGHT = 3;
    public final static Color SNAKE_HEAD_COLOR_P0 = Color.getHSBColor(0.81f, 1, 1f);
    public final static Color SNAKE_HEAD_COLOR_P1 = Color.getHSBColor(0.41f, 1, 1f);
    public final static Color SNAKE_HEAD_COLOR_P2 = Color.getHSBColor(0.61f, 1, 1f);
    public final static Color SNAKE_HEAD_COLOR_P3 = Color.getHSBColor(0.11f, 1, 1f);
    public final static Color SNAKE_BODY_COLOR = Color.pink;
    public final static int SNAKE_BODY_SIZE = 10;
    
    // Hằng Board game
    public final static int BOARD_WIDTH = 500;
    public final static int BOARD_HEIGHT = 500;
    public final static Color BOARD_COLOR = Color.white;
    public final static int WINDOW_HEIGHT = 700;
    public final static int WINDOW_WIDTH = 700;
    public final static Color WINDOW_COLOR = Color.white;
    
    //Hằng Food
    public final static Color FOOD_COLOR = Color.red;
    public final static Color FOOD_BONUS_COLOR = Color.blue;
    public static int isColorAsPlayer = 1;
    
    //Hằng ORDER
    public final static String SCORE_LABEL = "Điểm: ";
    public final static int BONUS_FOOD_SCORE = 100;
    public final static int FOOD_SCORE = 10;
    public final static Color WALL_COLOR = Color.black;
    public final static int WALL_WIDTH = 1;
    
    //Hằng game
    public static final int GAME_WIDTH = 30;
    public static final int GAME_HEIGHT = 30;
    public static final int GAME_MAX_FOOD = 4;
    public static final int GAME_SPEED = 100;
    
    //Hằng Board
    public static final int SNAKE_SIZE = 20;
    public static final Color BRUSH_DEFAUTL_COLOR = Color.black;
    public static final Font FONT_STATUS = new Font("Arial", Font.BOLD, 16);
 
        public final static int EMPTY = 55;                 // Ô cờ trống
    public final static int ILLEGAL = 77;               // Ô không thuộc trong bàn cờ
    public final static int WAITING = 3;                // Waiting other player
    public final static int HUMAN = 1;                  // Người chơi
    public final static int COMPUTER = 0;               // Máy chơi
    public final static int HUMAN2 = 2;                 // Người chơi qua mạng
    public final static int BOARD_IMAGE = 1000;         // Hình ảnh bàn cờ
    public final static int GLOW = 1001;                // Điểm sáng điểm bắt đầu
    public final static int GLOW2 = 1002;               // Điểm sáng điểm đi đến
    public final static int PREPARE_ANIMATION = 1003;   //
    public final static int ANIMATING = 1004;           // Nhập thành
    public final static int HUMAN_MOVE = 1005;          // Lượt di chuyển của HUmam
    public final static int COMPUTER_MOVE = 1006;       // Lượt di chuyển của Computer
    public final static int HUMAN2_MOVE = 1007;
    public final static int GAME_ENDED = 1008;          // Game đã kết thúc
    public final static int DRAW = 0;                   // Game đang tiếp tục
    public final static int CHECKMATE = 1;              // Đang bị check
    public final static int NEW_BUTTON = 10078;         // 
    public final static int NEW_BUTTON2 = 10079;        //
    public final static int QUIT_BUTTON = 10080;        // Nút thoát 1
    public final static int QUIT_BUTTON2 = 10081;       // Nút thoát 2
    public final static int ABOUT_BUTTON = 10082;       // Nút about
    public final static int ABOUT_BUTTON2 = 10083;      //
    public final static int HISTORY_BUTTON = 10084;     //
    public final static int HISTORY_BUTTON2 = 10085;    //
    public final static int FIRST_BUTTON = 10086;       //
    public final static int FIRST_BUTTON2 = 10087;      //
    public final static int LAST_BUTTON = 10088;        //
    public final static int LAST_BUTTON2 = 10089;       //
    public final static int PREV_BUTTON = 10090;        //
    public final static int PREV_BUTTON2 = 10091;       //
    public final static int NEXT_BUTTON = 10092;        //
    public final static int NEXT_BUTTON2 = 10093;       //
    public final static int PROMOTING = 10086;          //  Phong chốt
    public final static int BOARD_IMAGE2 = 10087;       //  Bàn cờ nhỏ history
    public final static int HISTORY_TITLE = 10094;      //  
    public final static int MYCHESSMATE = 10095;        //  
    public final static int LOADPIECE = 20001;          // Loại hình load ảnh cờ
    public final static int LOADBOARD = 20002;          // Loại hình load ảnh liên quan bàn cờ
    public final static int LOADICON = 20003;           // Loại hình load ảnh hỗ trợ nút bấm
    
    public final static String VERSION = new Resource().getResourceString("version");
    public final static String AUTHOR = new Resource().getResourceString("author");
    public final static String DATECREATE = new Resource().getResourceString("datecreate");
}

