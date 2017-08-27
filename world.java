//VIKAS TANWAR
//2016114

package ap_lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;


/** Class for buffered reading int and double values */
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

class animal{
	String name;
	float health;	
	int x; int y;
	int timestamp; 
	int turn;
	animal(float a,int b,int c,int d,String n){
		this.health=a;
		this.x=b;
		this.y=c;
		this.timestamp=d;
		name=n;
	}
	public boolean isdead(){
		if(this.health>0){
			return false;
		}
		return true;
	}
	public void inchealth(float n){
		this.health+=n;
	}
	public void dechealth(float n){
		this.health-=n;
	}
	public animal nearest(animal a,animal b){
		if(Math.sqrt(((this.x-a.x)*(this.x-a.x))+((this.y-a.y)*(this.y-a.y)))>=Math.sqrt(((this.x-a.x)*(this.x-a.x))+((this.y-a.y)*(this.y-a.y)))){
			return a;
		}
		return b;
	}
	public grassland nearestgrass(grassland a, grassland b){
		if(Math.sqrt(((this.x-a.x)*(this.x-a.x))+((this.y-a.y)*(this.y-a.y)))>=Math.sqrt(((this.x-a.x)*(this.x-a.x))+((this.y-a.y)*(this.y-a.y)))){
			return a;
		}
		return b;
	}
	public boolean ingrassland(grassland a){
		if(Math.sqrt(((this.x-a.x)*(this.x-a.x))+((this.y-a.y)*(this.y-a.y)))>a.radius){
			return false;
		}
		return true;
	}
}

class herbivore extends animal{
	int capacity;
	herbivore(float a,int b ,int c,int d,int e,String n){
		super(a,c,d,e,n);
		this.capacity=b;
	}
	public void movetograss(int n,grassland g){
		int x1=this.x;
		int y1=this.y;
		int x2=g.x;
		int y2=g.y;
		int t1=n;
		double length=Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
		double t2=length-t1;
		double x=((t1*x2)+(t2*x1))/(t1+t2);
		double y=((t1*y2)+(t2*y1))/(t1+t2);
		this.x=Math.round((float)(x));
		this.y=Math.round((float)(y));
	}
	public void moveawaycarnivore(int n,carnivore c){
		int x1=this.x;
		int y1=this.y;
		int x2=c.x;
		int y2=c.y;
		int t1=n;
		double length=Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
		double t2=length+t1;
		double x=((t1*x2)-(t2*x1))/(t1-t2);
		double y=((t1*y2)-(t2*y1))/(t1-t2);
		this.x=Math.round((float)(x));
		this.y=Math.round((float)(y));
	}
}

class carnivore extends animal{
	carnivore(float a,int c,int d,int e,String n){
		super(a,c,d,e,n);
	}
	public void movetoherbivore(int n,animal g){
		int x1=this.x;
		int y1=this.y;
		int x2=g.x;
		int y2=g.y;
		int t1=n;
		double length=Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
		double t2=length-t1;
		double x=((t1*x2)+(t2*x1))/(t1+t2);
		double y=((t1*y2)+(t2*y1))/(t1+t2);
		this.x=Math.round((float)(x));
		this.y=Math.round((float)(y));
	}
}

class grassland{
	int x; int y;
	int radius;
	int grass;
	grassland(int p,int q,int r,int g){
		this.x=p;
		this.y=q;
		this.radius=r;
		this.grass=g;
	}
	public boolean ifgrassavailable(){
		if(this.grass>0){
			return true;
		}
		return false;
	}
	public void reducegrass(int n){
		this.grass=n;
	}
}

 class node{
	animal a;
	node link;
	node(){
		a=null;
	}
	node(animal c1){
		a=c1;
	}
	public void setlink(node n){
        link = n;
    }
    public node getlink(){
    	return link;
    }
}

class list{
    node start;
    node end ;
    int size ;
    list(){
        start = null;
        end = null;
        size = 0;
    }

