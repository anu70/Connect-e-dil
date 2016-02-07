package verkstad.org.in.valentineapp;

/**
 * Created by coder on 1/16/2016.
 */
public class Shout {
    String name,message,id;
    Long time;
    public Shout(){

    }
    public Shout(String name,String id,String message,Long time){
        this.name=name;
        this.id=id;
        this.message=message;
        this.time=time;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
   public String getMessage(){
       return message;
   }
    public void setMessage(String message){
        this.message=message;
    }
    public Long getTime(){
        return time;
    }
    public void setTime(Long time){
        this.time=time;
    }
}
