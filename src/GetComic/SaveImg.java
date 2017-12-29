package GetComic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//���ഫ��һ��ͼƬ�ĵ�ַ�����ڱ����ͼƬ
public class SaveImg {
	private String UrlAdd = null;
	private String Path = null;
	private String FileName = null;
	private boolean interrput = false;
	
	public SaveImg(String UrlAdd, String Path, String FileName)
	{
		this.UrlAdd = UrlAdd;
		this.Path = Path;
		this.FileName = FileName;
	}
	//��ͼƬ�Ķ�������д���ļ��������Ƿ񱣴�ɹ�
	public int SavePicture()
	{
		if(null == Path || null == FileName) return 0;
		//��ǰ�ж��ļ��Ƿ���ڣ��������ֱ���������أ��ӿ������ٶ�
		File file = new File(Path + FileName);
		if(file.exists())
		{
			return 1;
		}
		
		byte[] imgData = getImgData();
		if(null == imgData) return 0;
		
		FileOutputStream fop = null;
		
		try {
			try {
				fop = new FileOutputStream(file);
				if(interrput) 
				{
					fop.close();
					file.delete();
				}
				
				fop.write(imgData);
				fop.flush();
			} catch (IOException e) {
				if(!interrput)
				{
					System.out.println("�����ļ�ʧ�ܣ����������ļ��л���̿ռ��Ƿ�������" + file);
					e.printStackTrace();
				}
				else
				{
					return 2;
				}
				return 0;
			}
			finally
			{
				fop.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return 1;
	}
	//��ȡͼƬ�Ķ�������
	private byte[] getImgData()
	{
		byte[] ImgData = null;
		if(null == UrlAdd) return null;
		try {
			URL url = new URL(UrlAdd);
			HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
			urlcon.setRequestProperty("Referer", "http://www.manhuagui.com/");
			urlcon.setRequestMethod("GET");
			urlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; ��) Gecko/20100101 Firefox/57.0");
			urlcon.setConnectTimeout(5*1000);
			
			ImgData = GetbyteFromStream(urlcon.getInputStream());
			return ImgData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//��ͼƬ��Http��ת��Ϊ��������
	private byte[] GetbyteFromStream(InputStream in) throws IOException
	{
		int len;
		
		byte[] buffer = new byte[3 * 100 * 1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while((len = in.read(buffer)) != -1)
		{
			out.write(buffer, 0 ,len);
		}
	
		byte[] imgData = out.toByteArray();
		in.close();
		out.close();
		
		return imgData;
	}
	
	public void setInterrput()
	{
		interrput = true;
	}

}