    public void insert(animal c){
    	size++;
    	node nptr = new node(c);
    	if(start == null){
            start = nptr;
            end = start;
        }
    	else{
    		if(start.a.timestamp>=nptr.a.timestamp){
    			nptr.setlink(start);
    			start=nptr;
    		}
    		else if(end.a.timestamp<=nptr.a.timestamp){
    			end.setlink(nptr);
    			end=end.getlink();
    		}
    		else{
    			node temp=start;
    			while(temp.getlink()!=null){
    				if(temp.a.timestamp<nptr.a.timestamp&&temp.getlink().a.timestamp>nptr.a.timestamp){
    	    			nptr.setlink(temp.getlink());
    	    			temp.setlink(nptr);
    	    			break;
    	    		}
    				temp=temp.getlink();
    			}
    		}
    	}
    }
    
    public animal delete(){
    	if(size<=0){
    		return null;
    	}
    	size--;
    	animal temp=new animal(0,0,0,0,"");
    	temp=start.a;
    	if(size==0){
    		start=end=null;
    	}
    	start=start.getlink();
    	return temp;
    }
    
    public void deleteAtPos(int ts){
    	if(size<=0){
    		return;
    	}
        if (start.a.timestamp == ts){
            start = start.link;
            size--;
            return ;
        }
        else if (end.a.timestamp == ts) 
        {
            node s = start;
            node t = start;
            while (s != end)
            {
                t = s;
                s = s.link;
            }
            end = t;
            end.setlink(null);
            size--;
            return;
        }
        node ptr = start;
        node temp=start;
        while(ptr.getlink()!=null && ptr.a.timestamp!=ts){
        	temp=ptr;
        	ptr=ptr.getlink();
        }
        temp.setlink(ptr.getlink());
        ptr.setlink(null);
        size--;
    }
    
    public void display(){
    	while(start!=null){
    		System.out.println(start.a.timestamp);
    		start=start.getlink();
    	}
    }

}


