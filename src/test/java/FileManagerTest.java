import com.lielamar.lielsutils.bukkit.files.FileManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileManagerTest {

    public static void main(String[] args) {
        FileManager fileManager = new FileManager(new JavaPlugin() {
            @Override
            protected @NotNull File getFile() {
                return super.getFile();
            }
        });

        fileManager.getConfig("messages.yml").toString();

    }

}
