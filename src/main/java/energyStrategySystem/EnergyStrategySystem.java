package energyStrategySystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.minespaceships.mod.menu.Menu;

public class EnergyStrategySystem {
	private int energy;
	private final ArrayList<EnergyC> consumerList;
	private final ArrayList<EnergyC> producerList;
	private final ArrayList<EnergyC> removeList;
	private final ArrayList<EnergyC> allList;
	
	
	
	public EnergyStrategySystem(){
		energy=0;
		consumerList= new ArrayList<EnergyC>();
		producerList= new ArrayList<EnergyC>();	
		removeList= new ArrayList<EnergyC>();
		allList= new ArrayList<EnergyC>();
	}
	
	public String print(){
		sortPriority(consumerList);
		String out="";
		out= "Energy in System:" +energy +'\n';
		out=out +"EnergyConsumer" +'\n';
		for(EnergyC e: consumerList){
			out=out +"Energy: " +e.getEnergy() +" Priority: " +e.getPriority() +'\n';
		}
		out=out +"EnergyProducer" +'\n';
		for(EnergyC e: producerList){
			out=out +"Energy: " +e.getEnergy() +" Priority: " +e.getPriority() + '\n';
		}
		
		out=out+'\n';
		return out;
	}
	
	public String add(EnergyC e){
		if(allList.contains(e)==false){
		if(e.getEnergy()>0){
			return this.addProducer(e);
		} else if (e.getEnergy()<0){
			return this.addConsumer(e);
		} else {
			return addNeutral(e);
		}
		}
		else {return "Already in System";
				
		}
	
		
	}
	
	public String addNeutral(EnergyC p){
		if(allList.contains(p)==true){
			return "Already in System";
		}
		allList.add(p);
		return "Neutral thing added to EnergySystem" + '\n';
	}

	public String addConsumer(EnergyC c){
		if(allList.contains(c)==true){
			return "Already in System";
		}
		if(energy>=c.getEnergy()){
		energy=energy-c.getEnergy();
		consumerList.add(c);
		allList.add(c);
		return "EnergyConsumer added. Energy of Consumer=" +energy+ '\n';
		}
		else{
		return "Not enough Energy to add Consumer"+ '\n';
		}
	}
	
	public String addProducer(EnergyC p){
		if(allList.contains(p)==true){
			return "Already in System";
		}
		energy=energy+p.getEnergy();
		producerList.add(p);
		allList.add(p);
		forceAdd(energy);
		return "EnergyProducer added. Energy of Producer=" +energy+ '\n';
		
	}
	
	private void forceAdd(int toAdd){
		ArrayList<EnergyC> consumer= new ArrayList();
		sortPriority(allList);
		for(Iterator<EnergyC> iterator=allList.iterator(); iterator.hasNext();){
			EnergyC next= iterator.next();
			if(next.getEnergy()<=toAdd){
				consumer.add(next);
				toAdd-=next.getEnergy();
			}
			
		}
		
		for(EnergyC c: consumer){
			addConsumer(c);
		}
		
	
	}
	
	private void sortPriority(ArrayList l){
		Collections.sort(l, new Comparator<EnergyC>(){
			@Override
	        public int compare(EnergyC e1, EnergyC e2)
	        {
				Integer i1= new Integer(e1.getPriority());
				Integer i2 = new Integer(e2.getPriority());
				
	            return  i1.compareTo(i2);
	        }
		});
		
	}
	
	public String removeConsumer(EnergyC c){
		energy=energy+c.getEnergy();
		for(int i=0; i<consumerList.size(); i++){
			if(c==consumerList.get(i)){
				consumerList.remove(consumerList.get(i));
				
			}
		}
		return "EnergyConsumer removed" + '\n';
	}
	
	public String removeProducer(EnergyC p){
		
		if(0>=energy-p.getEnergy()){
			forceRemove(java.lang.Math.abs(energy-p.getEnergy()));
		}
		
		energy=energy-p.getEnergy();
		for(int i=0; i<producerList.size(); i++){
			if(p==producerList.get(i)){
				producerList.remove(producerList.get(i));
				
			}
		}
		return "EnergyProducer removed" + '\n';
	}
	
	private void forceRemove(int toRemove){
		for(int i=0; i<consumerList.size(); i++){
			if (toRemove>0){
				if(consumerList.get(i).getPriority()==1){
				toRemove=toRemove-consumerList.get(i).getEnergy();
				removeList.add(consumerList.get(i));
						}		
			} 				
		}
		
		for(int j=0; j<consumerList.size(); j++){
			if (toRemove>0){
				if(consumerList.get(j).getPriority()==2){
				toRemove=toRemove-consumerList.get(j).getEnergy();
				removeList.add(consumerList.get(j));
						}		
			} 				
		}
		
		for(int k=0; k<consumerList.size(); k++){
			if (toRemove>0){
				if(consumerList.get(k).getPriority()==3){
				toRemove=toRemove-consumerList.get(k).getEnergy();
				removeList.add(consumerList.get(k));
						}		
			} 				
		}
		
		for(EnergyC e: removeList ){
			
			for(int l=0; l<consumerList.size(); l++){
				if(e.equals(consumerList.get(l))){
					//consumerList.remove(l);	
					removeConsumer(consumerList.get(l));
				}
			}
		}
		
		
		
		
		
	}
	
	
	
	
	public int getEnergy(){
		return energy;
	}
	}
	

