package net.dev213.ms.handlers.item;

import database.data.CharacterPotentialData;
import net.dev213.ms.Server;
import net.dev213.ms.client.Account;
import net.dev213.ms.client.Client;
import net.dev213.ms.client.character.BroadcastMsg;
import net.dev213.ms.client.character.Char;
import net.dev213.ms.client.character.ExtendSP;
import net.dev213.ms.client.character.avatar.BeautyAlbum;
import net.dev213.ms.client.character.items.*;
import net.dev213.ms.client.character.potential.CharacterPotential;
import net.dev213.ms.client.character.potential.CharacterPotentialMan;
import net.dev213.ms.client.character.quest.Quest;
import net.dev213.ms.client.character.skills.Option;
import net.dev213.ms.client.character.skills.Skill;
import net.dev213.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.dev213.ms.client.character.skills.temp.TemporaryStatManager;
import net.dev213.ms.connection.InPacket;
import net.dev213.ms.connection.packet.*;
import net.dev213.ms.constants.*;
import net.dev213.ms.enums.*;
import net.dev213.ms.handlers.Handler;
import net.dev213.ms.handlers.header.InHeader;
import net.dev213.ms.life.pet.Pet;
import net.dev213.ms.life.pet.PetSkill;
import net.dev213.ms.loaders.FieldData;
import net.dev213.ms.loaders.ItemData;
import net.dev213.ms.loaders.SkillData;
import net.dev213.ms.loaders.StringData;
import net.dev213.ms.loaders.containerclasses.ItemInfo;
import net.dev213.ms.loaders.containerclasses.MakingSkillRecipe;
import net.dev213.ms.scripts.ScriptType;
import net.dev213.ms.util.Position;
import net.dev213.ms.util.Util;
import net.dev213.ms.world.World;
import net.dev213.ms.world.field.Field;
import net.dev213.ms.world.field.Portal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.util.*;

import static net.dev213.ms.enums.ChatType.*;
import static net.dev213.ms.enums.EquipBaseStat.iuc;
import static net.dev213.ms.enums.EquipBaseStat.tuc;
import static net.dev213.ms.enums.InvType.*;
import static net.dev213.ms.enums.InventoryOperation.Move;

public class ItemHandler {

    private static final Logger log = LogManager.getLogger(ItemHandler.class);

    @Handler(op = InHeader.USER_PORTAL_SCROLL_USE_REQUEST)
    public static void handleUserPortalScrollUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        if ((field.getFieldLimit() & FieldOption.PortalScrollLimit.getVal()) > 0 || !field.isChannelField()) {
            chr.chatMessage("You may not use a return scroll in this map.");
            chr.dispose();
            return;
        }
        c.verifyTick(inPacket);
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();

        Item item = chr.getConsumeInventory().getItemBySlot(slot);

        if (item == null || item.getItemId() != itemID || item.getQuantity() < 1 || !chr.isInValidState()) {
            chr.dispose("You cannot use this return scroll right now.");
            return;
        }

        ItemInfo ii = ItemData.getItemInfoByID(itemID);
        Field toField;

