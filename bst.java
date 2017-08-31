package ap_lab5;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
	
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}

class node<T> {
	T data;
	node<T> left;
	node<T> right;
	node(){	
		left=null; 
		right=null;
	}
	node(T d){
		data=d; 
		left=null; 
		right=null;
	}
	public void setleft(node<T> n){
		left=n;
	}
	public void setright(node<T> n){
		right=n;
	}
	public node<T> getleft(){
		return left;
	}
	public node<T> getright(node<T> n){
		return right;
	}
	public T getdata(){
		return data;
	}
}

public class bst<T extends Comparable<T>> {
	
	node<T> root;
	int size;
	
	bst(){
		root=null;
		size=0;
	}
	public void insert(T val){
		root=insert(root,val);
		size++;
	}
	private node<T> insert(node<T> root,T value){
		if(root==null)
			root=new node<T>(value);
		else{
			if(value.compareTo(root.getdata())<=0)
				root.left=insert(root.left,value);
    		else
    			root.right=insert(root.right,value);
		}
		return root;
	}
	public void display(node<T> root){
		if(root!=null){
			//System.out.print (root.data+" ");
			display(root.left);
			System.out.print(root.data+" ");
			display(root.right);
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Reader.init(System.in);
		bst<Integer> int_tree=new bst<Integer>();
		bst<String> str_tree=new bst<String>();
		bst<Float> float_tree=new bst<Float>();
		BSTFilesBuilder b=new BSTFilesBuilder();
		b.createBSTFiles(15, 10);
		File f=new File("./src/ap_lab5/3.txt");
		Scanner in=new Scanner(f);
		String x=in.nextLine();
		if(x.compareTo("Integer")==0){
			int t=Integer.parseInt(in.nextLine());
			for(int i=0;i<t;i++){
				int_tree.insert(Integer.parseInt(in.next()));
			}
		}
		else if(x.compareTo("Float")==0){
			int t=Integer.parseInt(in.nextLine());
			for(int i=0;i<t;i++){
				float_tree.insert(Float.parseFloat(in.next()));
			}
		}
		else if(x.compareTo("String")==0){
			int t=Integer.parseInt(in.nextLine());
			for(int i=0;i<t;i++){
				str_tree.insert(in.next());
			}
		}
		in.close();
		
	}

}
