package com.example.mybatis.session;

import com.example.mybatis.constants.Constant;
import com.example.mybatis.session.defaults.DefaultSqlSession;
import com.example.mybatis.utils.XmlUtil;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class DefaultSqlSessionFactory implements SessionFactory {
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        loadMappersInfo(Configuration.getProperty(Constant.MAPPER_LOCATION));
    }

    private void loadMappersInfo(String dirName) {
        URL resource = getClass().getClassLoader().getResource(dirName);
        assert resource != null : "mapper locations classpath not found";
        File mappersDir = new File(resource.getFile());
        if (mappersDir.isDirectory()) {
            File[] files = mappersDir.listFiles();
            if (Objects.isNull(files)) {
                return;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    loadMappersInfo(dirName + "/" + file.getName());
                } else {
                    // 只对XML文件解析
                    XmlUtil.readMapperXml(file, this.configuration);
                }
            }
        }
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(this.configuration);
    }
}
