package UI;

import Config.ValidConfig;
import GetComic.Chapter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class FrameComic extends JFrame{
	private JPanel JpScroll = null;
	private LinkedHashMap<Chapter, JCheckBox> Boxs = new LinkedHashMap<Chapter, JCheckBox>();
	CheckBoxAll CheckAll = null;
	private int TaskNum = 0;
	PanelControl pc = null;
	JScrollPane scrollpane = null;
	
	public FrameComic(PanelControl pc)
	{
		this.pc = pc;
		initLayout();
	}
	
	private void initLayout()
	{
		this.setTitle("һ����ȡ��������");
		this.setResizable(false);
		//��ȡ�����ֵĶ�ȡ��ʽ��Ϊ����JAR���ܶ�ȡ��img
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("Comic.png")));
		this.setSize(ValidConfig.ComicW, ValidConfig.ComicH);
		this.setLocation((ValidConfig.WinW - ValidConfig.ComicW) / 2, (ValidConfig.WinH - ValidConfig.ComicH) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		con.setBackground(new Color(144, 238, 144));
		con.setLayout(null);
		con.add(pc);
		
		setScrollUI(con);
		setFileUI(con);
		setHelpUI(con);
	}
	
	private void setHelpUI(Container con)
	{	
		JTextArea ta = new JTextArea();
		ta.setEditable(false);
		ta.setLineWrap(true);
		ta.setFont(new Font("����", Font.PLAIN, 15));
		ta.setDisabledTextColor(Color.BLACK);
		ta.setText("�����ֻ��ץȡ��http://www.manhuagui.com/����վ����������(���ı����ڿ�����CTRL+C���и���)��\n" + 
				"1.����ID��ʲô��\n" + 
				"����http://www.manhuagui.com/��վ������������Ҫ�������������������ҳ�档����http://www.manhuagui.com/comic/1626/���������������ID����1626��\n"
				+ "���⼴ʹ����ҳ��ʾ���Ȩ�������ε�����Ҳ������һ��Ŷ�����мǲ�Ҫһ��֮�����ع�����౻�������������ױ���վ��̨���ֶ���IP��\n" + 
				"2.����ʧ�ܺ�����ж��ǲ��Ǳ���IP�ˣ���ν����\n" + 
				"���Գ�����������ϴ�http://www.manhuagui.com/����������޷����룬�����ʹ������ֻ���ע���벻Ҫʹ�ú͵���һ����wifi���磬ʹ���������������������磩���Ե�¼����վ������ֻ����Խ�������Բ��У���˵��ȷʵ����IP�ˡ�\n" + 
				"��IP��Ҫ���ţ�������ǲ����������û�����ô���²��žͿ��Ի�ȡ����IP��������ǹ����û�������Ҫ�ϵ�����һ�¹�ǥ���ӡ�\n" + 
				"3.����˵��\n" + 
				"1������������ز��������������һֱû�����������ж�һ��Ȼ����������أ��Ⲣ�������������������ļ������ǻ����½����������أ�������Ѹ�׵��������û�ٶ�����һ�¿��ܻ��һ���ĵ��������ܻ�ӿ������ٶ�\n" + 
				"2������жϺ���ѡ���½ڽ����ǻ�ɫ�ģ���ʱ�����ֱ�ӹرճ���Ȼ�����´����·������أ�ֻҪ�㲻�Ķ�����λ��Ҳ��ɾ���������ļ�����ô��������������ͬ����ֱ�������������������ء�");
		
		JScrollPane sp = new JScrollPane(ta);
		sp.setBounds(5, 450, 430, 115);
		con.add(sp);
	}
	
	private void setFileUI(Container con)
	{
		JPanel filepanel = new JPanel();
		filepanel.setBounds(5, 45, 430, 30);
		filepanel.setBackground(new Color(144, 238, 144));
		filepanel.setLayout(null);
		
		filepanel.add(pc.getTextFileText());
		filepanel.add(pc.getButtonFilePath());
			
		con.add(filepanel);
		
	}
	
	private void setScrollUI(Container con)
	{
		JpScroll = new JPanel();
		JpScroll.setLayout(new BoxLayout(JpScroll, BoxLayout.Y_AXIS));
		scrollpane = new JScrollPane(JpScroll);
		scrollpane.getVerticalScrollBar().setUnitIncrement(15);
		scrollpane.setBounds(5, 80, 430, 365);				
		con.add(scrollpane);		
	}
	
	public void addChapters(ArrayList<Chapter> Chapters)
	{
		JpScroll.removeAll();
		scrollpane.getVerticalScrollBar().setValue(0);
		Boxs.clear();
		CheckAll = new CheckBoxAll("ȫѡ", this);
		JpScroll.add(CheckAll);
		for(Chapter chapter : Chapters)
		{
			JCheckBox tmp = new JCheckBox(chapter.getTitle());
			tmp.setSelected(true);
			JpScroll.add(tmp);
			Boxs.put(chapter, tmp);
		}
		JpScroll.updateUI();
	}
	
	public ArrayList<Chapter> getDLInfo()
	{
		this.TaskNum = 0;
		ArrayList<Chapter> info = new ArrayList<Chapter>();
		Set<Chapter> keys = Boxs.keySet();
		for(Chapter key : keys)
		{
			JCheckBox cb = Boxs.get(key);
			if(cb.isSelected() && cb.isEnabled())
			{
				info.add(key);
				TaskNum ++;
			}
		}
		
		return info;
	}
	
	public void SelectAll()
	{
		Set<Chapter> keys = Boxs.keySet();
		for(Chapter key : keys)
		{
			Boxs.get(key).setSelected(true);
		}
		
		JpScroll.updateUI();
		return;
	}
	
	public void DeSelectAll()
	{
		Set<Chapter> keys = Boxs.keySet();
		for(Chapter key : keys)
		{
			Boxs.get(key).setSelected(false);
		}
		
		JpScroll.updateUI();
		return;
	}
	
	public void disableAllbox()
	{
		Set<Chapter> keys = Boxs.keySet();
		CheckAll.setEnabled(false);
		for(Chapter key : keys)
		{
			Boxs.get(key).setEnabled(false);;
		}
		
		JpScroll.updateUI();
		return;
	}
	
	public void UpdateDLinfo(Chapter chapter,int curr,int all)
	{
		JCheckBox box = Boxs.get(chapter);
		String currtext = box.getText();
		int index = currtext.indexOf("�����չ:");
		if(-1 != index)
		{
			currtext = currtext.substring(0, index - 1);
		}

		box.setText(currtext + " �����չ:" + curr + "/" + all);
		box.updateUI();
	}
	
	public void FinishDl(Chapter chapter, int type)
	{
		TaskNum --;
		JCheckBox box = Boxs.get(chapter);
		String currtext = box.getText();
		int index = currtext.indexOf("�����չ:");
		if(-1 != index)
		{
			currtext = currtext.substring(0, index - 1);
		}
		
		if(type == 1)
		{
			box.setText(currtext + " ��������~");
			box.setForeground(Color.RED);
		}
		else if(type == 0)
		{
			box.setText(currtext + " �����չ:����ʧ��,���Գ����ٴ����أ�����Ӱ������ͼƬ" );
		}
		
		box.updateUI();
		if(TaskNum == 0)
		{
			pc.ResetCompnent();
			ActiveAllbox();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "�������^_^", "˵��", JOptionPane.CLOSED_OPTION);
		}
	}
	
	public void ActiveAllbox()
	{
		Set<Chapter> keys = Boxs.keySet();
		CheckAll.setEnabled(true);
		for(Chapter key : keys)
		{
			if(Boxs.get(key).getForeground() == Color.RED)
			{
				continue;
			}
			
			Boxs.get(key).setEnabled(true);
		}
		
		JpScroll.updateUI();
		return;
	}
	
	public void InterrputDL()
	{
		ActiveAllbox();
		this.TaskNum = 0;
	}
}

