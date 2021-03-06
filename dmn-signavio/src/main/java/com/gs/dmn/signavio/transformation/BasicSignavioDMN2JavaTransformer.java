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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.semantics.environment.*;
import com.gs.dmn.feel.analysis.semantics.type.FEELFunctionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.StringLiteral;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.type.FEELTypeTranslator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.metadata.ExtensionElement;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import org.omg.spec.dmn._20180521.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class BasicSignavioDMN2JavaTransformer extends BasicDMN2JavaTransformer {
    private static final String DECISION_OUTPUT_FIELD_NAME = "value";

    private final SignavioDMNModelRepository dmnModelRepository;

    public BasicSignavioDMN2JavaTransformer(DMNModelRepository dmnModelRepository, EnvironmentFactory environmentFactory, FEELTypeTranslator feelTypeTranslator, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters) {
        super(dmnModelRepository, environmentFactory, feelTypeTranslator, lazyEvaluationDetector, inputParameters);
        this.dmnModelRepository = (SignavioDMNModelRepository) super.getDMNModelRepository();
    }

    //
    // BKM
    //
    @Override
    public String drgElementOutputType(TNamedElement element) {
        if (dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementOutputType(outputDecision);
        } else if (element instanceof TDecision && dmnModelRepository.isFreeTextLiteralExpression(element)) {
            Type type = drgElementOutputFEELType(element);
            return toJavaType(type);
        } else {
            return super.drgElementOutputType(element);
        }
    }

    @Override
    public Type drgElementOutputFEELType(TNamedElement element) {
        if (dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementOutputFEELType(outputDecision);
        } else if (element instanceof TDecision && dmnModelRepository.isFreeTextLiteralExpression(element)) {
            Expression feelExpression = analyzeExpression(element);
            if (feelExpression instanceof FunctionDefinition) {
                if (((FunctionDefinition) feelExpression).isExternal()) {
                    Expression body = ((FunctionDefinition) feelExpression).getBody();
                    return externalFunctionReturnFEELType(body);
                } else {
                    Type type = feelExpression.getType();
                    if (type instanceof FEELFunctionType) {
                        type = ((FEELFunctionType) type).getReturnType();
                    }
                    return type;
                }
            }
            return feelExpression.getType();
        } else {
            return super.drgElementOutputFEELType(element);
        }
    }

    @Override
    public Type drgElementOutputFEELType(TNamedElement element, Environment environment) {
        if (dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementOutputFEELType(outputDecision);
        } else if (element instanceof TDecision && dmnModelRepository.isFreeTextLiteralExpression(element)) {
            Expression feelExpression = analyzeExpression(element);
            if (feelExpression instanceof FunctionDefinition) {
                if (((FunctionDefinition) feelExpression).isExternal()) {
                    Expression body = ((FunctionDefinition) feelExpression).getBody();
                    return externalFunctionReturnFEELType(body);
                } else {
                    Type type = feelExpression.getType();
                    if (type instanceof FEELFunctionType) {
                        type = ((FEELFunctionType) type).getReturnType();
                    }
                    return type;
                }
            }
            return feelExpression.getType();
        } else {
            return super.drgElementOutputFEELType(element, environment);
        }
    }

    @Override
    protected void addDeclaration(TDRGElement parent, Environment parentEnvironment, TDRGElement child, Environment childEnvironment) {
        if (child instanceof TInputData) {
            Declaration declaration = makeVariableDeclaration(child, ((TInputData) child).getVariable(), childEnvironment);
            addDeclaration(parentEnvironment, (VariableDeclaration) declaration, parent, child);
        } else if (child instanceof TBusinessKnowledgeModel) {
            TBusinessKnowledgeModel bkm = (TBusinessKnowledgeModel) child;
            if (dmnModelRepository.isBKMLinkedToDecision(bkm)) {
                TDecision outputDecision = dmnModelRepository.getOutputDecision(bkm);
                Declaration declaration = makeVariableDeclaration(child, outputDecision.getVariable(), childEnvironment);
                addDeclaration(parentEnvironment, (VariableDeclaration) declaration, parent, child);
            } else {
                TFunctionDefinition functionDefinition = bkm.getEncapsulatedLogic();
                functionDefinition.getFormalParameter().forEach(
                        p -> parentEnvironment.addDeclaration(environmentFactory.makeVariableDeclaration(p.getName(), toFEELType(QualifiedName.toQualifiedName(p.getTypeRef())))));
                FunctionDeclaration declaration = makeInvocableDeclaration(bkm, childEnvironment);
                addDeclaration(parentEnvironment, declaration, parent, child);
            }
        } else if (child instanceof TDecision) {
            Declaration declaration = makeVariableDeclaration(child, ((TDecision) child).getVariable(), childEnvironment);
            addDeclaration(parentEnvironment, (VariableDeclaration) declaration, parent, child);
        } else if (child instanceof TDecisionService) {
            FunctionDeclaration functionDeclaration = makeInvocableDeclaration((TDecisionService) child, childEnvironment);
            addDeclaration(parentEnvironment, functionDeclaration, parent, child);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet", child.getClass().getSimpleName()));
        }
    }

    public String drgElementOutputFieldName(TDRGElement element, int outputIndex) {
        if (this.dmnModelRepository.isDecisionTableExpression(element)) {
            TDecisionTable decisionTable = (TDecisionTable) dmnModelRepository.expression(element);
            return javaFriendlyVariableName(this.dmnModelRepository.outputClauseName(element, decisionTable.getOutput().get(outputIndex)));
        } else if (this.dmnModelRepository.isLiteralExpression(element)) {
            return DECISION_OUTPUT_FIELD_NAME;
        } else {
            TExpression value = dmnModelRepository.expression(element);
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet ", value.getClass().getSimpleName()));
        }
    }

    public String externalFunctionClassName(Expression body) {
        if (body instanceof Context) {
            Expression javaExpression = ((Context) body).entry("java").getExpression();
            if (javaExpression instanceof Context) {
                Expression returnTypeExp = ((Context) javaExpression).entry("class").getExpression();
                if (returnTypeExp instanceof StringLiteral) {
                    String lexeme = ((StringLiteral) returnTypeExp).getLexeme();
                    return StringEscapeUtil.stripQuotes(lexeme);
                }
            }
        }
        throw new DMNRuntimeException(String.format("Missing class in '%s'", body));
    }

    public String externalFunctionMethodName(Expression body) {
        if (body instanceof Context) {
            Expression javaExpression = ((Context) body).entry("java").getExpression();
            if (javaExpression instanceof Context) {
                Expression returnTypeExp = ((Context) javaExpression).entry("methodSignature").getExpression();
                if (returnTypeExp instanceof StringLiteral) {
                    // Signature should be methodName(arg1, arg2, ..., argN)
                    String lexeme = ((StringLiteral) returnTypeExp).getLexeme();
                    String signature = StringEscapeUtil.stripQuotes(lexeme);
                    int index = signature.indexOf('(');
                    if (index != -1) {
                        signature = signature.substring(0, index);
                    }
                    return signature;
                }
            }
        }
        throw new DMNRuntimeException(String.format("Missing methodName in '%s'", body));
    }

    public Type externalFunctionReturnFEELType(Expression body) {
        if (body instanceof Context) {
            Expression javaExpression = ((Context) body).entry("java").getExpression();
            if (javaExpression instanceof Context) {
                Expression returnTypeExp = ((Context) javaExpression).entry("returnType").getExpression();
                if (returnTypeExp instanceof StringLiteral) {
                    String lexeme = ((StringLiteral) returnTypeExp).getLexeme();
                    String typeName = StringEscapeUtil.stripQuotes(lexeme);
                    return toFEELType(QualifiedName.toQualifiedName(typeName));
                }
            }
        }
        throw new DMNRuntimeException(String.format("Missing returnType in '%s'", body));
    }

    private Expression analyzeExpression(TNamedElement element) {
        TLiteralExpression expression = (TLiteralExpression) dmnModelRepository.expression(element);
        Environment decisionEnvironment = makeEnvironment((TDecision) element);
        return feelTranslator.analyzeExpression(expression.getText(), FEELContext.makeContext(decisionEnvironment));
    }

    @Override
    public String drgElementSignature(TDRGElement element) {
        if (dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            List<Pair<String, Type>> parameters = inputDataParametersClosure(outputDecision);
            String decisionSignature = parameters.stream().map(p -> String.format("%s %s", toJavaType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            return super.drgElementSignature(element);
        }
    }

    @Override
    public String drgElementArgumentList(TDRGElement element) {
        if (dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            List<Pair<String, Type>> parameters = inputDataParametersClosure(outputDecision);
            String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            return super.drgElementArgumentList(element);
        }
    }

    @Override
    public String drgElementDirectSignature(TDRGElement element) {
        if (dmnModelRepository.isMultiInstanceDecision(element)) {
            return iterationSignature((TDecision) element);
        } else {
            return super.drgElementDirectSignature(element);
        }
    }

    @Override
    public String drgElementDirectArgumentList(TDRGElement element) {
        if (dmnModelRepository.isMultiInstanceDecision(element)) {
            return iterationArgumentList((TDecision) element);
        } else {
            return super.drgElementDirectArgumentList(element);
        }
    }

    @Override
    public String drgElementConvertedArgumentList(TDRGElement element) {
        if (dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            List<Pair<String, Type>> parameters = inputDataParametersClosure(outputDecision);
            String arguments = parameters.stream().map(p -> String.format("%s", convertDecisionArgument(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            return super.drgElementConvertedArgumentList(element);
        }
    }

    @Override
    public List<String> drgElementArgumentNameList(TDRGElement element) {
        if (dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementArgumentNameList(outputDecision);
        } else {
            return super.drgElementArgumentNameList(element);
        }
    }

    public String bkmLinkedToDecisionToJava(TBusinessKnowledgeModel bkm) {
        TDecision outputDecision = dmnModelRepository.getOutputDecision(bkm);
        String decisionClassName = drgElementClassName(outputDecision);
        List<String> argNameList = drgElementArgumentNameList(outputDecision);
        String decisionArgList = argNameList.stream().collect(Collectors.joining(", "));
        decisionArgList = drgElementArgumentsExtra(augmentArgumentList(decisionArgList));
        return String.format("%s.apply(%s)", defaultConstructor(decisionClassName), decisionArgList);
    }

    @Override
    protected List<FormalParameter> bkmFEELParameters(TBusinessKnowledgeModel bkm) {
        TFunctionDefinition encapsulatedLogic = bkm.getEncapsulatedLogic();
        if (encapsulatedLogic == null) {
            List<FormalParameter> parameters = new ArrayList<>();
            TDecision outputDecision = dmnModelRepository.getOutputDecision(bkm);
            List<TInputData> inputDatas = this.dmnModelRepository.allInputDatas(outputDecision);
            this.dmnModelRepository.sortNamedElements(inputDatas);
            inputDatas.stream().forEach(id -> parameters.add(new Parameter(id.getName(), drgElementOutputFEELType(id))));
            return parameters;
        } else {
            return super.bkmFEELParameters(bkm);
        }
    }

    @Override
    public QualifiedName drgElementOutputTypeRef(TDRGElement element) {
        if (dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return drgElementOutputTypeRef(outputDecision);
        } else {
            return super.drgElementOutputTypeRef(element);
        }
    }

    //
    // Manifest
    //
    public List<ExtensionElement> makeMetadataExtensions(TDecision decision) {
        List<ExtensionElement> extensions = new ArrayList<>();
        if (this.dmnModelRepository.isMultiInstanceDecision(decision)) {
            ExtensionElement extensionElement = dmnModelRepository.getExtension().makeMultiInstanceExtension(decision);
            extensions.add(extensionElement);
        } else if (this.dmnModelRepository.isLiteralExpression(decision)) {
            TLiteralExpression expression = (TLiteralExpression) dmnModelRepository.expression(decision);
            Environment environment = this.makeEnvironment(decision);
            Expression literalExpression = feelTranslator.analyzeExpression(expression.getText(), FEELContext.makeContext(environment));
            ExtensionElement extensionElement = dmnModelRepository.getExtension().makeTechnicalAttributesExtension(literalExpression);
            if (extensionElement != null) {
                extensions.add(extensionElement);
            }
        }
        return extensions;
    }

    //
    // Multi Instance Decision
    //
    public MultiInstanceDecisionLogic multiInstanceDecisionLogic(TDecision decision) {
        return this.dmnModelRepository.getExtension().multiInstanceDecisionLogic(decision);
    }

    public String iterationExpressionToJava(TDecision decision, String iterationExpression) {
        return literalExpressionToJava(iterationExpression, decision);
    }

    private String iterationSignature(TDecision decision) {
        List<TDRGElement> elements = collectIterationInputs(decision);

        List<Pair<String, String>> parameters = new ArrayList<>();
        for (TDRGElement element : elements) {
            String parameterName = iterationParameterName(element);
            String parameterJavaType = lazyEvaluationType(element, parameterJavaType(element));
            parameters.add(new Pair<>(parameterName, parameterJavaType));
        }
        String signature = parameters.stream().map(p -> String.format("%s %s", p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
        return augmentSignature(signature);
    }

    private String iterationArgumentList(TDecision decision) {
        List<TDRGElement> elements = collectIterationInputs(decision);

        List<String> arguments = new ArrayList<>();
        for (TDRGElement element : elements) {
            String argumentName = iterationArgumentName(element);
            arguments.add(argumentName);
        }
        String argumentList = arguments.stream().collect(Collectors.joining(", "));
        return augmentArgumentList(argumentList);
    }

    private List<TDRGElement> collectIterationInputs(TDecision decision) {
        Set elementSet = new LinkedHashSet<>();
        elementSet.addAll(this.dmnModelRepository.allInputDatas(decision));
        elementSet.addAll(this.dmnModelRepository.directSubDecisions(decision));
        List<TDRGElement> elements = new ArrayList<>(elementSet);
        this.dmnModelRepository.sortNamedElements(elements);
        return elements;
    }

    private String iterationParameterName(TDRGElement element) {
        if (element instanceof TInputData) {
            return inputDataVariableName((TInputData) element);
        } else if (element instanceof TDecision) {
            return drgElementVariableName(element);
        }
        throw new UnsupportedOperationException(String.format("Not supported '%s'", element.getClass().getName()));
    }

    private String iterationArgumentName(TDRGElement element) {
        if (element instanceof TInputData) {
            return inputDataVariableName((TInputData) element);
        } else if (element instanceof TDecision) {
            return drgElementVariableName(element);
        }
        throw new UnsupportedOperationException(String.format("Not supported '%s'", element.getClass().getName()));
    }

    //
    // Free text LiteralExpression related functions
    //
    public String freeTextLiteralExpressionToJava(TDRGElement element) {
        TLiteralExpression expression = (TLiteralExpression) dmnModelRepository.expression(element);
        Environment environment = this.makeEnvironment(element);
        Expression literalExpression = feelTranslator.analyzeExpression(expression.getText(), FEELContext.makeContext(environment));
        if (literalExpression instanceof FunctionDefinition) {
            Expression body = ((FunctionDefinition) literalExpression).getBody();
            String javaCode;
            if (((FunctionDefinition) literalExpression).isExternal()) {
                String returnJavaType = toJavaType(externalFunctionReturnFEELType(body));
                String className = externalFunctionClassName(body);
                String methodName = externalFunctionMethodName(body);
                String arguments = drgElementEvaluateArgumentList(element);
                javaCode = String.format("(%s)%s.execute(\"%s\", \"%s\", new Object[] {%s})", returnJavaType, externalExecutorVariableName(), className, methodName, arguments);
            } else {
                javaCode = feelTranslator.expressionToJava(body, FEELContext.makeContext(environment));
            }
            Type expressionType = body.getType();
            Statement statement = new ExpressionStatement(javaCode, expressionType);
            Type expectedType = toFEELType(drgElementOutputTypeRef(element));
            Statement result = convertExpression(statement, expectedType);
            return ((ExpressionStatement) result).getExpression();
        } else {
            return super.literalExpressionToJava(expression.getText(), element);
        }
    }

}
