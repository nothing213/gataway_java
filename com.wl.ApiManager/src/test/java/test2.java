import java.util.*;

class test2{
    public static void main(String[] args) {
        List new_list = new ArrayList();
        Object info_object = "obej1";
        Object path_object = "object2";
        Map<String,Object> map = new HashMap<>();
        map.put("info",info_object);
        map.put("path",path_object);
        for (Map.Entry<String,Object> list:map.entrySet())
        {
            System.out.println(list.getValue());
        }

    }
}