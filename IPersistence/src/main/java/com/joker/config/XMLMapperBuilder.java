package com.joker.config;

import com.joker.pojo.Configuration;
import com.joker.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
/**
 * @author: wumj
 **/
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration =configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        List<Element> selectList = rootElement.selectNodes("//select");
        elementToMappedStatement(selectList,namespace);
        List<Element> insertList = rootElement.selectNodes("//insert");
        elementToMappedStatement(insertList,namespace);
        List<Element> updateList = rootElement.selectNodes("//update");
        elementToMappedStatement(updateList,namespace);
        List<Element> deleteList = rootElement.selectNodes("//delete");
        elementToMappedStatement(deleteList,namespace);
    }

    public void elementToMappedStatement(List<Element> list, String namespace){
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setSql(sqlText);
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
    }


}
