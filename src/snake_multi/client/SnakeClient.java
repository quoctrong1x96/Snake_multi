/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import snake_multi.GameData;
import snake_multi.Resource ;
/**
 *
 * @author TranCamTu
 */
public class SnakeClient  extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;
	
        private final Color bg_color = Color.decode("#efd39c");                       // Màu nền chung cho chương trình (hơi nâu xám)
        private JLabel new_game,quit,about,history;        // Nhãn button
        private Map<Integer,Image> images = new HashMap<>();       // Lưu danh sách image
        private Map<Integer,Icon> icon_images = new HashMap<>();    // Tương tự lưu danh sách icon
        
	public ClientControl handler;
	private JPanel contentPane;
	public Board board;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
                                        
					SnakeClient frame = new SnakeClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SnakeClient() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				handler.stop();
			}
		});
		setTitle("Snake Multiplayer Site");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
                AboutPane about = new AboutPane();
                
		board = new Board();
                contentPane.add(createMenuPane(),BorderLayout.WEST);
		contentPane.add(board, BorderLayout.CENTER);
                contentPane.add(about, BorderLayout.EAST);
		pack();
                
		handler = new ClientControl(this);
		handler.start();
                
        }
        
        /**
     * Khởi tạo Panel riêng chứa các label menu với các icon làm nền cho label
     * Gán nền cho label bằng hình ảnh thể hiện cho button, 
     * Gán màu nền cho panel chứa các label
     * Gán sự kiện cho các label để có thể ấn vào chọn, hoặc houver qua
     * Cho thêm vào Panel Menu
     * @return Menu panel
    */ 
    private JPanel createMenuPane(){      
        //Load images
        loadBoardImages(0);
        loadMenuIcons(0);
        //Khởi tạo Label với từng icon nền tương ứng (ID icon load từ resource)
        new_game = new JLabel(icon_images.get(GameData.NEW_BUTTON));
        about = new JLabel(icon_images.get(GameData.ABOUT_BUTTON));
        history = new JLabel(icon_images.get(GameData.HISTORY_BUTTON));
        quit = new JLabel(icon_images.get(GameData.QUIT_BUTTON));  
        
        // Thêm sự kiện chuột vào từng label
        new_game.addMouseListener(this);
        about.addMouseListener(this);
        history.addMouseListener( this);
        quit.addMouseListener(this);
        
        // Thêm và set màu nền cho menu Pan
        JPanel pane = new JPanel(new GridLayout(4,1));
        pane.add(new_game);        
        pane.add(history);
        pane.add(about);
        pane.add(quit);             
        pane.setBackground(bg_color);
        
        // Thêm Panel JLabel vào Panel menu
        JPanel menu_pane = new JPanel(new BorderLayout());
        menu_pane.setBackground(bg_color);
        menu_pane.add(pane,BorderLayout.SOUTH);
        menu_pane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        return menu_pane;
        
    }
    
    //Load hình ảnh cho bàn cờ và gán ID cho nó
    public void loadBoardImages(int style){
        Resource resource = new Resource();                             // Lưu resource
        String[] resource_keys = {"board","history_board","glow","glow2","history_title"};
        
        if(style==0){
            System.out.print("Đã load ảnh Board style 1\n");
        }
        try{ 
            images.put(GameData.BOARD_IMAGE,ImageIO.read(resource.getResource(resource_keys[0])));
            images.put(GameData.BOARD_IMAGE2,ImageIO.read(resource.getResource(resource_keys[1])));
            images.put(GameData.GLOW,ImageIO.read(resource.getResource(resource_keys[2])));
            images.put(GameData.GLOW2,ImageIO.read(resource.getResource(resource_keys[3])));            
            images.put(GameData.HISTORY_TITLE,ImageIO.read(resource.getResource(resource_keys[4])));
        }catch(IOException ex){
        }        
    }
    
    //Load tất cả icon trong resource và gán ID để sử dụng
    public void loadMenuIcons(int style){
        Resource resource = new Resource();                             // Lưu resource
        String[] resource_keys = {"new_game","new_game_hover","quit","quit_hover","history","history_hover","about","about_hover"};
        
        if(style==0){
            System.out.print("Đã load ảnh MenuIcon style 1\n");
        }
        
        icon_images.put(GameData.NEW_BUTTON,new ImageIcon(resource.getResource(resource_keys[0])));
        icon_images.put(GameData.NEW_BUTTON2,new ImageIcon(resource.getResource(resource_keys[1])));
        icon_images.put(GameData.QUIT_BUTTON,new ImageIcon(resource.getResource(resource_keys[2])));
        icon_images.put(GameData.QUIT_BUTTON2,new ImageIcon(resource.getResource(resource_keys[3])));
        icon_images.put(GameData.HISTORY_BUTTON,new ImageIcon(resource.getResource(resource_keys[4])));
        icon_images.put(GameData.HISTORY_BUTTON2,new ImageIcon(resource.getResource(resource_keys[5])));
        icon_images.put(GameData.ABOUT_BUTTON,new ImageIcon(resource.getResource(resource_keys[6])));
        icon_images.put(GameData.ABOUT_BUTTON2,new ImageIcon(resource.getResource(resource_keys[7])));
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if(source == quit){                         // nếu bám nút thoát, thì thoát
            quit();
        }else if(source == new_game){               // nếu bấm vào label các kiểu thì làm theo
            
        }else if(source == about){                              // Xem về chúng tôi
            
        }else if(source == history){                            // Tắt và hiển thị lịch sử
            
        }
    }    

    // Sự kiện mouse houver, chuột di chuyển qua label thì đổi nền tương ứng
    @Override
    public void mouseEntered(MouseEvent e) {
        Object source = e.getSource();
        if(source == new_game){
            new_game.setIcon(icon_images.get(GameData.NEW_BUTTON2));
        }else if(source == about){
            about.setIcon(icon_images.get(GameData.ABOUT_BUTTON2));
        }else if(source == history){
            history.setIcon(icon_images.get(GameData.HISTORY_BUTTON2));
        }else if(source == quit){
            quit.setIcon(icon_images.get(GameData.QUIT_BUTTON2));
        }
    }

    // Nếu mouse di chuyển khỏi label thì đặt lại hình bình thường
    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();
        if(source == new_game){
            new_game.setIcon(icon_images.get(GameData.NEW_BUTTON));
        }else if(source == about){
            about.setIcon(icon_images.get(GameData.ABOUT_BUTTON));
        }else if(source == history){
            history.setIcon(icon_images.get(GameData.HISTORY_BUTTON));
        }else if(source == quit){
            quit.setIcon(icon_images.get(GameData.QUIT_BUTTON));
        }
    }
    
    // Sự kiện nhấn chuột xuống
    @Override
    public void mousePressed(MouseEvent e) { }

    // Sự kiện chuột thả
    @Override
    public void mouseReleased(MouseEvent e) { }
    //Quit game
    public void quit(){
        int option = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?", 
                    "My Snake Multi v.4", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.YES_OPTION)
            System.exit(0);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}
