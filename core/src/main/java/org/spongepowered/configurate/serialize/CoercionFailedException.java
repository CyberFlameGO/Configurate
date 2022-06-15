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
package org.spongepowered.configurate.serialize;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

/**
 * Error thrown when a value fails to be converted to an expected type.
 *
 * @since 4.0.0
 */
public class CoercionFailedException extends SerializationException {

    private static final long serialVersionUID = 5800074754243723221L;

    /**
     * Indicate that a value transformation has failed.
     *
     * @param inputValue original value
     * @param typeDescription description of the expected type
     * @since 4.0.0
     */
    public CoercionFailedException(final Object inputValue, final String typeDescription) {
        super("Failed to coerce input value of type " + inputValue.getClass() + " to " + typeDescription);
    }

    /**
     * Indicate that a value transformation has failed.
     *
     * @param target expected type
     * @param inputValue original value
     * @param typeDescription description of the expected type
     * @since 4.0.0
     */
    public CoercionFailedException(final Type target, final Object inputValue, final String typeDescription) {
        super(target, "Failed to coerce input value of type " + inputValue.getClass() + " to " + typeDescription);
    }

    /**
     * Indicate that a value transformation has failed.
     *
     * @param target expected type
     * @param inputValue original value
     * @param typeDescription description of the expected type
     * @since 4.2.0
     */
    public CoercionFailedException(final AnnotatedType target, final Object inputValue, final String typeDescription) {
        super(target, "Failed to coerce input value of type " + inputValue.getClass() + " to " + typeDescription);
    }

}
