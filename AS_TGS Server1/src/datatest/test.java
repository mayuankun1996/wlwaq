/**
 * 
 */
/**
 * @author ��ס�ҵ�����DCX
 *
 */
package datatest;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.*;

public class test {

	// �����������hash�ɶ���8λ��Կ
	public static String hashcode(String Secret) {
		String str = Secret;
		int hash = str.hashCode();
		String str2 = null;
		int j = Math.abs(hash);
		Integer k = j;

		if (k.toString().length() == 8) {
			str2 = k.toString();
		}

		if (k.toString().length() < 8) {
			str2 = "0" + k.toString();
			while (str2.length() < 8) {
				str2 = "0" + str2;

			}
		}

		if (k.toString().length() > 8) {
			str2 = k.toString().substring(0, 8);
		}

		return str2;

	}

	// ��ȡʱ��
	public static String Time() {
		SimpleDateFormat df = new SimpleDateFormat("MddHHmmssSSS");// �������ڸ�ʽ
		String date = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ�䣬Ҳ��ʹ�õ�ǰʱ���
		return date;
	}

	// ��ȡʱ�䵽����
	public static String Time2() {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd_HH:mm:ss ");// �������ڸ�ʽ
		String date = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ�䣬Ҳ��ʹ�õ�ǰʱ���
		return date;
	}

	// �������ݿⲢ��ȡ����
	public static String Secret(int IDc) throws SQLException {
		Connection dbConn = null;
		String Secret = null;
		// �������ݿ�
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=test";
		String userName = "sa";
		String userPwd = "1";
		try {
			Class.forName(driverName);
			System.out.println("���������ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��������ʧ�ܣ�");
		}
		try {
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("�������ݿ�ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("SQL Server����ʧ�ܣ�");
		}

		Statement stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);// ����SQL�������

		// ��ȡ���ݿ�����
		ResultSet rs = stmt.executeQuery("SELECT * FROM ClientData");// ����������Ϣ��
		while (rs.next()) {
			if (Integer.toString(IDc).equals(rs.getString("IDc"))) {

				// System.out.println("�û�"+IDc+"��֤ͨ����");
				Secret = rs.getString(2);

			}
		}
		return Secret;

	}

	// �������ݿⲢ��������
	public static int Database(int IDc, String Secret) throws SQLException {
		Connection dbConn = null;

		// �������ݿ�
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=test";
		String userName = "sa";
		String userPwd = "1";
		try {
			Class.forName(driverName);
			System.out.println("���������ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��������ʧ�ܣ�");
		}
		try {
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("�������ݿ�ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("SQL Server����ʧ�ܣ�");
		}

		Statement stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);// ����SQL�������

		/*
		 * //������ System.out.println("��ʼ������"); String query =
		 * "create table ClientData(IDc int,Secret NCHAR(50),Hashkey varchar(50))";//
		 * ������SQL��� stmt.executeUpdate(query);// ִ��SQL������� System.out.println("�����ɹ�");
		 */
		String hashresult = hashcode(Secret);

		// ��������
		System.out.println("��ʼ��������");
		String a1 = null;
		int y = 0, l = 0;
		ResultSet rs = stmt.executeQuery("SELECT * FROM ClientData");// ����������Ϣ��
		if (!rs.next()) {
			String a2 = String.format("INSERT INTO ClientData VALUES('%d','%s','%s')", IDc, Secret, hashresult);// ��������SQL���
			stmt.executeUpdate(a2);// ִ��SQL�������
			System.out.println("�������ݳɹ�!");
			l++;
		}

		else {
			rs.previous();
			while (rs.next()) {
				int idd = rs.getInt(1);
				if (idd == IDc) {
					System.out.println("�û��Ѵ��ڣ������ظ�ע�ᣡ");
					y++;
					break;
				}
			}
		}
		if (y == 0 && l == 0) // ����û��������ұ�Ϊ�գ��ǾͲ�������
		{
			a1 = String.format("INSERT INTO ClientData VALUES('%d','%s','%s')", IDc, Secret, hashresult);// ��������SQL���
			stmt.executeUpdate(a1);// ִ��SQL�������
			System.out.println("�������ݳɹ�");
		}

		return y;
	}

	// ��ȡ���ݿ����ݽ��бȽ��Ƿ���֤�ɹ�,��󷵻�result
	public static String DBget(int IDc, String Secret) throws SQLException {
		Connection dbConn = null;

		// �������ݿ�
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=test";
		String userName = "sa";
		String userPwd = "1";
		try {
			Class.forName(driverName);
			// System.out.println("���������ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("��������ʧ�ܣ�");
		}
		try {
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			// System.out.println("�������ݿ�ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.print("SQL Server����ʧ�ܣ�");
		}

		Statement stmt = dbConn.createStatement();// ����SQL�������

		// ��ȡ���ݿ�����
		ResultSet rs = stmt.executeQuery("SELECT * FROM ClientData");// ����������Ϣ��
		String e = "*", f = "$";
		int j = 0;
		String result = "";
		String hash = "";
		String result2 = "";
		while (rs.next()) {
			if (Integer.toString(IDc).equals(rs.getString("IDc"))) {

				System.out.println("�û�" + IDc + "��֤ͨ����");
				j++;
				Secret = Secret.trim();
				String Secretlist = rs.getString(2).trim();
				// System.out.println(Secretlist+"* *"+Secret.trim()+"*");
				if (Secretlist.equals(Secret)) {
					System.out.println("�û�Secret��֤ͨ����");
					break;
				} else {
					System.out.println("�������*****����������*****��");
					result = e;

				}
			}
		}

		if (j == 0) {
			System.out.println("�û�������*****��ע��*****��");
			result = f;

		}

		String hashcoderesult = hashcode(Secret);
		hash = hashcoderesult;

		if (result == "") {
			result2 = hash + " " + Secret;
		} else if (result.length() != 0) {
			result2 = result + " " + hash + " " + Secret;
		}

		return result2;

	}

	// ���������ԿKc,tgs
	public static StringBuilder Randomfigure() {
		StringBuilder str = new StringBuilder();// ����䳤�ַ���
		Random random = new Random();
		// ����������֣�����ӵ��ַ���
		for (int i = 0; i < 8; i++) {
			str.append(random.nextInt(10));
		}
		return str;

	}

	public static void main(String[] args) throws UnknownHostException, SQLException {
		int IDc = 0001;
		String Secret = "dds123fff";
		Secret(12345678);
		System.out.println("ss");
		// Database(IDc,Secret);
		// String Kc = DBget(IDc,Secret);
		// StringBuilder l =Randomfigure();
		// System.out.println(l);
		// String inetAddress=InetAddress.getLocalHost().getHostAddress();
		// System.out.println(inetAddress);
	}
}
