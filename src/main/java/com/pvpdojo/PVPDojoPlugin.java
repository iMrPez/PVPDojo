package com.pvpdojo;



/*
* Add Special Attack Recharging with check for lightbearer
* */


import com.google.inject.Provides;
import javax.inject.Inject;

import com.pvpdojo.character.*;
import com.pvpdojo.character.datatypes.Coordinate;
import com.pvpdojo.character.datatypes.WeaponData;
import com.pvpdojo.combat.CombatUtility;
import com.pvpdojo.combat.equipment.EquipmentStats;
import com.pvpdojo.combat.equipment.EquipmentUtility;
import com.pvpdojo.combat.Prayers;
import com.pvpdojo.combatant.CorePlayer;
import com.pvpdojo.combatant.Dummy;
import com.pvpdojo.overlays.*;
import com.pvpdojo.pathfinding.MovementType;
import com.pvpdojo.pathfinding.PathFinder;
import com.pvpdojo.program.Program;
import com.pvpdojo.program.ProgramComp;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@PluginDescriptor(
	name = "PVP Dojo",
	description = "PVP Practice Tool"
)
public class PVPDojoPlugin extends Plugin
{
	private static final Logger log = LoggerFactory.getLogger(PVPDojoPlugin.class);
	@Inject
	private Client client;


	@Inject
	public ClientThread clientThread;

	@Inject
	public PVPDojoConfig config;

	@Inject
	private DataFinder dataFinder;

	@Inject
	private ModelGetter modelGetter;

	@Inject
	public OverheadOverlay overheadOverlay;

	@Inject
	public TextOverlay textOverlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	public HealthOverlay healthOverlay;

	@Inject
	public HitsplatOverlay hitsplatOverlay;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	public CombatUtility combatUtility;

	@Inject
	public CorePlayer corePlayer;

	@Inject
	private ItemManager itemManager;

	@Inject
	private HPOrbOverlay hpOrbOverlay;

	@Inject
	public SpecialBarOverlay specialBarOverlay;

	@Inject
	public FightOverlay fightOverlay;

	@Inject
	public PrayerOverlay prayerOverlay;

	@Inject
	public PathFinder pathFinder;

	private NavigationButton navButton;
	private boolean hasDummySpawned = false;

	@Override
	protected void startUp() throws Exception
	{
		log.info("PVP Dojo Initialized!");

		overlayManager.add(overheadOverlay);
		overlayManager.add(textOverlay);
		overlayManager.add(healthOverlay);
		overlayManager.add(hitsplatOverlay);
		overlayManager.add(hpOrbOverlay);
		overlayManager.add(specialBarOverlay);
		overlayManager.add(fightOverlay);
		overlayManager.add(prayerOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("PVP Dojo Stopped!");
		overlayManager.remove(overheadOverlay);
		overlayManager.remove(textOverlay);
		overlayManager.remove(healthOverlay);
		overlayManager.remove(hitsplatOverlay);
		overlayManager.remove(hpOrbOverlay);
		overlayManager.remove(specialBarOverlay);
		overlayManager.remove(fightOverlay);
		overlayManager.remove(prayerOverlay);

		clientToolbar.removeNavigation(navButton);

		if (dummy != null && dummy.dummyCharacter != null)
		{
			dummy.despawnCharacter();
		}

		isActive = false;
		dummy = null;

	}

	private boolean isActive = false;

	public Dummy dummy;

	public CustomModel magicModel;
	public CustomModel rangeModel;
	public CustomModel meleeModel;

	public EquipmentStats magicGearStats;
	public EquipmentStats rangeGearStats;
	public EquipmentStats meleeGearStats;

	public WeaponData magicWeaponData;
	public WeaponData rangeWeaponData;
	public WeaponData meleeWeaponData;

	private boolean isSettingMagicGear = false;
	private boolean isSettingRangeGear = false;
	private boolean isSettingMeleeGear = false;

	private int ticks = 0;

	private boolean fightStarted = false;
	private int fightStartTime = 5;
	private boolean fightStarting = false;

	public int totalDamageOnDummy = 0;

	public int totalHitsOnDummy = 0;
	public int totalOffPrayerHitsOnDummy = 0;


	public int totalDamageOnPlayer = 0;

	public int totalHitsOnPlayer = 0;
	public int totalOffPrayerHitsOnPlayer = 0;

	public List<Prayers> requestedPrayers = new ArrayList<>();

	public boolean hasStartedFightCountdown()
	{
		return fightStarted && fightStartTime > 0;
	}

	public boolean hasFightStarted()
	{
		return fightStarted && fightStartTime <= 0;
	}

	public int getTicks() {
		return ticks;
	}




	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{

	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			if (fightStarted)
			{
				corePlayer.fightFinished();
				dummy.fightFinished();
			}

			if (hasDummySpawned)
			{
				despawnDummy();
			}
			return;
		}

		corePlayer.OnGameTick();

		if (dummy != null)
		{
			dummy.OnGameTick();
		}

		ticks++;

		if (ticks > 1000 && !textOverlay.hasText()) {
			ticks = 0;
		}

		if (!requestedPrayers.isEmpty())
		{
			for (Prayers prayer : requestedPrayers)
			{
				togglePrayer(prayer);
			}

			requestedPrayers.clear();
		}

		if (isActive)
		{
			if (hasStartedFightCountdown())
			{
				fightStartTime--;
				dummy.displayText("Starting in " + fightStartTime, 1);

				if (fightStartTime <= 0)
				{
					fightStarting = true;
				}
			}

			if (fightStarting)
			{
				fightStarting = false;

				dummy.displayText("FIGHT!", 1);
			}
		}


	}


