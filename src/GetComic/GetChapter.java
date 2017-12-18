package GetComic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GetChapter {
	private ArrayList<Chapter> Chapter = new ArrayList<Chapter>();
	private String UrlAdd = null;
	private String ComicNum;
	private int LenOfComic;
	private String result = null;
	
	public GetChapter(String ComicNum)
	{
		this.ComicNum = ComicNum;
		this.LenOfComic = this.ComicNum.length();
		this.UrlAdd = "http://www.manhuagui.com/comic/" + ComicNum + "/" + "?" + (new Date()).getTime();
		GetHtml();
		AnalyHtml();
		Collections.reverse(Chapter);
	}
	
	//����HTTP��������ͷ
	private void SetProp(URLConnection con)
	{
		con.setRequestProperty("accept", "*/*");
		con.setRequestProperty("connection", "Keep-Alive");
		con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
		return;
	}
	
	//ץȡ��ҳ����
	private void GetHtml()
	{
		if(null == UrlAdd) return;
		String line;
		URL url;
		
		try {
			url = new URL(UrlAdd);
			URLConnection urlcon = url.openConnection();
			SetProp(urlcon);
			urlcon.connect();
			BufferedReader urlRead = new BufferedReader(new InputStreamReader(urlcon.getInputStream(), "utf-8"));
			while((line = urlRead.readLine()) != null)
			{
				result += line;
			}
		} catch (Exception e) {
			//TODO �����Ի�������ʾ���������������
			System.out.println("Error");
			e.printStackTrace();
		}
		
		return;
	}
	
	//������ҳ���ݣ���ȡ�غϱ��������
	private void AnalyHtml()
	{
		String Title = null;
		String Html = null;
		int startidx = 0;
		int endidx = 0;
		
		if(null == result) return;
		String SearchWord = "<li><a href=\"/comic/" + ComicNum;
		int index = 0;
		while(-1 != (index = result.indexOf(SearchWord)))
		{
			//��ȡ��Ϊ�˷�������������ٶȣ�ͬʱҲ�ò��ҵ��ĵ�һ���ض��ַ����������ǵ���Ҫ
			result = result.substring(index);
			//������ַ����
			startidx = result.indexOf(ComicNum);
			endidx = result.indexOf("\"", startidx);
			Html = "http://www.manhuagui.com/comic/" + ComicNum + "/" + result.substring(startidx + LenOfComic + 1, endidx);
			//���ұ�������
			startidx = result.indexOf("title");
			endidx = result.indexOf("\"", startidx + 8);
			Title = result.substring(startidx + 7, endidx);
			//�����б���
			Chapter.add(new Chapter(Title, Html));
			result = result.substring(endidx);
		}	
		
		return;
	}

	public ArrayList<Chapter> getChapter() {
		return Chapter;
	}
}
