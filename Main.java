import java.util.*;
public class Main{
    static Scanner sc=new Scanner(System.in);
    static Random random=new Random<>();
    static List<Integer> playhistory=new ArrayList<>();
    public static void main(Strings[] args){
        System.out.println("Please Enter your name: ");
        String name=sc.next();
        boolean playagain;
        do{
            playmatch();
            System.out.println("Do you want play again(y/n): ");
            playagain=sc.next().equalsIgnoreCase("y");
        }
        while(playagain);
    }
    public static void playmatch(){
        System.out.println("Chosse odd or even: ");
        String user_odd_even_choice=sc.next();
        System.out.println("Please enter a vaild number in between 1-10: ");
        int user_toss_choice=sc.nextInt();
        int computerchoice=random.nextInt(10)+1;
        boolean user_batting_first
        boolean computerbattingfirst;
        int toss_total=user_toss_choice+computerchoice;
        if((toss_total%2==0 && user_odd_even_choice=="even")||(toss_total%2!=0&user_odd_even_choice="odd")){
                System.out.println("You have won the toss , what could you do first (Bat/Bowl): ");
                user_batting_first=sc.next().equalsIgnoreCase("Bat");
                if(user_batting_first){
                    System.out.println("User won the toss and choosed to bat first");
                }
                else{
                    System.out.println("User won the toss and chossed to bowl first");
                }
        }
        else{
            computerbattingfirst=random.nextBoolean();
            if(computerbattingfirst){
                System.out.println("Computer won the toss and choosed to bat first");
            }
            else{
                System.out.println("Computer won the toss and choosed to bowl first");
            }
        }
        System.out.println("Choose the diffculty (easy/medium/hard): ");
        String diffculty=sc.next();
        int user_score;
        int computer_score;
        if(user_batting_first){
            System.out.println("User Batting firsr:")
            user_score=PlayerBatting(diffculty);
            computer_score=PlayerBatting(diffculty);
            if(computer_score>user_score){
                System.out.println("Computer has won the match , computer chased the users targer");
            }
            else if(computer_score<user_score){
                System.out.println("User has won the match,Computer failed to chased down the target.");
            }
            else{
                System.out.println("Match was draw");
            }
        }
        else(computer_batting_first){
            System.out.println("Computer batting first: ");
            computer_score=PlayerBatting(diffculty);
            user_score=PlayerBatting(diffculty);
            if(computer_score>user_score){
                System.out.println("Computer has won the match , computer chased the users targer");
            }
            else if(computer_score<user_score){
                System.out.println("User has won the match,Computer failed to chased down the target.");
            }
            else{
                System.out.println("Match was draw");
            }
        }
    }

}