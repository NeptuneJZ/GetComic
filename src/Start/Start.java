package Start;

import java.util.ArrayList;

import GetComic.Chapter;
import GetComic.GetChapter;

public class Start {

	public static void main(String[] args)
	{
		//TODO ������UI����������
		ArrayList<Chapter> result = null;
		
		result = (new GetChapter("18008")).getChapter();
		for(Chapter ch:result)
		{
			System.out.println(ch.getTitle() + "," + ch.getHtml());
		}
	}
}
