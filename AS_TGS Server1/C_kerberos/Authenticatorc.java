package C_kerberos;

import DES.DES;
//256
public class Authenticatorc {
	String IDc;
	String ADc;
	String TS3;
	 String enclosure(String password,String IDc,String ADc,String TS3)
	 //��װ���ģ���Կ��IDC,ADC,TS3��
	 {
		 String message="";
		 DES des=new DES();
		 int L_source_IP;
		 L_source_IP=ADc.length();//ͨ��length��ȡ�ַ�������
		 if(L_source_IP>15)//�����涨���ȱ���
			 System.out.println("ԴIP��ʽ���������·���");
		 if(L_source_IP<=15)//С�ڹ涨���ȣ���0��ȥ���м�ġ�.��
		 {
			 ADc=addZero(ADc);
		 }

		 message=IDc+ADc+TS3;
		 //System.out.println(message.length());
		 //����
		 message=des.Encryption(message,password);
		 //System.out.println(message.length());
		 return message;
		 
	 }
	  public String addZero(String ip){//IP��0
		   String result="";        //�ñ��洦���Ľ��
		   String []array=ip.split("\\.");     //���������������ַ����������С�����ֽ⣬��Ϊ�����ʽһ��ת���ַ���������Ҫд��"\\."
		   for(int i=0;i<array.length;i++){
		    while(array[i].length()<3){    //һ�����ֳ����ĸ��ַ������ַ������Ѿ�û����С�㣬���һ���ַ����ĳ���С��������ô����ǰ�����
		     array[i]="0"+array[i];
		    }
////		    if(i!=array.length-1){        //������ټ����ں������һ��С�㣬��Ϊ���һ������Ҫ�ӣ�������if����һ��
////		    array[i]=array[i]+".";
//		    }
		   }
		   for(int i=0;i<array.length;i++){
		    result+=array[i];              //������õ��ĸ��ַ���������
		   }
		   return result;
		  }
	  @SuppressWarnings("null")
	  Authenticatorc deblocking(String message,String password)
	  {
		  DES des=new DES();
		  message=des.Decryption(message, password);

		  //�жϳ����Ƿ���ϱ�׼
		  String[] result=null;
		  Authenticatorc a=new Authenticatorc();
		if(message.length()==28)
		{
		  //���̶���ʽ��ȡ����
		  

		  //System.out.println(Dest_IP);
		  
		  a.IDc=message.substring(0, 4);
		  a.ADc=message.substring(4, 16);
		  a.TS3=message.substring(16, 28);
		 
		}
		else 
		{
			System.out.println("���ĳ������������·���");
			
		}

		  return a;
	  }

	  public static void main(String args[]){
		  Authenticatorc deal=new Authenticatorc();
	       String a="";
	       Authenticatorc b;
	       
	       //System.out.println(deal.addZero("62.4.22.115"));
	       a=deal.enclosure("170628888811","1234","123456789123","123456789123");
	       System.out.println(a);
	       //System.out.println(deal.rebuildIP("11111111111111"));
	       
	       
	       b=deal.deblocking(a,"170628888811");
	      
	       System.out.println(b.TS3);

	       
	      }
}
