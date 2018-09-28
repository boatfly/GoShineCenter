package com.goshine.gscenter.gskit.base;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanUtilTest {
    @Test
    public void test(){
        assertThat(BooleanUtil.toBoolean("True")).isTrue();
        assertThat(BooleanUtil.toBoolean("tre")).isFalse();
        assertThat(BooleanUtil.toBoolean(null)).isFalse();

        assertThat(BooleanUtil.toBooleanObject("True")).isTrue();
        assertThat(BooleanUtil.toBooleanObject("tre")).isFalse();
        assertThat(BooleanUtil.toBooleanObject(null)).isNull();

        assertThat(BooleanUtil.parseGeneralString("1", false)).isFalse();
        assertThat(BooleanUtil.parseGeneralString("y", false)).isTrue();
        assertThat(BooleanUtil.parseGeneralString("y")).isTrue();
        assertThat(BooleanUtil.parseGeneralString("x")).isNull();
    }
}
