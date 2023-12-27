package net.mugeemu.ms.loaders;

import net.mugeemu.ms.loaders.containerclasses.ReactorInfo;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.life.Reactor;
import net.mugeemu.ms.life.drop.DropInfo;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import net.mugeemu.ms.util.Rect;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.util.XMLApi;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.io.*;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created on 4/21/2018.
 */
public class ReactorData {

    private static final boolean LOG_UNKS = false;
    private static final Logger log = Logger.getLogger(ReactorData.class);
    private static HashMap<Integer, ReactorInfo> reactorInfo = new HashMap<>();

    private static void loadReactorsFromWZ() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Reactor.nx";
        NXNode reactors = new LazyNXFile(nxDir).getRoot();

        for (NXNode reactor: reactors) {
            int id = Integer.parseInt(reactor.getName().replace(".img", ""));
            ReactorInfo ri = new ReactorInfo();
            ri.setId(id);
            if (reactor.hasChild("quest")) {
                ri.setQuest(Integer.parseInt(reactor.getChild("quest").get().toString()));
            }

            if (reactor.hasChild("action")) {
                ri.setAction(reactor.getChild("action").get().toString());
            }

            for (NXNode infoNode : reactor.getChild("info")) {
                String name = infoNode.getName();
                String value = infoNode.get().toString();
                switch (name) {
                    case "info":
                        ri.setInfo(value);
                        break;
                    case "lt":
                        int x1 = Integer.parseInt(infoNode.getChild("x").get().toString());
                        int y1 = Integer.parseInt(infoNode.getChild("y").get().toString());

                        NXNode rbNode = infoNode.getChild("rb");
                        int x2 = Integer.parseInt(rbNode.getChild("x").get().toString());
                        int y2 = Integer.parseInt(rbNode.getChild("y").get().toString());
                        ri.setRect(new Rect(x1, y1, x2, y2));
                        break;
                    case "name":
                        ri.setName(value);
                        break;
                    case "viewName":
                        ri.setViewName(value);
                        break;
                    case "link":
                        ri.setLink(Integer.parseInt(value));
                        break;
                    case "level":
                        ri.setLevel(Integer.parseInt(value));
                        break;
                    case "resetTime":
                        ri.setResetTime(Integer.parseInt(value));
                        break;
                    case "hitCount":
                        ri.setHitCount(Integer.parseInt(value));
                        break;
                    case "overlapHit":
                        ri.setOverlapHit(Integer.parseInt(value));
                        break;
                    case "overlapHitTime":
                        ri.setOverlapHitTime(Integer.parseInt(value));
                        break;
                    case "actMark":
                        ri.setActMark(Integer.parseInt(value));
                        break;
                    case "dcMark":
                        ri.setDcMark("1".equalsIgnoreCase(value));
                        break;
                    case "activateByTouch":
                        ri.setActivateByTouch("1".equalsIgnoreCase(value));
                        break;
                    case "removeInFieldSet":
                        ri.setRemoveInFieldSet("1".equalsIgnoreCase(value));
                        break;
                    case "notHitable":
                        ri.setNotHittable("1".equalsIgnoreCase(value));
                        break;
                    case "notFatigue":
                        ri.setNotFatigue("1".equalsIgnoreCase(value));
                        break;
                    case "backTile":
                    case "frontTile":
                    case "forcedViewName":
                    case "rb":
                        break;
                    default:
                        if (LOG_UNKS) {
                            log.warn(String.format("[Reactor] Unhandled info node id %d, %s, value %s", ri.getId(), name, value));
                        }
                }
            }
            addReactorInfo(ri);
        }
    }

    private static void saveReactors(String dir) {
        Util.makeDirIfAbsent(dir);
        for (ReactorInfo ri : getReactorInfo().values()) {
            File file = new File(String.format("%s/%d.dat", dir, ri.getId()));
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
                dos.writeInt(ri.getId());
                dos.writeInt(ri.getLink());
                dos.writeInt(ri.getLevel());
                dos.writeInt(ri.getResetTime());
                dos.writeInt(ri.getOverlapHitTime());
                dos.writeInt(ri.getActMark());
                dos.writeInt(ri.getHitCount());
                dos.writeInt(ri.getOverlapHit());
                dos.writeInt(ri.getQuest());
                dos.writeUTF(ri.getInfo());
                dos.writeUTF(ri.getName());
                dos.writeUTF(ri.getViewName());
                dos.writeUTF(ri.getAction());
                dos.writeBoolean(ri.isNotFatigue());
                dos.writeBoolean(ri.isDcMark());
                dos.writeBoolean(ri.isRemoveInFieldSet());
                dos.writeBoolean(ri.isActivateByTouch());
                dos.writeBoolean(ri.isNotHittable());
                dos.writeBoolean(ri.getRect() != null);
                if (ri.getRect() != null) {
                    Rect r = ri.getRect();
                    dos.writeShort(r.getLeft());
                    dos.writeShort(r.getTop());
                    dos.writeShort(r.getRight());
                    dos.writeShort(r.getBottom());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static ReactorInfo loadReactorFromFile(File file) {
        ReactorInfo ri = null;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            ri = new ReactorInfo();
            ri.setId(dis.readInt());
            ri.setLink(dis.readInt());
            ri.setLevel(dis.readInt());
            ri.setResetTime(dis.readInt());
            ri.setOverlapHitTime(dis.readInt());
            ri.setActMark(dis.readInt());
            ri.setHitCount(dis.readInt());
            ri.setOverlapHit(dis.readInt());
            ri.setQuest(dis.readInt());
            ri.setInfo(dis.readUTF());
            ri.setName(dis.readUTF());
            ri.setViewName(dis.readUTF());
            ri.setAction(dis.readUTF());
            ri.setNotFatigue(dis.readBoolean());
            ri.setDcMark(dis.readBoolean());
            ri.setRemoveInFieldSet(dis.readBoolean());
            ri.setActivateByTouch(dis.readBoolean());
            ri.setNotHittable(dis.readBoolean());
            boolean hasRect = dis.readBoolean();
            if (hasRect) {
                Rect r = new Rect();
                r.setLeft(dis.readShort());
                r.setTop(dis.readShort());
                r.setRight(dis.readShort());
                r.setBottom(dis.readShort());
                ri.setRect(r);
            }
            ri.setDrops(DropData.getDropInfoByID(ri.getId()).stream().filter(DropInfo::getReactorDrop).collect(Collectors.toSet()));
            addReactorInfo(ri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ri;
    }

    public static ReactorInfo getReactorInfoByID(int id) {
        ReactorInfo ri = getReactorInfo().get(id);
        return ri == null ? loadReactorByID(id) : ri;
    }

    public static Reactor getReactorByID(int id) {
        Reactor r = new Reactor(id);
        r.init();
        return r;
    }

    private static ReactorInfo loadReactorByID(int id) {
        File file = new File(String.format("%s/reactors/%d.dat", ServerConstants.DAT_DIR, id));
        if(file.exists()) {
            return loadReactorFromFile(file);
        } else {
            return null;
        }
    }

    private static HashMap<Integer, ReactorInfo> getReactorInfo() {
        return reactorInfo;
    }

    private static void addReactorInfo(ReactorInfo ri) {
        getReactorInfo().put(ri.getId(), ri);
    }

    public static void generateDatFiles() {
        log.info("Started generating reactor data.");
        long start = System.currentTimeMillis();
        try {
            loadReactorsFromWZ();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(String.format("Completed generating reactor data in %dms.", System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        generateDatFiles();
    }

    public static void clear() {
        getReactorInfo().clear();
    }
}
