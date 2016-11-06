package Restaurant;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Availability 
{
	Table table[];
	ReservationEntity reservation=new ReservationEntity();
	public ArrayList<ReservationEntity> Occupied=new ArrayList<ReservationEntity>();
	private static final DateFormat DATE_FORMAT=new DateFormat();
	public Availability(){
		
	}
	public Table[] getTable(){
		return table;
	}
	public void setTable(Table[] table){
		this.table=table;
	}
	public ArrayList<ReservationEntity> getOccupied(){
		return Occupied;
	}
	public void setOccupied(ArrayList<ReservationEntity> Occupied){
		this.Occupied=Occupied;
	}
	public void getOccupiedtable() throws ParseException,IOException{
		reservation=new ReservationEntity();
		Occupied=reservation.getCurrentReservation();
		
	}
	public void checkTable(String People)throws IOException, ParseException{
		reservation=new ReservationEntity();
		Date date,Start;
		String date_use;
		Calendar calendar=Calendar.getInstance();
		date=calendar.getTime();
		date_use=DATE_FORMAT.getDate(date);
		Start=calendar.getTime();
		reservation.assignTable(date_use,Start,People);
		if(reservation.getName()!=null){
			Occupied.add(reservation);
			System.out.println(reservation.getTableNo()+" "+"is available");
		}
		else{
			System.out.println("There is no suitable table");
		}
		
	}
    public boolean releaseTable(String tableNo){
    	boolean unassignsuccessfully=false;
    	for(int i=0;i<30;i++){
    		if(table[i].getTableNo()==Integer.parseInt(tableNo)){
    			if(table[i].getisOccupied()==1){
    				table[i].setOccupied(0);
    				unassignsuccessfully=true;
    			}
    		}
    	}
    	return unassignsuccessfully;
    }

}
