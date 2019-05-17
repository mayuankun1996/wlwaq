package TGSSocket;
  
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import ASSocket.Tickettgs;
import UI.TGSUIServer;
import datatest.test;


public class TGSserver {

	TGSUIServer Myframe= new TGSUIServer();//����UI����
	public static int PORT = 8787;//�˿ں�
	
	//TGS���췽��
	public TGSserver()
	{
		ServerSocket s = null;
		Socket socket  = null;
		Myframe.frmAs.setVisible(true);//��ʾ���湦�ܿɼ�
		try {
			s = new ServerSocket(PORT);//����������
			Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"TGS��������������\n");
			
			//�ȴ������󡢷���һֱ����
			while(true){
				socket = s.accept();//��������
				//System.out.println("socket:"+socket);
				Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"���յ��ͻ���" + socket.getInetAddress()+"����������\n");
				new serverone(socket);				
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
	
	//�����ڲ���
	public class serverone extends Thread{

		private Socket socket = null;
		private BufferedReader br = null;
		private PrintWriter pw = null;
		public String Lifetime4 = "000999";
		public String Lifetime2 = "000999";//����ʱ��
		public String Ktgs = "12345678";//AS-Tgs��Կ
		public String Kv = "87654321";//V-Tgs��Կ
		
		public serverone(Socket s)//���췽��
		{
		   socket = s;
		   try
		   {
		      br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		      pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
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
				try 
				{
				    str = br.readLine();//��ȡ	
					Myframe.frmAs.setVisible(true);
					Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"���յ�C->TGS���ݣ�\n" + str+"\n");
					
					//���ö���
					C_TGS c_tgs = new C_TGS();	
					TGS_handle handle = new TGS_handle();
					Tickettgs tickettgs = new Tickettgs();
					Authenticatorc au = new Authenticatorc();
					
					if(str.equals("END"))//�жϽ���
					{
					  //System.out.println("Client has Closed!");
					  Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"�ͻ���\n"+ socket.getInetAddress()+"����TGS������������\n");
					  br.close();
					  pw.close();
					  socket.close();
					  break;
					}
				
					if(str.length() > 20)//�յ�����C->TGS��Ϣ
					{
												
						//���
						c_tgs=handle.FroC_deblocking(str);		
						Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"���C->TGS���ݽ�����\n" );
						
						//���Tickettgs
						tickettgs = tickettgs.deblocking(c_tgs.Ttgs, Ktgs);
						Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"���TickettgsƱ�ݳɹ���\n" );
						
						//������������
						String a = test.Time().substring(9, 12);
						String b = tickettgs.TS2.substring(9, 12);
						int c = Integer.parseInt(a);
						int d = Integer.parseInt(b);
						int f = (c-d);
						int t = Integer.parseInt(Lifetime2);
						
						//�������������TGS->C 
						if(str.length() > 20 && f<t)						
						{	
						    Ticketv ticket = new Ticketv();								
							String Kcv = test.Randomfigure().toString();
							String TS4 = test.Time();	
							
							//���Authenticatorc
							au = au.deblocking(c_tgs.Autnenticatorc,tickettgs.Kctgs);
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"���Authenticatorc��֤����ɹ���\n");
							
							//��װTicketv
							String ticket2 = ticket.enclosure(Kv, Kcv, tickettgs.IDc, au.ADc, c_tgs.IDv, TS4, Lifetime4);
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"��װTicketvƱ�ݳɹ���\n");
							
							//TGS->C�ļ��ܽ��
							String tgsc = handle.TOC_enclosure(tickettgs.Kctgs, c_tgs.head, c_tgs.Dest_IP, c_tgs.source_IP, Kcv, c_tgs.IDv, TS4, ticket2);
							pw.println(tgsc);
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"TGS->C���ܽ�������ѷ��ͣ�\n");
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"���͸�C��Ʊ��Ϊ��\n"+ticket2);

						}
						else if(f>=t)
						{
							//System.out.println("��Ϣ�������ط�");
							Myframe.txtMsg.append("\n"+test.Time2()+"\n"+"TickettgsƱ���ѹ������ط���\n");
							pw.println("REPEAT");
							pw.flush();
						}
							
					}
									
				} catch (Exception e) {
					try {
						br.close();
						pw.close();
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}		
		
	}

	public static void main(String[] args) {
				
        new TGSserver();
	}
	
}
