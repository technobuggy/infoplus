package domain;

public class Feedback {
    
   
    private int userID;
    
     private int notID;
    
    private boolean like;
    
    
//    public Feedback()
//    {
//        //setId(0);
//        this.id = 1;
//        //setTopic("Default");
//        this.topic = "Default";
//        //setTekst("Default");
//        this.feedback = "Default";
//        //setAuthor(auteurId);
//        this.auteurId =1 ;
//    }
    
    public Feedback(int userID, int notID, boolean like)
    {
        this.userID = userID;
        this.notID = notID;
        this.like = like;
    }
    
    public Feedback()
    {
        this.userID = 1;
        this.notID = 1;
        this.like = false;
    }

    public int getNotID() 
    {
        return notID;
    }

    public void setNotID(int notID) 
    {
        this.notID = notID;
    }
    
    public int getUserID() 
    {
        return userID;
    }

    public void setUserID(int userID) 
    {
        this.userID = userID;
    }

    public boolean getLike() 
    {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Feedback{" + "userID=" + userID + ", notID=" + notID + ", like=" + like + '}';
    }
    
    
    
}
