/*
 * Configurate
 * Copyright (C) zml and Configurate contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spongepowered.configurate.objectmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.objectmapping.meta.NodeKey;
import org.spongepowered.configurate.objectmapping.meta.NodeResolver;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import org.spongepowered.configurate.serialize.SerializationException;

class NodeResolverTest {

    // node key

    static class TestNodeKey {
        @NodeKey String ownKey;
        String own;
    }

    @Test
    void testNodeKey() throws SerializationException {
        final ObjectMapper<TestNodeKey> mapper = ObjectMapper.factory().get(TestNodeKey.class);
        final BasicConfigurationNode source = BasicConfigurationNode.root().node("test");
        source.node("own").set("yeet");

        final TestNodeKey object = mapper.load(source);

        assertEquals("test", object.ownKey);
        assertEquals("yeet", object.own);
    }

    // key from setting

    static class TestSettingKey {
        @Setting("something") String notSomething;
    }

    @Test
    void testSettingKey() throws SerializationException {
        final ObjectMapper<TestSettingKey> mapper = ObjectMapper.factory().get(TestSettingKey.class);

        final BasicConfigurationNode source = BasicConfigurationNode.root(n -> {
            n.node("something").raw("blah");
        });

        final TestSettingKey object = mapper.load(source);

        assertEquals("blah", object.notSomething);
    }

    // only with annotation (setting.class in this case)

    static class TestOnlyWithAnnotation {
        @Setting String marked;
        String notProcessed;
    }

    @Test
    void testOnlyWithAnnotation() throws SerializationException {
        final ObjectMapper<TestOnlyWithAnnotation> mapper = ObjectMapper.factoryBuilder()
                .addNodeResolver(NodeResolver.onlyWithAnnotation(Setting.class))
                .build().get(TestOnlyWithAnnotation.class);

        final BasicConfigurationNode source = BasicConfigurationNode.root(n -> {
            n.node("marked").raw("something");
            n.node("not-processed").raw("ignored");
        });

        final TestOnlyWithAnnotation object = mapper.load(source);

        assertEquals("something", object.marked);
        assertNull(object.notProcessed);
    }

    @ConfigSerializable
    static class HolderOne {
        String hello = "eek";
    }

    @ConfigSerializable
    static class HolderTwo {
        String skeletons = "spooky | scary";
    }

    @ConfigSerializable
    static class TestNodeFromParent {
        @Setting(nodeFromParent = true) HolderOne one;
        @Setting(nodeFromParent = true) HolderTwo two;
    }

    @Test
    void testNodeFromParentRead() throws SerializationException {
        final ConfigurationNode root = BasicConfigurationNode.root(ConfigurationOptions.defaults()
                                                                           .nativeTypes(ImmutableSet.of(String.class)));

        root.node("hello").set("yay");
        root.node("skeletons").set("go clunk");

        final TestNodeFromParent value = root.get(TestNodeFromParent.class);
        assertNotNull(value);

        assertEquals("yay", value.one.hello);
        assertEquals("go clunk", value.two.skeletons);
    }

    @Test
    void testNodeFromParentWritesDefaults() throws SerializationException {
        final ConfigurationNode root = BasicConfigurationNode.root(ConfigurationOptions.defaults()
                                                                           .nativeTypes(ImmutableSet.of(String.class))
                                                                           .implicitInitialization(true)
                                                                           .shouldCopyDefaults(true));

        root.get(TestNodeFromParent.class);

        assertEquals("eek", root.node("hello").raw());
        assertEquals("spooky | scary", root.node("skeletons").raw());
    }

}
