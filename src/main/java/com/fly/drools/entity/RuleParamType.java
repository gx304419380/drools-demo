package com.fly.drools.entity;

import org.kie.api.definition.type.Annotation;
import org.kie.api.definition.type.FactField;
import org.kie.api.definition.type.FactType;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Map;

public class RuleParamType implements FactType {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getSimpleName() {
        return null;
    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public String getSuperClass() {
        return null;
    }

    @Override
    public List<FactField> getFields() {
        return null;
    }

    @Override
    public FactField getField(String name) {
        return null;
    }

    @Override
    public Class<?> getFactClass() {
        return null;
    }

    @Override
    public Object newInstance() throws InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public void set(Object bean, String field, Object value) {

    }

    @Override
    public Object get(Object bean, String field) {
        return null;
    }

    @Override
    public Map<String, Object> getAsMap(Object bean) {
        return null;
    }

    @Override
    public void setFromMap(Object bean, Map<String, Object> values) {

    }

    @Override
    public List<Annotation> getClassAnnotations() {
        return null;
    }

    @Override
    public Map<String, Object> getMetaData() {
        return null;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
