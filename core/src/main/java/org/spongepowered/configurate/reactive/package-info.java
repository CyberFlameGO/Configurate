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
/**
 * This package contains a rudimentary implementation of a reactive programming
 * API. It does not intend to meet all the requirements of the Reactive Streams
 * specification, but it is designed to allow for easy migration to
 * implementations based on JDK9's Flow API when Configurate targets Java 9.
 *
 * <p>With {@link org.spongepowered.configurate.reactive.Publisher},
 * {@link org.spongepowered.configurate.reactive.Subscriber},
 * and {@link org.spongepowered.configurate.reactive.Processor}, a system can
 * easily be built to process a series of units of information.
 *
 * <p>The origin of a system is generally in
 * {@link org.spongepowered.configurate.reactive.Processor#create() creating a processor},
 * and making it available for users to
 * {@link org.spongepowered.configurate.reactive.Subscriber subscribe} to. Then,
 * any value
 * {@link org.spongepowered.configurate.reactive.Subscriber#submit(java.lang.Object) submitted}
 * to the Processor will be forwarded to ever registered subscriber.
 *
 * <p>A unique feature of Configurate's reactive listeners is the ability to
 * have transactional subscribers. A
 * {@link org.spongepowered.configurate.reactive.TransactionalSubscriber} will
 * receive a new value, followed by either a commit or rollback notification.
 * When subscribing to a transactional processor, all subscribers must accept
 * the new value before it's committed.
 *
 * <p>In many cases, it is best to only expose the
 * {@link org.spongepowered.configurate.reactive.Publisher} side of the
 * processor, so that the submission of values can be more easily controlled.
 */
@DefaultQualifier(NonNull.class)
package org.spongepowered.configurate.reactive;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
