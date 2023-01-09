package com.learningplatform;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearningplatformApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void f() {
        String dataJson = "{\"colleage\":[{\"colleage_name\":\"浙江大学\",\"longitude\":\"60.3\",\"latitude\":\"120.0\",\"address\":\"浙江省杭州市玉泉\"},{\"colleage_name\":\"北京大学\",\"longitude\":\"70.3\",\"latitude\":\"120.0\",\"address\":\"北京市海淀区\"}]}";

        JsonParser parser = new JsonParser();  //创建JSON解析器
        JsonObject object = (JsonObject) parser.parse(dataJson);
        JsonArray array = object.get("colleage").getAsJsonArray();    //得到为json的数组
        for(int i = 0;i<array.size();i++)
        {
            System.out.println("---------------");
            JsonObject subObject = array.get(i).getAsJsonObject();
            System.out.println("colleage_name=" + subObject.get("colleage_name").getAsString());
            System.out.println("longitude=" + subObject.get("longitude").getAsDouble());
            System.out.println("latitude=" + subObject.get("latitude").getAsDouble());

        }
    }
}
