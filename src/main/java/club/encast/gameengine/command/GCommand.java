package club.encast.gameengine.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class GCommand {

    private String name;
    private List<String> permissions;
    private String description;
    private String usage;
    private List<String> aliases;
    private boolean consoleAllowed;

    public GCommand(String name) {
        this.name = name;
        this.permissions = new ArrayList<>();
        this.description = "No description";
        this.usage = "null";
        this.aliases = new ArrayList<>();
        this.consoleAllowed = true;
    }

    public final String getName() {
        return name;
    }

    public final List<String> getPermissions() {
        return permissions;
    }

    public final void addPermission(String permission) {
        if(permission != null) {
            permissions.add(permission);
        }
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final String getUsage() {
        return usage;
    }

    public final void setUsage(String usage) {
        this.usage = usage;
    }

    public final String getUsageMessage() {
        return "§cUsage: " + usage;
    }

    public final List<String> getAliases() {
        return aliases;
    }

    public final void addAlias(String alias) {
        if(alias != null) {
            aliases.add(alias);
        }
    }

    public final boolean isConsoleAllowed() {
        return consoleAllowed;
    }

    public final void setConsoleAllowed(boolean consoleAllowed) {
        this.consoleAllowed = consoleAllowed;
    }

    public abstract void execute(CommandSender sender, String[] args);
}