public class world {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Reader.init(System.in);
		list l=new list();
		int max=0;
		//System.out.println("Enter Total Final Time for Simulation: ");
		int time=Reader.nextInt();
		//System.out.println("Enter x, y centre, radius and Grass Available for First Grassland: ");
		int x=Reader.nextInt();
		int y=Reader.nextInt();
		int rad=Reader.nextInt();
		int grass=Reader.nextInt();
		grassland g1=new grassland(x,y,rad,grass);
		//System.out.println("Enter x, y centre, radius and Grass Available for Second Grassland: ");
		x=Reader.nextInt();
		y=Reader.nextInt();
		rad=Reader.nextInt();
		grass=Reader.nextInt();
		grassland g2=new grassland(x,y,rad,grass);
		//System.out.println("Enter Health and Grass Capacity for Herbivores: ");
		float a=Reader.nextInt();
		int b=Reader.nextInt();
		//System.out.println("Enter x, y position and timestamp for First Herbivore: ");
		int c=Reader.nextInt();
		int d=Reader.nextInt();
		int e=Reader.nextInt();
		if(e>max)
			max=e;
		animal h1=new herbivore(a,b,c,d,e,"First Herbivore");
		//System.out.println("Enter x, y position and timestamp for Second Herbivore: ");
		c=Reader.nextInt();
		d=Reader.nextInt();
		e=Reader.nextInt();
		if(e>max)
			max=e;
		animal h2=new herbivore(a,b,c,d,e,"Second Herbivore");
		//System.out.println("Enter Health for Carnivores: ");
		a=Reader.nextInt();
		//System.out.println("Enter x, y position and timestamp for First Carnivore: ");
		c=Reader.nextInt();
		d=Reader.nextInt();
		e=Reader.nextInt();
		if(e>max)
			max=e;
		animal c1=new carnivore(a,c,d,e,"First Carnivore");
		//System.out.println("Enter x, y position and timestamp for Second Carnivore: ");
		c=Reader.nextInt();
		d=Reader.nextInt();
		e=Reader.nextInt();
		if(e>max)
			max=e;
		animal c2=new carnivore(a,c,d,e,"Second Carnivore");
		l.insert(h1);
		l.insert(h2);
		l.insert(c1);
		l.insert(c2);
		int maxtime=time;
		while(l.size>0&&time-->0){
			animal temp=l.delete();
			System.out.println("It is "+temp.name);
			if(temp instanceof herbivore){
				int rand=(int)(Math.random()*100);
				if(rand<=50){
					if(!temp.ingrassland(g1)&&!temp.ingrassland(g2)){
						temp.turn++;
					}
					if(c1.isdead()&&c2.isdead()&&!temp.ingrassland(g1)&&!temp.ingrassland(g2)){
						((herbivore) temp).movetograss(5,temp.nearestgrass(g1, g2));
						if(temp.ingrassland(g1)||temp.ingrassland(g2)){
							temp.turn=0;
						}
						if(temp.turn>7){
							temp.dechealth(5);
						}
					}
				}
				else if(temp.ingrassland(g1)){
					temp.dechealth(25);
					((herbivore) temp).movetograss(5, g2);
				}
				else if(temp.ingrassland(g2)){
					temp.dechealth(25);
					((herbivore) temp).movetograss(5, g1);
				}
				else if(!temp.ingrassland(g1)&&!temp.ingrassland(g2)){
					rand=(int)(Math.random()*100);
					if(temp.turn>7){
						temp.dechealth(5);
					}
					else{
						rand=(int)(Math.random()*100);
						if(rand<=65){
							((herbivore) temp).movetograss(5,temp.nearestgrass(g1, g2));
						}
						else{
							((herbivore) temp).moveawaycarnivore(4,(carnivore)temp.nearest(c1,c2));
						}
					}
				}
				else if(temp.ingrassland(g1)||temp.ingrassland(g2)){
					temp.turn=0;
					if(temp.ingrassland(g1)){
						if(g1.grass>=((herbivore)temp).capacity){
							rand=(int)(Math.random()*100);
							if(rand<=90){
								//stay
								temp.inchealth((1/2)*temp.health);
							}
							else{
								rand=(int)(Math.random()*100);
								temp.dechealth(25);
								if(rand<=50){
									((herbivore) temp).moveawaycarnivore(2,(carnivore)temp.nearest(c1,c2));
								}
								else{
									((herbivore) temp).movetograss(3,temp.nearestgrass(g1, g2));
								}
							}
						}
						else{
							rand=(int)(Math.random()*100);
							if(rand<=20){
								//stay
								temp.inchealth((1/5)*temp.health);
								g1.grass=0;
							}
							else{
								rand=(int)(Math.random()*100);
								if(rand<=70){
									((herbivore) temp).moveawaycarnivore(4,(carnivore)temp.nearest(c1,c2));
								}
								else{
									((herbivore) temp).movetograss(2,temp.nearestgrass(g1, g2));
								}
							}
						}
					}
					else if(temp.ingrassland(g2)){
						if(g2.grass>=((herbivore)temp).capacity){
							rand=(int)(Math.random()*100);
							if(rand<=90){
								//stay
								temp.inchealth((1/2)*temp.health);
							}
							else{
								rand=(int)(Math.random()*100);
								temp.dechealth(25);
								if(rand<=50){
									((herbivore) temp).moveawaycarnivore(2,(carnivore)temp.nearest(c1,c2));
								}
								else{
									((herbivore) temp).movetograss(3,temp.nearestgrass(g1, g2));
								}
							}
						}
						else{
							rand=(int)(Math.random()*100);
							if(rand<=20){
								//stay
								temp.inchealth((1/5)*temp.health);
								g2.grass=0;
							}
							else{
								rand=(int)(Math.random()*100);
								if(rand<=70){
									((herbivore) temp).moveawaycarnivore(4,(carnivore)temp.nearest(c1,c2));
								}
								else{
									((herbivore) temp).movetograss(2,temp.nearestgrass(g1, g2));
								}
							}
						}
					}
				}
			}
			else if(temp instanceof carnivore){
				int rand;
				if(temp.turn>7){
					temp.dechealth(6);
				}
				else if(h1.isdead()&&h2.isdead()){
					if(temp.ingrassland(g1)||temp.ingrassland(g2)){
						temp.dechealth(30);
					}
				}
				else if(Math.sqrt(((temp.x-h1.x)*(temp.x-h1.x))+((temp.y-h1.y)*(temp.y-h1.y)))<=5||Math.sqrt(((temp.x-h2.x)*(temp.x-h2.x))+((temp.y-h2.y)*(temp.y-h2.y)))<=5){
					temp.turn=0;
				}
				else if(Math.sqrt(((temp.x-h1.x)*(temp.x-h1.x))+((temp.y-h1.y)*(temp.y-h1.y)))>5&&Math.sqrt(((temp.x-h2.x)*(temp.x-h2.x))+((temp.y-h2.y)*(temp.y-h2.y)))>5){
					temp.turn++;
				}
				else if(Math.sqrt(((temp.x-h1.x)*(temp.x-h1.x))+((temp.y-h1.y)*(temp.y-h1.y)))<=1&&Math.sqrt(((temp.x-h2.x)*(temp.x-h2.x))+((temp.y-h2.y)*(temp.y-h2.y)))<=1){
					if(Math.sqrt(((temp.x-h1.x)*(temp.x-h1.x))+((temp.y-h1.y)*(temp.y-h1.y)))<=Math.sqrt(((temp.x-h2.x)*(temp.x-h2.x))+((temp.y-h2.y)*(temp.y-h2.y)))){
						l.deleteAtPos(h1.timestamp);
						temp.inchealth(temp.health+((2/3)*h1.health));
					}
					else{
						l.deleteAtPos(h2.timestamp);
						temp.inchealth(temp.health+((2/3)*h1.health));
					}
				}
				else if(Math.sqrt(((temp.x-h1.x)*(temp.x-h1.x))+((temp.y-h1.y)*(temp.y-h1.y)))<=1||Math.sqrt(((temp.x-h2.x)*(temp.x-h2.x))+((temp.y-h2.y)*(temp.y-h2.y)))<=1){
					if(Math.sqrt(((temp.x-h1.x)*(temp.x-h1.x))+((temp.y-h1.y)*(temp.y-h1.y)))<=1){
						l.deleteAtPos(h1.timestamp);
						temp.inchealth(temp.health+((2/3)*h1.health));
					}
					else if(Math.sqrt(((temp.x-h2.x)*(temp.x-h2.x))+((temp.y-h2.y)*(temp.y-h2.y)))<=1){
						l.deleteAtPos(h2.timestamp);
						temp.inchealth(temp.health+((2/3)*h2.health));
					}
				}
				else if(!temp.ingrassland(g1)&&!temp.ingrassland(g2)){
					rand=(int)(Math.random()*100);
					if(rand<=92){
						((carnivore) temp).movetoherbivore(4, temp.nearest(h1, h2));
					}
					else{
						temp.dechealth(60);
					}
				}
				if(temp.ingrassland(g1)||temp.ingrassland(g2)){
					rand=(int)(Math.random()*100);
					if(rand<=25){
						temp.dechealth(30);
					}
					else{
						((carnivore) temp).movetoherbivore(2, temp.nearest(h1, h2));
					}
				}
			}
			if(temp.isdead()){
				System.out.println("It is dead");
			}
			else{
				System.out.println("It's health after taking turn is "+temp.health);
				int randomNum = ThreadLocalRandom.current().nextInt(max, maxtime);
				if(!temp.isdead()||!(randomNum==maxtime-1)){
					temp.timestamp=randomNum;
					l.insert(temp);
				}
				if(randomNum>max)
					max=randomNum;
			}
		}
	}
}
