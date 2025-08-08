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
}