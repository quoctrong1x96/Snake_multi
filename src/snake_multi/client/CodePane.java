/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author TranCamTu
 */
public class CodePane extends JFrame implements ActionListener{
     private ClientControl parrent;
     JTextField code;                               // Textbox input code
     JButton ok;                                    // Button Ok
     JButton cancel;                                // Button Cancel
     final static int WHITE = 0;                    // Mặc định quân Trắng là 0
     final static int BLACK = 1;                    // Mặc định quân Đen là 1 trong lần gọi đầu, và những lần sau k cần khởi tạo
     
     protected JPanel mainPane = new JPanel(new BorderLayout());  
     
    //Contruction với bàn cờ trước đó
    public CodePane(ClientControl _parent){
        super("Input Code");                                       //Khởi tạo JFram với title là Options
        parrent = _parent;
        code = new JTextField();
        code.setColumns(10);
        mainPane.add(code);
        
        mainPane.add(createButtonPane(),BorderLayout.SOUTH);
        mainPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setContentPane(mainPane);
        setVisible(false);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);             // Chỉ tắt JFrame hiện tại
        ok.addActionListener(this);
        cancel.addActionListener(this);
    }
    
    //Khởi tạo JPanel chứ 2 button và không có border
    public JPanel createButtonPane(){
        JPanel buttonPane = new JPanel(new BorderLayout());
        JPanel pane = new JPanel(new GridLayout(1,2,5,0));
        pane.add(ok = new JButton("OK"));
        pane.add(cancel = new JButton("Cancel"));
        buttonPane.add(pane,BorderLayout.EAST);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return buttonPane;
    }
    
     @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == ok){
            parrent.request("join;"+ code.getText());
        }else if(e.getSource() == cancel){
            // Ẩn màn hình chọn
            setVisible(false);
            parrent.stop();
        }
        
    }
    
}