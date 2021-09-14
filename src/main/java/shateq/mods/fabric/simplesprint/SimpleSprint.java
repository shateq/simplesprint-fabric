package shateq.mods.fabric.simplesprint;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class SimpleSprint implements ClientModInitializer {
    public static String id = "simplesprint";
    private KeyBinding toggleKeyBinding;
    public static boolean enabled;
    public static NotificationMode mode;

    @Override
    public void onInitializeClient() {
        enabled = false;
        mode = NotificationMode.ACTIONBAR;
        toggleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key."+id+".sprint", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y /* The keycode of the key */, "key.categories.movement"));

        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("sprint").executes(c -> {
                    MinecraftClient.getInstance().execute(()-> {
                        c.getSource().sendFeedback(Text.of("§7§lPoof! §bSimpleSprint - Fabric §7is here!"));
//                        c.getSource().getClient().setScreen(new SimpleSprintScreen(MinecraftClient.getInstance().currentScreen));
                    });
                    return 1;
                })
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.player != null) {
                final KeyBinding key = client.options.keySprint;

                // Toggle
                if (toggleKeyBinding.wasPressed()) {
                    key.setPressed(false);
                    toggleSprint(client.player);
                }
                // Work
                if(enabled) {
                    key.setPressed(true);
                }
            }
        });
    }

    private void toggleSprint(final ClientPlayerEntity player) {
        if(enabled) {
            switch (mode) {
                case ACTIONBAR -> player.sendMessage(new TranslatableText(id+".status", "§cOFF"), true);
                case CHAT -> player.sendMessage(new TranslatableText(id+".status", "§cOFF"), false);
                case SOUND -> player.playSound(SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, 0.5f, 1f);
            }
            enabled = false;

        } else {
            switch (mode) {
                case ACTIONBAR -> player.sendMessage(new TranslatableText(id+".status", "§aON"), true);
                case CHAT -> player.sendMessage(new TranslatableText(id+".status", "§aON"), false);
                case SOUND -> player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1f);
            }
            enabled = true;
        }
    }
}