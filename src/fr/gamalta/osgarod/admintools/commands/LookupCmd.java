package fr.gamalta.osgarod.admintools.commands;

import fr.gamalta.lib.message.Message;
import fr.gamalta.osgarod.admintools.AdminTools;
import fr.gamalta.osgarod.admintools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class LookupCmd implements CommandExecutor {

    private AdminTools main;
    private Utils utils;

    public LookupCmd(AdminTools main) {
        this.main = main;
        utils = new Utils(main);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission(main.settingsCFG.getString("Permission.Lookup"))) {

            if (args.length > 0) {

                switch (args[0].toLowerCase()) {

                    case "names":

                        if (sender.hasPermission(main.settingsCFG.getString("Permission.Names"))) {

                            if (args.length > 1) {

                                ArrayList<JSONObject> names = null;
                                String targetName = null;

                                if (args[1].length() > 16) {

                                    String uuidStr = args[1];

                                    if (!args[1].contains("-")) {

                                        StringBuilder builder = new StringBuilder(args[1].trim());
                                        builder.insert(20, "-");
                                        builder.insert(16, "-");
                                        builder.insert(12, "-");
                                        builder.insert(8, "-");
                                        uuidStr = builder.toString();
                                    }

                                    try {

                                        UUID uuid = UUID.fromString(uuidStr);
                                        targetName = Bukkit.getOfflinePlayer(uuid).getName();
                                        names = utils.getPlayerNames(uuid);

                                    } catch (IllegalArgumentException e) {

                                        sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Names.InvalidUUID").create());

                                    }
                                } else {

                                    @SuppressWarnings("deprecation")
                                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                                    targetName = target.getName();
                                    names = utils.getPlayerNames(target.getUniqueId());
                                }

                                if (!names.isEmpty()) {

                                    sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Names.FirstMessage").replace("%player%", targetName).create());

                                    for (JSONObject object : names) {

                                        if (object.has("changedToAt")) {

                                            sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Names.Format").replace("%pseudo%", object.getString("name")).replace("%date%", DateFormat.getDateInstance(0, new Locale("FR", "fr")).format(object.getDouble("changedToAt"))).create());

                                        } else {
                                            sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Names.FormatFirstName").replace("%pseudo%", object.getString("name")).create());

                                        }
                                    }

                                    sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Names.LastMessage").create());

                                } else {

                                    sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Names.PlayerNotFound").create());

                                }
                            } else {

                                sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Names.Usage").create());
                            }
                        } else {

                            sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Names.NoPermission").create());
                        }

                        break;

                    case "ip":

                        if (sender.hasPermission(main.settingsCFG.getString("Permission.Ip"))) {
                            if (args.length > 1) {

                                /*try {
                                    //.getIpInformation();
                                } catch (IOException e) {

                                    sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Ip.Failled").create());

                                }*/

                            } else {
                                sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Ip.Usage").create());

                            }
                        } else {
                            sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Ip.NoPermission").create());

                        }
                        break;

                    default:
                        break;
                }
            } else {

                sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.Usage").create());
            }
        } else {

            sender.spigot().sendMessage(new Message(main, main.settingsCFG, "Lookup.NoPermission").create());
        }

        return true;
    }
}
