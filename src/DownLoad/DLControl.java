package DownLoad;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import Config.ValidConfig;
import GetComic.Chapter;
import GetComic.GetChapter;
import UI.FrameComic;
//�����Ϊ���ؿ������ģ���Ҫ����������ͨ�ź������̵߳Ŀ���
public class DLControl {
	private String ComicNum = null;
	private int PoolSize = 0;
	private String FilePath = null;
	private FrameComic fc = null;
	private ExecutorService fixpool = null;
	private String BookName = null;
	//��ȡ��ҳ���½�
	public boolean AnalyChapter()
	{
		if(null == ComicNum) return false;
		GetChapter  getchapter =  new GetChapter(ComicNum);
		ArrayList<Chapter> Chapters = getchapter.getChapter();
		BookName = getchapter.getBookName();
		
		if(Chapters.isEmpty())
		{
			return false;
		}
		fc.addChapters(Chapters);
		return true;
	}

	public void InterrputDL()
	{
		if(null != fixpool)
		{
			fixpool.shutdownNow();
			ValidConfig.RunThread = false;	
		}
		fc.InterrputDL();
	}
	
	public DLControl(FrameComic fc)
	{
		this.fc = fc;
	}
	
	public boolean StartDL(int poolsize)
	{
		ArrayList<Chapter> Chapters = fc.getDLInfo();
		if(fixpool == null)
		{
			PoolSize = poolsize;
			fixpool = Executors.newFixedThreadPool(PoolSize);
		}	
		else if(fixpool != null)
		{
			PoolSize = poolsize;
			fixpool.shutdownNow();	
			fixpool = Executors.newFixedThreadPool(PoolSize);			
		}
		
		if(Chapters.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "û���κ�ѡ���½�", "����˵��", JOptionPane.CLOSED_OPTION);
			return false;
		}
		
		if(ThreadControl(Chapters))
		{
			fc.disableAllbox();
			return true;
		}

		return false;
	}
	
	public boolean ThreadControl(ArrayList<Chapter> Chapters)
	{	
		File HeadDir = new File(FilePath + "/" +  BookName + "(" + ComicNum + ")");
		
		if(!(HeadDir.exists() && HeadDir.isDirectory()))
		{
			if(!HeadDir.mkdirs())
			{
				JOptionPane.showMessageDialog(null, "������ʼ�ļ���ʧ�ܣ���������Ƿ�����/��ַ����:"+ HeadDir.getAbsolutePath(), "����˵��", JOptionPane.CLOSED_OPTION);
				return false;
			}
		}
		
		for(Chapter c : Chapters)
		{	
			DLThread dl = new DLThread(c, HeadDir.getAbsolutePath());
			dl.setFC(fc);
			fixpool.execute(dl);	
		}
		
		return true;
	}

	public void setComicNum(String comicNum) {
		ComicNum = comicNum;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
}
