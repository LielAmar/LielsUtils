import com.lielamar.lielsutils.SpigotUtils;
import com.lielamar.lielsutils.gui.GUI;
import com.lielamar.lielsutils.gui.GUIItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TestGUI2 implements GUI {

    private final String NAME;
    private final int ROWS;

    private Inventory inventory;
    private GUIItem[] items;
    private Player player;

    public TestGUI2() {
        this.NAME = ChatColor.GRAY + "Test GUI";
        this.ROWS = 3;
    }

    @Override
    public GUIItem[] getGUIItems() {
        return this.items;
    }

    @Override
    public Inventory getInventory() {
        this.build();
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

            this.items[0] = new GUIItem(SpigotUtils.getItem(Material.GOLD_INGOT, 1, ChatColor.GREEN + "Test Item", new String[] {
                    ChatColor.GRAY + "Hi " + ChatColor.AQUA + this.player.getName() + "!", "", ChatColor.GRAY + "I know your name!" })) {
                @Override
                public void onGUIItemClick(Player player, GUI gui) {
                    player.sendMessage("This is GUI Item 2 - " + this.getItemStack().getItemMeta().getDisplayName() + "!");
                }
            };

        this.items[1] = new GUIItem(SpigotUtils.getItem(Material.IRON_INGOT, 2, ChatColor.RED + "Test Item 3", new String[] {
                ChatColor.DARK_GRAY + "Hi " + ChatColor.AQUA + this.player.getName() + "!", "", ChatColor.DARK_GRAY + "I know your name!" })) {
            @Override
            public void onGUIItemClick(Player player, GUI gui) {
                player.sendMessage("This is GUI Item 2 (2) - " + this.getItemStack().getItemMeta().getDisplayName() + "!");
            }
        };
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
