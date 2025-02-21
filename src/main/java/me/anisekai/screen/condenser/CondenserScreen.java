package me.anisekai.screen.condenser;

import com.mojang.blaze3d.systems.RenderSystem;
import me.anisekai.AnisekaiMod;
import me.anisekai.packets.CondenserQueryPacket;
import me.anisekai.recipes.CondenserRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@SuppressWarnings("AssignmentToSuperclassField")
@Environment(value = EnvType.CLIENT)
public class CondenserScreen extends HandledScreen<CondenserScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of(AnisekaiMod.MOD_ID, "textures/gui/condenser.png");

    private final WidgetButtonPage[] offers     = new WidgetButtonPage[7];
    private       int                indexStartOffset;
    private       boolean            scrolling;
    private       boolean            hasQueried = false;

    public CondenserScreen(CondenserScreenHandler handler, PlayerInventory inventory, Text title) {

        super(handler, inventory, title);
        this.backgroundWidth  = 276;
        this.backgroundHeight = 166;
        this.init();

        this.playerInventoryTitleX = 107;
        this.playerInventoryTitleY = this.backgroundHeight - 92;
    }

    @Override
    protected void init() {

        super.init();

        int k = this.y + 16 + 2;
        for (int l = 0; l < 7; ++l) {
            int finalL = l;

            Runnable         action = () -> this.sendRecipeClick(finalL);
            WidgetButtonPage wdp    = new WidgetButtonPage(this.x + 5, k, l, action);

            this.offers[l] = this.addDrawableChild(wdp);
            k += 20;
        }
    }

    private void sendRecipeClick(int index) {

        List<CondenserRecipeRenderer> recipes     = this.handler.getRenderers();
        int                           recipeIndex = index + this.indexStartOffset;
        Identifier                    id          = recipes.get(recipeIndex).getRecipe().id();
        this.handler.sendRecipeSelection(id);
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
                512,
                256
        );
    }

    private void renderScrollbar(DrawContext context, int x, int y, int recipeAmount) {

        int i = recipeAmount + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int m = Math.min(113, this.indexStartOffset * k);
            if (this.indexStartOffset == i - 1) {
                m = 113;
            }
            context.drawTexture(TEXTURE, x + 94, y + 18 + m, 0, 276.0f, 0.0f, 6, 27, 512, 256);
        } else {
            context.drawTexture(TEXTURE, x + 94, y + 18, 0, 282.0f, 0.0f, 6, 27, 512, 256);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        if (!this.hasQueried) {
            CondenserQueryPacket.clientSend(this.handler.getBlockPos());
            this.hasQueried = true;
        }

        this.handler.tick();
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        this.drawItemSpeed(context);
        this.drawArrow(context);

        List<CondenserRecipeRenderer> recipes = this.handler.getRenderers();

        if (!recipes.isEmpty()) {
            int l = this.x + 8;
            int k = this.y + 19;
            this.renderScrollbar(context, this.x, this.y, recipes.size());
            int selectedIndex = -1;


            for (int m = 0; m < recipes.size(); m++) {
                if (this.canScroll() && m < this.indexStartOffset || m >= this.indexStartOffset + 7) {
                    continue;
                }
                CondenserRecipeRenderer      renderer = recipes.get(m);
                RecipeEntry<CondenserRecipe> recipe   = renderer.getRecipe();

                if (renderer.isSelected()) {
                    selectedIndex = m;
                }

                context.getMatrices().push();
                context.getMatrices().translate(0.0f, 0.0f, 100.0f);

                this.renderItem(context, renderer.getApply(), l, k);
                this.renderItem(context, renderer.getOnto(), l + 18, k);
                this.renderItem(context, renderer.getTool(), l + 36, k);
                this.renderItem(context, recipe.value().result(), l + 64, k);

                context.getMatrices().pop();
                k += 20;
            }

            for (int i = 0; i < this.offers.length; i++) {
                WidgetButtonPage wbp         = this.offers[i];
                int              offsetIndex = this.indexStartOffset + i;
                wbp.setFocused(offsetIndex == selectedIndex);

                if (wbp.isSelected()) {
                    wbp.renderTooltip(context, mouseX, mouseY);
                }
                wbp.visible = wbp.index < recipes.size();
            }

            RenderSystem.enableDepthTest();
        }

        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderItem(DrawContext context, ItemStack stack, int x, int y) {

        context.drawItemWithoutEntity(stack, x, y);
        context.drawItemInSlot(this.textRenderer, stack, x, y);
    }

    private void drawArrow(DrawContext context) {

        int x         = 201, y = 38, u = 276, v = 36, h = 17, w = 24;
        int realWidth = 0;

        if (this.handler.isWorking()) {
            realWidth = Math.round(this.handler.getProgress() * w);
        }

        context.drawTexture(TEXTURE, this.x + x, this.y + y, u, v, realWidth, h, 512, 256);
    }

    private void drawItemSpeed(DrawContext context) {

        String text = String.format("%.2f i/s", 0f);

        if (this.handler.isWorking()) {
            text = String.format("%.2f i/s", this.handler.getWorkingSpeed());
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

    private boolean canScroll() {

        return this.handler.getRecipes().size() > 7;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {

        if (this.canScroll()) {
            int j = this.handler.getRecipes().size() - 7;
            this.indexStartOffset = MathHelper.clamp((int) ((double) this.indexStartOffset - verticalAmount), 0, j);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        int i = this.handler.getRecipes().size();
        if (this.scrolling) {
            int   j = this.y + 18;
            int   k = j + 139;
            int   l = i - 7;
            float f = ((float) mouseY - (float) j - 13.5f) / ((float) (k - j) - 27.0f);
            f                     = f * (float) l + 0.5f;
            this.indexStartOffset = MathHelper.clamp((int) f, 0, l);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        this.scrolling = false;
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        if (this.canScroll() && mouseX > (double) (i + 94) && mouseX < (double) (i + 94 + 6) && mouseY > (double) (j + 18) && mouseY <= (double) (j + 18 + 139 + 1)) {
            this.scrolling = true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Environment(value = EnvType.CLIENT)
    class WidgetButtonPage extends ButtonWidget {

        final int index;

        public WidgetButtonPage(int x, int y, int index, Runnable action) {

            super(x, y, 88, 20, ScreenTexts.EMPTY, button -> action.run(), DEFAULT_NARRATION_SUPPLIER);
            this.index   = index;
            this.visible = false;
        }

        public void renderTooltip(DrawContext context, int x, int y) {

            CondenserScreen               screen  = CondenserScreen.this;
            List<CondenserRecipeRenderer> recipes = screen.handler.getRenderers();

            if (this.hovered && recipes.size() > this.index + screen.indexStartOffset) {
                CondenserRecipeRenderer      renderer = recipes.get(this.index + screen.indexStartOffset);
                RecipeEntry<CondenserRecipe> recipe   = renderer.getRecipe();
                if (x > 8 && x < this.getX() + 20) {
                    ItemStack itemStack = renderer.getApply();
                    context.drawItemTooltip(screen.textRenderer, itemStack, x, y);
                } else if (x < this.getX() + 38 && x > this.getX() + 22) {
                    ItemStack itemStack = renderer.getOnto();
                    if (!itemStack.isEmpty()) {
                        context.drawItemTooltip(screen.textRenderer, itemStack, x, y);
                    }
                } else if (x < this.getX() + 54 && x > this.getX() + 40) {
                    ItemStack itemStack = renderer.getTool();
                    if (!itemStack.isEmpty()) {
                        context.drawItemTooltip(screen.textRenderer, itemStack, x, y);
                    }
                } else if (x > this.getX() + 65) {
                    ItemStack itemStack = recipe.value().result();
                    context.drawItemTooltip(screen.textRenderer, itemStack, x, y);
                }
            }
        }

    }

}
