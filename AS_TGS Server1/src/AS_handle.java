package ASSocket;

import DES.DES;

public class AS_handle {
	////////
	//��װ���ģ���Կ����ͷ��ԴIP��Ŀ��IP��[Kc,tgs|| IDtgs|| TS2|| Lifetime2|| Tickettgs��	 
    String TOC_enclosure(	String password,String head,String source_IP,String Dest_IP,String Kctgs,String IDtgs,String TS2,String Lifetime2,String tgs)
	 {
		 String message="";
		 DES des=new DES();
		 int L_source_IP,L_Dest_IP;
		 L_source_IP=source_IP.length();//ͨ��length��ȡ�ַ�������
		 if(L_source_IP>15)//�����涨���ȱ���
			 System.out.println("ԴIP��ʽ���������·���");
		 if(L_source_IP <= 15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
		 {
			 source_IP=addZero(source_IP);
		 }
		 L_Dest_IP=Dest_IP.length();
		 if(L_Dest_IP>15)//�����涨���ȱ���
			 System.out.println("Ŀ��IP��ʽ���������·���");
		 if(L_Dest_IP <= 15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
		 {
			 Dest_IP=addZero(Dest_IP);
		 }
		 //System.out.println(Dest_IP);
		 message=head+source_IP+Dest_IP+Kctgs+IDtgs+TS2+Lifetime2+tgs;

		 //����
		 message=des.Encryption(message,password);
		 
		return message;
		 
	 }
    
    //IP��0
	public String addZero(String ip){
		
		   String result="";        //�ñ��洦���Ľ��
		   String []array=ip.split("\\.");   //���������������ַ����������С�����ֽ⣬��Ϊ�����ʽһ��ת���ַ���������Ҫд��"\\."
		   for(int i=0;i<array.length;i++){
		    while(array[i].length()<3){    //һ�����ֳ����ĸ��ַ������ַ������Ѿ�û����С�㣬���һ���ַ����ĳ���С��������ô����ǰ�����
		     array[i]="0"+array[i];
		    }
		   }
		   for(int i=0;i<array.length;i++){ //������õ��ĸ��ַ���������
		    result+=array[i];             
		   }
		   return result;
		  }
	
	//��ȡC->AS���� 
	@SuppressWarnings("null")	
	C_AS deblocking(String message)
	  {
		//�жϳ����Ƿ���ϱ�׼
		C_AS a=new C_AS();
		if(message.length()>15)
		{
		   //���̶���ʽ��ȡ����		  
		   a.head=message.substring(0, 4);
		   a.source_IP=message.substring(4, 16);		  		  
		   a.Dest_IP=message.substring(16, 28);
		   a.IDC=message.substring(28, 32);
		   a.IDtgs=message.substring(32, 36);
		   a.TS1=message.substring(36, 48);
		  
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
   ////////////////////////////// 
	//IP��.	
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
	       AS_handle deal=new AS_handle();
	       String a="";
	       C_AS b;
	       a=deal.TOC_enclosure("12345678","0001","2.168.1.1","192.168.43.121","12345678","1234","170628888811","123456","11111111111111111111111111111111111111111111111111111111111111111111");
	       System.out.println(a);
	      }

}
