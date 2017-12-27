package download;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import GetComic.Chapter;
import GetComic.GetChapter;
import Log.LOG;

public class DLControl {
		public static boolean ThreadControl(String ComicNum, int PoolSize) throws InterruptedException
		{
			ArrayList<Chapter> Chapters = new GetChapter(ComicNum).getChapter();
			if(Chapters.isEmpty())
			{
				LOG.log("�ֽ��½�ʧ�ܣ���������ID�Ƿ���ȷ��" + ComicNum);
				return false;
			}
			
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
				DownLoad dl = new DownLoad(c.getHtml(), c.getTitle(), HeadDir.getAbsolutePath());
				fixpool.execute(dl);
			}
			
			fixpool.shutdown();
			
			while(true)
			{
				if(fixpool.isTerminated())
				{
					return true;
				}
				Thread.sleep(2 * 1000);
			}
		}
}
