

import org.apache.axis.utils.StringUtils;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class test3 {

    /**
     * CSV文件生成方法
     * @param head 文件头
     * @param dataList 数据列表
     * @param outPutPath 文件输出路径
     * @param filename 文件名
     * @return
     */
    public static File createCSVFile(List<Object> head, List<List<Object>> dataList, String outPutPath, String filename) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if(csvFile.exists()){
                csvFile.delete();
                System.out.println(outPutPath+"文件已删除");
            }
            else{
                System.out.println("删除了为什么还有我");
                csvFile.createNewFile();
            }


            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GBK"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            System.out.println("this is rowstr:"+rowStr);
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    public static void main(String[] args) {
        List<Object> exportData = new ArrayList<Object>();
        exportData.add("Times");
        exportData.add("loaders");

        List<List<Object>> datalist = new ArrayList<List<Object>>();
//        List<Object> data=new ArrayList<Object>();
//        data.add(5);
//        data.add(0.2);
//
//        List<Object> data1=new ArrayList<Object>();
//        data1.add(10);
//        data1.add(0.8);
//
//        List<Object> data3=new ArrayList<Object>();
//        data3.add(15);
//        data3.add(0.3);



        int flag = 5;
        for(int i = 0;i<5;i++)
        {
            List data = new ArrayList();
            data.add(flag);
            data.add(i);
            flag = flag+5;
            datalist.add(data);
        }


        String path = "C:\\Users\\pc\\pythonCode\\wll";
        String fileName = "loaders";

        File file = createCSVFile(exportData, datalist, path, fileName);
        String fileName2 = file.getName();
        System.out.println("文件名称：" + fileName2);
    }

    // 如果需要导出时间格式，在时间数据的两边加上"\t"


}
