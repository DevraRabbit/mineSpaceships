package energyStrategySystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.minespaceships.mod.menu.Menu;

public class EnergyStrategySystem {
	
	/**
	 * saves how much energy is in system, due to Items in status On
	 */
	private int energy;
	
	
	/**
	 * saves consumer in status On
	 */
	private final ArrayList<EnergyC> consumerList;
	
	
	/**
	 * saves producer in status On
	 */
	private final ArrayList<EnergyC> producerList;
	
	
	/**
	 * save all producers and consumers in status off and on
	 */
	private final ArrayList<EnergyC> allList;
	
	
	/**
	 * Constructor of EnergySystem
	 * default energy=0
	 */
	public EnergyStrategySystem(){
		energy=0;
		consumerList= new ArrayList<EnergyC>();
		producerList= new ArrayList<EnergyC>();	
		
		allList= new ArrayList<EnergyC>();
	}
	
	/**
	 * prints Information about the EnergySystem
	 * @return Energy, List of Consumers and Producers
	 */
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
	
	/**
	 * User can use this method to register Consumer or Producer in System
	 * @param e Producer or Consumer to be added
	 * @return String: was adding successful?
	 */
	public String add(EnergyC e){
		if(allList.contains(e)==false){
		allList.add(e);
		return "added to System" +'\n';
		}
		return "already in System" +'\n';
	}
	
	/**
	 * User can switch On a Consumer or Producer
	 * @param e Consumer or Producer to be switched on
	 * @return Was switching successful?
	 */
	public String switchOn(EnergyC e){
		if(allList.contains(e)==true){
		if(e.getEnergy()>0){
			return this.switchOnProducer(e);
		} else if (e.getEnergy()<0){
			return this.switchOnConsumer(e);
		} else {
			return "Neutral switched on. no changes for EnergySystem." +'\n';
		}
		}
		else {return "Can not be switched on, because it is not in System" +'\n';
				
		}
	
		
	}
	/**
	 * User can switch off Consumer or Producer
	 * @param e Consumer or Producer
	 * @return was switching successful?
	 */
	public String switchOff(EnergyC e){
		if(allList.contains(e)==true){
		if(e.getEnergy()>0){
			return this.switchOffProducer(e);
		} else if (e.getEnergy()<0){
			return this.switchOffConsumer(e);
		} else {
			return "neutral. no changes for EnergySystem." +'\n';
		}
		}
		else {return "Can not be switched Off, because it is not in System" +'\n';
				
		}
	
		
	}
	
	
	/**
	 * if user wants to switch of Consumer, this method becomes active
	 * @param c Consumer to switch On
	 * @return was switching successful?
	 */
	private String switchOnConsumer(EnergyC c){
		if(allList.contains(c)==false){
			return "not in System"+'\n';
		}
		if(energy>=c.getEnergy()&&consumerList.contains(c)==false){
		energy=energy+c.getEnergy();
		consumerList.add(c);
		return "EnergyConsumer switched On. Energy of Consumer=" +c.getEnergy()+ '\n';
		}
		else{
		return "Not enough Energy to add Consumer"+ '\n';
		}
	}
	
	/**
	 * if user wants to switch on Producer, this method becomes active
	 * @param p Producer to be added
	 * @return was adding successful?
	 */
	private String switchOnProducer(EnergyC p){
		String toReturn="";
		if(allList.contains(p)==false){
			return "not in System";
		}
		energy=energy+p.getEnergy();
		producerList.add(p);
		toReturn+=forceSwitchOnConsumer(-energy);
		return  "EnergyProducer switched on. Energy of Producer=" +p.getEnergy()+'\n' +toReturn +'\n';
		
	}
	
	
	
	
	
	/**
	 * method becomes active, if User wants to switch off Consumer
	 * @param c Consumer
	 * @return was switching successful?
	 */
	private String switchOffConsumer(EnergyC c){
		
		energy=energy-c.getEnergy();
		for(int i=0; i<consumerList.size(); i++){
			if(c==consumerList.get(i)){
				consumerList.remove(consumerList.get(i));
				
			}
		}
		return "EnergyConsumer removed" + '\n';
	}
	
	/**
	 * method becomes active, if User wants to switch off Producer. Activates forceSwitchOffConsumer()
	 * @param p Producer to switch off
	 * @return was switching succesful?
	 */
	private String switchOffProducer(EnergyC p){
		String toReturn="";
		if(0>=energy-p.getEnergy()){
			toReturn+=forceSwitchOffConsumer(java.lang.Math.abs(energy-p.getEnergy()));
		}
		
		energy=energy-p.getEnergy();
		for(int i=0; i<producerList.size(); i++){
			if(p==producerList.get(i)){
				producerList.remove(producerList.get(i));
				
			}
		}
		return "EnergyProducer removed" + '\n' +toReturn +'\n';
	}
	
	/**
	 * becomes active, if User switches of Producer and Consumer has to be deleted. chooses consumer with low priority
	 * @param toRemove energy missing
	 */
	private String forceSwitchOffConsumer(int toRemove){
		ArrayList<EnergyC> removeList= new ArrayList<EnergyC>();
		String toReturn="";
		
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
						
					toReturn+= switchOffConsumer(consumerList.get(l));
				}
			}
		}
		
		return "Following Consumers forced to switch off: " +'\n' +toReturn +'\n';
		
		
		
		
		
	}
	
	/**
	 * If a Producer was switched on this method does automatically choose a Consumer with high priority and switches it On
	 * @param toAdd how much Energy is in System
	 */
	private String forceSwitchOnConsumer(int toAdd){
		String toReturn="";
		ArrayList<EnergyC> consumer= new ArrayList();
		sortPriority(allList);
		Collections.reverse(allList);
		for(Iterator<EnergyC> iterator=allList.iterator(); iterator.hasNext();){
			EnergyC next= iterator.next();
			if(next.getEnergy()>=toAdd&&next.getEnergy()<=0){
				consumer.add(next);
				toAdd-=next.getEnergy();
			}
			
		}
		
		for(EnergyC c: consumer){
			toReturn+=switchOnConsumer(c);
		}
		
		return "Automatically switched on following Consumers: " +'\n' +toReturn +'\n';
		
	
	}
	

	/**
	 * sorts arrayList due to the priority
	 * @param l ArrayList to sort
	 */
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
	
	public int getEnergy(){
		return energy;
	}
	}
	

