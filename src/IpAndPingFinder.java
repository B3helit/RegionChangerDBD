package test;

import java.io.IOException;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IpAndPingFinder {
	private String ip;
	private String sa_east_link;
	private String us_east_2_link;
	private String us_east_1_link;
	private String us_west_1_link;
	private String us_west_2_link;
	private String eu_central_1_link;
	private String eu_west_1_link;
	private String eu_west_2_link;
	private String ap_southeast_1;
	private int bestAsiaServer;
	private boolean changed_eu_pings = false;
	private boolean changed_na_pings = false;
	private String[] links = new String[9];
	public boolean refreshed = false;
	
	public String lastLinkPingResult;
	private String pingCmd;
	private int bestEUServer; private int bestEUPing;
	private int worstEUServer; private int worstEUPing;
	private int worstNAServer; private int worstNAPing;
	private int secondWorstNAServer; private int bestNAPing;
	private int bestNAServer; private int BestNPPing;
	private String bestServer;
	private int[] pings = new int[9]; //public boolean[] blockPings = new boolean[9];
	public SinglyLinkedList<String> blockPingsEU = new SinglyLinkedList(); 
	public SinglyLinkedList<String> blockPingsNA = new SinglyLinkedList(); 
	public IpAndPingFinder() {
		this.sa_east_link = "gamelift.sa-east-1.amazonaws.com";
		// us //
		this.us_east_2_link = "gamelift.us-east-2.amazonaws.com";
		this.us_east_1_link = "gamelift.us-east-1.amazonaws.com";
		this.us_west_1_link = "gamelift.us-west-1.amazonaws.com";
		this.us_west_2_link = "gamelift.us-west-2.amazonaws.com";
		// eu //
		this.eu_central_1_link = "gamelift.eu-central-1.amazonaws.com";
		this.eu_west_1_link = "gamelift.eu-west-1.amazonaws.com";
		this.eu_west_2_link = "gamelift.eu-west-2.amazonaws.com"; // timed out
		this.ap_southeast_1 = "gamelift.ap-southeast-1.amazonaws.com";
		
		this.links[0] = sa_east_link;
		this.links[1] = us_east_2_link;
		this.links[2] = us_east_1_link;
		this.links[3] = us_west_1_link;
		this.links[4] = us_west_2_link;
		this.links[5] = eu_central_1_link;
		this.links[6] = eu_west_1_link;
		this.links[7] = eu_west_2_link;
		this.links[8] = ap_southeast_1;
		this.bestAsiaServer = 8;
		this.bestEUServer = 0; this.bestEUPing = 0;
		this.worstEUServer = 0; this.worstEUPing = 0;
		this.bestNAServer = 0; this.bestNAPing = 0;
		this.worstNAServer = 0; this.worstNAPing = 0;
		this.secondWorstNAServer = 0; this.BestNPPing = 0;
		this.ip = "";
		this.lastLinkPingResult = "";
		this.pingCmd = "";
		this.bestServer = "";
	}
	
	public int getBestPingEU() {
		return this.bestEUPing;
	}
	public int getBestPingNA() {
		return this.bestNAPing;
	}
	public int getBestPingNP() {
		return this.BestNPPing;
	}
	
	public String getBestRegion() {
		String txt = "";
		if(this.bestEUPing < this.bestNAPing && this.bestEUPing < this.bestNAPing) {
			txt = links[this.bestEUServer];
		}else {
			
		}
		int n = txt.charAt('.');
		txt = txt.substring(n, txt.length());
		n = txt.charAt('.');
		txt = txt.substring(0, n);
		return txt;
	}
	
	public Integer findAndSetAndGetPing(int num) {
		String ping = "";
		String empty = "";
		try {
            Runtime r = Runtime.getRuntime();
            pingCmd = "ping " + links[num]; 
            Process p = r.exec(pingCmd); // new process

            BufferedReader in = new BufferedReader(new
            InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                empty += inputLine;
            }
            in.close();
            
            //finding the ping
            if(empty.contains("Request timed out"))
            	ping = "1000";
            else {
            int numText1= empty.indexOf("Average")+10;
            String IndexE = empty.substring(numText1, empty.length());
            int numText2 = IndexE.indexOf("m");
            ping = IndexE.substring(0, numText2); // the ping
            }
            
            this.lastLinkPingResult = ping;
        } catch (IOException e) {
            System.out.println(e);
        }
		return Integer.parseInt(ping);
	}
	public String findAndSetAndGetIp(int num) {
		String empty = "";
		try {
            Runtime r = Runtime.getRuntime();
            pingCmd = "ping " + links[num]; 
            Process p = r.exec(pingCmd); // new process

            BufferedReader in = new BufferedReader(new
            InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                empty += inputLine;
            }
            in.close();          
            
            // finding the ip address
            int numText3 = empty.indexOf("[")+1;
            String IndexE2 = empty.substring(numText3, empty.indexOf("]"));
            this.ip = IndexE2;
        } catch (IOException e) {
            System.out.println(e);
        }
		return this.ip;
	}
	public String get_sa_east_link() {
		return this.sa_east_link;
	}
	
	public void refreshAllPings() {
		
		if(refreshed)
			return;
		
		
		// na // 
		int ping1 = findAndSetAndGetPing(1); pings[1] = ping1;
		int ping2 = findAndSetAndGetPing(2); pings[2] = ping2;
		int ping3 = findAndSetAndGetPing(3); pings[3] = ping3;
		int ping4 = findAndSetAndGetPing(4); pings[4] = ping4;
	    int arr[] = {ping1, ping2, ping3, ping4};
	    int minValue = Integer.MAX_VALUE;
	      
	    for(int i = 0;i < arr.length; ++i){
	    	minValue = Math.min(minValue, arr[i]);
	    }
	    this.bestNAPing = minValue;
	    
		// eu // 
		int ping5 = findAndSetAndGetPing(5); pings[5] = ping5;
		int ping6 = findAndSetAndGetPing(6); pings[6] = ping6;
		int ping7 = findAndSetAndGetPing(7); pings[7] = ping7;
		
	    int arr2[] = {ping5, ping6, ping7};
	    minValue = Integer.MAX_VALUE;
	      
	    for(int i = 0;i < arr2.length; ++i){
	    	minValue = Math.min(minValue, arr2[i]);
	    }
		this.bestEUPing = minValue;
		// highest asia // 
		int ping8 = findAndSetAndGetPing(8); pings[8] = ping8;
		this.BestNPPing = ping8;
		refreshed = true;
	}

	
	public String stringEU() {
		if(!changed_eu_pings) {
			//setBestandWorstPingsEU();
			changed_eu_pings = true;
			determining_NA_and_ASIA_Pings();
		}
		 		
		String text = "\n\n#Edited\n";
		blockPingsNA.moveToStart();
		while(blockPingsNA.curr!=null) {
			text+=ip + " " + blockPingsNA.curr.getElement() + "\n";
			blockPingsNA.curr=blockPingsNA.curr.getNext();
		}
				
		return text;
		
	}
	public String stringNA() {
		if(!changed_na_pings) {
			//setBestandWorstPingsNA();
			changed_na_pings = true;
			determining_EU_and_ASIA_Pings();
		}
		
		String text = "\n\n#Edited\n";
		blockPingsEU.moveToStart();
		while(blockPingsEU.curr!=null) {
			text+=ip + " " + blockPingsEU.curr.getElement() + "\n";
			blockPingsEU.curr=blockPingsEU.curr.getNext();
		}
		return text;
	}

	public void determining_EU_and_ASIA_Pings() {
		if(bestNAPing > pings[5])
			blockPingsEU.addLast(eu_central_1_link);
		if(bestNAPing > pings[6])
			blockPingsEU.addLast(eu_west_1_link);
		if(bestNAPing > pings[7])
			blockPingsEU.addLast(eu_west_2_link);
		if(bestNAPing > pings[8])
			blockPingsEU.addLast(ap_southeast_1);
	}
	public void determining_NA_and_ASIA_Pings() {
		//if(!changed_na_pings)
			
		if(bestEUPing > pings[1])
			blockPingsNA.addLast(us_east_2_link);

		if(bestEUPing > pings[2])
			blockPingsNA.addLast(us_east_1_link);

		if(bestEUPing > pings[3])
			blockPingsNA.addLast(us_west_1_link);

		if(bestEUPing > pings[4])
			blockPingsNA.addLast(us_west_2_link);
		
		if(bestEUPing > pings[8])
			blockPingsNA.addLast(ap_southeast_1);		
		
	}
}
