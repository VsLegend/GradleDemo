package com.gradle.demo.activiti.servicetask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @User: Administrator
 * @Time: 2021/5/17
 * @Description:
 * @URL http://jeecg.com/activiti5.21/#bpmnJavaServiceTaskImplementation
 * 有四种方法声明如何调用Java逻辑：
 *
 * 1. 指定实现了JavaDelegate或ActivityBehavior的类
 * 要指定流程执行时调用的类，需要使用activiti:class属性提供全限定类名（fully qualified classname）。
 * @see org.activiti.engine.delegate.JavaDelegate
 * <serviceTask id="javaService" name="My Java Service Task" activiti:class="com.gradle.demo.activiti.servicetask.ServiceTaskDemo" />
 *
 * 2. 对解析为代理对象的表达式求值
 * <serviceTask id="serviceTask" activiti:delegateExpression="${delegateExpressionBean}" />
 * 这里，delegateExpressionBean是一个实现了JavaDelegate接口的bean，在Spring容器中定义。
 *
 * 3. 调用方法表达式
 * UEL方法表达式，使用activiti:expression属性。
 * <serviceTask id="javaService" name="My Java Service Task" activiti:expression="#{printer.printMessage()}" />
 * <serviceTask id="javaService" name="My Java Service Task" activiti:expression="#{printer.printMessage(execution, myVar)}" />
 * 将在名为printer的bean上调用printMessage方法。由该方法返回调用类
 * 将在名为printer的对象上调用printMessage方法。传递的第一个参数为DelegateExecution，名为execution，在表达式上下文中默认可用。传递的第二个参数，是当前执行中，名为myVar变量的值。
 *
 * 4. 对值表达式求值
 * 可以使用activiti:expression属性指定需要计算的UEL值表达式。
 * <serviceTask id="javaService" name="My Java Service Task" activiti:expression="#{split.ready}" />
 * 会调用名为split的bean的ready参数的getter方法，getReady（不带参数）。该对象会被解析为执行的流程变量或（如果可用的话）Spring上下文中的bean。
 *
 * 注意：
 * activiti:class指定的类，会在流程实例启动时，为每个活动，分别进行实例化。当该活动在流程中重复执行，或者为多实例时，使用的都会是同一个类实例。
 * 在流程定义中（如通过activiti:class）引用的类，不会在部署时实例化。只有当流程执行第一次到达该类使用的地方时，才会创建该类的实例。
 *
 * @see processes/serviceTask.bpmn
 *
 */
public class ServiceTaskDelegate implements JavaDelegate {

    private Expression text;


    @Override
    public void execute(DelegateExecution execution) {
        // 字段注入
        execution.setVariable("KEY", ((String) text.getValue(execution)).toUpperCase());
        System.out.println("服务器任务自动执行---------------------------" + text.getExpressionText());
    }

    public void setText(Expression text) {
        this.text = text;
    }
}
