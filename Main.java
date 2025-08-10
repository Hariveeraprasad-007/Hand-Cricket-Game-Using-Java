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
        boolean toss_result=(choice.equals("odd")&& total%2!=0)|(choice.equals("even")&&(total%2==0));
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
        System.out.println("Could you please choose the level,1.Easy 2.Medium 3.Hard");
        int difflevel=scanner.nextInt();
        diffculty=switch(difflevel){
            case 1->"easy";
            case 2->"medium";
            case 3->"hard";
            default->"medium";
        };
        int userscore,computerscore;
        if(player_batting_first){
            userscore=PlayBattingInnings(true,diffculty);
            computerscore=PlayBattingInnings(false,diffculty,userscore);
        }
        else{
            computerscore=PlayBattingInnings(true,diffculty);
            userscore=PlayBattingInnings(false,diffculty,computerscore);
        }
        if(userscore>computerscore){
            System.out.println("Great match had happend, "+" had played well and win the match");
        }
        else if(computerscore>userscore){
            System.out.println("Oppontent have won the match,great play by them");
        }
        else{
            System.out.println("The intense match cames to end ,Match is draw , both players played well.");
        }
    }
    public static int PlayBattingInnings(boolean isplayerbatting,String diffculty){
        return PlayBattingInnings(isplayerbatting,diffculty,Integer.MAX_VALUE);
    }
    public static int PlayBattingInnings(boolean isplayerbatting,String diffculty,int tareget){
        int score=0;
        playhistory.clear();
        System.out.prinln("-==- "+(isplayerbatting?"Your":"opponent's")+" innings -==-");
        while(true){
            int batsmanchoice,bowlerchoice;
            if(isplayerbatting){
                System.out.prinln("Enter a vaild number(1-10): ");
                batsmanchoice=getvaildnumber();
                playhistory.add(batsmanchoice);
                bowlerchoice=getComputerchoice(playhistory,diffculty);
                System.out.println("Computer bowled "+bowlerchoice);
            }
            else{
                batsmanchoice=random.nextInt(10)+1;
                bowlerchoice=getvaildnumber()
                System.out.println("Computer choice: "+batsmanchoice);
                System.out.prinln("You bowled: "+bowlerchoice);
            }
            if(batsmanchoice==bowlerchoice){
                if(isplayerbatting){
                    System.out.println("Your Out");
                }
                else{
                    System.out.prinln("Opponent Out");
                }
                break;
            }
            else{
                score+=batsmanchoice;
                System.out.prinln("Runs score "+batsmanchoice+"Total Runs: "+score);
            }
            if(score>target){
                break;
            }
        }
        return score;
    }
}