package sk.eea.jira.temp;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.StringWriter;

public class VelocityEngineFactory {
    @Autowired
    private VelocityEngine velocityEngine;

    public String prepareRegistrationEmailText() throws Exception {
        VelocityContext context = new VelocityContext();
        //context.put("username", user.getUsername());
        //context.put("email", user.getEmail());
        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("edit.vm", "UTF-8", context, stringWriter);
        String text = stringWriter.toString();
        return text;
    }
}
