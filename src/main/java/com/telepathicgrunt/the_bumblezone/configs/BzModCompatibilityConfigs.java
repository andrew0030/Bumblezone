package com.telepathicgrunt.the_bumblezone.configs;

import com.telepathicgrunt.the_bumblezone.utils.ConfigHelper;
import com.telepathicgrunt.the_bumblezone.utils.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BzModCompatibilityConfigs
{
	public static class BzModCompatibilityConfigValues
	{
	    public ConfigValueListener<Boolean> allowPotionOfBeesCompat;
	    public ConfigValueListener<Boolean> allowSplashPotionOfBeesCompat;

		public ConfigValueListener<Boolean> spawnResourcefulBeesBeesMob;
		public ConfigValueListener<Boolean> spawnResourcefulBeesHoneycombVariants;
		public ConfigValueListener<Integer> RBGreatHoneycombRarityBeeDungeon;
		public ConfigValueListener<Double> RBOreHoneycombSpawnRateBeeDungeon;
		public ConfigValueListener<Integer> RBGreatHoneycombRaritySpiderBeeDungeon;
		public ConfigValueListener<Double> RBOreHoneycombSpawnRateSpiderBeeDungeon;
		public ConfigValueListener<Boolean> RBBeesWaxWorldgen;

	    public ConfigValueListener<Boolean> spawnProductiveBeesBeesMob;
	    public ConfigValueListener<Boolean> spawnProductiveBeesHoneycombVariants;
	    public ConfigValueListener<Integer> PBGreatHoneycombRarityBeeDungeon;
	    public ConfigValueListener<Double> PBOreHoneycombSpawnRateBeeDungeon;
	    public ConfigValueListener<Integer> PBGreatHoneycombRaritySpiderBeeDungeon;
	    public ConfigValueListener<Double> PBOreHoneycombSpawnRateSpiderBeeDungeon;

	    public BzModCompatibilityConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {

	        builder.push("Mod Compatibility Options");
					builder.push("Resourceful Bees Options");

					spawnResourcefulBeesBeesMob = subscriber.subscribe(builder
							.comment(" \r\n-----------------------------------------------------\r\n\r\n"
									+" Spawn Resourceful Bees in The Bumblezone alongside regular\r\n"
									+" bees at a 1/15th chance when spawning regular bees.\r\n")
							.translation("the_bumblezone.config.modcompat.resourcefulbees.spawnresourcefulbeesbeesmob")
							.define("spawnResourcefulBeesBeesMob", true));

					spawnResourcefulBeesHoneycombVariants = subscriber.subscribe(builder
						.comment(" \r\n-----------------------------------------------------\r\n\r\n"
								+" Spawn Resourceful Bees's various honeycomb variants in The Bumblezone\r\n"
								+" at all kinds of heights and height bands. Start exploring to find \r\n"
								+" where they spawn!"
								+" \r\n"
								+" NOTE: Will require a restart of the world to take effect. \r\n")
						.translation("the_bumblezone.config.modcompat.productivebees.spawnproductivebeeshoneycombvariants")
						.define("spawnResourcefulBeesHoneycombVariants", true));

					RBBeesWaxWorldgen = subscriber.subscribe(builder
						.comment(" \r\n-----------------------------------------------------\r\n\r\n"
								+" Spawn Resourceful Bees's Wax Block as part of The Bumblezone's worldgen.\r\n")
						.translation("the_bumblezone.config.modcompat.resourcefulbees.rbbeeswaxworldgen")
						.define("RBBeesWaxWorldgen", true));


					RBOreHoneycombSpawnRateBeeDungeon = subscriber.subscribe(builder
						.comment(" \r\n-----------------------------------------------------\r\n\r\n"
								+" How much of Bee Dungeons is made of ore-based honeycombs.\r\n"
								+" 0 is no or honeycombs, 1 is max ore honeycombs, and default is 0.3D\r\n")
						.translation("the_bumblezone.config.productivebees.RBorehoneycombspawnratebeedungeon")
						.defineInRange("RBOreHoneycombSpawnRateBeeDungeon", 0.3D, 0D, 1D));

					RBGreatHoneycombRarityBeeDungeon = subscriber.subscribe(builder
						.comment(" \r\n-----------------------------------------------------\r\n\r\n"
								+" How rare good ore-based Honeycombs (diamonds, ender, emerald, etc) are \r\n"
								+" in Bee Dungeons. \r\n"
								+" Higher numbers means more rare. Default rate is 3.\r\n")
						.translation("the_bumblezone.config.productivebees.RBgreathoneycombraritybeedungeon")
						.defineInRange("RBGreatHoneycombRarityBeeDungeon", 2, 1, 1001));

					RBOreHoneycombSpawnRateSpiderBeeDungeon = subscriber.subscribe(builder
						.comment(" \r\n-----------------------------------------------------\r\n\r\n"
								+" How much of Spider Infested Bee Dungeons is made of ore-based honeycombs.\r\n"
								+" 0 is no or honeycombs, 1 is max ore honeycombs, and default is 0.1D\r\n")
						.translation("the_bumblezone.config.productivebees.RBorehoneycombspawnratespiderbeedungeon")
						.defineInRange("RBOreHoneycombSpawnRateSpiderBeeDungeon", 0.1D, 0D, 1D));

					RBGreatHoneycombRaritySpiderBeeDungeon = subscriber.subscribe(builder
						.comment(" \r\n-----------------------------------------------------\r\n\r\n"
								+" How rare good ore-based Honeycombs (diamonds, ender, emerald, etc) are \r\n"
								+" in Spider Infested Bee Dungeons. \r\n"
								+" Higher numbers means more rare. Default rate is 2.\r\n")
						.translation("the_bumblezone.config.productivebees.RBgreathoneycombrarityspiderbeedungeon")
						.defineInRange("RBGreatHoneycombRaritySpiderBeeDungeon", 2, 1, 1001));

				builder.pop();

				builder.push("Productive Bees Options");

					spawnProductiveBeesBeesMob = subscriber.subscribe(builder
		                    .comment(" \r\n-----------------------------------------------------\r\n\r\n"
		                    		+" Spawn Productive Bees in The Bumblezone alongside regular\r\n"
		                    		+" bees at a 1/15th chance when spawning regular bees.\r\n")
		                    .translation("the_bumblezone.config.modcompat.productivebees.spawnproductivebeesbeesmob")
		                    .define("spawnProductiveBeesBeesMob", true));

					spawnProductiveBeesHoneycombVariants = subscriber.subscribe(builder
		                    .comment(" \r\n-----------------------------------------------------\r\n\r\n"
		                    		+" Spawn Productive Bees's various honeycomb variants in The Bumblezone\r\n"
		                    		+" at all kinds of heights and height bands. Start exploring to find \r\n"
		                    		+" where they spawn!"
		                    		+" \r\n"
		                    		+" NOTE: Will require a restart of the world to take effect. \r\n")
		                    .translation("the_bumblezone.config.modcompat.productivebees.spawnproductivebeeshoneycombvariants")
		                    .define("spawnProductiveBeesHoneycombVariants", true));


	        		PBOreHoneycombSpawnRateBeeDungeon = subscriber.subscribe(builder
	    		            .comment(" \r\n-----------------------------------------------------\r\n\r\n"
	    		            		+" How much of Bee Dungeons is made of ore-based honeycombs.\r\n"
	    		            		+" 0 is no or honeycombs, 1 is max ore honeycombs, and default is 0.3D\r\n")
	    		            .translation("the_bumblezone.config.productivebees.pborehoneycombspawnratebeedungeon")
	    		            .defineInRange("PBOreHoneycombSpawnRateBeeDungeon", 0.3D, 0D, 1D));
		    		
	        		PBGreatHoneycombRarityBeeDungeon = subscriber.subscribe(builder
	    		            .comment(" \r\n-----------------------------------------------------\r\n\r\n"
	    		            		+" How rare good ore-based Honeycombs (diamonds, ender, emerald, etc) are \r\n"
	    		            		+" in Bee Dungeons. \r\n"
	    		            		+" Higher numbers means more rare. Default rate is 3.\r\n")
	    		            .translation("the_bumblezone.config.productivebees.pbgreathoneycombraritybeedungeon")
	    		            .defineInRange("PBGreatHoneycombRarityBeeDungeon", 2, 1, 1001));
	        		
	        		PBOreHoneycombSpawnRateSpiderBeeDungeon = subscriber.subscribe(builder
	    		            .comment(" \r\n-----------------------------------------------------\r\n\r\n"
	    		            		+" How much of Spider Infested Bee Dungeons is made of ore-based honeycombs.\r\n"
	    		            		+" 0 is no or honeycombs, 1 is max ore honeycombs, and default is 0.1D\r\n")
	    		            .translation("the_bumblezone.config.productivebees.pborehoneycombspawnratespiderbeedungeon")
	    		            .defineInRange("PBOreHoneycombSpawnRateSpiderBeeDungeon", 0.1D, 0D, 1D));
		    		
	        		PBGreatHoneycombRaritySpiderBeeDungeon = subscriber.subscribe(builder
	    		            .comment(" \r\n-----------------------------------------------------\r\n\r\n"
	    		            		+" How rare good ore-based Honeycombs (diamonds, ender, emerald, etc) are \r\n"
	    		            		+" in Spider Infested Bee Dungeons. \r\n"
	    		            		+" Higher numbers means more rare. Default rate is 2.\r\n")
	    		            .translation("the_bumblezone.config.productivebees.pbgreathoneycombrarityspiderbeedungeon")
	    		            .defineInRange("PBGreatHoneycombRaritySpiderBeeDungeon", 2, 1, 1001));

	            builder.pop();
	            
	            
	            builder.push("Potion of Bees Options");

	            		allowPotionOfBeesCompat = subscriber.subscribe(builder
		                    .comment(" \r\n-----------------------------------------------------\r\n\r\n"
		                    		+" Allow Potion of Bees item to turn Empty Honeycomb Brood blocks \r\n"
			                    	+" back into Honeycomb Brood Blocks with a larva in it. (affects Dispenser too)\r\n")
		                    .translation("the_bumblezone.config.modcompat.potionofbees.allowpotionofbeescompat")
		                    .define("allowPotionOfBeesCompat", true));
	            		
	            		allowSplashPotionOfBeesCompat = subscriber.subscribe(builder
			            .comment(" \r\n-----------------------------------------------------\r\n\r\n"
			                    	+" Allow Splash Potion of Bees item to turn Empty Honeycomb Brood \r\n"
				                +" blocks back into Honeycomb Brood Blocks with a larva in it when \r\n"
				                +" the potion is thrown and splashed near the block. (affects Dispenser too)\r\n")
			            .translation("the_bumblezone.config.modcompat.productivebees.allowsplashpotionofbeescompat")
			            .define("allowSplashPotionOfBeesCompat", true));
        
	            builder.pop();
	            
	        builder.pop();
	    }
	}
}
