package ASSocket;
  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import UI.ASUIServer;
import datatest.test;


public class server {
	
	ASUIServer Myframe= new ASUIServer();//����UI����
    public static int PORT = 8087;//�˿ں�
	
	public server()
	{
		ServerSocket s = null;
		Socket socket  = null;
		Myframe.frmAs.setVisible(true);//��ʾ�ɼ�
		try {			
			s = new ServerSocket(PORT);//����������
			Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"AS��������������\n");
			
			//�ȴ������󡢷���һֱ����
			while(true){
				socket = s.accept();
				//System.out.println("socket:"+socket);
				Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"���յ��ͻ���" + socket.getInetAddress()+"����������\n");
				serverone one = new serverone(socket);
			}

		} catch (Exception e) {
			try {
				//socket.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			try {
				//s.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	//������ã����������
    public class serverone extends Thread{

		public Socket socket = null;
		public BufferedReader br = null;
		public PrintWriter pw = null;
		public String Lifetime2 = "000999";//����ʱ��
		String password = "12345678";//AS-Tgs��Կ
		
		//���췽��
		public serverone(Socket s)
		{
		   socket = s;
		   try {
				 br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				 pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				 //pw.println("socket:"+socket);
				 start();//�Զ�����run()
			} catch (Exception e) {
						
				e.printStackTrace();
			}
		}
				
		public void run() 
		{
			while(true)
			{
			    String str;
				int y;
				try {
					  str = br.readLine();			
					  if(str.equals(""))//��ȡ�ռ�����ȡ
					  {
						  continue;
					  }
					  String[] k = str.split(" ");//����ȡ����ע����¼��Ϣ�ָ�����ж�
					  Myframe.frmAs.setVisible(true);
					  Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"��ȡ��C->AS���ݣ�\n"+str+"\n");
					  
					  if(str.length() < 20 && k[0].equals("*"))//ע��
					  {
						   y = test.Database(Integer.parseInt(k[1]), k[2]);//������Ϣ�����ͻ��ˣ���������ж�
						   pw.println(Integer.toString(y));
						   pw.flush();
						   if(y==0)
						   {
						     Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"�ͻ��� "+k[1]+"("+socket.getInetAddress()+")"+" ע��ɹ���\n");
						   }
						   else if(y==1)
						   {
							 Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"�ͻ��� "+k[1]+"("+socket.getInetAddress()+")"+" �Ѵ��ڣ�\n");
						   }
								
					   }
					  
					   if(str.length() < 20 && k[0].equals("$"))//��֤
					   {
							String kk = test.DBget(Integer.parseInt(k[1]),k[2]);							
							String[] kk2 = kk.split(" ");
							if(kk2[0].length() == 8)
							{
							  Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"�ͻ��� "+k[1]+"("+socket.getInetAddress()+")"+" ��֤�ɹ���\n");
							  pw.println(kk);
							  pw.flush();
							}
							else if(kk2[0].length() != 8)
							{
							   Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"�ͻ��� "+k[1]+"("+socket.getInetAddress()+")"+" ��֤ʧ�ܣ�\n");
							   pw.println(kk);
							   pw.flush();

							}

						}		
					   
					    C_AS c_as = new C_AS();
						AS_handle handle = new AS_handle();
						Tickettgs ticket = new Tickettgs();
							
						if(str.length() > 20)//��������String��Ϣ
						{
							
							
							//���
							c_as=handle.deblocking(str);
							
							//���ݿ��ȡ����
                            String ss = test.Secret(Integer.parseInt(c_as.IDC));
                            
                            //���ݿⷵ�ط�����Կ
							String kk = test.DBget(Integer.parseInt(c_as.IDC), ss);
							
							//��ȡKc
							String Kc = null;
							String[] kk2 = kk.split(" ");
							if(kk2[0].length() == 8)
							{
								Kc = kk2[0];
							}
							else if(kk2[0].length() != 8)
							{
								Kc = kk2[1];
							}
							
                            //�����ȡKctgs
							String Kctgs = test.Randomfigure().toString();
							
							//��ȡ��ǰʱ���TS2
							String TS2 = test.Time();

							//��װTicket
							String Ticket = ticket.enclosure(password, Kctgs, c_as.IDC, c_as.source_IP, c_as.IDtgs, TS2, Lifetime2);
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"Ticket��װ�ɹ���\n");
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"��װ�������Ϊ��\n"+Ticket+"\n");
								
							//AS->C�ļ��ܽ��
							String asc = handle.TOC_enclosure(Kc, c_as.head, c_as.Dest_IP, c_as.source_IP, Kctgs, c_as.IDtgs, TS2, Lifetime2,Ticket);
							pw.println(asc);
							pw.flush();
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"AS->C�ļ��ܽ���ѷ��ͣ�\n");
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"AS��������֤��������ɣ�\n");

						}
						
						if(str.equals("END"))//�жϽ���
						{
							//System.out.println("Client has Closed!");
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"�ͻ��ˣ�" + socket.getInetAddress() +" ����AS������������\n");
							br.close();
							pw.close();
							socket.close();
							break;
						}	
											
//					} catch (Exception e) {
//						try {
//								br.close();
//								pw.close();
//								socket.close();
//							} catch (IOException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//						}
					}
				}//woshiwushuer
				
					
		}	
	
	public static void main(String[] args)
	{
		new server();		
    }
}
	
		



 
 