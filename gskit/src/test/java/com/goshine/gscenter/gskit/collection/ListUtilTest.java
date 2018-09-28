package com.goshine.gscenter.gskit.collection;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ListUtilTest {
    @Test
    public void jdkBuild() {
        List<String> list1 = ListUtil.emptyList();

        assertThat(list1).hasSize(0);

        List<String> list2 = ListUtil.emptyListIfNull(null);
        assertThat(list2).isNotNull().hasSize(0);

        List<String> list3 = ListUtil.emptyListIfNull(list1);
        assertThat(list3).isSameAs(list1);

        try {
            list1.add("a");
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(UnsupportedOperationException.class);
        }

        List<String> list4 = ListUtil.singletonList("1");
        assertThat(list4).hasSize(1).contains("1");
        try {
            list4.add("a");
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(UnsupportedOperationException.class);
        }

        List<String> list5 = ListUtil.newArrayList();
        List<String> list6 = ListUtil.unmodifiableList(list5);

        try {
            list6.add("a");
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(UnsupportedOperationException.class);
        }

        List<String> list7 = ListUtil.synchronizedList(list6);
    }
}
