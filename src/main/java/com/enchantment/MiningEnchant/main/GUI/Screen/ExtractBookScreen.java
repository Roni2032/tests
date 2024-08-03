package com.enchantment.MiningEnchant.main.GUI.Screen;

import com.enchantment.MiningEnchant.main.GUI.Menu.ExtractBookMenu;
import com.enchantment.MiningEnchant.main.MiningEnchant;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtractBookScreen extends AbstractContainerScreen<ExtractBookMenu> {

    private static final ResourceLocation MENU_TEXTURE = new ResourceLocation(MiningEnchant.MOD_ID,"textures/gui/extract_book_menu.png");

    private static final Component INVENTORY_TITLE = Component.translatable("container." + MiningEnchant.MOD_ID + ".inventory");

    public ExtractBookScreen(ExtractBookMenu blockMenu, Inventory inventory, Component component) {
        super(blockMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        int setX = (this.width  - this.imageWidth) / 2;
        int setY = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(MENU_TEXTURE,setX,setY,0,0,this.imageWidth,this.imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int w, int h, float f) {
        this.renderBackground(graphics);
        super.render(graphics, w, h, f);
        this.renderTooltip(graphics, w, h);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int w, int h) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, INVENTORY_TITLE, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }
}
