//VIKAS TANWAR
//2016114

package ap_lab7;

import java.io.*;
import java.util.*;

class song implements Serializable{
	public static final long serialVersionUID = 1;
	String name;
	String singer;
	int time;
	song(){
		name=""; singer=""; time=0;
	}
	song(String n,String s,int t){
		name=n; singer=s; time=t;
	}
	public String toString(){
		return(this.name+" by "+this.singer+" is "+this.time+" seconds long.");
	}
}
class playlist implements Serializable{
	public static final long serialVersionUID = 2;
	ArrayList<song> list=new ArrayList<song>();
}

public class App {
	
	public static void serialize(playlist p,String l) throws IOException{
		ObjectOutputStream out =null;
		try{
			out=new ObjectOutputStream(new FileOutputStream("./src/ap_lab7/"+l+".bin"));
			out.writeObject(p);
		}
		finally{
			out.close();
		}
	}
	
	public static void deserialize(String l) throws IOException, ClassNotFoundException{
		ObjectInputStream in=null;
		boolean flag=false;
		try{
			in=new ObjectInputStream(new FileInputStream("./src/ap_lab7/"+l+".bin"));
			playlist c=(playlist)in.readObject();
			flag=true;
			for(int i=0;i<c.list.size();i++){
				System.out.println(c.list.get(i));
			}
		}
		finally{
			if(!flag || in==null){
				System.out.println("No Song Exist!");
			}
			in.close();
		}
	}
	
	public static void main(String[] args) throws IOException,ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		Scanner s=new Scanner(System.in);
		String[] ps = (new File("./src/ap_lab7")).list();
		//System.out.print("PLAYLISTS IN CURRENT DIRETORY\n");
		for(String k: ps) {
			String[] t=k.split("\\.");
			if(t[1].compareTo("bin")==0){
				System.out.print(t[0]+"      ");
			}
		}
		//System.out.print("\n\nEnter name of the playlist: ");
		String l=s.nextLine();
		String input=l+".bin";
		for(String path: ps) {
			if(input.compareTo(path)==0){
				ObjectInputStream in=null;
				try{
					in=new ObjectInputStream(new FileInputStream("./src/ap_lab7/"+input));
					playlist c=(playlist)in.readObject();
					System.out.println(/*"\nNumber of songs in the playlist: "+*/c.list.size());
					//boolean flag;
					//String e="n";
					//do{
						//flag=true;
						//System.out.println("\nMENU\n");
						//System.out.println("1.Add      2.Delete      3.Search      4.Show      5.Back To Menu      6.Exit\nEnter Choice: ");
						int choice=s.nextInt();
						switch(choice){
							case 1://System.out.print("\nName: ");
								//s.wait();
							       String a=s.nextLine();
							       //System.out.print("\nSinger: ");
							       String b=s.nextLine();
							       //System.out.print("\nDuration (in seconds): ");
							       int d=s.nextInt();
							       c.list.add(new song(a,b,d));
							       System.out.println(c.list.size());
							       serialize(c,l);
								break;
							case 2://System.out.print("\nEnter name of the song to be deleted: ");
						           String p=s.nextLine();
						           for(int i=0;i<c.list.size();i++){
						        	   if(c.list.get(i).name.compareTo(p)==0){
						        		   c.list.remove(i);
						        		   break;
						        	   }
						           }
						           serialize(c,l);
								break;
							case 3://System.out.print("\nEnter name of the song to be searched: ");
								   String temp=s.nextLine();
								   boolean found=false;
								   for(int i=0;i<c.list.size();i++){
									   if(c.list.get(i).name.compareTo(temp)==0){
										   found=true;
										   System.out.println(c.list.get(i).toString());
										   break;
									   }
								   }
								   if(!found){
									   System.out.print("Song not found!");
								   }
								break;
							case 4:deserialize(l);
								break;
							case 5:for(String k: ps) {
										String[] t=k.split("\\.");
										if(t[1].compareTo("bin")==0){
											System.out.print(t[0]+"      ");
										}
									}
								break;
							case 6:;
								break;
							default:;
									break;
						}
					//}while(!flag || e.compareTo("y")==0);
				}
				finally{
					in.close();
				}
			}
		}
		s.close();
	}
}