package ASSocket;
//384
import DES.DES;

public class Tickettgs {
	public String Kctgs;
	public String IDc;
	public String ADc;
	public String ID;
	public String TS2;
	public String Lifetime2;
	
     //��װTickettgs��Kc,tgs|| IDC|| ADC|| IDtgs|| TS2|| Lifetime2��
     String enclosure(String password,String Kctgs,String IDc,String ADc,String IDtgs,String TS2,String Lifetime2)	 
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
		 message=Kctgs+IDc+ADc+IDtgs+TS2+Lifetime2;
		 //����
		 message=des.Encryption(message,password);
	
		 return message;
		 
	 }
 	 
     //��0
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
	
	 //���Tickettgs
     @SuppressWarnings("null")
	 public Tickettgs deblocking(String message,String password)
	  {
		  DES des=new DES();
		  message=des.Decryption(message, password);
		  //�жϳ����Ƿ���ϱ�׼
		  String[] result=null;
		  Tickettgs a=new Tickettgs();
		 if(message.length()==46)
		 {
		  //���̶���ʽ��ȡ����
		  a.Kctgs=message.substring(0, 8);
		  a.IDc=message.substring(8, 12);
		  a.ADc=message.substring(12, 24);
		  a.ID=message.substring(24, 28);
		  a.TS2=message.substring(28, 40);
		  a.Lifetime2=message.substring(40, 46);
		 }
		 else 
		 {
			System.out.println("���ĳ������������·���");
			
		 }

		  return a;
	  }

	 public static void main(String args[]){
	       Tickettgs deal=new Tickettgs();
	       String a;
	       a=deal.enclosure("17062888","12345678","1234","123456789123","1234","123456789123","123456");
	       System.out.println(a.length());
	       deal=deal.deblocking(a,"170628888811");      
	       System.out.println(deal.ADc);  
	       System.out.println(deal.ID);
	       System.out.println(deal.IDc);
	       System.out.println(deal.Kctgs);
	       System.out.println(deal.Lifetime2); 
	       System.out.println(deal.TS2);
	     	       
	      }
}
