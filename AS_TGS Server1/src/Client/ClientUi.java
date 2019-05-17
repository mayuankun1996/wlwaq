package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import C_kerberos.socket;

import javax.swing.JPasswordField;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Color;
import java.awt.TextField;

public class ClientUi {

	private JFrame frmKeberos;
	private JTextField textField;
	public TextField textField_1;
	String IP="172.20.10.7";
	int port=8087;
	public String username;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUi window = new ClientUi();
					window.frmKeberos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientUi() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKeberos = new JFrame();
		frmKeberos.getContentPane().setBackground(new Color(238, 232, 170));
		frmKeberos.setTitle("Keberos�ͻ���");
		frmKeberos.setBounds(100, 100, 412, 300);
		frmKeberos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKeberos.getContentPane().setLayout(null);
		
		JButton btnNewButton_2 = new JButton("�û�ע��");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText()!=null&&textField_1.getText()!=null)
				{
					send();//����ע����Ϣ	
				}
				
			}
		});
		btnNewButton_2.setBounds(68, 154, 113, 27);
		frmKeberos.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("�û���½");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText()!=null&&textField_1.getText()!=null)
				{
					send2();//���͵�½��Ϣ	
				}
			}
		});
		btnNewButton_3.setBounds(212, 154, 113, 27);
		frmKeberos.getContentPane().add(btnNewButton_3);
		
		JLabel label = new JLabel("�û���");
		label.setBounds(52, 46, 72, 18);
		frmKeberos.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("  ����");
		label_1.setBounds(52, 98, 72, 18);
		frmKeberos.getContentPane().add(label_1);
		
		textField = new JTextField();
		textField.setBounds(123, 43, 203, 24);
		frmKeberos.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new TextField();
		textField_1.setBounds(123, 98, 202, 25);
		frmKeberos.getContentPane().add(textField_1);
	}
	public void send()//����ע����Ϣ
	{
		try {
			System.out.println(" �û�ע��");
			Socket socket = new Socket(IP, port);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));
			//System.out.println(" �û�ע��2");
			String hashID=hash(textField.getText());
			pw.println("* "+hashID+" "+textField_1.getText());
			pw.flush();
			System.out.println("* "+hashID+" "+textField_1.getText());
	        while(true)
	        {
	        	String str=br.readLine();
	        	if(str.equals("0"))//ע��ɹ�
				{
	        		username=textField.getText();
					prompt window1 = new prompt();
					window1.frame.setVisible(true);
					window1.textArea.append(" \n   ע��ɹ���");
					Thread.sleep(10000);
					textField.setText("");
					textField.requestFocus();//�����ı���
					textField_1.setText("");
					textField_1.requestFocus();//�����ı���
					pw.close();
					br.close();
					socket.close();
					break;
				}
				else if(str.equals("1"))//�û��Ѵ���
				{
					prompt window1 = new prompt();
					window1.frame.setVisible(true);
					window1.textArea.append(" \n �û��Ѵ��ڣ�");
					Thread.sleep(10000);
					textField.setText("");
					textField.requestFocus();//�����ı���
					textField_1.setText("");
					textField_1.requestFocus();//�����ı���
					pw.close();
					br.close();
					socket.close();
					break;
				}
	    		br.close();
				pw.close();
				socket.close();
				break;
	
	        }
			
		} catch (Exception e1) {
			System.out.println("�쳣:" + e1.getMessage());
		}
		
	}
	public void send2()//���͵�½��Ϣ
	{
		try {
			System.out.println(" �û���½");
			Socket socket = new Socket(IP, port);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));
			//System.out.println(" �û���½2");
			System.out.println("Socket=" + socket);
			username=textField.getText();
			String hashID=hash(textField.getText());
			pw.println("$ "+hashID+" "+textField_1.getText());
			pw.flush();
			System.out.println("* "+hashID+" "+textField_1.getText());
	        while(true)
	        {
	        	String str=br.readLine();
	        	System.out.println(str);
	        	String str2=str.substring(0,1);
	        	if(str2.equals("*"))//�������
				{
					prompt window1 = new prompt();
					window1.frame.setVisible(true);
					window1.textArea.append(" \n   �������");
					textField_1.setText("");
					textField_1.requestFocus();//�����ı���
					pw.close();
					br.close();
					socket.close();
					break;
				}
				else if(str2.equals("$"))//�û�������
				{
					prompt window1 = new prompt();
					window1.frame.setVisible(true);
					window1.textArea.append(" \n�û��������ڣ�");
					textField.setText("");
					textField.requestFocus();//�����ı���
					textField_1.setText("");
					textField_1.requestFocus();//�����ı���
					pw.close();
					br.close();
					socket.close();
					break;
				}
				else
				{

					pw.close();
					br.close();
					socket.close();
					socket a=new socket(hashID,textField_1.getText());//��֤����
					System.out.println(a.judge);
					if(a.judge.equals("true"))
					{
						frmKeberos.dispose();//�رյ�½����
						new client(username);
					}
					break;
				}
	
	        }
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	//��������û���hash�ɶ���4λID
		public static String hash(String Secret)
		{
			String str = Secret;
			int hash = str.hashCode();
			String str2 = null;
		    int j = Math.abs(hash);
			Integer k = j;
			
			if(k.toString().length() == 4)
			{
				str2 = k.toString();
			}
			
			if(k.toString().length()<4)
			{
				str2 = "0"+k.toString();
				while(str2.length() < 4)
				{
				  str2 = "0"+str2;
					
				}
			}
			
			if(k.toString().length() >4 )
			{
				str2 = k.toString().substring(0, 4);
			}
						
			return str2;
			
		}

}
