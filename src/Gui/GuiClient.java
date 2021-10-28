package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Class.Packet;
import Class.Sender;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class GuiClient extends JFrame implements KeyListener{

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblNewLabel ;
	private JTextField textPane; 
	private Sender sender ; 
	private Vector<String> listData = new Vector<String>();
	private JList list ; 

	String SLabel = ""; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String s = JOptionPane.showInputDialog("Mời bạn nhập tên"); 
					
					GuiClient frame = new GuiClient(s);
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
	public GuiClient(String s) {
		super(s); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		try {// set icon giao dien---------------------------
			Image iconmes = ImageIO.read(new File("icon.jpg"));
			this.setIconImage(iconmes); 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		
		}
		
		textPane = new JTextField();
		textPane.setBounds(10, 10, 416, 29);
		textPane.addKeyListener(this);
		contentPane.add(textPane);

		 list = new JList(listData);
		list.setCellRenderer(new ContentCell());

		//list.setBounds(10, 49, 416, 211);
		//contentPane.add(list);
		JScrollPane scroll  = new JScrollPane(list);
		
		scroll.setPreferredSize(getPreferredSize());
		scroll.setBounds(10, 49, 416, 211);
		scroll.createVerticalScrollBar();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scroll, BorderLayout.CENTER);
		sender = new Sender(s) ;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					String s = sender.receiveMess(); 
					if( s != null) {
						listData.add(s);
						list.updateUI(); 
					}
				}
			}
		}).start();
		
		this.addWindowListener(new WindowAdapter() {
			 public void windowClosed(WindowEvent e) {
			       
			 }
			    public void windowClosing(WindowEvent e) {
			    	sender.sendMess("&&&remove");			    }
		});

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			sender.sendMess(textPane.getText());
			textPane.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
class ContentCell implements ListCellRenderer{
	boolean ok = true ;
	int index1 = -1; 
	@Override
	public JPanel getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,boolean cellHasFocus) {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel text = new JLabel(value.toString()); 
		panel.add(text, BorderLayout.CENTER); 
		return panel;

	}
	
	
	
}
