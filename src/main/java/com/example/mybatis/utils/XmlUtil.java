package com.example.mybatis.utils;


import com.example.mybatis.constants.Constant;
import com.example.mybatis.mapping.MappedStatement;
import com.example.mybatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.mybatis.constants.SqlType.*;

public final class XmlUtil {

    /**
     * readMapperXml
     */
    public static void readMapperXml(File fileName, Configuration configuration) {

        try {

            // 创建一个读取器
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding(Constant.CHARSET_UTF8);

            // 读取文件内容
            Document document = saxReader.read(fileName);

            // 获取xml中的根元素
            Element rootElement = document.getRootElement();

            // 不是beans根元素的，文件不对
            if (!Constant.XML_ROOT_LABEL.equals(rootElement.getName())) {
                System.err.println("mapper xml文件根元素不是mapper");
                return;
            }

            String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);

            List<MappedStatement> statements = new ArrayList<>();
            for (Iterator<Element> iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = iterator.next();
                String eleName = element.getName();

                MappedStatement statement = new MappedStatement();

                if (SELECT.name().equalsIgnoreCase(eleName)) {
                    String resultType = element.attributeValue(Constant.XML_SELECT_RESULT_TYPE);
                    statement.setResultType(resultType);
                    statement.setSqlCommandType(SELECT);
                } else if (UPDATE.name().equalsIgnoreCase(eleName)) {
                    statement.setSqlCommandType(UPDATE);
                } else {
                    // 其他标签自己实现
                    System.err.println("不支持此xml标签解析:" + eleName);
                    statement.setSqlCommandType(DEFAULT);
                }

                // 设置SQL的唯一ID
                String sqlId = namespace + "." + element.attributeValue(Constant.XML_ELEMENT_ID);

                statement.setSqlId(sqlId);
                statement.setNamespace(namespace);
                statement.setSql(element.getStringValue().trim());
                statements.add(statement);

                System.out.println(statement);
                configuration.addMappedStatement(sqlId, statement);

                // 这里其实是在MapperRegistry中生产一个mapper对应的代理工厂
                configuration.addMapper(Class.forName(namespace));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
