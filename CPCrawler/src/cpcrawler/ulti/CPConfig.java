/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpcrawler.ulti;

import cpcrawler.model.CrawlerCommentModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationDecoder;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.apache.commons.configuration2.interpol.Lookup;
import org.apache.commons.configuration2.sync.LockMode;
import org.apache.commons.configuration2.sync.Synchronizer;

/**
 *
 * @author toanhx
 */
public class CPConfig implements Configuration{
    
    public static final CPConfig Instance = new CPConfig();
    private static Configuration config;
    public CPConfig() {

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("./conf/config.properties"));

        try {
            config = builder.getConfiguration();

        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
        
    }

    @Override
    public Configuration subset(String string) {
        return config.subset(string);
    }

    @Override
    public void addProperty(String string, Object o) {
        config.addProperty(string, o);
    }

    @Override
    public void setProperty(String string, Object o) {
        config.setProperty(string, o);
    }

    @Override
    public void clearProperty(String string) {
        config.clearProperty(string);
    }

    @Override
    public void clear() {
        config.clear();
    }

    @Override
    public ConfigurationInterpolator getInterpolator() {
        return config.getInterpolator();
    }

    @Override
    public void setInterpolator(ConfigurationInterpolator ci) {
        config.setInterpolator(ci);
    }

    @Override
    public void installInterpolator(Map<String, ? extends Lookup> map, Collection<? extends Lookup> clctn) {
        config.installInterpolator(map, clctn);
    }

    @Override
    public boolean isEmpty() {
        return config.isEmpty();
    }

    @Override
    public int size() {
        return config.size();
    }

    @Override
    public boolean containsKey(String string) {
        return config.containsKey(string);
    }

    @Override
    public Object getProperty(String string) {
        return config.getProperty(string);
    }

    @Override
    public Iterator<String> getKeys(String string) {
        return config.getKeys(string);
    }

    @Override
    public Iterator<String> getKeys() {
        return config.getKeys();
    }

    @Override
    public Properties getProperties(String string) {
        return config.getProperties(string);
    }

    @Override
    public boolean getBoolean(String string) {
        return config.getBoolean(string);
    }

    @Override
    public boolean getBoolean(String string, boolean bln) {
        return config.getBoolean(string,bln);
    }

    @Override
    public Boolean getBoolean(String string, Boolean bln) {
        return config.getBoolean(string,bln);
    }

    @Override
    public byte getByte(String string) {
        return config.getByte(string);
    }

    @Override
    public byte getByte(String string, byte b) {
        return config.getByte(string,b);
    }

    @Override
    public Byte getByte(String string, Byte b) {
        return config.getByte(string,b);
    }

    @Override
    public double getDouble(String string) {
        return config.getDouble(string);
    }

    @Override
    public double getDouble(String string, double d) {
        return config.getDouble(string,d);
    }

    @Override
    public Double getDouble(String string, Double d) {
        return config.getDouble(string,d);
    }

    @Override
    public float getFloat(String string) {
        return config.getFloat(string);
    }

    @Override
    public float getFloat(String string, float f) {
        return config.getFloat(string,f);
    }

    @Override
    public Float getFloat(String string, Float f) {
        return config.getFloat(string,f);
    }

    @Override
    public int getInt(String string) {
        return config.getInt(string);
    }

    @Override
    public int getInt(String string, int i) {
        return config.getInt(string,i);
    }

    @Override
    public Integer getInteger(String string, Integer intgr) {
        return config.getInteger(string,intgr);
    }

    @Override
    public long getLong(String string) {
        return config.getLong(string);
    }

    @Override
    public long getLong(String string, long l) {
        return config.getLong(string,l);
    }

    @Override
    public Long getLong(String string, Long l) {
        return config.getLong(string,l);
    }

    @Override
    public short getShort(String string) {
        return config.getShort(string);
    }

    @Override
    public short getShort(String string, short s) {
        return config.getShort(string,s);
    }

    @Override
    public Short getShort(String string, Short s) {
        return config.getShort(string,s);
    }

    @Override
    public BigDecimal getBigDecimal(String string) {
        return config.getBigDecimal(string);
    }

    @Override
    public BigDecimal getBigDecimal(String string, BigDecimal bd) {
        return config.getBigDecimal(string,bd);
    }

    @Override
    public BigInteger getBigInteger(String string) {
        return config.getBigInteger(string);
    }

    @Override
    public BigInteger getBigInteger(String string, BigInteger bi) {
        return config.getBigInteger(string,bi);
    }

    @Override
    public String getString(String string) {
        return config.getString(string);
    }

    @Override
    public String getString(String string, String string1) {
        return config.getString(string,string1);
    }

    @Override
    public String getEncodedString(String string, ConfigurationDecoder cd) {
        return config.getEncodedString(string,cd);
    }

    @Override
    public String getEncodedString(String string) {
        return config.getEncodedString(string);
    }

    @Override
    public String[] getStringArray(String string) {
        return config.getStringArray(string);
    }

    @Override
    public List<Object> getList(String string) {
        return config.getList(string);
    }

    @Override
    public List<Object> getList(String string, List<?> list) {
        return config.getList(string,list);
    }

    @Override
    public <T> T get(Class<T> type, String string) {
        return config.get(type, string);
    }

    @Override
    public <T> T get(Class<T> type, String string, T t) {
        return config.get(type, string);
    }

    @Override
    public Object getArray(Class<?> type, String string) {
        return config.getArray(type, string);
    }

    @Override
    public Object getArray(Class<?> type, String string, Object o) {
        return config.getArray(type, string, o);
    }

    @Override
    public <T> List<T> getList(Class<T> type, String string) {
        return config.getList(type, string);
    }

    @Override
    public <T> List<T> getList(Class<T> type, String string, List<T> list) {
        return config.getList(type, string, list);
    }

    @Override
    public <T> Collection<T> getCollection(Class<T> type, String string, Collection<T> clctn) {
        return config.getCollection(type, string,clctn);
    }

    @Override
    public <T> Collection<T> getCollection(Class<T> type, String string, Collection<T> clctn, Collection<T> clctn1) {
        return config.getCollection(type, string, clctn, clctn1);
    }

    @Override
    public ImmutableConfiguration immutableSubset(String string) {
        return config.immutableSubset(string);
    }

    @Override
    public Synchronizer getSynchronizer() {
        return config.getSynchronizer();
    }

    @Override
    public void setSynchronizer(Synchronizer s) {
        config.setSynchronizer(s);
    }

    @Override
    public void lock(LockMode lm) {
        config.lock(lm);
    }

    @Override
    public void unlock(LockMode lm) {
        config.unlock(lm);
    }
    

}
