package com.minespaceships.mod.items;

import com.minespaceships.util.PhaserUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemPhaser extends ItemBow{

	public ItemPhaser(){
	
	 this.maxStackSize = 1;
     this.setMaxDamage(384);
     this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	
	 public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft)
	    {
	        int j = this.getMaxItemUseDuration(stack) - timeLeft;
	       int charge = 1;
//	       if (j!=0) {
//			charge = (j+2/(j))-10;
//			System.out.println(charge);
//			System.out.println(j+"charged");
//	       }
	       
	       boolean flag = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

	        if (flag || playerIn.inventory.hasItem(Items.redstone))
	        {
	        	
	        	
	        	worldIn.spawnParticle(EnumParticleTypes.FLAME,playerIn.posX, playerIn.posY+1, playerIn.posZ, playerIn.getLookVec().xCoord, playerIn.getLookVec().yCoord, playerIn.getLookVec().zCoord,3);
	            PhaserUtils.shoot(playerIn.getPosition().add(0, 1, 0), playerIn.getLookVec(),0.5+j/10, 80+j, worldIn);

	            stack.damageItem(1, playerIn);
	            worldIn.playSoundAtEntity(playerIn, "random.bow", 20.0F, 5.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 4 * 0.5F);
	        }
	        
	        
	        if (!flag) {
	        	 playerIn.inventory.consumeInventoryItem(Items.redstone);
			}
	        
	    }
	        
	        public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	        {
	            net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(playerIn, itemStackIn);
	            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return event.result;

	            if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(Items.redstone))
	            {
	            	worldIn.spawnParticle(EnumParticleTypes.FLAME,playerIn.posX, playerIn.posY+1, playerIn.posZ, playerIn.getLookVec().xCoord*2, playerIn.getLookVec().yCoord*2, playerIn.getLookVec().zCoord*2, 30);
	                playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
	            }

	            return itemStackIn;
	        }
	    
}
