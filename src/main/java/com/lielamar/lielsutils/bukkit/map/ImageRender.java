package com.lielamar.lielsutils.bukkit.map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;

public class ImageRender extends MapRenderer {

    private final SoftReference<BufferedImage> cacheImage;

    private boolean hasRendered = false;

    public ImageRender(String url) throws IOException {
        this.cacheImage = new SoftReference<>(this.getImage(url));
    }

    public ImageRender(BufferedImage bufferedImage) throws IOException {
        this.cacheImage = new SoftReference<>(resize(bufferedImage, new Dimension(128, 128)));
    }

    @Override
    public void render(@NotNull MapView view, @NotNull MapCanvas canvas, @NotNull Player player) {
        if(this.hasRendered) return;

        view.setScale(MapView.Scale.CLOSEST);

        if(this.cacheImage != null && this.cacheImage.get() != null)
            canvas.drawImage(0, 0, this.cacheImage.get());
        else
            player.sendMessage(ChatColor.RED + "Attempted to render the image, but the cached image was null!");

        this.hasRendered = true;
    }

    private BufferedImage getImage(String url) throws IOException {
        boolean useCache = ImageIO.getUseCache();
        ImageIO.setUseCache(false);

        final BufferedImage originalImage = ImageIO.read(new URL(url).openStream());
        BufferedImage image = resize(originalImage, new Dimension(128, 128));

        ImageIO.setUseCache(useCache);
        return image;
    }

    private BufferedImage resize(final BufferedImage image, final Dimension size) throws IOException {
        final BufferedImage resized = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = resized.createGraphics();

        g.drawImage(image, 0, 0, size.width, size.height, null);
        g.dispose();
        return resized;
    }
}