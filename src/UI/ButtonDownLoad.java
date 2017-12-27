package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import DownLoad.DLControl;

@SuppressWarnings("serial")
public class ButtonDownLoad extends JButton{

	private DLControl dlc = new DLControl();
	private SelectThread select = new SelectThread();
	
	private boolean DLState = false;

	public ButtonDownLoad()
	{
		this.setText("����");
		this.setEnabled(false);
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("����"))
				{
					dlc.setPoolSize((int)Math.pow(2, select.getSelectedIndex()) * 4);
					ButtonDownLoad.this.setEnabled(false);
					ButtonDownLoad.this.setText("������");
					DLState = true;
					//dlc.ThreadControl();
				}
				else if(!ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("����"))
				{
					JOptionPane.showMessageDialog(null, "���ȷ����Ի�ȡ�½���Ϣ", "����˵��", JOptionPane.CLOSED_OPTION);
				}
				else if(!ButtonDownLoad.this.isEnabled() && ButtonDownLoad.this.getText().equals("������"))
				{
					JOptionPane.showMessageDialog(null, "����������ô�㣬Ҳû�취�ӿ������ٶ�ѽ-_-!", "����˵��", JOptionPane.CLOSED_OPTION);
				}
			}
		});
	}

	public DLControl getDlc() {
		return dlc;
	}

	public boolean getDLState() {
		return DLState;
	}

	public SelectThread getSelect() {
		return select;
	}
}
