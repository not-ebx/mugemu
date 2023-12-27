package net.mugeemu.ms.loaders;

import net.mugeemu.ms.connection.db.DatabaseManager;
import net.mugeemu.ms.constants.GameConstants;
import net.mugeemu.ms.client.character.runestones.RuneStone;
import net.mugeemu.ms.enums.FieldType;
import net.mugeemu.ms.life.mob.Mob;
import net.mugeemu.ms.world.field.*;
import net.mugeemu.ms.life.Life;
import net.mugeemu.ms.life.npc.Npc;
import net.mugeemu.ms.life.Reactor;
import net.mugeemu.ms.ServerConstants;
import org.apache.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.util.Util;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created on 12/21/2017.
 */
public class FieldData {

    private static List<Field> fields = new ArrayList<>();
    private static List<Integer> worldMapFields = new ArrayList<>();
    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();
    private static final boolean LOG_UNKS = false;

    public static void main(String[] args) {
        generateDatFiles();
    }

    public static void loadNPCFromSQL() {

        Session session = DatabaseManager.getSession();
        Transaction transaction = session.beginTransaction();

        Query loadNpcQuery = session.createNativeQuery("SELECT * FROM npc");

        List<Object[]> results =loadNpcQuery.getResultList();

        for(Object[] r : results) {
            Npc n = NpcData.getNpcDeepCopyById((Integer)r[1]);
            Field f = getFieldById( (Integer)r[2] );

            Position p = new Position();
            p.setX((Integer)r[3]);
            p.setY((Integer)r[4]);

            n.setPosition(p);
            n.setCy((Integer)r[5]);
            n.setRx0((Integer)r[6]);
            n.setRx1((Integer)r[7]);
            n.setFh((Integer)r[8]);

            f.addLife(n);
        }


        transaction.commit();
        session.close();

    }

    private static void loadFieldInfoFromWz() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Map.nx";
        NXNode mapNX = new LazyNXFile(nxDir).resolve("Map");

