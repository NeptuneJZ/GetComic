package DownLoad;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

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
	private ArrayList<DLThread> Threads = new ArrayList<DLThread>();
	//��ȡ��ҳ���½�
	public boolean AnalyChapter()
	{
		if(null == ComicNum) return false;
		ArrayList<Chapter> Chapters = new GetChapter(ComicNum).getChapter();
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
			for(DLThread thread : Threads)
			{
				thread.setInterrput();		
			}
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
		Threads.clear();
		if(fixpool == null)
		{
			PoolSize = poolsize;
			fixpool = Executors.newFixedThreadPool(PoolSize);
			fixpool.shutdownNow();
		}	
		else if(fixpool != null && PoolSize != poolsize)
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
		File HeadDir = new File(FilePath + "/" + ComicNum);
		
		if(!(HeadDir.exists() && HeadDir.isDirectory()))
		{
			if(!HeadDir.mkdirs())
			{
				JOptionPane.showMessageDialog(null, "������ʼ�ļ���ʧ�ܣ���������Ƿ�����/��ַ����:"+ HeadDir.getAbsolutePath(), "����˵��", JOptionPane.CLOSED_OPTION);
				return false;
			}
		}
		
		ExecutorService fixpool = Executors.newFixedThreadPool(PoolSize);
		
		for(Chapter c : Chapters)
		{	
			DLThread dl = new DLThread(c, HeadDir.getAbsolutePath());
			dl.setFC(fc);
			Threads.add(dl);
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
