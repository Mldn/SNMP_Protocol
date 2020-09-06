package Paket;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.io.IOException;
import java.util.*;

import com.ireasoning.protocol.snmp.SnmpConst;
import com.ireasoning.protocol.snmp.SnmpSession;
import com.ireasoning.protocol.snmp.SnmpVarBind;


public class Router extends Thread {
	private String[][] bgpT;
	private Main f;
	private boolean working;
	private int num;
	private int neighbours;
	private SnmpSession session ;
	private Panel p ;
	private Panel pp[];
	public volatile boolean panelReady;
	
	
	public static String[] oids = {".1.3.6.1.2.1.15.3.1.1", ".1.3.6.1.2.1.15.3.1.2",
			".1.3.6.1.2.1.15.3.1.4",".1.3.6.1.2.1.15.3.1.5",
			".1.3.6.1.2.1.15.3.1.9",".1.3.6.1.2.1.15.3.1.10",
			".1.3.6.1.2.1.15.3.1.11",".1.3.6.1.2.1.15.3.1.19",
			".1.3.6.1.2.1.15.3.1.24"};
	public static String ids[] = {"192.168.10.1" , "192.168.20.1", "192.168.30.1"};
	
	
	
	
	
	public Router(int b, Main f ) {
		this.num = b ;
		this.f = f;
		this.pp = f.getPanels();
		this.panelReady = false;
		this.working = true;
		try {
			session=new SnmpSession(ids[b],161,"si2019","si2019",SnmpConst.SNMPV2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		this.start();
	}
	
	public void run() {
		try {
		while(!interrupted()) {
			while(working) {
				
					this.panelReady = false;
					//p.removeAll();
					for(int j = 0 ; j < 9 ; j++) {
						//pp[j].validate();
						pp[j].removeAll();;
						SnmpVarBind[] pom;	
						pom = session.snmpGetTableColumn(oids[j]);
	    				int g = pom.length;
	    				this.neighbours = g;
						//this.bgpT = new String[this.neighbours][9];
	    				for(int k = 0 ; k < pom.length ;k++) {
	    					//this.bgpT[k][j] = pom[k].getValue().toString() ;
	    					//System.out.println(pom[k].getValue().toString());
	    					if(j == 1) {
	    						switch(pom[k].getValue().toString()) {
	    						case("1"):Label l = new Label("Idle"); pp[j].add(l);break;
	    						case("2"):Label l1 = new Label("Connect");pp[j].add(l1);break;
	    						case("3"):Label l2 = new Label("Active");pp[j].add(l2);break;
	    						case("4"):Label l3 = new Label("OpenSent");pp[j].add(l3);break;
	    						case("5"): Label l4 = new Label("OpenConfirm");pp[j].add(l4);break;
	    						case("6"):Label l5 = new Label("Established");pp[j].add(l5);break;
	    						}
	    					}else {
	    						Label l= new Label(pom[k].getValue().toString());
	    						pp[j].add(l);
	    					}
	    					//Label l = new Label(pom[k].getValue().toString());
	    					
	    					//this.sleep(500);
	    				}
	    			
	    				pp[j].validate();
	    			}
					this.panelReady = true;
					//f.repaint();
					//this.notifyAll();
					this.sleep(10000);
			}
		}
		
	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
			
		
}
	
	public String[][] getTable() {
		return this.bgpT;
	}
	
	public int numR() {
		return this.neighbours;
	}
	public Panel GetP() {
		return this.p;
	}
	
	public boolean GetPR() {
		return this.panelReady;
	}
	
	public void halt() {
		this.working = false;
	}
	public void cont() {
		this.working = true;
	}
	
	
	
}
