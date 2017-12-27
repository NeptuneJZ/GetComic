package download;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import GetComic.GetPicture;
import GetComic.SaveImg;

public class DownLoad implements Runnable{
	private String html = null;
	private String title = null;
	private String path = null;
	
	public DownLoad(String html,String title,String path)
	{
		this.html = html;
		this.title = title;
		this.path = path;
		processTitle();
	}
	
	@Override
	public void run() {
		//System.out.println(Thread.currentThread().getName() + "��ʼ����" + title);
		File dir = new File(path + "/" + title);
		if(dir.exists() && dir.isDirectory())
		{
			dir.delete();
		}
		
		if(!dir.mkdir())
		{
			JOptionPane.showConfirmDialog(null, "�����½��ļ���ʧ�ܣ���������Ƿ�����/��ַ����/�ļ����Ѵ���"+ dir.getPath(), "����˵��", JOptionPane.CLOSED_OPTION);
			//TODO ����д�뵽��־����
			System.out.println("�����½��ļ���ʧ�ܣ���������Ƿ�����/��ַ����/�ļ����Ѵ���\n" + dir.getPath());
			return;
		}
		
		ArrayList<String> PicPath = new GetPicture(html).getPicturePath();
		
		if(PicPath.isEmpty())
		{
			//TODO ����д�뵽��־����
			System.out.println("����ͼƬ��ַʧ�ܣ���Ҫ��������");
			return;
		}
		
		int index = 1;
		int result = 0;
		for(String path : PicPath)
		{
			//System.out.println(Thread.currentThread().getName() + "��ʼ���ص�" + index + "P");
			result = (new SaveImg(path, dir.getAbsolutePath() + "/", index + ".jpg")).SavePicture();
			if(0 == result)
			{
				//TODO ����д�뵽��־����
				System.out.println("����ͼƬ��ַʧ�ܣ���Ҫ��������");
				return;
			}
			index ++;
		}
	}
	//һЩ����ַ��ŵ����ƿ��ܻᵼ�´����ļ���ʧ�ܣ�������Ҫ����Ԥ����
	private void processTitle()
	{
		title = title.replaceAll("[\\^%&',;=?$]+", "");
		System.out.println(title);
	}

}