	@Subscribe
	public void onClientTick(ClientTick event)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
			return;


		corePlayer.OnClientTick();

		if (dummy != null)
		{
			dummy.OnClientTick();
		}
	}


	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (event.getContainerId() != InventoryID.EQUIPMENT.getId())
			return; // Only care about equipment changes

		corePlayer.equipmentChanged();

		/*ItemContainer container = event.getItemContainer();
		Item[] items = container.getItems();*/

		// You can now compare `items` to a cached previous state
	}


	private void despawnDummy()
	{
		dummy.despawnCharacter();
		isActive = false;
		dummy = null;
		hasDummySpawned = false;
	}

	private void spawnDummy()
	{
		dummy = new Dummy(client, this, config, pathFinder);
		dummy.setLocation(dummy.dummyCharacter, false, false, true, false);
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Dummy", "Dummy Spawned!", "Dummy");
		hasDummySpawned = true;

		isActive = true;
	}

	private void setMeleeGear()
	{
		isSettingMeleeGear = true;
		modelGetter.storePlayer(client.getLocalPlayer(), true);
		meleeGearStats = combatUtility.getEquipmentStats();
		meleeWeaponData = combatUtility.getWeaponData();
	}

	private void setRangeGear() {

		isSettingRangeGear = true;
		modelGetter.storePlayer(client.getLocalPlayer(), true);
		rangeGearStats = combatUtility.getEquipmentStats();
		rangeWeaponData = combatUtility.getWeaponData();
	}

	private void setMagicGear()
	{
		isSettingMagicGear = true;
		modelGetter.storePlayer(client.getLocalPlayer(), true);
		magicGearStats = combatUtility.getEquipmentStats();
		magicWeaponData = combatUtility.getWeaponData();
	}

	public void startFight()
	{
		fightStarted = true;
		fightStartTime = 6;

		dummy.fightStarted();
		corePlayer.fightStarted();
	}

	public void stopFight(boolean win)
	{
		fightStarted = false;
		fightStarting = false;

		if (win)
		{
			dummy.displayText("You Won!", 3);
		}
		else
		{
			dummy.displayText("You Lose!", 3);
		}


		dummy.setHealth(config.dummyHitPoints());
		corePlayer.setHealth(config.playerHitPoints());

		dummy.fightFinished();
		corePlayer.fightFinished();
		dummy.returnHome();

		dummy.setPrayers(new ArrayList<>());

		totalDamageOnPlayer = 0;
		totalDamageOnDummy = 0;

		totalOffPrayerHitsOnPlayer = 0;
		totalOffPrayerHitsOnDummy = 0;

		totalHitsOnPlayer = 0;
		totalHitsOnDummy = 0;
	}


	private boolean canActivate() {
		return magicModel != null && rangeModel != null && meleeModel != null;
	}


	public double getCurrentTick()
	{
		return ticks;
	}

	public Model constructModelFromCache(ModelStats[] modelStatsArray, int[] kitRecolours, boolean player, LightingStyle ls, CustomLighting cl)
	{
		ModelData md = constructModelDataFromCache(modelStatsArray, kitRecolours, player);
		if (ls == LightingStyle.CUSTOM)
		{
			return client.mergeModels(md).light(cl.getAmbient(), cl.getContrast(), cl.getX(), -cl.getZ(), cl.getY());
		}

		return client.mergeModels(md).light(ls.getAmbient(), ls.getContrast(), ls.getX(), -ls.getZ(), ls.getY());
	}

	public ModelData constructModelDataFromCache(ModelStats[] modelStatsArray, int[] kitRecolours, boolean player)
	{
		ModelData[] mds = new ModelData[modelStatsArray.length];

		for (int i = 0; i < modelStatsArray.length; i++)
		{
			ModelStats modelStats = modelStatsArray[i];
			ModelData modelData = client.loadModelData(modelStats.getModelId());

			if (modelData == null)
				continue;

			modelData.cloneColors().cloneVertices();

			for (short s = 0; s < modelStats.getRecolourFrom().length; s++)
				modelData.recolor(modelStats.getRecolourFrom()[s], modelStats.getRecolourTo()[s]);

			if (player)
				KitRecolourer.recolourKitModel(modelData, modelStats.getBodyPart(), kitRecolours);

			short[] textureFrom = modelStats.getTextureFrom();
			short[] textureTo = modelStats.getTextureTo();

			if (textureFrom == null || textureTo == null)
			{
				modelStats.setTextureFrom(new short[0]);
				modelStats.setTextureTo(new short[0]);
			}

			textureFrom = modelStats.getTextureFrom();
			textureTo = modelStats.getTextureTo();

			if (textureFrom.length > 0 && textureTo.length > 0)
			{
				for (int e = 0; e < textureFrom.length; e++)
				{
					modelData.retexture(textureFrom[e], textureTo[e]);
				}
			}

			if (modelStats.getResizeX() == 0 && modelStats.getResizeY() == 0 && modelStats.getResizeZ() == 0)
			{
				modelStats.setResizeX(128);
				modelStats.setResizeY(128);
				modelStats.setResizeZ(128);
			}

			modelData.scale(modelStats.getResizeX(), modelStats.getResizeZ(), modelStats.getResizeY());

			modelData.translate(0, -1 * modelStats.getTranslateZ(), 0);

			mds[i] = modelData;
		}

		return client.mergeModels(mds);
	}



	public void addCustomModel(CustomModel customModel)
	{
		if (isSettingMagicGear)
		{
			magicModel = customModel;

			isSettingMagicGear = false;
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Dummy", "Magic Model Set", "Dummy");
		}

		if (isSettingRangeGear)
		{
			rangeModel = customModel;

			isSettingRangeGear = false;
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Dummy", "Range Model Set", "Dummy");
		}

		if (isSettingMeleeGear)
		{
			meleeModel = customModel;

			isSettingMeleeGear = false;
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Dummy", "Melee Model Set", "Dummy");
		}
	}

	@Subscribe
	public void onPostMenuSort(PostMenuSort event)
	{
		WorldView worldView = client.getTopLevelWorldView();

		handlePrayerMenuEntries();

		handleInventoryMenuEntries();

		handleCombatMenuEntries(worldView);
	}

	private void togglePrayer(Prayers prayer)
	{
		if (corePlayer.prayers.contains(prayer.prayer))
		{
			EquipmentUtility.playSound(client, 2663);
			corePlayer.removePrayer(prayer.prayer);
			corePlayer.updateOverheadPrayers();
		}
		else
		{
			EquipmentUtility.playSound(client, prayer.soundID);
			corePlayer.addPrayer(prayer);
			corePlayer.updateOverheadPrayers();
		}
	}

	public void addRequestedPrayer(Prayers prayer)
	{
		log.info("Adding Requested Prayer");
		requestedPrayers.add(prayer);
	}

	private void handlePrayerMenuEntries()
	{
		/*if (!hasDummySpawned) return;*/

		MenuEntry[] menuEntries = client.getMenu().getMenuEntries();
		for (MenuEntry menuEntry : menuEntries)
		{
			var prayer = combatUtility.getPrayerFromMenu(menuEntry);

			if (prayer != null)
			{
				MenuEntry prayerEntry = client.getMenu().createMenuEntry(-1)
						.setOption(ColorUtil.prependColorTag("Activate", Color.WHITE))
						.setTarget(ColorUtil.colorTag(Color.BLUE) + prayer.prayerName)
						.setType(MenuAction.RUNELITE)
						.onClick(e -> { addRequestedPrayer(prayer); });
			}
		}
	}

	private void handleInventoryMenuEntries()
	{
		if (!hasDummySpawned) return;

		MenuEntry[] menuEntries = client.getMenu().getMenuEntries();


		for (MenuEntry menuEntry : menuEntries)
		{
			var inventoryItem = combatUtility.getItemFromMenu(menuEntry);
			if (inventoryItem != null)
			{
				var canUse = corePlayer.canUseInventoryItem(inventoryItem);
				var itemUses = corePlayer.getInventoryItemUses(inventoryItem);
				if (canUse == 2)
				{
					MenuEntry itemEntry = client.getMenu().createMenuEntry(-1)
							.setOption(ColorUtil.prependColorTag(inventoryItem.getActionName(), Color.GREEN))
							.setTarget(ColorUtil.colorTag(Color.GREEN) + inventoryItem.itemName + "(" + itemUses + ")")
							.setType(MenuAction.RUNELITE)
							.onClick(e -> {corePlayer.requestItemUse(inventoryItem);});
				}
				else if(canUse == 1)
				{
					MenuEntry outEntry = client.getMenu().createMenuEntry(-1)
							.setOption(ColorUtil.prependColorTag("Out", Color.blue))
							.setType(MenuAction.RUNELITE)
							.onClick(e -> {});
				}
				else if(canUse == 0)
				{
					MenuEntry cooldownEntry = client.getMenu().createMenuEntry(-1)
							.setOption(ColorUtil.prependColorTag("Cooldown (" + corePlayer.getInventoryItemCooldown(inventoryItem) + ")", Color.blue))
							.setType(MenuAction.RUNELITE)
							.onClick(e -> {});
				}

			}

			//log.info(menuEntry.getOption());
			if (menuEntry.getOption().contains("Special Attack"))
			{
				MenuEntry itemEntry = client.getMenu().createMenuEntry(-1)
						.setOption(ColorUtil.prependColorTag("Use", Color.WHITE))
						.setTarget(ColorUtil.colorTag(Color.cyan) + "Special Attack")
						.setType(MenuAction.RUNELITE)
						.onClick(e -> { corePlayer.requestedSpecialBar(); });
			}
		}
	}


	private void handleCombatMenuEntries(WorldView worldView)
	{
		if (dummy != null && dummy.dummyCharacter.getCharacterObject() != null)
		{
			if (dummy.isHovered())
			{
				addHoveredMenuEntries(dummy);
				return;
			}
		}

		Tile tile = worldView.getSelectedSceneTile();
		if (tile == null)
		{
			return;
		}

		MenuEntry[] menuEntries = client.getMenu().getMenuEntries();
		boolean hoveringTile = false;
		for (MenuEntry menuEntry : menuEntries)
		{
			if (menuEntry.getOption().equals("Walk here"))
			{
				hoveringTile = true;
				break;
			}
		}

		if (!hoveringTile)
		{
			return;
		}

		if (!hasFightStarted())
		{
			addSetupMenuEntries();
		}

		if (!addCharacterMenuEntries(tile))
		{
			if (corePlayer.isFrozen)
			{
				addCharacterFrozenMenuEntries();
			}
		}
	}

	public void addCharacterFrozenMenuEntries()
	{
		if (!hasDummySpawned) return;

		MenuEntry menuEntry = client.getMenu().createMenuEntry(-1)
				.setOption(ColorUtil.prependColorTag("FROZEN(" + corePlayer.frozenTicks + ")", Color.blue))
				.setType(MenuAction.RUNELITE)
				.onClick(e -> {});
	}

	public void addSetupMenuEntries()
	{

		if (canActivate() && !hasDummySpawned)
		{
			MenuEntry spawnEntry = client.getMenu().createMenuEntry(0)
					.setOption(ColorUtil.prependColorTag("Spawn Dummy", Color.blue))
					.setType(MenuAction.RUNELITE)
					.onClick(e -> {spawnDummy();});
		}

		if (hasDummySpawned) return;

		MenuEntry setMagicGearEntry = client.getMenu().createMenuEntry(1)
				.setOption(ColorUtil.prependColorTag("Set", Color.white))
				.setTarget(ColorUtil.colorTag(Color.blue) + "Magic Gear " + (magicModel != null ? "(X)" : ""))
				.setType(MenuAction.RUNELITE)
				.onClick(e -> {setMagicGear();});

		MenuEntry setRangeGearEntry = client.getMenu().createMenuEntry(2)
				.setOption(ColorUtil.prependColorTag("Set", Color.white))
				.setTarget(ColorUtil.colorTag(Color.green) + "Range Gear" + (rangeModel != null ? "(X)" : ""))
				.setType(MenuAction.RUNELITE)
				.onClick(e -> {setRangeGear();});

		MenuEntry setMeleeGearEntry = client.getMenu().createMenuEntry(3)
				.setOption(ColorUtil.prependColorTag("Set", Color.white))
				.setTarget(ColorUtil.colorTag(Color.red) + "Melee Gear" + (meleeModel != null ? "(X)" : ""))
				.setType(MenuAction.RUNELITE)
				.onClick(e -> {setMeleeGear();});


	}

	public boolean addCharacterMenuEntries(Tile tile)
	{
		if (dummy == null) return false;

		CharacterObject dummyObject = dummy.dummyCharacter.getCharacterObject();
		if (dummy.dummyCharacter.isActive() && dummyObject != null)
		{
			LocalPoint localPoint = dummyObject.getLocation();
			if (localPoint != null && localPoint.equals(tile.getLocalLocation()))
			{
				addHoveredMenuEntries(dummy);
				return true;
			}
		}

		return false;
	}


	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.getMenuOption().equals("Walk here"))
		{
			//log.debug("Player clicked Walk here at: {}", event.getMenuTarget());
			corePlayer.playerMoved();

			// Optional: get the clicked world location
			WorldPoint destination = WorldPoint.fromScene(
					client,
					event.getParam0(), // scene x
					event.getParam1(), // scene y
					client.getPlane()
			);

			log.info("Destination WorldPoint: {}", destination);
		}
	}

	public void addHoveredMenuEntries(Dummy dummy)
	{

		CharacterObject dummyObject = dummy.dummyCharacter.getCharacterObject();
		LocalPoint localPoint = dummyObject.getLocation();
		var dummyWP = WorldPoint.fromLocal(client, localPoint);

		if (!hasFightStarted())
		{
			if (hasStartedFightCountdown())
			{
				MenuEntry startingEntry = client.getMenu().createMenuEntry(-1)
						.setOption(ColorUtil.prependColorTag("Starting", Color.RED))
						.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
						.setType(MenuAction.RUNELITE)
						.onClick(e -> {});
			}
			else
			{
				MenuEntry startFightEntry = client.getMenu().createMenuEntry(-1)
						.setOption(ColorUtil.prependColorTag("Start Fight", Color.ORANGE))
						.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
						.setType(MenuAction.RUNELITE)
						.onClick(e -> startFight());

				MenuEntry despawnDummyEntry = client.getMenu().createMenuEntry(0)
						.setOption(ColorUtil.prependColorTag("Remove Dummy", Color.red))
						.setType(MenuAction.RUNELITE)
						.onClick(e -> despawnDummy());
			}

			return;
		}



		if (corePlayer.isInRange())
		{

			MenuEntry attackEntry = client.getMenu().createMenuEntry(-1)
					.setOption(ColorUtil.prependColorTag("Attack", Color.ORANGE))
					.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
					.setType(MenuAction.RUNELITE)
					.onClick(e -> corePlayer.clickedAttack());
		}
		else
		{
			MenuEntry outOfRangeEntry = client.getMenu().createMenuEntry(-1)
					.setOption(ColorUtil.prependColorTag("Out of Range", Color.RED))
					.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
					.setType(MenuAction.RUNELITE)
					.onClick(e -> {});
		}

		MenuEntry stopFightEntry = client.getMenu().createMenuEntry(0)
				.setOption(ColorUtil.prependColorTag("Stop Fight", Color.RED))
				.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
				.setType(MenuAction.RUNELITE)
				.onClick(e -> stopFight(false));
	}

	public Program createEmptyProgram(int poseAnim, int walkAnim)
	{
		Color color = new Color(255, 255, 255);
		ProgramComp comp = new ProgramComp(new WorldPoint[0], new WorldPoint[0], new LocalPoint[0], new LocalPoint[0], new Coordinate[0], false, 0, 1, 25, poseAnim, walkAnim, MovementType.NORMAL, color.getRGB(), false, false);
		return new Program(comp, color);
	}


	@Provides
	PVPDojoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PVPDojoConfig.class);
	}
}
