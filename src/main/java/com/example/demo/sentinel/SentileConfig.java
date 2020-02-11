package com.example.demo.sentinel;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SentileConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
 // 流控规则
    @PostConstruct
    private void initRules() throws Exception {
        FlowRule rule1 = new FlowRule();
        rule1.setResource("flowRule");
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setCount(1);   // 每秒调用最大次数为 1 次

        List<FlowRule> rules = new ArrayList<>();
        rules.add(rule1);
        // 将控制规则载入到 Sentinel
        com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager.loadRules(rules);
    }
    // 降级规则
    @PostConstruct
    private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("degradeRule");
        // set threshold rt, 10 ms
        rule.setCount(10);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(10);
        rules.add(rule);
        com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager.loadRules(rules);;
    }
    
    // 系统规则
    @PostConstruct
    private void initSystemRule() {
        List<SystemRule> rules = new ArrayList<SystemRule>();
        SystemRule rule = new SystemRule();
        rule.setResource("systemRule");
        rule.setMaxThread(2);
        rules.add(rule);
        com.alibaba.csp.sentinel.slots.system.SystemRuleManager.loadRules(rules);;
    }
    
    // 权限规则
    @PostConstruct
    private void initAuthorityRule() {
        List<AuthorityRule> rules = new ArrayList<AuthorityRule>();
        AuthorityRule rule = new AuthorityRule();
        rule.setResource("authorityRule");
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("appA,appB");
        rules.add(rule);
        com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager.loadRules(rules);;
    }
    
 // 权限规则
    @PostConstruct
    private void initParamFlowRule() {
        List<ParamFlowRule> rules = new ArrayList<ParamFlowRule>();
        ParamFlowRule rule = new ParamFlowRule();
        rule.setResource("paramFlowRule");
        rule.setParamIdx(0);
        rule.setCount(5);
        rules.add(rule);
        com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager.loadRules(rules);
    }
}