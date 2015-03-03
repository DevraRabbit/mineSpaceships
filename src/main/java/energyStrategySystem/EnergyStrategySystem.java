package energyStrategySystem;
import java.util.ArrayList;

import com.minespaceships.mod.menu.Menu;

public class EnergyStrategySystem {
	private int energy;
	private final ArrayList<EnergyConsumer> consumerList;
	private final ArrayList<EnergyProducer> producerList;
	private final ArrayList<EnergyConsumer> removeList;
	
	
	
	public EnergyStrategySystem(){
		energy=0;
		consumerList= new ArrayList<EnergyConsumer>();
		producerList= new ArrayList<EnergyProducer>();	
		removeList= new ArrayList<EnergyConsumer>();
	}
	
	public String print(){
		String out;
		out="EnergyConsumer" +'\n';
		for(EnergyConsumer e: consumerList){
			out=out +e.toString();
		}
		out=out +"EnergyProducer" +'\n';
		for(EnergyProducer e: producerList){
			out=out +e.toString();
		}
		
		out=out+'\n';
		return out;
	}
	
	

	public String addConsumer(EnergyConsumer c){
		if(energy>=c.getEnergy()){
		energy=energy-c.getEnergy();
		consumerList.add(c);
		return "EnergyConsumer added. Energy=" +energy+ '\n';
		}
		else{
		return "Not enough Energy to add Consumer"+ '\n';
		}
	}
	
	public String addProducer(EnergyProducer p){
		energy=energy+p.getEnergy();
		producerList.add(p);
		return "EnergyProducer added. Energy=" +energy+ '\n';
		
	}
	
	public String removeConsumer(EnergyConsumer c){
		energy=energy+c.getEnergy();
		for(int i=0; i<consumerList.size(); i++){
			if(c==consumerList.get(i)){
				consumerList.remove(consumerList.get(i));
				
			}
		}
		return "EnergyConsumer removed" + '\n';
	}
	
	public String removeProducer(EnergyProducer p){
		
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
		
		for(EnergyConsumer e: removeList ){
			
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
	

