/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package me.anisekai.inventories.constrained;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@SuppressWarnings("AssignmentToSuperclassField")
@Environment(value = EnvType.CLIENT)
public class ConstrainedContainerScreen extends HandledScreen<ConstrainedContainerScreenHandler> implements ScreenHandlerProvider<ConstrainedContainerScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of("minecraft", "textures/gui/container/generic_54.png");
    private final        int        rows;

    public ConstrainedContainerScreen(ConstrainedContainerScreenHandler handler, PlayerInventory inventory, Text title) {

        super(handler, inventory, title);
        this.rows                  = 3;
        this.backgroundHeight      = 114 + this.rows * 18;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.rows * 18 + 17);
        context.drawTexture(TEXTURE, i, j + this.rows * 18 + 17, 0, 126, this.backgroundWidth, 96);
    }

}