class AboutPane extends JPanel{
    
    //Create AboutPane Init
    public AboutPane(){
        
        //declare variable
        setLayout(new BorderLayout());
        JPanel northPane = new NorthPane();       
        JPanel centerPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); 
        
        //Set background for JPanel and the space, Center mod
        northPane.setBackground(Color.MAGENTA);
        centerPane.setBackground(Color.decode("#efd39c"));
        c.insets = new Insets(4,4,4,4);
        c.fill = GridBagConstraints.HORIZONTAL;        

        //Varible some text for about, get from Properties file
        String[][] values = new String[][]{
            {"Project","Snake_Multi "+GameData.VERSION},
            {"Category","Game"},
            {"Author",GameData.AUTHOR},
            {"Date created",GameData.DATECREATE}
        };
        
        //Enable text above to JPanel
        for(int i=0; i<values.length; i++){
            JLabel header = new JLabel(values[i][0]+": ");
            header.setFont(new Font(header.getFont().getName(),Font.BOLD,13));
            JLabel data = new JLabel(values[i][1]);
            c.gridx = 0;
            c.gridy = i;
            centerPane.add(header,c);
            c.gridx = 1;
            centerPane.add(data,c);
        }
        centerPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //The end, add this to JPanel parent
        add(northPane,BorderLayout.NORTH);
        add(centerPane,BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }    
    
    //Create JFrame and add info and Enable to UI
    public static void createAndShowUI(){                   
        JFrame f = new JFrame("AboutBox");
        AboutPane ap = new AboutPane();
        f.getContentPane().add(ap);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    //Create Jpanel for title Aboutbox, 
    class NorthPane extends JPanel{
        NorthPane(){          
            JLabel label = new JLabel("About Snake Multi",JLabel.LEFT);
            label.setFont(new Font(label.getFont().getName(),Font.BOLD,15));
            label.setForeground(Color.decode("#9900AF"));
            add(label);
        }
        //Draw the Line to space title JPanel and information Game
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = this.getWidth()- 5;
            int height = this.getHeight() - 1;
            g.setColor(Color.decode("#9900FF"));
            g.drawLine(0, height, width, height);
        }
    }
}
