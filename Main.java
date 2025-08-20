import java.util.*;
public class Main{
    static Scanner scanner=new Scanner(System.in);
    static Random random=new Random();
    static List<Integer> playhistory=new ArrayList<>();
    public static void main(String[] args){
        System.out.println("Enter your name please: ");
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
        boolean player_batting_first;
        System.out.println("**** TOSS TIME ****");
        System.out.println("Choose odd or even");
        String choice=scanner.next().toLowerCase();
        System.out.println("Enter a number in between 1-10");
        int user_num=getvaildnumber();
        int computer_num=random.nextInt(10)+1;
        int total=user_num+computer_num;
        boolean toss_result=(choice.equals("odd")&& total%2!=0)|(choice.equals("even")&&(total%2==0));
        if(toss_result){
            System.out.println("You have won toss,what could like to do? would bat first or bowl first(bat or bowl)");
            String user_choice=scanner.next().toLowerCase();
            player_batting_first=user_choice.equals("bat");
        }
        else{
            System.out.println("Your Oppent won the toss");
            player_batting_first=random.nextBoolean();
            System.out.println("Your Oppent choosed that you "+(player_batting_first?"bowl":"bat")+" first");
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
    public static int PlayBattingInnings(boolean isplayerbatting,String diffculty,int target){
        int score=0;
        playhistory.clear();
        System.out.println("-==- "+(isplayerbatting?"Your":"opponent's")+" innings -==-");
        while(true){
            int batsmanchoice,bowlerchoice;
            if(isplayerbatting){
                System.out.println("Enter a vaild number(1-10): ");
                batsmanchoice=getvaildnumber();
                playhistory.add(batsmanchoice);
                bowlerchoice=getComputerchoice(playhistory,diffculty);
                System.out.println("Computer bowled "+bowlerchoice);
            }
            else{
                batsmanchoice=random.nextInt(10)+1;
                bowlerchoice=getvaildnumber();
                System.out.println("Computer choice: "+batsmanchoice);
                System.out.println("You bowled: "+bowlerchoice);
            }
            if(batsmanchoice==bowlerchoice){
                if(isplayerbatting){
                    System.out.println("Your Out");
                }
                else{
                    System.out.println("Opponent Out");
                }
                break;
            }
            else{
                score+=batsmanchoice;
                System.out.println("Runs score "+batsmanchoice+" Total Runs: "+score);
            }
            if(score>target){
                break;
            }
        }
        return score;
    }
    public static int getvaildnumber(){
        int n;
        while(true){
            n=scanner.nextInt();
            if(n>0&&n<=10){
                return n;
            }
            else{
                System.out.println("Please enter a vaild number between 1 to 10");
            }
        }
    }
    public static int getComputerchoice(List <Integer> history,String level){
        switch(level){
            case "easy":
                return random.nextInt(10)+1;
            case "medium":
                if(random.nextInt(100)<100 && !history.isEmpty()){
                    return history.get(history.size()-1);
                }
                return random.nextInt(10)+1;
            case "hard":
                if(!history.isEmpty()){
                    Map<Integer,Integer> freq=new HashMap<>();
                    for(int n:history){
                        freq.put(n,freq.getOrDefault(n,0)+1);
                    }
                    int predicted=history.get(0),max_val=0;
                    for(Map.Entry<Integer,Integer> entry:freq.entrySet()){
                        if(entry.getValue()>max_val){
                            max_val=entry.getValue();
                            predicted=entry.getKey();
                        }
                    }
                    return predicted;
                }
                return random.nextInt(10)+1;
            default:
                return random.nextInt(10)+1;
        }
    }
}