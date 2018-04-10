package org.gdz.netty.rpc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 对象序列化工具
 * @author guandezhi
 * @ClassName: org.gdz.netty.rpc.util
 * @Description:
 * @date 2018/4/3 13:57
 */
public class ObjectSerializerUtils {

    private static final Logger logger = LoggerFactory.getLogger(ObjectSerializerUtils.class);


    /**
     * 反序列化
     * @param data
     * @return
     */
    public static Object deSerializer(byte[] data) {
        if (data != null && data.length > 0) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                return objectInputStream.readObject();
            } catch (IOException e) {
                logger.info("IOException异常信息{}", e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                logger.info("ClassNotFoundException异常信息{}", e.getMessage());
                e.printStackTrace();
            }
            return null;
        } else {
            logger.info("反序列化入参为空");
            return null;
        }
    }

    /**
     * 序列化对象
     * @param object
     * @return
     */
    public static byte[] serializer(Object object) {
        if (object != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                objectOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                logger.info("IOException异常信息{}", e.getMessage());
                e.printStackTrace();
            }
            return null;
        } else {
            logger.info("序列化入参为空");
            return null;
        }
    }
}
