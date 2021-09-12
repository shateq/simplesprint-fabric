package shateq.mods.fabric.simplesprint;

import dev.lambdaurora.spruceui.util.Nameable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

public enum NotificationMode implements Nameable {
    ACTIONBAR(new TranslatableText(SimpleSprint.id+".notification.actionbar"), Formatting.GREEN),
    CHAT(new TranslatableText(SimpleSprint.id+".notification.chat"), Formatting.AQUA),
    SOUND(new TranslatableText(SimpleSprint.id+".notification.sound"), Formatting.LIGHT_PURPLE);

    private final Text text;

    NotificationMode(@NotNull Text text, Formatting formatting) {
        this.text = text.copy().formatted(formatting);
    }

    public NotificationMode next() {
        var v = values();
        if (v.length == this.ordinal() + 1)
            return v[0];
        return v[this.ordinal() + 1];
    }

    @Override
    public String getName() {
        return null;
    }

    public Text getTranslatedText() {
        return this.text;
    }
}