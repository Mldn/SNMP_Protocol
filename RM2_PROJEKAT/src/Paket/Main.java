package Paket;
import com.ireasoning.protocol.*;
import com.ireasoning.protocol.snmp.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;

import com.ireasoning.protocol.snmp.*;

public class Main extends Frame {
	
	private Router r;
	private int Rnum;
	private Menu menu = new Menu("Router");
	private String[] op = {"R1", "R2", "R3"} ;
	private Panel pp ;
	private Panel[] ppp;
	
	
	public Label imeRutera= new Label();
	public Main() {
		super("SNMPapp") ;
		//r = new Router(0);
		//Rnum = 0;
		setSize(1050 , 600) ;
		Panel p = new Panel(new GridLayout(1,9));
		add(p,"North");
		p.add(new Label("PeerIdentifier"));
		p.add(new Label("PeerState"));
		p.add(new Label("BGPversion"));
		p.add(new Label("IpAddress"));
		p.add(new Label("AS"));
		p.add(new Label("InMessNum"));
		p.add(new Label("OutMessNum"));
		p.add(new Label("KeepAliveTime"));
		p.add(new Label("ElapsedTime"));
		
		pp = new Panel(new GridLayout(1,9));
		add(pp,"Center");
		add(imeRutera,BorderLayout.SOUTH);
		
		
		
		ppp = new Panel[9];
		for(int i = 0 ; i < 9 ;i ++) {
				ppp[i] = new Panel(new GridLayout(0,1));
		}
			
		for (int i =0 ; i < 9 ; i++) {
			for(int j = 0; j < 2 ;j ++){
					Label l = new Label("****");
					ppp[i].add(l);
					pp.add(ppp[i]);
			}
		}
		
		
		
		
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent dog) {
				r.interrupt();
				dispose() ;
			}
		}) ;
		setVisible(true);
		addMenu() ;
		
		
		
		
		
	}
	
	public Panel[] getPanels() {
		return this.ppp;
	}
	
	
	public void addMenu() {
		Main t=this;
		ActionListener osl1 = new ActionListener() {
			public void actionPerformed(ActionEvent dog) {
				MenuItem stv = (MenuItem)dog.getSource();
				if(r != null) r.interrupt();
				String s = stv.getLabel();
				imeRutera.setBackground(Color.GREEN);
				imeRutera.setText(s);
				if( s == "R1") {
					r = new Router(0,t);
					Rnum = 0;
				}else if(s == "R2") {
					r = new Router(1, t);
					Rnum = 1;
					
				}else if(s== "R3") {
					Rnum = 2;
					r = new Router(2,t);
					
				}
			}

			
		};
		
		MenuBar mb = new MenuBar();
		setMenuBar(mb) ;
		mb.add(menu) ;
		for(String v : op) {
			MenuItem st = new MenuItem(v);
			menu.add(st) ;
			st.addActionListener(osl1);
		}
		
		
	}
	
	private void Update() {
		synchronized(r) {
			while(!r.GetPR())
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			this.remove(pp);
			pp = r.GetP();
			add(pp,"Center");
			this.repaint();
		}
		
	}
	

	
	public static void main(String args[]) {
		SnmpSession.loadMib2();
		Main m = new Main();
		
	}
	
	
}
