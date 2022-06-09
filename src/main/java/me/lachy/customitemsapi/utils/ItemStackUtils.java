package me.lachy.customitemsapi.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemStackUtils {

    public static String encodeInventory(ItemStack[] is) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(is.length);

            for (ItemStack itemStack : is) {
                dataOutput.writeObject(itemStack);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Could not convert inventory to base64.", e);
        }
    }

    public static ItemStack[] decodeInventory(String inventoryData) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(inventoryData));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] is = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < is.length; i++) {
                is[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return is;

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new IOException("Could not decode inventory.", e);
        }
    }

}
