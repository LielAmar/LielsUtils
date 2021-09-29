package com.lielamar.lielsutils.bukkit.scoreboard;

import com.lielamar.lielsutils.bukkit.color.ColorUtils;
import com.lielamar.lielsutils.groups.Pair;
import org.bukkit.ChatColor;

import java.util.List;

public class ScoreboardUtils {

    /**
     * Assembles an array of lines
     *
     * @param sb       Scoreboard data to assemble from
     */
    public static ScoreboardManager.Line[] assembleScoreboard(List<String> sb, Pair<?,?>... pairs) {
        ScoreboardManager.Line[] lines;
        String[] msgParts;

        lines = new ScoreboardManager.Line[sb.size()];

        for(int i = 0; i < sb.size(); i++) {
            String msg = ChatColor.translateAlternateColorCodes('&', sb.get(sb.size()-1-i));
            if(pairs != null) {
                for(Pair<?, ?> pair : pairs)
                    msg = msg.replaceAll((pair.getA() + ""), (pair.getB() + ""));
            }

            msgParts = ScoreboardUtils.divideStringForScoreboard(msg);

            lines[i] = new ScoreboardManager.Line("" + i, msgParts[0], msgParts[1], i);
        }

        return lines;
    }

    /**
     * Divides a string in the next format: [(16),(16)]
     *
     * @param msg        String to divide
     * @return           The divided array of messages assembling the message
     */
    public static String[] divideStringForScoreboard(String msg) {
        String[] msgParts = new String[] { "               ", "               " };

        msg = ColorUtils.unTranslateAlternateColorCodes(msg);
        if(msg.length() == 0) return msgParts;

        if(!msg.contains("&")) msg = "&f" + msg; // Applying a white color if there is no color at all

        if(msg.length() < 16)
            msgParts[0] = msg;
        else {
            msgParts[0] = msg.substring(0, 16);

            if(msg.length() < 32)
                msgParts[1] = msg.substring(16);
            else
                msgParts[1] = msg.substring(16, 32);

            if(msgParts[0].endsWith("&") || msgParts[0].endsWith("§")) {
                msgParts[0] = msgParts[0].substring(0, 15);
                msgParts[1] = "&" + msgParts[1];
            } else if(msgParts[0].contains("&")) {
                msgParts[1] = "&" + msgParts[0].charAt(msgParts[0].lastIndexOf('&')+1) + msgParts[1];
            }
        }

        if(msgParts[0].length() > 16) msgParts[0] = msgParts[0].substring(0, 16);
        if(msgParts[1].length() > 16) msgParts[1] = msgParts[1].substring(0, 16);

        msgParts[0] = ChatColor.translateAlternateColorCodes('&', msgParts[0]);
        msgParts[1] = ChatColor.translateAlternateColorCodes('&', msgParts[1]);
        return msgParts;
    }
}
