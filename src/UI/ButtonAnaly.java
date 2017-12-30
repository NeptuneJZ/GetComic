package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ButtonAnaly extends JButton{
	private PanelControl pc= null;
	
	public void setControl(PanelControl pc)
	{
		this.pc = pc;
	}
	
	public ButtonAnaly()
	{
		this.setText("����");
		this.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(ButtonAnaly.this.isEnabled())
				{	
					pc.StartAnaly(ButtonAnaly.this);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "���ڷ����ţ�Ī��~~", "˵��", JOptionPane.CLOSED_OPTION);
				}
			}
		});
	}
}
