package TGSSocket;

import DES.DES;

public class TGS_handle {
	
	//��װ���ģ���Կ����ͷ��ԴIP��Ŀ��IP��Kcv,IDv,TS4,,Ticketv��
	String TOC_enclosure(String password,String head,String source_IP,String Dest_IP,String Kcv,String IDv,String TS4,String v)	
	 {
		 String message="";
		 DES des=new DES();
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
		 }message=head+source_IP+Dest_IP+Kcv+IDv+TS4+v;
		 //����
		 message=des.Encryption(message,password);
		 
		return message;
		 
	 }
	
	//IP��0
	public String addZero(String ip){
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
	
	//���  
	public C_TGS FroC_deblocking(String message)
	{
	   //�жϳ����Ƿ���ϱ�׼
		String[] result=null;
		C_TGS a=new C_TGS();
		//System.out.println(message.length());
		if(message.length()==672)
		{
		  //���̶���ʽ��ȡ����	  
		  a.head=message.substring(0, 4);
		  a.source_IP=message.substring(4, 16);
	      a.Dest_IP=message.substring(16, 28);
		  a.IDv=message.substring(28, 32);
		  a.Ttgs=message.substring(32, 416);
		  a.Autnenticatorc=message.substring(416, 672);
		  
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

	      }
}
