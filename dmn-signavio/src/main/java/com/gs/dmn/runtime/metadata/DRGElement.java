/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.runtime.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class DRGElement extends NamedElement {
    @JsonProperty("javaParameterName")
    private String javaParameterName;
    @JsonProperty("javaTypeName")
    private String javaTypeName;
    @JsonProperty("typeRef")
    private QName typeRef;

    // Required by ObjectMapper
    DRGElement() {
    }

    public DRGElement(String id, String name, String label, String javaParameterName, String javaTypeName, QName typeRef) {
        super(id, name, label);
        this.javaParameterName = javaParameterName;
        this.javaTypeName = javaTypeName;
        this.typeRef = typeRef;
    }

    public QName getTypeRef() {
        return typeRef;
    }

    public String getJavaParameterName() {
        return javaParameterName;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }
}