        if (itemID != 2030000) {
            toField = chr.getOrCreateFieldByCurrentInstanceType(ii.getMoveTo());
        } else {
            toField = chr.getOrCreateFieldByCurrentInstanceType(field.getReturnMap());
        }
        Portal portal = toField.getDefaultPortal();
        chr.warp(toField, portal);
        chr.consumeItem(itemID, 1);
    }


    @Handler(op = InHeader.USER_STAT_CHANGE_ITEM_CANCEL_REQUEST)
    public static void handleUserStatChangeItemCancelRequest(Char chr, InPacket inPacket) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int itemID = inPacket.decodeInt();
        tsm.removeStatsBySkill(itemID);
        tsm.sendResetStatPacket();
    }

    @Handler(op = InHeader.USER_SKILL_RESET_ITEM_USE_REQUEST)
    public static void handleUserResetSP(Char chr, InPacket inPacket) {
        if (inPacket != null) {
            chr.getClient().verifyTick(inPacket);
            short slot = inPacket.decodeShort();
            int itemID = inPacket.decodeInt();
            Item item = chr.getConsumeInventory().getItemBySlot(slot);
            if (item == null || itemID != item.getItemId() || itemID / 10 != 250000) {
                chr.write(WvsContext.resetItemResult(true, chr.getId(), false, true));
                return;
            }
            chr.consumeItem(item);
        }
        short jobID = chr.getJob();
        List<Skill> skills = new ArrayList<>();
        List<Integer> jobs = SkillConstants.getSkillRootFromJob(jobID);
        ExtendSP extendSP = JobConstants.isExtendSpJob(jobID) ? chr.getAvatarData().getCharacterStat().getExtendSP() : null;
        int sp = 0;
        for (int job : jobs) {
            if (JobConstants.isBeginnerJob((short) job)) {
                continue;
            }
            for (Skill skill : SkillData.getSkillsByJob((short) job)) {
                Skill curSkill = chr.getSkill(skill.getSkillId());
                if (curSkill != null && SkillData.getSkillInfoById(skill.getSkillId()).getHyper() == 0) {
                    sp += curSkill.getCurrentLevel();
                    curSkill.setCurrentLevel(0);
                    skills.add(curSkill);
                    chr.addSkill(curSkill);
                }
            }
            if (extendSP != null) {
                byte jobLevel = (byte) JobConstants.getJobLevelDetail((short) job);
                extendSP.setSpToJobLevel(jobLevel, extendSP.getSpByJobLevel(jobLevel) + sp);
                sp = 0;
            }
        }
        if (extendSP != null) {
            chr.write(WvsContext.statChanged(Collections.singletonMap(Stat.sp, extendSP)));
        } else {
            chr.setStatAndSendPacket(Stat.sp, chr.getStat(Stat.sp) + sp);
        }
        if (!skills.isEmpty()) {
            chr.write(WvsContext.changeSkillRecordResult(skills, true, false, false, false));
        }
        chr.write(WvsContext.resetItemResult(true, chr.getId(), true, true));
    }

    @Handler(op = InHeader.USER_ABILITY_RESET_ITEM_USE_REQUEST)
    public static void handleUserResetAP(Char chr, InPacket inPacket) {
        if (inPacket != null) {
            chr.getClient().verifyTick(inPacket);
            short slot = inPacket.decodeShort();
            int itemID = inPacket.decodeInt();
            Item item = chr.getConsumeInventory().getItemBySlot(slot);
            if (item == null || itemID != item.getItemId() || itemID / 10 != 250100) {
                chr.write(WvsContext.resetItemResult(true, chr.getId(), false, false));
                return;
            }
            chr.consumeItem(item);
        }
        int ap = chr.getStat(Stat.ap);
        ap += chr.getStat(Stat.str) - 4;
        ap += chr.getStat(Stat.dex) - 4;
        ap += chr.getStat(Stat.inte) - 4;
        ap += chr.getStat(Stat.luk) - 4;
        chr.setStat(Stat.str, 4);
        chr.setStat(Stat.dex, 4);
        chr.setStat(Stat.inte, 4);
        chr.setStat(Stat.luk, 4);
        chr.setStat(Stat.ap, ap);
        Map<Stat, Object> stats = Map.of(Stat.str, (short) 4, Stat.dex, (short) 4, Stat.inte, (short) 4, Stat.luk, (short) 4, Stat.ap, (short) ap);
        chr.write(WvsContext.statChanged(stats));
        chr.write(WvsContext.resetItemResult(true, chr.getId(), true, false));
    }

    @Handler(op = InHeader.USER_CONSUME_CASH_ITEM_USE_REQUEST)
    public static void handleUserConsumeCashItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Inventory cashInv = chr.getInventoryByType(InvType.CASH);
        c.verifyTick(inPacket);
        short pos = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Item item = cashInv.getItemBySlot(pos);
        ItemInfo itemInfo = ItemData.getItemInfoByID(itemID);
        BeautyAlbum album = chr.getBeautyAlbum();
        Item cube;
        int cubeCount;
        if (item == null || item.getItemId() != itemID) {
            return;
        }
        if (itemID / 10000 == 553) {
            // Reward items
            if (itemID % 10000 < 3000) {
                Object reward;
                if (itemInfo != null && (reward = itemInfo.getRandomReward()) != null) {
                    if (reward instanceof Item) {
                        chr.addItemToInventory((Item) reward);
                    } else if (reward instanceof Integer && (int) reward != 0) {
                        chr.addMoney((int) reward);
                    } else {
                        chr.chatMessage("Oh, you're unlucky."); // should not happen
                    }
                } else {
                    chr.chatMessage("Oh, you're unlucky.");
                }
            } else if (itemID == 5534000) { // Tim's Secret lab
                short ePos = (short) inPacket.decodeInt();
                InvType invType = ePos < 0 ? EQUIPPED : EQUIP;
                Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
                if (equip == null) {
                    chr.dispose("Could not find equip.");
                    return;
                } else if (!ItemConstants.canEquipHavePotential(equip)) {
                    chr.dispose("You cannot use Tim's Secret Lab on this item.");
                    return;
                }
                equip.setHiddenOptionBase(ItemGrade.HiddenRare.getVal(), 100);
                c.write(FieldPacket.showItemUpgradeEffect(chr.getId(), true, false, itemID, equip.getItemId(), false));
                equip.updateToChar(chr);
            }
        } else if (itemID / 10000 == 539) {
            // Avatar Megaphones
            List<String> lineList = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                String line = inPacket.decodeString();
                lineList.add(line);
            }
            boolean whisperIcon = inPacket.decodeByte() != 0;
            World world = c.getWorld();
            world.broadcastPacket(WvsContext.setAvatarMegaphone(chr, itemID, lineList, whisperIcon));

        } else if (itemID / 10000 == 519) {
            // Pet Skill Items
            long sn = inPacket.decodeLong();
            PetSkill ps = ItemConstants.getPetSkillFromID(itemID);
            if (ps == null) {
                chr.chatMessage(String.format("Unhandled pet skill item %d", itemID));
                return;
            }
            Item pi = chr.getCashInventory().getItemBySN(sn);
            if (!(pi instanceof PetItem)) {
                chr.chatMessage("Could not find that pet.");
                return;
            }
            boolean add = itemID < 5191000; // add property doesn't include the "Slimming Medicine"
            PetItem petItem = (PetItem) pi;
            if (add) {
                petItem.addPetSkill(ps);
            } else {
                petItem.removePetSkill(ps);
            }
            petItem.updateToChar(chr);
        } else if (ItemConstants.isMiuMiuMerchant(itemID)) {

            chr.getScriptManager().openShop(9090000);

        } else if (ItemConstants.isPortableStorage(itemID)) {

            chr.getScriptManager().openTrunk(1022005);
        } else if (itemID / 10000 == 512) { // Weather Items
            chr.getField().blowWeather(itemID, inPacket.decodeString(), 10, null);
            int stateChangeItem = itemInfo.getStateChangeItem();
            if (stateChangeItem != 0 && !ItemData.getItemInfoByID(stateChangeItem).getSpecStats().isEmpty()) {
                chr.getField().getChars().forEach(it -> ItemBuffs.giveItemBuffsFromItemID(it, it.getTemporaryStatManager(), stateChangeItem));
            }
        } else {

            Equip medal = (Equip) chr.getEquippedInventory().getFirstItemByBodyPart(BodyPart.Medal);
            int medalInt = 0;
            if (medal != null) {
                medalInt = (medal.getAnvilId() == 0 ? medal.getItemId() : medal.getAnvilId()); // Check for Anvilled medal
            }
            String medalString = (medalInt == 0 ? "" : String.format("<%s> ", StringData.getItemStringById(medalInt)));

            switch (itemID) {
                case ItemConstants.HYPER_TELEPORT_ROCK: // Hyper Teleport Rock
                    short type = inPacket.decodeShort();
                    if (type == 0 || type == 1) {
                        int fieldId = inPacket.decodeInt();
                        Field field = chr.getOrCreateFieldByCurrentInstanceType(fieldId);
                        if (field == null || (chr.getField().getFieldLimit() & FieldOption.TeleportItemLimit.getVal()) > 0 ||
                                !FieldData.getWorldMapFields().contains(fieldId)) {
                            chr.chatMessage("You may not warp to that map, as you cannot teleport from your current map.");
                            chr.dispose();
                            return;
                        }
                        chr.setInstance(null);
                        chr.warp(field);
                    } else {
                        String targetName = inPacket.decodeString();
                        int worldID = chr.getClient().getChannelInstance().getWorldId().getVal();
                        World world = Server.getInstance().getWorldById(worldID);
                        Char targetChr = world.getCharByName(targetName);

                        // Target doesn't exist or target char is not a regular player
                        if (targetChr == null || targetChr.getAccount().getAccountType().getVal() >= 1) {
                            chr.chatMessage(String.format("%s is not online.", targetName));
                            chr.dispose();
                            return;
                        }

                        Position targetPosition = targetChr.getPosition();

                        Field targetField = targetChr.getField();
                        if (targetField == null || (targetField.getFieldLimit() & FieldOption.TeleportItemLimit.getVal()) > 0) {
                            chr.chatMessage("You may not warp to that map, as the targeted map cannot be teleported to.");
                            chr.dispose();
                            return;
                        }
                        // Target is in an instanced Map
                        if (targetChr.getInstance() != null) {
                            chr.chatMessage(String.format("cannot find %s", targetName));
                            // Change channels & warp & teleport
                        } else if (targetChr.getClient().getChannel() != c.getChannel()) {
                            chr.setInstance(null);
                            chr.chatMessage(String.format("cannot find %s on this channel", targetName));
                            //chr.changeChannelAndWarp(targetChr.getClient().getChannel(), fieldId); // Makes you warp to player without being in the same CH
                            return;
                            // warp & teleport
                        } else if (targetChr.getFieldID() != chr.getFieldID()) {
                            chr.setInstance(null);
                            chr.warp(targetField);
                            chr.write(FieldPacket.teleport(targetPosition, chr));
                            // teleport
                        } else {
                            chr.write(FieldPacket.teleport(targetPosition, chr));
                        }
                    }
                    break;
                case 5170000:
                    int petID = inPacket.decodeInt();
                    inPacket.decodeInt(); //??
                    String petName1 = inPacket.decodeString();

                    Pet petid = chr.getPetById(petID);

                    if (petName1.length() > 13) {
                        chr.chatMessage("no");
                        chr.dispose();
                        return;
                    }

                    if (petid != null) {
                        petid.setName(petName1);
                        petid.getItem().setName(petName1);
                        int idx = petid.getIdx();
                        chr.write(UserLocal.petNameChange(chr, idx, petName1));
                    }
                    break;
                case ItemConstants.OCCULT_CUBE:
                case ItemConstants.RED_CUBE: // Red Cube
                case ItemConstants.BLACK_CUBE: // Black cube
                    cube = chr.getCashInventory().getItemByItemID(itemID);
                    cubeCount = cube.getQuantity();
                    cubeCount--;

                    short ePos = (short) inPacket.decodeInt();
                    InvType invType = ePos < 0 ? EQUIPPED : EQUIP;
                    Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
                    if (equip == null) {
                        chr.chatMessage(SystemNotice, "Could not find equip.");
                        chr.dispose();
                        return;
                    } else if (equip.getBaseGrade() < ItemGrade.Rare.getVal()) {
                        String msg = String.format("Character %d tried to use cube (id %d) an equip without a potential (id %d)", chr.getId(), itemID, equip.getItemId());
                        chr.getOffenseManager().addOffense(msg);
                        chr.dispose();
                        return;
                    } else if (itemID == ItemConstants.OCCULT_CUBE && equip.getBaseGrade() > ItemGrade.Epic.getVal()) {
                        chr.chatMessage(SystemNotice, "You may only use this on Rare or Epic Items!");
                        chr.dispose();
                        return;
                    }
                    short hiddenValue = ItemGrade.getHiddenGradeByVal(equip.getBaseGrade()).getVal();
                    int tierUpChance = ItemConstants.getTierUpChance(itemID, hiddenValue);
                    boolean tierUp = tierUpChance > 0 && Util.succeedProp(tierUpChance);
                    if (tierUp && (itemID != ItemConstants.OCCULT_CUBE || equip.getBaseGrade() != ItemGrade.Epic.getVal())) {
                        hiddenValue++;
                    }
                    if (itemID == ItemConstants.RED_CUBE) {
                        equip.setHiddenOptionBase(hiddenValue, 0);
                        equip.releaseOptions(false);
                        chr.getField().broadcastPacket(UserPacket.showItemMemorialEffect(chr.getId(), true, itemID, ePos, pos));
                        c.write(FieldPacket.redCubeResult(chr.getId(), tierUp, itemID, ePos, equip, cubeCount));
//                        c.write(FieldPacket.showItemReleaseEffect(chr.getId(), ePos, false));
                        equip.updateToChar(chr);
                        if (invType == EQUIPPED) {
                            chr.recalcStats(equip.getBaseStatFlag());
                        }
                    } else {
                        if (chr.getMemorialCubeInfo() == null) {
                            chr.setMemorialCubeInfo(new MemorialCubeInfo(equip.deepCopy(), itemID));
                        }
                        Equip newEquip = chr.getMemorialCubeInfo().getEquip();
                        newEquip.setHiddenOptionBase(hiddenValue, 0);
                        newEquip.releaseOptions(false);
                        chr.getField().broadcastPacket(UserPacket.showItemMemorialEffect(chr.getId(), true, itemID, ePos, pos));
                        c.write(WvsContext.blackCubeResult(equip, chr.getMemorialCubeInfo(), cubeCount));
                    }
                    break;
                case ItemConstants.VIOLET_CUBE: // Violet cube
                    ePos = (short) inPacket.decodeInt();
                    invType = ePos < 0 ? EQUIPPED : EQUIP;
                    equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
                    if (equip == null) {
                        chr.chatMessage(SystemNotice, "Could not find equip.");
                        chr.dispose();
                        return;
                    }
                    long number = equip.getId();
                    Equip copy = equip.deepCopy();
                    if (copy.getBaseGrade() < ItemGrade.Rare.getVal()) {
                        String msg = String.format("Character %d tried to use cube (id %d) an equip without a potential (id %d)", chr.getId(), itemID, equip.getItemId());
                        chr.getOffenseManager().addOffense(msg);
                        chr.dispose();
                        return;
                    } else if (chr.getMoney() < 36000) { // TODO: correct cost by level
                        c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("You do not have enough mesos.")));
                        c.write(CUIHandler.violetCubeResult(0, 1, 0, Collections.emptyList()));
                        return;
                    }
                    byte line = (byte) Arrays.stream(copy.getOptionBase()).filter(option -> option > 0).count();
                    if (line <= 1) {
                        chr.chatMessage(SystemNotice, "You cannot use Violet Cube on this item.");
                        c.write(CUIHandler.violetCubeResult(0, 1, line, Collections.emptyList()));
                        return;
                    }
                    short optionGrade = ItemGrade.getHiddenGradeByVal(copy.getBaseGrade()).getVal();
                    tierUpChance = ItemConstants.getTierUpChance(itemID, optionGrade);
                    if (tierUpChance > 0 && Util.succeedProp(tierUpChance)) {
                        optionGrade++;
                    }
                    copy.setItemState(optionGrade);
                    List<Integer> options = new ArrayList<>(line * 2);
                    for (byte i = 0; i < line * 2; i++) {
                        options.add(copy.getRandomOption(false, i % 3)); // ensure 2 of 4/6 options are higher rank
                    }
                    Quest quest = chr.getQuestManager().getOrCreateQuestById(QuestConstants.VIOLET_CUBE_INFO);
                    quest.setProperty("o", options.toString().replace("[", "").replace("]", "").replace(" ", ""));
                    quest.setProperty("n", String.valueOf(number));
                    quest.setProperty("p", ePos);
                    quest.setProperty("c", line);
                    quest.setProperty("i", copy.getItemId());
                    quest.setProperty("og", optionGrade);
                    chr.write(WvsContext.questRecordExMessage(quest));
                    chr.deductMoney(36000); // TODO: correct cost by level
                    c.write(FieldPacket.showItemUnReleaseEffect(chr.getId(), true, itemID, 0, copy.getItemId()));
                    c.write(CUIHandler.violetCubeResult(0, 0, line, options));
                    chr.chatMessage(options.toString());
                    break;
                case ItemConstants.BONUS_POT_CUBE: // Bonus Potential Cube
                case ItemConstants.SPECIAL_BONUS_POT_CUBE: // [Special] Bonus Potential Cube
                case ItemConstants.WHITE_BONUS_POT_CUBE: // White Bonus Potential Cube
                    cube = chr.getCashInventory().getItemByItemID(itemID);
                    cubeCount = cube.getQuantity();
                    cubeCount--;

                    if (c.getWorld().isReboot()) {
                        chr.getOffenseManager().addOffense(String.format("Character %d attempted to use a bonus potential cube in reboot world.", chr.getId()));
                        chr.dispose();
                        return;
                    }
                    ePos = (short) inPacket.decodeInt();
                    invType = ePos < 0 ? EQUIPPED : EQUIP;
                    equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
                    if (equip == null) {
                        chr.chatMessage(SystemNotice, "Could not find equip.");
                        chr.dispose();
                        return;
                    } else if (equip.getBonusGrade() < ItemGrade.Rare.getVal()) {
                        chr.getOffenseManager().addOffense(String.format("Character %d tried to use cube (id %d) an equip without a potential (id %d)", chr.getId(), itemID, equip.getItemId()));
                        chr.dispose();
                        return;
                    }
                    hiddenValue = ItemGrade.getHiddenGradeByVal(equip.getBonusGrade()).getVal();
                    tierUpChance = ItemConstants.getTierUpChance(itemID, hiddenValue);
                    tierUp = tierUpChance > 0 && Util.succeedProp(tierUpChance);
                    if (tierUp) {
                        hiddenValue++;
                    }
                    if (itemID != ItemConstants.WHITE_BONUS_POT_CUBE) {
                        equip.setHiddenOptionBonus(hiddenValue, 0);
                        equip.releaseOptions(true);
                        chr.getField().broadcastPacket(UserPacket.showItemMemorialEffect(chr.getId(), true, itemID, ePos, pos));
                        c.write(FieldPacket.bonusCubeResult(chr.getId(), tierUp, itemID, ePos, equip, cubeCount));
//                        c.write(FieldPacket.showItemReleaseEffect(chr.getId(), ePos, true));
                        equip.updateToChar(chr);
                        if (invType == EQUIPPED) {
                            chr.recalcStats(equip.getBaseStatFlag());
                        }
                    } else {
                        if (chr.getMemorialCubeInfo() == null) {
                            chr.setMemorialCubeInfo(new MemorialCubeInfo(equip.deepCopy(), itemID));
                        }
                        Equip newEquip = chr.getMemorialCubeInfo().getEquip();
                        newEquip.setHiddenOptionBonus(hiddenValue, 0);
                        newEquip.releaseOptions(true);
                        chr.getField().broadcastPacket(UserPacket.showItemMemorialEffect(chr.getId(), true, itemID, ePos, pos));
                        c.write(WvsContext.whiteCubeResult(equip, chr.getMemorialCubeInfo(), cubeCount));
                    }
                    break;
                case 5520001: // Platinum Scissors of Karma
                    inPacket.decodeInt(); // 1
                    ePos = (short) inPacket.decodeInt();
                    equip = (Equip) chr.getEquipInventory().getItemBySlot(ePos);
                    if (equip == null) {
                        chr.chatMessage("Could not find equip.");
                        chr.dispose();
                        return;
                    } else if (equip.hasAttribute(EquipAttribute.Locked)
                            || !equip.hasAttribute(EquipAttribute.Untradable)
                            || equip.hasAttribute(EquipAttribute.UntradableAfterTransaction)
                            || equip.getCuttable() == 0) {
                        chr.chatMessage("You cannot use Scissors of Karma on this equip.");
                        chr.dispose();
                        return;
                    }
                    equip.addAttribute(EquipAttribute.UntradableAfterTransaction);
                    equip.setTradeBlock(false);
                    equip.setEquipTradeBlock(true);
                    //equip.setCuttable((short) 0);
                    equip.updateToChar(chr);
                    break;
                case 5750001: // Nebulite Diffuser
                    ePos = inPacket.decodeShort();
                    equip = (Equip) chr.getEquipInventory().getItemBySlot(ePos);
                    if (equip == null || equip.getSocket(0) == 0 || equip.getSocket(0) == ItemConstants.EMPTY_SOCKET_ID) {
                        chr.chatMessage("That item currently does not have an active socket.");
                        chr.dispose();
                        return;
                    }
                    equip.setSocket(0, ItemConstants.EMPTY_SOCKET_ID);
                    equip.updateToChar(chr);
                    break;
                case 5072000: // Super Megaphone
                    String text = inPacket.decodeString();
                    boolean whisperIcon = inPacket.decodeByte() != 0;
                    World world = chr.getClient().getWorld();
                    BroadcastMsg smega = BroadcastMsg.megaphone(
                            String.format("%s%s : %s", medalString, chr.getName(), text),
                            (byte) chr.getClient().getChannelInstance().getChannelId(), whisperIcon, chr);
                    world.broadcastPacket(WvsContext.broadcastMsg(smega));
                    break;
                case 5076000: // Item Megaphone
                    text = inPacket.decodeString();
                    whisperIcon = inPacket.decodeByte() != 0;
                    boolean eqpSelected = inPacket.decodeByte() != 0;
                    invType = EQUIP;
                    int itemPosition = 0;
                    if (eqpSelected) {
                        invType = InvType.getInvTypeByVal(inPacket.decodeInt());
                        itemPosition = inPacket.decodeInt();
                        if (invType == EQUIP && itemPosition < 0) {
                            invType = EQUIPPED;
                        }
                    }
                    Item broadcastedItem = chr.getInventoryByType(invType).getItemBySlot(itemPosition);

                    world = chr.getClient().getWorld();
                    smega = BroadcastMsg.itemMegaphone(String.format("%s%s : %s", medalString, chr.getName(), text),
                            (byte) chr.getClient().getChannelInstance().getChannelId(), whisperIcon, eqpSelected,
                            broadcastedItem, chr);
                    world.broadcastPacket(WvsContext.broadcastMsg(smega));
                    break;
                case 5077000: // Triple Megaphone
                    byte stringListSize = inPacket.decodeByte();
                    List<String> stringList = new ArrayList<>();
                    for (int i = 0; i < stringListSize; i++) {
                        stringList.add(String.format("%s%s : %s", medalString, chr.getName(), inPacket.decodeString()));
                    }
                    whisperIcon = inPacket.decodeByte() != 0;

                    world = chr.getClient().getWorld();
                    smega = BroadcastMsg.tripleMegaphone(stringList,
                            (byte) chr.getClient().getChannelInstance().getChannelId(), whisperIcon, chr);
                    world.broadcastPacket(WvsContext.broadcastMsg(smega));
                    break;
                case 5062400: // Fusion anvil
                case 5062402: // Medal Fusion anvil
                case 5062405: // Fusion anvil
                    int appearancePos = inPacket.decodeInt();
                    int functionPos = inPacket.decodeInt();
                    Inventory inv = chr.getEquipInventory();
                    Equip appearance = (Equip) inv.getItemBySlot(appearancePos);
                    Equip function = (Equip) inv.getItemBySlot(functionPos);
                    if (appearance == null || function == null) {
                        chr.dispose("Could not find equip.");
                        return;
                    } else if (appearance.getItemId() / 10000 != function.getItemId() / 10000) {
                        chr.dispose("Fusion only works on equipment of the same type. Please check the item you want to Fuse.");
                        return;
                    } else if (itemID == 5062402 && !ItemConstants.isMedal(appearance.getItemId())) {
                        chr.dispose("Medal Fusion Anvil only works on medals.");
                        return;
                    } else if (itemID != 5062402 && ItemConstants.isMedal(appearance.getItemId())) {
                        chr.dispose("Fusion Anvil doesn't work on medals.");
                        return;
                    }
                    if (appearance.getItemId() / 10000 == function.getItemId() / 10000) {
                        function.setOption(6, appearance.getItemId() % 10000, false);
                    }
                    function.updateToChar(chr);
                    break;
                //These are semi buggy
                case 5155000: //Elf to Human | anyone to Elf    
                    if (chr.getCarta() == 1) {
                        chr.setCarta(0);
                    } else {
                        chr.setCarta(1);
                    }
                    break;
                case 5155004: //Illium to Human | anyone to Illium
                    if (chr.getCarta() == 2) {
                        chr.setCarta(0);
                    } else {
                        chr.setCarta(2);
                    }
                    break;
                case 5155005: //Ark to Human | anyone to Ark
                    if (chr.getCarta() == 3) {
                        chr.setCarta(0);
                    } else {
                        chr.setCarta(3);
                    }
                    break;
                case 5068300: //Random Pet Box
                    int[] pets = new int[]{
                            5002106, 5002107, 5002108, 5000921, 5000922, 5000923, 5002076, 5002077, 5002078, 5002057, 5002058, 5002059, 5000954, 5000955, 5000956, 5002048, 5002049, 5002050, 5002045, 5002046, 5002047,
                            5000960, 5000961, 5000962, 5002066, 5002067, 5002068, 5002030, 5002031, 5002032, 5002040, 5002039, 5002036, 5002037, 5002038, 5002033, 5002034, 5002035, 5002028, 5002029, 5002021, 5002022,
                            5002023, 5002017, 5002011, 5002012, 5002013, 5002003, 5002004, 5002005, 5000997, 5000998, 5000999, 5000979, 5000980, 5000981, 5000977, 5000794, 5000973, 5000972, 5000971, 5000970, 5000964,
                            5000965, 5000966, 5000967, 5000968, 5000969, 5000909, 5000910, 5000911, 5000942, 5000943, 5000944, 5000939, 5000933, 5000934, 5000935, 5000915, 5000916, 5000917, 5000912, 5000913, 5000914,
                            5000906, 5000907, 5000908, 5000900, 5000901, 5000902, 5000846, 5000847, 5000848, 5000836, 5000835, 5000822, 5000823, 5000824, 5000812, 5000813, 5000814, 5000798, 5000793, 5000794, 5000796,
                            5000762, 5000763, 5000764, 5000696, 5000633, 5000634, 5000635, 5000565, 5000566, 5000567, 5000460, 5000461, 5000462, 5000677, 5000678, 5000443, 5000444, 5000445, 5000647, 5000648, 5000649,
                            5000636, 5000637, 5000638, 5000637, 5000638, 5000536, 5000537, 5000538, 5000456, 5000457, 5000458, 5000499, 5000405, 5000406, 5000407, 5000402, 5000403, 5000404, 5000385, 5000386, 5000387,
                            5000368, 5000341, 5000316
                    };
                    Random pet = new Random();
                    int randomIndex = pet.nextInt(pets.length);
                    int getRandomPet = pets[randomIndex];
                    String petName = StringData.getItemStringById(getRandomPet);
                    if (chr.canHold(getRandomPet, 1)) {
                        String worldTxt = (chr.getName() + " just got a cute " + petName + " from the Wisp's Wondrous Wonderberry");
                        chr.getWorld().broadcastPacket(UserLocal.chatMsg(MiracleTime, worldTxt));
                        chr.addItemToInventory(getRandomPet, 1);
                    } else {
                        chr.chatMessage("Make room in your cash Inventory!");
                    }
                    break;
                case 5222058:
                    int[] chairs = new int[]{3018427, 3018425, 3018424, 3018416, 3018390, 3018389, 3018370, 3018369, 3018365, 3018358};
                    Random chair = new Random();
                    int randomcIndex = chair.nextInt(chairs.length);
                    int getRandomChair = chairs[randomcIndex];
                    String chairName = StringData.getItemStringById(getRandomChair);
                    if (chr.canHold(getRandomChair, 1)) {
                        String worldTxt = (chr.getName() + " just got a " + chairName + " from the Chair Box");
                        chr.getWorld().broadcastPacket(UserLocal.chatMsg(MiracleTime, worldTxt));
                        chr.addItemToInventory(getRandomChair, 1);
                    } else {
                        chr.chatMessage("Make room in your SetUp Inventory!");
                    }
                    break;
                case 5062800: // Miracle Circulator
                case 5062801:
                    CharacterPotentialMan cpm = chr.getPotentialMan();
                    if (cpm.getGrade() == 0) {
                        chr.dispose("You cannot use Miracle Circulator in your current state.");
                        return;
                    }
                    byte grade = cpm.getGrade();
                    // update grades
                    if (grade < CharPotGrade.Legendary.ordinal() && Util.succeedProp(GameConstants.BASE_CHAR_POT_UP_RATE)) {
                        grade++;
                    }
                    MiracleCirculatorInfo mci = new MiracleCirculatorInfo(itemID, item.getId());
                    List<CharacterPotential> potentials = CharacterPotentialMan.generateRandomPotential(3, grade, false, null);
                    potentials.sort(Comparator.comparingInt(CharacterPotentialData::getKey));
                    for (CharacterPotential cp : potentials) {
                        mci.getPotentials().add(cp);
                        chr.chatMessage("Key:" + cp.getKey() + " Grade:" + cp.getGrade() + " SkillID:" + cp.getSkillID()
                                + "(" + StringData.getSkillStringById(cp.getSkillID()).getName() + ") SLV:" + cp.getSlv());
                    }
                    chr.setMiracleCirculatorInfo(mci);
                    c.write(WvsContext.miracleCirculatorResult(mci, pos));
                    break;
                case 5570000:
                    inPacket.decodeInt(); // use hammer? useless though
                    ePos = (short) inPacket.decodeInt(); // equip slot
                    invType = ePos < 0 ? EQUIPPED : EQUIP;
                    equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
                    if (equip == null) {
                        chr.write(WvsContext.viciousHammerItemUpgradeResult(192, 1, 0));
                        return;
                    }
                    short maxHammers = ItemConstants.MAX_HAMMER_SLOTS;
                    Equip defaultEquip = ItemData.getEquipById(equip.getItemId());
                    if (defaultEquip.isHasIUCMax()) {
                        maxHammers = defaultEquip.getIUCMax();
                    }
                    if (equip.getIuc() >= maxHammers) {
                        chr.write(WvsContext.viciousHammerItemUpgradeResult(192, 2, 0));
                        chr.getOffenseManager().addOffense(String.format("Character %d tried to use Vicious's Hammer an invalid equip (id %d)",
                                chr.getId(), equip.getItemId()));
                        return;
                    }
                    if (!ItemConstants.canEquipGoldHammer(equip)) {
                        chr.write(WvsContext.viciousHammerItemUpgradeResult(192, 3, 0));
                        chr.getOffenseManager().addOffense(String.format("Character %d tried to use Vicious' Hammer on an invalid equip (id %d)",
                                chr.getId(), equip.getItemId()));
                        return;
                    }
                    equip.addStat(iuc, 1); // +1 hammer used
                    equip.addStat(tuc, 1); // +1 upgrades available
                    equip.updateToChar(chr);
                    chr.write(WvsContext.viciousHammerItemUpgradeResult(190, 0, equip.getIuc()));
                    break;
                case 5050100: // AP Reset Scroll
                    handleUserResetAP(chr, null);
                    break;
                case 5051001: // SP Reset Scroll
                    handleUserResetSP(chr, null);
                    break;
                case 5064000: // Shielding Ward (reg 12 stars)
                case 5064003: // Superior Shielding Ward (reg 7 stars)
                case 5064100: // Shield Scroll
                case 5064300: // Guardian Scroll
                case 5064400: // Return Scroll
                case 5068100: // Pet Shield Scroll
                    ePos = inPacket.decodeShort();
                    inPacket.decodeByte();
                    invType = ePos < 0 ? EQUIPPED : EQUIP;
                    equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
                    if (equip == null) {
                        chr.dispose("Could not find equip.");
                        return;
                    } else if (itemID == 5064000 && equip.getChuc() >= 12 // TODO: superior item check
                            || itemID == 5064003 && equip.getChuc() >= 7
                            || itemID == 5068100 && equip.getItemId() / 10000 != EquipPrefix.PetWear.getVal()) {
                        chr.dispose("You cannot use this scroll on this item.");
                        return;
                    }
                    switch (itemID % 10000 / 100) {
                        case 40 -> equip.addAttribute(EquipAttribute.ProtectionScroll);
                        case 41, 81 -> equip.addAttribute(EquipAttribute.UpgradeCountProtection);
                        case 43 -> equip.addAttribute(EquipAttribute.ScrollProtection);
                        case 44 -> equip.addAttribute(EquipAttribute.ReturnScroll);
                        // TODO: add actual effects on server side
                    }
                    chr.chatMessage("The scroll effect is not implemented yet.");
                    equip.updateToChar(chr);
                    break;
                case 5553000:
                    if (album.getHairSlot() < 50) {
                        album.setHairSlot(album.getHairSlot() + 1);
                        chr.write(UserLocal.beautyAlbumActions((byte) 0x5, 30000, album.getHairSlot(), album));
                    }
                    break;
                case 5552000:
                    if (album.getFaceSlot() < 50) {
                        album.setFaceSlot(album.getFaceSlot() + 1);
                        chr.write(UserLocal.beautyAlbumActions((byte) 0x5, 20000, album.getFaceSlot(), album));
                    }
                    break;
                default:
                    chr.chatMessage(Mob, String.format("Cash item %d is not implemented, notify Taiga pls.", itemID));
                    return;
            }
        }
        if (itemID != 5040004 && itemID / 10000 != 545) { // TP Rocks and Portable Stores / Storages
            chr.consumeItem(item);
        }
        chr.dispose();
    }


    @Handler(op = InHeader.USER_ARCANE_SYMBOL_MERGE_REQUEST)
    public static void handleUserArcaneSymbolMergeRequest(Char chr, InPacket inPacket) {
        int type = inPacket.decodeInt();
        int fromPos = inPacket.decodeInt();
        switch (type) {
            case 0:
                // merge
                Equip fromEq = (Equip) chr.getEquipInventory().getItemBySlot(fromPos);
                Equip toEq = (Equip) chr.getEquippedInventory().getItemByItemID(fromEq == null ? 0 : fromEq.getItemId());
                if (fromEq == null || toEq == null || fromEq.getItemId() != toEq.getItemId() || !ItemConstants.isArcaneSymbol(toEq.getItemId())) {
                    chr.chatMessage("Could not find one of the symbols.");
                    return;
                }
                if (toEq.getSymbolExp() >= ItemConstants.getRequiredSymbolExp(toEq.getSymbolLevel())) {
                    chr.chatMessage("Your symbol already is at max exp.");
                    return;
                }
                chr.consumeItem(fromEq);
                toEq.setSymbolExp(Math.min(toEq.getSymbolExp() + fromEq.getSymbolExp(), ItemConstants.getRequiredSymbolExp(toEq.getSymbolLevel())));
                toEq.updateToChar(chr);
                break;
            case 1:
                // enhance
                Equip symbol = (Equip) chr.getEquippedInventory().getItemBySlot(fromPos);
                int reqSymbolExp = ItemConstants.getRequiredSymbolExp(symbol.getLevel());
                if (symbol == null || !ItemConstants.isArcaneSymbol(symbol.getItemId())
                        || symbol.getSymbolLevel() >= ItemConstants.MAX_ARCANE_SYMBOL_LEVEL
                        || symbol.getSymbolExp() < reqSymbolExp) {
                    chr.chatMessage("Could not find symbol.");
                    return;
                }
                long cost = ItemConstants.getSymbolMoneyReqByLevel(symbol.getSymbolLevel());
                if (cost > chr.getMoney()) {
                    chr.chatMessage("You do not have enough mesos to level up your symbol.");
                    return;
                }
                chr.deductMoney(cost);
                symbol.setSymbolLevel((short) (symbol.getSymbolLevel() + 1));
                symbol.addSymbolExp(-reqSymbolExp);
                symbol.initSymbolStats(symbol.getSymbolLevel(), chr.getJob());
                symbol.updateToChar(chr);
                chr.recalcStats(symbol.getBaseStatFlag());
                break;
            case 2:
                // mass merge
                int itemId = fromPos;
                toEq = (Equip) chr.getEquippedInventory().getItemByItemID(itemId);
                if (!(toEq instanceof Equip) || !ItemConstants.isArcaneSymbol(itemId)) {
                    chr.chatMessage("Could not find an arcane symbol to transfer to.");
                    return;
                }
                if (toEq.hasSymbolExpForLevelUp()) {
                    chr.chatMessage("First level your symbol before trying to add more symbols onto it.");
                    return;
                }
                Set<Equip> matchingSymbols = new HashSet<>();
                for (Item item : chr.getEquipInventory().getItems()) {
                    if (item.getItemId() == toEq.getItemId()) {
                        matchingSymbols.add((Equip) item);
                    }
                }
                for (Equip eqSymbol : matchingSymbols) {
                    chr.consumeItem(eqSymbol);
                    toEq.addSymbolExp(eqSymbol.getTotalSymbolExp());
                    if (toEq.hasSymbolExpForLevelUp()) {
                        break;
                    }
                }
                toEq.updateToChar(chr);
                break;
        }
    }


    @Handler(op = InHeader.USER_STAT_CHANGE_ITEM_USE_REQUEST)
    public static void handleUserStatChangeItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        if ((field.getFieldLimit() & FieldOption.StatChangeItemConsumeLimit.getVal()) > 0) {
            chr.dispose();
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        c.verifyTick(inPacket);
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        if (item == null || item.getItemId() != itemID || !chr.isInValidState()) {
            chr.dispose();
            return;
        }
        chr.useStatChangeItem(item, true);
        if (field.getConsumeItemCoolTime() > 0) {
            chr.write(UserLocal.consumeItemCooltime());
        }
    }


    @Handler(op = InHeader.USER_SCRIPT_ITEM_USE_REQUEST)
    public static void handleUserScriptItemUseRequest(Client c, InPacket inPacket) {
        c.verifyTick(inPacket);
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        int quant = inPacket.decodeInt();
        Char chr = c.getChr();
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        if (item == null || item.getItemId() != itemID) {
            item = chr.getCashInventory().getItemBySlot(slot);
        }
        if (item == null || item.getItemId() != itemID || quant < 0 || !chr.isInValidState()) {
            chr.dispose();
            return;
        }
        String script = String.valueOf(itemID);
        ItemInfo ii = ItemData.getItemInfoByID(itemID);
        if (ii.getScript() != null && !"".equals(ii.getScript())) {
            script = ii.getScript();
        }
        if (item.getItemId() == ItemConstants.GAGAGUCCI) {
            int qid = QuestConstants.PVAC_DATA;
            if (chr.getRecordFromQuestEx(qid, "vac") == 0) {
                chr.setQuestRecordEx(qid, "vac", 1);
                chr.chatMessage("Pvac has been enabled");
            } else {
                chr.setQuestRecordEx(qid, "vac", 0);
                chr.chatMessage("Pvac has been disabled");
            }
            chr.dispose();
            return;
        }

        chr.getScriptManager().startScript(itemID, script, ScriptType.Item);
        chr.dispose();
    }


    @Handler(op = InHeader.USER_EQUIPMENT_ENCHANT_WITH_SINGLE_UI_REQUEST)
    public static void handleUserEquipmentEnchantWithSingleUIRequest(Client c, InPacket inPacket) {
        byte equipmentEnchantType = inPacket.decodeByte();

        Char chr = c.getChr();
        EquipmentEnchantType eeType = EquipmentEnchantType.getByVal(equipmentEnchantType);

        if (eeType == null) {
            log.error(String.format("Unknown enchant UI request %d", equipmentEnchantType));
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }

        switch (eeType) {
            case ScrollUpgradeRequest:
                c.verifyTick(inPacket);
                short pos = inPacket.decodeShort();
                int scrollID = inPacket.decodeInt();
                Inventory inv = pos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
                pos = (short) Math.abs(pos);
                Equip equip = (Equip) inv.getItemBySlot(pos);
                if (equip == null || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
                    chr.getOffenseManager().addOffense(String.format("Character %d tried to enchant a non-scrollable equip (pos %d, itemid %d).",
                            chr.getId(), pos, equip == null ? 0 : equip.getItemId()));
                    chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                    return;
                }
                Equip prevEquip = equip.deepCopy();
                List<ScrollUpgradeInfo> suis = ItemConstants.getScrollUpgradeInfosByEquip(equip);
                if (scrollID < 0 || scrollID >= suis.size()) {
                    chr.getOffenseManager().addOffense(String.format("Characer %d tried to spell trace scroll with an invalid scoll ID (%d, "
                            + "itemID %d).", chr.getId(), scrollID, equip.getItemId()));
                    chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                    return;
                }
                ScrollUpgradeInfo sui = suis.get(scrollID);
                if (equip.getBaseStat(tuc) <= 0 && sui.getType() == SpellTraceScrollType.Normal) {
                    chr.getOffenseManager().addOffense(String.format("Character %d tried to enchant a non-scrollable equip (pos %d, itemid %d).",
                            chr.getId(), pos, equip.getItemId()));
                    chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                    return;
                }
                chr.consumeItem(ItemConstants.SPELL_TRACE_ID, sui.getCost());
                boolean success = sui.applyTo(equip);
                equip.recalcEnchantmentStats();
                String desc = success ? "Your item has been upgraded." : "Your upgrade has failed.";
                chr.write(FieldPacket.showScrollUpgradeResult(false, success ? 1 : 0, desc, prevEquip, equip));
                equip.updateToChar(chr);
                if (pos < 0) {
                    chr.recalcStats(equip.getBaseStatFlag());
                }
                suis = ItemConstants.getScrollUpgradeInfosByEquip(equip);
                c.write(FieldPacket.scrollUpgradeDisplay(false, suis));
                break;
            case HyperUpgradeResult:
                c.verifyTick(inPacket);
                int eqpPos = inPacket.decodeShort();
                boolean extraChanceFromMiniGame = inPacket.decodeByte() != 0;
                equip = (Equip) chr.getEquipInventory().getItemBySlot(eqpPos);
                if (extraChanceFromMiniGame) {
                    inPacket.decodeInt();
                }
                inPacket.decodeInt();
                inPacket.decodeInt();
                boolean safeGuard = inPacket.decodeByte() != 0;
                boolean equippedInv = eqpPos < 0;
                inv = equippedInv ? chr.getEquippedInventory() : chr.getEquipInventory();
                equip = (Equip) inv.getItemBySlot(Math.abs(eqpPos));
                if (equip == null) {
                    chr.chatMessage("Could not find the given equip.");
                    chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                    return;
                }
                if (!ItemConstants.isUpgradable(equip.getItemId())
                        || (equip.getBaseStat(tuc) != 0 && !c.getWorld().isReboot())
                        || chr.getEquipInventory().getEmptySlots() == 0
                        || equip.getChuc() >= GameConstants.getMaxStars(equip)
                        || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
                    chr.chatMessage("Equipment cannot be enhanced.");
                    chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                    return;
                }
                long cost = GameConstants.getEnchantmentMesoCost(equip.getrLevel() + equip.getiIncReq(), equip.getChuc(), equip.isSuperiorEqp());
                if (chr.getMoney() < cost) {
                    chr.chatMessage("Mesos required: " + NumberFormat.getNumberInstance(Locale.US).format(cost));
                    chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                    return;
                }
                Equip oldEquip = equip.deepCopy();
                int successProp = GameConstants.getEnchantmentSuccessRate(equip);
                if (extraChanceFromMiniGame) {
                    successProp *= 1.045;
                }
                int destroyProp = safeGuard && equip.canSafeguardHyperUpgrade() ? 0 : GameConstants.getEnchantmentDestroyRate(equip);
                if (equippedInv && destroyProp > 0 && chr.getEquipInventory().getEmptySlots() == 0) {
                    c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("You do not have enough space in your "
                            + "equip inventory in case your item gets destroyed.")));
                    return;
                }
                success = Util.succeedProp(successProp, 1000);
                boolean boom = false;
                boolean canDegrade = equip.isSuperiorEqp() ? equip.getChuc() > 0 : equip.getChuc() > 5 && equip.getChuc() % 5 != 0;
                if (success) {
                    equip.setChuc((short) (equip.getChuc() + 1));
                    equip.setDropStreak(0);
                } else if (Util.succeedProp(destroyProp, 1000)) {
                    equip.setChuc((short) 0);
                    equip.makeVestige();
                    boom = true;
                    if (equippedInv) {
                        chr.unequip(equip);
                        equip.setBagIndex(chr.getEquipInventory().getFirstOpenSlot());
                        equip.updateToChar(chr);
                        c.write(WvsContext.inventoryOperation(true, false, Move, (short) eqpPos, (short) equip.getBagIndex(), 0, equip));
                    }
                    if (!equip.isSuperiorEqp()) {
                        equip.setChuc((short) Math.min(12, equip.getChuc()));
                    } else {
                        equip.setChuc((short) 0);
                    }
                } else if (canDegrade) {
                    equip.setChuc((short) (equip.getChuc() - 1));
                    equip.setDropStreak(equip.getDropStreak() + 1);
                }
                chr.deductMoney(cost);
                equip.recalcEnchantmentStats();
                oldEquip.recalcEnchantmentStats();
                equip.updateToChar(chr);
                if (equippedInv) {
                    chr.recalcStats(equip.getBaseStatFlag());
                }
                c.write(FieldPacket.showUpgradeResult(oldEquip, equip, success, boom, canDegrade));
                chr.dispose();
                break;
            case TransmissionResult:
                c.verifyTick(inPacket);
                short toPos = inPacket.decodeShort();
                short fromPos = inPacket.decodeShort();
                Equip fromEq = (Equip) chr.getEquipInventory().getItemBySlot(fromPos);
                Equip toEq = (Equip) chr.getEquipInventory().getItemBySlot(toPos);
                if (fromEq == null || toEq == null || fromEq.getItemId() != toEq.getItemId()
                        || !fromEq.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
                    log.error(String.format("Equip transmission failed: from = %s, to = %s", fromEq, toEq));
                    c.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                    return;
                }
                fromEq.removeVestige();
                fromEq.setChuc((short) 0);
                chr.consumeItem(toEq);
                fromEq.updateToChar(chr);
                c.write(FieldPacket.showTranmissionResult(fromEq, toEq));
                break;
            case ScrollUpgradeDisplay:
                int ePos = inPacket.decodeInt();
                inv = ePos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
                ePos = Math.abs(ePos);
                equip = (Equip) inv.getItemBySlot(ePos);
                if (c.getWorld().isReboot()) {
                    chr.getOffenseManager().addOffense(String.format("Character %d attempted to scroll in reboot world (pos %d, itemid %d).",
                            chr.getId(), ePos, equip == null ? 0 : equip.getItemId()));
                    chr.dispose();
                    return;
                }
                if (equip == null || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige) || !ItemConstants.isUpgradable(equip.getItemId())) {
                    chr.getOffenseManager().addOffense(String.format("Character %d tried to scroll a non-scrollable equip (pos %d, itemid %d).",
                            chr.getId(), ePos, equip == null ? 0 : equip.getItemId()));
                    chr.dispose();
                    return;
                }
                suis = ItemConstants.getScrollUpgradeInfosByEquip(equip);
                c.write(FieldPacket.scrollUpgradeDisplay(false, suis));
                break;
            /*case ScrollTimerEffective:
             break;*/
            case HyperUpgradeDisplay:
                ePos = inPacket.decodeInt();
                safeGuard = inPacket.decodeByte() != 0;
                inv = ePos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
                ePos = Math.abs(ePos);
                equip = (Equip) inv.getItemBySlot(ePos);
                if (equip == null || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige) || !ItemConstants.isUpgradable(equip.getItemId())) {
                    chr.getOffenseManager().addOffense(String.format("Character %d tried to enchant a non-enchantable equip (pos %d, itemid %d).",
                            chr.getId(), ePos, equip == null ? 0 : equip.getItemId()));
                    chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                    return;
                }
                cost = GameConstants.getEnchantmentMesoCost(equip.getrLevel() + equip.getiIncReq(), equip.getChuc(), equip.isSuperiorEqp());
                destroyProp = GameConstants.getEnchantmentDestroyRate(equip);
                if (safeGuard && equip.canSafeguardHyperUpgrade()) {
                    cost *= 2;
                }
                c.write(FieldPacket.hyperUpgradeDisplay(equip, equip.isSuperiorEqp() ? equip.getChuc() > 0 : equip.getChuc() > 5 && equip.getChuc() % 5 != 0,
                        cost, 0, 0, GameConstants.getEnchantmentSuccessRate(equip), 0,
                        destroyProp, 0, equip.getDropStreak() >= 2));
                break;
            case MiniGameDisplay:
                c.write(FieldPacket.miniGameDisplay(eeType));
                break;
            //case ShowScrollUpgradeResult:
            case ScrollTimerEffective:
            case ShowHyperUpgradeResult:
                break;
            /*
             case ShowScrollVestigeCompensationResult:
             case ShowTransmissionResult:
             case ShowUnknownFailResult:
             break;*/
            default:
                log.debug("Unhandled Equipment Enchant Type: " + eeType);
                chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                break;
        }
    }

    @Handler(op = InHeader.USER_SKILL_LEARN_ITEM_USE_REQUEST)
    public static void handleUserLearnItemUseRequest(Client c, InPacket inPacket) {
        c.verifyTick(inPacket);
        short pos = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Char chr = c.getChr();

        ItemInfo ii = ItemData.getItemInfoByID(itemID);
        Item item = chr.getConsumeInventory().getItemBySlot(pos);

        if (ii == null || !chr.hasItem(itemID) || item == null || item.getItemId() != itemID || !chr.isInValidState()) {
            chr.chatMessage("Could not find that item.");
            return;
        }

        int masterLevel = ii.getMasterLv();
        int reqSkillLv = ii.getReqSkillLv();
        int skillid = 0;
        Map<ScrollStat, Integer> vals = ii.getScrollStats();
        int chance = vals.getOrDefault(ScrollStat.success, 100);

        for (int skill : ii.getSkills()) {
            if (chr.hasSkill(skill)) {
                skillid = skill;
                break;
            }
        }
        Skill skill = chr.getSkill(skillid);
        if (skill == null) {
            chr.chatMessage(Notice2, "An error has occured. Mastery Book ID: " + itemID + ",  skill ID: " + skillid + ".");
            chr.dispose();
            return;
        }
        if (skillid == 0 || (skill.getMasterLevel() >= masterLevel) || skill.getCurrentLevel() < reqSkillLv) {
            chr.chatMessage(SystemNotice, "You cannot use this Mastery Book.");
            chr.dispose();
            return;
        }

        if (skill.getCurrentLevel() > reqSkillLv && skill.getMasterLevel() < masterLevel) {
            chr.chatMessage(Mob, "Success Chance: " + chance + "%.");
            chr.consumeItem(itemID, 1);
            if (Util.succeedProp(chance)) {
                skill.setMasterLevel(masterLevel);
                chr.addSkill(skill);
                chr.write(WvsContext.changeSkillRecordResult(skill));
                chr.chatMessage(Notice2, "[Mastery Book] Item id: " + itemID + "  set Skill id: " + skillid + "'s Master Level to: " + masterLevel + ".");
            } else {
                chr.chatMessage(Notice2, "[Mastery Book] Item id: " + itemID + " was used, however it was unsuccessful.");
            }
        }
        chr.dispose();
    }


    @Handler(op = InHeader.SOCKET_CREATE_REQUEST)
    public static void handleSocketCreateRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        c.verifyTick(inPacket);
        short uPos = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        short ePos = inPacket.decodeShort();
        Item item = chr.getConsumeInventory().getItemBySlot(uPos);
        Equip equip = (Equip) chr.getEquipInventory().getItemBySlot(ePos);
        if (equip == null || item == null || item.getItemId() != itemID) {
            log.error("Unknown equip or mismatching use items.");
            return;
        }
        int success = 0;
        if (equip.getSocket(0) == ItemConstants.INACTIVE_SOCKET && ItemConstants.canEquipHavePotential(equip)) {
            chr.consumeItem(item);
            equip.setSocket(0, ItemConstants.EMPTY_SOCKET_ID);
        } else {
            success = 1;
        }
        c.write(FieldPacket.socketCreateResult(success));
        equip.updateToChar(chr);
    }

    @Handler(op = InHeader.SOCKET_CREATE_RESULT)
    public static void handleSocketCreateResult(Client c, InPacket inPacket) {
        c.write(FieldPacket.socketCreateResult(inPacket.decodeInt() + 2));
    }

    @Handler(op = InHeader.NEBULITE_INSERT_REQUEST)
    public static void handleNebuliteInsertRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        c.verifyTick(inPacket);
        short nebPos = inPacket.decodeShort();
        int nebID = inPacket.decodeInt();
        Item item = chr.getInstallInventory().getItemBySlot(nebPos);
        short ePos = inPacket.decodeShort();
        Equip equip = (Equip) chr.getEquipInventory().getItemBySlot(ePos);
        if (item == null || equip == null || item.getItemId() != nebID || !ItemConstants.isNebulite(item.getItemId())) {
            log.error("Nebulite or equip was not found when inserting.");
            chr.dispose();
            return;
        }
        if (equip.getSocket(0) != ItemConstants.EMPTY_SOCKET_ID) {
            log.error("Tried to Nebulite an item without an empty socket.");
            chr.chatMessage("You can only insert a Nebulite into empty socket slots.");
            chr.dispose();
            return;
        }
        if (!ItemConstants.nebuliteFitsEquip(equip, item)) {
            chr.getOffenseManager().addOffense(String.format("Character %d attempted to use a nebulite (%d) that doesn't fit an equip (%d).", chr.getId(), item.getItemId(), equip.getItemId()));
            chr.chatMessage("The nebulite cannot be mounted on this equip.");
            chr.dispose();
            return;
        }
        chr.consumeItem(item);
        equip.setSocket(0, nebID % ItemConstants.NEBULITE_BASE_ID);
        equip.updateToChar(chr);
    }

    @Handler(op = InHeader.USER_ITEM_SKILL_SOCKET_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserItemSkillSocketUpdateItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        c.verifyTick(inPacket);
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        Item item = chr.getConsumeInventory().getItemBySlot(uPos);
        Equip equip = (Equip) chr.getEquipInventory().getItemBySlot(ePos);
        if (item == null || equip == null || !ItemConstants.isWeapon(equip.getItemId())
                || !ItemConstants.isSoulEnchanter(item.getItemId()) || equip.getrLevel() + equip.getiIncReq() < ItemConstants.MIN_LEVEL_FOR_SOUL_SOCKET) {
            chr.dispose();
            return;
        }
        int successProp = ItemData.getItemInfoByID(item.getItemId()).getScrollStats().get(ScrollStat.success);
        boolean success = Util.succeedProp(successProp);
        if (success) {
            equip.setSoulSocketId((short) (item.getItemId() % ItemConstants.SOUL_ENCHANTER_BASE_ID));
            equip.updateToChar(chr);
        }
        chr.getField().broadcastPacket(UserPacket.showItemSkillSocketUpgradeEffect(chr.getId(), success));
        chr.consumeItem(item);
    }

    @Handler(op = InHeader.USER_ITEM_SKILL_OPTION_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserItemSkillOptionUpdateItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        c.verifyTick(inPacket);
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        Item item = chr.getConsumeInventory().getItemBySlot(uPos);
        Equip equip = (Equip) chr.getEquipInventory().getItemBySlot(ePos);
        if (item == null || equip == null || !ItemConstants.isWeapon(equip.getItemId())
                || !ItemConstants.isSoul(item.getItemId()) || equip.getSoulSocketId() == 0) {
            chr.dispose();
            return;
        }
        equip.setSoulOptionId((short) (1 + item.getItemId() % ItemConstants.SOUL_ITEM_BASE_ID));
        short option = ItemConstants.getSoulOptionFromSoul(item.getItemId()); //Currently does nothing
        if (option == 0) {
            option = (short) ItemConstants.getRandomSoulOption();
        }
        equip.setSoulOption(option);
        equip.updateToChar(chr);
        chr.consumeItem(item);
        chr.getField().broadcastPacket(UserPacket.showItemSkillOptionUpgradeEffect(chr.getId(), true, false, ePos, uPos));
    }

    @Handler(op = InHeader.USER_WEAPON_TEMP_ITEM_OPTION_REQUEST)
    public static void handleUserWeaponTempItemOptionRequest(Char chr, InPacket inPacket) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(CharacterTemporaryStat.SoulMP)
                && tsm.getOption(CharacterTemporaryStat.SoulMP).nOption >= ItemConstants.MAX_SOUL_CAPACITY) {
            Option o = new Option();
            o.nOption = tsm.getOption(CharacterTemporaryStat.SoulMP).nOption;
            o.xOption = tsm.getOption(CharacterTemporaryStat.SoulMP).xOption;
            o.rOption = ItemConstants.getSoulSkillFromSoulID(
                    ((Equip) chr.getEquippedItemByBodyPart(BodyPart.Weapon)).getSoulOptionId()
            );
            tsm.putCharacterStatValue(CharacterTemporaryStat.FullSoulMP, o);
            tsm.sendSetStatPacket();
        }
        chr.dispose();
    }

    @Handler(op = InHeader.USER_PROTECT_BUFF_DIE_ITEM_REQUEST)
    public static void handleUserProtectBuffDieItemRequest(Char chr, InPacket inPacket) {
        chr.getClient().verifyTick(inPacket);
        boolean used = inPacket.decodeByte() != 0;
        if (used) {
            // grabs the first one from the list of buffItems
            Item buffProtector = chr.getBuffProtectorItem();
            if (buffProtector != null) {
                chr.setBuffProtector(true);
                chr.consumeItem(buffProtector);
                chr.write(UserLocal.setBuffProtector(buffProtector.getItemId(), true));
            } else {
                chr.getOffenseManager().addOffense(String.format("Character id %d tried to use a buff without having the appropriate item.", chr.getId()));
            }
        }
    }

    @Handler(op = InHeader.USER_DEFAULT_WING_ITEM)
    public static void handleUserDefaultWingItem(Char chr, InPacket inPacket) {
        int wingItem = inPacket.decodeInt();
        if (wingItem == 5010093) { // AB
            chr.getAvatarData().getCharacterStat().setWingItem(wingItem);
            chr.getField().broadcastPacket(UserRemote.setDefaultWingItem(chr));
        }
    }

    @Handler(op = InHeader.USER_RECIPE_OPEN_ITEM_USE_REQUEST)
    public static void handleUserRecipeOpenItemUseRequest(Char chr, InPacket inPacket) {
        chr.getClient().verifyTick(inPacket);
        short pos = inPacket.decodeShort();// // nPOS
        int itemID = inPacket.decodeInt();// nItemID

        Item item = chr.getInventoryByType(CONSUME).getItemBySlot(pos);
        if (item == null || item.getItemId() != itemID || !chr.isInValidState()) {
            chr.dispose();
            return;
        }
        if (chr != null && chr.getHP() > 0 && ItemConstants.isRecipeOpenItem(itemID)) {
            ItemInfo recipe = ItemData.getItemInfoByID(itemID);
            if (recipe != null) {
                int recipeID = recipe.getSpecStats().getOrDefault(SpecStat.recipe, 0);
                int reqSkillLevel = recipe.getSpecStats().getOrDefault(SpecStat.reqSkillLevel, 0);
                MakingSkillRecipe msr = SkillData.getRecipeById(recipeID);
                if (msr != null && msr.isNeedOpenItem()) {
                    if (chr.getSkillLevel(msr.getReqSkillID()) < reqSkillLevel || chr.getSkillLevel(recipeID) > 0) {
                        return;
                    }
                    chr.addSkill(recipeID, 1, 1);
                }
            }
        }
    }

    @Handler(op = InHeader.USER_ACTIVATE_NICK_ITEM)
    public static void handleUserActivateNickItem(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int nickItem = inPacket.decodeInt();
        if (nickItem == 0 || nickItem / 10000 == 370 && chr.hasItem(nickItem)) {
            chr.setActiveNickItemID(nickItem);
            chr.getField().broadcastPacket(UserRemote.setActiveNickItem(chr, null), chr);
        }
    }

    @Handler(op = InHeader.USER_CHAR_SLOT_INC_ITEM_USE_REQUEST)
    public static void addChrSlot(Client c, InPacket inPacket) {
        Account user = c.getAccount();
        int currentSlots = user.getCharacterSlots();
        user.setCharacterSlots(currentSlots + 1);
    }

    @Handler(op = InHeader.USER_LOTTERY_ITEM_USE_REQUEST)
    public static void handleUserLotteryItemUseRequest(Char chr, InPacket inPacket) {
        short pos = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        inPacket.decodeByte(); // bSendForUI
        inPacket.decodeByte(); // bLogStart
        inPacket.decodeInt(); // 1
        Item item;
        ItemInfo itemInfo;
        if (itemID / 1000 != 2028 || (item = chr.getConsumeInventory().getItemBySlot(pos)) == null
                || itemID != item.getItemId() || ((itemInfo = ItemData.getItemInfoByID(itemID)) == null)) {
            chr.dispose("Not found.");
            return;
        }
        Object reward = itemInfo.getRandomReward();
        if (reward instanceof Item) {
            chr.addItemToInventory((Item) reward);
        } else if (reward instanceof Integer && (int) reward != 0) {
            chr.addMoney((int) reward);
        } else {
            chr.chatMessage("Oh, you're unlucky."); // should not happen
        }
        chr.consumeItem(item);
        chr.dispose();
    }
}
