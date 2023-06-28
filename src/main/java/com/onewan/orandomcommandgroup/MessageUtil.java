package com.onewan.orandomcommandgroup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author OneWan
 * @date 2023/6/25 19:06
 **/
public class MessageUtil {

    public static void sendConsoleMessage(String message) {
        Bukkit.getLogger().info(message.replace('&', '§'));
    }
    public static void sendPlayerMessage(Player player, String message) {
        player.sendMessage(message.replace('&', '§'));
    }

}
