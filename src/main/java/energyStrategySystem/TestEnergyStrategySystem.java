package energyStrategySystem;

public class TestEnergyStrategySystem {
	private static EnergyProducer p1;
	private static EnergyProducer p2;
	private static EnergyProducer p3;
	private static EnergyConsumer c1;
	private static EnergyConsumer c2;
	private static EnergyConsumer c3;
	private static EnergyStrategySystem e;
	public static void main(String[] args){
		e = new EnergyStrategySystem();
		p1= new EnergyProducer(10,1);
		p2= new EnergyProducer(50,1);
		p3= new EnergyProducer(10,2);
		c1= new EnergyConsumer(10,1);
		c2= new EnergyConsumer(50,2);
		c3= new EnergyConsumer(10,3);
		
		System.out.print(
		
		
		e.addProducer(p2)+
		e.addConsumer(c1)+
		e.addConsumer(c2)+
		
		e.print()+
		e.removeConsumer(c1)+
		e.removeProducer(p2)+
		e.print()+
		
		e.addProducer(p1)+
		e.addProducer(p2)+
		e.addProducer(p3)+
		e.addConsumer(c1)+
		e.addConsumer(c2)+
		e.addConsumer(c3)+
		
		e.print()+
		e.removeProducer(p2)+
		e.print()
		
		
		
		
		
				);
		
		
		
	}

}
