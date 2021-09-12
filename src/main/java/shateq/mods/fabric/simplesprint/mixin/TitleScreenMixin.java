package shateq.mods.fabric.simplesprint.mixin;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shateq.mods.fabric.simplesprint.ConfigurationScreen;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        this.addDrawableChild(new SpruceButtonWidget(Position.of(0, 0), 100, 20, new LiteralText("SimpleSprint Fabric"),
                btn -> this.client.setScreen(new ConfigurationScreen(this))).asVanilla());
    }
}
