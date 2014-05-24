package hu.unideb.inf.prt.petriDish;

import hu.unideb.inf.prt.petriDish.ANN.ANN;
import hu.unideb.inf.prt.petriDish.ANN.FeedForwardNeuron.WeigthNumberNotMatchException;

import java.util.List;

public class Agent extends Entity {
	protected ANN network;
	protected double hunger;
	protected double heading;
	private double movement;
	private double headingDelta;
	private Genotype genotype;
	public static final double viewDistance = 500;
	public static final double viewDistanceSquared = viewDistance*viewDistance;
	
	public Agent(Genotype g, int xPos, int yPos, int heading) throws WeigthNumberNotMatchException
	{
		this.genotype=g;
		this.hunger=0;
		network = new ANN(this, g);
		this.xPos = xPos;
		this.yPos = yPos;
		this.heading = heading;
	}
	
	public Genotype getGenotype()
	{
		return genotype;
	}
	
	public void exec()
	{
		List<Double> annOut = network.run();
		if (annOut.get(0)>.5 && annOut.get(1)<=.5)
		{
			headingDelta=5;
			movement=0;
			hunger+=.0009;
		} else
		if (annOut.get(0)>.5 && annOut.get(1)>.5)
		{
			headingDelta=0;
			movement=3;
			hunger+=.0009;
		} else
		if (annOut.get(0)<=.5 && annOut.get(1)>.5){
			headingDelta=-5;
			movement=0;
			hunger+=.0009;
		} else
		{
			headingDelta=0;
			movement=0;
			hunger+=.0009;
		}
	}
	
	public double getHeading()
	{
		return heading;
	}
	
	public void setHeading(double heading)
	{
		if (heading>=360) heading-=360;
		if (heading<0) heading+=360;
		this.heading=heading;
	}
	
	public double getHunger()
	{
		return hunger;
	}
	
	public void setHunger(double hung)
	{
		if (hung<0)
			hunger=0;
		else if (hung>1)
			hunger=1;
		else hunger=hung;
	}
	
	public void apply()
	{
		heading+=headingDelta;
		if (heading>=360) heading-=360;
		if (heading<0) heading+=360;
		xPos+=Math.cos(Math.toRadians(heading))*movement;
		yPos+=Math.sin(Math.toRadians(heading))*movement;
	}
	
	public boolean sees(Entity other)
	{
		if (distanceSquared(other)>viewDistanceSquared) return false; //only check in circle with r=200
		
		double p1X=this.xPos - other.xPos;
		double p1Y=this.yPos - other.yPos;
		double p2X=this.xPos - other.xPos +Math.cos(Math.toRadians(heading))*10;
		double p2Y=this.yPos - other.yPos +Math.sin(Math.toRadians(heading))*10;
		double a = p1Y-p2Y;
		double b = (p2X-p1X);
		double c = (p2X*p1Y - p1X*p2Y);
		double discriminant = radiusSquared*(Math.pow(a,2) + Math.pow(b,2))-Math.pow(c, 2);
		if (discriminant<0) return false;
		
		double v1X=other.xPos-this.xPos;
		double v1Y=other.yPos-this.yPos;
		//normalize
		double v1Length = Math.sqrt(v1X*v1X + v1Y*v1Y);
		v1X/=v1Length;
		v1Y/=v1Length;
		double v2X=Math.cos(Math.toRadians(heading));
		double v2Y=Math.sin(Math.toRadians(heading));
		double dotProduct = v1X*v2X + v1Y*v2Y;
		if (dotProduct<0) return false;
		return true;
	}
}
