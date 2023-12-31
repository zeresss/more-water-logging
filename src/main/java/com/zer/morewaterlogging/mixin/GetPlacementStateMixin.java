package com.zer.morewaterlogging.mixin;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
    AbstractRedstoneGateBlock.class, AnvilBlock.class, BannerBlock.class, BedBlock.class,
    BellBlock.class, DoorBlock.class, EndPortalFrameBlock.class, EndRodBlock.class, FenceGateBlock.class,
    HopperBlock.class, LeavesBlock.class, LecternBlock.class, PistonBlock.class, RedstoneWireBlock.class,
    RepeaterBlock.class, SkullBlock.class, StonecutterBlock.class, WallBannerBlock.class, WallMountedBlock.class,
    WallRedstoneTorchBlock.class, WallSkullBlock.class, WallTorchBlock.class
})
public abstract class GetPlacementStateMixin {

    /**
     * @since 1.0.0
     * makes block initially waterlogged when placed underwater
     */
    @Inject(method = "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;", at = @At("RETURN"), cancellable = true)
    public void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        BlockState returnValue = cir.getReturnValue();
        if (this instanceof NewWaterloggable && returnValue != null && returnValue.contains(Properties.WATERLOGGED) && ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER))
            cir.setReturnValue(returnValue.with(Properties.WATERLOGGED, true));
    }

}