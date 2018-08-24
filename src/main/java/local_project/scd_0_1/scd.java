package local_project.scd_0_1;

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
 * Job: scd Purpose: <br>
 * Description:  <br>
 * @author user@talend.com
 * @version 6.5.1.20180116_1512
 * @status 
 */
public class scd implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "scd.log");
	}
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(scd.class);

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
	private final String jobName = "scd";
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
					scd.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass()
							.getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(scd.this, new Object[] { e,
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

	public void tFileInputDelimited_1_error(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent,
				globalMap);
	}

	public void tMSSqlSCD_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent,
				globalMap);
	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception,
			String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread
				.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(),
				ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row1Struct implements
			routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_LOCAL_PROJECT_scd = new byte[0];
		static byte[] commonByteArray_LOCAL_PROJECT_scd = new byte[0];

		public String Postal;

		public String getPostal() {
			return this.Postal;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Capital;

		public String getCapital() {
			return this.Capital;
		}

		public String MostPopulousCity;

		public String getMostPopulousCity() {
			return this.MostPopulousCity;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_LOCAL_PROJECT_scd.length) {
					if (length < 1024
							&& commonByteArray_LOCAL_PROJECT_scd.length == 0) {
						commonByteArray_LOCAL_PROJECT_scd = new byte[1024];
					} else {
						commonByteArray_LOCAL_PROJECT_scd = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_LOCAL_PROJECT_scd, 0, length);
				strReturn = new String(commonByteArray_LOCAL_PROJECT_scd, 0,
						length, utf8Charset);
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

			synchronized (commonByteArrayLock_LOCAL_PROJECT_scd) {

				try {

					int length = 0;

					this.Postal = readString(dis);

					this.State = readString(dis);

					this.Capital = readString(dis);

					this.MostPopulousCity = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Postal, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Capital, dos);

				// String

				writeString(this.MostPopulousCity, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Postal=" + Postal);
			sb.append(",State=" + State);
			sb.append(",Capital=" + Capital);
			sb.append(",MostPopulousCity=" + MostPopulousCity);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Postal == null) {
				sb.append("<null>");
			} else {
				sb.append(Postal);
			}

			sb.append("|");

			if (State == null) {
				sb.append("<null>");
			} else {
				sb.append(State);
			}

			sb.append("|");

			if (Capital == null) {
				sb.append("<null>");
			} else {
				sb.append(Capital);
			}

			sb.append("|");

			if (MostPopulousCity == null) {
				sb.append("<null>");
			} else {
				sb.append(MostPopulousCity);
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

	public void tFileInputDelimited_1Process(
			final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

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
				 * [tMSSqlSCD_1 begin ] start
				 */

				ok_Hash.put("tMSSqlSCD_1", false);
				start_Hash.put("tMSSqlSCD_1", System.currentTimeMillis());

				currentComponent = "tMSSqlSCD_1";

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null) {

						runStat.updateStatOnConnection("row1" + iterateId, 0, 0);

					}
				}

				int tos_count_tMSSqlSCD_1 = 0;

				class BytesLimit65535_tMSSqlSCD_1 {
					public void limitLog4jByte() throws Exception {

					}
				}

				new BytesLimit65535_tMSSqlSCD_1().limitLog4jByte();

				class SCDSK_tMSSqlSCD_1 {
					private int hashCode;
					public boolean hashCodeDirty = true;
					String Postal;

					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final SCDSK_tMSSqlSCD_1 other = (SCDSK_tMSSqlSCD_1) obj;
						if (this.Postal == null) {
							if (other.Postal != null)
								return false;
						} else if (!this.Postal.equals(other.Postal))
							return false;

						return true;
					}

					public int hashCode() {
						if (hashCodeDirty) {
							int prime = 31;
							hashCode = prime * hashCode
									+ (Postal == null ? 0 : Postal.hashCode());
							hashCodeDirty = false;
						}
						return hashCode;
					}
				}

				class SCDStruct_tMSSqlSCD_1 {
					private String Capital;
					private String MostPopulousCity;
					private String previous_MostPopulousCity;
				}

				int nb_line_update_tMSSqlSCD_1 = 0;
				int nb_line_inserted_tMSSqlSCD_1 = 0;
				int nb_line_rejected_tMSSqlSCD_1 = 0;
				String tableName_tMSSqlSCD_1 = null;
				String dbschema_tMSSqlSCD_1 = null;
				java.sql.Connection conn_tMSSqlSCD_1 = null;
				String dbUser_tMSSqlSCD_1 = null;
				dbschema_tMSSqlSCD_1 = "dbo";
				String driverClass_tMSSqlSCD_1 = "net.sourceforge.jtds.jdbc.Driver";

				java.lang.Class.forName(driverClass_tMSSqlSCD_1);
				String port_tMSSqlSCD_1 = "1433";
				String dbname_tMSSqlSCD_1 = "CITI";
				String url_tMSSqlSCD_1 = "jdbc:jtds:sqlserver://" + "localhost";
				if (!"".equals(port_tMSSqlSCD_1)) {
					url_tMSSqlSCD_1 += ":" + "1433";
				}
				if (!"".equals(dbname_tMSSqlSCD_1)) {
					url_tMSSqlSCD_1 += "//" + "CITI";

				}
				url_tMSSqlSCD_1 += ";appName=" + projectName + ";" + "";
				dbUser_tMSSqlSCD_1 = "sa";

				final String decryptedPassword_tMSSqlSCD_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("7412caae74206e73");

				String dbPwd_tMSSqlSCD_1 = decryptedPassword_tMSSqlSCD_1;
				conn_tMSSqlSCD_1 = java.sql.DriverManager.getConnection(
						url_tMSSqlSCD_1, dbUser_tMSSqlSCD_1, dbPwd_tMSSqlSCD_1);

				if (dbschema_tMSSqlSCD_1 == null
						|| dbschema_tMSSqlSCD_1.trim().length() == 0) {
					tableName_tMSSqlSCD_1 = "trial";
				} else {
					tableName_tMSSqlSCD_1 = dbschema_tMSSqlSCD_1 + "].["
							+ "trial";
				}
				org.talend.designer.components.util.mssql.MSSqlGenerateTimestampUtil mssqlGTU_tMSSqlSCD_1 = org.talend.designer.components.util.mssql.MSSqlUtilFactory
						.getMSSqlGenerateTimestampUtil();
				String tmpValue_tMSSqlSCD_1 = null;
				String search_tMSSqlSCD_1 = "SELECT [Postal], [Capital], [MostPopulousCity], [previous_MostPopulousCity] FROM ["
						+ tableName_tMSSqlSCD_1 + "] WHERE [scd_end] IS NULL";
				java.sql.Statement statement_tMSSqlSCD_1 = conn_tMSSqlSCD_1
						.createStatement();
				java.sql.ResultSet resultSet_tMSSqlSCD_1 = statement_tMSSqlSCD_1
						.executeQuery(search_tMSSqlSCD_1);
				java.util.Map<SCDSK_tMSSqlSCD_1, SCDStruct_tMSSqlSCD_1> cache_tMSSqlSCD_1 = new java.util.HashMap<SCDSK_tMSSqlSCD_1, SCDStruct_tMSSqlSCD_1>();
				while (resultSet_tMSSqlSCD_1.next()) {
					SCDSK_tMSSqlSCD_1 sk_tMSSqlSCD_1 = new SCDSK_tMSSqlSCD_1();
					SCDStruct_tMSSqlSCD_1 row_tMSSqlSCD_1 = new SCDStruct_tMSSqlSCD_1();
					if (resultSet_tMSSqlSCD_1.getObject(1) != null) {
						sk_tMSSqlSCD_1.Postal = resultSet_tMSSqlSCD_1
								.getString(1);
					}
					if (resultSet_tMSSqlSCD_1.getObject(2) != null) {
						row_tMSSqlSCD_1.Capital = resultSet_tMSSqlSCD_1
								.getString(2);
					}
					if (resultSet_tMSSqlSCD_1.getObject(3) != null) {
						row_tMSSqlSCD_1.MostPopulousCity = resultSet_tMSSqlSCD_1
								.getString(3);
					}
					if (resultSet_tMSSqlSCD_1.getObject(4) != null) {
						row_tMSSqlSCD_1.previous_MostPopulousCity = resultSet_tMSSqlSCD_1
								.getString(4);
					}
					cache_tMSSqlSCD_1.put(sk_tMSSqlSCD_1, row_tMSSqlSCD_1);
				}
				resultSet_tMSSqlSCD_1.close();
				statement_tMSSqlSCD_1.close();
				int nextSurrogateKey_tMSSqlSCD_1 = 1;
				if (cache_tMSSqlSCD_1.size() > 0) {
					String tmpQuery_tMSSqlSCD_1 = "SELECT MAX([SK_1]) FROM ["
							+ tableName_tMSSqlSCD_1 + "]";
					java.sql.Statement tmpStmt_tMSSqlSCD_1 = conn_tMSSqlSCD_1
							.createStatement();
					java.sql.ResultSet tmpRS_tMSSqlSCD_1 = tmpStmt_tMSSqlSCD_1
							.executeQuery(tmpQuery_tMSSqlSCD_1);
					if (tmpRS_tMSSqlSCD_1.next()
							&& tmpRS_tMSSqlSCD_1.getObject(1) != null) {
						nextSurrogateKey_tMSSqlSCD_1 = tmpRS_tMSSqlSCD_1
								.getInt(1) + 1;
					}
					tmpRS_tMSSqlSCD_1.close();
					tmpStmt_tMSSqlSCD_1.close();
				}
				String insertionSQL_tMSSqlSCD_1 = "INSERT INTO ["
						+ tableName_tMSSqlSCD_1
						+ "]([SK_1], [Postal], [State], [Capital], [scd_start], [scd_end], [MostPopulousCity]) VALUES(?, ?, ?, ?, ?, ?, ?)";
				java.sql.PreparedStatement insertionStatement_tMSSqlSCD_1 = conn_tMSSqlSCD_1
						.prepareStatement(insertionSQL_tMSSqlSCD_1);
				insertionStatement_tMSSqlSCD_1.setTimestamp(5,
						new java.sql.Timestamp(start_Hash.get("tMSSqlSCD_1")));
				insertionStatement_tMSSqlSCD_1.setNull(6, java.sql.Types.DATE);
				String updateSQLForType2_tMSSqlSCD_1 = "UPDATE ["
						+ tableName_tMSSqlSCD_1
						+ "] SET [scd_end] = ? WHERE [Postal] = ? AND [scd_end] IS NULL";
				java.sql.PreparedStatement updateForType2_tMSSqlSCD_1 = conn_tMSSqlSCD_1
						.prepareStatement(updateSQLForType2_tMSSqlSCD_1);
				updateForType2_tMSSqlSCD_1.setTimestamp(1,
						new java.sql.Timestamp(start_Hash.get("tMSSqlSCD_1")));
				String updateSQLForType3_tMSSqlSCD_1 = "UPDATE ["
						+ tableName_tMSSqlSCD_1
						+ "] SET [MostPopulousCity] = ?, [previous_MostPopulousCity] = ? WHERE [Postal] = ? AND [scd_end] IS NULL";
				java.sql.PreparedStatement updateForType3_tMSSqlSCD_1 = conn_tMSSqlSCD_1
						.prepareStatement(updateSQLForType3_tMSSqlSCD_1);

				SCDSK_tMSSqlSCD_1 lookUpKey_tMSSqlSCD_1 = null;
				SCDStruct_tMSSqlSCD_1 lookUpValue_tMSSqlSCD_1 = null;

				/**
				 * [tMSSqlSCD_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1",
						System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Start to work."));
				class BytesLimit65535_tFileInputDelimited_1 {
					public void limitLog4jByte() throws Exception {

						StringBuilder log4jParamters_tFileInputDelimited_1 = new StringBuilder();
						log4jParamters_tFileInputDelimited_1
								.append("Parameters:");
						log4jParamters_tFileInputDelimited_1
								.append("FILENAME"
										+ " = "
										+ "\"C:/Users/Grad57/Downloads/Handover/states.txt\"");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("CSV_OPTION" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("ROWSEPARATOR" + " = " + "\"\\n\"");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("FIELDSEPARATOR" + " = " + "\";\"");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("HEADER"
								+ " = " + "1");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("FOOTER"
								+ " = " + "0");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("LIMIT"
								+ " = " + "");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("REMOVE_EMPTY_ROW" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("UNCOMPRESS" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("DIE_ON_ERROR" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("ADVANCED_SEPARATOR" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("RANDOM"
								+ " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("TRIMALL"
								+ " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("TRIMSELECT" + " = " + "[{TRIM="
										+ ("false") + ", SCHEMA_COLUMN="
										+ ("Postal") + "}, {TRIM=" + ("false")
										+ ", SCHEMA_COLUMN=" + ("State")
										+ "}, {TRIM=" + ("false")
										+ ", SCHEMA_COLUMN=" + ("Capital")
										+ "}, {TRIM=" + ("false")
										+ ", SCHEMA_COLUMN="
										+ ("MostPopulousCity") + "}]");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("CHECK_FIELDS_NUM" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("CHECK_DATE" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1.append("ENCODING"
								+ " = " + "\"US-ASCII\"");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("SPLITRECORD" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						log4jParamters_tFileInputDelimited_1
								.append("ENABLE_DECODE" + " = " + "false");
						log4jParamters_tFileInputDelimited_1.append(" | ");
						if (log.isDebugEnabled())
							log.debug("tFileInputDelimited_1 - "
									+ (log4jParamters_tFileInputDelimited_1));
					}
				}

				new BytesLimit65535_tFileInputDelimited_1().limitLog4jByte();

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_1 = null;
				try {

					Object filename_tFileInputDelimited_1 = "C:/Users/Grad57/Downloads/Handover/states.txt";
					if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_1 = 0, random_value_tFileInputDelimited_1 = -1;
						if (footer_value_tFileInputDelimited_1 > 0
								|| random_value_tFileInputDelimited_1 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_1 = new org.talend.fileprocess.FileInputDelimited(
								"C:/Users/Grad57/Downloads/Handover/states.txt",
								"US-ASCII", ";", "\n", false, 1, 0, -1, -1,
								false);
					} catch (java.lang.Exception e) {

						log.error("tFileInputDelimited_1 - " + e.getMessage());

						System.err.println(e.getMessage());

					}

					log.info("tFileInputDelimited_1 - Retrieving records from the datasource.");

					while (fid_tFileInputDelimited_1 != null
							&& fid_tFileInputDelimited_1.nextRecord()) {
						rowstate_tFileInputDelimited_1.reset();

						row1 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row1 = new row1Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_1 = 0;

							columnIndexWithD_tFileInputDelimited_1 = 0;

							row1.Postal = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 1;

							row1.State = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 2;

							row1.Capital = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 3;

							row1.MostPopulousCity = fid_tFileInputDelimited_1
									.get(columnIndexWithD_tFileInputDelimited_1);

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1
										.getException();
							}

						} catch (java.lang.Exception e) {
							whetherReject_tFileInputDelimited_1 = true;

							log.error("tFileInputDelimited_1 - "
									+ e.getMessage());

							System.err.println(e.getMessage());
							row1 = null;

						}

						log.debug("tFileInputDelimited_1 - Retrieving the record "
								+ fid_tFileInputDelimited_1.getRowNumber()
								+ ".");

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */
						// Start of branch "row1"
						if (row1 != null) {

							/**
							 * [tMSSqlSCD_1 main ] start
							 */

							currentComponent = "tMSSqlSCD_1";

							// row1
							// row1

							if (execStat) {
								runStat.updateStatOnConnection("row1"
										+ iterateId, 1, 1);
							}

							if (log.isTraceEnabled()) {
								log.trace("row1 - "
										+ (row1 == null ? "" : row1
												.toLogString()));
							}

							try {
								lookUpKey_tMSSqlSCD_1 = new SCDSK_tMSSqlSCD_1();
								lookUpKey_tMSSqlSCD_1.Postal = row1.Postal;
								lookUpKey_tMSSqlSCD_1.hashCodeDirty = true;
								lookUpValue_tMSSqlSCD_1 = cache_tMSSqlSCD_1
										.get(lookUpKey_tMSSqlSCD_1);
								if (lookUpValue_tMSSqlSCD_1 == null) {
									lookUpValue_tMSSqlSCD_1 = new SCDStruct_tMSSqlSCD_1();

									insertionStatement_tMSSqlSCD_1.setInt(1,
											nextSurrogateKey_tMSSqlSCD_1);

									nextSurrogateKey_tMSSqlSCD_1++;
									if (row1.Postal == null) {
										insertionStatement_tMSSqlSCD_1.setNull(
												2, java.sql.Types.VARCHAR);
									} else {
										insertionStatement_tMSSqlSCD_1
												.setString(2, row1.Postal);
									}

									if (row1.State == null) {
										insertionStatement_tMSSqlSCD_1.setNull(
												3, java.sql.Types.VARCHAR);
									} else {
										insertionStatement_tMSSqlSCD_1
												.setString(3, row1.State);
									}

									if (row1.Capital == null) {
										insertionStatement_tMSSqlSCD_1.setNull(
												4, java.sql.Types.VARCHAR);
									} else {
										insertionStatement_tMSSqlSCD_1
												.setString(4, row1.Capital);
									}

									if (row1.MostPopulousCity == null) {
										insertionStatement_tMSSqlSCD_1.setNull(
												7, java.sql.Types.VARCHAR);
									} else {
										insertionStatement_tMSSqlSCD_1
												.setString(7,
														row1.MostPopulousCity);
									}

									nb_line_inserted_tMSSqlSCD_1 += insertionStatement_tMSSqlSCD_1
											.executeUpdate();
								} else {
									if ((lookUpValue_tMSSqlSCD_1.Capital == null && row1.Capital != null)
											|| (lookUpValue_tMSSqlSCD_1.Capital != null && !lookUpValue_tMSSqlSCD_1.Capital
													.equals(row1.Capital))) {
										if (row1.Postal == null) {
											updateForType2_tMSSqlSCD_1.setNull(
													2, java.sql.Types.VARCHAR);
										} else {
											updateForType2_tMSSqlSCD_1
													.setString(2, row1.Postal);
										}

										nb_line_update_tMSSqlSCD_1 += updateForType2_tMSSqlSCD_1
												.executeUpdate();
										insertionStatement_tMSSqlSCD_1
												.setInt(1,
														nextSurrogateKey_tMSSqlSCD_1);

										nextSurrogateKey_tMSSqlSCD_1++;
										if (row1.Postal == null) {
											insertionStatement_tMSSqlSCD_1
													.setNull(
															2,
															java.sql.Types.VARCHAR);
										} else {
											insertionStatement_tMSSqlSCD_1
													.setString(2, row1.Postal);
										}

										if (row1.State == null) {
											insertionStatement_tMSSqlSCD_1
													.setNull(
															3,
															java.sql.Types.VARCHAR);
										} else {
											insertionStatement_tMSSqlSCD_1
													.setString(3, row1.State);
										}

										if (row1.Capital == null) {
											insertionStatement_tMSSqlSCD_1
													.setNull(
															4,
															java.sql.Types.VARCHAR);
										} else {
											insertionStatement_tMSSqlSCD_1
													.setString(4, row1.Capital);
										}

										if (row1.MostPopulousCity == null) {
											insertionStatement_tMSSqlSCD_1
													.setNull(
															7,
															java.sql.Types.VARCHAR);
										} else {
											insertionStatement_tMSSqlSCD_1
													.setString(
															7,
															row1.MostPopulousCity);
										}

										nb_line_inserted_tMSSqlSCD_1 += insertionStatement_tMSSqlSCD_1
												.executeUpdate();
									}
									if ((lookUpValue_tMSSqlSCD_1.MostPopulousCity == null && row1.MostPopulousCity != null)
											|| (lookUpValue_tMSSqlSCD_1.MostPopulousCity != null && !lookUpValue_tMSSqlSCD_1.MostPopulousCity
													.equals(row1.MostPopulousCity))) {
										if (row1.MostPopulousCity == null) {
											updateForType3_tMSSqlSCD_1.setNull(
													1, java.sql.Types.VARCHAR);
										} else {
											updateForType3_tMSSqlSCD_1
													.setString(
															1,
															row1.MostPopulousCity);
										}

										if (lookUpValue_tMSSqlSCD_1.MostPopulousCity == null) {
											updateForType3_tMSSqlSCD_1.setNull(
													2, java.sql.Types.VARCHAR);
										} else {
											updateForType3_tMSSqlSCD_1
													.setString(
															2,
															lookUpValue_tMSSqlSCD_1.MostPopulousCity);
										}

										if (row1.Postal == null) {
											updateForType3_tMSSqlSCD_1.setNull(
													3, java.sql.Types.VARCHAR);
										} else {
											updateForType3_tMSSqlSCD_1
													.setString(3, row1.Postal);
										}

										nb_line_update_tMSSqlSCD_1 += updateForType3_tMSSqlSCD_1
												.executeUpdate();
									}
								}

							} catch (java.lang.Exception e) {// catch

								System.err.print(e.getMessage());
							}// end catch

							lookUpValue_tMSSqlSCD_1.Capital = row1.Capital;
							lookUpValue_tMSSqlSCD_1.MostPopulousCity = row1.MostPopulousCity;
							cache_tMSSqlSCD_1.put(lookUpKey_tMSSqlSCD_1,
									lookUpValue_tMSSqlSCD_1);

							tos_count_tMSSqlSCD_1++;

							/**
							 * [tMSSqlSCD_1 main ] stop
							 */

						} // End of branch "row1"

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

					}
				} finally {
					if (!((Object) ("C:/Users/Grad57/Downloads/Handover/states.txt") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_1 != null) {
							fid_tFileInputDelimited_1.close();
						}
					}
					if (fid_tFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE",
								fid_tFileInputDelimited_1.getRowNumber());

						log.info("tFileInputDelimited_1 - Retrieved records count: "
								+ fid_tFileInputDelimited_1.getRowNumber()
								+ ".");

					}
				}

				if (log.isDebugEnabled())
					log.debug("tFileInputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1",
						System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tMSSqlSCD_1 end ] start
				 */

				currentComponent = "tMSSqlSCD_1";

				insertionStatement_tMSSqlSCD_1.close();
				updateForType2_tMSSqlSCD_1.close();
				updateForType3_tMSSqlSCD_1.close();

				if (conn_tMSSqlSCD_1 != null && !conn_tMSSqlSCD_1.isClosed()) {
					conn_tMSSqlSCD_1.close();
				}

				globalMap.put("tMSSqlSCD_1_NB_LINE_UPDATED",
						nb_line_update_tMSSqlSCD_1);
				globalMap.put("tMSSqlSCD_1_NB_LINE_INSERTED",
						nb_line_inserted_tMSSqlSCD_1);
				globalMap.put("tMSSqlSCD_1_NB_LINE_REJECTED",
						nb_line_rejected_tMSSqlSCD_1);

				if (execStat) {
					if (resourceMap.get("inIterateVComp") == null
							|| !((Boolean) resourceMap.get("inIterateVComp"))) {
						runStat.updateStatOnConnection("row1" + iterateId, 2, 0);
					}
				}

				ok_Hash.put("tMSSqlSCD_1", true);
				end_Hash.put("tMSSqlSCD_1", System.currentTimeMillis());

				/**
				 * [tMSSqlSCD_1 end ] stop
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
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tMSSqlSCD_1 finally ] start
				 */

				currentComponent = "tMSSqlSCD_1";

				/**
				 * [tMSSqlSCD_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
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
		final scd scdClass = new scd();

		int exitCode = scdClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'scd' - Done.");
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
		log.info("TalendJob: 'scd' - Start.");

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
			java.io.InputStream inContext = scd.class.getClassLoader()
					.getResourceAsStream(
							"local_project/scd_0_1/contexts/" + contextStr
									+ ".properties");
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

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputDelimited_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_1) {
			globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_1.printStackTrace();

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
					+ " bytes memory increase when running : scd");
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
 * 49392 characters generated by Talend Data Integration on the August 14, 2018
 * 4:00:34 PM IST
 ************************************************************************************************/
