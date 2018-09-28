package com.goshine.gscenter.gskit.base;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PairTest {
    @Test
    public void pairTest() {
        Pair<String, Integer> pair = Pair.of("haha", 1);
        Pair<String, Integer> pair2 = Pair.of("haha", 2);
        Pair<String, Integer> pair3 = Pair.of("kaka", 1);

        assertThat(pair.equals(pair2)).isFalse();
        assertThat(pair.equals(pair3)).isFalse();
        assertThat(pair.hashCode() != pair2.hashCode()).isTrue();
        assertThat(pair.toString()).isEqualTo("Pair [left=haha, right=1]");

        assertThat(pair.getLeft()).isEqualTo("haha");
        assertThat(pair.getRight()).isEqualTo(1);
    }
}
