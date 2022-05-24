package lb.essentials.main;

import lb.essentials.commands.CommandLight;
import lb.essentials.commands.CommandProfile;
import lb.essentials.commands.CommandTitle;
import lb.essentials.events.OnCraftItemEvent;
import lb.essentials.events.OnInteractInventory;
import lb.essentials.events.OnPortalEvent;
import lb.essentials.events.OnSendCommandEvent;
import lb.essentials.utils.FunctionsManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainEssentials extends JavaPlugin {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static MainEssentials instance;

    public static MainEssentials getPlugin() {
        return instance;
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnCraftItemEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPortalEvent(), this);
        getServer().getPluginManager().registerEvents(new OnInteractInventory(), this);
        getServer().getPluginManager().registerEvents(new OnSendCommandEvent(), this);
        console.sendMessage("§aLBEssentialsComplement: Eventos carregados com sucesso.");
    }

    public void loadingWorld() {
        Bukkit.getServer().createWorld(new WorldCreator("serverBuilds"));
        console.sendMessage("§aLBEssentialsComplement: Mundos carregados com sucesso.");
    }

    public FunctionsManager functions;

    public void registerCommands() {
        CommandLight light = new CommandLight(this, "lantern");
        CommandTitle title = new CommandTitle(this, "titles");
        CommandProfile profile = new CommandProfile(this, "profile");
        console.sendMessage("§aLBEssentialsComplement: Comandos carregados com sucesso.");
    }

    @Override
    public void onEnable() {
        instance = this;
        functions = new FunctionsManager();
        loadingWorld();
        registerEvents();
        registerCommands();
        console.sendMessage("§aLBEssentialsComplement: Plugin habilitado com sucesso.");
    }

    @Override
    public void onDisable() {
        console.sendMessage("§cLBEssentialsComplement: Plugin desabilitado com sucesso.");
    }

    public FunctionsManager getFunctions() {
        return functions;
    }
}
