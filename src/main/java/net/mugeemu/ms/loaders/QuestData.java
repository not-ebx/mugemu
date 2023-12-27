package net.mugeemu.ms.loaders;

import net.mugeemu.ms.client.character.quest.progress.*;
import net.mugeemu.ms.client.character.quest.requirement.*;
import net.mugeemu.ms.client.character.quest.reward.*;
import net.mugeemu.ms.constants.ItemConstants;
import net.mugeemu.ms.loaders.containerclasses.ItemInfo;
import net.mugeemu.ms.loaders.containerclasses.QuestInfo;
import net.mugeemu.ms.client.character.quest.Quest;
import net.mugeemu.ms.client.character.quest.progress.*;
import net.mugeemu.ms.client.character.quest.requirement.*;
import net.mugeemu.ms.client.character.quest.reward.*;
import net.mugeemu.ms.life.mob.Mob;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.enums.InvType;
import net.mugeemu.ms.enums.QuestStatus;
import net.mugeemu.ms.enums.Stat;
import org.apache.log4j.LogManager;
import org.w3c.dom.Node;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.util.XMLApi;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 3/2/2018.
 */
public class QuestData {
    private static Set<QuestInfo> baseQuests = new HashSet<>();
    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();
    private static final boolean LOG_UNKS = false;

