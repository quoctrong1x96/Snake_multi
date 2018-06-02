/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake_multi.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author TranCamTu
 */
public class SnakeServer extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private ServerControl handler;
	private JPanel contentPane;
	public JButton buttonStart;
	public JButton buttonStop;
	public JTextPane textLog;
        public String Code;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeServer frame = new SnakeServer();
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
	public SnakeServer() {
		setTitle("Snake Server Site");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		buttonStart = new JButton("Start");
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler = new ServerControl(SnakeServer.this);
				handler.start();
			}
		});
		panel.add(buttonStart);
		
		buttonStop = new JButton("Stop");
		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.stop();
			}
		});
		buttonStop.setEnabled(false);
		panel.add(buttonStop);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textLog = new JTextPane();
		textLog.setEnabled(false);
		textLog.setEditable(false);
		scrollPane.setViewportView(textLog);
		
                JTextField txtCodeRandom = new JTextField();
		txtCodeRandom.setEnabled(false);
                Random rnd = new Random();
                int code = 1000 + rnd.nextInt(8999);
                txtCodeRandom.setText(Integer.toString(code));
		contentPane.add(txtCodeRandom, BorderLayout.SOUTH);
		txtCodeRandom.setColumns(10);
                
		DefaultCaret caret = (DefaultCaret)textLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
	}

}
