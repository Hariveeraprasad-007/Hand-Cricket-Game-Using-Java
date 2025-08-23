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
            user_score=FirstBatting(diffculty,"user");
            computer_score=SecondBatting(diffculty,"computer");
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
            computer_score=FirstBatting(diffculty,"computer");
            user_score=SecondBatting(diffculty,"user");
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
    public static int FirstBatting(String diffculty,String batter){
        int current_score=0;
        if(batter.equals("user")){
            System.out.println("**** User is batting *****");
            System.out.println("Enter a vaild number in range of 1 to 10");
            While(true){
                int userchoice=sc.nextInt();
                playhistory.add(userchoice);
                int computerchoice=Computer_Response(diffculty,playhistory);
                System.out.println("Score= "+current_score);
                if(user_choice!=computer_choice){
                    current_score+=user_choice;
                }
                else{
                    System.out.println("User you are out at score of "+current_score);
                    return current_score;
                }
            }
        }
        else{
            System.out.println("**** Computer is batting ***");
            System.out.println("Enter a vaild number in range of 1 to 10");
            while(true){
                int computerchoice=Computer_Response(diffculty,playhistory);
                int userchoice=sc.nextInt();
                playhistory.add(userchoice);
                System.out.println("Score= "+current_score);
                if(user_choice!=computer_choice){
                    current_score+=computerchoice;
                }
                else{
                    System.out.println("User you are out at score of "+current_score);
                    return current_score;
                }
            }
        }
    }
    public static int SecondBatting(String diffculty,String secondbatting,int target){
        int cuurent_score=0;
        if(secondbatting.equals("user")){
            System.out.println("**** User is batting *****");
            System.out.println("Enter a vaild number in range of 1 to 10");
            while(current_score<=target){
                int userchoice=sc.nextInt();
                playhistory.add(userchoice);
                int computerchoice=Computer_Response(diffculty,playhistory);
                System.out.println("Score= "+current_score);
                if(user_choice!=computer_choice){
                    current_score+=user_choice;
                }
                else{
                    System.out.println("User you are out at score of "+current_score);
                    return current_score;
                }
            }
        }
        else{
            System.out.println("**** Computer is batting ***");
            System.out.println("Enter a vaild number in range of 1 to 10");
            while(current_score<=target){
                int computerchoice=Computer_Response(diffculty,playhistory);
                int userchoice=sc.nextInt();
                playhistory.add(userchoice);
                System.out.println("Score= "+current_score);
                if(user_choice!=computer_choice){
                    current_score+=computerchoice;
                }
                else{
                    System.out.println("User you are out at score of "+current_score);
                    return current_score;
                }
            }
        }
    }
    public static int Computer_Response(String diffculty,List<Integer> playhistory){
        if(diffculty.equals("easy")){
            return random.nextInt(10)+1;
        }
        else if(diffculty.equals("medium")){
            if (random.nextInt(100) < 100 && !history.isEmpty()) {
                    return history.get(history.size() - 1);
                }
        }
        else if(diffculty.equals("hard")){
            if (!history.isEmpty()) {
                    Map<Integer, Integer> freq = new HashMap<>();
                    for (int n : history) {
                        freq.put(n, freq.getOrDefault(n, 0) + 1);
                    }
                    int predicted = history.get(0), max_val = 0;
                    for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
                        if (entry.getValue() > max_val) {
                            max_val = entry.getValue();
                            predicted = entry.getKey();
                        }
                    }
                    return predicted;
                }
                return random.nextInt(10) + 1;
            default:
                return random.nextInt(10) + 1;
        }
    }
}