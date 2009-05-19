import javax.swing.JFrame;




/**
 * This class contains the server side gui code. It is written in Java Swing.
 * @author jmcelroy
 *
 */
public class ServerGUI extends JFrame {

	private String serverName = "";
	private String folderName = "";
	
	public ServerGUI(){
		this.setTitle("PineApple Server");
		
		this.setSize(300,300);
		
		this.add(setUpGui());
		
		
	}
	
	
	public JPanel setUpGui(){
		
	}
	
	
	public String getServerName(){
		return serverName;
	}
	
	public String getFolderName(){
		return folderName;
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
