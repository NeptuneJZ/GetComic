package DownLoad;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Config.LOG;
import GetComic.Chapter;
import GetComic.GetChapter;

public class DLControl {
	private String ComicNum = null;
	public int PoolSize = 4;
	private ArrayList<Chapter> Chapters = null;
	private String FilePath = null;
	
	public boolean AnalyChapter()
	{
		if(null == ComicNum) return false;
		Chapters = new GetChapter(ComicNum).getChapter();
		if(Chapters.isEmpty())
		{
			LOG.log("�ֽ��½�ʧ�ܣ���������ID�Ƿ���ȷ��" + ComicNum);
			return false;
		}
		return true;
	}
	
	public boolean ThreadControl()
	{	
		File HeadDir = new File("/Comic/" + ComicNum);
		
		if(HeadDir.exists() && HeadDir.isDirectory())
		{
			HeadDir.delete();
		}
		
		if(!HeadDir.mkdirs())
		{
			LOG.log("������ʼ�ļ���ʧ�ܣ���������Ƿ�����/��ַ����/�ļ����Ѵ���:" + HeadDir.getAbsolutePath());
			return false;
		}
		
		ExecutorService fixpool = Executors.newFixedThreadPool(PoolSize);
		
		for(Chapter c : Chapters)
		{	
			DLThread dl = new DLThread(c.getHtml(), c.getTitle(), HeadDir.getAbsolutePath());
			fixpool.execute(dl);
		}
		
		fixpool.shutdown();
		
		while(true)
		{
			if(fixpool.isTerminated())
			{
				return true;
			}
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setComicNum(String comicNum) {
		ComicNum = comicNum;
	}

	public void setPoolSize(int poolSize) {
		PoolSize = poolSize;
	}
}
