package com.vtw.pleiades.agent.custom.processes;

import com.vtw.pleiades.agent.properties.AgentBasicConfiguration;
import com.vtw.pleiades.agent.route.ProcessRouteTemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * X플랫폼 프로세스
 * 프로세스ID: ${processId}
 * 프로세스명: ${processName}
 */
@Component
public class ${className} extends ProcessRouteTemplateBuilder {

    public static final String TEMPLATE_ID = "${processId}";

    @Autowired
    public ${className}(AgentBasicConfiguration config) {
        super(TEMPLATE_ID, config);
    }

    @Override
    public void configure() {
		// 프로세스 라우트를 구현하세요.
        fromT(direct("{{routeId}}"))
        .log("Executed {{routeId}} Route.");
    }

    @Override
    protected List<String> getAttributeNames() {
    	// 라우트 속성목록을 입력하세요.
        return List.of("param1", "param2");
    }
}
