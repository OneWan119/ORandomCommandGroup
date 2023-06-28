package com.onewan.orandomcommandgroup;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.onewan.orandomcommandgroup.MessageUtil.sendConsoleMessage;

/**
 * @author OneWan
 * @date 2023/6/27 21:00
 **/
public class ORandomCommandGroup extends JavaPlugin {

    private static ORandomCommandGroup instance;
    private HashMap<String, List<String>> commandGroups;
    private Set<String> groupSet;

    @Override
    public void onEnable() {
        instance = this;
        long time = System.currentTimeMillis();
        sendConsoleMessage("&6ORandomCommandGroup - 开始载入");
        getCommand("ORandomCommandGroup").setExecutor(new ORandomCommandGroupCmd());
        load();
        sendConsoleMessage("&6ORandomCommandGroup &a加载完毕 &b共耗时 &c" + (System.currentTimeMillis() - time) + " &bms");
    }

    public int load() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveResource("config.yml", true);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        commandGroups = new HashMap<>();
        groupSet = config.getConfigurationSection("RandomCommandGroups").getKeys(false);
        groupSet.forEach(name -> {
            commandGroups.put(name, config.getStringList("RandomCommandGroups." + name));
            sendConsoleMessage(" &a载入 &b" + name);
        });
        sendConsoleMessage("&e一共载入 &c" + groupSet.size() + " &e条");
        return groupSet.size();
    }

    @Override
    public  void onDisable() {

    }

    public static ORandomCommandGroup getInstance() {
        return instance;
    }

    public HashMap<String, List<String>> getCommandGroups() {
        return commandGroups;
    }

    public Set<String> getGroupSet() {
        return groupSet;
    }
}
