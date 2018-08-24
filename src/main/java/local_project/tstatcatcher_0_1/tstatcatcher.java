package local_project.tstatcatcher_0_1;

import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.DataQuality;
import routines.Relational;
import routines.Mathematical;
import routines.SQLike;
import routines.Numeric;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.DQTechnical;
import routines.MDM;
import routines.StringHandling;
import routines.TalendDate;
import routines.DqStringHandling;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")
/**
 * Job: tstatcatcher Purpose: <br>
 * Description:  <br>
 * @author user@talend.com
 * @version 6.5.1.20180116_1512
 * @status 
 */
public class tstatcatcher implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "tstatcatcher.log");
	}
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(tstatcatcher.class);

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset
			.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

	}

	private ContextProperties context = new ContextProperties();

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "tstatcatcher";
	private final String projectName = "LOCAL_PROJECT";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	public void setDataSources(
			java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources
				.entrySet()) {
			talendDataSources.put(
					dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry
							.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
	}

	StatCatcherUtils tStatCatcher_1 = new StatCatcherUtils(
			"_H9M9YJ-zEeiuXcm3dPMskw", "0.1");
	StatCatcherUtils talendStats_STATS = new StatCatcherUtils(
			"_H9M9YJ-zEeiuXcm3dPMskw", "0.1");

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(
			new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent,
				final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null
						&& currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE",
							getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE",
						getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent
						+ " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					tstatcatcher.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass()
							.getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(tstatcatcher.this, new Object[] { e,
									currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tRowGenerator_1_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputDelimited_1_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		tStatCatcher_1.addMessage("failure", errorComponent,
				end_Hash.get(errorComponent) - start_Hash.get(errorComponent));
		tStatCatcher_1Process(globalMap);

		talendStats_STATS.addMessage("failure", errorComponent,
				end_Hash.get(errorComponent) - start_Hash.get(errorComponent));
		talendStats_STATSProcess(globalMap);

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDie_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDie_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tStatCatcher_1_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tStatCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tReplicate_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tStatCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMSSqlOutput_1_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tStatCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tStatCatcher_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendStats_STATS_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		talendStats_CONSOLE_error(exception, errorComponent, globalMap);

	}

	public void talendStats_CONSOLE_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendStats_STATS_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRowGenerator_1_onSubJobError(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread
				.currentThread().getId() + "", "ERROR", "",
				exception.getMessage(),
				ResumeUtil.getExceptionStackTrace(exception), "");

		try {

			if (this.execStat) {
				runStat.updateStatOnConnection("OnSubjobError1", 0, "error");
			}

			errorCode = null;
			tDie_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tDie_1_onSubJobError(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread
				.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(),
				ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tStatCatcher_1_onSubJobError(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread
				.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(),
				ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendStats_STATS_onSubJobError(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread
				.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(),
				ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row1Struct implements
			routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_LOCAL_PROJECT_tstatcatcher = new byte[0];
		static byte[] commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[0];

		public Integer id;

		public Integer getId() {
			return this.id;
		}

		public String matriculate;

		public String getMatriculate() {
			return this.matriculate;
		}

		public Integer age;

		public Integer getAge() {
			return this.age;
		}

		public java.util.Date date;

		public java.util.Date getDate() {
			return this.date;
		}

		public String pays;

		public String getPays() {
			return this.pays;
		}

		public java.util.Date newColumn;

		public java.util.Date getNewColumn() {
			return this.newColumn;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos)
				throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_LOCAL_PROJECT_tstatcatcher.length) {
					if (length < 1024
							&& commonByteArray_LOCAL_PROJECT_tstatcatcher.length == 0) {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[1024];
					} else {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_LOCAL_PROJECT_tstatcatcher, 0,
						length);
				strReturn = new String(
						commonByteArray_LOCAL_PROJECT_tstatcatcher, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos)
				throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_LOCAL_PROJECT_tstatcatcher) {

				try {

					int length = 0;

					this.id = readInteger(dis);

					this.matriculate = readString(dis);

					this.age = readInteger(dis);

					this.date = readDate(dis);

					this.pays = readString(dis);

					this.newColumn = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id, dos);

				// String

				writeString(this.matriculate, dos);

				// Integer

				writeInteger(this.age, dos);

				// java.util.Date

				writeDate(this.date, dos);

				// String

				writeString(this.pays, dos);

				// java.util.Date

				writeDate(this.newColumn, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + String.valueOf(id));
			sb.append(",matriculate=" + matriculate);
			sb.append(",age=" + String.valueOf(age));
			sb.append(",date=" + String.valueOf(date));
			sb.append(",pays=" + pays);
			sb.append(",newColumn=" + String.valueOf(newColumn));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (id == null) {
				sb.append("<null>");
			} else {
				sb.append(id);
			}

			sb.append("|");

			if (matriculate == null) {
				sb.append("<null>");
			} else {
				sb.append(matriculate);
			}

			sb.append("|");

			if (age == null) {
				sb.append("<null>");
			} else {
				sb.append(age);
			}

			sb.append("|");

			if (date == null) {
				sb.append("<null>");
			} else {
				sb.append(date);
			}

			sb.append("|");

			if (pays == null) {
				sb.append("<null>");
			} else {
				sb.append(pays);
			}

			sb.append("|");

			if (newColumn == null) {
				sb.append("<null>");
			} else {
				sb.append(newColumn);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tRowGenerator_1Process(
			final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {

			String currentMethodName = new java.lang.Exception()
					.getStackTrace()[0].getMethodName();
			boolean resumeIt = currentMethodName.equals(resumeEntryMethodName);
			if (resumeEntryMethodName == null || resumeIt || globalResumeTicket) {// start
																					// the
																					// resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();

				/**
				 * [tFileOutputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_1", false);
				start_Hash.put("tFileOutputDelimited_1",
						System.currentTimeMillis());

				tStatCatcher_1.addMessage("begin", "tFileOutputDelimited_1");
				tStatCatcher_1Process(globalMap);

				talendStats_STATS.addMessage("begin", "tFileOutputDelimited_1");
				talendStats_STATSProcess(globalMap);

				currentComponent = "tFileOutputDelimited_1";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("row1" + iterateId, 0, 0);

					}
				}

				int tos_count_tFileOutputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_1 - " + ("Start to work."));
				class BytesLimit65535_tFileOutputDelimited_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tFileOutputDelimited_1 = new StringBuilder();
						log4jParamters_tFileOutputDelimited_1
								.append("Parameters:");
						log4jParamters_tFileOutputDelimited_1
								.append("USESTREAM" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("FILENAME"
										+ " = "
										+ "\"C:/Users/Grad57/Desktop/Intsallables/Talend-Tools-Studio/Talend-Tools-Studio-20180116_1512-V6.5.1/workspace/out.csv\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("OS_LINE_SEPARATOR_AS_ROW_SEPARATOR"
										+ " = " + "true");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("CSVROWSEPARATOR" + " = " + "\"\\n\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("FIELDSEPARATOR" + " = " + "\",\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("APPEND"
								+ " = " + "true");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("INCLUDEHEADER" + " = " + "true");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("ADVANCED_SEPARATOR" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("CSV_OPTION" + " = " + "true");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("ESCAPE_CHAR" + " = " + "\"\"\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("TEXT_ENCLOSURE" + " = " + "\"\"\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("CREATE"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("SPLIT"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("FLUSHONROW" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("ROW_MODE"
								+ " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1.append("ENCODING"
								+ " = " + "\"ISO-8859-15\"");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						log4jParamters_tFileOutputDelimited_1
								.append("DELETE_EMPTYFILE" + " = " + "false");
						log4jParamters_tFileOutputDelimited_1.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tFileOutputDelimited_1 - "
									+ (log4jParamters_tFileOutputDelimited_1));
					}
				}

				new BytesLimit65535_tFileOutputDelimited_1().limitLog4jByte();

				String fileName_tFileOutputDelimited_1 = "";
				fileName_tFileOutputDelimited_1 = (new java.io.File(
						"C:/Users/Grad57/Desktop/Intsallables/Talend-Tools-Studio/Talend-Tools-Studio-20180116_1512-V6.5.1/workspace/out.csv"))
						.getAbsolutePath().replace("\\", "/");
				String fullName_tFileOutputDelimited_1 = null;
				String extension_tFileOutputDelimited_1 = null;
				String directory_tFileOutputDelimited_1 = null;
				if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1
							.lastIndexOf("/")) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(0, fileName_tFileOutputDelimited_1
										.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1
										.lastIndexOf("."));
					}
					directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
							.substring(0, fileName_tFileOutputDelimited_1
									.lastIndexOf("/"));
				} else {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(0, fileName_tFileOutputDelimited_1
										.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1
										.lastIndexOf("."));
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					}
					directory_tFileOutputDelimited_1 = "";
				}
				boolean isFileGenerated_tFileOutputDelimited_1 = true;
				java.io.File filetFileOutputDelimited_1 = new java.io.File(
						fileName_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME",
						fileName_tFileOutputDelimited_1);
				if (filetFileOutputDelimited_1.exists()) {
					isFileGenerated_tFileOutputDelimited_1 = false;
				}
				String[] headColutFileOutputDelimited_1 = new String[6];
				class CSVBasicSet_tFileOutputDelimited_1 {
					private char field_Delim;
					private char row_Delim;
					private char escape;
					private char textEnclosure;
					private boolean useCRLFRecordDelimiter;

					public boolean isUseCRLFRecordDelimiter() {
						return useCRLFRecordDelimiter;
					}

					public void setFieldSeparator(String fieldSep)
							throws IllegalArgumentException {
						char field_Delim_tFileOutputDelimited_1[] = null;

						// support passing value (property: Field Separator) by
						// 'context.fs' or 'globalMap.get("fs")'.
						if (fieldSep.length() > 0) {
							field_Delim_tFileOutputDelimited_1 = fieldSep
									.toCharArray();
						} else {
							throw new IllegalArgumentException(
									"Field Separator must be assigned a char.");
						}
						this.field_Delim = field_Delim_tFileOutputDelimited_1[0];
					}

					public char getFieldDelim() {
						if (this.field_Delim == 0) {
							setFieldSeparator(",");
						}
						return this.field_Delim;
					}

					public void setRowSeparator(String rowSep) {
						if ("\r\n".equals(rowSep)) {
							useCRLFRecordDelimiter = true;
							return;
						}
						char row_DelimtFileOutputDelimited_1[] = null;

						// support passing value (property: Row Separator) by
						// 'context.rs' or 'globalMap.get("rs")'.
						if (rowSep.length() > 0) {
							row_DelimtFileOutputDelimited_1 = rowSep
									.toCharArray();
						} else {
							throw new IllegalArgumentException(
									"Row Separator must be assigned a char.");
						}
						this.row_Delim = row_DelimtFileOutputDelimited_1[0];
					}

					public char getRowDelim() {
						if (this.row_Delim == 0) {
							setRowSeparator("\n");
						}
						return this.row_Delim;
					}

					public void setEscapeAndTextEnclosure(String strEscape,
							String strTextEnclosure)
							throws IllegalArgumentException {
						if (strEscape.length() <= 0) {
							throw new IllegalArgumentException(
									"Escape Char must be assigned a char.");
						}

						if ("".equals(strTextEnclosure))
							strTextEnclosure = "\0";
						char textEnclosure_tFileOutputDelimited_1[] = null;

						if (strTextEnclosure.length() > 0) {
							textEnclosure_tFileOutputDelimited_1 = strTextEnclosure
									.toCharArray();
						} else {
							throw new IllegalArgumentException(
									"Text Enclosure must be assigned a char.");
						}

						this.textEnclosure = textEnclosure_tFileOutputDelimited_1[0];

						if (("\\").equals(strEscape)) {
							this.escape = '\\';
						} else if (strEscape.equals(strTextEnclosure)) {
							this.escape = this.textEnclosure;
						} else {
							// the default escape mode is double escape
							this.escape = this.textEnclosure;
						}

					}

					public char getEscapeChar() {
						return (char) this.escape;
					}

					public char getTextEnclosure() {
						return this.textEnclosure;
					}
				}

				int nb_line_tFileOutputDelimited_1 = 0;
				int splitedFileNo_tFileOutputDelimited_1 = 0;
				int currentRow_tFileOutputDelimited_1 = 0;

				CSVBasicSet_tFileOutputDelimited_1 csvSettings_tFileOutputDelimited_1 = new CSVBasicSet_tFileOutputDelimited_1();
				csvSettings_tFileOutputDelimited_1.setFieldSeparator(",");
				csvSettings_tFileOutputDelimited_1.setRowSeparator("\n");
				csvSettings_tFileOutputDelimited_1.setEscapeAndTextEnclosure(
						"\"", "\"");
				com.talend.csv.CSVWriter CsvWritertFileOutputDelimited_1 = null;

				CsvWritertFileOutputDelimited_1 = new com.talend.csv.CSVWriter(
						new java.io.BufferedWriter(
								new java.io.OutputStreamWriter(
										new java.io.FileOutputStream(
												fileName_tFileOutputDelimited_1,
												true), "ISO-8859-15")));
				CsvWritertFileOutputDelimited_1
						.setSeparator(csvSettings_tFileOutputDelimited_1
								.getFieldDelim());
				if (!csvSettings_tFileOutputDelimited_1
						.isUseCRLFRecordDelimiter()
						&& csvSettings_tFileOutputDelimited_1.getRowDelim() != '\r'
						&& csvSettings_tFileOutputDelimited_1.getRowDelim() != '\n') {
					CsvWritertFileOutputDelimited_1.setLineEnd(""
							+ csvSettings_tFileOutputDelimited_1.getRowDelim());
				}
				if (filetFileOutputDelimited_1.length() == 0) {
					headColutFileOutputDelimited_1[0] = "id";
					headColutFileOutputDelimited_1[1] = "matriculate";
					headColutFileOutputDelimited_1[2] = "age";
					headColutFileOutputDelimited_1[3] = "date";
					headColutFileOutputDelimited_1[4] = "pays";
					headColutFileOutputDelimited_1[5] = "newColumn";
					CsvWritertFileOutputDelimited_1
							.writeNext(headColutFileOutputDelimited_1);
					CsvWritertFileOutputDelimited_1.flush();
				}
				CsvWritertFileOutputDelimited_1
						.setEscapeChar(csvSettings_tFileOutputDelimited_1
								.getEscapeChar());
				CsvWritertFileOutputDelimited_1
						.setQuoteChar(csvSettings_tFileOutputDelimited_1
								.getTextEnclosure());
				CsvWritertFileOutputDelimited_1
						.setQuoteStatus(com.talend.csv.CSVWriter.QuoteStatus.FORCE);

				resourceMap.put("CsvWriter_tFileOutputDelimited_1",
						CsvWritertFileOutputDelimited_1);
				resourceMap.put("nb_line_tFileOutputDelimited_1",
						nb_line_tFileOutputDelimited_1);

				/**
				 * [tFileOutputDelimited_1 begin ] stop
				 */

				/**
				 * [tRowGenerator_1 begin ] start
				 */

				ok_Hash.put("tRowGenerator_1", false);
				start_Hash.put("tRowGenerator_1", System.currentTimeMillis());

				currentComponent = "tRowGenerator_1";

				int tos_count_tRowGenerator_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tRowGenerator_1 - " + ("Start to work."));
				class BytesLimit65535_tRowGenerator_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tRowGenerator_1 = new StringBuilder();
						log4jParamters_tRowGenerator_1.append("Parameters:");
						if (log.isDebugEnabled())
							log.debug("tRowGenerator_1 - "
									+ (log4jParamters_tRowGenerator_1));
					}
				}

				new BytesLimit65535_tRowGenerator_1().limitLog4jByte();

				int nb_line_tRowGenerator_1 = 0;
				int nb_max_row_tRowGenerator_1 = 100000;

				class tRowGenerator_1Randomizer {
					public Integer getRandomid() {

						return Numeric.sequence("s1", 1, 1);

					}

					public String getRandommatriculate() {

						return TalendString.getAsciiRandomString(6);

					}

					public Integer getRandomage() {

						return Numeric.random(0, 100);

					}

					public java.util.Date getRandomdate() {

						return TalendDate.getRandomDate("2007-01-01",
								"2008-12-31");

					}

					public String getRandompays() {

						String[] paysTable = new String[] { "India", "France",
								"U.S.A." };
						java.util.Random randomtRowGenerator_1 = new java.util.Random();
						return paysTable[randomtRowGenerator_1
								.nextInt(paysTable.length)];

					}

					public java.util.Date getRandomnewColumn() {

						return ((Date) globalMap.get("Trial"));

					}
				}
				tRowGenerator_1Randomizer randtRowGenerator_1 = new tRowGenerator_1Randomizer();

				log.info("tRowGenerator_1 - Generating records.");
				for (int itRowGenerator_1 = 0; itRowGenerator_1 < nb_max_row_tRowGenerator_1; itRowGenerator_1++) {
					row1.id = randtRowGenerator_1.getRandomid();
					row1.matriculate = randtRowGenerator_1
							.getRandommatriculate();
					row1.age = randtRowGenerator_1.getRandomage();
					row1.date = randtRowGenerator_1.getRandomdate();
					row1.pays = randtRowGenerator_1.getRandompays();
					row1.newColumn = randtRowGenerator_1.getRandomnewColumn();
					nb_line_tRowGenerator_1++;

					log.debug("tRowGenerator_1 - Retrieving the record "
							+ nb_line_tRowGenerator_1 + ".");

					/**
					 * [tRowGenerator_1 begin ] stop
					 */

					/**
					 * [tRowGenerator_1 main ] start
					 */

					currentComponent = "tRowGenerator_1";

					tos_count_tRowGenerator_1++;

					/**
					 * [tRowGenerator_1 main ] stop
					 */

					/**
					 * [tFileOutputDelimited_1 main ] start
					 */

					currentComponent = "tFileOutputDelimited_1";

					// row1
					// row1

					if (execStat) {
						runStat.updateStatOnConnection("row1" + iterateId, 1, 1);
					}

					if (log.isTraceEnabled()) {
						log.trace("row1 - "
								+ (row1 == null ? "" : row1.toLogString()));
					}

					String[] rowtFileOutputDelimited_1 = new String[6];
					rowtFileOutputDelimited_1[0] = row1.id == null ? null
							: String.valueOf(row1.id);
					rowtFileOutputDelimited_1[1] = row1.matriculate == null ? null
							: row1.matriculate;
					rowtFileOutputDelimited_1[2] = row1.age == null ? null
							: String.valueOf(row1.age);
					rowtFileOutputDelimited_1[3] = row1.date == null ? null
							: FormatterUtils.format_Date(row1.date,
									"dd-MM-yyyy");
					rowtFileOutputDelimited_1[4] = row1.pays == null ? null
							: row1.pays;
					rowtFileOutputDelimited_1[5] = row1.newColumn == null ? null
							: FormatterUtils.format_Date(row1.newColumn,
									"dd-MM-yyyy");
					nb_line_tFileOutputDelimited_1++;
					resourceMap.put("nb_line_tFileOutputDelimited_1",
							nb_line_tFileOutputDelimited_1);
					CsvWritertFileOutputDelimited_1
							.writeNext(rowtFileOutputDelimited_1);
					log.debug("tFileOutputDelimited_1 - Writing the record "
							+ nb_line_tFileOutputDelimited_1 + ".");

					tos_count_tFileOutputDelimited_1++;

					/**
					 * [tFileOutputDelimited_1 main ] stop
					 */

					/**
					 * [tRowGenerator_1 end ] start
					 */

					currentComponent = "tRowGenerator_1";

				}
				globalMap.put("tRowGenerator_1_NB_LINE",
						nb_line_tRowGenerator_1);
				log.info("tRowGenerator_1 - Generated records count:"
						+ nb_line_tRowGenerator_1 + " .");

				if (log.isDebugEnabled())
					log.debug("tRowGenerator_1 - " + ("Done."));

				ok_Hash.put("tRowGenerator_1", true);
				end_Hash.put("tRowGenerator_1", System.currentTimeMillis());

				/**
				 * [tRowGenerator_1 end ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 end ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (CsvWritertFileOutputDelimited_1 != null) {
					CsvWritertFileOutputDelimited_1.close();
				}

				globalMap.put("tFileOutputDelimited_1_NB_LINE",
						nb_line_tFileOutputDelimited_1);

				resourceMap.put("finish_tFileOutputDelimited_1", true);

				log.debug("tFileOutputDelimited_1 - Written records count: "
						+ nb_line_tFileOutputDelimited_1 + " .");

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("row1" + iterateId, 2, 0);
					}
				}

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileOutputDelimited_1", true);
				end_Hash.put("tFileOutputDelimited_1",
						System.currentTimeMillis());

				tStatCatcher_1.addMessage(
						"end",
						"tFileOutputDelimited_1",
						end_Hash.get("tFileOutputDelimited_1")
								- start_Hash.get("tFileOutputDelimited_1"));
				tStatCatcher_1Process(globalMap);
				talendStats_STATS.addMessage(
						"end",
						"tFileOutputDelimited_1",
						end_Hash.get("tFileOutputDelimited_1")
								- start_Hash.get("tFileOutputDelimited_1"));
				talendStats_STATSProcess(globalMap);

				/**
				 * [tFileOutputDelimited_1 end ] stop
				 */

			}// end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent,
					globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRowGenerator_1 finally ] start
				 */

				currentComponent = "tRowGenerator_1";

				/**
				 * [tRowGenerator_1 finally ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 finally ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (resourceMap.get("finish_tFileOutputDelimited_1") == null) {

					com.talend.csv.CSVWriter CsvWritertFileOutputDelimited_1 = (com.talend.csv.CSVWriter) resourceMap
							.get("CsvWriter_tFileOutputDelimited_1");

					if (CsvWritertFileOutputDelimited_1 != null) {
						CsvWritertFileOutputDelimited_1.close();
					}

				}

				/**
				 * [tFileOutputDelimited_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", 1);
	}

	public void tDie_1Process(final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("tDie_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {

			String currentMethodName = new java.lang.Exception()
					.getStackTrace()[0].getMethodName();
			boolean resumeIt = currentMethodName.equals(resumeEntryMethodName);
			if (resumeEntryMethodName == null || resumeIt || globalResumeTicket) {// start
																					// the
																					// resume
				globalResumeTicket = true;

				/**
				 * [tDie_1 begin ] start
				 */

				ok_Hash.put("tDie_1", false);
				start_Hash.put("tDie_1", System.currentTimeMillis());

				currentComponent = "tDie_1";

				int tos_count_tDie_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDie_1 - " + ("Start to work."));
				class BytesLimit65535_tDie_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tDie_1 = new StringBuilder();
						log4jParamters_tDie_1.append("Parameters:");
						log4jParamters_tDie_1
								.append("MESSAGE"
										+ " = "
										+ "\"ERRRO MESSAGE IS: \"+((String)globalMap.get(\"tDie_1_ERROR_MESSAGE\"))");
						log4jParamters_tDie_1.append(" | ");
						log4jParamters_tDie_1.append("CODE" + " = " + "4");
						log4jParamters_tDie_1.append(" | ");
						log4jParamters_tDie_1.append("PRIORITY" + " = " + "5");
						log4jParamters_tDie_1.append(" | ");
						log4jParamters_tDie_1.append("EXIT_JVM" + " = "
								+ "false");
						log4jParamters_tDie_1.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tDie_1 - " + (log4jParamters_tDie_1));
					}
				}

				new BytesLimit65535_tDie_1().limitLog4jByte();

				/**
				 * [tDie_1 begin ] stop
				 */

				/**
				 * [tDie_1 main ] start
				 */

				currentComponent = "tDie_1";

				globalMap.put("tDie_1_DIE_PRIORITY", 5);
				System.err.println("ERRRO MESSAGE IS: "
						+ ((String) globalMap.get("tDie_1_ERROR_MESSAGE")));

				log.error("tDie_1 - The die message: " + "ERRRO MESSAGE IS: "
						+ ((String) globalMap.get("tDie_1_ERROR_MESSAGE")));

				globalMap.put("tDie_1_DIE_MESSAGE", "ERRRO MESSAGE IS: "
						+ ((String) globalMap.get("tDie_1_ERROR_MESSAGE")));
				globalMap.put("tDie_1_DIE_MESSAGES", "ERRRO MESSAGE IS: "
						+ ((String) globalMap.get("tDie_1_ERROR_MESSAGE")));
				currentComponent = "tDie_1";
				status = "failure";
				errorCode = new Integer(4);
				globalMap.put("tDie_1_DIE_CODE", errorCode);

				if (true) {
					throw new TDieException();
				}

				tos_count_tDie_1++;

				/**
				 * [tDie_1 main ] stop
				 */

				/**
				 * [tDie_1 end ] start
				 */

				currentComponent = "tDie_1";

				if (log.isDebugEnabled())
					log.debug("tDie_1 - " + ("Done."));

				ok_Hash.put("tDie_1", true);
				end_Hash.put("tDie_1", System.currentTimeMillis());

				/**
				 * [tDie_1 end ] stop
				 */
			}// end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent,
					globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDie_1 finally ] start
				 */

				currentComponent = "tDie_1";

				/**
				 * [tDie_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDie_1_SUBPROCESS_STATE", 1);
	}

	public static class row3Struct implements
			routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_LOCAL_PROJECT_tstatcatcher = new byte[0];
		static byte[] commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		private java.util.Date readDate(ObjectInputStream dis)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos)
				throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_LOCAL_PROJECT_tstatcatcher.length) {
					if (length < 1024
							&& commonByteArray_LOCAL_PROJECT_tstatcatcher.length == 0) {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[1024];
					} else {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_LOCAL_PROJECT_tstatcatcher, 0,
						length);
				strReturn = new String(
						commonByteArray_LOCAL_PROJECT_tstatcatcher, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_LOCAL_PROJECT_tstatcatcher) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (moment == null) {
				sb.append("<null>");
			} else {
				sb.append(moment);
			}

			sb.append("|");

			if (pid == null) {
				sb.append("<null>");
			} else {
				sb.append(pid);
			}

			sb.append("|");

			if (father_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(father_pid);
			}

			sb.append("|");

			if (root_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(root_pid);
			}

			sb.append("|");

			if (system_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(system_pid);
			}

			sb.append("|");

			if (project == null) {
				sb.append("<null>");
			} else {
				sb.append(project);
			}

			sb.append("|");

			if (job == null) {
				sb.append("<null>");
			} else {
				sb.append(job);
			}

			sb.append("|");

			if (job_repository_id == null) {
				sb.append("<null>");
			} else {
				sb.append(job_repository_id);
			}

			sb.append("|");

			if (job_version == null) {
				sb.append("<null>");
			} else {
				sb.append(job_version);
			}

			sb.append("|");

			if (context == null) {
				sb.append("<null>");
			} else {
				sb.append(context);
			}

			sb.append("|");

			if (origin == null) {
				sb.append("<null>");
			} else {
				sb.append(origin);
			}

			sb.append("|");

			if (message_type == null) {
				sb.append("<null>");
			} else {
				sb.append(message_type);
			}

			sb.append("|");

			if (message == null) {
				sb.append("<null>");
			} else {
				sb.append(message);
			}

			sb.append("|");

			if (duration == null) {
				sb.append("<null>");
			} else {
				sb.append(duration);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row4Struct implements
			routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_LOCAL_PROJECT_tstatcatcher = new byte[0];
		static byte[] commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		private java.util.Date readDate(ObjectInputStream dis)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos)
				throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_LOCAL_PROJECT_tstatcatcher.length) {
					if (length < 1024
							&& commonByteArray_LOCAL_PROJECT_tstatcatcher.length == 0) {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[1024];
					} else {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_LOCAL_PROJECT_tstatcatcher, 0,
						length);
				strReturn = new String(
						commonByteArray_LOCAL_PROJECT_tstatcatcher, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_LOCAL_PROJECT_tstatcatcher) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (moment == null) {
				sb.append("<null>");
			} else {
				sb.append(moment);
			}

			sb.append("|");

			if (pid == null) {
				sb.append("<null>");
			} else {
				sb.append(pid);
			}

			sb.append("|");

			if (father_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(father_pid);
			}

			sb.append("|");

			if (root_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(root_pid);
			}

			sb.append("|");

			if (system_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(system_pid);
			}

			sb.append("|");

			if (project == null) {
				sb.append("<null>");
			} else {
				sb.append(project);
			}

			sb.append("|");

			if (job == null) {
				sb.append("<null>");
			} else {
				sb.append(job);
			}

			sb.append("|");

			if (job_repository_id == null) {
				sb.append("<null>");
			} else {
				sb.append(job_repository_id);
			}

			sb.append("|");

			if (job_version == null) {
				sb.append("<null>");
			} else {
				sb.append(job_version);
			}

			sb.append("|");

			if (context == null) {
				sb.append("<null>");
			} else {
				sb.append(context);
			}

			sb.append("|");

			if (origin == null) {
				sb.append("<null>");
			} else {
				sb.append(origin);
			}

			sb.append("|");

			if (message_type == null) {
				sb.append("<null>");
			} else {
				sb.append(message_type);
			}

			sb.append("|");

			if (message == null) {
				sb.append("<null>");
			} else {
				sb.append(message);
			}

			sb.append("|");

			if (duration == null) {
				sb.append("<null>");
			} else {
				sb.append(duration);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements
			routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_LOCAL_PROJECT_tstatcatcher = new byte[0];
		static byte[] commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		private java.util.Date readDate(ObjectInputStream dis)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos)
				throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_LOCAL_PROJECT_tstatcatcher.length) {
					if (length < 1024
							&& commonByteArray_LOCAL_PROJECT_tstatcatcher.length == 0) {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[1024];
					} else {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_LOCAL_PROJECT_tstatcatcher, 0,
						length);
				strReturn = new String(
						commonByteArray_LOCAL_PROJECT_tstatcatcher, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_LOCAL_PROJECT_tstatcatcher) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (moment == null) {
				sb.append("<null>");
			} else {
				sb.append(moment);
			}

			sb.append("|");

			if (pid == null) {
				sb.append("<null>");
			} else {
				sb.append(pid);
			}

			sb.append("|");

			if (father_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(father_pid);
			}

			sb.append("|");

			if (root_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(root_pid);
			}

			sb.append("|");

			if (system_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(system_pid);
			}

			sb.append("|");

			if (project == null) {
				sb.append("<null>");
			} else {
				sb.append(project);
			}

			sb.append("|");

			if (job == null) {
				sb.append("<null>");
			} else {
				sb.append(job);
			}

			sb.append("|");

			if (job_repository_id == null) {
				sb.append("<null>");
			} else {
				sb.append(job_repository_id);
			}

			sb.append("|");

			if (job_version == null) {
				sb.append("<null>");
			} else {
				sb.append(job_version);
			}

			sb.append("|");

			if (context == null) {
				sb.append("<null>");
			} else {
				sb.append(context);
			}

			sb.append("|");

			if (origin == null) {
				sb.append("<null>");
			} else {
				sb.append(origin);
			}

			sb.append("|");

			if (message_type == null) {
				sb.append("<null>");
			} else {
				sb.append(message_type);
			}

			sb.append("|");

			if (message == null) {
				sb.append("<null>");
			} else {
				sb.append(message);
			}

			sb.append("|");

			if (duration == null) {
				sb.append("<null>");
			} else {
				sb.append(duration);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tStatCatcher_1Process(
			final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("tStatCatcher_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {

			String currentMethodName = new java.lang.Exception()
					.getStackTrace()[0].getMethodName();
			boolean resumeIt = currentMethodName.equals(resumeEntryMethodName);
			if (resumeEntryMethodName == null || resumeIt || globalResumeTicket) {// start
																					// the
																					// resume
				globalResumeTicket = true;

				row2Struct row2 = new row2Struct();
				row3Struct row3 = new row3Struct();
				row4Struct row4 = new row4Struct();

				/**
				 * [tMSSqlOutput_1 begin ] start
				 */

				ok_Hash.put("tMSSqlOutput_1", false);
				start_Hash.put("tMSSqlOutput_1", System.currentTimeMillis());

				currentComponent = "tMSSqlOutput_1";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("row3" + iterateId, 0, 0);

					}
				}

				int tos_count_tMSSqlOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - " + ("Start to work."));
				class BytesLimit65535_tMSSqlOutput_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tMSSqlOutput_1 = new StringBuilder();
						log4jParamters_tMSSqlOutput_1.append("Parameters:");
						log4jParamters_tMSSqlOutput_1
								.append("USE_EXISTING_CONNECTION" + " = "
										+ "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("DRIVER" + " = "
								+ "JTDS");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("HOST" + " = "
								+ "\"localhost\"");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("PORT" + " = "
								+ "\"1433\"");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("DB_SCHEMA"
								+ " = " + "\"dbo\"");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("DBNAME" + " = "
								+ "\"CITI\"");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("USER" + " = "
								+ "\"sa\"");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("PASS"
								+ " = "
								+ String.valueOf("7412caae74206e73").substring(
										0, 4) + "...");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("TABLE" + " = "
								+ "\"tstatcatcher\"");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("TABLE_ACTION"
								+ " = " + "CREATE_IF_NOT_EXISTS");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("IDENTITY_INSERT"
								+ " = " + "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("DATA_ACTION"
								+ " = " + "INSERT");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1
								.append("SPECIFY_IDENTITY_FIELD" + " = "
										+ "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1
								.append("SPECIFY_DATASOURCE_ALIAS" + " = "
										+ "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("DIE_ON_ERROR"
								+ " = " + "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("PROPERTIES"
								+ " = " + "\"\"");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("COMMIT_EVERY"
								+ " = " + "10000");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("ADD_COLS" + " = "
								+ "[]");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1
								.append("USE_FIELD_OPTIONS" + " = " + "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1
								.append("IGNORE_DATE_OUTOF_RANGE" + " = "
										+ "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1
								.append("ENABLE_DEBUG_MODE" + " = " + "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1
								.append("SUPPORT_NULL_WHERE" + " = " + "false");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("USE_BATCH_SIZE"
								+ " = " + "true");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						log4jParamters_tMSSqlOutput_1.append("BATCH_SIZE"
								+ " = " + "10000");
						log4jParamters_tMSSqlOutput_1.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tMSSqlOutput_1 - "
									+ (log4jParamters_tMSSqlOutput_1));
					}
				}

				new BytesLimit65535_tMSSqlOutput_1().limitLog4jByte();

				int nb_line_tMSSqlOutput_1 = 0;
				int nb_line_update_tMSSqlOutput_1 = 0;
				int nb_line_inserted_tMSSqlOutput_1 = 0;
				int nb_line_deleted_tMSSqlOutput_1 = 0;
				int nb_line_rejected_tMSSqlOutput_1 = 0;

				int deletedCount_tMSSqlOutput_1 = 0;
				int updatedCount_tMSSqlOutput_1 = 0;
				int insertedCount_tMSSqlOutput_1 = 0;
				int rejectedCount_tMSSqlOutput_1 = 0;
				String dbschema_tMSSqlOutput_1 = null;
				String tableName_tMSSqlOutput_1 = null;
				boolean whetherReject_tMSSqlOutput_1 = false;

				java.util.Calendar calendar_tMSSqlOutput_1 = java.util.Calendar
						.getInstance();
				long year1_tMSSqlOutput_1 = TalendDate.parseDate("yyyy-MM-dd",
						"0001-01-01").getTime();
				long year2_tMSSqlOutput_1 = TalendDate.parseDate("yyyy-MM-dd",
						"1753-01-01").getTime();
				long year10000_tMSSqlOutput_1 = TalendDate.parseDate(
						"yyyy-MM-dd HH:mm:ss", "9999-12-31 24:00:00").getTime();
				long date_tMSSqlOutput_1;

				java.util.Calendar calendar_datetimeoffset_tMSSqlOutput_1 = java.util.Calendar
						.getInstance(java.util.TimeZone.getTimeZone("UTC"));

				java.sql.Connection conn_tMSSqlOutput_1 = null;
				String dbUser_tMSSqlOutput_1 = null;
				dbschema_tMSSqlOutput_1 = "dbo";
				String driverClass_tMSSqlOutput_1 = "net.sourceforge.jtds.jdbc.Driver";

				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - " + ("Driver ClassName: ")
							+ (driverClass_tMSSqlOutput_1) + ("."));
				java.lang.Class.forName(driverClass_tMSSqlOutput_1);
				String port_tMSSqlOutput_1 = "1433";
				String dbname_tMSSqlOutput_1 = "CITI";
				String url_tMSSqlOutput_1 = "jdbc:jtds:sqlserver://"
						+ "localhost";
				if (!"".equals(port_tMSSqlOutput_1)) {
					url_tMSSqlOutput_1 += ":" + "1433";
				}
				if (!"".equals(dbname_tMSSqlOutput_1)) {
					url_tMSSqlOutput_1 += "//" + "CITI";

				}
				url_tMSSqlOutput_1 += ";appName=" + projectName + ";" + "";
				dbUser_tMSSqlOutput_1 = "sa";

				final String decryptedPassword_tMSSqlOutput_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("7412caae74206e73");

				String dbPwd_tMSSqlOutput_1 = decryptedPassword_tMSSqlOutput_1;
				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - "
							+ ("Connection attempts to '")
							+ (url_tMSSqlOutput_1) + ("' with the username '")
							+ (dbUser_tMSSqlOutput_1) + ("'."));
				conn_tMSSqlOutput_1 = java.sql.DriverManager.getConnection(
						url_tMSSqlOutput_1, dbUser_tMSSqlOutput_1,
						dbPwd_tMSSqlOutput_1);
				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - " + ("Connection to '")
							+ (url_tMSSqlOutput_1) + ("' has succeeded."));

				resourceMap.put("conn_tMSSqlOutput_1", conn_tMSSqlOutput_1);

				conn_tMSSqlOutput_1.setAutoCommit(false);
				int commitEvery_tMSSqlOutput_1 = 10000;
				int commitCounter_tMSSqlOutput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - "
							+ ("Connection is set auto commit to '")
							+ (conn_tMSSqlOutput_1.getAutoCommit()) + ("'."));
				int batchSize_tMSSqlOutput_1 = 10000;
				int batchSizeCounter_tMSSqlOutput_1 = 0;

				if (dbschema_tMSSqlOutput_1 == null
						|| dbschema_tMSSqlOutput_1.trim().length() == 0) {
					tableName_tMSSqlOutput_1 = "tstatcatcher";
				} else {
					tableName_tMSSqlOutput_1 = dbschema_tMSSqlOutput_1 + "].["
							+ "tstatcatcher";
				}
				int count_tMSSqlOutput_1 = 0;

				java.sql.Statement isExistStmt_tMSSqlOutput_1 = conn_tMSSqlOutput_1
						.createStatement();
				boolean whetherExist_tMSSqlOutput_1 = false;
				try {
					isExistStmt_tMSSqlOutput_1.execute("SELECT TOP 1 1 FROM ["
							+ tableName_tMSSqlOutput_1 + "]");
					whetherExist_tMSSqlOutput_1 = true;
				} catch (java.lang.Exception e) {
					whetherExist_tMSSqlOutput_1 = false;
				}
				isExistStmt_tMSSqlOutput_1.close();
				if (!whetherExist_tMSSqlOutput_1) {
					java.sql.Statement stmtCreate_tMSSqlOutput_1 = conn_tMSSqlOutput_1
							.createStatement();
					if (log.isDebugEnabled())
						log.debug("tMSSqlOutput_1 - " + ("Creating")
								+ (" table '") + (tableName_tMSSqlOutput_1)
								+ ("'."));
					stmtCreate_tMSSqlOutput_1
							.execute("CREATE TABLE ["
									+ tableName_tMSSqlOutput_1
									+ "]([moment] DATETIME ,[pid] VARCHAR(20)  ,[father_pid] VARCHAR(20)  ,[root_pid] VARCHAR(20)  ,[system_pid] BIGINT ,[project] VARCHAR(50)  ,[job] VARCHAR(255)  ,[job_repository_id] VARCHAR(255)  ,[job_version] VARCHAR(255)  ,[context] VARCHAR(50)  ,[origin] VARCHAR(255)  ,[message_type] VARCHAR(255)  ,[message] VARCHAR(255)  ,[duration] BIGINT )");
					if (log.isDebugEnabled())
						log.debug("tMSSqlOutput_1 - " + ("Create")
								+ (" table '") + (tableName_tMSSqlOutput_1)
								+ ("' has succeeded."));
					stmtCreate_tMSSqlOutput_1.close();
				}
				String insert_tMSSqlOutput_1 = "INSERT INTO ["
						+ tableName_tMSSqlOutput_1
						+ "] ([moment],[pid],[father_pid],[root_pid],[system_pid],[project],[job],[job_repository_id],[job_version],[context],[origin],[message_type],[message],[duration]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				java.sql.PreparedStatement pstmt_tMSSqlOutput_1 = conn_tMSSqlOutput_1
						.prepareStatement(insert_tMSSqlOutput_1);

				/**
				 * [tMSSqlOutput_1 begin ] stop
				 */

				/**
				 * [tLogRow_1 begin ] start
				 */

				ok_Hash.put("tLogRow_1", false);
				start_Hash.put("tLogRow_1", System.currentTimeMillis());

				currentComponent = "tLogRow_1";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("row4" + iterateId, 0, 0);

					}
				}

				int tos_count_tLogRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Start to work."));
				class BytesLimit65535_tLogRow_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tLogRow_1 = new StringBuilder();
						log4jParamters_tLogRow_1.append("Parameters:");
						log4jParamters_tLogRow_1.append("BASIC_MODE" + " = "
								+ "true");
						log4jParamters_tLogRow_1.append(" | ");
						log4jParamters_tLogRow_1.append("TABLE_PRINT" + " = "
								+ "false");
						log4jParamters_tLogRow_1.append(" | ");
						log4jParamters_tLogRow_1.append("VERTICAL" + " = "
								+ "false");
						log4jParamters_tLogRow_1.append(" | ");
						log4jParamters_tLogRow_1.append("FIELDSEPARATOR"
								+ " = " + "\"|\"");
						log4jParamters_tLogRow_1.append(" | ");
						log4jParamters_tLogRow_1.append("PRINT_HEADER" + " = "
								+ "false");
						log4jParamters_tLogRow_1.append(" | ");
						log4jParamters_tLogRow_1.append("PRINT_UNIQUE_NAME"
								+ " = " + "false");
						log4jParamters_tLogRow_1.append(" | ");
						log4jParamters_tLogRow_1.append("PRINT_COLNAMES"
								+ " = " + "false");
						log4jParamters_tLogRow_1.append(" | ");
						log4jParamters_tLogRow_1.append("USE_FIXED_LENGTH"
								+ " = " + "false");
						log4jParamters_tLogRow_1.append(" | ");
						log4jParamters_tLogRow_1
								.append("PRINT_CONTENT_WITH_LOG4J" + " = "
										+ "true");
						log4jParamters_tLogRow_1.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tLogRow_1 - "
									+ (log4jParamters_tLogRow_1));
					}
				}

				new BytesLimit65535_tLogRow_1().limitLog4jByte();

				// /////////////////////

				final String OUTPUT_FIELD_SEPARATOR_tLogRow_1 = "|";
				java.io.PrintStream consoleOut_tLogRow_1 = null;

				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
				// /////////////////////

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tReplicate_1 begin ] start
				 */

				ok_Hash.put("tReplicate_1", false);
				start_Hash.put("tReplicate_1", System.currentTimeMillis());

				currentComponent = "tReplicate_1";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("row2" + iterateId, 0, 0);

					}
				}

				int tos_count_tReplicate_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tReplicate_1 - " + ("Start to work."));
				class BytesLimit65535_tReplicate_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tReplicate_1 = new StringBuilder();
						log4jParamters_tReplicate_1.append("Parameters:");
						if (log.isDebugEnabled())
							log.debug("tReplicate_1 - "
									+ (log4jParamters_tReplicate_1));
					}
				}

				new BytesLimit65535_tReplicate_1().limitLog4jByte();

				/**
				 * [tReplicate_1 begin ] stop
				 */

				/**
				 * [tStatCatcher_1 begin ] start
				 */

				ok_Hash.put("tStatCatcher_1", false);
				start_Hash.put("tStatCatcher_1", System.currentTimeMillis());

				currentComponent = "tStatCatcher_1";

				int tos_count_tStatCatcher_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tStatCatcher_1 - " + ("Start to work."));
				class BytesLimit65535_tStatCatcher_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tStatCatcher_1 = new StringBuilder();
						log4jParamters_tStatCatcher_1.append("Parameters:");
						if (log.isDebugEnabled())
							log.debug("tStatCatcher_1 - "
									+ (log4jParamters_tStatCatcher_1));
					}
				}

				new BytesLimit65535_tStatCatcher_1().limitLog4jByte();

				for (StatCatcherUtils.StatCatcherMessage scm : tStatCatcher_1
						.getMessages()) {
					row2.pid = pid;
					row2.root_pid = rootPid;
					row2.father_pid = fatherPid;
					row2.project = projectName;
					row2.job = jobName;
					row2.context = contextStr;
					row2.origin = (scm.getOrigin() == null
							|| scm.getOrigin().length() < 1 ? null : scm
							.getOrigin());
					row2.message = scm.getMessage();
					row2.duration = scm.getDuration();
					row2.moment = scm.getMoment();
					row2.message_type = scm.getMessageType();
					row2.job_version = scm.getJobVersion();
					row2.job_repository_id = scm.getJobId();
					row2.system_pid = scm.getSystemPid();

					/**
					 * [tStatCatcher_1 begin ] stop
					 */

					/**
					 * [tStatCatcher_1 main ] start
					 */

					currentComponent = "tStatCatcher_1";

					tos_count_tStatCatcher_1++;

					/**
					 * [tStatCatcher_1 main ] stop
					 */

					/**
					 * [tReplicate_1 main ] start
					 */

					currentComponent = "tReplicate_1";

					// row2
					// row2

					if (execStat) {
						runStat.updateStatOnConnection("row2" + iterateId, 1, 1);
					}

					if (log.isTraceEnabled()) {
						log.trace("row2 - "
								+ (row2 == null ? "" : row2.toLogString()));
					}

					row3 = new row3Struct();

					row3.moment = row2.moment;
					row3.pid = row2.pid;
					row3.father_pid = row2.father_pid;
					row3.root_pid = row2.root_pid;
					row3.system_pid = row2.system_pid;
					row3.project = row2.project;
					row3.job = row2.job;
					row3.job_repository_id = row2.job_repository_id;
					row3.job_version = row2.job_version;
					row3.context = row2.context;
					row3.origin = row2.origin;
					row3.message_type = row2.message_type;
					row3.message = row2.message;
					row3.duration = row2.duration;
					row4 = new row4Struct();

					row4.moment = row2.moment;
					row4.pid = row2.pid;
					row4.father_pid = row2.father_pid;
					row4.root_pid = row2.root_pid;
					row4.system_pid = row2.system_pid;
					row4.project = row2.project;
					row4.job = row2.job;
					row4.job_repository_id = row2.job_repository_id;
					row4.job_version = row2.job_version;
					row4.context = row2.context;
					row4.origin = row2.origin;
					row4.message_type = row2.message_type;
					row4.message = row2.message;
					row4.duration = row2.duration;

					tos_count_tReplicate_1++;

					/**
					 * [tReplicate_1 main ] stop
					 */

					/**
					 * [tMSSqlOutput_1 main ] start
					 */

					currentComponent = "tMSSqlOutput_1";

					// row3
					// row3

					if (execStat) {
						runStat.updateStatOnConnection("row3" + iterateId, 1, 1);
					}

					if (log.isTraceEnabled()) {
						log.trace("row3 - "
								+ (row3 == null ? "" : row3.toLogString()));
					}

					whetherReject_tMSSqlOutput_1 = false;
					if (row3.moment != null) {
						pstmt_tMSSqlOutput_1.setTimestamp(1,
								new java.sql.Timestamp(row3.moment.getTime()));
					} else {
						pstmt_tMSSqlOutput_1.setNull(1, java.sql.Types.DATE);
					}

					if (row3.pid == null) {
						pstmt_tMSSqlOutput_1.setNull(2, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(2, row3.pid);
					}

					if (row3.father_pid == null) {
						pstmt_tMSSqlOutput_1.setNull(3, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(3, row3.father_pid);
					}

					if (row3.root_pid == null) {
						pstmt_tMSSqlOutput_1.setNull(4, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(4, row3.root_pid);
					}

					if (row3.system_pid == null) {
						pstmt_tMSSqlOutput_1.setNull(5, java.sql.Types.INTEGER);
					} else {
						pstmt_tMSSqlOutput_1.setLong(5, row3.system_pid);
					}

					if (row3.project == null) {
						pstmt_tMSSqlOutput_1.setNull(6, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(6, row3.project);
					}

					if (row3.job == null) {
						pstmt_tMSSqlOutput_1.setNull(7, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(7, row3.job);
					}

					if (row3.job_repository_id == null) {
						pstmt_tMSSqlOutput_1.setNull(8, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(8,
								row3.job_repository_id);
					}

					if (row3.job_version == null) {
						pstmt_tMSSqlOutput_1.setNull(9, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(9, row3.job_version);
					}

					if (row3.context == null) {
						pstmt_tMSSqlOutput_1
								.setNull(10, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(10, row3.context);
					}

					if (row3.origin == null) {
						pstmt_tMSSqlOutput_1
								.setNull(11, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(11, row3.origin);
					}

					if (row3.message_type == null) {
						pstmt_tMSSqlOutput_1
								.setNull(12, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(12, row3.message_type);
					}

					if (row3.message == null) {
						pstmt_tMSSqlOutput_1
								.setNull(13, java.sql.Types.VARCHAR);
					} else {
						pstmt_tMSSqlOutput_1.setString(13, row3.message);
					}

					if (row3.duration == null) {
						pstmt_tMSSqlOutput_1
								.setNull(14, java.sql.Types.INTEGER);
					} else {
						pstmt_tMSSqlOutput_1.setLong(14, row3.duration);
					}

					pstmt_tMSSqlOutput_1.addBatch();
					nb_line_tMSSqlOutput_1++;

					if (log.isDebugEnabled())
						log.debug("tMSSqlOutput_1 - " + ("Adding the record ")
								+ (nb_line_tMSSqlOutput_1) + (" to the ")
								+ ("INSERT") + (" batch."));
					batchSizeCounter_tMSSqlOutput_1++;

					// ////////batch execute by batch size///////
					class LimitBytesHelper_tMSSqlOutput_1 {
						public int limitBytePart1(int counter,
								java.sql.PreparedStatement pstmt_tMSSqlOutput_1)
								throws Exception {
							try {

								if (log.isDebugEnabled())
									log.debug("tMSSqlOutput_1 - "
											+ ("Executing the ") + ("INSERT")
											+ (" batch."));
								for (int countEach_tMSSqlOutput_1 : pstmt_tMSSqlOutput_1
										.executeBatch()) {
									if (countEach_tMSSqlOutput_1 == -2
											|| countEach_tMSSqlOutput_1 == -3) {
										break;
									}
									counter += countEach_tMSSqlOutput_1;
								}

								if (log.isDebugEnabled())
									log.debug("tMSSqlOutput_1 - "
											+ ("The ")
											+ ("INSERT")
											+ (" batch execution has succeeded."));
							} catch (java.sql.BatchUpdateException e) {

								int countSum_tMSSqlOutput_1 = 0;
								for (int countEach_tMSSqlOutput_1 : e
										.getUpdateCounts()) {
									counter += (countEach_tMSSqlOutput_1 < 0 ? 0
											: countEach_tMSSqlOutput_1);
								}

								log.error("tMSSqlOutput_1 - "
										+ (e.getMessage()));
								System.err.println(e.getMessage());

							}
							return counter;
						}

						public int limitBytePart2(int counter,
								java.sql.PreparedStatement pstmt_tMSSqlOutput_1)
								throws Exception {
							try {

								if (log.isDebugEnabled())
									log.debug("tMSSqlOutput_1 - "
											+ ("Executing the ") + ("INSERT")
											+ (" batch."));
								for (int countEach_tMSSqlOutput_1 : pstmt_tMSSqlOutput_1
										.executeBatch()) {
									if (countEach_tMSSqlOutput_1 == -2
											|| countEach_tMSSqlOutput_1 == -3) {
										break;
									}
									counter += countEach_tMSSqlOutput_1;
								}

								if (log.isDebugEnabled())
									log.debug("tMSSqlOutput_1 - "
											+ ("The ")
											+ ("INSERT")
											+ (" batch execution has succeeded."));
							} catch (java.sql.BatchUpdateException e) {

								for (int countEach_tMSSqlOutput_1 : e
										.getUpdateCounts()) {
									counter += (countEach_tMSSqlOutput_1 < 0 ? 0
											: countEach_tMSSqlOutput_1);
								}

								log.error("tMSSqlOutput_1 - "
										+ (e.getMessage()));
								System.err.println(e.getMessage());

							}
							return counter;
						}
					}
					if ((batchSize_tMSSqlOutput_1 > 0)
							&& (batchSize_tMSSqlOutput_1 <= batchSizeCounter_tMSSqlOutput_1)) {

						insertedCount_tMSSqlOutput_1 = new LimitBytesHelper_tMSSqlOutput_1()
								.limitBytePart1(insertedCount_tMSSqlOutput_1,
										pstmt_tMSSqlOutput_1);

						batchSizeCounter_tMSSqlOutput_1 = 0;
					}

					// //////////commit every////////////

					commitCounter_tMSSqlOutput_1++;
					if (commitEvery_tMSSqlOutput_1 <= commitCounter_tMSSqlOutput_1) {
						if ((batchSize_tMSSqlOutput_1 > 0)
								&& (batchSizeCounter_tMSSqlOutput_1 > 0)) {

							insertedCount_tMSSqlOutput_1 = new LimitBytesHelper_tMSSqlOutput_1()
									.limitBytePart1(
											insertedCount_tMSSqlOutput_1,
											pstmt_tMSSqlOutput_1);

							batchSizeCounter_tMSSqlOutput_1 = 0;
						}

						if (log.isDebugEnabled())
							log.debug("tMSSqlOutput_1 - "
									+ ("Connection starting to commit ")
									+ (commitCounter_tMSSqlOutput_1)
									+ (" record(s)."));
						conn_tMSSqlOutput_1.commit();

						if (log.isDebugEnabled())
							log.debug("tMSSqlOutput_1 - "
									+ ("Connection commit has succeeded."));
						commitCounter_tMSSqlOutput_1 = 0;
					}

					tos_count_tMSSqlOutput_1++;

					/**
					 * [tMSSqlOutput_1 main ] stop
					 */

					/**
					 * [tLogRow_1 main ] start
					 */

					currentComponent = "tLogRow_1";

					// row4
					// row4

					if (execStat) {
						runStat.updateStatOnConnection("row4" + iterateId, 1, 1);
					}

					if (log.isTraceEnabled()) {
						log.trace("row4 - "
								+ (row4 == null ? "" : row4.toLogString()));
					}

					// /////////////////////

					strBuffer_tLogRow_1 = new StringBuilder();

					if (row4.moment != null) { //

						strBuffer_tLogRow_1.append(FormatterUtils.format_Date(
								row4.moment, "yyyy-MM-dd HH:mm:ss"));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.pid != null) { //

						strBuffer_tLogRow_1.append(String.valueOf(row4.pid));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.father_pid != null) { //

						strBuffer_tLogRow_1.append(String
								.valueOf(row4.father_pid));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.root_pid != null) { //

						strBuffer_tLogRow_1.append(String
								.valueOf(row4.root_pid));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.system_pid != null) { //

						strBuffer_tLogRow_1.append(String
								.valueOf(row4.system_pid));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.project != null) { //

						strBuffer_tLogRow_1
								.append(String.valueOf(row4.project));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.job != null) { //

						strBuffer_tLogRow_1.append(String.valueOf(row4.job));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.job_repository_id != null) { //

						strBuffer_tLogRow_1.append(String
								.valueOf(row4.job_repository_id));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.job_version != null) { //

						strBuffer_tLogRow_1.append(String
								.valueOf(row4.job_version));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.context != null) { //

						strBuffer_tLogRow_1
								.append(String.valueOf(row4.context));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.origin != null) { //

						strBuffer_tLogRow_1.append(String.valueOf(row4.origin));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.message_type != null) { //

						strBuffer_tLogRow_1.append(String
								.valueOf(row4.message_type));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.message != null) { //

						strBuffer_tLogRow_1
								.append(String.valueOf(row4.message));

					} //

					strBuffer_tLogRow_1.append("|");

					if (row4.duration != null) { //

						strBuffer_tLogRow_1.append(String
								.valueOf(row4.duration));

					} //

					if (globalMap.get("tLogRow_CONSOLE") != null) {
						consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap
								.get("tLogRow_CONSOLE");
					} else {
						consoleOut_tLogRow_1 = new java.io.PrintStream(
								new java.io.BufferedOutputStream(System.out));
						globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
					}
					log.info("tLogRow_1 - Content of row "
							+ (nb_line_tLogRow_1 + 1) + ": "
							+ strBuffer_tLogRow_1.toString());
					consoleOut_tLogRow_1
							.println(strBuffer_tLogRow_1.toString());
					consoleOut_tLogRow_1.flush();
					nb_line_tLogRow_1++;
					// ////

					// ////

					// /////////////////////

					tos_count_tLogRow_1++;

					/**
					 * [tLogRow_1 main ] stop
					 */

					/**
					 * [tStatCatcher_1 end ] start
					 */

					currentComponent = "tStatCatcher_1";

				}

				if (log.isDebugEnabled())
					log.debug("tStatCatcher_1 - " + ("Done."));

				ok_Hash.put("tStatCatcher_1", true);
				end_Hash.put("tStatCatcher_1", System.currentTimeMillis());

				/**
				 * [tStatCatcher_1 end ] stop
				 */

				/**
				 * [tReplicate_1 end ] start
				 */

				currentComponent = "tReplicate_1";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("row2" + iterateId, 2, 0);
					}
				}

				if (log.isDebugEnabled())
					log.debug("tReplicate_1 - " + ("Done."));

				ok_Hash.put("tReplicate_1", true);
				end_Hash.put("tReplicate_1", System.currentTimeMillis());

				/**
				 * [tReplicate_1 end ] stop
				 */

				/**
				 * [tMSSqlOutput_1 end ] start
				 */

				currentComponent = "tMSSqlOutput_1";

				try {
					int countSum_tMSSqlOutput_1 = 0;
					if (pstmt_tMSSqlOutput_1 != null
							&& batchSizeCounter_tMSSqlOutput_1 > 0) {

						if (log.isDebugEnabled())
							log.debug("tMSSqlOutput_1 - " + ("Executing the ")
									+ ("INSERT") + (" batch."));
						for (int countEach_tMSSqlOutput_1 : pstmt_tMSSqlOutput_1
								.executeBatch()) {
							if (countEach_tMSSqlOutput_1 == -2
									|| countEach_tMSSqlOutput_1 == -3) {
								break;
							}
							countSum_tMSSqlOutput_1 += countEach_tMSSqlOutput_1;
						}

						if (log.isDebugEnabled())
							log.debug("tMSSqlOutput_1 - " + ("The ")
									+ ("INSERT")
									+ (" batch execution has succeeded."));
					}

					insertedCount_tMSSqlOutput_1 += countSum_tMSSqlOutput_1;

				} catch (java.sql.BatchUpdateException e) {

					int countSum_tMSSqlOutput_1 = 0;
					for (int countEach_tMSSqlOutput_1 : e.getUpdateCounts()) {
						countSum_tMSSqlOutput_1 += (countEach_tMSSqlOutput_1 < 0 ? 0
								: countEach_tMSSqlOutput_1);
					}

					insertedCount_tMSSqlOutput_1 += countSum_tMSSqlOutput_1;

					log.error("tMSSqlOutput_1 - " + (e.getMessage()));
					System.err.println(e.getMessage());

				}
				if (pstmt_tMSSqlOutput_1 != null) {

					pstmt_tMSSqlOutput_1.close();

				}

				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - "
							+ ("Connection starting to commit ")
							+ (commitCounter_tMSSqlOutput_1) + (" record(s)."));
				conn_tMSSqlOutput_1.commit();

				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - "
							+ ("Connection commit has succeeded."));
				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - "
							+ ("Closing the connection to the database."));
				conn_tMSSqlOutput_1.close();
				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - "
							+ ("Connection to the database has closed."));
				resourceMap.put("finish_tMSSqlOutput_1", true);

				nb_line_deleted_tMSSqlOutput_1 = nb_line_deleted_tMSSqlOutput_1
						+ deletedCount_tMSSqlOutput_1;
				nb_line_update_tMSSqlOutput_1 = nb_line_update_tMSSqlOutput_1
						+ updatedCount_tMSSqlOutput_1;
				nb_line_inserted_tMSSqlOutput_1 = nb_line_inserted_tMSSqlOutput_1
						+ insertedCount_tMSSqlOutput_1;
				nb_line_rejected_tMSSqlOutput_1 = nb_line_rejected_tMSSqlOutput_1
						+ rejectedCount_tMSSqlOutput_1;

				globalMap.put("tMSSqlOutput_1_NB_LINE", nb_line_tMSSqlOutput_1);
				globalMap.put("tMSSqlOutput_1_NB_LINE_UPDATED",
						nb_line_update_tMSSqlOutput_1);
				globalMap.put("tMSSqlOutput_1_NB_LINE_INSERTED",
						nb_line_inserted_tMSSqlOutput_1);
				globalMap.put("tMSSqlOutput_1_NB_LINE_DELETED",
						nb_line_deleted_tMSSqlOutput_1);
				globalMap.put("tMSSqlOutput_1_NB_LINE_REJECTED",
						nb_line_rejected_tMSSqlOutput_1);

				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - " + ("Has ") + ("inserted")
							+ (" ") + (nb_line_inserted_tMSSqlOutput_1)
							+ (" record(s)."));

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("row3" + iterateId, 2, 0);
					}
				}

				if (log.isDebugEnabled())
					log.debug("tMSSqlOutput_1 - " + ("Done."));

				ok_Hash.put("tMSSqlOutput_1", true);
				end_Hash.put("tMSSqlOutput_1", System.currentTimeMillis());

				/**
				 * [tMSSqlOutput_1 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				currentComponent = "tLogRow_1";

				// ////
				// ////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);
				if (log.isInfoEnabled())
					log.info("tLogRow_1 - " + ("Printed row count: ")
							+ (nb_line_tLogRow_1) + ("."));

				// /////////////////////

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("row4" + iterateId, 2, 0);
					}
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Done."));

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

			}// end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent,
					globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tStatCatcher_1 finally ] start
				 */

				currentComponent = "tStatCatcher_1";

				/**
				 * [tStatCatcher_1 finally ] stop
				 */

				/**
				 * [tReplicate_1 finally ] start
				 */

				currentComponent = "tReplicate_1";

				/**
				 * [tReplicate_1 finally ] stop
				 */

				/**
				 * [tMSSqlOutput_1 finally ] start
				 */

				currentComponent = "tMSSqlOutput_1";

				if (resourceMap.get("finish_tMSSqlOutput_1") == null) {
					if (resourceMap.get("conn_tMSSqlOutput_1") != null) {
						try {

							if (log.isDebugEnabled())
								log.debug("tMSSqlOutput_1 - "
										+ ("Closing the connection to the database."));

							java.sql.Connection ctn_tMSSqlOutput_1 = (java.sql.Connection) resourceMap
									.get("conn_tMSSqlOutput_1");

							ctn_tMSSqlOutput_1.close();

							if (log.isDebugEnabled())
								log.debug("tMSSqlOutput_1 - "
										+ ("Connection to the database has closed."));
						} catch (java.sql.SQLException sqlEx_tMSSqlOutput_1) {
							String errorMessage_tMSSqlOutput_1 = "failed to close the connection in tMSSqlOutput_1 :"
									+ sqlEx_tMSSqlOutput_1.getMessage();

							log.error("tMSSqlOutput_1 - "
									+ (errorMessage_tMSSqlOutput_1));
							System.err.println(errorMessage_tMSSqlOutput_1);
						}
					}
				}

				/**
				 * [tMSSqlOutput_1 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				currentComponent = "tLogRow_1";

				/**
				 * [tLogRow_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tStatCatcher_1_SUBPROCESS_STATE", 1);
	}

	public static class row_talendStats_STATSStruct implements
			routines.system.IPersistableRow<row_talendStats_STATSStruct> {
		final static byte[] commonByteArrayLock_LOCAL_PROJECT_tstatcatcher = new byte[0];
		static byte[] commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		private java.util.Date readDate(ObjectInputStream dis)
				throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos)
				throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_LOCAL_PROJECT_tstatcatcher.length) {
					if (length < 1024
							&& commonByteArray_LOCAL_PROJECT_tstatcatcher.length == 0) {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[1024];
					} else {
						commonByteArray_LOCAL_PROJECT_tstatcatcher = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_LOCAL_PROJECT_tstatcatcher, 0,
						length);
				strReturn = new String(
						commonByteArray_LOCAL_PROJECT_tstatcatcher, 0, length,
						utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos)
				throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_LOCAL_PROJECT_tstatcatcher) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (moment == null) {
				sb.append("<null>");
			} else {
				sb.append(moment);
			}

			sb.append("|");

			if (pid == null) {
				sb.append("<null>");
			} else {
				sb.append(pid);
			}

			sb.append("|");

			if (father_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(father_pid);
			}

			sb.append("|");

			if (root_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(root_pid);
			}

			sb.append("|");

			if (system_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(system_pid);
			}

			sb.append("|");

			if (project == null) {
				sb.append("<null>");
			} else {
				sb.append(project);
			}

			sb.append("|");

			if (job == null) {
				sb.append("<null>");
			} else {
				sb.append(job);
			}

			sb.append("|");

			if (job_repository_id == null) {
				sb.append("<null>");
			} else {
				sb.append(job_repository_id);
			}

			sb.append("|");

			if (job_version == null) {
				sb.append("<null>");
			} else {
				sb.append(job_version);
			}

			sb.append("|");

			if (context == null) {
				sb.append("<null>");
			} else {
				sb.append(context);
			}

			sb.append("|");

			if (origin == null) {
				sb.append("<null>");
			} else {
				sb.append(origin);
			}

			sb.append("|");

			if (message_type == null) {
				sb.append("<null>");
			} else {
				sb.append(message_type);
			}

			sb.append("|");

			if (message == null) {
				sb.append("<null>");
			} else {
				sb.append(message);
			}

			sb.append("|");

			if (duration == null) {
				sb.append("<null>");
			} else {
				sb.append(duration);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_talendStats_STATSStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(),
						object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void talendStats_STATSProcess(
			final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("talendStats_STATS_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;
		String currentVirtualComponent = null;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {

			String currentMethodName = new java.lang.Exception()
					.getStackTrace()[0].getMethodName();
			boolean resumeIt = currentMethodName.equals(resumeEntryMethodName);
			if (resumeEntryMethodName == null || resumeIt || globalResumeTicket) {// start
																					// the
																					// resume
				globalResumeTicket = true;

				row_talendStats_STATSStruct row_talendStats_STATS = new row_talendStats_STATSStruct();

				/**
				 * [talendStats_CONSOLE begin ] start
				 */

				ok_Hash.put("talendStats_CONSOLE", false);
				start_Hash.put("talendStats_CONSOLE",
						System.currentTimeMillis());

				currentVirtualComponent = "talendStats_CONSOLE";

				currentComponent = "talendStats_CONSOLE";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("Main" + iterateId, 0, 0);

					}
				}

				int tos_count_talendStats_CONSOLE = 0;

				if (log.isDebugEnabled())
					log.debug("talendStats_CONSOLE - " + ("Start to work."));
				class BytesLimit65535_talendStats_CONSOLE {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_talendStats_CONSOLE = new StringBuilder();
						log4jParamters_talendStats_CONSOLE
								.append("Parameters:");
						log4jParamters_talendStats_CONSOLE.append("BASIC_MODE"
								+ " = " + "true");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						log4jParamters_talendStats_CONSOLE.append("TABLE_PRINT"
								+ " = " + "false");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						log4jParamters_talendStats_CONSOLE.append("VERTICAL"
								+ " = " + "false");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						log4jParamters_talendStats_CONSOLE
								.append("FIELDSEPARATOR" + " = " + "\"|\"");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						log4jParamters_talendStats_CONSOLE
								.append("PRINT_HEADER" + " = " + "false");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						log4jParamters_talendStats_CONSOLE
								.append("PRINT_UNIQUE_NAME" + " = " + "false");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						log4jParamters_talendStats_CONSOLE
								.append("PRINT_COLNAMES" + " = " + "false");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						log4jParamters_talendStats_CONSOLE
								.append("USE_FIXED_LENGTH" + " = " + "false");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						log4jParamters_talendStats_CONSOLE
								.append("PRINT_CONTENT_WITH_LOG4J" + " = "
										+ "true");
						log4jParamters_talendStats_CONSOLE.append(" | ");
						if (log.isDebugEnabled())
							log.debug("talendStats_CONSOLE - "
									+ (log4jParamters_talendStats_CONSOLE));
					}
				}

				new BytesLimit65535_talendStats_CONSOLE().limitLog4jByte();

				// /////////////////////

				final String OUTPUT_FIELD_SEPARATOR_talendStats_CONSOLE = "|";
				java.io.PrintStream consoleOut_talendStats_CONSOLE = null;

				StringBuilder strBuffer_talendStats_CONSOLE = null;
				int nb_line_talendStats_CONSOLE = 0;
				// /////////////////////

				/**
				 * [talendStats_CONSOLE begin ] stop
				 */

				/**
				 * [talendStats_STATS begin ] start
				 */

				ok_Hash.put("talendStats_STATS", false);
				start_Hash.put("talendStats_STATS", System.currentTimeMillis());

				currentVirtualComponent = "talendStats_STATS";

				currentComponent = "talendStats_STATS";

				int tos_count_talendStats_STATS = 0;

				if (log.isDebugEnabled())
					log.debug("talendStats_STATS - " + ("Start to work."));
				class BytesLimit65535_talendStats_STATS {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_talendStats_STATS = new StringBuilder();
						log4jParamters_talendStats_STATS.append("Parameters:");
						if (log.isDebugEnabled())
							log.debug("talendStats_STATS - "
									+ (log4jParamters_talendStats_STATS));
					}
				}

				new BytesLimit65535_talendStats_STATS().limitLog4jByte();

				for (StatCatcherUtils.StatCatcherMessage scm : talendStats_STATS
						.getMessages()) {
					row_talendStats_STATS.pid = pid;
					row_talendStats_STATS.root_pid = rootPid;
					row_talendStats_STATS.father_pid = fatherPid;
					row_talendStats_STATS.project = projectName;
					row_talendStats_STATS.job = jobName;
					row_talendStats_STATS.context = contextStr;
					row_talendStats_STATS.origin = (scm.getOrigin() == null
							|| scm.getOrigin().length() < 1 ? null : scm
							.getOrigin());
					row_talendStats_STATS.message = scm.getMessage();
					row_talendStats_STATS.duration = scm.getDuration();
					row_talendStats_STATS.moment = scm.getMoment();
					row_talendStats_STATS.message_type = scm.getMessageType();
					row_talendStats_STATS.job_version = scm.getJobVersion();
					row_talendStats_STATS.job_repository_id = scm.getJobId();
					row_talendStats_STATS.system_pid = scm.getSystemPid();

					/**
					 * [talendStats_STATS begin ] stop
					 */

					/**
					 * [talendStats_STATS main ] start
					 */

					currentVirtualComponent = "talendStats_STATS";

					currentComponent = "talendStats_STATS";

					tos_count_talendStats_STATS++;

					/**
					 * [talendStats_STATS main ] stop
					 */

					/**
					 * [talendStats_CONSOLE main ] start
					 */

					currentVirtualComponent = "talendStats_CONSOLE";

					currentComponent = "talendStats_CONSOLE";

					// Main
					// row_talendStats_STATS

					if (execStat) {
						runStat.updateStatOnConnection("Main" + iterateId, 1, 1);
					}

					// /////////////////////

					strBuffer_talendStats_CONSOLE = new StringBuilder();

					if (row_talendStats_STATS.moment != null) { //

						strBuffer_talendStats_CONSOLE.append(FormatterUtils
								.format_Date(row_talendStats_STATS.moment,
										"yyyy-MM-dd HH:mm:ss"));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.pid != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.pid));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.father_pid != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.father_pid));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.root_pid != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.root_pid));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.system_pid != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.system_pid));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.project != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.project));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.job != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.job));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.job_repository_id != null) { //

						strBuffer_talendStats_CONSOLE
								.append(String
										.valueOf(row_talendStats_STATS.job_repository_id));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.job_version != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.job_version));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.context != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.context));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.origin != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.origin));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.message_type != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.message_type));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.message != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.message));

					} //

					strBuffer_talendStats_CONSOLE.append("|");

					if (row_talendStats_STATS.duration != null) { //

						strBuffer_talendStats_CONSOLE.append(String
								.valueOf(row_talendStats_STATS.duration));

					} //

					if (globalMap.get("tLogRow_CONSOLE") != null) {
						consoleOut_talendStats_CONSOLE = (java.io.PrintStream) globalMap
								.get("tLogRow_CONSOLE");
					} else {
						consoleOut_talendStats_CONSOLE = new java.io.PrintStream(
								new java.io.BufferedOutputStream(System.out));
						globalMap.put("tLogRow_CONSOLE",
								consoleOut_talendStats_CONSOLE);
					}
					log.info("talendStats_CONSOLE - Content of row "
							+ (nb_line_talendStats_CONSOLE + 1) + ": "
							+ strBuffer_talendStats_CONSOLE.toString());
					consoleOut_talendStats_CONSOLE
							.println(strBuffer_talendStats_CONSOLE.toString());
					consoleOut_talendStats_CONSOLE.flush();
					nb_line_talendStats_CONSOLE++;
					// ////

					// ////

					// /////////////////////

					tos_count_talendStats_CONSOLE++;

					/**
					 * [talendStats_CONSOLE main ] stop
					 */

					/**
					 * [talendStats_STATS end ] start
					 */

					currentVirtualComponent = "talendStats_STATS";

					currentComponent = "talendStats_STATS";

				}

				if (log.isDebugEnabled())
					log.debug("talendStats_STATS - " + ("Done."));

				ok_Hash.put("talendStats_STATS", true);
				end_Hash.put("talendStats_STATS", System.currentTimeMillis());

				/**
				 * [talendStats_STATS end ] stop
				 */

				/**
				 * [talendStats_CONSOLE end ] start
				 */

				currentVirtualComponent = "talendStats_CONSOLE";

				currentComponent = "talendStats_CONSOLE";

				// ////
				// ////
				globalMap.put("talendStats_CONSOLE_NB_LINE",
						nb_line_talendStats_CONSOLE);
				if (log.isInfoEnabled())
					log.info("talendStats_CONSOLE - " + ("Printed row count: ")
							+ (nb_line_talendStats_CONSOLE) + ("."));

				// /////////////////////

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("Main" + iterateId, 2, 0);
					}
				}

				if (log.isDebugEnabled())
					log.debug("talendStats_CONSOLE - " + ("Done."));

				ok_Hash.put("talendStats_CONSOLE", true);
				end_Hash.put("talendStats_CONSOLE", System.currentTimeMillis());

				/**
				 * [talendStats_CONSOLE end ] stop
				 */

			}// end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent,
					globalMap);

			te.setVirtualComponentName(currentVirtualComponent);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendStats_STATS finally ] start
				 */

				currentVirtualComponent = "talendStats_STATS";

				currentComponent = "talendStats_STATS";

				/**
				 * [talendStats_STATS finally ] stop
				 */

				/**
				 * [talendStats_CONSOLE finally ] start
				 */

				currentVirtualComponent = "talendStats_CONSOLE";

				currentComponent = "talendStats_CONSOLE";

				/**
				 * [talendStats_CONSOLE finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendStats_STATS_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	private PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final tstatcatcher tstatcatcherClass = new tstatcatcher();

		int exitCode = tstatcatcherClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'tstatcatcher' - Done.");
		}

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}

		if (!"".equals(log4jLevel)) {
			if ("trace".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				log.setLevel(org.apache.log4j.Level.OFF);
			}
			org.apache.log4j.Logger.getRootLogger().setLevel(log.getLevel());
		}
		log.info("TalendJob: 'tstatcatcher' - Start.");

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket
				// can't open
				System.err.println("The statistics socket port " + portStats
						+ " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}

		try {
			// call job/subjob with an existing context, like:
			// --context=production. if without this parameter, there will use
			// the default context instead.
			java.io.InputStream inContext = tstatcatcher.class.getClassLoader()
					.getResourceAsStream(
							"local_project/tstatcatcher_0_1/contexts/"
									+ contextStr + ".properties");
			if (isDefaultContext && inContext == null) {

			} else {
				if (inContext != null) {
					// defaultProps is in order to keep the original context
					// value
					defaultProps.load(inContext);
					inContext.close();
					context = new ContextProperties(defaultProps);
				} else {
					// print info and job continue to run, for case:
					// context_param is not empty.
					System.err.println("Could not find the context "
							+ contextStr);
				}
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param
							.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil
				.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName,
				jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName,
				parent_part_launcher, Thread.currentThread().getId() + "", "",
				"", "", "",
				resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();
		tStatCatcher_1.addMessage("begin");
		talendStats_STATS.addMessage("begin");

		this.globalResumeTicket = true;// to run tPreJob

		try {
			tStatCatcher_1Process(globalMap);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		try {
			talendStats_STATSProcess(globalMap);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tRowGenerator_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tRowGenerator_1) {
			globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", -1);

			e_tRowGenerator_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory)
					+ " bytes memory increase when running : tstatcatcher");
		}
		tStatCatcher_1.addMessage(status == "" ? "end" : status,
				(end - startTime));
		try {
			tStatCatcher_1Process(globalMap);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		talendStats_STATS.addMessage(status == "" ? "end" : status,
				(end - startTime));
		try {
			talendStats_STATSProcess(globalMap);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;
		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher,
				Thread.currentThread().getId() + "", "", "" + returnCode, "",
				"", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index),
							keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index),
							keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		}

	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" },
			{ "\\'", "\'" }, { "\\r", "\r" }, { "\\f", "\f" }, { "\\b", "\b" },
			{ "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex,
							index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left
			// into the result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 155691 characters generated by Talend Data Integration on the August 14, 2018
 * 4:57:03 PM IST
 ************************************************************************************************/
