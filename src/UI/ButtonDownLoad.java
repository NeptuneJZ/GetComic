package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Config.ValidConfig;

@SuppressWarnings("serial")
public class ButtonDownLoad extends JButton{
	private boolean DLState = false;
	private PanelControl pc= null;
	
	public void setControl(PanelControl pc)
	{
		this.pc = pc;
	}
	
	public ButtonDownLoad()
	{
		this.setText("����");
		this.setEnabled(false);
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("����"))
				{
					ButtonDownLoad.this.setText("�ж�");
					DLState = true;
					ValidConfig.RunThread = true;
					boolean result = pc.StartDL(ButtonDownLoad.this);
					if(false == result)
					{
						ButtonDownLoad.this.setEnabled(true);
						ButtonDownLoad.this.setText("����");
						DLState = false;
					}					
				}
				else if(!ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("����"))
				{
					JOptionPane.showMessageDialog(null, "���ȷ����Ի�ȡ�½���Ϣ", "����˵��", JOptionPane.CLOSED_OPTION);
				}
				else if(!ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("������"))
				{
					JOptionPane.showMessageDialog(null, "����������ô�㣬Ҳû�취�ӿ������ٶ�ѽ-_-!", "����˵��", JOptionPane.CLOSED_OPTION);
				}
				else if(ButtonDownLoad.this.getText().equals("�ж�"))
				{			
					pc.InterrputDL();
				}	
			}
		});
	}

	public boolean getDLState() {
		return DLState;
	}
	
	public void resetDLStat()
	{
		this.DLState = false;
	}
}
