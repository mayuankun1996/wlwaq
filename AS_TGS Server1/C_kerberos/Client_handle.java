package C_kerberos;

import DES.DES;

public class Client_handle {
	 String ToAS_enclosure(String head,String source_IP,String Dest_IP,String IDC,String IDtgs,String TS1)
	 //��װ��AS���ģ���ͷ��ԴID��Ŀ��ID��IDC,IDtgs,TS1��
	 {
		 String message="";
		 int L_source_IP,L_Dest_IP;
		 L_source_IP=source_IP.length();//ͨ��length��ȡ�ַ�������
		 if(L_source_IP>15)//�����涨���ȱ���
			 System.out.println("ԴIP��ʽ���������·���");
		 if(L_source_IP<=15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
		 {
			 source_IP=addZero(source_IP);
		 }
		 L_Dest_IP=Dest_IP.length();
		 if(L_Dest_IP>15)//�����涨���ȱ���
			 System.out.println("Ŀ��IP��ʽ���������·���");
		 if(L_Dest_IP<=15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
		 {
			 Dest_IP=addZero(Dest_IP);
		 }
		 message=head+source_IP+Dest_IP+IDC+IDtgs+TS1;
		return message;
		 
	 }
	  public String addZero(String ip){//IP��0
		   String result="";        //�ñ��洦���Ľ��
		   String []array=ip.split("\\.");     //���������������ַ����������С�����ֽ⣬��Ϊ�����ʽһ��ת���ַ���������Ҫд��"\\."
		   for(int i=0;i<array.length;i++){
		    while(array[i].length()<3){    //һ�����ֳ����ĸ��ַ������ַ������Ѿ�û����С�㣬���һ���ַ����ĳ���С��������ô����ǰ�����
		     array[i]="0"+array[i];
		    }

		   }
		   for(int i=0;i<array.length;i++){
		    result+=array[i];              //������õ��ĸ��ַ���������
		   }
		   return result;
		  }
		 String TOtgs_enclosure(String head,String source_IP,String Dest_IP,String IDv,String Ttgs,String Authenticatorc)
		 //��װ���ģ���ͷ��ԴIP��Ŀ��IP��IDv,Ttgs,Authenticatorc��
		 {
			 String message="";
			 
			 int L_source_IP,L_Dest_IP;
			 L_source_IP=source_IP.length();//ͨ��length��ȡ�ַ�������
			 if(L_source_IP>15)//�����涨���ȱ���
				 System.out.println("ԴIP��ʽ���������·���");
			 if(L_source_IP<=15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
			 {
				 source_IP=addZero(source_IP);
			 }
			 
			 L_Dest_IP=Dest_IP.length();
			 if(L_Dest_IP>15)//�����涨���ȱ���
				 System.out.println("Ŀ��IP��ʽ���������·���");
			 if(L_Dest_IP<=15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
			 {
				 Dest_IP=addZero(Dest_IP);
			 }
			 message=head+source_IP+Dest_IP+IDv+Ttgs+Authenticatorc;
			return message;
			 
		 }
		 String TOv_enclosure(String head,String source_IP,String Dest_IP,String Tv,String Authenticatorc)
		 //��װ��V���ģ���ͷ��ԴIP��Ŀ��IP��Tv,Authenticatorc��
		 {
			 String message="";
			 
			 int L_source_IP,L_Dest_IP;
			 L_source_IP=source_IP.length();//ͨ��length��ȡ�ַ�������
			 if(L_source_IP>15)//�����涨���ȱ���
				 System.out.println("ԴIP��ʽ���������·���");
			 if(L_source_IP<=15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
			 {
				 source_IP=addZero(source_IP);
			 }
			 
			 L_Dest_IP=Dest_IP.length();
			 if(L_Dest_IP>15)//�����涨���ȱ���
				 System.out.println("Ŀ��IP��ʽ���������·���");
			 if(L_Dest_IP<=15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
			 {
				 Dest_IP=addZero(Dest_IP);
			 }
			 message=head+source_IP+Dest_IP+Tv+Authenticatorc;
			return message;
			 
		 }
		 //�������TGS�ı��ģ����ģ���Կ��
		 TGS_C FROTGS_deblocking(String message,String password)
			  {

				  //�жϳ����Ƿ���ϱ�׼
				  String[] result=null;
				  TGS_C a=new TGS_C();
				  DES des=new DES();
				  message=des.Decryption(message, password);
				if(message.length()>4)
				{
				  //���̶���ʽ��ȡ����
				  
				  a.head=message.substring(0, 4);
				  a.source_IP=message.substring(4, 16);
				  a.Dest_IP=message.substring(16, 28);
				  a.Kcv=message.substring(28, 36);
				  a.IDv=message.substring(36, 40);
				  a.TS4=message.substring(40, 52);
				  a.Tv=message.substring(52, message.length());
				  
				  //��ԭIP
				  a.source_IP=rebuildIP(a.source_IP);
				  //System.out.println(a.source_IP);
				  a.Dest_IP=rebuildIP(a.Dest_IP);
				}
				else 
				{
					System.out.println("���ĳ������������·���");
					
				}

				  return a;
			  }
		//�������AS�ı��ģ����ģ���Կ��
		AS_C FROAS_deblocking(String message,String password)
	  {

		  //�жϳ����Ƿ���ϱ�׼
		  AS_C a=new AS_C();
		  DES des=new DES();

		  message=des.Decryption(message, password);
		  //System.out.println(message.length());
		  //System.out.println(message);
		if(message.length()>2)
		{
		  //���̶���ʽ��ȡ����
		  
		  a.head=message.substring(0, 4);
		  a.source_IP=message.substring(4, 16);
		  a.Dest_IP=message.substring(16, 28);
		  a.Kctgs=message.substring(28, 36);
		  a.IDtgs=message.substring(36, 40);
		  a.TS2=message.substring(40, 52);
		  a.Lifetime2=message.substring(52, 58);
		  a.Ttgs=message.substring(58, message.length());
		  
		  //��ԭIP
		  a.source_IP=rebuildIP(a.source_IP);
		  a.Dest_IP=rebuildIP(a.Dest_IP);
		}
		else 
		{
			System.out.println("���ĳ������������·���");
		}
		  return a;
	  }
		//�������V�ı��ģ����ģ���Կ��
	/*	V_C FROv_deblocking(String message,String password)
		  {

			  //�жϳ����Ƿ���ϱ�׼
			  String[] result=null;
			  V_C a=new V_C();
			  DES des=new DES();
			  message=des.Decryption(message, password);
			if(message.length()>4)
			{
			  //���̶���ʽ��ȡ����
			  
			  a.head=message.substring(0, 4);
		 a.source_IP=message.substring(4, 16);
			//  a.Dest_IP=message.substring(16, 28);
			  //System.out.println(Dest_IP);
			//   a.TS5=message.substring(28, 41);

			  
			  //��ԭIP
			a.source_IP=rebuildIP(a.source_IP);
			  System.out.println(a.source_IP);
			  a.Dest_IP=rebuildIP(a.Dest_IP);
			}
			else 
			{
				System.out.println("���ĳ������������·���");
				
			}

			  return a;
		  }*/
		C_V  deblocking(String message)
		  {

			  //�жϳ����Ƿ���ϱ�׼
			  String[] result=null;
			  C_V  a=new C_V ();
			if(message.length()==668)
			{
			  //���̶���ʽ��ȡ����
			  
			  a.head=message.substring(0, 4);
			  a.source_IP=message.substring(4, 16);
			  a.Dest_IP=message.substring(16, 28);
			  //System.out.println(Dest_IP);
			  a.Tv=message.substring(28, 412);
			  a.Authenticatorc=message.substring(412, 424);
			  //��ԭIP
			  a.source_IP=rebuildIP(a.source_IP);
			//  System.out.println(a.source_IP);
			  a.Dest_IP=rebuildIP(a.Dest_IP);
			}
			else 
			{
				System.out.println("���ĳ������������·���");
				
			}

			  return a;
		  }
		//�ַ�����ԭΪIP��ַ
	  String rebuildIP(String IP)
	  {
		String first,second,third,forth;
		first=IP.substring(0, 3);
		second=IP.substring(3, 6);
		third=IP.substring(6, 9);
		forth=IP.substring(9, 12);
		
		int i = Integer.valueOf(first);//���ַ���ת��Ϊ����
		first = String.valueOf(i);//ת�����ַ���
		i = Integer.valueOf(second);
		second= String.valueOf(i);
		i = Integer.valueOf(third);
		third=String.valueOf(i);
		i = Integer.valueOf(forth);
		forth=String.valueOf(i);
		IP=first+"."+second+"."+third+"."+forth;
		return IP;
		  
	  }
	  public static void main(String args[]){
	       Client_handle deal=new Client_handle();
	       String a="192168022006";
	       AS_C b;
	       System.out.println(deal.rebuildIP(a));
	       a=deal.ToAS_enclosure("0000","2.168.1.1","192.168.1.2","0001","2001","170628888811");
	       System.out.println(a);
	       a+="111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

	      }

}
