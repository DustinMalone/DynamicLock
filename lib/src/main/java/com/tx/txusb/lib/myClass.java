package com.tx.txusb.lib;

public class myClass {
    public static  void main(String args[]){
//        String s="[\"0\",\"1\",\"2\",\"3\",\"4\"]";
//        String[] a=s.replace("\"","").split(",");
//        System.out.print(a[0]);

//        {"timestamp":"1537335982",
//                "appId":"d0717b1c6b754e918b15a00a0d0db037",
//                "method":"utopa.product.cooperator.listDisplayProductRecord",
//                "sign":"9242099e0a56316b0fdfa05bf8b67eb1",
//                "params":{"b":1,"a":"x","c":{"e":2,"d":1}},
//            "ver":"1.0"
//        }

//        HashMap<String,String> hashMap=new HashMap<>();
//        hashMap.put("ver","1.0");
//        hashMap.put("timestamp",System.currentTimeMillis()/1000+"");
//        hashMap.put("appId","d0717b1c6b754e918b15a00a0d0db037");
//        hashMap.put("method","utopa.product.cooperator.listDisplayProductRecord");
//        hashMap.put("params","{\"pageNo\":1,\"pageSize\":20}");
//        System.out.println(hashMap.get("timestamp"));
//       System.out.print(SignParamUtil.getSignStr(hashMap,"VTp40mKhsM2ApQ4hhuORPRgidelp5lewuzNDUDQijGicVyQ0EQUByXTImu1bE8xX"));

//        String str = "{\"pageNo\":1,\"pageSize\":20}"; //默认环境，已是UTF-8编码
//        try {
//            String strGBK = URLEncoder.encode(str, "GBK");
//            System.out.println(strGBK);
//            String strs = URLEncoder.encode(str, "UTF-8");
//            System.out.println(strs);
//            String strUTF8 = URLDecoder.decode(str, "UTF-8");
//            System.out.println(strUTF8);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        String s="accessToken=8a3cf6a0b98dee68d624332382b5810145c48cce2e2d7fbdea1afc51c7c6ad26&mode=cart&quantity=1&sku_id=104888&user_id=9&key=HN**pYQRPnyEaBpXHqH%C3ScHF7ML4QkmksQj6Q1*rzk#SX45d";
        System.out.println(MD5Utils.getPwd(s).toUpperCase());

    }
}
