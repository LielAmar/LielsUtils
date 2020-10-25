import com.lielamar.lielsutils.SpigotUtils;
import com.lielamar.lielsutils.gui.GUI;
import com.lielamar.lielsutils.gui.GUIItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TestGUI implements GUI {

    private final String NAME;
    private final int ROWS;

    private Inventory inventory;
    private GUIItem[] items;

    public TestGUI() {
        this.NAME = ChatColor.GRAY + "Test GUI";
        this.ROWS = 3;

        this.build();
    }

    @Override
    public GUIItem[] getGUIItems() {
        return this.items;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void onGUIClick(Player player, int slot) {
        GUIItem clickedItem = this.items[slot];
        if(clickedItem == null) return;

        clickedItem.onGUIItemClick(player, this);
    }

    @Override
    public void build() {
        this.inventory = Bukkit.createInventory(this, this.ROWS, this.NAME);
        this.items = new GUIItem[this.ROWS*9];

        this.items[0] = new GUIItem(SpigotUtils.getItem(Material.DIAMOND, 1, ChatColor.GREEN + "Test Item", new String[] {
                ChatColor.GRAY + "test lore", "", ChatColor.YELLOW + "test lore 2" })) {
            @Override
            public void onGUIItemClick(Player player, GUI gui) {
                player.sendMessage("This is GUI Item - " + this.getItemStack().getItemMeta().getDisplayName() + "!");
            }
        };
    }
}
