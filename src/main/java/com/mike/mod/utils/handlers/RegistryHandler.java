package com.mike.mod.utils.handlers;

import com.mike.mod.items.ItemBase;
import com.mike.mod.main;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
	public static Item bullet;

	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, main.MOD_ID);

	public static void init(){
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	//items

	//bullet = new ItemThrowingRock().setUnlocalizedName("throwingRock").setMaxStackSize(18).setCreativeTab(CreativeTabs.tabCombat);

	public static final RegistryObject<Item> GLOCK_18 = ITEMS.register("g18", ItemBase::new);
	public static final RegistryObject<Item> AK47 = ITEMS.register("ak47", ItemBase::new);
	public static final RegistryObject<Item> SPRINGFIELD = ITEMS.register("springfield", ItemBase::new);
	public static final RegistryObject<Item> CHINA_LAKE = ITEMS.register("china_lake", ItemBase::new);
	public static final RegistryObject<Item> AMMUNITION = ITEMS.register("ammo", ItemBase::new);


}
