package shateq.mods.fabric.simplesprint;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.SpruceTexts;
import dev.lambdaurora.spruceui.option.SpruceBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceCyclingOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

public class ConfigurationScreen extends SpruceScreen {
    public static final String PROJECT = "https://github.com/shateq/simplesprint-fabric/";
    private final Screen parent;
    private final SpruceOption booleanOption;
    private final SpruceCyclingOption notificationOption;

    public ConfigurationScreen(@Nullable Screen parent) {
        super(new LiteralText("§f§lSimpleSprint §r- Fabric"));
        this.parent = parent;

        this.booleanOption = new SpruceBooleanOption(SimpleSprint.id+".option.enabled",
                () -> SimpleSprint.enabled,
                newValue -> SimpleSprint.enabled = newValue,
                new TranslatableText(SimpleSprint.id+".option.enabled.text"), true);

        this.notificationOption = new SpruceCyclingOption(SimpleSprint.id+".option.notification", type -> SimpleSprint.mode = SimpleSprint.mode.next(),
                option -> option.getDisplayText(SimpleSprint.mode.getTranslatedText()),
                new TranslatableText(SimpleSprint.id+".notification.tooltip", NotificationMode.ACTIONBAR.getTranslatedText(), NotificationMode.CHAT.getTranslatedText(), NotificationMode.SOUND.getTranslatedText()));
    }

    @Override
    protected void init() {
        super.init();
        int buttonHeight = 20;
        int textStart = this.height / 2 + 20;

        this.addDrawableChild(booleanOption.createWidget(Position.of(this, this.width / 2 - 90, this.height / 2 - 60), 180));
        this.addDrawableChild(notificationOption.createWidget(Position.of(this, this.width / 2 - 90, this.height / 2 - 30), 180));
        // Text
        var text = new LiteralText("");
        text.append(new TranslatableText(SimpleSprint.id+".info.title").formatted(Formatting.AQUA, Formatting.BOLD)).append("\n");
        text.append(new TranslatableText(SimpleSprint.id+".info.content"));
        var greatRead = this.addDrawableChild(new SpruceLabelWidget(Position.of(this, 0, textStart), text, this.width, true));
        var link = new SpruceLabelWidget(Position.of(this, 0, textStart + 5 + greatRead.getHeight()), new TranslatableText(SimpleSprint.id+".info.project").formatted(Formatting.YELLOW), this.width, label -> Util.getOperatingSystem().open(PROJECT), true);
        this.addDrawableChild(link); // Link

        this.addDrawableChild(new SpruceButtonWidget(Position.of(this, this.width / 2 - 75, this.height - 30), 150, buttonHeight, SpruceTexts.GUI_DONE, btn -> this.client.setScreen(this.parent)));
    }

    @Override
    public void onClose() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    @Override
    public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }
}
