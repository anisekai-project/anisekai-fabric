package me.anisekai.screen.condenser;

import me.anisekai.AnisekaiMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class CondenserScreen extends HandledScreen<CondenserScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(AnisekaiMod.MOD_ID, "textures/gui/condenser.png");

    public CondenserScreen(CondenserScreenHandler handler, PlayerInventory inventory, Text title) {

        super(handler, inventory, title);
        this.backgroundWidth  = 176;
        this.backgroundHeight = 149;
        this.init();

        this.playerInventoryTitleY = 56;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

        // u and v are x,y offset IN the texture to draw
        context.drawTexture(
                TEXTURE,
                this.x,
                this.y,
                0,
                0.0f,
                0.0f,
                this.backgroundWidth,
                this.backgroundHeight,
                200,
                149
        );
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);

        this.drawItemSpeed(context);
        this.drawArrow(context);
    }

    private void drawArrow(DrawContext context) {

        int x         = 110, y = 24, u = 176, v = 0, h = 17, w = 24;
        int realWidth = 0;

        if (this.handler.isDoingThings()) {
            realWidth = Math.round(this.handler.getCondensingProgress() * w);
        }

        context.drawTexture(TEXTURE, this.x + x, this.y + y, u, v, realWidth, h, 200, 149);
    }

    private void drawItemSpeed(DrawContext context) {

        String text = String.format("%.2f i/s", 0f);

        if (this.handler.isDoingThings()) {
            text = String.format("%.2f i/s", this.handler.getCondensingSpeed());
        }

        int textWidth = this.textRenderer.getWidth(text);

        int x = this.x + this.backgroundWidth - textWidth - 8;
        int y = this.y + this.playerInventoryTitleY;

        context.drawText(
                this.textRenderer,
                text,
                x,
                y,
                0x404040,
                false
        );
    }

}
