package GetComic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//�������ڻ�ȡ�������½����͸��½ڵ�URL
public class GetChapter {
	private ArrayList<Chapter> Chapter = new ArrayList<Chapter>();
	private String UrlAdd = null;
	private String ComicNum;
	
	public GetChapter(String ComicNum)
	{
		this.ComicNum = ComicNum;
		this.UrlAdd = "http://www.manhuagui.com/comic/" + ComicNum + "/" + "?" + (new Date()).getTime();
		AnalyHtml(GetHtml.GetInfo(this.UrlAdd));
		//TODO ������Ż�����������ͨ���������ֽ��������Ż�����Ҫ���Ǿ��عؼ���
		Collections.reverse(Chapter);
	}	
		
	//������ҳ���ݣ���ȡ�½ڱ����URL
	private void AnalyHtml(String HtmlInfo)
	{
		String Title = null;
		String Html = null;
		
		if(null == HtmlInfo) return;
		//ʹ��������ʽ����ƥ��
		String Rex = "<li><a href=\"/comic/" + ComicNum + "/(.+?)\" title=\"(.+?)\"";
		Pattern pattern = Pattern.compile(Rex);
		Matcher matcher = pattern.matcher(HtmlInfo);
		while(matcher.find())
		{
			Title = matcher.group(2);
			Html = "http://www.manhuagui.com/comic/" + ComicNum + "/" + matcher.group(1);
			Chapter.add(new Chapter(Title, Html));
		}
		
		return;
	}
	
	public ArrayList<Chapter> getChapter() {
		return Chapter;
	}
}
