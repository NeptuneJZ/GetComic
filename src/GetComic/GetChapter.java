package GetComic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import Config.ValidConfig;
//�������ڻ�ȡ�������½����͸��½ڵ�URL
public class GetChapter {
	private ArrayList<Chapter> Chapter = new ArrayList<Chapter>();
	private String UrlAdd = null;
	private String ComicNum = null;
	private String BookName = null;
	
	public GetChapter(String ComicNum)
	{
		this.ComicNum = ComicNum;
		this.UrlAdd = "http://www.manhuagui.com/comic/" + ComicNum + "/" + "?" + (new Date()).getTime();
		AnalyHtml(GetHtml.GetInfo(this.UrlAdd));
		//TODO ������Ż�����������ͨ���������ֽ��������Ż�����Ҫ���Ǿ��عؼ���
		Collections.reverse(Chapter);
	}	
		
	//������ҳ���ݣ���ȡ�½ڱ����URL�Լ�����
	private void AnalyHtml(String HtmlInfo)
	{
		String Title = null;
		String Html = null;
		
		if(null == HtmlInfo) return;
		
		String BookNameRex = "name: '(.+?)'";
		Pattern pattern = Pattern.compile(BookNameRex);
		Matcher matcher = pattern.matcher(HtmlInfo);
		
		while(matcher.find())
		{
			BookName = matcher.group(1);
		}
		
		//��������һ������վ�϶����½���ϢҲ������base64ѹ������Ҫ�Ƚ��н�ѹ��
		if(HtmlInfo.indexOf("__VIEWSTATE") != -1)
		{
			String Rex = "VIEWSTATE\" value=\"(.+?)\"";
			String keyword = null;
			pattern = Pattern.compile(Rex);
			matcher = pattern.matcher(HtmlInfo);
			
			while(matcher.find())
			{
				keyword = matcher.group(1);
			}
			
			ScriptEngineManager enginemanager = new ScriptEngineManager();
			ScriptEngine engine = enginemanager.getEngineByName("js");
			try {
				engine.eval(ValidConfig.JSFile);
				Invocable inv = (Invocable)engine;
				HtmlInfo = (String)inv.invokeFunction("getChapter", keyword);			
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		//ʹ��������ʽ����ƥ��
		String Rex = "<li><a href=\"/comic/" + ComicNum + "/(.+?)\" title=\"(.+?)\"";
		pattern = Pattern.compile(Rex);
		matcher = pattern.matcher(HtmlInfo);
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
	
	public String getBookName()
	{
		return this.BookName;
	}
}
