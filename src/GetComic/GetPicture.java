package GetComic;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import Start.Start;

public class GetPicture {
	private ArrayList<String> PicturePath = new ArrayList<String>();
	
	public GetPicture(String UrlAdd)
	{
		GetJsInfo(GetHtml.GetInfo(UrlAdd));
	}
	/*����ҳ�����л�ȡ������Ҫ���ڽ����JS������������ʹ����2��ѹ����
	 * 1.eval��������ִ�еľ���һ��parkerѹ��;
	 * 2.����parkerѹ���ĵ��ĸ����Ҳ����k,��Ҫ�Ƚ���base64��ѹ��;
	 * ���������Ŀ�ľ���Ϊ�˻�ȡ������Ҫ����ز���*/
	private void GetJsInfo(String HtmlInfo)
	{
		if(null == HtmlInfo) return;
		
		String Para1 = null,Para2 = null,Para3 = null,Para4 = null,Para5 = null;
		String Base64StringMatch = "\\(function\\(p,a,c,k,e,d\\)\\{(.+?)\\}\\('(.+?)',(.+?),(.+?),'(.+?)'(.+?),(.+?),";
		
		Pattern pattern = Pattern.compile(Base64StringMatch);
		Matcher match = pattern.matcher(HtmlInfo);
		
		while(match.find())
		{
			Para1 = match.group(2);
			Para2 = match.group(3);
			Para3 = match.group(4);
			Para4 = match.group(5);
			Para5 = match.group(7);
		}
		
		if(null == Para1)
		{
			return;
		}
		
		paraseInfo(Para1,Integer.parseInt(Para2), Integer.parseInt(Para3), Para4,Integer.parseInt(Para5));
		
		return;
	}
	//�˴�ֱ�ӵ����޸���ԴHTML�Ľű������н���
	private void paraseInfo(String para1, int para2, int para3, String para4, int para5)
	{
		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("js");
		try {
			engine.eval(new FileReader(Start.class.getResource("/config.js").toURI().getPath()));
			Invocable inv = (Invocable)engine;
			String ParkerInfo = (String)inv.invokeFunction("parase", para1,para2,para3,para4,para5);
			getPath(ParkerInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	//���ݽ�������ȡ��ͼƬ��URL��ַ
	private void getPath(String ParkerInfo)
	{
		String path = null;
		String[] files = null;
		String MatchString = "files\":\\[(.+?)\\],(.+?)path\":\"(.+?)\"";
		Pattern pattern = Pattern.compile(MatchString);
		Matcher match = pattern.matcher(ParkerInfo);
		
		while(match.find())
		{
			path = match.group(3);
			files = match.group(1).replaceAll("\"","").replaceAll(".webp", "").split(",");
		}
		for(String file:files)
		{
			PicturePath.add("http://i.hamreus.com:8080" + path + file);
		}
	}
	
	public ArrayList<String> getPicturePath() {
		return PicturePath;
	}
}
