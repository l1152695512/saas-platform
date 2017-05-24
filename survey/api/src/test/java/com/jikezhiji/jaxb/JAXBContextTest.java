package com.jikezhiji.jaxb;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.util.JAXBResult;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neo on 2016/4/22.
 */
public class JAXBContextTest {
    public Map<String, String> getFucked() {
        return null;
    }
    @Test
    public void generateSchemaTest() throws JAXBException, IOException, NoSuchMethodException {
        Method method =JAXBContextTest.class.getMethod("getFucked");
        JAXBContext ctx = ContextFactory.createContext(new Class[]{MapSchemaContainer.class},new HashMap<>());
//        final JAXBContext ctx = JAXBContext.newInstance(MapSchemaContainer.class);
        ctx.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                StreamResult result =  new StreamResult();
                result.setOutputStream(System.out);
                result.setSystemId("D:/map.xsd");
                return result;
            }
        });
    }
}