    public static void loadQuestsFromWZ() throws IOException {
        String questNxDir = ServerConstants.NX_DIR + "/Quest.nx";
        NXNode checkNode = new LazyNXFile(questNxDir).resolve("Check.img");

        for (NXNode questIDNode : checkNode){
            QuestInfo quest = new QuestInfo();
            int questID = Integer.parseInt(questIDNode.getName());
            quest.setQuestID(questID);
            for (NXNode statusNode : questIDNode) {
                byte status = Byte.parseByte(statusNode.getName());
                for (NXNode infoNode: statusNode) {
                    String name = infoNode.getName();
                    String value = "";
                    if(infoNode.get() != null){
                        value = infoNode.get().toString().trim();
                    }
                    switch (name) {
                        case "npc":
                            if (status == 0) {
                                quest.setStartNpc(Integer.parseInt(value));
                            } else {
                                quest.setEndNpc(Integer.parseInt(value));
                            }
                            break;
                        case "infoNumber":
                            quest.setInfoNumber(Integer.parseInt(value));
                            break;
                        case "fieldsetkeeptime":
                            quest.setFieldSetKeepTime(Integer.parseInt(value));
                            break;
                        case "subJobFlags":
                            quest.setSubJobFlags(Integer.parseInt(value));
                            break;
                        case "deathCount":
                            quest.setDeathCount(Integer.parseInt(value));
                            break;
                        case "mobDropMeso":
                            quest.setMobDropMeso(Integer.parseInt(value));
                            break;
                        case "morph":
                            quest.setMorph(Integer.parseInt(value));
                            break;
                        case "start":
                            quest.setStart(Long.parseLong(value));
                            break;
                        case "start_t":
                            quest.setStartT(Long.parseLong(value));
                            break;
                        case "end":
                            quest.setEnd(Long.parseLong(value));
                            break;
                        case "end_t":
                            quest.setEndT(Long.parseLong(value));
                            break;
                        case "startscript":
                            quest.setStartScript(value);
                            break;
                        case "endscript":
                            quest.setEndScript(value);
                            break;
                        case "fieldset":
                            quest.setFieldSet(value);
                            break;
                        case "normalAutoStart":
                            quest.setNormalAutoStart(Integer.parseInt(value) != 0);
                            break;
                        case "completeNpcAutoGuide":
                            quest.setCompleteNpcAutoGuide(Integer.parseInt(value) != 0);
                            break;
                        case "autoStart":
                            quest.setAutoStart(Integer.parseInt(value) != 0);
                            break;
                        case "scenarioQuest":
                            quest.setAutoStart(Integer.parseInt(value) != 0);
                            break;
                        case "secret":
                            quest.setSecret(Integer.parseInt(value) != 0);
                            break;
                        case "marriaged":
                            quest.addRequirement(new QuestStartMarriageRequirement());
                            break;
                        case "lvmin":
                            quest.addRequirement(new QuestStartMinStatRequirement(Stat.level, Short.parseShort(value)));
                            break;
                        case "pop":
                        case "fameGradeReq":
                            quest.addRequirement(new QuestStartMinStatRequirement(Stat.pop, Short.parseShort(value)));
                            break;
                        case "charismaMin":
                            quest.addRequirement(new QuestStartMinStatRequirement(Stat.charismaEXP, Short.parseShort(value)));
                            break;
                        case "insightMin":
                            quest.addRequirement(new QuestStartMinStatRequirement(Stat.insightEXP, Short.parseShort(value)));
                            break;
                        case "willMin":
                            quest.addRequirement(new QuestStartMinStatRequirement(Stat.willEXP, Short.parseShort(value)));
                            break;
                        case "craftMin":
                            quest.addRequirement(new QuestStartMinStatRequirement(Stat.craftEXP, Short.parseShort(value)));
                            break;
                        case "senseMin":
                            quest.addRequirement(new QuestStartMinStatRequirement(Stat.senseEXP, Short.parseShort(value)));
                            break;
                        case "charm":
                        case "charmMin":
                            quest.addRequirement(new QuestStartMinStatRequirement(Stat.charmEXP, Short.parseShort(value)));
                            break;
                        case "level":
                            quest.addProgressRequirement(new QuestProgressLevelRequirement(Short.parseShort(value)));
                            break;
                        case "lvmax":
                            quest.addRequirement(new QuestStartMaxLevelRequirement(Short.parseShort(value)));
                            break;
                        case "endmeso":
                            quest.addProgressRequirement(new QuestProgressMoneyRequirement(Integer.parseInt(value)));
                            break;
                        case "order":
                        case "notInTeleportItemLimitedField":
                        case "anotherUserORCheck":
                        case "damageOnFalling":
                        case "hpR":
                        case "dayByDay":
                        case "QuestRecordAndOption":
                        case "infoex":
                        case "equipAllNeed":
                        case "interval":
                        case "interval_t":
                        case "dayOfWeek":
                        case "QuestOrOption":
                        case "ItemOrOption":
                        case "dayN":
                        case "anotherUserCheckType":
                        case "anotherUserCheck":
                        case "userInteract":
                        case "petRecallLimit":
                        case "pettamenessmin":
                        case "dayN_t":
                        case "worldmin":
                        case "worldmax":
                        case "petAutoSpeakingLimit":
                        case "name":
                        case "multiKill":
                        case "comboKill":
                        case "job_JP":
                        case "job_TW":
                        case "dayByDay_t":
                        case "runeAct":
                        case "weeklyRepeatResetDayOfWeek":
                        case "weeklyRepeat":
                        case "dressChanged":
                        case "equipSelectNeed":
                        case "infoAccount":
                        case "infoAccountExt":
                        case "breakTimeField":
                        case "multiKillCount":
                        case "randomGroupList":
                        case "randomGroup":
                        case "mbmin":
                        case "duo":
                        case "duoAssistPoint":
                        case "wsrInfo":
                        case "premium":
                        case "dayOfWeek_t":
                        case "nxInfo":
                        case "episodeQuest":
                        case "pvpGrade":
                        case "vipStartGradeMin":
                        case "vipStartGradeMax":
                        case "vipStartAccount":
                        case "dailyCommitment":
                        case "purchasePeriodAbove":
                        case "charisma": // Maybe implement later
                        case "craft": // Maybe implement later
                        case "gender": // it's 2018, so equal opportunity
                        case "buff": // Maybe implement later
                        case "exceptbuff": // Maybe implement later
                            break;
                        case "quest":
                            for (NXNode idNode : infoNode) {
                                QuestStartCompletionRequirement qcr = new QuestStartCompletionRequirement();
                                for (NXNode questNode : idNode) {
                                    String questName = questNode.getName();
                                    String questValue = questNode.get().toString();
                                    if (questNode.getChild("status") == null) {
                                        qcr.setQuestStatus((byte) -1);
                                    }
                                    switch (questName) {
                                        case "id":
                                            qcr.setQuestID(Integer.parseInt(questValue));
                                            break;
                                        case "order":
                                            break;
                                        case "state":
                                            qcr.setQuestStatus(Byte.parseByte(questValue));
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn(String.format("(%d) Unk quest name %s with value %s", questID, questName, questValue));
                                            }
                                            break;
                                    }
                                }
                                if (qcr.getQuestStatus() != -1) {
                                    quest.addRequirement(qcr);
                                }
                            }
                            break;
                        case "pet":
                            for (NXNode idNode : infoNode) {
                                QuestStartItemRequirement qisr = new QuestStartItemRequirement();
                                for (NXNode questNode : idNode) {
                                    String questName = questNode.getName();
                                    String questValue = questNode.get().toString();
                                    switch (questName) {
                                        case "id":
                                            qisr.setId(Integer.parseInt(questValue));
                                            break;
                                        case "order":
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn(String.format("(%d) Unk pet name %s with value %s", questID, questName, questValue));
                                            }
                                            break;
                                    }
                                }
                                quest.addRequirement(qisr);
                            }
                            break;
                        case "job":
                        case "job ":
                            QuestStartJobRequirement qjr = new QuestStartJobRequirement();
                            for (NXNode idNode : infoNode) {
                                qjr.addJobReq(Short.parseShort(idNode.get().toString()));
                            }
                            quest.addRequirement(qjr);
                            break;
                        case "scenarioQuestList":
                            for (NXNode idNode : infoNode) {
                                quest.addScenario(Integer.parseInt(idNode.get().toString()));
                            }
                            break;
                        case "fieldEnter":
                            for (NXNode idNode : infoNode) {
                                quest.addFieldEnter(Integer.parseInt(idNode.get().toString()));
                            }
                            break;
                        case "mob":
                            for (NXNode idNode : infoNode) {
                                QuestProgressMobRequirement qpmr = new QuestProgressMobRequirement();
                                // items are always after mobs
                                qpmr.setOrder(Integer.parseInt(idNode.getName()));
                                for (NXNode questNode : idNode) {
                                    String questName = questNode.getName();
                                    String questValue = questNode.get().toString();
                                    switch (questName) {
                                        case "id":
                                            qpmr.setMobID(Integer.parseInt(questValue));
                                            break;
                                        case "count":
                                            qpmr.setRequiredCount(Integer.parseInt(questValue));
                                            break;
                                        case "order":
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn(String.format("(%d) Unk mob name %s with value %s", questID, questName, questValue));
                                            }
                                            break;
                                    }
                                }
                                quest.addProgressRequirement(qpmr);
                            }
                            break;
                        case "item":
                            for (NXNode idNode : infoNode) {
                                QuestStartItemRequirement qir = new QuestStartItemRequirement();
                                QuestProgressItemRequirement qpir = new QuestProgressItemRequirement();
                                qpir.setOrder(Integer.parseInt(idNode.getName()));
                                for (NXNode questNode : idNode) {
                                    String questName = questNode.getName();
                                    String questValue = questNode.get().toString();
                                    switch (questName) {
                                        case "id":
                                            if (status == 0) {
                                                qir.setId(Integer.parseInt(questValue));
                                            } else {
                                                qpir.setItemID(Integer.parseInt(questValue));
                                            }
                                            break;
                                        case "count":
                                            if (status == 0) {
                                                qir.setQuantity(Integer.parseInt(questValue));
                                            } else {
                                                qpir.setRequiredCount(Integer.parseInt(questValue));
                                            }
                                            break;
                                        case "order":
                                            break;
                                        case "secret":
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn(String.format("(%d) Unk item name %s with value %s", questID, questName, questValue));
                                            }
                                            break;
                                    }
                                }
                                if (status == 0) {
                                    quest.addRequirement(qir);
                                } else {
                                    quest.addProgressRequirement(qpir);
                                }
                            }
                            break;
                        case "skill":
                            for (NXNode idNode : infoNode) {
                                for (NXNode questNode : idNode) {
                                    String questName = questNode.getName();
                                    String questValue = questNode.get().toString();
                                    switch (questName) {
                                        case "id":
                                            quest.setSkill(Integer.parseInt(questValue));
                                            break;
                                        case "order":
                                        case "acquire":
                                        case "level":
                                        case "levelCondition":
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn(String.format("(%d) Unk skill name %s with value %s", questID, questName, questValue));
                                            }
                                            break;
                                    }
                                }
                            }
                            break;
                        case "npcSpeech":
                            String speechValue = "NpcSpeech=";
                            for (NXNode idNode : infoNode) {
                                boolean hasSpeech = false;
                                int templateID = 0, order = 0;
                                for (NXNode questNode : idNode) {
                                    String questName = questNode.getName();
                                    String questValue = questNode.get().toString();
                                    switch (questName) {
                                        case "script":
                                            quest.addSpeech(questValue);
                                            break;
                                        case "speech":
                                            hasSpeech = true;
                                            break;
                                        case "id":
                                            templateID = Integer.parseInt(questValue);
                                            break;
                                        case "order":
                                            order = Integer.parseInt(questValue);
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn(String.format("(%d) Unk npc speech name %s with value %s", questID, questName, questValue));
                                            }
                                            break;
                                    }
                                }
                                if (hasSpeech && templateID != 0) {
                                    speechValue += templateID + "" + order + "/";
                                    quest.addSpeech(speechValue);
                                }
                            }
                            break;
                        default:
                            if (LOG_UNKS) {
                                log.warn(String.format("(%d) Unk name %s with value %s", questID, name, value));
                            }
                            break;
                    }
                }
            }
            getBaseQuests().add(quest);
        }

        NXNode actNode = new LazyNXFile(questNxDir).resolve("Act.img");

        for (NXNode questIDNode : actNode) {
            int id = Integer.parseInt(questIDNode.getName());
            QuestInfo quest = getQuestInfo(id);
            if(quest == null)
                continue;
            for (NXNode statusNode : questIDNode) {
                int status = Integer.parseInt(statusNode.getName());
                for (NXNode rewardNode : statusNode) {
                    String name = rewardNode.getName();
                    String value = "";
                    if(rewardNode.get() != null)
                        value = rewardNode.get().toString();
                    switch (name) {
                        case "transferField":
                            quest.setTransferField(Integer.parseInt(value));
                            break;
                        case "nextQuest":
                            quest.setNextQuest(Integer.parseInt(value));
                            break;
                        case "exp":
                            quest.addReward(new QuestExpReward(Long.parseLong(value)));
                            break;
                        case "money":
                            quest.addReward(new QuestMoneyReward(Long.parseLong(value)));
                            break;
                        case "pop":
                            quest.addReward(new QuestPopReward(Integer.parseInt(value)));
                            break;
                        case "buffItemID":
                            quest.addReward(new QuestBuffItemReward(Integer.parseInt(value), status));
                            break;
                        case "item":
                            for (NXNode itemNode : rewardNode) {
                                QuestItemReward qir = new QuestItemReward();
                                qir.setStatus(status);
                                for (NXNode itemInfoNode : itemNode) {
                                    String itemName = itemInfoNode.getName();
                                    String itemValue = itemInfoNode.get().toString();
                                    switch (itemName) {
                                        case "id":
                                            qir.setId(Integer.parseInt(itemValue));
                                            break;
                                        case "prop":
                                            qir.setProp(Integer.parseInt(itemValue));
                                            break;
                                        case "count":
                                            qir.setQuantity(Short.parseShort(itemValue));
                                            break;
                                        case "potentialGrade":
                                            qir.setPotentialGrade(itemValue);
                                            break;
                                        case "gender":
                                            qir.setGender(Integer.parseInt(itemValue));
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn(String.format("(%d) Unk item name %s with value %s status %d", id, itemName, itemValue, status));
                                            }
                                            break;
                                    }
                                }
                                quest.addReward(qir);
                            }
                            break;
                        default:
                            if (LOG_UNKS) {
                                log.warn(String.format("(%d) Unk name %s with value %s status %d", id, name, value, status));
                            }
                            break;
                    }
                }
            }
        }
        NXNode questInfoImg = new LazyNXFile(questNxDir).resolve("QuestInfo.img");
        for (NXNode questIDNode : questInfoImg) {
            int id = Integer.parseInt(questIDNode.getName());
            QuestInfo quest = getQuestInfo(id);
            if (quest == null)
                continue;
            for (NXNode questInfoNode : questIDNode) {
                String name = questInfoNode.getName();
                String value = "";
                if(questInfoNode.get() != null)
                    value = questInfoNode.get().toString();

                switch (name) {
                    case "autoComplete":
                        quest.setAutoComplete(Integer.parseInt(value) == 1);
                        break;
                    case "viewMedalItem":
                        quest.setMedalItemId(Integer.parseInt(value));
                        break;
                }
            }
        }
    }

    public static Set<QuestInfo> getBaseQuests() {
        return baseQuests;
    }

    public static void linkMobData() throws IOException {
        if (getBaseQuests().isEmpty()) {
            loadQuestsFromWZ();
        }
        for (QuestInfo qi : getBaseQuests()) {
            for (QuestProgressMobRequirement qpmr :
                    qi.getQuestProgressRequirements()
                            .stream()
                            .filter(q -> q instanceof QuestProgressMobRequirement)
                            .map(q -> (QuestProgressMobRequirement) q)
                            .collect(Collectors.toSet())) { // readability is overrated
                Mob m = MobData.getMobById(qpmr.getMobID());
                if (m != null) {
                    m.addQuest(qi.getQuestID());
                }
            }
        }
    }

    public static void linkItemData() throws IOException {
        if (getBaseQuests().isEmpty()) {
            loadQuestsFromWZ();
        }
        for (QuestInfo qi : getBaseQuests()) {
            for (QuestProgressItemRequirement qpmr :
                    qi.getQuestProgressRequirements()
                            .stream()
                            .filter(q -> q instanceof QuestProgressItemRequirement)
                            .map(q -> (QuestProgressItemRequirement) q)
                            .collect(Collectors.toSet())) { // readability is overrated
                int itemID = qpmr.getItemID();
                if (ItemConstants.isEquip(itemID)) {
                    // create new ItemInfos just for equips that are required for quests
                    // normally ItemInfo is just for non-equips.
                    ItemInfo ii = new ItemInfo();
                    ii.setItemId(itemID);
                    ii.setInvType(InvType.EQUIP);
                    ii.addQuest(qi.getQuestID());
                    ItemData.addItemInfo(ii);
                } else {
                    ItemInfo item = ItemData.getItemInfoByID(qpmr.getItemID());
                    if (item != null) {
                        item.addQuest(qi.getQuestID());
                    }
                }
            }
        }
    }

    public static void generateDatFiles() throws IOException {
        log.info("Started generating quest data.");
        long start = System.currentTimeMillis();
        if (getBaseQuests().isEmpty()) {
            loadQuestsFromWZ();
        }
        log.info(String.format("Completed generating quest data in %dms.", System.currentTimeMillis() - start));
    }

    private static QuestInfo getQuestInfo(int id) {
        return getBaseQuests().stream().filter(qi -> qi.getQuestID() == id).findFirst().orElse(null);
    }

    public static QuestInfo getQuestInfoById(int id) {
        return getQuestInfo(id);
    }


    public static Quest createQuestFromId(int questID) {
        QuestInfo qi = getQuestInfoById(questID);
        Quest quest = new Quest();
        quest.setQRKey(questID);
        if (qi != null) {
            if (qi.isAutoComplete()) {
                quest.setStatus(QuestStatus.Started);
//            quest.completeQuest(); // TODO check what autocomplete actually means
            } else {
                quest.setStatus(QuestStatus.Started);
            }
            for (QuestProgressRequirement qpr : qi.getQuestProgressRequirements()) {
                quest.addQuestProgressRequirement(qpr.deepCopy());
            }
        } else {
            quest.setStatus(QuestStatus.Started);
        }
        return quest;
    }

    public static void main(String[] args) {
        try {
            generateDatFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clear() {
        getBaseQuests().clear();
    }
}
