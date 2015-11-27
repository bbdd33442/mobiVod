package edu.csu.music;

import java.lang.reflect.Field;
import java.util.Date;

import org.json.JSONObject;

public class FromJsonToJavas {

    @SuppressWarnings("rawtypes")
    public static Object fromJsonToJava(JSONObject json, Class pojo) throws Exception {
        // ���ȵõ�pojo��������ֶ�
        Field[] fields = pojo.getDeclaredFields();
        // ���ݴ����Class��̬����pojo����
        Object obj = pojo.newInstance();
        for (Field field : fields) {
            // �����ֶοɷ��ʣ����룬���򱨴�
            field.setAccessible(true);
            // �õ��ֶε�������
            String name = field.getName();
            // ��һ�ε�����������ֶ���JSONObject�в����ڻ��׳��쳣��������쳣����������
            try {
                json.get(name);
            } catch (Exception ex) {
                continue;
            }
            if (json.get(name) != null && !"".equals(json.getString(name))) {
                // �����ֶε����ͽ�ֵת��Ϊ��Ӧ�����ͣ������õ����ɵĶ����С�
                if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    field.set(obj, Long.parseLong(json.getString(name)));
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, json.getString(name));
                } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    field.set(obj, Double.parseDouble(json.getString(name)));
                } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                    field.set(obj, Integer.parseInt(json.getString(name)));
                } else if (field.getType().equals(java.util.Date.class)) {
                    field.set(obj, Date.parse(json.getString(name)));
                } else {
                    continue;
                }
            }
        }
        return obj;
    }

}
