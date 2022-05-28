import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class FilterOrders {


    public static void main(String[] args) throws ParseException, java.text.ParseException {
//        SimpleDateFormat
        Scanner in = new Scanner(System.in);
        JSONArray jsonArray = (JSONArray) JSONValue.parse(in.nextLine());
        ArrayList filterParam = new ArrayList<String>();
        String tmp_str;
        //чтение параметров фильтрации хз сколько их
        while (!((tmp_str = in.nextLine()).length() == 0)){
            filterParam.add( tmp_str);
        }
        // стабильно 5 раз
//        for (int i = 0; i < 5; i++){
//            filterParam.add(in.nextLine());
//        }

        for (int i = 0; i < filterParam.size(); i++) {
            if (((String)(filterParam.get(i))).contains("NAME")) {
                jsonArray = FilterName((String)(filterParam.get(i)),jsonArray);
            }
            if (((String)(filterParam.get(i))).contains("DATE")) {
                jsonArray = FilterDate((String)(filterParam.get(i)),jsonArray);
            }
            if (((String)(filterParam.get(i))).contains("PRICE")) {
                jsonArray = FilterPrice((String)(filterParam.get(i)),jsonArray);
            }
        }

        System.out.println(jsonArray.toJSONString());
        System.out.println(jsonArray.toString());
        System.out.println(jsonArray);
        jsonArray.toJSONString();

    }
    public static JSONArray FilterName(String string, JSONArray jsonArray){
        String[] typeParam = string.split(" ");
        JSONArray jsonArray_red = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++){
            if (((String) ((JSONObject) jsonArray.get(i)).get("name")).toLowerCase().contains(typeParam[1].toLowerCase())) {
                jsonArray_red.add(jsonArray.get(i));
            }
        }
        return jsonArray_red;
    }

    public static JSONArray FilterDate(String string, JSONArray jsonArray) throws java.text.ParseException {
        String[] typeParam = string.split(" ");
        JSONArray jsonArray_red = new JSONArray();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < jsonArray.size(); i++){
            if(typeParam[0].contains("AFTER")){
                if(formatter.parse((String) ((JSONObject) jsonArray.get(i)).get("date")).after(formatter.parse(typeParam[1]))){
                    jsonArray_red.add(jsonArray.get(i));
                }
            }
            else if (typeParam[0].contains("BEFORE")){
                if(formatter.parse((String) ((JSONObject) jsonArray.get(i)).get("date")).before(formatter.parse(typeParam[1]))){
                    jsonArray_red.add(jsonArray.get(i));
                }
            }
        }
        return jsonArray_red;
    }

    public static JSONArray FilterPrice(String string, JSONArray jsonArray){
        String[] typeParam = string.split(" ");
        JSONArray jsonArray_red = new JSONArray();
        jsonArray.forEach(obj->{
            if ((typeParam[0].contains("LESS")) &
                    (Integer.parseInt(((JSONObject) obj).get("price").toString()) <= Integer.parseInt(typeParam[1]))){
                jsonArray_red.add(obj);
            }
            else if ((typeParam[0].contains("GREATER")) &
                    (Integer.parseInt(((JSONObject) obj).get("price").toString()) >= Integer.parseInt(typeParam[1]))){
                jsonArray_red.add(obj);
            }
        });
        return jsonArray_red;
    }
}
