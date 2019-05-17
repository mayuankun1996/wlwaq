package C_kerberos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

public class socket {

	//111111111
	   public String judge="fause";
       static String IDc;
       static String IDtgs="2001";
       static String ASIP="172.20.10.7";
       static String TGSIP="172.20.10.8";
       static String VIP="172.20.10.7";
       
	   public static String Time()
		{
			SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");//�������ڸ�ʽ
			String date = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ�䣬Ҳ��ʹ�õ�ǰʱ���
			return date;
		}
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;

	   public static String hashcode(String Secret)
		{
			String str = Secret;
			int hash = str.hashCode();
			String str2 = null;
		    int j = Math.abs(hash);
			Integer k = j;
			
			if(k.toString().length() == 8)
			{
				str2 = k.toString();
			}
			
			if(k.toString().length()<8)
			{
				str2 = "0"+k.toString();
				while(str2.length() < 8)
				{
				  str2 = "0"+str2;
					
				}
			}
			
			if(k.toString().length() >8 )
			{
				str2 = k.toString().substring(0, 8);
			}
						
			return str2;
			
		}
	   int Vsocket(String Tv,String Kcv)
	   {
		   Client_handle  d=new Client_handle ();
		   String Au="";
			Socket socket = null;
			
			BufferedReader br = null;
			PrintWriter pw = null;
			
			try {
				//�ͻ���socketָ���������ĵ�ַ�Ͷ˿ں�
				socket = new Socket(VIP,7777);
				System.out.println("Socket=" + socket);
				//ͬ������ԭ��һ��
				br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream())));
				String source_IP=
				InetAddress.getLocalHost().getHostAddress();
				Authenticatorc au=new Authenticatorc();
				
				Au=au.enclosure(Kcv,IDc,source_IP, Time());
					pw.println(d.TOv_enclosure("0003",source_IP,VIP,Tv, Au));
					pw.flush();
					C_V handleV=new C_V();
					while(true)
					{   
						String str = br.readLine();
			
					//	System.out.println(source_IP);
						if(str.length()>20)
						{   

							//System.out.println(str.length());
							handleV=d.deblocking(str);
						//	handleV=deal.FROv_deblocking(str, Kcv);
						//	System.out.println(source_IP);
							System.out.println("�Ѳ��Ʊ����Ϣ�ǣ�");
							System.out.println(handleV.Authenticatorc+1);
							System.out.println(handleV.Dest_IP);
							if(!VIP.equals(handleV.Dest_IP))//�ж���֤�Ƿ����
							{
								System.out.println("��Ϣ�������ط�");                                                                                                                 
								pw.println("REPEAT");
								pw.flush();
							}
							else
							
								pw.println("END");
								pw.flush();
								judge="true";
								break;	
								
						}
						
					}
					System.out.println("V��֤�ɹ�");
					br.close();
					pw.close();
					System.out.println("��֤����,���Կ�ʼͨ��" );

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					//System.out.println("TGSclose......");
					br.close();
					pw.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return 0;
	   }
	   int TGSsocket(String Ttgs,String Kctgs)
	   {
		   Client_handle  deal=new Client_handle ();
		   String Au="";
			Socket socket = null;
			
			BufferedReader br = null;
			PrintWriter pw = null;
			try {
				//�ͻ���socketָ���������ĵ�ַ�Ͷ˿ں�
				socket = new Socket(TGSIP,8787);
				System.out.println("Socket=" + socket);
				//ͬ������ԭ��һ��
				br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream())));
				String source_IP=
				InetAddress.getLocalHost().getHostAddress();
				//System.out.println(source_IP);
				Authenticatorc au=new Authenticatorc();
				Au=au.enclosure(Kctgs,IDc,source_IP, Time());
					pw.println(deal.TOtgs_enclosure("0002", source_IP, TGSIP, "1004", Ttgs, Au));
					//System.out.println(deal.TOtgs_enclosure("0002", source_IP, TGSIP, "1004", Ttgs, Au).length());
					pw.flush();
					TGS_C handleTGS=new TGS_C();
					while(true)
					{
						
						String str = br.readLine();
						//System.out.println(str.length());
					
						if(str.length()>20)
						{
							//System.out.println(str.length());
							//String Kc=hashcode(password);
							System.out.println(str.length());
							handleTGS=deal.FROTGS_deblocking(str, Kctgs);
							System.out.println("�Ѳ��Ʊ����Ϣ�ǣ�");
							System.out.println(handleTGS.Tv);
							
							if(!TGSIP.equals(handleTGS.source_IP))//�ж���֤�Ƿ������������TGS��ͨ��
							{
								System.out.println("��Ϣ�������ط�");
								pw.println("REPEAT");
								pw.flush();			
							}
							else
							{
								pw.println("END");
								pw.flush();
								
								break;
								
							}
													
						}
					}
					System.out.println("TGS��֤�ɹ�");
					br.close();
					pw.close();
					socket.close();
					System.out.println("��ʼV��֤" ); 
					Vsocket(handleTGS.Tv,handleTGS.Kcv);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					//System.out.println("TGSclose......");
					br.close();
					pw.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return 0;
	   }
	   
	   public socket(String ID,String password)//���캯��
	   {
		    
			Client_handle  deal=new Client_handle ();
		
			//ͬ������ԭ��һ��

		
			//String password="ddsgddfff";
			try {
				IDc=ID;
				System.out.println(password);
				//�ͻ���socketָ���������ĵ�ַ�Ͷ˿ں�
				socket = new Socket(ASIP,8087);
				System.out.println("Socket=" + socket);
				br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream())));
				String source_IP=
				InetAddress.getLocalHost().getHostAddress();
				socket.getInetAddress().getHostAddress();

				
				//System.out.println(socket.getInetAddress().getHostAddress());
					pw.println(deal.ToAS_enclosure("0001",source_IP,ASIP,IDc,IDtgs,Time()));
					pw.flush();
					AS_C handleC=new AS_C();
					
					while(true)
					{
						String str = br.readLine();
						if(str.length()>20)
						{
							System.out.println("��ʼAS��֤" );
							System.out.println(str.length());
							String Kc=hashcode(password);
							//String Kc=hashcode("ddsgddfff");
							handleC=deal.FROAS_deblocking(str, Kc);
							System.out.println("�Ѳ��Ʊ����Ϣ�ǣ�");
							System.out.println(handleC.Ttgs);
							//System.out.println(handleC.head+" "+handleC.Dest_IP+" "+handleC.source_IP+" "+handleC.Kctgs+" "+handleC.TS2+" "+handleC.Lifetime2+handleC.IDtgs);
							if(!ASIP.equals(handleC.source_IP))//�ж���֤�Ƿ������������TGS��ͨ��
							{
								System.out.println("��Ϣ�������ط�");
								pw.println("REPEAT");
								pw.flush();
								
							}
							else
							{
								pw.println("END");
								pw.flush();
								br.close();
								pw.close();
								socket.close();
								break;
								
							}
							
							
						}
					}
				System.out.println("AS��֤�ɹ�");
				br.close();
				pw.close();
				socket.close();
				System.out.println("��ʼTGS��֤" );
				TGSsocket(handleC.Ttgs,handleC.Kctgs);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					//System.out.println("ASclose......");
					br.close();
					pw.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	   
		public static void main(String[] args) {
		//socket a=new socket();
		}
	}

