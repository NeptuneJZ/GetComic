package Start;

import java.io.IOException;

import javax.swing.JOptionPane;

import Log.LOG;
import download.DLControl;

public class Start {

	public static void main(String[] args) throws InterruptedException
	{
		//TODO ������UI����������
		String ComicNum = "456465465465";
		boolean result = DLControl.ThreadControl(ComicNum, 16);
		boolean logRst = LOG.LogIntoFile("/Comic/" + "log.txt");
		if(!logRst)
		{
			JOptionPane.showConfirmDialog(null, "������־�ļ���������·���Ƿ����óɹ�", "����˵��", JOptionPane.CLOSED_OPTION);
		}
		if(result)
		{
			JOptionPane.showConfirmDialog(null, "�������^_^", "˵��", JOptionPane.CLOSED_OPTION);
		}
		else
		{
			int Select = JOptionPane.showConfirmDialog(null, "���س�������-_-!\n�Ƿ�Ҫ�򿪴���˵����־", "˵��", JOptionPane.OK_CANCEL_OPTION);
			if(Select == JOptionPane.OK_OPTION)
			{
				try {
					Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", "/Comic/" + "log.txt"});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}

