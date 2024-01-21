package net.mugeemu.ms;

import net.mugeemu.ms.constants.JobConstants;

/**
 * Created on 2/18/2017.
 */
public class ServerConstants {
	public static final String DIR = System.getProperty("user.dir");
	public static final byte LOCALE = 8;
	public static final String WZ_DIR = DIR + "/WZ";
	public static final String NX_DIR = DIR + "/resources/nxfiles";
	public static final String DAT_DIR = DIR + "/dat";
	public static final int MAX_CHARACTERS = JobConstants.LoginJob.values().length * 3;
	public static final String SCRIPT_DIR = DIR + "/scripts";
	public static final String RESOURCES_DIR = DIR + "/resources";
	public static final String HANDLERS_DIR = DIR + "/src/main/java/net/mugeemu/ms/handlers";
	public static final short VERSION = 92;
	public static final String MINOR_VERSION = "1";
	public static final byte[] SERVER_IP = new byte[] {
		(byte)192,
		(byte)168,
		(byte)1,
		(byte)167
	};
	public static final int LOGIN_PORT = 8484;
	public static final short CHAT_PORT = 8483;
	public static final int BCRYPT_ITERATIONS = 12;
}
