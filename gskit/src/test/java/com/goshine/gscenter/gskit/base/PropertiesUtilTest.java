package com.goshine.gscenter.gskit.base;

import org.junit.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesUtilTest {
    @Test
    public void loadProperties() {
        Properties p1 = PropertiesUtil.loadFromFile("classpath:config.properties");
        assertThat(p1.get("goshine.scale.min")).isEqualTo("1");
        assertThat(p1.get("goshine.scale.max")).isEqualTo("12");

        Properties p2 = PropertiesUtil.loadFromString("goshine.scale.min=1\ngoshine.scale.max=10\nisOpen=true");
        assertThat(PropertiesUtil.getInt(p2, "goshine.scale.min", 0)).isEqualTo(1);
        assertThat(PropertiesUtil.getInt(p2, "goshine.scale.max", 0)).isEqualTo(10);
        assertThat(PropertiesUtil.getInt(p2, "goshine.scale.maxA", 0)).isEqualTo(0);

        assertThat(PropertiesUtil.getLong(p2, "goshine.scale.min", 0L)).isEqualTo(1);
        assertThat(PropertiesUtil.getLong(p2, "goshine.scale.max", 0L)).isEqualTo(10);
        assertThat(PropertiesUtil.getLong(p2, "goshine.scale.maxA", 0L)).isEqualTo(0);

        assertThat(PropertiesUtil.getDouble(p2, "goshine.scale.min", 0d)).isEqualTo(1);
        assertThat(PropertiesUtil.getDouble(p2, "goshine.scale.max", 0d)).isEqualTo(10);
        assertThat(PropertiesUtil.getDouble(p2, "goshine.scale.maxA", 0d)).isEqualTo(0);

        assertThat(PropertiesUtil.getString(p2, "goshine.scale.min", "")).isEqualTo("1");
        assertThat(PropertiesUtil.getString(p2, "goshine.scale.max", "")).isEqualTo("10");
        assertThat(PropertiesUtil.getString(p2, "goshine.scale.maxA", "")).isEqualTo("");

        assertThat(PropertiesUtil.getBoolean(p2, "isOpen", false)).isTrue();
    }
}
