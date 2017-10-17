package club.encast.gameengine.command;

import club.encast.gameengine.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CommandHandler implements CommandExecutor {

    private Set<GCommand> commands;
    private CommandMap commandMap;
    private String consolePermissionDeniedMessage = "Only players can use this command.";
    private String permissionDeniedMessage = "§cYou are not allowed to use this command.";

    public CommandHandler() {
        commands = new HashSet<>();
        try {
            Field f = Class.forName("org.bukkit.craftbukkit." + Util.VERSION + ".CraftServer").getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<GCommand> getCommands() {
        return Collections.unmodifiableSet(commands);
    }

    public CommandMap getCommandMap() {
        return commandMap;
    }

    public String getConsolePermissionDeniedMessage() {
        return consolePermissionDeniedMessage;
    }

    public void setConsolePermissionDeniedMessage(String consolePermissionDeniedMessage) {
        this.consolePermissionDeniedMessage = consolePermissionDeniedMessage;
    }

    public String getPermissionDeniedMessage() {
        return permissionDeniedMessage;
    }

    public void setPermissionDeniedMessage(String permissionDeniedMessage) {
        this.permissionDeniedMessage = permissionDeniedMessage;
    }

    public void registerCommand(String prefix, GCommand command) {
        commands.add(command);
        commandMap.register(prefix, new Command(command.getName(), command.getDescription(), command.getUsageMessage(), command.getAliases()) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                onCommand(sender, this, label, args);
                return true;
            }
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        for(GCommand gc : commands) {
            boolean containedInAlias = false;
            for(String s : gc.getAliases()) {
                if(s.equalsIgnoreCase(label)) containedInAlias = true;
            }
            if(gc.getName().equalsIgnoreCase(label) || containedInAlias) {
                if(sender instanceof ConsoleCommandSender && !gc.isConsoleAllowed()) {
                    sender.sendMessage(consolePermissionDeniedMessage);
                    return true;
                }
                if(gc.getPermissions().size() >= 1) {
                    for(String permission : gc.getPermissions()) {
                        if(!sender.hasPermission(permission)) {
                            sender.sendMessage(permissionDeniedMessage);
                            return true;
                        }
                    }
                }
                gc.execute(sender, args);
            }
        }
        return true;
    }
}
