package chineseCheckers.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
    public static byte[] translateMove(String s){
        byte[] move = new byte[4];


        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);

        int i =0;

        while (m.find()){

            String sg =m.group();
            move[i] = Byte.parseByte(sg);
            System.out.println(move[i]);
            i++;
        }

        return move;
    }


    public static String translateInfo(byte player, byte part1, byte field1, byte part2, byte field2){
        String info = "";
        info = info + Byte.toString(player) + " " + Byte.toString(part1) +" "+ Byte.toString(field1)+" "+ Byte.toString(part2)+" "+ Byte.toString(field2);
        return info;
    }

    public static byte[] translateOption(String s){
        byte[] opt = new byte[2];
        if(s.equals("skip"))
            return null;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);

        int i =0;

        while (m.find()){

            String sg =m.group();
            opt[i] = Byte.parseByte(sg);
            i++;
        }

        return opt;
    }
}
