
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision-with-extension.ftl", "keepInputallFalse"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "keepInputallFalse",
    label = "keepInput_allFalse",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class KeepInputallFalse extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "keepInputallFalse",
        "keepInput_allFalse",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public KeepInputallFalse() {
    }

    public Boolean apply(String booleanAllFalse_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanAllFalse_iterator != null ? Boolean.valueOf(booleanAllFalse_iterator) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputallFalse'", e);
            return null;
        }
    }

    public Boolean apply(String booleanAllFalse_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((booleanAllFalse_iterator != null ? Boolean.valueOf(booleanAllFalse_iterator) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputallFalse'", e);
            return null;
        }
    }

    public Boolean apply(Boolean booleanAllFalse_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanAllFalse_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Boolean apply(Boolean booleanAllFalse_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'keepInputallFalse'
            long keepInputallFalseStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments keepInputallFalseArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            keepInputallFalseArguments_.put("booleanAllFalse_iterator", booleanAllFalse_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, keepInputallFalseArguments_);

            // Evaluate decision 'keepInputallFalse'
            Boolean output_ = evaluate(booleanAllFalse_iterator, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'keepInputallFalse'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, keepInputallFalseArguments_, output_, (System.currentTimeMillis() - keepInputallFalseStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'keepInputallFalse' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(Boolean booleanAllFalse_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return booleanAllFalse_iterator;
    }
}
