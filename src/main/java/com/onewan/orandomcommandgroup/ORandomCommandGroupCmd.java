package com.onewan.orandomcommandgroup;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.onewan.orandomcommandgroup.MessageUtil.sendConsoleMessage;
import static com.onewan.orandomcommandgroup.MessageUtil.sendPlayerMessage;

/**
 * @author OneWan
 * @date 2023/6/27 21:17
 **/

class ErrorMessage {
    public static final String NOT_FOUND_PLAYER = "&dORandomCommandGroup &6>> &c玩家不存在或玩家不在线";
    public static final String TYPE = "&dORandomCommandGroup &6>> &c执行类型错误";
    public static final String NOT_FOUND_GROUP = "&dORandomCommandGroup &6>> &c指令组不存在";
    public static final String CHANCE = "&dORandomCommandGroup &6>> &c概率错误";
    public static final String NUM = "&dORandomCommandGroup &6>> &c执行次数错误";
    public static final String COMMAND = "&dORandomCommandGroup &6>> &c指令输入错误";
}

public class ORandomCommandGroupCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) {
                return true;
            }
        }
        if (args.length == 0) {
            sendMessage(sender, "&dORandomCommandGroup &6>> &a/orcg <player_name> <one/all> <group_name> <chance> [num1] [num2]");
            sendMessage(sender, "&dORandomCommandGroup &6>> &a/orcg <perm> <player_name> <one/all> <group_name1> <one/all> <group_name2> <chance> [num1] [num2]");
            return true;
        }
        if (args.length == 1 && "reload".equalsIgnoreCase(args[0])) {
            if (sender instanceof Player) {
                sendMessage(sender, "&dORandomCommandGroup &6>> &a载入 &b" + ORandomCommandGroup.getInstance().load() + " &a条指令组");
            }
            return true;
        }
        if (args.length == 4 || args.length == 5 || args.length == 6) {
            /*
             * /orcg <player_name> <one/all> <group_name> <chance> [num1] [num2]
             * 0 player_name
             * 1 one/all
             * 2 group_name
             * 3 chance
             * 4 num1
             * 5 num2
             */
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sendMessage(sender, ErrorMessage.NOT_FOUND_PLAYER);
                return true;
            }
            String type = args[1];
            if (!("one".equalsIgnoreCase(type) || "all".equalsIgnoreCase(type))) {
                sendMessage(sender, ErrorMessage.TYPE);
                return true;
            }
            String groupName = args[2];
            if (!ORandomCommandGroup.getInstance().getGroupSet().contains(groupName)) {
                sendMessage(sender, ErrorMessage.NOT_FOUND_GROUP);
                return true;
            }
            double chance = 0.0D;
            try {
                chance = Double.parseDouble(args[3]);
            } catch (NumberFormatException e) {
                sendMessage(sender, ErrorMessage.CHANCE);
                e.printStackTrace();
            }
            if (chance <= 0.0D || chance > 100.0D) {
                sendMessage(sender, ErrorMessage.CHANCE);
                return true;
            }
            if (args.length == 4) {
                randomGroup(player, chance, ORandomCommandGroup.getInstance().getCommandGroups().get(groupName), "one".equalsIgnoreCase(type));
            } else if (args.length == 5) {
                int num = -1;
                try {
                    num = Integer.parseInt(args[4]);
                } catch (NumberFormatException e) {
                    sendMessage(sender, ErrorMessage.NUM);
                    e.printStackTrace();
                }
                if (num < 0) {
                    sendMessage(sender, ErrorMessage.NUM);
                    return true;
                }
                randomGroup(player, chance, ORandomCommandGroup.getInstance().getCommandGroups().get(groupName), "one".equalsIgnoreCase(type), num);
            } else {
                int num1 = -1, num2 = -1;
                try {
                    num1 = Integer.parseInt(args[4]);
                    num2 = Integer.parseInt(args[5]);
                } catch (NumberFormatException e) {
                    sendMessage(sender, ErrorMessage.NUM);
                    e.printStackTrace();
                }
                if (num1 > num2 || num1 < 0) {
                    sendMessage(sender, ErrorMessage.NUM);
                    return true;
                }
                randomGroup(player, chance, ORandomCommandGroup.getInstance().getCommandGroups().get(groupName), "one".equalsIgnoreCase(type), num1, num2);
            }
            return true;
        }
        if (args.length == 7 || args.length == 8 || args.length == 9) {
            /*
             * /orcg <perm> <player_name> <one/all> <group_name1> <one/all> <group_name2> <chance> [num1] [num2]
             * 0 perm
             * 1 player_name
             * 2 one/all
             * 3 group_name1
             * 4 one/all
             * 5 group_name2
             * 6 chance
             * 7 num1
             * 8 num2
             */
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                sendMessage(sender, ErrorMessage.NOT_FOUND_PLAYER);
                return true;
            }
            String perm = args[0];
            String type, groupName;
            if (player.hasPermission(perm)) {
                type = args[2];
                groupName = args[3];
            } else {
                type = args[4];
                groupName = args[5];
            }
            if (!("one".equalsIgnoreCase(type) || "all".equalsIgnoreCase(type))) {
                sendMessage(sender, ErrorMessage.TYPE);
                return true;
            }
            if (!ORandomCommandGroup.getInstance().getGroupSet().contains(groupName)) {
                sendMessage(sender, ErrorMessage.NOT_FOUND_GROUP);
                return true;
            }
            double chance = 0.0D;
            try {
                chance = Double.parseDouble(args[6]);
            } catch (NumberFormatException e) {
                sendMessage(sender, ErrorMessage.CHANCE);
                e.printStackTrace();
            }
            if (chance <= 0.0D || chance > 100.0D) {
                sendMessage(sender, ErrorMessage.CHANCE);
                return true;
            }
            if (args.length == 7) {
                randomGroup(player, chance, ORandomCommandGroup.getInstance().getCommandGroups().get(groupName), "one".equalsIgnoreCase(type));
            } else if (args.length == 8) {
                int num = -1;
                try {
                    num = Integer.parseInt(args[7]);
                } catch (NumberFormatException e) {
                    sendMessage(sender, ErrorMessage.NUM);
                    e.printStackTrace();
                }
                if (num < 0) {
                    sendMessage(sender, ErrorMessage.NUM);
                    return true;
                }
                randomGroup(player, chance, ORandomCommandGroup.getInstance().getCommandGroups().get(groupName), "one".equalsIgnoreCase(type), num);
            } else {
                int num1 = -1, num2 = -1;
                try {
                    num1 = Integer.parseInt(args[7]);
                    num2 = Integer.parseInt(args[8]);
                } catch (NumberFormatException e) {
                    sendMessage(sender, ErrorMessage.NUM);
                    e.printStackTrace();
                }
                if (num1 > num2 || num1 < 0) {
                    sendMessage(sender, ErrorMessage.NUM);
                    return true;
                }
                randomGroup(player, chance, ORandomCommandGroup.getInstance().getCommandGroups().get(groupName), "one".equalsIgnoreCase(type), num1, num2);
            }
            return true;
        }
        sendMessage(sender, ErrorMessage.COMMAND);
        return false;
    }

    public static void sendMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            sendPlayerMessage((Player)sender, message);
        } else {
            sendConsoleMessage(message);
        }
    }

    public static void randomGroup(Player player, double chance, List<String> commands, boolean type) {
        randomGroup(player, chance, commands, type, 1);
    }

    public static void randomGroup(Player player, double chance, List<String> commands, boolean type, int num1, int num2) {
        int cnt = RandomUtils.nextInt(num2 - num1 + 1) + num1;
        randomGroup(player, chance, commands, type, cnt);
    }
    public static void randomGroup(Player player, double chance, List<String> commands, boolean type, int cnt) {
        if (commands.isEmpty()) {
            return;
        }
        while (cnt > 0) {
            if (!getChance(chance)) {
                cnt--;
                continue;
            }
            if (type) {
                int index = RandomUtils.nextInt(commands.size());
                String command = commands.get(index);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formatString(command, player));
            } else {
                commands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formatString(command, player)));
            }
            cnt--;
        }
    }

    public static String formatString(String string, Player player) {
        return string.replace("%player%", player.getName());
    }

    public static boolean getChance(double chance) {
        double k = Math.random();
//        Bukkit.getLogger().info(chance / 100.0D + " " + k);
        return (k <= chance / 100.0D);
    }

}
