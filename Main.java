import java.util.*;
public class Main{
    static Scanner scanner=new Scanner(System.in);
    static Random rand=new Random();
    static List<Integer> playhistory=new ArrayList<>();
    public static void main(String[] args){
        String name=scanner.next();
        System.out.println("Welcome to Hand Cricket Game ,"+name);
        boolean playagain;
        do{
            playmatch();
            System.out.println("Do you want to play again y/n");
            playagain=scanner.next().equalsIgnoreCase("y");
        }while(playagain);
        System.out.println("Thanks for playing "+name);
    }
    public static void playmatch(){
        String toss_choice;
        String diffculty;
        boolean batting_first;
        System.out.println("**** TOSS TIME ****");
        System.out.println("Choose odd or even");
        String choice=scanner.next().to_LowerCase();
        System.out.println("Enter a number in between 1-10");
        int user_num=getvaildnumber();
        int computer_num=random.nextInt(10)+1;
        int total=user_num+computer_num;
        boolean toss_result=(choice.equals("odd")&& total%2!=0)|(choice.equals("even")&&(total%2==0))
        if(toss_result){
            System.out.println(name+" You have won toss,what could like to do? would bat first or bowl first(bat or bowl)");
            String user_choice=scanner.next().to_LowerCase();
            boolean player_batting_first=user_choice.equals("bat");
        }
        else{
            System.out.println("Your Oppent won the toss");
            player_batting_first=random.nextboolean();
            System.out.println("Your Oppent choosed "+(player_batting_first?"bowl":"bat")+" first");
        }
    }
}