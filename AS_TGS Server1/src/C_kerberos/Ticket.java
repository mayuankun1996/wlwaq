package C_kerberos;
import DES.DES;

public class Ticket {
	String Kctgs;
	String IDc;
	String ADc;
	String ID;
	String TS;
	String Lifetime;
	
	 String enclosure(String password,String Kctgs,String IDc,String ADc,String IDtgs,String TS2,String Lifetime2)
	 //��װ���ģ���ͷ��ԴID��Ŀ��ID��IDC,IDtgs,TS1��
	 {
		 String message="";
		 DES des=new DES();
		 message=Kctgs+IDc+ADc+IDtgs+TS2+Lifetime2;
		 System.out.println(message.length());
		 //����
		 message=des.Encryption(message,password);
		 System.out.println(message.length());
		 return message;
		 
	 }
//	  public String addZero(String ip){//IP��0
//		   String result="";        //�ñ��洦���Ľ��
//		   String []array=ip.split("\\.");     //���������������ַ����������С�����ֽ⣬��Ϊ�����ʽһ��ת���ַ���������Ҫд��"\\."
//		   for(int i=0;i<array.length;i++){
//		    while(array[i].length()<3){    //һ�����ֳ����ĸ��ַ������ַ������Ѿ�û����С�㣬���һ���ַ����ĳ���С��������ô����ǰ�����
//		     array[i]="0"+array[i];
//		    }
//////		    if(i!=array.length-1){        //������ټ����ں������һ��С�㣬��Ϊ���һ������Ҫ�ӣ�������if����һ��
//////		    array[i]=array[i]+".";
////		    }
//		   }
//		   for(int i=0;i<array.length;i++){
//		    result+=array[i];              //������õ��ĸ��ַ���������
//		   }
//		   return result;
//		  }
	  @SuppressWarnings("null")
	  Ticket deblocking(String message,String password)
	  {
		  DES des=new DES();
		  message=des.Decryption(message, password);

		  //�жϳ����Ƿ���ϱ�׼
		  String[] result=null;
		  Ticket a=new Ticket();
		if(message.length()==46)
		{
		  //���̶���ʽ��ȡ����
		  
 
		  //System.out.println(Dest_IP);
		  a.Kctgs=message.substring(0, 8);
		  a.IDc=message.substring(8, 12);
		  a.ADc=message.substring(12, 24);
		  a.ID=message.substring(24, 28);
		  a.TS=message.substring(28, 40);
		  a.Lifetime=message.substring(40, 46);
		}
		else 
		{
			System.out.println("���ĳ������������·���");
			
		}

		  return a;
	  }

	  public static void main(String args[]){
	       Ticket deal=new Ticket();
	       String a="";
	       Ticket b;
	       
	       //System.out.println(deal.addZero("62.4.22.115"));
	       a=deal.enclosure("170628888811","12345678","1234","123456789123","1234","123456789123","123456");
	       System.out.println(a);
	       //System.out.println(deal.rebuildIP("11111111111111"));
	       
	       
	       b=deal.deblocking(a,"170628888811");
	      
	       System.out.println(b.Lifetime);

	       
	      }
}
