package com.pvpdojo;



/*
* Add Special Attack Recharging with check for lightbearer
* */


import com.google.inject.Provides;
import javax.inject.Inject;
import javax.swing.*;

import com.pvpdojo.character.*;
import com.pvpdojo.character.datatypes.*;
import com.pvpdojo.combat.*;
import com.pvpdojo.combat.equipment.EquipmentStats;
import com.pvpdojo.combat.equipment.EquipmentUtility;
import com.pvpdojo.combatant.CorePlayer;
import com.pvpdojo.combatant.Dummy;
import com.pvpdojo.fightPanel.EquipmentItemPanel;
import com.pvpdojo.overlays.*;
import com.pvpdojo.pathfinding.PathFinder;
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
import net.runelite.client.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
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
	private ModelGetter modelGetter;

	@Inject
	private ConfigManager configManager;

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
	public ItemManager itemManager;

	@Inject
	private HPOrbOverlay hpOrbOverlay;

	@Inject
	private SpecOrbOverlay specOrbOverlay;

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

	public FightSetupPanel fightPanel;

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
		overlayManager.add(specOrbOverlay);

		fightPanel = injector.getInstance(FightSetupPanel.class);
		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "/skull_blue.png");
		navButton = NavigationButton.builder()
				.tooltip("PvP Fight History")
				.icon(icon)
				.priority(6)
				.panel(fightPanel)
				.build();


		clientToolbar.addNavigation(navButton);



		// Explicitly rebuild panel after all setup and import.
		SwingUtilities.invokeLater(() ->
		{
			if (fightPanel != null)
			{
				var loadData = PVPDojoData.loadData(configManager);
				if (loadData != null)
				{
					meleeEquipmentData = loadData.meleeEquipmentData;
					rangeEquipmentData = loadData.rangeEquipmentData;
					magicEquipmentData = loadData.magicEquipmentData;
					specEquipmentData = loadData.specEquipmentData;

					equipmentGroupPressed(EquipmentItemGroup.MELEE);
				}

				fightPanel.rebuild();
			}
		});
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
		overlayManager.remove(specOrbOverlay);

		clientToolbar.removeNavigation(navButton);

		resetPlugin();

		save();
	}

	public void save()
	{
		var saveData = new PVPDojoData(meleeEquipmentData, rangeEquipmentData, magicEquipmentData, specEquipmentData);

		PVPDojoData.saveData(configManager, saveData);
	}

	private boolean isActive = false;

	public Dummy dummy;

	public CustomModel currentModel;
	public EquipmentStats currentEquipmentStats;
	public WeaponData currentWeaponData;

	public EquipmentItemGroup selectedEquipmentGroup = EquipmentItemGroup.MELEE;

	public EquipmentData meleeEquipmentData;

	public EquipmentData rangeEquipmentData;

	public EquipmentData magicEquipmentData;

	public EquipmentData specEquipmentData;

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

	private EquipmentItemData newlyAddedEquipmentItem;


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


	boolean hasResetPlugin = false;

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		log.info("Game State Changed: " + gameStateChanged.getGameState());
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			log.info("Game State is now LOGGED_IN");
			resetPlugin();
		}

	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
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

	private void resetPlugin()
	{
		if (dummy != null && dummy.dummyCharacter != null)
		{
			dummy.despawnCharacter();
		}

		isActive = false;
		dummy = null;
		hasDummySpawned = false;
		fightStarted = false;
		fightStarting = false;

		healthOverlay.clearHealth();
		overheadOverlay.clearOverheads();

		corePlayer.reset();
	}


	@Subscribe
	public void onClientTick(ClientTick event)
	{
		/*var clientHeight = fightPanel.getPreferredHeight();
		log.info("" + clientHeight);
		if (lastHeight != clientHeight)
		{
			lastHeight = clientHeight;
			fightPanel.setScrollViewHeight(clientHeight - 30);
			fightPanel.rebuild();
			log.info("Updated UI Height: " + clientHeight);
		}
*/

		/*if (newlyAddedEquipmentItem != null)
		{
			switch (selectedEquipmentGroup)
			{
                case MELEE:
					if (meleeEquipmentData != null)
					{
						meleeEquipmentData.equipmentList.add(newlyAddedEquipmentItem);
					}
					else
					{
						meleeEquipmentData = new EquipmentData(List.of(newlyAddedEquipmentItem));
					}
                    break;
                case RANGE:
					if (rangeEquipmentData != null)
					{
						rangeEquipmentData.equipmentList.add(newlyAddedEquipmentItem);
					}
					else
					{
						rangeEquipmentData = new EquipmentData(List.of(newlyAddedEquipmentItem));
					}
                    break;
                case MAGIC:
					if (magicEquipmentData != null)
					{
						magicEquipmentData.equipmentList.add(newlyAddedEquipmentItem);
					}
					else
					{
						magicEquipmentData = new EquipmentData(List.of(newlyAddedEquipmentItem));
					}
                    break;
                case SPEC:
					if (specEquipmentData != null)
					{
						specEquipmentData.equipmentList.add(newlyAddedEquipmentItem);
					}
					else
					{
						specEquipmentData = new EquipmentData(List.of(newlyAddedEquipmentItem));
					}
                    break;
            }

			newlyAddedEquipmentItem = null;
		}*/

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
		meleeEquipmentData = new EquipmentData(getEquippedItemData());
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Dummy", "Melee Gear Set", "Dummy");
	}

	private void setRangeGear() {

		rangeEquipmentData = new EquipmentData(getEquippedItemData());
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Dummy", "Range Gear Set", "Dummy");
	}

	private void setMagicGear()
	{
		/*var player = client.getLocalPlayer();
		PlayerComposition comp = player.getPlayerComposition();
		var modelStats = modelGetter.getModelStats(comp);*/

		/*var equippedItems = new EquipmentData(getEquippedItemData());
		var defaultItems = EquipmentData.getDefault();
		defaultItems.replace(equippedItems.getWeapon());
		var modelStats = defaultItems.getModelStats();
		log.info("EquipmentList: " + equippedItems.equipmentList.size());
		log.info("ModelStats: " + modelStats.length);
		isSettingMagicGear = true;
		modelGetter.storeModelStats(modelStats);
		magicGearStats = equippedItems.getEquipmentStats(itemManager);
		magicWeaponData = combatUtility.getWeaponData();*/

		/*isSettingMagicGear = true;
		modelGetter.storePlayer(client.getLocalPlayer(), true);
		magicGearStats = combatUtility.getEquipmentStats();
		magicWeaponData = combatUtility.getWeaponData();*/

		magicEquipmentData = new EquipmentData(getEquippedItemData());
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "Dummy", "Magic Gear Set", "Dummy");
	}


	public List<EquipmentItemData> getEquippedItemData()
	{
		List<EquipmentItemData> equipmentItemData = new ArrayList<>(List.of());

		for (BodyPart bodyPart : BodyPart.values())
		{
			switch (bodyPart)
			{
                case NA:
                    break;
                case HEAD:
					var headStat = modelGetter.getModelStat(BodyPart.HEAD);
					var headItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.HEAD);
					EquipmentItemData headItemData = new EquipmentItemData(headItem != null ? headItem.getId() : -1, EquipmentInventorySlot.HEAD, null, headStat);
					equipmentItemData.add(headItemData);
                    break;
                case CAPE:
					var capeStat = modelGetter.getModelStat(BodyPart.CAPE);
					var capeItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.CAPE);
					EquipmentItemData capeItemData = new EquipmentItemData(capeItem != null ? capeItem.getId() : -1, EquipmentInventorySlot.CAPE, null, capeStat);
					equipmentItemData.add(capeItemData);
                    break;
                case AMULET:
					var amuletStat = modelGetter.getModelStat(BodyPart.AMULET);
					var amuletItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.AMULET);
					EquipmentItemData amuletItemData = new EquipmentItemData(amuletItem != null ? amuletItem.getId() : -1, EquipmentInventorySlot.AMULET, null, amuletStat);
					equipmentItemData.add(amuletItemData);
                    break;
                case WEAPON:
					var weaponStat = modelGetter.getModelStat(BodyPart.WEAPON);
					var weaponItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.WEAPON);
					var weaponData = combatUtility.getWeaponData();
					EquipmentItemData weaponItemData = new EquipmentItemData(weaponItem != null ? weaponItem.getId() : -1, EquipmentInventorySlot.WEAPON, weaponData, weaponStat);
					equipmentItemData.add(weaponItemData);
                    break;
                case TORSO:
					var torsoStat = modelGetter.getModelStat(BodyPart.TORSO);
					var torsoItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.BODY);
					EquipmentItemData torsoItemData = new EquipmentItemData(torsoItem != null ? torsoItem.getId() : -1, EquipmentInventorySlot.BODY, null, torsoStat);
					equipmentItemData.add(torsoItemData);
                    break;
                case SHIELD:
					var shieldStat = modelGetter.getModelStat(BodyPart.SHIELD);
					var shieldItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.SHIELD);
					EquipmentItemData shieldItemData = new EquipmentItemData(shieldItem != null ? shieldItem.getId() : -1, EquipmentInventorySlot.SHIELD, null, shieldStat);
					equipmentItemData.add(shieldItemData);
                    break;
                case ARMS:
					var armsStat = modelGetter.getModelStat(BodyPart.ARMS);
					var armsItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.ARMS);
					EquipmentItemData armsItemData = new EquipmentItemData(armsItem != null ? armsItem.getId() : -1, EquipmentInventorySlot.ARMS, null, armsStat);
					equipmentItemData.add(armsItemData);
                    break;
                case LEGS:
					var legsStat = modelGetter.getModelStat(BodyPart.LEGS);
					var legsItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.LEGS);
					EquipmentItemData legsItemData = new EquipmentItemData(legsItem != null ? legsItem.getId() : -1, EquipmentInventorySlot.LEGS, null, legsStat);
					equipmentItemData.add(legsItemData);
                    break;
                case HAIR:
					var hairStat = modelGetter.getModelStat(BodyPart.HAIR);
					var hairItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.HAIR);
					EquipmentItemData hairItemData = new EquipmentItemData(hairItem != null ? hairItem.getId() : -1, EquipmentInventorySlot.HAIR, null, hairStat);
					equipmentItemData.add(hairItemData);
                    break;
                case HANDS:
					var handsStat = modelGetter.getModelStat(BodyPart.HANDS);
					var handsItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.GLOVES);
					EquipmentItemData handsItemData = new EquipmentItemData(handsItem != null ? handsItem.getId() : -1, EquipmentInventorySlot.GLOVES, null, handsStat);
					equipmentItemData.add(handsItemData);
                    break;
                case FEET:
					var feetStat = modelGetter.getModelStat(BodyPart.FEET);
					var feetItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.BOOTS);
					EquipmentItemData feetItemData = new EquipmentItemData(feetItem != null ? feetItem.getId() : -1, EquipmentInventorySlot.BOOTS, null, feetStat);
					equipmentItemData.add(feetItemData);
                    break;
                case JAW:
					var jawStat = modelGetter.getModelStat(BodyPart.JAW);
					var jawItem = EquipmentUtility.getSlot(client, EquipmentInventorySlot.JAW);
					EquipmentItemData jawItemData = new EquipmentItemData(jawItem != null ? jawItem.getId() : -1, EquipmentInventorySlot.JAW, null, jawStat);
					equipmentItemData.add(jawItemData);
                    break;
                case SPOTANIM:
                    break;
            }
		}

		return equipmentItemData;
	}

	public void startFight()
	{
		fightStarted = true;
		fightStartTime = 6;

		dummy.fightStarted();
		corePlayer.fightStarted();

		totalDamageOnPlayer = 0;
		totalDamageOnDummy = 0;

		totalOffPrayerHitsOnPlayer = 0;
		totalOffPrayerHitsOnDummy = 0;

		totalHitsOnPlayer = 0;
		totalHitsOnDummy = 0;
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


		dummy.setHealth(dummy.getMaxHP());
		corePlayer.setHealth(corePlayer.getMaxHP());

		dummy.fightFinished();
		corePlayer.fightFinished();
		dummy.returnHome();

		hitsplatOverlay.clearHitsplats();

		dummy.setPrayers(new ArrayList<>());
	}


	private boolean canActivate()
	{
		return magicEquipmentData != null && rangeEquipmentData != null && meleeEquipmentData != null;
	}


	public double getCurrentTick()
	{
		return ticks;
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
		if (!hasDummySpawned) return;

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

				/*MenuEntry testMoveEntry = client.getMenu().createMenuEntry(-1)
						.setOption(ColorUtil.prependColorTag("Test Move", Color.ORANGE))
						.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
						.setType(MenuAction.RUNELITE)
						.onClick(e -> testMove());

				MenuEntry testMagic = client.getMenu().createMenuEntry(-1)
						.setOption(ColorUtil.prependColorTag("Test Magic", Color.BLUE))
						.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
						.setType(MenuAction.RUNELITE)
						.onClick(e -> testMagic());

				MenuEntry testRange = client.getMenu().createMenuEntry(-1)
						.setOption(ColorUtil.prependColorTag("Test Range", Color.GREEN))
						.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
						.setType(MenuAction.RUNELITE)
						.onClick(e -> testRange());

				MenuEntry testMelee = client.getMenu().createMenuEntry(-1)
						.setOption(ColorUtil.prependColorTag("Test Melee", Color.RED))
						.setTarget(ColorUtil.colorTag(Color.GREEN) + dummy.dummyCharacter.getName())
						.setType(MenuAction.RUNELITE)
						.onClick(e -> testMelee());*/

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



		if (corePlayer.isInRange(true))
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


	private void testMove()
	{
		dummy.moveToPlayer(true);
	}

	private void testRange()
	{
		dummy.setCombatStyle(CombatStyle.RANGE);
		var attackData = new AttackData(null, dummy.getWeaponData(), false, 1);
		dummy.tryAttack(attackData);
	}

	private void testMelee()
	{
		dummy.setCombatStyle(CombatStyle.MELEE);
		var attackData = new AttackData(null, dummy.getWeaponData(), false, 1);
		dummy.tryAttack(attackData);
	}

	private void testMagic()
	{
		dummy.setCombatStyle(CombatStyle.MAGIC);
		var attackData = new AttackData(Spell.ICE_BARRAGE, dummy.getWeaponData(), false, 1);
		dummy.tryAttack(attackData);
	}


	@Provides
	PVPDojoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PVPDojoConfig.class);
	}

	public EquipmentData getEquipmentData(EquipmentItemGroup group)
	{
		switch (group)
		{
            case MELEE:
				return meleeEquipmentData;
            case RANGE:
                return rangeEquipmentData;
            case MAGIC:
                return magicEquipmentData;
            case SPEC:
                return specEquipmentData;
			default:
				return meleeEquipmentData;
        }
	}

	public void setSelectedEquipmentGroup(EquipmentItemGroup group)
	{
		selectedEquipmentGroup = group;

		loadFromEquipmentData(getEquipmentData(group));
	}

	public void loadFromEquipmentData(EquipmentData equipmentData)
	{
		fightPanel.clearEquippedItems();

		if (equipmentData != null)
		{
			for (EquipmentItemData itemData : equipmentData.equipmentList)
			{
				ItemComposition composition = itemManager.getItemComposition(itemData.itemID);

				BufferedImage itemIcon = itemManager.getImage(itemData.itemID);
				if (itemData.itemID >= 0)
				{
					fightPanel.addEquipmentItem(new EquipmentItemPanel(this, itemIcon, composition.getName(), itemData.slot.name()));
				}
			}
		}

		fightPanel.rebuild();
	}


	public void addEquippedWeaponToPanel()
	{
		ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
		if (equipment == null) return;

		Item weapon = equipment.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx());
		if (weapon == null || weapon.getId() == -1) return;

		int weaponId = weapon.getId();
		ItemComposition composition = itemManager.getItemComposition(weapon.getId());


		var weaponData = combatUtility.getWeaponData();

		BufferedImage weaponIcon = itemManager.getImage(weaponId);
		fightPanel.addEquipmentItem(new EquipmentItemPanel(this, weaponIcon, composition.getName(), EquipmentInventorySlot.WEAPON.name()));

		if (specEquipmentData == null) {
			specEquipmentData = new EquipmentData(new ArrayList<>());
		}
        List<EquipmentItemData> equipmentItemData = new ArrayList<>(specEquipmentData.equipmentList);

		var stats = modelGetter.getModelStat(BodyPart.WEAPON);

		equipmentItemData.add(new EquipmentItemData(weaponId, EquipmentInventorySlot.WEAPON, weaponData, stats));


		specEquipmentData = new EquipmentData(equipmentItemData);

		fightPanel.rebuild();
	}

	public void addEquippedItemsToPanel(EquipmentItemGroup equipmentItemGroup)
	{

		ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
		if (equipment == null) return;

		List<EquipmentItemData> equipmentList = new ArrayList<>();
		for (EquipmentInventorySlot slot : EquipmentInventorySlot.values())
		{
			Item item = equipment.getItem(slot.getSlotIdx());

            if (item != null)
			{
				ItemComposition composition = itemManager.getItemComposition(item.getId());
				BufferedImage itemIcon = itemManager.getImage(item.getId());
				if (slot == EquipmentInventorySlot.WEAPON)
				{
					var weaponData = combatUtility.getWeaponData();
					equipmentList.add(new EquipmentItemData(item.getId(), slot, weaponData, null));
				}
				else
				{
					equipmentList.add(new EquipmentItemData(item.getId(), slot, null, null));
				}
				fightPanel.addEquipmentItem(new EquipmentItemPanel(this, itemIcon, composition.getName(), slot.name()));
            }
		}

		var equipmentData = new EquipmentData(equipmentList);

		switch (equipmentItemGroup)
		{
            case MELEE:
				setMeleeGear();
                break;
            case RANGE:
				setRangeGear();
                break;
            case MAGIC:
				setMagicGear();
                break;
            case SPEC:
				specEquipmentData = equipmentData;
                break;
        }

		fightPanel.rebuild();
	}


	public void clearEquippedItemsPanel()
	{
		switch (selectedEquipmentGroup)
		{
            case MELEE:
				meleeEquipmentData = null;
                break;
            case RANGE:
				rangeEquipmentData = null;
                break;
            case MAGIC:
				magicEquipmentData = null;
                break;
            case SPEC:
				specEquipmentData = null;
                break;
        }

		fightPanel.clearEquippedItems();

		fightPanel.rebuild();
	}

	public void removeEquippedItem(Component component)
	{
		fightPanel.removeEquippedItem(component);

		fightPanel.rebuild();
	}

	public void equipmentGroupPressed(EquipmentItemGroup equipmentItemGroup)
	{
		clientThread.invoke(() ->
		{
			setSelectedEquipmentGroup(equipmentItemGroup);
		});
	}

	public void addEquippedButtonPressed()
	{
		clientThread.invoke(() ->
		{
			switch (selectedEquipmentGroup)
			{
                case MELEE:
                case RANGE:
                case MAGIC:
					addEquippedItemsToPanel(selectedEquipmentGroup);
                    break;
                case SPEC:
					addEquippedWeaponToPanel();
                    break;
            }

			save();
		});
	}

	public void clearButtonPressed()
	{
		clientThread.invoke(() ->
		{
			clearEquippedItemsPanel();
		});
	}

	public void removeEquippedItemPressed(Component component)
	{
		clientThread.invoke(() ->
		{
			removeEquippedItem(component);
		});
	}



	public void updateCurrentData(EquipmentData equipmentData)
	{
		currentModel = modelGetter.createModel(equipmentData);
		currentEquipmentStats = equipmentData.getEquipmentStats(itemManager);
		currentWeaponData = equipmentData.getWeapon().weaponData;
		//log.info("Current Weapon: " + currentWeaponData.toString());
	}

}
