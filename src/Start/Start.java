package Start;

import javax.swing.JOptionPane;

import download.DLControl;

public class Start {

	public static void main(String[] args) throws InterruptedException
	{
		//TODO ������UI����������
		String ComicNum = "20671";
		boolean result = DLControl.ThreadControl(ComicNum, 16);
		if(result)
		{
			JOptionPane.showConfirmDialog(null, "�������^_^", "˵��", JOptionPane.CLOSED_OPTION);
		}
		else
		{
			JOptionPane.showConfirmDialog(null, "���س�������-_-!", "˵��", JOptionPane.CLOSED_OPTION);
		}
	}
}