        for (NXNode mapNode : mapNX) {
            if(mapNode.getName().contains("AreaCode") || mapNode.getName().contains("Graph")){
                continue;
            }

            for (NXNode mapImg : mapNode) {
                int id = Integer.parseInt(mapImg.getName().replace(".img", ""));
                Field field = new Field(id);
                NXNode infoNode = mapImg.getChild("info");
                if(infoNode == null)
                    continue;
                for (NXNode n : infoNode) {
                    if(n.get() == null)
                        continue;
                    String name = n.getName();
                    String value = n.get().toString();
                    switch (name) {
                        case "town":
                            field.setTown(Integer.parseInt(value) != 0);
                            break;
                        case "swim":
                            field.setSwim(Integer.parseInt(value) != 0);
                            break;
                        case "fieldLimit":
                            field.setFieldLimit(Long.parseLong(value));
                            break;
                        case "returnMap":
                            field.setReturnMap(Integer.parseInt(value));
                            break;
                        case "forcedReturn":
                            field.setForcedReturn(Integer.parseInt(value));
                            break;
                        case "mobRate":
                            field.setMobRate(Double.parseDouble(value));
                            break;
                        case "fly":
                            field.setFly(Integer.parseInt(value) != 0);
                            break;
                        case "onFirstUserEnter":
                            field.setOnFirstUserEnter(value);
                            break;
                        case "onUserEnter":
                            field.setOnUserEnter(value);
                            break;
                        case "fieldScript":
                            field.setFieldScript(value);
                            break;
                        case "reactorShuffle":
                            field.setReactorShuffle(Integer.parseInt(value) != 0);
                            break;
                        case "expeditionOnly":
                            field.setExpeditionOnly(Integer.parseInt(value) != 0);
                            break;
                        case "partyOnly":
                            field.setPartyOnly(Integer.parseInt(value) != 0);
                            break;
                        case "isNeedSkillForFly":
                            field.setNeedSkillForFly(Integer.parseInt(value) != 0);
                            break;
                        case "fixedMobCapacity":
                            field.setFixedMobCapacity(Integer.parseInt(value));
                            break;
                        case "createMobInterval":
                            field.setCreateMobInterval(Integer.parseInt(value));
                            break;
                        case "timeOut":
                            field.setTimeOut(Integer.parseInt(value));
                            break;
                        case "timeLimit":
                            field.setTimeLimit(Integer.parseInt(value));
                            break;
                        case "lvLimit":
                            field.setLvLimit(Integer.parseInt(value));
                            break;
                        case "lvForceMove":
                            field.setLvForceMove(Integer.parseInt(value));
                            break;
                        case "consumeItemCoolTime":
                            field.setConsumeItemCoolTime(Integer.parseInt(value));
                            break;
                        case "link":
                            field.setLink(Integer.parseInt(value));
                            break;
                        case "bossMobID":
                            field.setBossMobID(Integer.parseInt(value));
                            break;
                        case "VRTop":
                            field.setVrTop(Integer.parseInt(value));
                            break;
                        case "VRLeft":
                            field.setVrLeft(Integer.parseInt(value));
                            break;
                        case "VRBottom":
                            field.setVrBottom(Integer.parseInt(value));
                            break;
                        case "VRRight":
                            field.setVrRight(Integer.parseInt(value));
                            break;
                        case "fieldType":
                            if (value.equals("")) {
                                field.setFieldType(FieldType.DEAFULT);
                            } else {
                                FieldType fieldType = FieldType.getByVal(Integer.parseInt(value));
                                if (fieldType == null) {
                                    field.setFieldType(FieldType.DEAFULT);
                                    break;
                                }
                                field.setFieldType(fieldType);
                            }
                            break;
                    }
                }
                if (field.getFieldType() == null) {
                    field.setFieldType(FieldType.DEAFULT);
                }
                NXNode fhNode = mapImg.getChild("foothold");
                if (fhNode != null) {
                    for (NXNode layerIDNode : fhNode) {
                        int layerID = Integer.parseInt(layerIDNode.getName());
                        for (NXNode groupIDNode : layerIDNode) {
                            int groupID = Integer.parseInt(groupIDNode.getName());
                            for (NXNode idNode : groupIDNode) {
                                int fhId = Integer.parseInt(idNode.getName());
                                Foothold fh = new Foothold(fhId, layerID, groupID);
                                for (NXNode n : idNode) {
                                    String name = n.getName();
                                    String value = n.get().toString();
                                    switch (name) {
                                        case "x1":
                                            fh.setX1(Integer.parseInt(value));
                                            break;
                                        case "y1":
                                            fh.setY1(Integer.parseInt(value));
                                            break;
                                        case "x2":
                                            fh.setX2(Integer.parseInt(value));
                                            break;
                                        case "y2":
                                            fh.setY2(Integer.parseInt(value));
                                            break;
                                        case "next":
                                            fh.setNext(Integer.parseInt(value));
                                            break;
                                        case "prev":
                                            fh.setPrev(Integer.parseInt(value));
                                            break;
                                        case "force":
                                            fh.setForce(Integer.parseInt(value));
                                            break;
                                        case "forbidFallDown":
                                            fh.setForbidFallDown(Integer.parseInt(value) != 0);
                                            break;
                                    }
                                }
                                field.addFoothold(fh);
                            }
                        }
                    }
                }
                NXNode portalNode = mapImg.getChild("portal");
                if (portalNode != null) {
                    for (NXNode idNode : portalNode) {
                        int portalId = Integer.parseInt(idNode.getName());
                        Portal portal = new Portal(portalId);
                        for (NXNode n : idNode) {
                            if(n.get() == null)
                                continue;
                            String name = n.getName();
                            String value = n.get().toString();


                            switch (name) {
                                case "pt":
                                    portal.setType(PortalType.getTypeByInt(Integer.parseInt(value)));
                                    break;
                                case "pn":
                                    portal.setName(value);
                                    break;
                                case "tm":
                                    portal.setTargetMapId(Integer.parseInt(value));
                                    break;
                                case "tn":
                                    portal.setTargetPortalName(value);
                                    break;
                                case "x":
                                    portal.setX(Integer.parseInt(value));
                                    break;
                                case "y":
                                    portal.setY(Integer.parseInt(value));
                                    break;
                                case "horizontalImpact":
                                    portal.setHorizontalImpact(Integer.parseInt(value));
                                    break;
                                case "verticalImpact":
                                    portal.setVerticalImpact(Integer.parseInt(value));
                                    break;
                                case "script":
                                    portal.setScript(value);
                                    break;
                                case "onlyOnce":
                                    portal.setOnlyOnce(Integer.parseInt(value) != 0);
                                    break;
                                case "delay":
                                    portal.setDelay(Integer.parseInt(value));
                                    break;
                            }
                        }
                        field.addPortal(portal);
                    }
                }

                NXNode lifeNode = mapImg.getChild("life");
                if (lifeNode != null) {
                    List<NXNode> idNodes = new ArrayList<>();
                    idNodes.add(lifeNode);

                    if (lifeNode.getChild("isCategory") != null) {
                        for (NXNode catNode : lifeNode) {
                            if (!catNode.getName().equals("isCategory")) {
                                idNodes.add(catNode);
                            }
                        }
                    }

                    for(NXNode idChildNode : idNodes) {
                        for (NXNode idNode : idChildNode) {
                            Life life = new Life(0);
                            for (NXNode n : idNode) {
                                String name = n.getName();
                                String value = n.get().toString();
                                switch (name) {
                                    case "type":
                                        life.setLifeType(value);
                                        break;
                                    case "id":
                                        life.setTemplateId(Integer.parseInt(value));
                                        break;
                                    case "x":
                                        life.setX(Integer.parseInt(value));
                                        break;
                                    case "y":
                                        life.setY(Integer.parseInt(value));
                                        break;
                                    case "mobTime":
                                        life.setMobTime(Integer.parseInt(value));
                                        break;
                                    case "f":
                                        life.setFlip(Integer.parseInt(value) != 0);
                                        break;
                                    case "hide":
                                        life.setHide(Integer.parseInt(value) != 0);
                                        break;
                                    case "fh":
                                        life.setFh(Integer.parseInt(value));
                                        break;
                                    case "cy":
                                        life.setCy(Integer.parseInt(value));
                                        break;
                                    case "rx0":
                                        life.setRx0(Integer.parseInt(value));
                                        break;
                                    case "rx1":
                                        life.setRx1(Integer.parseInt(value));
                                        break;
                                    case "limitedname":
                                        life.setLimitedName(value);
                                        break;
                                    case "useDay":
                                        life.setUseDay(Integer.parseInt(value) != 0);
                                        break;
                                    case "useNight":
                                        life.setUseNight(Integer.parseInt(value) != 0);
                                        break;
                                    case "hold":
                                        life.setHold(Integer.parseInt(value) != 0);
                                        break;
                                    case "nofoothold":
                                        life.setNoFoothold(Integer.parseInt(value) != 0);
                                        break;
                                    case "dummy":
                                        life.setDummy(Integer.parseInt(value) != 0);
                                        break;
                                    case "spine":
                                        life.setSpine(Integer.parseInt(value) != 0);
                                        break;
                                    case "mobTimeOnDie":
                                        life.setMobTimeOnDie(Integer.parseInt(value) != 0);
                                        break;
                                    case "regenStart":
                                        life.setRegenStart(Integer.parseInt(value));
                                        break;
                                    case "mobAliveReq":
                                        life.setMobAliveReq(Integer.parseInt(value));
                                        break;
                                    default:
                                        if (LOG_UNKS) {
                                            log.warn("unknown life property " + name + " with value " + value);
                                        }
                                        break;
                                }
                            }
                            field.addLife(life);
                        }
                    }
                }
                NXNode reactorNode = mapImg.getChild("reactor");
                if (reactorNode != null) {
                    for (NXNode reactorIdNode : reactorNode) {
                        Reactor reactor = new Reactor(0);
                        reactor.setLifeType("r");
                        for (NXNode valNode : reactorIdNode) {
                            String name = valNode.getName();
                            String value = valNode.get().toString();
                            int iVal = Util.isNumber(value) ? Integer.parseInt(value) : 0;
                            switch (name) {
                                case "id":
                                    reactor.setTemplateId(iVal);
                                    break;
                                case "x":
                                    Position curPos = reactor.getHomePosition();
                                    if (curPos == null) {
                                        curPos = new Position();
                                    }
                                    curPos.setX(iVal);
                                    reactor.setX(iVal);
                                    reactor.setHomePosition(curPos);
                                    break;
                                case "y":
                                    curPos = reactor.getHomePosition();
                                    if (curPos == null) {
                                        curPos = new Position();
                                    }
                                    curPos.setY(iVal);
                                    reactor.setY(iVal);
                                    reactor.setHomePosition(curPos);
                                    break;
                                case "reactorTime":
                                    reactor.setMobTime(iVal);
                                    break;
                                case "f":
                                    reactor.setFlip(iVal != 0);
                                    break;
                                case "name":
                                    reactor.setLimitedName(value);
                                    break;
                                case "phantomForest":
                                    reactor.setPhantomForest(iVal != 0);
                                    break;
                                default:
                                    if (LOG_UNKS) {
                                        log.warn(String.format("Unknown reactor property %s with value %s", name, value));
                                    }
                            }
                        }
                        field.addLife(reactor);
                    }
                }
                NXNode directionInfoNode = mapImg.getChild("directionInfo");
                if (directionInfoNode != null) {
                    for (NXNode idNode : directionInfoNode) {
                        String name = idNode.getName();
                        for (NXNode n : idNode) {
                            // there are more values but only the client use it we need only eventQ
                            if (n.getName().equals("EventQ")) {
                                List<String> directionInfo = new ArrayList<>();
                                for (NXNode event : n) {
                                    directionInfo.add(event.getName());
                                }
                                field.addDirectionInfo(Integer.parseInt(name), directionInfo);
                            }
                        }
                    }
                }
                getFields().add(field);
            }
        }
    }

    public static List<Field> getFields() {
        return fields;
    }

    public static List<Integer> getWorldMapFields() { return worldMapFields; }

    public static Field getFieldById(int id) {
        for (Field f : getFields()) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    public static void loadWorldMapFromWz() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Map.nx";
        NXNode worldMapNode = new LazyNXFile(nxDir).resolve("WorldMap");

        for (NXNode worldMapImg : worldMapNode) {
            NXNode mapList = worldMapImg.getChild("MapList");
            for (NXNode n : mapList) {
                NXNode infoNode = n.getChild("mapNo");
                for (NXNode info : infoNode) {
                    int fieldId = Integer.parseInt(info.get().toString());
                    if (!worldMapFields.contains(fieldId)) {
                        worldMapFields.add(fieldId);
                    }
                }
            }
        }
    }

    public static void generateDatFiles() {
        log.info("Started generating field data.");
        long start = System.currentTimeMillis();
        try {
            loadFieldInfoFromWz();
            loadWorldMapFromWz();
            log.info(String.format("Completed generating field data in %dms.", System.currentTimeMillis() - start));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getFieldCopyById(int id) {
        Field field = getFieldById(id);
        if (field == null) {
            return null;
        }
        Field copy = new Field(id);
        copy.setFieldType(field.getFieldType());
        copy.setTown(field.isTown());
        copy.setSwim(field.isSwim());
        copy.setFieldLimit(field.getFieldLimit());
        copy.setReturnMap(field.getReturnMap());
        copy.setForcedReturn(field.getForcedReturn());
        copy.setMobRate(field.getMobRate());
        copy.setFly(field.isFly());
        copy.setOnFirstUserEnter(field.getOnFirstUserEnter());
        copy.setOnUserEnter(field.getOnUserEnter());
        copy.setFieldScript(field.getFieldScript());
        copy.setReactorShuffle(field.isReactorShuffle());
        copy.setExpeditionOnly(field.isExpeditionOnly());
        copy.setPartyOnly(field.isPartyOnly());
        copy.setNeedSkillForFly(field.isNeedSkillForFly());
        if (field.getFixedMobCapacity() != 0) {
            copy.setFixedMobCapacity(field.getFixedMobCapacity());
        }
        copy.setCreateMobInterval(field.getCreateMobInterval());
        copy.setTimeOut(field.getTimeOut());
        copy.setTimeLimit(field.getTimeLimit());
        copy.setLvLimit(field.getLvLimit());
        copy.setLvForceMove(field.getLvForceMove());
        copy.setConsumeItemCoolTime(field.getConsumeItemCoolTime());
        copy.setLink(field.getLink());
        for (Foothold fh : field.getFootholds()) {
            copy.addFoothold(fh.deepCopy());
        }
        for (Portal p : field.getPortals()) {
            copy.addPortal(p.deepCopy());
        }
        for (Life l : field.getLifes().values()) {
            copy.addLife(l.deepCopy());
        }
        copy.setObjectIDCounter(field.getNewObjectID());
        copy.setRuneStone(new RuneStone().getRandomRuneStone(copy));
        copy.setVrTop(field.getVrTop());
        copy.setVrLeft(field.getVrLeft());
        copy.setVrBottom(field.getVrBottom());
        copy.setVrRight(field.getVrRight());
        copy.startBurningFieldTimer();
        int mobGens = field.getMobGens().size();
        copy.setFixedMobCapacity((int) (mobGens * GameConstants.DEFAULT_FIELD_MOB_RATE_BY_MOBGEN_COUNT));
        copy.generateMobs(true);
        copy.setDirectionInfo(field.getDirectionInfo());
        copy.startFieldScript();
        return copy;
    }

    public static void clear() {
        getFields().clear();
        getWorldMapFields().clear();
    }
}
