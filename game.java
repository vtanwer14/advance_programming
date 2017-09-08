//VIKAS TANWAR
//2016114

package ap_lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

class coordinate{
	int x; int y;
	coordinate(int p,int q){
		x=p; y=q;
	}
	public int overlap(ArrayList<knight> list, int j){
		for(int i=0;i<list.size();i++){
			if(i!=j){
				if(this.x==list.get(i).c.x && this.y==list.get(i).c.y)
					return i;
			}
		}
		return -1;
	}
}

class knight implements Comparable<knight>{
	String name;
	coordinate c;
	Stack<Object> box;
	knight(String s,int p,int q,Stack<Object> b){
		name=s;
		c=new coordinate(p,q);
		box=b;
	}
	public String getname(){
		return this.name;
	}
	public void updatepos(coordinate pos){
		c.x=pos.x;
		c.y=pos.y;
	}
	
	@Override 
	public int compareTo(knight k){
		return this.name.compareTo(k.getname());
	}
}

class NoException extends Exception {
	public NoException(String message) {
		super(message);
	}
}

class NonCoordinateException extends Exception {
	public NonCoordinateException(String message) {
		super(message);
	}
}

class StackEmptyException extends Exception {
	public StackEmptyException(String message) {
		super(message);
	}
}

class OverlapException extends Exception {
	public OverlapException(String message) {
		super(message);
	}
}

class QueenFoundException extends Exception {
	public QueenFoundException(String message) {
		super(message);
	}
}

public class game {
	
	public static boolean found(coordinate c1, coordinate c2){
		return (c1.x==c2.x && c1.y==c2.y);
	}

	public static void main(String[] args) throws FileNotFoundException,NoException,NonCoordinateException,
	StackEmptyException,OverlapException,QueenFoundException{
		// TODO Auto-generated method stub
		Scanner s=new Scanner(System.in);
		int nk=s.nextInt();
		int iterations=s.nextInt();
		int qx=s.nextInt();
		int qy=s.nextInt();
		coordinate queen=new coordinate(qx,qy);
		s.close();
		ArrayList<knight> list=new ArrayList<knight>();
		for(int i=1;i<=nk;i++){
			File f=new File("./src/ap_lab6/"+i+".txt");
			Scanner in=new Scanner(f);
			String name=in.nextLine();
			String[] pos=in.nextLine().split(" ");
			Stack<Object> box=new Stack<Object>();
			int n=Integer.parseInt(in.nextLine());
			for(int t=0;t<n;t++){
				String[] temp=in.nextLine().split(" ");
				if(temp[0].compareTo("Integer")==0){
					box.push(new Integer(Integer.parseInt(temp[1])));
				}
				else if(temp[0].compareTo("String")==0){
					box.push(new String(temp[1]));
				}
				else if(temp[0].compareTo("Float")==0){
					box.push(new Float(Float.parseFloat(temp[1])));
				}
				else if(temp[0].compareTo("Coordinate")==0){
					box.push(new coordinate(Integer.parseInt(temp[1]),Integer.parseInt(temp[2])));
				}
			}
			knight k=new knight(name,Integer.parseInt(pos[0]),Integer.parseInt(pos[1]),box);
			list.add(k);
			in.close();
		}
		
		Collections.sort(list);

		/*for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getname());
		}*/
		
		boolean flag=false;
		int iterate=1;
		

		PrintStream out=new PrintStream(new FileOutputStream("./src/ap_lab6/output.txt"));
		System.setOut(out);
		
		
		while(list.size()>0 && iterations>=iterate && !flag){
			for(int i=0;i<list.size();i++){
				try{
					System.out.println(iterate+" "+list.get(i).name+" "+list.get(i).c.x+" "+list.get(i).c.y);
					if(list.get(i).box.size()==0){
						list.remove(i);
						throw new StackEmptyException("StackEmptyException: Stack Empty exception");
					}
					else if(list.get(i).box.peek() instanceof Integer){
						throw new NonCoordinateException("NonCoordinateException: Not a coordinate Exception "+list.get(i).box.pop());
					}
					else if(list.get(i).box.peek() instanceof String){
						throw new NonCoordinateException("NonCoordinateException: Not a coordinate Exception "+list.get(i).box.pop());				
					}
					else if(list.get(i).box.peek() instanceof Float){
						throw new NonCoordinateException("NonCoordinateException: Not a coordinate Exception "+list.get(i).box.pop());
					}
					else if(list.get(i).box.peek() instanceof coordinate){
						coordinate temp=(coordinate)list.get(i).box.pop();
						if(found(queen,temp)){
							flag=true;
							throw new QueenFoundException("QueenFoundException: Queen has been Found. Abort!");
						}
						else if(temp.overlap(list,i)!=-1){
							String n=list.get(temp.overlap(list,i)).name;
							knight del=list.remove(temp.overlap(list,i));
							list.get(i).updatepos(del.c);
							throw new OverlapException("OverlapException: Knights Overlap Exception "+n);
						}
						else{
							list.get(i).updatepos(temp);
							throw new NoException("No exception "+temp.x+" "+temp.y);
						}
					}
				}
				catch (StackEmptyException e){
					System.out.println(e.getMessage());
				}
				catch (NonCoordinateException e){
					System.out.println(e.getMessage());
				}
				catch (QueenFoundException e){
					System.out.println(e.getMessage());
				}
				catch (OverlapException e){
					System.out.println(e.getMessage());
				}
				catch (NoException e){
					System.out.println(e.getMessage());
				}
				
				finally{
					if(flag){
						break;
					}
				}
				
			}
			iterate++;
		}
		
	}

}
