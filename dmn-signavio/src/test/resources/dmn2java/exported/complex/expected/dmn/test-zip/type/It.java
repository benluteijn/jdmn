package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "it"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.ItImpl.class)
public interface It extends com.gs.dmn.runtime.DMNType {
    static It toIt(Object other) {
        if (other == null) {
            return null;
        } else if (It.class.isAssignableFrom(other.getClass())) {
            return (It)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            ItImpl result_ = new ItImpl();
            result_.setA((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("a", "A"));
            result_.setB((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("b", "B"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toIt(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), It.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("A")
    java.math.BigDecimal getA();

    @com.fasterxml.jackson.annotation.JsonGetter("B")
    java.math.BigDecimal getB();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("a", getA());
        context.put("b", getB());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        It other = (It) o;
        if (this.getA() != null ? !this.getA().equals(other.getA()) : other.getA() != null) return false;
        if (this.getB() != null ? !this.getB().equals(other.getB()) : other.getB() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getA() != null ? this.getA().hashCode() : 0);
        result = 31 * result + (this.getB() != null ? this.getB().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("A=" + getA());
        result_.append(", B=" + getB());
        result_.append("}");
        return result_.toString();
    }
}
