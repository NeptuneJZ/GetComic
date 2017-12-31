package Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class LOG {

	private static String logbuff = "";
	private static int lognum = 0;
	public static final int PeriodType = 1;
	public static final int NormalType = 0;
	
	public static synchronized void log(String str,int type)
	{
		if(type > PeriodType || type < NormalType) return;
		
		if(type == PeriodType)
		{
			if(logbuff.length() > 0)
			{
				logintofile();
				lognum = 0;
				logbuff = "";
			}		
			return;
		}
		
		lognum ++;
		logbuff = logbuff.concat(str + "\r\n");
		
		if(lognum > 20 || str.length() > 1024)
		{
			logintofile();
			lognum = 0;
			logbuff = "";
		}
	}
	
	private static void logintofile()
	{
		File file = new File("./Comiclog.txt");
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(logbuff);
			logbuff = "";
			fw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "д����־������ر���־�ļ�", "����", JOptionPane.CLOSED_OPTION);
			return;
		}
	}
}
