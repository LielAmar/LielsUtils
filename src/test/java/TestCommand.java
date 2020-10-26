import com.lielamar.lielsutils.commands.Command;
import org.bukkit.command.CommandSender;

public class TestCommand extends Command {

    public TestCommand(String name) {
        super(name);
    }

    public String getDescription() {
        return "A test command";
    }

    public String[] getAliases() {
        return new String[] { "testcommand", "testcmd" };
    }

    public String[] getPermissions() {
        return new String[] { "lielsutils.test" };
    }

    public void execute(CommandSender sender, String[] args) {
        return;
    }
}
