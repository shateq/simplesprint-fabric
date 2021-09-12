package shateq.mods.fabric.simplesprint.mixin;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shateq.mods.fabric.simplesprint.ConfigurationScreen;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At("RETURN"))
    private void initWidgets(CallbackInfo ci) {
        this.addDrawableChild(new SpruceButtonWidget(Position.of(this.width - 82, 1), 80, 20,
                new LiteralText("Sprint"),
                btn -> this.client.setScreen(new ConfigurationScreen(this))).asVanilla());
    }
}