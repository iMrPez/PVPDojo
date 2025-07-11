package com.pvpdojo.combatant;

import com.pvpdojo.*;
import com.pvpdojo.character.*;
import com.pvpdojo.character.Character;
import com.pvpdojo.character.datatypes.*;
import com.pvpdojo.combat.*;
import com.pvpdojo.combat.equipment.EquipmentStats;
import com.pvpdojo.combat.equipment.EquipmentUtility;
import com.pvpdojo.combat.weapon.WeaponAnimationData;
import com.pvpdojo.combat.weapon.WeaponUtility;
import com.pvpdojo.orientation.Orientation;
import com.pvpdojo.pathfinding.MovementType;
import com.pvpdojo.pathfinding.PathFinder;
import com.pvpdojo.timesheet.keyframe.HealthKeyFrame;
import com.pvpdojo.timesheet.keyframe.TextKeyFrame;
import com.pvpdojo.timesheet.keyframe.settings.HealthbarSprite;
import com.pvpdojo.timesheet.keyframe.settings.OverheadSprite;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Dummy extends Combatant
{

    private static final Logger log = LoggerFactory.getLogger(ModelGetter.class);

    private final PathFinder pathFinder;

    public Character dummyCharacter;

    public CombatStyle combatStyle = CombatStyle.MAGIC;

    private boolean lookingAtTarget = false;
    private int lookAtTimer = 0;
    private LocalPoint lookAtTarget;

    private long lastTickTime = -1;

    private WorldPoint startLocation;

    public boolean isTryingToSpec = false;

    private boolean isTryingToRun = true;

    private WorldPoint[] pathWP = new WorldPoint[0];

    private long lastClientTickTime = -1;
    private long clientTickDelta = -1;


    public CharacterState state = CharacterState.IDLE;

    public final CombatAI combatAI;

    public final EquipmentHandler equipment = new EquipmentHandler();

    public Dummy(Client client, PVPDojoPlugin plugin, PVPDojoConfig config, PathFinder pathFinder)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.pathFinder = pathFinder;

        setCombatStyle(CombatStyle.MAGIC);
        dummyCharacter = initializeCharacter(client.getLocalPlayer());
        spawn();

        combatAI = new CombatAI(client, plugin, config, this);
    }

    public void OnGameTick()
    {
        lastTickTime = System.currentTimeMillis();
        triggerGameTick();

        if (plugin.hasFightStarted() && config.useDummyAI())
        {
            combatAI.OnGameTick();
        }

        if (lookingAtTarget && lookAtTimer > 0)
        {
            lookAtTimer--;
        }
        else
        {
            lookingAtTarget = false;
        }



    }




    public void OnClientTick()
    {
        clientTickDelta = System.currentTimeMillis() - lastClientTickTime;
        lastClientTickTime = System.currentTimeMillis();

        if (dummyCharacter == null) return;


        if (lookingAtTarget)
        {
            lookAtUpdate();
        }

        switch (state)
        {
            case IDLE:
                break;
            case MOVING:
                break;
            case DOING_ACTION:
                if (actionAnimationTimer <= 0)
                {
                    setState(CharacterState.IDLE);
                }
                break;
        }

        updatePathing();

        setModel(dummyCharacter, getModel());

        if (state != CharacterState.DOING_ACTION && actionAnimationTimer <= 0)
        {
            setAnimation(dummyCharacter.getCharacterObject().getBaseAnimation(), AnimationType.STANDARD, true);
        }

    }


    private void equipCombatStyle(CombatStyle combatStyle)
    {
        switch (combatStyle)
        {
            case MAGIC:
                equipment.equipData(plugin.magicEquipmentData);
                break;
            case MELEE:
                equipment.equipData(plugin.meleeEquipmentData);
                break;
            case RANGE:
                equipment.equipData(plugin.rangeEquipmentData);
                break;
        }
    }

    public void tryAttack(AttackData attackData)
    {
        if (state != CharacterState.IDLE) return;

        setState(CharacterState.DOING_ACTION);

        /*var weaponData = getWeaponData();
        var attackData = new AttackData(spell, weaponData, isTryingToSpec, );*/
        requestAttack(attackData);
    }

    private void setState(CharacterState characterState)
    {
        if (characterState == CharacterState.DOING_ACTION)
        {
            actionAnimationTimer = 3;
            state = characterState;
        }

        if (actionAnimationTimer > 0) return;

        state = characterState;
    }

    private void setPathWP(WorldPoint[] path)
    {
        pathWP = path;
        currentStep = 0;
    }

    public void switchWeapon(EquipmentItemData itemData)
    {
        if (itemData != null)
        {
            equipment.equipItem(itemData);

            updateEquipment();
        }
    }

    public void updateEquipment()
    {
        plugin.updateCurrentData(equipment.getEquipmentData());
    }

    int currentStep = 0;

    public void updateWeaponAnimation()
    {
        /*var characterObject = dummyCharacter.getCharacterObject();

        setAnimation(dummyCharacter, characterObject.getBaseAnimation());*/
        var weaponAnimData = getWeaponAnimationData();
        setWeaponAnimationData(dummyCharacter.getCharacterObject(), weaponAnimData);
        //setAnimation(dummyCharacter, dummyCharacter.getCharacterObject().getBaseAnimation(), AnimationType.STANDARD, false);

    }

    public boolean isInMeleeRange()
    {
        CharacterObject dummyObject = plugin.dummy.dummyCharacter.getCharacterObject();
        LocalPoint localPoint = dummyObject.getLocation();
        var dummyWP = WorldPoint.fromLocal(client, localPoint);

        return EquipmentUtility.isInRange(dummyWP, client.getLocalPlayer().getWorldLocation(), 1);
    }

    private void reachedEndOfPath()
    {
        setState(CharacterState.IDLE);
        setAnimation(dummyCharacter.getCharacterObject().getBaseAnimation(), AnimationType.STANDARD, true);
        dummyCharacter.getCharacterObject().isMoving = false;
    }


    private boolean updatePathing()
    {
        WorldView worldView = client.getTopLevelWorldView();

        var characterObject = dummyCharacter.getCharacterObject();

        characterObject.setAnimationFrame(characterObject.getAnimationFrame());

        boolean instance = worldView.getScene().isInstance();

        //int pathLength = instance ? comp.getPathLP().length : comp.getPathWP().length;
        var pathLength = pathWP.length;

        if (pathLength <= 0 || !isInScene(dummyCharacter))
        {
            return false;
        }

        LocalPoint start = characterObject.getLocation();

        characterObject.isRunning = (isTryingToRun && pathWP.length - currentStep > 1);
        //var distance =  startWP.distanceTo(pathWP[currentStep]);
        /*log.info("Distance To: " + distance);*/
        //characterObject.isRunning = isTryingToRun && distance > 1;

        if (pathWP.length <= currentStep) return false;
        LocalPoint destination;

        /*if (instance)
        {
            destination = pathWP[currentStep];
        }
        else
        {*/
        destination = LocalPoint.fromWorld(worldView, pathWP[currentStep]);
        //}

        if (destination == null)
        {
            return false;
        }

        if (start.getX() == destination.getX() && start.getY() == destination.getY())
        {
            /*characterObject.isMoving = false;*/
            currentStep++;
            if (currentStep >= pathWP.length)
            {
                //log.info("Reached Destination!");
                reachedEndOfPath();
                //setAnimation(dummyCharacter, characterObject.getBaseAnimation(), AnimationType.STANDARD, false);
                //dummyCharacter.getCharacterObject().setLoop(true);
                return false;
            }
        }

        //setAnimation(dummyCharacter, characterObject.getBaseAnimation(), AnimationType.STANDARD, false); TODO READD

        double speed = (128 * ((double) clientTickDelta / 600) * (characterObject.isRunning ? 2 : 1));
        double turnSpeed = (128 * ((double) clientTickDelta / 75));



        var moveToPoint = moveTowards(start, destination, speed);

        int startX = start.getX();
        int startY = start.getY();
        int destX = destination.getX();
        int destY = destination.getY();

        double changeX = destX - startX;
        double changeY = destY - startY;
        double angle = Orientation.radiansToJAngle(Math.atan(changeY / changeX), changeX, changeY);
        if (!lookingAtTarget)
            dummyCharacter.setTargetOrientation((int) angle);


        int orientation = characterObject.getOrientation();
        int targetOrientation = dummyCharacter.getTargetOrientation();



        if (orientation != targetOrientation)
        {
            int newOrientation;
            int difference = Orientation.subtract(targetOrientation, orientation);

            if (difference > (turnSpeed * -1) && difference < turnSpeed)
            {
                newOrientation = targetOrientation;
            }
            else if (difference > 0)
            {
                newOrientation = Orientation.boundOrientation((int) (orientation + turnSpeed));
            }
            else
            {
                newOrientation = Orientation.boundOrientation((int) (orientation - turnSpeed));
            }

            if (!lookingAtTarget)
                characterObject.setOrientation(newOrientation);
        }


        //LocalPoint finalPoint = new LocalPoint(endX, endY, worldView);
        //log.info("Setting Location: " + finalPoint);
        characterObject.setLocation(moveToPoint, worldView.getPlane());
        return true;
    }



    public LocalPoint moveTowards(LocalPoint start, LocalPoint end, double amountToMove)
    {
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();

        double distance = (double) Math.sqrt(dx * dx + dy * dy);

        if (distance == 0)
            return start;

        double t = amountToMove / distance;
        t = Math.min(t, 1.0f); // Clamp to max distance

        int newX = Math.round((float) (start.getX() + dx * t));
        int newY = Math.round((float) (start.getY() + dy * t));

        return new LocalPoint(newX, newY);
    }


    public void moveTo(LocalPoint targetPoint, boolean run)
    {

        if (state == CharacterState.DOING_ACTION) return;

        lookingAtTarget = false;
        isTryingToRun = run;

        WorldView worldView = client.getTopLevelWorldView();
        var step = pathFinder.findPath(dummyCharacter.getCharacterObject().getLocation(),
                targetPoint, MovementType.NORMAL);

        if (step == null) return;
        WorldPoint[] path = new WorldPoint[step.length];
        for (int i = 0; i < step.length; i++)
        {
            if (step.length != 0)
            {
                int[] first = step[i];
                var worldPoint = WorldPoint.fromScene(worldView, first[0], first[1], worldView.getPlane());
                path[i] = worldPoint;
            }
        }

        setPathWP(path);
        setState(CharacterState.MOVING);
        log.info("4 - MOVING");
        dummyCharacter.getCharacterObject().isMoving = true;
        dummyCharacter.getCharacterObject().isRunning = false;

    }


    private void lookAtUpdate()
    {
        if (dummyCharacter == null || dummyCharacter.getCharacterObject() == null) {
            return;
        }
        double turnSpeed = (128 * ((double) clientTickDelta / 75));

        var dummyObject = dummyCharacter.getCharacterObject();
        var start = dummyObject.getLocation();
        int startX = start.getX();
        int startY = start.getY();
        int destX = lookAtTarget.getX();
        int destY = lookAtTarget.getY();

        double changeX = destX - startX;
        double changeY = destY - startY;
        double angle = Orientation.radiansToJAngle(Math.atan(changeY / changeX), changeX, changeY);
        dummyCharacter.setTargetOrientation((int) angle);

        int orientation = dummyObject.getOrientation();
        int targetOrientation = dummyCharacter.getTargetOrientation();

        if (orientation != targetOrientation)
        {
            int newOrientation;
            int difference = Orientation.subtract(targetOrientation, orientation);

            if (difference > (turnSpeed * -1) && difference < turnSpeed)
            {
                newOrientation = targetOrientation;
            }
            else if (difference > 0)
            {
                newOrientation = Orientation.boundOrientation((int) (orientation + turnSpeed));
            }
            else
            {
                newOrientation = Orientation.boundOrientation((int) (orientation - turnSpeed));
            }


            dummyObject.setOrientation(newOrientation);
        }

        /*double changeX = destX - startX;
        double changeY = destY - startY;
        double angle = Orientation.radiansToJAngle(Math.atan(changeY / changeX), changeX, changeY);

        var endAngle = (int)angle;
        var newAngle = moveTowards(currentAngle, endAngle, (float) turnSpeed);*/
        //dummyObject.setOrientation((int) newAngle);
    }


    public void lookAtLocation(LocalPoint target, int lookAtDuration)
    {
        lookingAtTarget = true;
        lookAtTimer = lookAtDuration;
        lookAtTarget = target;
    }

    public void stopLookAt()
    {
        lookingAtTarget = false;
    }

    public CustomModel getModel()
    {
        return plugin.currentModel;
    }

    public void setCombatStyle(CombatStyle combatStyle)
    {
        log.info("Combat Style Swapped: " + combatStyle);
        equipCombatStyle(combatStyle);
        this.combatStyle = combatStyle;
        updateEquipment();
    }

    public void spawn()
    {
        if (dummyCharacter.isActive())
        {
            despawnCharacter();

            return;
        }

        spawnCharacter();

        if (!dummyCharacter.isLocationSet())
        {
            setLocation(dummyCharacter, true, true, false, false);
        }
    }

    public void spawnCharacter()
    {
        CharacterObject characterObject = dummyCharacter.getCharacterObject();
        dummyCharacter.setActive(true);
        plugin.clientThread.invokeLater(() -> characterObject.setActive(true));
    }



    public void despawnCharacter()
    {
        CharacterObject characterObject = dummyCharacter.getCharacterObject();
        dummyCharacter.setActive(false);
        plugin.clientThread.invokeLater(() ->
        {
            if (characterObject == null)
            {
                return;
            }

            characterObject.setActive(false);
        });
    }


    public boolean isHovered()
    {
        var hull = dummyCharacter.getCharacterObject().getModelScreenExtents(client);
        return (hull != null && hull.contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY()));
    }


    public Character initializeCharacter(Player player) {
        Character character = createCharacter(
                false,
                player.getWorldLocation(),
                player.getLocalLocation(),
                new int[0],
                -1,
                false,
                false);

        var worldObject = character.getCharacterObject();

        worldObject.setModel(getModel().getModel());

        var weaponAnimationData = getWeaponAnimationData();
        setWeaponAnimationData(character.getCharacterObject(), weaponAnimationData);

        return character;
    }

    public com.pvpdojo.character.Character createCharacter(
            boolean active,
            WorldPoint worldPoint,
            LocalPoint localPoint,
            int[] localPointRegion,
            int localPointPlane,
            boolean inInstance,
            boolean setHoveredLocation)
    {


        Character character = new com.pvpdojo.character.Character(
                "Dummy",
                active,
                worldPoint != null || localPoint != null,
                new Color(255, 255, 255),
                worldPoint,
                localPoint,
                localPointRegion,
                localPointPlane,
                inInstance,
                new CharacterObject(client),
                0);



        setupRLObject(character, setHoveredLocation);

        return character;
    }

    public void setupRLObject(Character setupCharacter, boolean setHoveredTile)
    {
        CharacterObject setupObject = setupCharacter.getCharacterObject();
        client.registerRuneLiteObject(setupObject);

        boolean active = setupCharacter.isActive();
        var weaponAnimationData = getWeaponAnimationData();

        plugin.clientThread.invoke(() ->
        {
            setModel(setupCharacter, getModel());
            setAnimation(setupCharacter, weaponAnimationData != null ? weaponAnimationData.idleID : 808, AnimationType.STANDARD, true);
            setAnimationFrame(setupCharacter, 0);
            setLocation(setupCharacter, !setupCharacter.isLocationSet(), false, setHoveredTile, false);

            setupObject.setActive(active);
            setupCharacter.setActive(active);
        });
    }

    public void setModel(Character character, CustomModel model)
    {
        CharacterObject characterObject = character.getCharacterObject();
        if (characterObject == null)
        {
            return;
        }

        if (model != null)
            characterObject.setModel(model.getModel());

        var weaponAnimData = getWeaponAnimationData();

        setWeaponAnimationData(character.getCharacterObject(), weaponAnimData);
    }

    public void setWeaponAnimationData(CharacterObject characterObject, WeaponAnimationData weaponAnimationData)
    {
        characterObject.setWeaponAnimData(weaponAnimationData);
    }

    @Override
    public void fightStarted()
    {
        setHealth(getMaxHP());

        resetConsumables();
        startLocation = WorldPoint.fromLocal(client, dummyCharacter.getCharacterObject().getLocation());
    }

    @Override
    public void fightFinished()
    {
        combatAI.reset();
        reset();
        plugin.healthOverlay.setPlayerHealthKeyFrame(new HealthKeyFrame(plugin.getTicks(), 1, HealthbarSprite.DEFAULT, getMaxHP(), getMaxHP()));


    }

    private void resetConsumables()
    {
        hardFoodUses = config.dummyHardFood();
        comboFoodUses = config.dummyComboFood();
        saraBrewUses = config.dummyBrewDoses();
    }

    @Override
    public void setAnimation(int animationId, AnimationType type, boolean loop)
    {
        setAnimation(dummyCharacter, animationId, type, loop);
        if (animationId != dummyCharacter.getCharacterObject().getAnimationId())
        {
            // Make it only set anim frame to 0 if a new animation is set over the current one
            setAnimationFrame(dummyCharacter, 0);
        }
    }

    public void setAnimation(Character character, int animationId, AnimationType type, boolean loop)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
            return;

        CharacterObject characterObject = character.getCharacterObject();
        plugin.clientThread.invoke(() -> characterObject.setAnimation(animationId, type, loop));
    }

    public void setAnimationFrame(Character character, int animFrame)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
            return;

        CharacterObject characterObject = character.getCharacterObject();
        plugin.clientThread.invoke(() -> characterObject.setAnimationFrame(animFrame));
    }

    public void setOrientation(Character character, int orientation)
    {
        CharacterObject characterObject = character.getCharacterObject();
        plugin.clientThread.invokeLater(() -> characterObject.setOrientation(orientation));
    }

    public void setLocation(Character character, boolean newLocation, boolean setToPlayer, boolean setToHoveredTile, boolean setToPathStart)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
            return;

        boolean instance = client.getTopLevelWorldView().getScene().isInstance();

        if (!newLocation && !isInScene(character))
            return;

        if (instance)
        {
            setLocationInstance(character, setToPlayer, setToHoveredTile, setToPathStart);
            return;
        }

        setLocationNonInstance(character, setToPlayer, setToHoveredTile, setToPathStart);
    }

    public void setLocationNonInstance(Character character, boolean setToPlayer, boolean setToHoveredTile, boolean setToPathStart)
    {
        plugin.clientThread.invoke(() ->
        {
            WorldView worldView = client.getTopLevelWorldView();
            LocalPoint localPoint;

            if (setToPlayer)
            {
                localPoint = client.getLocalPlayer().getLocalLocation();
            }
            else if (setToHoveredTile)
            {
                Tile tile = worldView.getSelectedSceneTile();
                if (tile == null)
                    return;

                localPoint = tile.getLocalLocation();
            }
            else
            {
                if (character.getNonInstancedPoint() == null)
                {
                    localPoint = client.getLocalPlayer().getLocalLocation();
                }
                else
                {
                    localPoint = LocalPoint.fromWorld(worldView, character.getNonInstancedPoint());
                }
            }

            if (localPoint == null)
                return;

            WorldPoint newLocation = WorldPoint.fromLocalInstance(client, localPoint);

            character.setLocationSet(true);

            if (newLocation != null)
            {
                character.setInInstance(false);
                character.setNonInstancedPoint(newLocation);
            }

            CharacterObject characterObject = character.getCharacterObject();
            characterObject.setLocation(localPoint, worldView.getPlane());
            character.setActive(true, true, plugin.clientThread);

            int orientation = (int) 0;
            characterObject.setOrientation(orientation);
        });
    }

    public void setLocationInstance(Character character, boolean setToPlayer, boolean setToHoveredTile, boolean setToPathStart)
    {
        plugin.clientThread.invoke(() ->
        {
            WorldView worldView = client.getTopLevelWorldView();
            LocalPoint localPoint;

            if (setToPlayer)
            {
                localPoint = client.getLocalPlayer().getLocalLocation();
            }
            else if (setToHoveredTile)
            {
                Tile tile = worldView.getSelectedSceneTile();
                if (tile == null)
                    return;

                localPoint = tile.getLocalLocation();
            }
            else
            {
                localPoint = character.getInstancedPoint();
            }

            if (localPoint == null)
                return;

            character.setInstancedPoint(localPoint);
            character.setLocationSet(true);
            character.setInInstance(true);
            character.setInstancedRegions(client.getMapRegions());
            character.setInstancedPlane(worldView.getPlane());

            CharacterObject characterObject = character.getCharacterObject();
            characterObject.setLocation(localPoint, worldView.getPlane());
            character.setActive(true, true, plugin.clientThread);

            int orientation = 0;
            characterObject.setOrientation(orientation);

        });
    }

    public boolean isInScene(Character character)
    {
        WorldView worldView = client.getTopLevelWorldView();
        boolean instance = worldView.getScene().isInstance();
        int[] mapRegions = client.getMapRegions();
        if (instance && character.isInInstance())
        {
            if (character.getInstancedPoint() == null)
                return false;

            if (character.getInstancedPlane() != worldView.getPlane())
                return false;

            //This function is finicky with larger instances, in that only an exact region:region map will load
            //The alternative of finding any match will otherwise make spawns off if the regions don't match because the scenes won't exactly match
            Object[] mapRegionsObjects = {mapRegions};
            Object[] instancedRegionObjects = {character.getInstancedRegions()};
            return Arrays.deepEquals(mapRegionsObjects, instancedRegionObjects);
        }

        if (!instance && !character.isInInstance())
        {
            WorldPoint worldPoint = character.getNonInstancedPoint();
            if (worldPoint == null)
                return false;

            return worldPoint.isInScene(client);
        }

        return false;
    }

    public void displayText(String text, double duration)
    {
        plugin.textOverlay.setTextKeyFrame(new TextKeyFrame(plugin.getTicks(), duration, text));
    }


    public void moveToRandom()
    {
        if (isFrozen) return;

        var targetTile = EquipmentUtility.getRandomWalkableWorldPoint(client, client.getLocalPlayer().getWorldLocation(), 4);
        if (targetTile != null)
        {
            moveTo(LocalPoint.fromWorld(client.getTopLevelWorldView(), targetTile), true);
        }
    }


    @Override
    public void attackedTarget(Combatant combatant, DamageData data, DamageInfo damageInfo)
    {
        isTryingToSpec = false;

        setState(CharacterState.DOING_ACTION);

        plugin.totalHitsOnPlayer++;
        plugin.totalOffPrayerHitsOnPlayer += !damageInfo.onPrayer ? 1 : 0;
        plugin.totalDamageOnPlayer += data.getTotalDamage();

    }

    @Override
    public boolean getIsSpec()
    {
        return isTryingToSpec;
    }

    @Override
    public int getSpecCount()
    {
        return 1;
    }

    @Override
    public Combatant getTarget()
    {
        return plugin.corePlayer;
    }

    @Override
    public EquipmentStats getEquipmentStats()
    {
        return plugin.currentEquipmentStats;
    }

    @Override
    public CombatantSkills getSkills()
    {
        return plugin.fightPanel.getDummySkills();
    }

    @Override
    public WeaponData getWeaponData()
    {
        return plugin.currentWeaponData;
    }


    @Override
    public WeaponAnimationData getWeaponAnimationData()
    {

        var weaponData = getWeaponData();
        if (weaponData != null)
        {
            var weapon = WeaponUtility.getWeapon(weaponData.getWeaponID());
            if (weapon != null && weapon.WeaponAnimationData != null)
            {
                return weapon.WeaponAnimationData;
            }
        }



        return null;
    }

    @Override
    public int getMaxHP()
    {
        return plugin.fightPanel.getDummyHP();
    }

    @Override
    protected void displayCombatTicks(int newActionTicks)
    {
        if (newActionTicks > 0)
        {
            plugin.textOverlay.setDummyAttackTicksFrame(new TextKeyFrame(plugin.getTicks(), 1, "" + newActionTicks));
        }
        else
        {
            plugin.textOverlay.setDummyAttackTicksFrame(null);
        }
    }


    @Override
    protected void displayDamage(DamageData data)
    {
        if (data.isSpec)
        {
            for (int i = 0; i < data.getSpecHitCount(); i++)
            {
                var damage = data.damages[i] * data.getSpecMultiplier();
                plugin.hitsplatOverlay.HitDummy(plugin.getTicks(), EquipmentUtility.getHitsplatSprite((int)damage), (int)damage);
            }
        }
        else
        {
            for (int i = 0; i < data.getNormalHitCount(); i++)
            {
                var damage = data.damages[i] * data.getSpecMultiplier();
                plugin.hitsplatOverlay.HitDummy(plugin.getTicks(), EquipmentUtility.getHitsplatSprite((int)damage), (int)damage);
            }
        }

        var weaponAnimData = getWeaponAnimationData();
        if (weaponAnimData != null)
        {
            setAnimation(weaponAnimData.hitID, AnimationType.ACTIVE, false);
        }
        EquipmentUtility.playCombatStyleHitSound(client, data.combatStyle);
        plugin.healthOverlay.setDummyHealthKey(new HealthKeyFrame(plugin.getTicks(), 10, HealthbarSprite.DEFAULT, getMaxHP(), getHealth()));

        if (getHealth() <= 0)
        {
            plugin.stopFight(true);
        }
    }

    @Override
    public int getHitDelayOffset() {
        return 0;
    }

    public void returnHome()
    {
        moveTo(LocalPoint.fromWorld(client.getTopLevelWorldView(), startLocation), false);
    }

    public int getDistanceToPlayer()
    {
        var worldPoint = WorldPoint.fromLocal(client, dummyCharacter.getCharacterObject().getLocation());
        var playerPoint = client.getLocalPlayer().getWorldLocation();

        return worldPoint.distanceTo(playerPoint);
    }

    public void moveToPlayer(boolean run)
    {
        if (isFrozen) return;
        var worldPoint = WorldPoint.fromLocal(client, dummyCharacter.getCharacterObject().getLocation());
        var targetPoint = EquipmentUtility.getNearestAdjacentTile(worldPoint, client.getLocalPlayer().getWorldLocation(), 0);
        moveTo(LocalPoint.fromWorld(client.getTopLevelWorldView(), targetPoint), run);
    }

    public void moveToUnderPlayer(boolean run)
    {
        if (isFrozen) return;
        moveTo(client.getLocalPlayer().getLocalLocation(), run);
    }

    public void setPrayers(List<Prayer> newPrayers)
    {
        prayers = newPrayers;

        if (newPrayers.contains(Prayer.PROTECT_FROM_MAGIC))
        {
            setOverhead(OverheadSprite.PROTECT_MAGIC);
        }
        else if (newPrayers.contains(Prayer.PROTECT_FROM_MISSILES))
        {
            setOverhead(OverheadSprite.PROTECT_RANGED);
        }
        else if (newPrayers.contains(Prayer.PROTECT_FROM_MELEE))
        {
            setOverhead(OverheadSprite.PROTECT_MELEE);
        }
        else
        {
            setOverhead(OverheadSprite.NONE);
        }
    }

    protected void setOverhead(OverheadSprite sprite)
    {
        plugin.overheadOverlay.setDummyPrayerSprite(sprite);

    }
    protected void setSkull(OverheadSprite sprite)
    {
        plugin.overheadOverlay.setDummySkullSprite(sprite);
    }

    /*public void updateProgramPath(Program program, boolean gameStateChanged, boolean instanced)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return;
        }

        if (instanced)
        {
            updateInstancedProgramPath(program, gameStateChanged);
            return;
        }

        updateNonInstancedProgramPath(program, gameStateChanged);
    }*/

    /*public void updateInstancedProgramPath(Program program, boolean gameStateChanged)
    {
        Scene scene = client.getTopLevelWorldView().getScene();
        ProgramComp comp = program.getComp();
        LocalPoint[] stepsLP = comp.getStepsLP();
        LocalPoint[] pathLP = new LocalPoint[0];
        Coordinate[] allCoordinates = new Coordinate[0];

        if (stepsLP.length < 2)
        {
            comp.setPathLP(pathLP);
            comp.setCoordinates(allCoordinates);
            return;
        }

        for (int i = 0; i < stepsLP.length - 1; i++)
        {
            Coordinate[] coordinates;
            coordinates = pathFinder.getPath(stepsLP[i], stepsLP[i + 1], comp.getMovementType());

            if (coordinates == null)
            {
                if (!gameStateChanged)
                    log.info("No Path Found!");

                comp.setPathFound(false);
                return;
            }

            comp.setPathFound(true);
            allCoordinates = ArrayUtils.addAll(allCoordinates, coordinates);

            Direction direction = Direction.UNSET;

            for (int c = 0; c < coordinates.length - 1; c++)
            {
                int x = coordinates[c].getColumn();
                int y = coordinates[c].getRow();
                int nextX = coordinates[c + 1].getColumn();
                int nextY = coordinates[c + 1].getRow();
                int changeX = nextX - x;
                int changeY = nextY - y;

                Direction newDirection = Direction.getDirection(changeX, changeY);
                if (direction == Direction.UNSET || direction != newDirection)
                {
                    direction = newDirection;
                    LocalPoint localPoint = LocalPoint.fromScene(x, y, scene);
                    pathLP = ArrayUtils.add(pathLP, localPoint);
                }
            }
        }

        pathLP = ArrayUtils.add(pathLP, stepsLP[stepsLP.length - 1]);
        comp.setPathLP(pathLP);
        comp.setCoordinates(allCoordinates);
    }*/

    /*public void updateNonInstancedProgramPath(Program program, boolean gameStateChanged)
    {
        Scene scene = client.getTopLevelWorldView().getScene();
        ProgramComp comp = program.getComp();
        WorldPoint[] stepsWP = comp.getStepsWP();
        WorldPoint[] pathWP = new WorldPoint[0];
        Coordinate[] allCoordinates = new Coordinate[0];

        if (stepsWP.length < 2)
        {
            comp.setPathWP(pathWP);
            comp.setCoordinates(allCoordinates);
            return;
        }

        for (int i = 0; i < stepsWP.length - 1; i++)
        {
            Coordinate[] coordinates;
            coordinates = pathFinder.getPath(stepsWP[i], stepsWP[i + 1], comp.getMovementType());

            if (coordinates == null) {
                if (!gameStateChanged)
                    log.info("No Path Found!");

                comp.setPathFound(false);
                return;
            }

            comp.setPathFound(true);
            allCoordinates = ArrayUtils.addAll(allCoordinates, coordinates);

            Direction direction = Direction.UNSET;

            for (int c = 0; c < coordinates.length - 1; c++)
            {
                int x = coordinates[c].getColumn();
                int y = coordinates[c].getRow();
                int nextX = coordinates[c + 1].getColumn();
                int nextY = coordinates[c + 1].getRow();
                int changeX = nextX - x;
                int changeY = nextY - y;

                Direction newDirection = Direction.getDirection(changeX, changeY);
                if (direction == Direction.UNSET || direction != newDirection)
                {
                    direction = newDirection;
                    LocalPoint localPoint = LocalPoint.fromScene(x, y, scene);
                    pathWP = ArrayUtils.add(pathWP, WorldPoint.fromLocalInstance(client, localPoint));

                }
            }
        }

        pathWP = ArrayUtils.add(pathWP, stepsWP[stepsWP.length - 1]);
        comp.setPathWP(pathWP);
        comp.setCoordinates(allCoordinates);
    }*/

    public float getTickProgress()
    {
        if (lastTickTime == -1)
            return Long.MAX_VALUE; // not initialized yet


        return Math.min(((float) (System.currentTimeMillis() - lastTickTime) / 1000) / 0.5f, 1);
    }
}
