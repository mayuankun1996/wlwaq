package DES;

public class DES {
	String EncryResult="";
	String DecryResult="";
	public String Encryption(String str,String password)//����
	{
		int len=str.length();
		int a=len/8;
		int b=len%8;
		if(b==0)//�պ�8�ı���
		{
			for(int i=0;i<a;i++)		
			{
				DES8byte des=new DES8byte();
				String data=str.substring(8*i,8*i+8);
				EncryResult+=des.Encryption(data,password);
			}
		}
		else
		{
			DES8byte des=new DES8byte();
			for(int i=0;i<a;i++)		
			{
				String data=str.substring(8*i,8*i+8);
				EncryResult+=des.Encryption(data,password);
			}
			String data2=str.substring(a*8);
			int k=8-b;
			for(int j=0;j<k;j++)
			{
				data2+="a";
			}
			EncryResult+=des.Encryption(data2,password);
		}
		return EncryResult;		
	}
	public String Decryption(String str,String password)//����
	{
		int len=str.length();
		int a=len/64;
		String data[]=new String[a];
		for(int i=0;i<a;i++)
		{
			DES8byte des=new DES8byte();
			DecryResult+=des.Decryption(str.substring(64*i,64*i+64),password);
		}
		String re[]=DecryResult.split("a");
		DecryResult=re[0];
		//System.out.println(DecryResult);
		return DecryResult;
	}
	public static void main(String args[]) {
		DES des=new DES();
		String str = "0100011100001fw1"; 
		System.out.println("���������Ϊ��"+str);
		String password = "abcdefgh";
		System.out.println("�������ԿΪ��"+password);

		String a=des.Encryption(str,password);
		System.out.println("��Ϊ��"+a);
		System.out.println("���ܽ��Ϊ��"+des.Decryption(a, password));

	}
}


