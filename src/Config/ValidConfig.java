package Config;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;

//��������ڼ�¼һЩ���õı���
public class ValidConfig {
	public static final int WinH = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int WinW = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int ComicH = 480;
	public static final int ComicW = 450;
	public static String JSFile = "";
	public static boolean RunThread = true;
	
	static
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(Start.Start.class.getResourceAsStream("/config.js")));
		String line = null;
		
		try {
			try{
				while((line = br.readLine()) != null)
				{
					JSFile = JSFile.concat(line);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
