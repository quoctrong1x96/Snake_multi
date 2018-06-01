package snakeboss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
/**
 *
 * @author TranCamTu
 */
public final class CreateServerPanel extends JFrame implements ActionListener{
     JButton ok;                                    // Button Ok
     JButton cancel;                                // Button Cancel
     final static int MAXCODE = 9999;               // Max của code là 9999 và min là 1000
     final static int MINCODE = 1000;               

     
    //Contruction với table cha
    public CreateServerPanel(){
        super("Options Server");                                //Khởi tạo JFram với title là Options
        JPanel mainPane = new JPanel(new BorderLayout());       // Tạo Jpanel chứa 3 Jpanel là level, color and button   
        mainPane.add(createLevelPane(),BorderLayout.NORTH);
        mainPane.add(createCodePane(),BorderLayout.CENTER);
        mainPane.add(createButtonPane(),BorderLayout.SOUTH);
        mainPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setContentPane(mainPane);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);             // Chỉ tắt JFrame hiện tại
        ok.addActionListener(this);
        cancel.addActionListener(this);
    }
    //Khởi tạo Level JPanel
    private JPanel createLevelPane(){
        JSlider levelSlider;                                    // Slide số người trong phòng chơi
        levelSlider = new JSlider(JSlider.HORIZONTAL,1,4,3);    //Tạo với slide với 4 mức từ 1 đến 4
        JPanel levelPane = new JPanel();        
        levelSlider.setMajorTickSpacing(1);                     // Tạo khoảng cách giữ các số là 1           
        levelSlider.setPaintTicks(true);                        // Thêm dấu gạch vào bên dưới slide phân chia
        levelSlider.setPaintLabels(true);                       // Thêm số vào các dấu gạch
        levelPane.add(levelSlider);                             //
        
        levelPane.setBorder(BorderFactory.createCompoundBorder( // Tạo boder chìm xuống trong Jpanel
                BorderFactory.createEmptyBorder(5,5,5,5),
                BorderFactory.createTitledBorder("Player limit")));
        return levelPane;                               
    }
    
    // Khởi tạo CodePanel
    private JPanel createCodePane(){
        // Code 4 số
        JTextField codeText = new JTextField("");   
        JTextField roomTitle = new JTextField(15); 
        JLabel codeLabel = new JLabel("Code for this Room: ");
        JLabel roomLabel = new JLabel("Title: ");
        JPanel codePane = new JPanel(new GridLayout(0,1)); 
        // Tạo số ngẫu nhiên [1000,9999]
        Random rn = new Random();
        int range = MAXCODE - MINCODE + 1;
        int randomNum = MINCODE + rn.nextInt(range);  
        //Gán vào pane
        codeText.setText(String.valueOf(randomNum));
        codeText.setEnabled(false);
        codePane.add(roomLabel);
        codePane.add(roomTitle);
        codePane.add(codeLabel);
        codePane.add(codeText);
        
        codePane.setBorder(BorderFactory.createCompoundBorder( //Cài đặt border chìm xuống dưới 
                BorderFactory.createEmptyBorder(5,5,5,5),
                BorderFactory.createTitledBorder("Room settings")));
        return codePane;
    }
    //Khởi tạo JPanel chứ 2 button và không có border
    private JPanel createButtonPane(){
        JPanel buttonPane = new JPanel(new BorderLayout());
        JPanel pane = new JPanel(new GridLayout(1,2,5,0));
        pane.add(ok = new JButton("OK"));
        pane.add(cancel = new JButton("Cancel"));
        buttonPane.add(pane,BorderLayout.EAST);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return buttonPane;
    }
    public void Open()
    {
        setVisible(true);
    }
    
    //Bắt sự kiện người dùng click vào nút Ok thì tạo mới

    /**
     *
     * @param e
     */
     @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == ok){        
            //Sự kiện khi ấn OK
        }
        // Ẩn màn hình chọn
        setVisible(false);
    }
}
